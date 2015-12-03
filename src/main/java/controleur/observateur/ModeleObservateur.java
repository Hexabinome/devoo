package controleur.observateur;

/**
 * Joue le rôle d'observateur quand il y'a eu un changement dans le modèle quand on est dans
 * l'état principal
 * @author Maxou
 */
public interface ModeleObservateur {
	
    /**
     * Notifie les observeurs que le modèle a changé
     */
    void notifierObservateursModele();
}
