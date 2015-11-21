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
public class EtatPlanCharge extends AbstractEtat
{

    private final ControleurDonnees controleurDonnees;

    public EtatPlanCharge(ControleurDonnees controleurDonnees)
    {
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public EtatInterface cliqueSurListItem(int livraisonId)
    {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec la liste.");
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        //TODO create and launch command here...
        //remplacer plan qui est charge d'un nouveau plan (ssi le chargement du xml a reussi)
        controleurDonnees.setPlan(DeserialiseurXML.ouvrirPlanDeVille(plan));
        return this;
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws JDOMException, SAXException, ParseException, IOException
    {
        PlanDeVille plan = controleurDonnees.getPlan();
        Demande demande = DeserialiseurXML.ouvrirLivraison(livraisons, plan);
        controleurDonnees.setModel(new Model(plan, demande));
        
        return new EtatPrincipal(controleurDonnees);
    }

    @Override
    public EtatInterface cliqueSurPlan(int x, int y)
    {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec le plan.");
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new RuntimeException("Cet etat ne permet pas d'calculer la tournee");
    }

}
