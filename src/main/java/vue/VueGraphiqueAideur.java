package vue;

import controleur.ControleurInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;
import modele.xmldata.*;

import java.util.*;
import java.util.Map.Entry;

/**
 * Contient les méthodes permettant d'afficher les éléments dans la zone
 * graphique
 *
 * @author David
 */
public class VueGraphiqueAideur {

    /**
     * Contient tous les points graphiques actuellement afficher pour le plan
     * grâce à leur id, et pour chaque intersection, ses arcs graphiques ainsi
     * que l'intersection ciblée
     */
    private Map<Integer, Pair<Ellipse, Collection<Integer>>> intersectionsGraphiques = null;

    /**
     * Contient l'échelle X actuelle par rapport à laquelle les intersections
     * sont affichés
     */
    private double echelleXIntersection = 0;
    /**
     * Contient l'échelle Y actuelle par rapport à laquelle les intersections
     * sont affichés
     */
    private double echelleYIntersection = 0;

    /**
     * Liste des id des livraisons récupéreés après avoir chargé la demande, associés au numéro de la fenêtre
     */
    private Map<Integer, Integer> listeIdLivraison;

    private List<Livraison> livraisons = null;

    /**
     * Le canvas graphique sur lequel on dessinera les éléments graphiques
     */
    private StackPane canvas;

    private Group group;

    private ScrollPane scrollPane;

    private Slider sliderZoom;
    private ControleurInterface controleurInterface;

    /**
     * Constructeur de la vue graphique
     *
     * @param canvas Le canvas sur lequel on dessinera les éléments graphiques
     */
    public VueGraphiqueAideur(StackPane canvas, Group group, ScrollPane scrollPane, Slider slider) {
        this.canvas = canvas;
        this.group = group;
        this.scrollPane = scrollPane;
        this.sliderZoom = slider;
        initzoom();
        
        canvas.setOnMouseMoved(new HoverGraphiqueGestionnaireEvenement());
        canvas.setOnMouseClicked(new ClicGraphiqueGestionnaireEvenement());
    }
    
    private class HoverGraphiqueGestionnaireEvenement implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			Livraison l = estSurLivraison(event.getX(), event.getY());
			if (l == null) {
				desactiverSurbrillance();
				return;
			}
			
			surbrillanceLivraison(l);
			// TODO surbrillance de la zone dans la vue textuelle
		}
    }
    
    private class ClicGraphiqueGestionnaireEvenement implements EventHandler<MouseEvent> {

		@Override
		public void handle(MouseEvent event) {
			int idIntersection = estSurIntersection(event.getX(), event.getY());
			if (idIntersection == -1) {
				return;
			}
			
			controleurInterface.cliqueSurPlan(idIntersection);
		}
    }

    public void setControleurInterface(ControleurInterface controleurInterface) {
        this.controleurInterface = controleurInterface;
    }

    public StackPane getCanvas() {
    	return canvas;
    }

    private void initzoom() {
        final Group scrollContent = (Group) scrollPane.getContent();
        sliderZoom.setBlockIncrement(0.5);

        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable,
                                Bounds oldValue, Bounds newValue) {
                canvas.setMinSize(newValue.getWidth(), newValue.getHeight());
            }
        });


        /**
         * Liaison du slider avec le canvas.
         */
        canvas.scaleXProperty().bind(sliderZoom.valueProperty());
        canvas.scaleYProperty().bind(sliderZoom.valueProperty());

        canvas.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();


                double oldScale = canvas.getScaleX();

                if (event.getDeltaY() == 0) {
                    return;
                }
                if (event.getDeltaY() > 0) {
                    sliderZoom.increment();
                } else {
                    sliderZoom.decrement();
                }

                double newScale = canvas.getScaleX();

                double scaleFactor = newScale / oldScale;


                // amount of scrolling in each direction in scrollContent coordinate
                // Calcul le decalage
                Point2D scrollOffset = figureScrollOffset(scrollContent, scrollPane);

                // Repositionne la scrollbare pour que la fenetre soit centrée
                repositionScroller(scrollContent, scrollPane, scaleFactor, scrollOffset);


            }
        });

    }

    /**
     * Construit et affiche le graphe du plan de la ville sur le canvas
     * graphique de la fenêtre
     *
     * @param plan Le plan de la ville, chargée par le couche controleur et
     *             persistance
     */
    public void construireGraphe(PlanDeVille plan) {
    	intersectionsGraphiques = new HashMap<Integer, Pair<Ellipse,Collection<Integer>>>();
    	listeIdLivraison = null;
    	livraisons = null;
    	tournee = null;
        group.getChildren().clear();

        Map<Integer, Intersection> toutesIntersections = plan.getIntersections();
        intersectionsGraphiques = new HashMap<>();

        echelleXIntersection = 0;
        echelleYIntersection = 0;
        // Construction de toutes les intersections
        for (Intersection i : toutesIntersections.values()) {
            echelleXIntersection = Math.max(echelleXIntersection, i.getX());
            echelleYIntersection = Math.max(echelleYIntersection, i.getY());

            Collection<Integer> destinations = new ArrayList<>(); // Tous les tronçons de destinations
            destinations.addAll(i.getTroncons().keySet());
            intersectionsGraphiques.put(i.getId(),
                    new Pair<>(construireEllipse(i, ConstantesGraphique.COULEUR_INTERSECTION), destinations));
        }

        afficherPlan();
        listeIdLivraison = null;
    }

    /**
     * Affiche tous les points du plan et met à jour la taille du canvas
     * graphique. Les points sont toujours affichés par rapport : (leur taille
     * initiale dans le fichier XML / la plus grande taille dans le fichier XML)
     * => (la nouvelle taille / la taille du canvas)
     */
    public void afficherPlan() {
        if (intersectionsGraphiques == null || intersectionsGraphiques.isEmpty())
            return;

        // Affichage (+ mise à l'échelle) des intersections
        for (Pair<Ellipse, Collection<Integer>> pair : intersectionsGraphiques.values()) {
            afficherEllipse(pair.getKey());
        }

        // Affichage (+ mise à l'échalle) des tronçons
        for (Pair<Ellipse, Collection<Integer>> pair : intersectionsGraphiques.values()) {
            for (int destination : pair.getValue()) {
                afficherTroncon(pair.getKey(), intersectionsGraphiques.get(destination).getKey(),
                        ConstantesGraphique.COULEUR_TRONCON);
            }
        }

        // Mise à jour de l'échelle sur laquelle sont affichées les intersections du plan
        echelleXIntersection = 0;
        echelleYIntersection = 0;
        for (Pair<Ellipse, Collection<Integer>> pair : intersectionsGraphiques.values()) {
            echelleXIntersection = Math.max(echelleXIntersection, pair.getKey().getCenterX());
            echelleYIntersection = Math.max(echelleYIntersection, pair.getKey().getCenterY());
        }

        intersectionAuPremierPlan();
    }

    private void intersectionAuPremierPlan() {
        group.getChildrenUnmodifiable().stream()
                .filter(node -> node instanceof Ellipse)
                .forEach(node -> node.toFront());
    }

    /**
     * Affiche une livraison en surbrillance sur la partie graphique
     *
     * @param livraison La livraison à mettre en surbrillance
     */
    public void surbrillanceLivraison(Livraison livraison) {
        surbrillanceLivraison(livraison, true);
    }

    private void surbrillanceLivraison(Livraison livraison, boolean desactiverSurbrillance) {
        // Repeindre toutes les intersections en couleur normal (pour parvenir aux entrées et sorties non détectées) si demandé
        if (desactiverSurbrillance)
            desactiverSurbrillance();

        // Mise en surbrillance d'une intersection + agrandissement
        Ellipse livraisonGraphique = intersectionsGraphiques.get(livraison.getAdresse()).getKey();
        
        colorerEllipse(livraison.getAdresse(), livraisonGraphique);

        changerTailleEllipse(livraisonGraphique,
                ConstantesGraphique.DIAMETRE_INTERSECTION * ConstantesGraphique.COEFFICIENT_INTERSECTION_SURBRILLANCE);
    }

    /**
     * Mets plusieurs livraisons en surbrillance sur la partie graphique
     *
     * @param livraisons Les livraisons à mettre en surbrillance
     */
    public void surbrillanceLivraisons(Collection<Livraison> livraisons) {
        desactiverSurbrillance();

        // Surbrillance de toutes les livraisons
        livraisons.forEach(livraison -> surbrillanceLivraison(livraison, false));
    }

    /**
     * Désactive la surbrillance pour toutes les surbrillances
     */
    public void desactiverSurbrillance() {
    	if (intersectionsGraphiques == null || listeIdLivraison == null)
    		return;
    	
        Iterator<Entry<Integer, Pair<Ellipse, Collection<Integer>>>> it = intersectionsGraphiques.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<Integer, Pair<Ellipse, Collection<Integer>>> pairIntersection =
                    (Map.Entry<Integer, Pair<Ellipse, Collection<Integer>>>) it.next();

            int idIntersection = pairIntersection.getKey();
            Ellipse intersection = pairIntersection.getValue().getKey();
            changerTailleEllipse(intersection, ConstantesGraphique.DIAMETRE_INTERSECTION);

            colorerEllipse(idIntersection, intersection);
        }
    }

    /**
     * Construit un point graphique pour une intersection du plan de la ville
     *
     * @param i L'intersection
     * @return Le point du graphe, à sa position du fichier XML du plan de la
     * ville
     */
    private Ellipse construireEllipse(Intersection i, Paint couleur) {
        Ellipse ellipse = new Ellipse(i.getX(), i.getY(), ConstantesGraphique.DIAMETRE_INTERSECTION,
                ConstantesGraphique.DIAMETRE_INTERSECTION);
        colorerEllipse(ellipse, couleur);
        return ellipse;
    }

    /**
     * Affiche une ellipse sur l'interface, en fonction de la taille actuelle
     *
     * @param e Le point à afficher
     */
    private void afficherEllipse(Ellipse e) {
        double newX = e.getCenterX() * canvas.getWidth() / (echelleXIntersection + ConstantesGraphique.MARGE_INTERSECTION);
        double newY = e.getCenterY() * canvas.getHeight() / (echelleYIntersection + ConstantesGraphique.MARGE_INTERSECTION);

        e.setCenterX(newX);
        e.setCenterY(newY);

        group.getChildren().add(e);
    }

    /**
     * Change la couleur d'une ellipse
     *
     * @param idEllipse L'identifiant de l'ellipse
     * @param couleur   La nouvelle couleur
     */
    private void colorerEllipse(int idEllipse, Paint couleur) {
        colorerEllipse(intersectionsGraphiques.get(idEllipse).getKey(), couleur);
    }

    /**
     * Change la couleur d'une ellipse
     *
     * @param e       L'ellipse à modifier
     * @param couleur La nouvelle couleur
     */
    private void colorerEllipse(Ellipse e, Paint couleur) {
       
        e.setFill(couleur);
    }
    
    private void colorerEllipse(int idIntersection, Ellipse e) {
    	 // Choix de la bonne couleur
        Paint couleur;

        if (idIntersection == entrepot) {
            couleur = ConstantesGraphique.COULEUR_ENTREPOT;
        } else if (listeIdLivraison != null && listeIdLivraison.containsKey(idIntersection)) {
            couleur = ConstantesGraphique.COULEURS_FENETRES[listeIdLivraison.get(idIntersection) % ConstantesGraphique.COULEURS_FENETRES.length];
        } else {
            couleur = ConstantesGraphique.COULEUR_INTERSECTION;
        }
        
        colorerEllipse(e, couleur);
    }

    /**
     * Change le rayon d'une ellipse (qui est un cercle)
     *
     * @param e     L'ellipse à modifier
     * @param rayon Le nouveau rayon
     */
    private void changerTailleEllipse(Ellipse e, double rayon) {
        e.setRadiusX(rayon);
        e.setRadiusY(rayon);
    }

    /**
     * Affiche le lien entre les deux intersections dans une direction
     *
     * @param debut Le point de début
     * @param cible Le point de destination
     */
    private void afficherTroncon(Ellipse debut, Ellipse cible, Paint couleur) {
        double p1X = debut.getCenterX();
        double p1Y = debut.getCenterY();
        double p2X = cible.getCenterX();
        double p2Y = cible.getCenterY();

        Line ligne = new Line(p1X, p1Y, p2X, p2Y);
        ligne.setStroke(couleur);

        group.getChildren().add(ligne);

        // Création flèche
        double longueur = 8 * Math.sqrt(3);
        double demiLongueur = 4;

        double Ux = p2X - p1X;
        double Uy = p2Y - p1Y;

        double distance = Math.sqrt((Ux * Ux) + (Uy * Uy));

        Ux /= distance;
        Uy /= distance;

        double Vx = -Uy;
        double Vy = Ux;

        double extremiteFleche1X = p2X - longueur * Ux + demiLongueur * Vx;
        double extremiteFleche1Y = p2Y - longueur * Uy + demiLongueur * Vy;
        double extremiteFleche2X = p2X - longueur * Ux - demiLongueur * Vx;
        double extremiteFleche2Y = p2Y - longueur * Uy - demiLongueur * Vy;

        Polygon fleche = new Polygon(p2X, p2Y, extremiteFleche1X, extremiteFleche1Y, extremiteFleche2X,
                extremiteFleche2Y);
        fleche.setFill(couleur);

        group.getChildren().add(fleche);
    }

    /**
     * Id (adresse) de l'intersection où se situe l'entrepot
     */
    private int entrepot = -1;

    /**
     * Une liste d'entier = les id des intersections par lesquelles on passe dans une fenêtre
     */
    private List<List<Integer>> tournee;

    /**
     * Construit et affiche la tournée
     *
     * @param tournee
     */
    public void construireTournee(List<List<Integer>> tournee) {
        // Mémoriser l'ordre de tournée
        this.tournee = tournee;

        afficherTournee();
    }

    /**
     * Affiche la tournée (les tronçons empreintés entre les lieux de livraisons)
     */
    public void afficherTournee() {
        if (tournee == null || tournee.isEmpty())
            return;

        // Afficher tournée dans chaque fenêtre
        for (int idFenetre = 0; idFenetre < tournee.size(); ++idFenetre) {

            Paint couleur = ConstantesGraphique.COULEURS_FENETRES[(idFenetre + 1) % ConstantesGraphique.COULEURS_FENETRES.length];

            for (int idIntersectionLivraison = 0; idIntersectionLivraison < tournee.get(
                    idFenetre).size() - 1; idIntersectionLivraison++) {
                int departIntersectionId = tournee.get(idFenetre).get(idIntersectionLivraison);
                int arriveeIntersectionId = tournee.get(idFenetre).get(idIntersectionLivraison + 1);
                Ellipse debut = intersectionsGraphiques.get(departIntersectionId).getKey();
                Ellipse fin = intersectionsGraphiques.get(arriveeIntersectionId).getKey();

                afficherTroncon(debut, fin, couleur);
            }
        }

        // Affichage départ de l'entrepot
        Ellipse entrepot = intersectionsGraphiques.get(this.entrepot).getKey();
        Ellipse premierNoeud = intersectionsGraphiques.get(tournee.get(0).get(0)).getKey();
        afficherTroncon(entrepot, premierNoeud, ConstantesGraphique.COULEURS_FENETRES[1]);

        // Afficher les trajets entre les fenêtres
        for (int idFenetre = 0; idFenetre < tournee.size() - 1; ++idFenetre) {

            Paint couleur = ConstantesGraphique.COULEURS_FENETRES[(idFenetre + 2) % ConstantesGraphique.COULEURS_FENETRES.length];

            int derniereIntersectionFenetre = tournee.get(idFenetre).get(tournee.get(idFenetre).size() - 1);
            int premiereIntersectionFenetreSuivante = tournee.get(idFenetre + 1).get(0);

            Ellipse debut = intersectionsGraphiques.get(derniereIntersectionFenetre).getKey();
            Ellipse fin = intersectionsGraphiques.get(premiereIntersectionFenetreSuivante).getKey();

            afficherTroncon(debut, fin, couleur);
        }

        intersectionAuPremierPlan();
    }

    public void construireDemande(final Demande demande) {
        tournee = null;
        
        listeIdLivraison = new HashMap<Integer, Integer>();
        livraisons = new ArrayList<Livraison>();
        this.entrepot = demande.getEntrepot().getId();
        

    	List<Fenetre> fenetres = demande.getFenetres();
        for (int i = 0; i < fenetres.size(); ++i) {
    		Fenetre fenetre = fenetres.get(i);
            for (Entry<Integer, Livraison> pair : fenetre.getListeLivraisons().entrySet()) {
            	livraisons.add(pair.getValue());
                if(pair.getKey() != -1) // -1 c'est l'identifiant de l'entrepot qui est crée comme une livraison dans une fenetre speciale
                    listeIdLivraison.put(pair.getValue().getAdresse(), i);
            }
        }

        afficherDemande();
    }

    /**
     * Affiche les livraisons contenues dans une demande de livraison
     */
    public void afficherDemande() {

        if (listeIdLivraison == null)
            return;

        // Affichage entrepot
        colorerEllipse(entrepot, ConstantesGraphique.COULEUR_ENTREPOT);

        // Affichage des livraisons
        listeIdLivraison.forEach((idLivraison, idCouleur) -> colorerEllipse(idLivraison, ConstantesGraphique.COULEURS_FENETRES[idCouleur % ConstantesGraphique.COULEURS_FENETRES.length]));

        intersectionAuPremierPlan();
    }


    private Point2D figureScrollOffset(Node scrollContent, ScrollPane scroller) {
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        double hScrollProportion = (scroller.getHvalue() - scroller.getHmin()) / (scroller.getHmax() - scroller.getHmin());
        double scrollXOffset = hScrollProportion * Math.max(0, extraWidth);
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        double vScrollProportion = (scroller.getVvalue() - scroller.getVmin()) / (scroller.getVmax() - scroller.getVmin());
        double scrollYOffset = vScrollProportion * Math.max(0, extraHeight);
        return new Point2D(scrollXOffset, scrollYOffset);
    }

    private void repositionScroller(Node scrollContent, ScrollPane scroller, double scaleFactor, Point2D scrollOffset) {
        double scrollXOffset = scrollOffset.getX();
        double scrollYOffset = scrollOffset.getY();
        double extraWidth = scrollContent.getLayoutBounds().getWidth() - scroller.getViewportBounds().getWidth();
        if (extraWidth > 0) {
            double halfWidth = scroller.getViewportBounds().getWidth() / 2;
            double newScrollXOffset = (scaleFactor - 1) * halfWidth + scaleFactor * scrollXOffset;
            scroller.setHvalue(
                    scroller.getHmin() + newScrollXOffset * (scroller.getHmax() - scroller.getHmin()) / extraWidth);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
        double extraHeight = scrollContent.getLayoutBounds().getHeight() - scroller.getViewportBounds().getHeight();
        if (extraHeight > 0) {
            double halfHeight = scroller.getViewportBounds().getHeight() / 2;
            double newScrollYOffset = (scaleFactor - 1) * halfHeight + scaleFactor * scrollYOffset;
            scroller.setVvalue(
                    scroller.getVmin() + newScrollYOffset * (scroller.getVmax() - scroller.getVmin()) / extraHeight);
        } else {
            scroller.setHvalue(scroller.getHmin());
        }
    }

	/**
	 * Retourne la livraison si les coordonnées paramètres correspondent à la position de la livraison
	 * @param x Coordonnées X du canvas graphique
	 * @param y Coordonnées Y du canvas graphique
	 * @return null si les coordonnées ne sont sur aucune livraison
	 */
	private Livraison estSurLivraison(double x, double y) {
		if (livraisons == null || livraisons.isEmpty() || intersectionsGraphiques == null) {
			return null;
		}

		for (Livraison l : livraisons) {
			Ellipse e = intersectionsGraphiques.get(l.getAdresse()).getKey();
			if (estSurEllipse(e, x, y)) {
				return l;
			}
		}

		return null;
	}
	
	/**
	 * Renvoie l'id de l'intersection sur laquelle on a cliqué
	 * @param x La position x sur le canvas graphique
	 * @param y La position y sur le canvas graphique
	 * @return -1 si les positions ne sont pas sur une intersection
	 */
	private int estSurIntersection(double x, double y) {
		if (intersectionsGraphiques == null || intersectionsGraphiques.isEmpty()) {
			return -1;
		}
		
		for (Entry<Integer, Pair<Ellipse, Collection<Integer>>> pair : intersectionsGraphiques.entrySet()) {
			Ellipse e = pair.getValue().getKey();
			if (estSurEllipse(e, x, y)) {
				return pair.getKey();
			}
		}
		return -1;
	}
	
	private boolean estSurEllipse(Ellipse e, double x, double y) {
		return e.getCenterX() - ConstantesGraphique.DIAMETRE_PERMISSION <= x && x <= e.getCenterX() + ConstantesGraphique.DIAMETRE_PERMISSION
				&& e.getCenterY() - ConstantesGraphique.DIAMETRE_PERMISSION <= y && y <= e.getCenterY() + ConstantesGraphique.DIAMETRE_PERMISSION;
	}

    /**
     * Contient les constantes définissant certaines propriétés (taille, marge, couleur,...) de la fenêtre
     */
    private static class ConstantesGraphique {
        /**
         * La taille sur l'interface graphique d'une intersection du plan de la
         * ville
         */
        private final static double DIAMETRE_INTERSECTION = 7;
        
        private final static double DIAMETRE_PERMISSION = 13;
        
        /**
         * La marge à laisser sur les côté du canvas graphique afin d'avoir plus
         * du lisibilité
         */
        private final static int MARGE_INTERSECTION = 30;

        private final static double MAX_ZOOM = 15;

        /**
         * Coefficient mutliplicateur des ellipse pour les livraisons
         */
        private final static double COEFFICIENT_INTERSECTION_SURBRILLANCE = 1.5;

        private final static Paint COULEUR_INTERSECTION = Color.WHITE;
        private final static Paint COULEUR_TRONCON = Color.WHITE;

        private final static Paint COULEUR_ENTREPOT = Color.RED;

        private final static Paint[] COULEURS_FENETRES = new Paint[] {
            Color.LIGHTSEAGREEN,
            Color.BLUE,
            Color.GREEN,
            Color.VIOLET,
            Color.ORANGE,
            Color.CHARTREUSE,
            Color.DARKSLATEBLUE
        };
    }

	public void nettoyerAffichage() {
		group.getChildren().clear();
	}
}
