package controleur;

import java.io.File;

/**
 *
 * @author Maxou
 */
class EtatInitial extends AbstractEtat
{

    private final ControleurDonnees controleurDonnees;

    public EtatInitial(ControleurDonnees controleurDonnees)
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
        return new EtatPlanCharge(controleurDonnees);
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons)  throws CommandException
    {
        throw new RuntimeException("Cet etat ne permet pas de charger un fichier de livraison");
    }

    @Override
    public EtatInterface cliqueSurPlan(int x, int y)
    {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec le plan.");
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new RuntimeException("Cet etat ne permet pas d'calculer la tournee");
    }

}
