package controleur.observable;

/**
 * Interface pour les observables du modèle
 * @author Maxou
 */
public interface ModeleObservableInterface {
	
    /**
     * Notifie les observeurs que le modèle a changé
     */
    void notifierObserveursModele();
}
