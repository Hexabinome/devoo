package controleur;

import java.io.File;
import modele.persistence.ChargeurXML;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Max Schiedermeier
 */
class EtatInitial extends AbstractEtat
{

    public EtatInitial()
    {
    }

    @Override
    public void cliqueSurListItem(int livraisonId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        //TODO pass file as param
        return ChargeurXML.chargePlanDeVille();
    }

    @Override
    public Model chargerLivraisons(File livraisons, PlanDeVille plan) throws JDOMException, SAXException, ParseException, IOException
    {
        //TODO pass file as param
        return new Model(plan, ChargeurXML.chargeDemande(plan));
    }

    @Override
    public void cliqueSurPlan(int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
