package controleur;

import java.io.File;
import modele.xmldata.ModelLecture;

/**
 *
 * @author Maxou
 */
public interface ControleurInterface
{

    public void ajouterDesactObserver(DesactivationObserver observer);

    public void ajouterModelObserver(ModelObserver observer);

    public void cliqueSurPlan();

    public boolean cliqueAnnuler();

    public boolean cliqueRetablir();

    public boolean chargerPlan(File fichierPlan);

    public boolean chargerLivraisons(File fichierLivraisons);

    public void cliqueOutilAjouter();

    public void cliqueOutilSupprimer();

    public void cliqueOutilEchanger();

    public ModelLecture getModel();

    public void cliqueCalculerTourne();

}
