package controleur;

/**
 * Joue le rôle d'observeur quand l'action annulée doit etre activée
 * @author Maxou
 */
public interface AnnulerCommandeObserveur
{

    /**
     * @param active Dit si l'action Annuler doit etre activée ou non
     */
    void notificationAnnulerCommande(boolean active);

}
