package controleur.etat;

import java.io.File;

import controleur.commande.CommandeException;

/**
 *
 * @author Maxou
 */
public interface EtatInterface
{

    EtatInterface cliqueAnnuler();

    EtatInterface cliqueRetablir();

    EtatInterface cliqueSurLivraison(int livraisonId);

    EtatInterface chargerPlan(File plan) throws CommandeException;

    EtatInterface chargerLivraisons(File livraisons) throws CommandeException;

    EtatInterface cliqueSurPlan(int intersectionId);

    EtatInterface cliqueCalculerTournee();

}
