package controleur.observable;

/**
 * Interface pour les observables du plan 
 * @author elmhaidara 
 * @date 25/11/15
 */
public interface ActivationOuvrirDemandeObserveur {
	
    /**
     * Notifie les observeurs de l'élement du menu qui permet d'ouvrir une livraison.
     * @param activer Vrai s'il faut s'activer ou se désactiver lors de cette modification
     */
    void notifierObserveurOuvrirDemande(boolean activer);
}
