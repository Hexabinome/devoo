package modele.persistence;

import modele.xmldata.Demande;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Cette classe joue le role d'interface pour le chargement des fichiers XML
 * @author Mohamed El Mouctar HAIDARA
 */
public class ChargeurXML {

    private ChargeurXML(){}

    public static PlanDeVille chargePlanDeVille() throws JDOMException, SAXException, IOException {
        File file = OuvreurDeFichierXML.getInstance().ouvrirSelectionneurDeFichier("Choissiez le plan de la ville");
        if (file == null) {
            return null;
        }

        return DeserialiseurXML.ouvrirPlanDeVille(file);
    }

    public static Demande chargeDemande(PlanDeVille planDeVille)
            throws JDOMException, SAXException, ParseException, IOException {
        File file = OuvreurDeFichierXML.getInstance().ouvrirSelectionneurDeFichier("Choisissez la demande de livraison");
        if (file == null) {
            return null;
        }

        return DeserialiseurXML.ouvrirLivraison(file, planDeVille);
    }
}
