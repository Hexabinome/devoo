package vue;

import modele.xmldata.Livraison;

/**
 * Created by elmhaidara on 23/11/15.
 */
public class DetailLivraison implements ObjetVisualisable {

    private final Livraison livraison;

    public DetailLivraison(Livraison livraison){
        this.livraison = livraison;
    }

    @Override
    public String afficherCaracteriquesGloable() {
        return livraison.getId() + " - Client " + livraison.getClientId() + " à " + livraison.getAdresse();
    }

    @Override
    public String afficherCaracteriqueSpeciale() {
        return String.valueOf(livraison.getHoraireDePassage());
    }
}
