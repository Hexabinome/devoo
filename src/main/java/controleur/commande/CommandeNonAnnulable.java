package controleur.commande;


/**
 * Représente les commandes non annulables
 *
 * @author Max Schiedermeier
 */
public abstract class CommandeNonAnnulable implements Commande {
    @Override
    public void annuler() {
        throw new RuntimeException("Il n'est pas possible d'annuler cette commande.");
    }


}
