package controleur.observateur;

/**
 * Interface pour les observateurs de l'état des boutons
 * @author maxou
 */
public interface ActivationFonctionnalitesObservateur {
	
    /**
     * Notifie les observateurs qui attendent un message d'activation notamment les boutons de fonctionnalités (ajouter,
     * supprimer, echanger) qu'il faut changer l'état d'activation
     *
     * @param desactiver Vrai si on doit désactiver les observeurs
     */
    void notifierObservateursActivation(boolean desactiver);

}
