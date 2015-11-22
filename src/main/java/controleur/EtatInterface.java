package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author Maxou
 */
public interface EtatInterface
{

    EtatInterface cliqueAnnuler();

    EtatInterface cliqueRetablir();

    EtatInterface cliqueSurLivraison(int livraisonId);

    EtatInterface chargerPlan(File plan) throws JDOMException, SAXException, IOException;

    EtatInterface chargerLivraisons(File livraisons) throws JDOMException, SAXException, ParseException, IOException;

    EtatInterface cliqueSurPlan(int x, int y);
    
    EtatInterface cliqueCalculerTournee();
}
