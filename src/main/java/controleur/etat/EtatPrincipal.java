package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeChargerDemande;
import controleur.commande.CommandeChargerPlan;
import controleur.commande.CommandeException;

/**
 * Etat principal de l'application après avoir calculé la tournée. Les fonctionnalités sont débloqués
 * @author Maxou
 */
public class EtatPrincipal implements EtatInterface {

    /** Le contrôleur de données */
    private final ControleurDonnees controleurDonnees;

    /**
     * Constructeur de l'état principal
     * @param controleurDonnees Le contrôleur de données
     */
    public EtatPrincipal(ControleurDonnees controleurDonnees) {
        this.controleurDonnees = controleurDonnees;
        controleurDonnees.notifierObservateurOuvrirDemande(true);
        controleurDonnees.notifierObservateurOuvrirPlan(true);
    }

    @Override
    public EtatInterface clicAnnuler() {
        throw new UnsupportedOperationException("Cet état ne permet pas d'annuler des commandes");
    }

    @Override
    public EtatInterface clicRetablir() {
        throw new UnsupportedOperationException("Cet état ne permet pas de rétablier des commandes");
    }

    @Override
    public EtatInterface clicSurLivraison(int livraisonId) {
        // Ne fait rien
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException {
        new CommandeChargerPlan(controleurDonnees, plan).executer();
        return new EtatPlanCharge(controleurDonnees);
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException {
        new CommandeChargerDemande(controleurDonnees, livraisons).executer();
        controleurDonnees.effacerHistorique();
        return new EtatDemandeChargee(controleurDonnees);
    }

    @Override
    public EtatInterface clicSurPlan(int intersectionId) {
        throw new UnsupportedOperationException("Cet état ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface clicCalculerTournee() {
    	// Ne fait rien
        return this;
    }

    @Override
    public EtatInterface clicDroit() {
    	// Ne fait rien
        return this;
    }

}
