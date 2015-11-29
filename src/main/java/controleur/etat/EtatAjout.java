package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeException;

/**
 * Cet état correspond à l'état dans lequel on se trouve quand on clic sur le bouton ajouter.
 * Dans ce état on doit cliquer sur une intersection dans le plan pour passer à l'étatAjout2
 * @author Maxou
 */
public class EtatAjout extends AbstractEtat
{

    private final ControleurDonnees donnees;

    public EtatAjout(ControleurDonnees donnees)
    {
        this.donnees = donnees;
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        donnees.notifierAllMessageObserveurs("Veuillez d'abord choissir une intersection svp.");
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
        throw new RuntimeException("Cet etat ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException
    {
        throw new RuntimeException("Cet etat ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        donnees.notifierAllMessageObserveurs("Veuillez maintenant cliquer sur une livraison svp.");
        return new EtatAjout2(donnees,intersectionId);
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new RuntimeException("Cet etat ne permet pas de calculer la tournée");
    }

}
