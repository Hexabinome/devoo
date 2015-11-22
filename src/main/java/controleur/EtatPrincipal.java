package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import modele.persistance.DeserialiseurXML;
import modele.xmldata.Demande;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author Maxou
 */
class EtatPrincipal implements EtatInterface
{

    private final ControleurDonnees controleurDonnees;

    public EtatPrincipal(ControleurDonnees controleurDonnees)
    {
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public EtatInterface cliqueAnnuler()
    {
        throw new UnsupportedOperationException("Undo/Redo is allowed in this state, but not supported yet, since not a core feature."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueRetablir()
    {
        throw new UnsupportedOperationException("Undo/Redo is allowed in this state, but not supported yet, since not a core feature."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        //cet interation sera sans effect.
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        //remplacer plan qui est charge d'un nouveau plan (ssi le chargement du xml a reussi)
        controleurDonnees.setPlan(DeserialiseurXML.ouvrirPlanDeVille(plan));
        controleurDonnees.notifyAllActObserveurs(true);
        return new EtatPlanCharge(controleurDonnees);
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws JDOMException, SAXException, ParseException, IOException
    {
        //TODO use command instead if direct implementation here
        PlanDeVille plan = controleurDonnees.getPlan();
        Demande demande = DeserialiseurXML.ouvrirLivraison(livraisons, plan);
        controleurDonnees.setModel(new Model(plan, demande));
        return this;
    }

    @Override
    public EtatInterface cliqueSurPlan(int x, int y)
    {
        throw new UnsupportedOperationException("Interaction with plan is allowed in this state, but not supported yet, since not a core feature."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        controleurDonnees.getModel().calculerTournee();
        return this;
    }

}
