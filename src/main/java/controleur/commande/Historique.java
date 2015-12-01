package controleur.commande;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Cette classe gère l'historique des commandes pour le undo et le redo.
 * Elle stocke deux piles de commandes : une pile pour les commandes à annuler et une autre pile pour les commandes
 * à retablir c'est-à-dire celles qui ont été annulées par l'utilisateur.
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public class Historique {

    /**
     * Pile des commandes qui ont été éxécutées avec succès c'est à dire les commandes à annuler
     */
    private Deque<Commande> commandesAAnnuller;

    /**
     * Pile des commandes à retablir
     */
    private Deque<Commande> commandesARetablir;

    /**
     * Constructeur de l'historique
     */
    public Historique() {
        commandesAAnnuller = new ArrayDeque<>();
        commandesARetablir = new ArrayDeque<>();
    }

    /**
     * Ajoute une commande annulable qui s'est exécutée avec succès
     * @param commande Une commande qui s'est exécutée avec succès
     */
    public void ajouterCommande(Commande commande) {
        commandesAAnnuller.push(commande);
    }

    /**
     * Annule la dernière commande annulable executée s'il y'en a (Ctrl+Z)
     */
    public void annuler() {
        if(!commandesAAnnuller.isEmpty()) {
            Commande commandeAAnnuler = commandesAAnnuller.pop();
            commandesARetablir.push(commandeAAnnuler);
            commandeAAnnuler.annuler();
        }
    }

    /**
     * Execute la dernière commande annulée s'il y'en a (Ctrl+Y)
     */
    public void executer() throws CommandeException {
        if (!commandesARetablir.isEmpty()) {
            Commande commande = commandesARetablir.pop();
            commande.executer();
            commandesAAnnuller.push(commande);
        }
    }

    /**
     * Efface toutes les commandes stockées dans l'historique
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
     * Renvoie vrai s'il reste des commandes à rétablir, faux sinon
     */
    public boolean estVideCommandesARetablir(){
        return commandesARetablir.isEmpty();
    }

    /**
     * Renvoie vrai s'il reste des commandes à annuler, faux sinon
     */
    public boolean estVideCommandesAAnnuler(){
        return commandesAAnnuller.isEmpty();
    }
}
