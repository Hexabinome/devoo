package controleur;

import java.util.Collection;
import java.util.LinkedList;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;

/**
 * Cette classe contient les donnees necessaires pour la gestion des etats. On
 * pourrait dire qu'elle represente seulement des donnees et devrait du coup
 * mieux etre situe dans le package model. Par contre cette classe est lie a une
 * seul IHM, du coup il y a un bon raison pour la laisser ici dans le
 * controleur.
 *
 * @author Maxou
 */
public class ControleurDonnees
{
    // represente les fichiers XMLs charge. Peut aussi calculer et stoquer une solution
    private Model model;

    //collection des observeurs (pour gui avec functionalite reduit si plan et livraisons n'sons pas encore charge)
    private final Collection<DesactivationObserver> desactObserveurs;

    //collection des observeurs pour le model
    private final Collection<ModelObserver> modelObserveurs;

    // collection des observers pour la passibilite d'annuler / retablis des interactions effectuees.
    private final Collection<AnnulerObserveur> annulerObserveurs;

    // collection des observers pour la passibilite d'annuler / retablis des interactions effectuees.
    private final Collection<RetablirObserveur> retablirObserveurs;

    private PlanDeVille plan = null;

    private Historique hist;

    public ControleurDonnees()
    {
        desactObserveurs = new LinkedList<>();
        modelObserveurs = new LinkedList<>();
        annulerObserveurs = new LinkedList<>();
        retablirObserveurs = new LinkedList<>();
    }

    public Model getModel()
    {
        return model;
    }

    public void setModel(Model model)
    {
        this.model = model;
    }

    public PlanDeVille getPlan()
    {
        return plan;
    }

    public void setPlan(PlanDeVille plan)
    {
        this.plan = plan;
    }

    public Historique getHist()
    {
        return hist;
    }

    public void setHist(Historique hist)
    {
        this.hist = hist;
    }

    public void addDesactObserveur(DesactivationObserver obs)
    {
        desactObserveurs.add(obs);
    }

    public void addModelObserveur(ModelObserver obs)
    {
        modelObserveurs.add(obs);
    }

    public void addAnnulerObserveur(AnnulerObserveur obs)
    {
        annulerObserveurs.add(obs);
    }

    public void addRetablirObserveur(RetablirObserveur obs)
    {
        retablirObserveurs.add(obs);
    }

    public void notifyAllDesactObserveurs(boolean state)
    {
        desactObserveurs.stream().forEach((obs) -> {
            obs.notifyObserver(state);
        });
    }

    public void notifyAllModelObserveurs()
    {
        modelObserveurs.stream().forEach((obs) -> {
            obs.notifyObserver();
        });
    }

    public void notifyAllAnnulerObserveurs()
    {
        annulerObserveurs.stream().forEach((obs) -> {
            obs.notifyObserver();
        });
    }

    public void notifyAllRetablirObserveurs()
    {
        retablirObserveurs.stream().forEach((obs) -> {
            obs.notifyObserver();
        });
    }

}
