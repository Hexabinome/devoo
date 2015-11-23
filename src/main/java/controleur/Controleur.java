package controleur;

import java.io.File;
import modele.xmldata.ModelLecture;
import modele.xmldata.PlanDeVille;

/**
 * Implements controller interface. Primary entry point for all interaction with
 * package view.
 *
 * @author Maxou
 */
public class Controleur implements ControleurInterface
{

    private EtatInterface etat;
    private final ControleurDonnees controleurDonnees;

    public Controleur()
    {
        controleurDonnees = new ControleurDonnees();
        etat = new EtatInitial(controleurDonnees);
    }

    @Override
    public void ajouterDesactObserver(MainActivationObserverInterface observer)
    {
        controleurDonnees.addDesactObserveur(observer);
    }

    @Override
    public void ajouterModelObserver(ModelObserver observer)
    {
        controleurDonnees.addModelObserveur(observer);
    }

    @Override
    public void cliqueAnnuler()
    {
        etat = etat.cliqueAnnuler();
    }

    @Override
    public void cliqueRetablir()
    {
        etat = etat.cliqueRetablir();
    }

    @Override
    public Exception chargerPlan(File fichierPlan)
    {
        //TODO: catch and return exception here
        try {
            etat = etat.chargerPlan(fichierPlan);
            return null;
        }
        catch (CommandException e) {
            return e;
        }
    }

    @Override
    public Exception chargerLivraisons(File fichierLivraisons)
    {
        try {
            etat = etat.chargerLivraisons(fichierLivraisons);
            return null;
        }
        catch (CommandException e) {
            return e;
        }
    }

    @Override
    public void cliqueOutilAjouter()
    {
        etat = new EtatAjout(controleurDonnees);
    }

    @Override
    public void cliqueOutilSupprimer()
    {
        etat = new EtatSupression(controleurDonnees);
    }

    @Override
    public void cliqueOutilEchanger()
    {
        etat = new EtatEchange(controleurDonnees);
    }

    @Override
    public ModelLecture getModel()
    {
        if (controleurDonnees.getModel() == null)
            throw new RuntimeException("Model n'existe pas, il faut charger des fichiers xml avant");
        return controleurDonnees.getModel();
    }

    @Override
    public void cliqueSurPlan(int x, int y)
    {
        etat = etat.cliqueSurPlan(x, y);
    }

    @Override
    public void cliqueCalculerTourne()
    {
        etat = etat.cliqueCalculerTournee();
    }

    @Override
    public PlanDeVille getPlanDeVille()
    {
        PlanDeVille plan = controleurDonnees.getPlan();
        if (plan == null)
            throw new RuntimeException("Plan n'existe pas, il faut charger le fichier xml avant d'appeler cette methode");
        return plan;
    }

    @Override
    public void cliqueSurLivraison(int livraisonId)
    {
        etat.cliqueSurLivraison(livraisonId);
    }

}
