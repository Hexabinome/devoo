package controleur;

import modele.xmldata.ModeleLecture;
import modele.xmldata.PlanDeVille;

import java.io.File;

import controleur.commande.CommandeException;
import controleur.etat.EtatAjout;
import controleur.etat.EtatEchange;
import controleur.etat.EtatInitial;
import controleur.etat.EtatInterface;
import controleur.etat.EtatSuppression;
import javafx.scene.text.Text;

/**
 * Implements controller interface. Primary entry point for all interaction with
 * package view.
 *
 * @author Maxou
 */
public class Controleur implements ControleurInterface {

    private EtatInterface etat;
    private final ControleurDonnees controleurDonnees;

    public Controleur() {
        controleurDonnees = new ControleurDonnees();
        etat = new EtatInitial(controleurDonnees);
    }

    @Override
    public void ajouterDesactObserver(ActivationObserverInterface observer) {
        controleurDonnees.addDesactObserveur(observer);
    }

    @Override
    public void ajouterModelObserver(ModelObserveur observer) {
        controleurDonnees.addModelObserveur(observer);
    }

    @Override
    public void ajouterPlanObserveur(PlanObserveur planObserveur) {
        controleurDonnees.ajouterPlanObserveur(planObserveur);
    }

    @Override
    public void cliqueAnnuler() {
        // etat = etat.cliqueAnnuler();
        controleurDonnees.getHist().annuler();
    }

    @Override
    public void cliqueRetablir() {
        //   etat = etat.cliqueRetablir();
        try {
            controleurDonnees.getHist().executer();
        } catch (CommandeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Exception chargerPlan(File fichierPlan) {
        //TODO: catch and return exception here
        try {
            etat = etat.chargerPlan(fichierPlan);
            return null;
        } catch (CommandeException e) {
            return e;
        }
    }

    @Override
    public Exception chargerLivraisons(File fichierLivraisons) {
        try {
            etat = etat.chargerLivraisons(fichierLivraisons);
            return null;
        } catch (CommandeException e) {
            return e;
        }
    }

    @Override
    public void cliqueOutilAjouter() {
        etat = new EtatAjout(controleurDonnees);
    }

    @Override
    public void cliqueOutilSupprimer() {
        etat = new EtatSuppression(controleurDonnees);
    }

    @Override
    public void cliqueOutilEchanger() {
        etat = new EtatEchange(controleurDonnees);
    }

    @Override
    public ModeleLecture getModel() {
        if (controleurDonnees.getModele() == null)
            throw new RuntimeException("Model n'existe pas, il faut charger des fichiers xml avant");
        return controleurDonnees.getModele();
    }

    @Override
    public void cliqueSurPlan(int intersectionId) {
        etat = etat.cliqueSurPlan(intersectionId);
    }

    @Override
    public void cliqueCalculerTourne() {
        etat = etat.cliqueCalculerTournee();
    }

    @Override
    public PlanDeVille getPlanDeVille() {
        PlanDeVille plan = controleurDonnees.getPlan();
        if (plan == null)
            throw new RuntimeException(
                    "Plan n'existe pas, il faut charger le fichier xml avant d'appeler cette methode");
        return plan;
    }

    @Override
    public void cliqueSurLivraison(int livraisonId) {
        etat.cliqueSurLivraison(livraisonId);
    }

    @Override
    public void ajouterTourneeObserveur(ActivationObserverInterface tourneeObserveur) {
        controleurDonnees.ajouterTourneeObserveur(tourneeObserveur);
    }

    @Override
    public void ajouterAnnulerCommandeObserveur(AnnulerCommandeObserveur annulerCommandeObserveur) {
        controleurDonnees.ajouterAnnulerCommandeObserveur(annulerCommandeObserveur);
    }

    @Override
    public void ajouterRetablirCommandeObserveur(RetablirCommandeObserveur retablirCommandeObserveur) {
        controleurDonnees.ajouterRetablirCommandeObserveur(retablirCommandeObserveur);
    }

	@Override
	public String genererFeuilleDeRoute() {
		return getModel().genererFeuilleDeRoute();
	}

    @Override
    public void ajouterMessageObserveur(MessageObserveur obs)
    {
controleurDonnees.ajouterMessageObserveur(obs);    }

}
