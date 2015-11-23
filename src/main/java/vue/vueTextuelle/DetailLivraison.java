package vue.vueTextuelle;

import modele.xmldata.Livraison;

/**
 * Created by elmhaidara on 23/11/15.
 */
public class DetailLivraison extends ObjetVisualisable {

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

    @Override
    public void accepter(Visiteur v) {
        v.visit(this);
    }

    public Livraison getLivraison() {
        return livraison;
    }
}
