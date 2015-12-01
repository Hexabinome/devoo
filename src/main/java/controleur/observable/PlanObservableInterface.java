package controleur.observable;

/**
 * Interface pour les observables du plan 
 * @author elmhaidara 
 * @date 25/11/15
 */
public interface PlanObservableInterface {
	
    /**
     * Notifie les observeurs du plan
     * @param activer Vrai s'il faut s'activer ou se désactiver lors de cette modification
     */
    void notifierObserveursPlan(boolean activer);
}
