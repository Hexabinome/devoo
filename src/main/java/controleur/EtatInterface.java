package controleur;

import java.io.File;

/**
 *
 * @author Maxou
 */
public interface EtatInterface
{

    EtatInterface cliqueAnnuler();

    EtatInterface cliqueRetablir();

    EtatInterface cliqueSurLivraison(int livraisonId);

    EtatInterface chargerPlan(File plan) throws CommandException;

    EtatInterface chargerLivraisons(File livraisons) throws CommandException;

    EtatInterface cliqueSurPlan(int intersectionId);

    EtatInterface cliqueCalculerTournee();

}
