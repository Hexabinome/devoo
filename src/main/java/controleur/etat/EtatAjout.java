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
        donnees.notifierAllMessageObserveurs("[AJOUT] Où souhaitez-vous ajouter une livraison ? Choisissez l'adresse de livraison en cliquant sur une intersection de le plan. Clic droit pour sortir du mode d'ajout.");
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        donnees.notifierAllMessageObserveurs("[AJOUT] Veuillez d'abord choisir l'adresse de la livraison en cliquant sur une intersection sur le plan. Clic droit pour sortir du mode d'ajout?");
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
    	donnees.notifierAllMessageObserveurs("Cet etat ne permet pas de charger un plan");
        throw new RuntimeException("Cet etat ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException
    {
    	donnees.notifierAllMessageObserveurs("Cet etat ne permet pas de charger une demande de livraison");
        throw new RuntimeException("Cet etat ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        return new EtatAjout2(donnees,intersectionId);
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
    	donnees.notifierAllMessageObserveurs("Cet etat ne permet pas de calculer la tournée");
        throw new RuntimeException("Cet etat ne permet pas de calculer la tournée");
    }

    @Override
    public EtatInterface clicDroit() {
        donnees.notifierAllMessageObserveurs(TEXTE_ETAT_PRINCIPAL);
        return new EtatPrincipal(donnees);
    }

}
