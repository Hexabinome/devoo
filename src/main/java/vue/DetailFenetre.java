package vue;

import modele.donneesxml.Fenetre;

/**
 * Gére l'affichage sous forme textuelle des details d'une fenêtre de livraison dans la TreeTableView.
 */
public class DetailFenetre extends ObjetVisualisable {

    /** Le fenêtre affichée */
    private final Fenetre fenetre;

    /** Constructeur du détail fenêtre
     * @param fenetre La fenêtre associée
     */
    public DetailFenetre(Fenetre fenetre) {
        this.fenetre = fenetre;
    }

    @Override
    public String afficherCaracteriquesGlobales() {
        return convertirEnHeureLisible(fenetre.getTimestampDebut()) + " - " + convertirEnHeureLisible(
                fenetre.getTimestampFin());
    }

    @Override
    public String afficherCaracteriquesSpeciales() {
        // Pour la fenetre ya rien de particulier à afficher dans ce cas
        return "";
    }

    /**
     * @return La fenêtre associée à ce détail
     */
    public Fenetre getFenetre() {
        return fenetre;
    }
}
