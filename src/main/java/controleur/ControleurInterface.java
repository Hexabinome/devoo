package controleur;

import java.io.File;

import controleur.commande.CommandeException;
import modele.xmldata.ModeleLecture;
import modele.xmldata.PlanDeVille;

/**
 *
 * @author Maxou
 */
public interface ControleurInterface
{

    void ajouterActivationObserveur(ActivationObserveurInterface observer);

    void ajouterModeleObserveur(ModelObserveur observer);

    void ajouterPlanObserveur(PlanObserveur planObserveur);
    
    void ajouterChargementPlanObserveur(ChargementPlanObserveurInterface chargementPlanObserveur);
    
    void ajouterTourneeObserveur(ActivationObserveurInterface tourneeObserveur);

    void ajouterAnnulerCommandeObserveur(AnnulerCommandeObserveur annulerCommandeObserveur);

    void ajouterRetablirCommandeObserveur(RetablirCommandeObserveur retablirCommandeObserveur);
    
    void ajouterActivationFonctionnalitesObserveur(ActivationFonctionnalitesObserveurInterface obs);

    void clicSurPlan(int intersectionId);
    
    void clicSurLivraison(int livraisonId);

    void clicAnnuler();

    void clicRetablir();

    /**
     * Cette methode essaye de convertir un fichier XML dans sa representation
     * d'objets.
     *
     * @param fichierPlan comme objet File qui represente le fichier XML
     * @return String vide, si la conversion marchait sans erreurs, String avec
     * un message d'erreur sinon.
     */
    Exception chargerPlan(File fichierPlan);

    /**
     * Cette methode essaye de convertir un fichier XML dans sa representation
     * d'objets.
     *
     * @param fichierLivraisons comme objet File qui represente le fichier XML
     * @return String vide, si la conversion marchait sans erreurs, String avec
     * un message d'erreur sinon.
     */
    Exception chargerLivraisons(File fichierLivraisons);

    void clicOutilAjouter();

    void clicOutilSupprimer();

    void clicOutilEchanger();

    void clicDroit();
    
    ModeleLecture getModele();
    
    PlanDeVille getPlanDeVille();

    void clicCalculerTourne();
    
    public void ajouterMessageObserveur(MessageObserveur obs);

	void genererFeuilleDeRoute(File fichier) throws CommandeException;
}
