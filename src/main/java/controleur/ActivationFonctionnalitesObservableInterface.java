package controleur;

/**
 * Interface des observables pour notifier les boutons des fonctionnalités
 * @author David
 */
public interface ActivationFonctionnalitesObservableInterface {
    
	/**
	 * Notifie les boutons de fonctionnalités qu'il faut changer l'état d'activation
     * @param activer Vrai s'il faut activer les boutons des fonctionnalités
     */
    void notifierObserveursFonctionnalites(boolean activer);
}
