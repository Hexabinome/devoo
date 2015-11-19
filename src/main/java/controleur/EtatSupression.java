package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author Max Schiedermeier
 */
public class EtatSupression extends AbstractEtat
{

    @Override
    public void cliqueSurListItem(int livraisonId)
    {
        throw new UnsupportedOperationException("Cet etat n'est pas encore implemente!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        throw new UnsupportedOperationException("Cet etat n'est pas encore implemente!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Model chargerLivraisons(File livraisons, PlanDeVille plan) throws JDOMException, SAXException, ParseException, IOException
    {
        throw new UnsupportedOperationException("Cet etat n'est pas encore implemente!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cliqueSurPlan(int x, int y)
    {
        throw new UnsupportedOperationException("Cet etat n'est pas encore implemente!"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cliqueCalculerTournee(Model model)
    {
        throw new UnsupportedOperationException("Cet etat n'est pas encore implemente!"); //To change body of generated methods, choose Tools | Templates.
    }

}
