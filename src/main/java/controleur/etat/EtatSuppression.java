package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeException;
import controleur.commande.Commande;
import controleur.commande.CommandeSupprimerLivraison;

/**
 * Etat de suppression
 * @author Maxou
 */
public class EtatSuppression extends AbstractEtat {

    /** Le contrôleur de données */
    private final ControleurDonnees donnees;

    /**
     * Constructeur de l'état de suppression
     * @param donnees Le contrôleur de données
     */
    public EtatSuppression(ControleurDonnees donnees) {
        this.donnees = donnees;
        donnees.notifierObservateurOuvrirPlan(false);
        donnees.notifierObservateurOuvrirDemande(false);
        donnees.notifierObservateursMessage("[SUPPRESSION] Souhaitez-vous supprimer une livraison ? Choisissez dans la liste à gauche la livraison que vous voulez supprimer. Clic droit pour sortir du mode de suppression.");
    }

    @Override
    public EtatInterface clicSurLivraison(int livraisonId) {
        try {
            Commande commande = new CommandeSupprimerLivraison(donnees,livraisonId);
            commande.executer();
            donnees.ajouterCommande(commande);
            donnees.effacerCommandesARetablir();
        } catch (CommandeException e) {
        	//e.printStackTrace();
        	// TODO afficher ou erreur?
        	donnees.notifierObservateursMessage(e.getMessage());
        }
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException {
        throw new UnsupportedOperationException("Cet état ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException {
        throw new UnsupportedOperationException("Cet état ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface clicSurPlan(int intersectionId) {
        donnees.notifierObservateursMessage("[SUPPRESSION] Veuillez choisir une livraison dans la liste à gauche. Clic droit pour sortir du mode de suppression");
        return this;
    }

    @Override
    public EtatInterface clicCalculerTournee() {
        // Ne fait rien
        return this;
    }

    @Override
    public EtatInterface clicDroit() {
        donnees.notifierObservateursMessage(TEXTE_ETAT_PRINCIPAL);
        return new EtatPrincipal(donnees);
    }

    @Override
    public EtatInterface clicAnnuler() {
        throw new UnsupportedOperationException("Cet état ne permet pas d'annuler");
    }

    @Override
    public EtatInterface clicRetablir() {
        throw new UnsupportedOperationException("Cet état ne permet pas de rétablir.");
    }

}
