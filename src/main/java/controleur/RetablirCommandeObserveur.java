package controleur;

/**
 * Joue le role d'observeur quand l'action retablir doit etre activée
 * @author Maxou
 */
public interface RetablirCommandeObserveur {

    /**
     * @param active Dit si l'action retablir doit etre activée ou non
     */
    void notificationRetablirCommande(boolean active);
}
