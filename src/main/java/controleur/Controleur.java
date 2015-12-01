package controleur;

import java.io.File;

import modele.xmldata.ModeleLecture;
import modele.xmldata.PlanDeVille;
import controleur.commande.CommandeException;
import controleur.commande.CommandeGenererFeuilleDeRoute;
import controleur.etat.EtatAjout;
import controleur.etat.EtatEchange;
import controleur.etat.EtatInitial;
import controleur.etat.EtatInterface;
import controleur.etat.EtatSuppression;
import controleur.observable.ActivationFonctionnalitesObservableInterface;
import controleur.observable.ActivationObservableInterface;
import controleur.observable.AnnulerCommandeObservableInterface;
import controleur.observable.ChargementPlanObservableInterface;
import controleur.observable.MessageObservableInterface;
import controleur.observable.ModeleObservableInterface;
import controleur.observable.ActivationOuvrirDemandeObserveur;
import controleur.observable.RetablirCommandeObservableInterface;

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
    public void ajouterActivationObserveur(ActivationObservableInterface observer) {
        controleurDonnees.ajouterActivationObserveur(observer);
    }

    @Override
    public void ajouterModeleObserveur(ModeleObservableInterface observer) {
        controleurDonnees.ajouterModeleObserveur(observer);
    }

    @Override
    public void ajouterPlanObserveur(ActivationOuvrirDemandeObserveur planObserveur) {
        controleurDonnees.ajouterPlanObserveur(planObserveur);
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
        etat = etat.cliqueSurPlan(intersectionId);
    }

    @Override
    public void clicCalculTournee() {
        etat = etat.cliqueCalculerTournee();
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
       etat = etat.cliqueSurLivraison(livraisonId);
    }

    @Override
    public void ajouterTourneeObserveur(ActivationObservableInterface tourneeObserveur) {
        controleurDonnees.ajouterTourneeObserveur(tourneeObserveur);
    }

    @Override
    public void ajouterAnnulerCommandeObserveur(AnnulerCommandeObservableInterface annulerCommandeObserveur) {
        controleurDonnees.ajouterAnnulerCommandeObserveur(annulerCommandeObserveur);
    }

    @Override
    public void ajouterRetablirCommandeObserveur(RetablirCommandeObservableInterface retablirCommandeObserveur) {
        controleurDonnees.ajouterRetablirCommandeObserveur(retablirCommandeObserveur);
    }

	@Override
	public void genererFeuilleDeRoute(File fichier) throws CommandeException {
		new CommandeGenererFeuilleDeRoute(controleurDonnees, fichier).executer();
	}

    @Override
    public void ajouterMessageObserveur(MessageObservableInterface obs) {
		controleurDonnees.ajouterMessageObserveur(obs);
	}

	@Override
	public void ajouterChargementPlanObserveur(ChargementPlanObservableInterface chargementPlanObserveur) {
		controleurDonnees.ajouterChargementPlanObserveur(chargementPlanObserveur);
	}

	@Override
	public void ajouterActivationFonctionnalitesObserveur(ActivationFonctionnalitesObservableInterface obs) {
		controleurDonnees.ajouterActivationFonctionnalitesObserveurs(obs);
		
	}
}
