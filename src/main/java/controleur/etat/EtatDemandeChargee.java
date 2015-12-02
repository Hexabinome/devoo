package controleur.etat;

import controleur.ControleurDonnees;
import controleur.commande.CommandeCalculerTournee;
import controleur.commande.CommandeChargerDemande;
import controleur.commande.CommandeChargerPlan;
import controleur.commande.CommandeException;

import java.io.File;

/**
 * On se retrouve dans cet état après avoir chargé la demande de livraisons mais sans avoir calculé la tournée encore.
 *
 */
public class EtatDemandeChargee extends AbstractEtat {

    /** Le contrôleur de données */
    private final ControleurDonnees controleurDonnees;

    /**
     * Constructeur de l'état demande chargée
     * @param controleurDonnees Le contrôleur de données
     */
    public EtatDemandeChargee(ControleurDonnees controleurDonnees) {
        // On désactive les fonctionnalités principales
    	controleurDonnees.notifierObservateursFonctionnalites(false);
        this.controleurDonnees = controleurDonnees;
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
        return this;
    }

    @Override
    public EtatInterface clicSurPlan(int intersectionId) {
        // Ne fait rien
        return this;
    }

    @Override
    public EtatInterface clicCalculerTournee() {
        try {
            new CommandeCalculerTournee(controleurDonnees).executer();
        } catch (CommandeException ex) {
            throw new RuntimeException("Un problème est survenu lors du calcul de la tournée");
        }
        controleurDonnees.effacerHistorique();
        controleurDonnees.notifierObservateursCalculTournee(true);
        return new EtatPrincipal(controleurDonnees);
    }

    @Override
    public EtatInterface clicDroit() {
        // Ne fait rien
    	return this;
    }
    
}
