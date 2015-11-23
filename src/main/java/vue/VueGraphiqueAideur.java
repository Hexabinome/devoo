package vue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.util.Pair;
import modele.xmldata.Demande;
import modele.xmldata.Intersection;
import modele.xmldata.Livraison;
import modele.xmldata.PlanDeVille;


/**
 * Contient les méthodes permettant d'afficher les éléments dans la zone graphique
 * @author David
 *
 */
public class VueGraphiqueAideur {
    /**
     * Contient tous les points graphiques actuellement afficher pour le plan grâce à leur
     * id, et pour chaque intersection, ses arcs graphiques ainsi que
     * l'intersection ciblée
     */
    private Map<Integer, Pair<Ellipse, Collection<Integer>>> intersectionsGraphiques = new HashMap<Integer, Pair<Ellipse, Collection<Integer>>>();

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
	
	private Pane canvas;
	
	public VueGraphiqueAideur(Pane canvas) {
		this.canvas = canvas;
	}
	
    /**
     * Construit et affiche le graphe du plan de la ville sur le canvas
     * graphique de la fenêtre
     *
     * @param plan Le plan de la ville, chargée par le couche controleur et
     *             persistance
     */
    public void construireGraphe(PlanDeVille plan) {
        canvas.getChildren().clear();

        Map<Integer, Intersection> toutesIntersections = plan.getIntersections();
        intersectionsGraphiques = new HashMap<Integer, Pair<Ellipse, Collection<Integer>>>();

        echelleXIntersection = 0;
        echelleYIntersection = 0;
        // Construction de toutes les intersections
        for (Intersection i : toutesIntersections.values()) {
            echelleXIntersection = Math.max(echelleXIntersection, i.getX());
            echelleYIntersection = Math.max(echelleYIntersection, i.getY());

            Collection<Integer> destinations = new ArrayList<Integer>(); // Tous les tronçons de destinations
            destinations.addAll(i.getTroncons().keySet());

            intersectionsGraphiques.put(i.getId(),
                    new Pair<Ellipse, Collection<Integer>>(construireEllipse(i, ConstantesGraphique.COULEUR_INTERSECTION), destinations));
        }

        afficherPlan();
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

        // Put nodes to the front
        for (Node n : canvas.getChildrenUnmodifiable()) {
            if (n instanceof Ellipse)
                n.toFront();
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
        Ellipse intersection = new Ellipse(i.getX(), i.getY(), ConstantesGraphique.DIAMETRE_INTERSECTION, ConstantesGraphique.DIAMETRE_INTERSECTION);
        intersection.setFill(couleur);
        return intersection;
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

        canvas.getChildren().add(e);
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
    
    private static class ConstantesGraphique {
        /**
         * La taille sur l'interface graphique d'une intersection du plan de la
         * ville
         */
        private final static double DIAMETRE_INTERSECTION = 7;
        /**
         * La marge à laisser sur les côté du canvas graphique afin d'avoir plus du
         * lisibilité
         */
        private final static int MARGE_INTERSECTION = 30;
        
        private final static Paint COULEUR_INTERSECTION = Color.WHITE;
        private final static Paint COULEUR_TRONCON = Color.WHITE;
        
        private final static Paint COULEUR_ENTREPOT = Color.RED;
        
        private final static Paint[] COULEURS_FENETRES = new Paint[] {
        	Color.LIGHTBLUE,
        	Color.YELLOW,
        	Color.RED,
        	Color.GREEN
        };
    }

    private Pair<Integer, Ellipse> entrepot;
    private List<List<Integer>> tournee;
    
	/**
	 * Construit et affiche la tournée
	 * @param entrepot
	 * @param tournee
	 * @param demande
	 */
	public void construireTournee(Intersection entrepot, List<List<Integer>> tournee, Demande demande) {
		
		this.entrepot = new Pair<Integer, Ellipse>(entrepot.getId(), construireEllipse(entrepot, ConstantesGraphique.COULEUR_ENTREPOT));
		
		this.tournee = new ArrayList<List<Integer>>();
		for (int idxFenetre = 0; idxFenetre < demande.getFenetres().size(); ++idxFenetre) {
			List<Integer> tourneeFenetre = tournee.get(idxFenetre);
			Map<Integer, Livraison> livraisonsFenetre = demande.getFenetres().get(idxFenetre).getLivraisons();
			
			List<Integer> tourneeFenetreGraphique = new ArrayList<Integer>();
			for (int idLivraison : tourneeFenetre) {
				tourneeFenetreGraphique.add(livraisonsFenetre.get(idLivraison).getAdresse());
			}
			
			this.tournee.add(tourneeFenetreGraphique);
		}
		
		afficherTournee();
	}
	
	public void afficherTournee() {
		afficherEllipse(entrepot.getValue());
		
		// Afficher tournée dans chaque fenêtre
		for (int idFenetre = 0; idFenetre < tournee.size(); ++idFenetre) {

			Paint couleur = ConstantesGraphique.COULEURS_FENETRES[idFenetre % ConstantesGraphique.COULEURS_FENETRES.length];

			for (int idIntersectionLivraison = 0; idIntersectionLivraison < tournee.get(idFenetre).size() - 1; ++idIntersectionLivraison) {
				Ellipse debut = intersectionsGraphiques.get(idIntersectionLivraison).getKey();
				Ellipse fin = intersectionsGraphiques.get(idIntersectionLivraison+1).getKey();
				
				afficherTroncon(debut, fin, couleur);
			}
		}
		
		// Afficher les liaisons entre fenêtres et avec l'entrepôt
		for (int idFenetre = 0; idFenetre < tournee.size(); ++idFenetre) {
			Paint couleur = ConstantesGraphique.COULEURS_FENETRES[idFenetre % ConstantesGraphique.COULEURS_FENETRES.length];
			Ellipse debut;
			Ellipse fin;
			// Première fenêtre, départ de l'entrepot
			if (idFenetre == 0) {
				debut = entrepot.getValue();
				fin = intersectionsGraphiques.get(tournee.get(0).get(0)).getKey();
			}
			// Dernière fenetre, retour à l'entrepot
			else if (idFenetre + 1 == tournee.size()) {
				int idDerniereLivraison = tournee.get(idFenetre).get(tournee.get(idFenetre).size() - 1);
				debut = intersectionsGraphiques.get(idDerniereLivraison).getKey();
				fin = entrepot.getValue();
			}
			// Entre deux fenêtres
			else {
				int idDerniereLivraisonFenetre = tournee.get(idFenetre).get(tournee.get(idFenetre).size() - 1);
				int idPremiereLivraisonProchaineFenetre = tournee.get(idFenetre+1).get(0);
				debut = intersectionsGraphiques.get(idDerniereLivraisonFenetre).getKey();
				fin = intersectionsGraphiques.get(idPremiereLivraisonProchaineFenetre).getKey();
			}
			
			afficherTroncon(debut, fin, couleur);
		}
	}
}