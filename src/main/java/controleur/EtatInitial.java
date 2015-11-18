package controleur;

import modele.persistence.ChargerXML;
import modele.persistence.OuvreurDeFichierXML;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 *
 * @author Max Schiedermeier
 */
class EtatInitial extends AbstractEtat {

    public EtatInitial()
    {
    }



    @Override
    public void cliqueSurListItem(int livraisonId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlanDeVille chargerPlan(String chemin)
    {
        PlanDeVille planDeVille = null;
        try {
           planDeVille = ChargerXML.chargePlanDeVille();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return planDeVille;
    }

    @Override
    public Model chargerLivraisons(String chemin, PlanDeVille model)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cliqueSurPlan(int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
