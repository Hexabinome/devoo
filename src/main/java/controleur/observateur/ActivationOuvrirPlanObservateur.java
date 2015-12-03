package controleur.observateur;

/**
 * Interface pour l'observateur de l'action qui permet d'ouvrir un plan.
 * @author Mohamed El Mouctar HAIDARA
 */
public interface ActivationOuvrirPlanObservateur {
	
	/**
	 * Notifie les observateurs qu'il faut ou pas activer l'élément du menu qui permet d'ouvrir un plan
	 * @param activer Le plan a été charger ou désactivé
	 */
	void notifierObservateursOuvrirPlan(boolean activer);
}
