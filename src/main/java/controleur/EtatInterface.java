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
 * @author Maxou
 */
public interface EtatInterface
{

    boolean cliqueAnnuler();

    boolean cliqueRetablir();

    void cliqueSurListItem(int livraisonId);

    PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException;

    Model chargerLivraisons(File livraisons, PlanDeVille plan) throws JDOMException, SAXException, ParseException, IOException;

    void cliqueSurPlan(int x, int y);
    
    void cliqueCalculerTournee(Model model);
}
