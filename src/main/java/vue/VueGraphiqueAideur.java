package vue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;
import modele.xmldata.Demande;
import modele.xmldata.Fenetre;
import modele.xmldata.Intersection;
import modele.xmldata.Livraison;
import modele.xmldata.PlanDeVille;

/**
 * Contient les méthodes permettant d'afficher les éléments dans la zone
 * graphique
 *
 * @author David
 *
 */
public class VueGraphiqueAideur
{

    /**
     * Contient tous les points graphiques actuellement afficher pour le plan
     * grâce à leur id, et pour chaque intersection, ses arcs graphiques ainsi
     * que l'intersection ciblée
     */
    private Map<Integer, Pair<Ellipse, Collection<Integer>>> intersectionsGraphiques = new HashMap<>();

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
     * Liste des id des livraisons récupéreés après avoir chargé la demande
     */
    private List<Integer> listeIdLivraison;

    /**
     * Le canvas graphique sur lequel on dessinera les éléments graphiques
     */
    private PaneZoomable canvas;

    /**
     * Constructeur de la vue graphique
     * @param canvas Le canvas sur lequel on dessinera les éléments graphiques
     */
    public VueGraphiqueAideur(PaneZoomable canvas) {
        this.canvas = canvas;
    }

    /**
     * Construit et affiche le graphe du plan de la ville sur le canvas
     * graphique de la fenêtre
     *
     * @param plan Le plan de la ville, chargée par le couche controleur et
     * persistance
     */
    public void construireGraphe(PlanDeVille plan) {
        canvas.getChildren().clear();

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
                afficherTroncon(pair.getKey(), intersectionsGraphiques.get(destination).getKey(), ConstantesGraphique.COULEUR_TRONCON);
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
        canvas.getChildrenUnmodifiable().stream()
	    	.filter(node -> node instanceof Ellipse)
	    	.forEach(node -> node.toFront());
	}
    
    /**
     * Affiche une livraison en surbrillance sur la partie graphique
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
    	colorerEllipse(livraisonGraphique, ConstantesGraphique.COULEUR_INTERSECTION_SURBRILLANCE);
    	
    	changerTailleEllipse(livraisonGraphique, ConstantesGraphique.DIAMETRE_INTERSECTION * ConstantesGraphique.COEFFICIENT_INTERSECTION_SURBRILLANCE);
    }
    
    /**
     * Mets plusieurs livraisons en surbrillance sur la partie graphique
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
    	Iterator<Entry<Integer, Pair<Ellipse, Collection<Integer>>>> it = intersectionsGraphiques.entrySet().iterator();
    	
    	while(it.hasNext()) {
    		Map.Entry<Integer, Pair<Ellipse, Collection<Integer>>> pairIntersection = 
    				(Map.Entry<Integer, Pair<Ellipse, Collection<Integer>>>)it.next();
    		
    		int idIntersection = pairIntersection.getKey();
    		Ellipse intersection = pairIntersection.getValue().getKey();
    		changerTailleEllipse(intersection, ConstantesGraphique.DIAMETRE_INTERSECTION);
    		
    		// Choix de la bonne couleur
    		Paint couleur;
    		if (idIntersection == entrepot) {
    			couleur = ConstantesGraphique.COULEUR_ENTREPOT;
    		}
    		else if (listeIdLivraison.contains(idIntersection)) {
    			couleur = ConstantesGraphique.COULEUR_INTERSECTION_LIVRAISON;
    		}
    		else {
    			couleur = ConstantesGraphique.COULEUR_INTERSECTION;
    		}
    		
    		colorerEllipse(intersection, couleur);
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
        Ellipse ellipse = new Ellipse(i.getX(), i.getY(), ConstantesGraphique.DIAMETRE_INTERSECTION, ConstantesGraphique.DIAMETRE_INTERSECTION);
        colorerEllipse(ellipse, couleur);
        canvas.setListenerForNode(ellipse);
        return ellipse;
    }

    /**
     * Affiche une ellipse sur l'interface, en fonction de la taille actuelle
     * @param e Le point à afficher
     */
    private void afficherEllipse(Ellipse e) {
        double newX = e.getCenterX() * canvas.getWidth() / (echelleXIntersection + ConstantesGraphique.MARGE_INTERSECTION);
        double newY = e.getCenterY() * canvas.getHeight() / (echelleYIntersection + ConstantesGraphique.MARGE_INTERSECTION);

        e.setCenterX(newX);
        e.setCenterY(newY);

        canvas.getChildren().add(e);
    }
    
    /** Change la couleur d'une ellipse
     * @param idEllipse L'identifiant de l'ellipse
     * @param couleur La nouvelle couleur
     */
    private void colorerEllipse(int idEllipse, Paint couleur) {
    	colorerEllipse(intersectionsGraphiques.get(idEllipse).getKey(), couleur);
    }
    
    /** Change la couleur d'une ellipse
     * @param e L'ellipse à modifier
     * @param couleur La nouvelle couleur
     */
    private void colorerEllipse(Ellipse e, Paint couleur) {
    	e.setFill(couleur);
    }
    
    /** Change le rayon d'une ellipse (qui est un cercle)
     * @param e L'ellipse à modifier
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

        canvas.getChildren().add(ligne);

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

        canvas.getChildren().add(fleche);
    }

    /**
     * Id (adresse) de l'intersection où se situe l'entrepot
     */
    private int entrepot;
    
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

            Paint couleur = ConstantesGraphique.COULEURS_FENETRES[idFenetre % ConstantesGraphique.COULEURS_FENETRES.length];
            
            for (int idIntersectionLivraison = 0; idIntersectionLivraison < tournee.get(idFenetre).size() - 1; idIntersectionLivraison++) {
                int departIntersectionId = tournee.get(idFenetre).get(idIntersectionLivraison);
                int arriveeIntersectionId = tournee.get(idFenetre).get(idIntersectionLivraison+1);
                Ellipse debut = intersectionsGraphiques.get(departIntersectionId).getKey();
                Ellipse fin = intersectionsGraphiques.get(arriveeIntersectionId).getKey();

                afficherTroncon(debut, fin, couleur);
            }
        }
        
        // Afficher les trajets entre les fenêtres
        for (int idFenetre = 0; idFenetre < tournee.size() - 1; ++idFenetre) {

            Paint couleur = ConstantesGraphique.COULEURS_FENETRES[idFenetre % ConstantesGraphique.COULEURS_FENETRES.length];
            
            int derniereIntersectionFenetre = tournee.get(idFenetre).get(tournee.get(idFenetre).size() - 1);
            int premiereIntersectionFenetreSuivante = tournee.get(idFenetre + 1).get(tournee.get(idFenetre + 1).size() - 1);

            Ellipse debut = intersectionsGraphiques.get(derniereIntersectionFenetre).getKey();
            Ellipse fin = intersectionsGraphiques.get(premiereIntersectionFenetreSuivante).getKey();

            afficherTroncon(debut, fin, couleur);
        }
        
        intersectionAuPremierPlan();
    }

    public void construireDemande(final Demande demande) {
        listeIdLivraison = new LinkedList<>();
        this.entrepot = demande.getEntrepot().getId();

        for (Fenetre fenetre : demande.getFenetres()) {
            fenetre.getListeLivraisons().forEach((idLivraison, livraison) -> {
                if(idLivraison != -1) // -1 c'est l'identifiant de l'entrepot qui est crée comme une livraison dans une fenetre speciale
                    listeIdLivraison.add(livraison.getAdresse());
            });
        }

        afficherDemande();
    }

    /**
     * Affiche les livraisons contenues dans une demande de livraison
     */
    public void afficherDemande() {

        if(listeIdLivraison == null)
            return;

        // Affichage entrepot
        colorerEllipse(entrepot, ConstantesGraphique.COULEUR_ENTREPOT);

        // Affichage des 
        listeIdLivraison.forEach(idLivraison -> colorerEllipse(idLivraison, ConstantesGraphique.COULEUR_INTERSECTION_LIVRAISON));
        
        intersectionAuPremierPlan();
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
        /**
         * La marge à laisser sur les côté du canvas graphique afin d'avoir plus
         * du lisibilité
         */
        private final static int MARGE_INTERSECTION = 30;

        /**
         * Coefficient mutliplicateur des ellipse pour les livraisons
         */
        private final static double COEFFICIENT_INTERSECTION_SURBRILLANCE = 1.4;

        private final static Paint COULEUR_INTERSECTION = Color.WHITE;
        private final static Paint COULEUR_INTERSECTION_LIVRAISON = Color.BLUE;
        private final static Paint COULEUR_INTERSECTION_SURBRILLANCE = Color.YELLOW;
        private final static Paint COULEUR_TRONCON = Color.WHITE;

        private final static Paint COULEUR_ENTREPOT = Color.RED;

        private final static Paint[] COULEURS_FENETRES = new Paint[] {
            Color.RED,
            Color.LIGHTBLUE,
            Color.GREEN,
            Color.VIOLET
        };
    }
}
