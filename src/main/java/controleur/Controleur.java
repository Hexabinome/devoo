package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import modele.xmldata.Model;
import modele.xmldata.ModelLecture;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 * Implements controller interface. Primary entry point for all interaction with
 * package view.
 *
 * @author maxou
 */
public class Controleur implements ControleurInterface
{

    // on va deleger la plupart des appels decelnche par une interaction IHM utilisateur par l'etat currant
    private EtatInterface currentEtat;

    // represente les fichiers XMLs charge. Peut aussi calculer et stoquer une solution
    private Model model;

    //some gui elements can be disables in certain situations, 
    private final Collection<DesactivationObserver> desactObserverList;

    //some gui elements need to be notified when the model changes
    private final Collection<ModelObserver> modelObserverList;

    private PlanDeVille plan;

    public Controleur()
    {
        desactObserverList = new LinkedList<>();
        modelObserverList = new LinkedList<>();

        currentEtat = new EtatInitial();
    }

    @Override
    public void ajouterDesactObserver(DesactivationObserver observer)
    {
        desactObserverList.add(observer);
    }

    @Override
    public void ajouterModelObserver(ModelObserver observer)
    {
        modelObserverList.add(observer);
    }

    @Override
    public boolean cliqueAnnuler()
    {
        return currentEtat.cliqueAnnuler();
    }

    @Override
    public boolean cliqueRetablir()
    {
        return currentEtat.cliqueRetablir();
    }

    @Override
    public String chargerPlan(File fichierPlan)
    {
        try {
            plan = currentEtat.chargerPlan(fichierPlan);

            //currentEtat = new Etat
            
            
            
            
            return "";
        }
        catch (JDOMException | SAXException | IOException ex) {
            return ex.getMessage();
        }
    }

    @Override
    public String chargerLivraisons(File fichierLivraisons)
    {
        try {
            model = currentEtat.chargerLivraisons(fichierLivraisons, plan);
            return "";
        }
        catch (JDOMException | SAXException | ParseException | IOException ex) {
            return ex.getMessage();
        }
    }

    @Override
    public void cliqueOutilAjouter()
    {
        //replace currentEtat
    }

    @Override
    public void cliqueOutilSupprimer()
    {
        //replace currentEtat
    }

    @Override
    public void cliqueOutilEchanger()
    {
        //replace currentEtat
    }

    @Override
    public ModelLecture getModel()
    {
        if (model == null)
            throw new RuntimeException("Model n'existe pas, il faut charger des fichiers xml avant");
        return model;
    }

    @Override
    public void cliqueCalculerTourne()
    {
        //call to current etat
    }

    @Override
    public void cliqueSurPlan()
    {
// forward to current etat
    }

}
