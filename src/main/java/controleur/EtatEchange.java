package controleur;

import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author Max Schiedermeier
 */
public class EtatEchange extends AbstractEtat
{

    @Override
    public void cliqueSurListItem(int livraisonId) {

    }

    @Override
    public PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException {
        return null;
    }

    @Override
    public Model chargerLivraisons(File livraisons, PlanDeVille plan)
            throws JDOMException, SAXException, ParseException, IOException {
        return null;
    }

    @Override
    public void cliqueSurPlan(int x, int y) {

    }

    @Override
    public void cliqueCalculerTournee(Model model) {

    }
}
