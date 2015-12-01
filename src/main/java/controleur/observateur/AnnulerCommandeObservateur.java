package controleur.observateur;

/**
 * Joue le rôle d'observeur quand l'action annulée doit etre activée
 * @author Maxou
 */
public interface AnnulerCommandeObservateur {

    /**
     * @param active Dit si l'action Annuler doit etre activée ou non
     */
    void notifierObservateurAnnulerCommande(boolean active);

}
