package controleur.observable;

/**
 * Interface des observeurs du chargement du plan
 * @author David
 */
public interface ChargementPlanObservableInterface {
	
	/**
	 * Notifie ses observeurs que le plan a été charger
	 * @param activer Le plan a été charger ou désactivé
	 */
	void notifierObserveursChargementPlan(boolean activer);
}
