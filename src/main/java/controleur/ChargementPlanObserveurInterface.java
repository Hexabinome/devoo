package controleur;

/**
 * Interface des observeurs du chargement du plan
 * @author David
 */
public interface ChargementPlanObserveurInterface {
	
	/**
	 * Notifie ses observeurs que le plan a été charger
	 * @param activer Le plan a été charger ou désactivé
	 */
	void notificationActiverChargementPlan(boolean activer);
}
