package controleur.observable;

/**
 * Interface pour les observables qui envoie des messages
 * @author maex
 */
public interface MessageObservableInterface {
	
    /**
     * Notifie les observateurs des messages
     * @param message Le message envoyé
     */
    void notifierObserveursMessage(String message);
}
