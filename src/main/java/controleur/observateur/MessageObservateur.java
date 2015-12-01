package controleur.observateur;

/**
 * Interface pour les observables qui envoie des messages
 * @author maex
 */
public interface MessageObservateur {
	
    /**
     * Notifie les observateurs des messages
     * @param message Le message envoyé
     */
    void notifierObservateursMessage(String message);
}
