package controleur;

import java.io.File;
import modele.xmldata.ModelLecture;

/**
 *
 * @author Maxou
 */
public interface ControleurInterface
{

    void ajouterDesactObserver(DesactivationObserver observer);

    void ajouterModelObserver(ModelObserver observer);

    void cliqueSurPlan();

    boolean cliqueAnnuler();

    boolean cliqueRetablir();

    /**
     * Cette methode essaye de convertir un fichier XML dans sa representation
     * d'objets.
     *
     * @param fichierPlan comme objet File qui represente le fichier XML
     * @return String vide, si la conversion marchait sans erreurs, String avec
     * un message d'erreur sinon.
     */
    String chargerPlan(File fichierPlan);

    /**
     * Cette methode essaye de convertir un fichier XML dans sa representation
     * d'objets.
     *
     * @param fichierLivraison comme objet File qui represente le fichier XML
     * @return String vide, si la conversion marchait sans erreurs, String avec
     * un message d'erreur sinon.
     */
    String chargerLivraisons(File fichierLivraisons);

    void cliqueOutilAjouter();

    void cliqueOutilSupprimer();

    void cliqueOutilEchanger();
    
    ModelLecture getModel();

    void cliqueCalculerTourne();
}
