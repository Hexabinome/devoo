package vue;

import modele.donneesxml.Fenetre;

/**
 * Gére l'affichage sous forme textuelle des details d'une fenêtre de livraison dans la TreeTableView.
 */
public class DetailFenetre extends ObjetVisualisable {

    private final Fenetre fenetre;

    public DetailFenetre(Fenetre fenetre) {
        this.fenetre = fenetre;
    }

    @Override
    public String afficherCaracteriquesGloable() {
        return convertirEnHeureLisible(fenetre.getTimestampDebut()) + " - " + convertirEnHeureLisible(
                fenetre.getTimestampFin());
    }

    @Override
    public String afficherCaracteriqueSpeciale() {
        // Pour la fenetre ya rien de particulier à afficher dans ce cas
        return "";
    }

    public Fenetre getFenetre() {
        return fenetre;
    }
}
