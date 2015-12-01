package controleur.observateur;

/**
 * Interface pour les observables du modèle
 * @author Maxou
 */
public interface ModeleObservateur {
	
    /**
     * Notifie les observeurs que le modèle a changé
     */
    void notifierObservateursModele();
}
