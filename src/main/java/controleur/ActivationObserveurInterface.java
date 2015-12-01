package controleur;

/**
 * Interface pour les observables qui peuvent être activer
 * @author maxou
 */
public interface ActivationObserveurInterface {
    /**
     * Notifie les observeurs qui attentent un message d'activation
     * @param desactiver Vrai si on doit désactiver les observeurs
     */
    void notifierLesObserveursActivation(boolean desactiver);

}
