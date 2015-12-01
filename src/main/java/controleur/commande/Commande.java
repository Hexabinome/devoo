package controleur.commande;


/**
 * L'interface définissant les méthodes des commandes
 * @author maxou
 */
public interface Commande {

    /**
     * Il y a des commandes qu'on ne peut pas annuler, par exemple charger un fichier
     * xml ou calculer la tournée.
     *
     * @return Vrai si annulable
     */
    boolean estAnnulable();

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
