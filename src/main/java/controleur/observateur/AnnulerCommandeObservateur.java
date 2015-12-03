package controleur.observateur;

/**
 * Interface pour l'observateur qui permet d'activer l'action annuler dans le menu
 * @author Maxou
 */
public interface AnnulerCommandeObservateur {

    /**
     * Notifie l'observateur s'il faut activer l'action dans le menu qui permet d'annuler une commande
     * @param active Dit si l'action Annuler doit etre activée ou non
     */
    void notifierObservateurAnnulerCommande(boolean active);

}
