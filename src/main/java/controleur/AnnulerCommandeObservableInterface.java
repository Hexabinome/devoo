package controleur;

/**
 * Joue le rôle d'observeur quand l'action annulée doit etre activée
 * @author Maxou
 */
public interface AnnulerCommandeObservableInterface {

    /**
     * @param active Dit si l'action Annuler doit etre activée ou non
     */
    void notifierObserveursAnnulerCommande(boolean active);

}
