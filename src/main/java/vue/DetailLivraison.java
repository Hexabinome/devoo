package vue;

import modele.donneesxml.Livraison;

/**
 * Gére l'affichage sous forme textuelle des details d'une livraison dans la TreeTableView.
 */
public class DetailLivraison extends ObjetVisualisable {

    /** La livraison affichée */
    private final Livraison livraison;

    /** Constructeur du détail de livraison
     * @param livraison La livraison associée
     */
    public DetailLivraison(Livraison livraison) {
        this.livraison = livraison;
    }

    @Override
    public String afficherCaracteriquesGlobales() {
        return String.format("%d - Client %d à %d", livraison.getId(), livraison.getClientId(), livraison.getAdresse());
    }

    @Override
    public String afficherCaracteriquesSpeciales() {
        return convertirEnHeureLisible(livraison.getHoraireDePassage());
    }


    /**
     * @return La livraison associée
     */
    public Livraison getLivraison() {
        return livraison;
    }
}
