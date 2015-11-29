package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeChargerDemande;
import controleur.commande.CommandeChargerPlan;
import controleur.commande.CommandeException;

/**
 * Cet état répresente l'état où la tournée a été calculée.
 * @author Maxou
 */
public class EtatPrincipal implements EtatInterface
{

    private final ControleurDonnees controleurDonnees;

    public EtatPrincipal(ControleurDonnees controleurDonnees)
    {
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public EtatInterface cliqueAnnuler()
    {
        throw new UnsupportedOperationException("Undo/Redo is allowed in this state, but not supported yet, since not a core feature."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueRetablir()
    {
        throw new UnsupportedOperationException("Undo/Redo is allowed in this state, but not supported yet, since not a core feature."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        //cet interation sera sans effect.
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
        new CommandeChargerPlan(controleurDonnees, plan).executer();
        return new EtatPlanCharge(controleurDonnees);
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException
    {
        new CommandeChargerDemande(controleurDonnees, livraisons).executer();
        controleurDonnees.effacerHistorique();
        return new EtatDemandeChargee(controleurDonnees);
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        throw new UnsupportedOperationException("Interaction with plan is allowed in this state, but not supported yet, since not a core feature."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        return this;
    }

    @Override
    public EtatInterface clicDroit() {

        return this;
    }

}
