package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeException;
import controleur.commande.CommandeChargerPlan;

/**
 * Premier état de l'application au lancement. A partir de cet état on ne peut charger un plan.
 * @author Maxou
 */
public class EtatInitial extends AbstractEtat
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
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
        new CommandeChargerPlan(controleurDonnees, plan).executer();
        return new EtatPlanCharge(controleurDonnees);
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons)  throws CommandeException
    {
        throw new RuntimeException("Cet etat ne permet pas de charger un fichier de livraison");
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
