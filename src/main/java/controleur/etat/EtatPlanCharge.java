package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeException;
import controleur.commande.CommandeChargerDemande;
import controleur.commande.CommandeChargerPlan;

/**
 * Etat après le chargement du plan
 * @author Maxou
 */
public class EtatPlanCharge extends AbstractEtat {

    /** Le contrôleur de données */
    private final ControleurDonnees controleurDonnees;

    /**
     * Constructeur de l'état après le chargement du plan
     * @param controleurDonnees Le contrôleur de données
     */
    public EtatPlanCharge(ControleurDonnees controleurDonnees) {
        this.controleurDonnees = controleurDonnees;
        //controleurDonnees.notifierObservateursFonctionnalites(false);
        controleurDonnees.notifierObservateursActivation(false);
    }

    @Override
    public EtatInterface clicSurLivraison(int livraisonId) {
        throw new RuntimeException("Cet état ne permet pas d'interagir avec la liste.");
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException {
        new CommandeChargerPlan(controleurDonnees, plan).executer();
        return this;
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException {
        new CommandeChargerDemande(controleurDonnees, livraisons).executer();
        return new EtatDemandeChargee(controleurDonnees);
    }

    @Override
    public EtatInterface clicSurPlan(int intersectionId) {
        throw new RuntimeException("Cet état ne permet pas d'interagir avec le plan.");
    }

    @Override
    public EtatInterface clicCalculerTournee() {
        throw new RuntimeException("Cet état ne permet pas de calculer la tournee");
    }

    @Override
    public EtatInterface clicDroit() {
        // Ne fait rien
        return this;
    }

}
