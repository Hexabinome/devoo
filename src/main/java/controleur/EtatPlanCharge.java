package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import modele.persistence.DeserialiseurXML;
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

    @Override
    public void cliqueSurPlan(int x, int y)
    {
        throw new RuntimeException("");
    }

    @Override
    public void cliqueSurListItem(int livraisonId)
    {
        throw new RuntimeException("");
    }

    /**
     * Replace loaded plan by another plan
     *
     * @param plan
     * @return
     * @throws JDOMException
     * @throws SAXException
     * @throws IOException
     */
    @Override
    public PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        //TODO: implement command and call command
        return DeserialiseurXML.ouvrirPlanDeVille(plan);
    }

    @Override
    public Model chargerLivraisons(File livraisons, PlanDeVille plan) throws JDOMException, SAXException, ParseException, IOException
    {
        //TODO: implement command and call command
        Demande demande = DeserialiseurXML.ouvrirLivraison(livraisons, plan);
        return new Model(plan, DeserialiseurXML.ouvrirLivraison(livraisons, plan));
    }

    @Override
    public void cliqueCalculerTournee(Model model)
    {
        throw new RuntimeException("On ne peut pas calculer une tournee quand on ne connait pas encore les livraisons");
    }

}
