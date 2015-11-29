package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeException;
import controleur.commande.CommandeChargerLivraisons;
import controleur.commande.CommandeChargerPlan;

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
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
        new CommandeChargerPlan(controleurDonnees, plan).executer();
        return this;
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException
    {
        new CommandeChargerLivraisons(controleurDonnees, livraisons).executer();
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
