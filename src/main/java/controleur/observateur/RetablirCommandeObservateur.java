package controleur.observateur;

/**
 * Joue le rôle d'observeur quand l'action retablir doit etre activée.
 * @author Maxou
 */
public interface RetablirCommandeObservateur {

    /**
     * Notifie les observeurs du rétablissement d'une commande
     * @param active Vrai s'il faut activer suite au rétablissement de la commande
     */
    void notifierObservateurRetablirCommande(boolean active);
}
