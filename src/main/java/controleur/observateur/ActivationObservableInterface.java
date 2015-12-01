package controleur.observateur;

/**
 * Interface pour les observables qui peuvent être activer
 * @author maxou
 */
public interface ActivationObservableInterface {
	
    /**
     * Notifie les observeurs qui attentent un message d'activation
     * @param desactiver Vrai si on doit désactiver les observeurs
     */
    void notifierObservateursActivation(boolean desactiver);

}
