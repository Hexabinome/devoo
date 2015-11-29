package controleur;

import java.io.File;
import javafx.scene.text.Text;
import modele.xmldata.ModeleLecture;
import modele.xmldata.PlanDeVille;

/**
 *
 * @author Maxou
 */
public interface ControleurInterface
{

    void ajouterDesactObserver(ActivationObserverInterface observer);

    void ajouterModelObserver(ModelObserveur observer);

    void ajouterPlanObserveur(PlanObserveur planObserveur);
    
    void ajouterTourneeObserveur(ActivationObserverInterface tourneeObserveur);

    void ajouterAnnulerCommandeObserveur(AnnulerCommandeObserveur annulerCommandeObserveur);

    void ajouterRetablirCommandeObserveur(RetablirCommandeObserveur retablirCommandeObserveur);

    void cliqueSurPlan(int intersectionId);
    
    void cliqueSurLivraison(int livraisonId);

    void cliqueAnnuler();

    void cliqueRetablir();

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

    void cliqueOutilAjouter();

    void cliqueOutilSupprimer();

    void cliqueOutilEchanger();
    
    ModeleLecture getModel();
    
    PlanDeVille getPlanDeVille();

    void cliqueCalculerTourne();
    
    String genererFeuilleDeRoute();

    public void ajouterMessageObserveur(MessageObserveur obs);
}
