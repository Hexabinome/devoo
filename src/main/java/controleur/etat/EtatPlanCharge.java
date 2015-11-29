package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeException;
import controleur.commande.CommandeChargerDemande;
import controleur.commande.CommandeChargerPlan;

/**
 * On se retrouve dans cet état après avoir le chargé le plan.
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
        controleurDonnees.effacerHistorique();
        return this;
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
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec le plan.");
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new RuntimeException("Cet etat ne permet pas de calculer la tournee");
    }

    @Override
    public EtatInterface clicDroit() {
        // Ne fais rien
        return this;
    }

}
