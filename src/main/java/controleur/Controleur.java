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
    public void ajouterActivationObserveur(ActivationObserveurInterface observer) {
        controleurDonnees.ajouterActivationObserveur(observer);
    }

    @Override
    public void ajouterModeleObserveur(ModelObserveur observer) {
        controleurDonnees.ajouterModeleObserveur(observer);
    }

    @Override
    public void ajouterPlanObserveur(PlanObserveur planObserveur) {
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
    public Exception chargerPlan(File fichierPlan) {
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
    public void clicCalculerTourne() {
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
    public void ajouterTourneeObserveur(ActivationObserveurInterface tourneeObserveur) {
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
	public void genererFeuilleDeRoute(File fichier) throws CommandeException {
		new CommandeGenererFeuilleDeRoute(controleurDonnees, fichier).executer();
	}

    @Override
    public void ajouterMessageObserveur(MessageObserveur obs) {
		controleurDonnees.ajouterMessageObserveur(obs);
	}

	@Override
	public void ajouterChargementPlanObserveur(ChargementPlanObserveurInterface chargementPlanObserveur) {
		controleurDonnees.ajouterChargementPlanObserveur(chargementPlanObserveur);
	}

	@Override
	public void ajouterActivationFonctionnalitesObserveur(ActivationFonctionnalitesObserveurInterface obs) {
		controleurDonnees.ajouterActivationFonctionnalitesObserveurs(obs);
		
	}
	
	

}
