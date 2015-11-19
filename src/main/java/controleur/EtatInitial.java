package controleur;

import java.io.File;
import modele.persistence.DeserialiseurXML;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Maxou
 */
class EtatInitial extends AbstractEtat
{

    public EtatInitial()
    {
    }

    @Override
    public void cliqueSurListItem(int livraisonId)
    {
        throw new RuntimeException("Dans cet etat il n y a pas une liste qui on pourrait cliquer.");
    }

    @Override
    public PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        //TODO implement end call command here
        return DeserialiseurXML.ouvrirPlanDeVille(plan);
    }

    @Override
    public Model chargerLivraisons(File livraisons, PlanDeVille plan) throws JDOMException, SAXException, ParseException, IOException
    {
        throw new RuntimeException("Chargement de Livraisons pas possible quand en etat initial.");
    }

    @Override
    public void cliqueSurPlan(int x, int y)
    {
        throw new RuntimeException("Dans cet etat il n y a pas un plan qui on pourrait cliquer.");
    }

    @Override
    public void cliqueCalculerTournee(Model model)
    {
        throw new RuntimeException("On ne peut pas calculer une tournee quand on ne connait pas encore le plan de ville et les livraisons");
    }

}
