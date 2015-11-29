package controleur.commande;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Cette classe gere l'historique des commandes pour le undo et le redo
 *
 * @author Max Schiedermeier
 */
public class Historique {

    /**
     * Pile des commandes qui ont été éxécutées avec succès c'est à dire les commandes à annuler
     */
    Deque<Commande> commandesAAnnuller;

    /**
     * Pile des commandes à retablir c'est à dire les commandes à retabir
     */
    Deque<Commande> commandesARetablir;

    public Historique() {
        commandesAAnnuller = new ArrayDeque<Commande>();
        commandesARetablir = new ArrayDeque<Commande>();
    }

    /**
     * Ajoute une commande annulable qui s'est éxécutée avec succès
     *
     * @param commande Une commande qui s'est éxécutée avec succès
     */
    public void ajouterCommande(Commande commande) {
        commandesAAnnuller.push(commande);
        effacerCommandeARetablir(); // on efface les commandes qui avaient été annulées quand une ajoute une nouvelle commande
    }

    /**
     * Annule la dernière commande annulable executée (Ctrl+Z)
     */
    public void annuler() {
        if(!commandesAAnnuller.isEmpty()){
            Commande commande = commandesAAnnuller.pop();
            commandesARetablir.push(commande);
            commande.annuler();
        }
    }

    /**
     * Execute la dernière commande annulée s'il y'en a (Ctrl+Y)
     */
    public void executer() throws CommandeException {
        if (!commandesARetablir.isEmpty()) {
            Commande commande = commandesARetablir.pop();
            commande.executer();
            commandesAAnnuller.add(commande);
        }
    }

    /**
     * Efface toutes les commandes stockées
     */
    public void effacerToutesLesCommandes() {
        effacerCommandeARetablir();
        effacerCommandesAAnnuler();
    }

    /**
     * Efface le contenu la pile des commandes qui ont été annulées
     */
    public void effacerCommandeARetablir() {
        commandesARetablir.clear();
    }

    /**
     * Efface le contenu de la pile des commandes qui ont été éxécutées
     */
    public void effacerCommandesAAnnuler() {
        commandesAAnnuller.clear();
    }

    /**
     * Renvoie true s'il reste des commandes à annuler
     */
    public boolean estVideCommandesARetablir(){
        return commandesARetablir.isEmpty();
    }

    public boolean estVideCommandesAAnnuler(){
        return commandesAAnnuller.isEmpty();
    }

}
