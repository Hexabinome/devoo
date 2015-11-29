package controleur;

import java.util.Collection;
import java.util.LinkedList;

import controleur.commande.Commande;
import controleur.commande.Historique;
import modele.xmldata.Modele;
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
    private Modele modele;

    //collection des observeurs (pour gui avec functionalite reduit si plan et livraisons n'sons pas encore charge)
    private final Collection<ActivationObserverInterface> desactObserveurs;

    //collection des observeurs pour le model
    private final Collection<ModelObserveur> modelObserveurs;

    // collection des observers pour la passibilite d'annuler / retablis des interactions effectuees.
    private final Collection<AnnulerCommandeObserveur> annulerCommandeObserveurs;

    // collection des observers pour la passibilite d'annuler / retablis des interactions effectuees.
    private final Collection<RetablirCommandeObserveur> retablirCommandeObserveurs;

    private final Collection<PlanObserveur> planObserveurs;

    private final Collection<ActivationObserverInterface> tourneeObserveurs;

    private PlanDeVille plan = null;

    private Historique hist;

    public ControleurDonnees()
    {
        desactObserveurs = new LinkedList<>();
        modelObserveurs = new LinkedList<>();
        annulerCommandeObserveurs = new LinkedList<>();
        retablirCommandeObserveurs = new LinkedList<>();
        planObserveurs = new LinkedList<>();
        tourneeObserveurs = new LinkedList<>();
        hist = new Historique();
    }

    public Modele getModele()
    {
        return modele;
    }

    public void setModele(Modele modele)
    {
        this.modele = modele;
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

    public void addDesactObserveur(ActivationObserverInterface obs)
    {
        desactObserveurs.add(obs);
    }

    public void addModelObserveur(ModelObserveur obs)
    {
        modelObserveurs.add(obs);
    }

    public void ajouterAnnulerCommandeObserveur(AnnulerCommandeObserveur obs)
    {
        annulerCommandeObserveurs.add(obs);
    }

    public void ajouterRetablirCommandeObserveur(RetablirCommandeObserveur obs)
    {
        retablirCommandeObserveurs.add(obs);
    }

    public void ajouterPlanObserveur(PlanObserveur planObserveur)
    {
        planObserveurs.add(planObserveur);
    }

    void addTourneeObserveur(ActivationObserverInterface tourneeObserveur)
    {
        tourneeObserveurs.add(tourneeObserveur);
    }

    public void ajouterCommande(Commande commande){
        hist.ajouterCommande(commande);
    }

    public void notifierLesObserveursDuPlan()
    {
        planObserveurs.stream().forEach((planObserveur -> {
            planObserveur.notificationPlanAChange();
        }));
    }

    public void notifyAllActObserveurs(boolean state)
    {
        desactObserveurs.stream().forEach((obs) -> {
            obs.notifierLesObserveurs(state);
        });
    }

    public void notifyAllModelObserveurs()
    {
        modelObserveurs.stream().forEach((obs) -> {
            obs.notificationModelAChange();
        });
    }

    public void notifyAllAnnulerObserveurs(boolean activation)
    {
        annulerCommandeObserveurs.stream().forEach((obs) -> {
            obs.notificationAnnulerCommande(activation);
        });
    }

    public void notifyAllRetablirObserveurs(boolean activation)
    {
        retablirCommandeObserveurs.stream().forEach((obs) -> {
            obs.notificationRetablirCommande(activation);
        });
    }

    public void notifyAllCalculerTourneeObserveurs(boolean activation)
    {
        tourneeObserveurs.stream().forEach((obs) -> {
            obs.notifierLesObserveurs(!activation);
        });
    }

}
