package controleur.commande;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Cette classe gere l'historique des commandes pour le undo et le redo
 *
 * @author Max Schiedermeier
 */
class Historique {

    /**
     * Pile des commandes qui ont été éxécutées avec succès
     */
    Deque<Commande> commandesExecutees;

    /**
     * Pile des commandes annulées
     */
    Deque<Commande> commandesAnnulees;

    public Historique() {
        commandesExecutees = new ArrayDeque<Commande>();
        commandesAnnulees = new ArrayDeque<Commande>();
    }

    /**
     * Ajoute une commande annulable qui s'est éxécutée avec succès
     *
     * @param commande Une commande qui s'est éxécutée avec succès
     */
    public void ajouterCommande(Commande commande) {
        commandesExecutees.add(commande);
        effacerCommandesAnnullees(); // on efface les commandes qui avaient été annulées quand une ajoute une nouvelle commande
    }

    /**
     * Annule la dernière commande annulable executée (Ctrl+Z)
     */
    public void annuler() {
        Commande commande = commandesExecutees.pop();
        commandesAnnulees.push(commande);
        commande.annuler();
    }

    /**
     * Execute la dernière commande annulée s'il y'en a (Ctrl+Y)
     */
    public void executer() throws CommandeException {
        if (!commandesAnnulees.isEmpty()) {
            Commande commande = commandesAnnulees.pop();
            commande.executer();
            commandesExecutees.add(commande);
        }
    }

    /**
     * Efface toutes les commandes stockées
     */
    public void effacerToutesLesCommandes() {
        effacerCommandesAnnullees();
        effacerCommandesExecutees();
    }

    /**
     * Efface le contenu la pile des commandes qui ont été annulées
     */
    public void effacerCommandesAnnullees() {
        commandesAnnulees.clear();
    }

    /**
     * Efface le contenu de la pile des commandes qui ont été éxécutées
     */
    public void effacerCommandesExecutees() {
        commandesExecutees.clear();
    }

    /**
     * Renvoie true s'il reste des commandes à annuler
     */
    public boolean estVideCommandesAnnulees(){
        return commandesAnnulees.isEmpty();
    }

    public boolean estVideCommandesExecutees(){
        return commandesExecutees.isEmpty();
    }

}
