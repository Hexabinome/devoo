package controleur.observable;

/**
 * Interface des observeurs du chargement du plan
 * @author David
 */
public interface ActivationOuvrirPlanObserveur {
	
	/**
	 * Notifie les observeurs qu'il faut ou pas activer l'élément du menu qui permet d'ouvrir un plan
	 * @param activer Le plan a été charger ou désactivé
	 */
	void notifierObserveursOuvrirPlan(boolean activer);
}
