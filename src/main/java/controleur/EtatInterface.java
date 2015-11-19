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
 * @author maxou
 */
public interface EtatInterface
{
    public void cliqueSurPlan(int x, int y);

    public void cliqueSurListItem(int livraisonId);

    public boolean cliqueAnnuler();

    public boolean cliqueRetablir();

    public PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException;

    public Model chargerLivraisons(File livraisons, PlanDeVille plan) throws JDOMException, SAXException, ParseException, IOException;
}
