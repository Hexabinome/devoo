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

    private final ControleurDonnees controleurDonnees;


    public EtatDemandeChargee(ControleurDonnees controleurDonnees) {
    	controleurDonnees.notifierLesAutresBoutons(false);
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId) {
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
    public EtatInterface cliqueSurPlan(int intersectionId) {
        // Ne fais rien
        return this;
    }

    @Override
    public EtatInterface cliqueCalculerTournee() {
        try {
            new CommandeCalculerTournee(controleurDonnees).executer();
        }
        catch (CommandeException ex) {
            throw new RuntimeException("Un problème est survenu lors du calcul de la tournée");
        }
        controleurDonnees.effacerHistorique();
        controleurDonnees.notifyAllCalculerTourneeObserveurs(true);
        return new EtatPrincipal(controleurDonnees);
    }

    @Override
    public EtatInterface clicDroit() {
        // Ne fais rien
    	return;
        //throw new RuntimeException("Le clic droit sur cet état n'a pas d'effet");
    }

}
