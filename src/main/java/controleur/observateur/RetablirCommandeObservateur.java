package controleur.observateur;

/**
 * Interface des observables pour le rétali
 * Joue le role d'observeur quand l'action retablir doit etre activée
 * @author Maxou
 */
public interface RetablirCommandeObservateur {

    /**
     * Notifie les observeurs du rétablissement d'une commande
     * @param active Vrai s'il faut activer suite au rétablissement de la commande
     */
    void notifierObservateurRetablirCommande(boolean active);
}