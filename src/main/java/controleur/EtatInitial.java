package controleur;

import modele.persistence.ChargeurXML;
import modele.xmldata.Demande;
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

    public PlanDeVille chargerPlan(String chemin) throws JDOMException, SAXException, IOException
    {
            return ChargeurXML.chargePlanDeVille();
    }

    @Override
    public Model chargerLivraisons(String chemin, PlanDeVille plan)
    {
        Demande demande;
        try {
            demande = ChargeurXML.chargeDemande(plan);
            return new Model(plan, demande);
        }
        catch (JDOMException | SAXException | ParseException | IOException e) {
            throw new RuntimeException("Chargement des livraisonsa echoue");
        }
    }

    @Override
    public void cliqueSurPlan(int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
