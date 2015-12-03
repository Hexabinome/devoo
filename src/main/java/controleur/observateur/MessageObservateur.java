package controleur.observateur;

/**
 * Interface pour les observateurs qui affichent un message dans la vue.
 * @author maex
 */
public interface MessageObservateur {
	
    /**
     * Notifie les observateurs qu'il doit afficher un nouveau message
     * @param message Le message envoyé
     */
    void notifierObservateursMessage(String message);
}
