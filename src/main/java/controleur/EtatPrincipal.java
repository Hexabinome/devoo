package controleur;

import java.io.File;

/**
 *
 * @author Maxou
 */
class EtatPrincipal implements EtatInterface
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
    public EtatInterface chargerPlan(File plan) throws CommandException
    {
        new CommandeChargerPlan(controleurDonnees, plan).executer();
        return new EtatPlanCharge(controleurDonnees);
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandException
    {
        new CommandeChargerLivraisons(controleurDonnees, livraisons).executer();
        return this;
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        throw new UnsupportedOperationException("Interaction with plan is allowed in this state, but not supported yet, since not a core feature."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        controleurDonnees.getModele().calculerTournee();
        return this;
    }

}
