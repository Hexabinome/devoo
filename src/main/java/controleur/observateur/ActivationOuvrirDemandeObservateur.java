package controleur.observateur;

/**
 * Interface pour les observables du plan 
 * @author elmhaidara 
 * @date 25/11/15
 */
public interface ActivationOuvrirDemandeObservateur {
	
    /**
     * Notifie les observeurs qu'il faut ou pas activer l'élément du menu qui permet d'ouvrir une demande
     * @param activer Vrai s'il faut s'activer ou se désactiver lors de cette modification
     */
    void notifierObservateurOuvrirDemande(boolean activer);
}
