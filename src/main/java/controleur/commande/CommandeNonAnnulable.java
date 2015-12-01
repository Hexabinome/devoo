package controleur.commande;


/**
 * Représente les commandes non annulable
 * @author Max Schiedermeier
 */
public abstract class CommandeNonAnnulable implements Commande {

    @Override
    public boolean estAnnulable() {
        return false;
    }

    @Override
    public void annuler() {
        throw new RuntimeException("Il n'est pas possible d'annuler cette commande.");
    }


}
