package controleur;

import java.io.File;

import controleur.observateur.*;
import modele.donneesxml.ModeleLecture;
import modele.donneesxml.PlanDeVille;
import controleur.commande.CommandeException;
import controleur.commande.CommandeGenererFeuilleDeRoute;
import controleur.etat.EtatAjout;
import controleur.etat.EtatEchange;
import controleur.etat.EtatInitial;
import controleur.etat.EtatInterface;
import controleur.etat.EtatSuppression;

/**
 * Implémente l'interface contrôleur. Point d'entrée principal pour toutes les intéractions avec le package vue.
 * @author Maxou
 */
public class Controleur implements ControleurInterface {

    /**
     * Etat actuel de l'application
     */
    private EtatInterface etat;
    
    /**
     * Le contrôleur qui intéragit avec le package modèle
     */
    private final ControleurDonnees controleurDonnees;

    /**
     * Constructeur public du contrôleur 
     */
    public Controleur() {
        controleurDonnees = new ControleurDonnees();
        etat = new EtatInitial(controleurDonnees);
    }

    @Override
    public void ajouterActivationFonctionnalitesObservateur(ActivationFonctionnalitesObservateur observer) {
        controleurDonnees.ajouterActivationObservateur(observer);
    }

    @Override
    public void ajouterModeleObservateur(ModeleObservateur observer) {
        controleurDonnees.ajouterModeleObservateur(observer);
    }

    @Override
    public void ajouterActivationOuvrirDemandeObservateur(ActivationOuvrirDemandeObservateur planObserveur) {
        controleurDonnees.ajouterPlanObservateur(planObserveur);
    }

    @Override
    public void clicAnnuler() {
        controleurDonnees.getHist().annuler();
    }

    @Override
    public void clicRetablir() {
        try {
            controleurDonnees.getHist().executer();
        } catch (CommandeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void chargerPlan(File fichierPlan) throws Exception {
        etat = etat.chargerPlan(fichierPlan);
    }

    @Override
    public void chargerLivraisons(File fichierLivraisons) throws Exception {
        etat = etat.chargerLivraisons(fichierLivraisons);
    }

    @Override
    public void clicOutilAjouter() {
        etat = new EtatAjout(controleurDonnees);
    }

    @Override
    public void clicOutilSupprimer() {
        etat = new EtatSuppression(controleurDonnees);
    }

    @Override
    public void clicOutilEchanger() {
        etat = new EtatEchange(controleurDonnees);
    }

    @Override
    public void clicDroit() {
        etat = etat.clicDroit();
    }

    @Override
    public ModeleLecture getModele() {
        if (controleurDonnees.getModele() == null)
            throw new RuntimeException("Modèle n'existe pas, il faut charger des fichiers xml avant");
        return controleurDonnees.getModele();
    }

    @Override
    public void clicSurPlan(int intersectionId) {
        etat = etat.clicSurPlan(intersectionId);
    }

    @Override
    public void clicCalculTournee() {
        etat = etat.clicCalculerTournee();
    }

    @Override
    public PlanDeVille getPlanDeVille() {
        PlanDeVille plan = controleurDonnees.getPlan();
        if (plan == null)
            throw new RuntimeException(
                    "Plan n'existe pas, il faut charger le fichier xml avant d'appeler cette méthode");
        return plan;
    }

    @Override
    public void clicSurLivraison(int livraisonId) {
       etat = etat.clicSurLivraison(livraisonId);
    }

    @Override
    public void ajouterTourneeObservateur(ActivationFonctionnalitesObservateur tourneeObserveur) {
        controleurDonnees.ajouterTourneeObservateur(tourneeObserveur);
    }

    @Override
    public void ajouterAnnulerCommandeObservateur(AnnulerCommandeObservateur annulerCommandeObserveur) {
        controleurDonnees.ajouterAnnulerCommandeObservateur(annulerCommandeObserveur);
    }

    @Override
    public void ajouterRetablirCommandeObservateur(RetablirCommandeObservateur retablirCommandeObserveur) {
        controleurDonnees.ajouterRetablirCommandeObservateur(retablirCommandeObserveur);
    }

	@Override
	public void genererFeuilleDeRoute(File fichier) throws CommandeException {
		new CommandeGenererFeuilleDeRoute(controleurDonnees, fichier).executer();
	}

    @Override
    public void ajouterMessageObservateur(MessageObservateur obs) {
		controleurDonnees.ajouterMessageObservateur(obs);
	}

    @Override
    public void ajouterPlanChargeObserveur(PlanChargeObservateur planChargeObservateur) {
        controleurDonnees.ajouterPlanChargeObservateur(planChargeObservateur);
    }

    @Override
	public void ajouterActivationOuvrirPlanObservateur(ActivationOuvrirPlanObservateur chargementPlanObserveur) {
		controleurDonnees.ajouterChargementPlanObservateur(chargementPlanObserveur);
	}

}
