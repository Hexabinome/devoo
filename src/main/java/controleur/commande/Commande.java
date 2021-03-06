package controleur.commande;


/**
 * L'interface définissant les méthodes des commandes
 * @author maxou
 */
public interface Commande {

    /**
     * La création d'une commande ne déclenche pas automatiquement son exécution
     * (ce qui est notamment utile pour l'historique, quand on veut ré-exécuter une
     * commande)
     */
    void executer() throws CommandeException;

    /**
     * Si une commande est annulable, on peut revenir dans un ancien etat du modèle
     * en appelant cette methode et annulant la commande.
     */
    void annuler();
}
