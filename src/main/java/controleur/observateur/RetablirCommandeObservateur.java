package controleur.observateur;

/**
 * Interface pour l'observateur qui permet d'activer l'action retablir dans le menu
 * @author Maxou
 */
public interface RetablirCommandeObservateur {

    /**
     * Notifie l'observateur s'il faut activer l'action dans le menu qui permet de retablir une commande une commande
     * @param active Vrai s'il faut activer suite au rétablissement de la commande
     */
    void notifierObservateurRetablirCommande(boolean active);
}
