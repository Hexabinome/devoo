package controleur;

import java.io.File;

/**
 *
 * @author Maxou
 */
public class EtatPlanCharge extends AbstractEtat
{

    private final ControleurDonnees controleurDonnees;

    public EtatPlanCharge(ControleurDonnees controleurDonnees)
    {
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec la liste.");
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandException
    {
        new CommandChargerPlan(controleurDonnees, plan).executer();
        return this;
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandException
    {
        new CommandChargerLivraisons(controleurDonnees, livraisons).executer();
        return new EtatPrincipal(controleurDonnees);
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec le plan.");
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new RuntimeException("Cet etat ne permet pas d'calculer la tournee");
    }

}
