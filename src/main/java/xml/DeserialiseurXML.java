package xml;

import modele.Intersection;
import modele.PlanDeVille;
import modele.Troncon;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by elmhaidara on 16/11/15.
 */
public class DeserialiseurXML {


    /**
     * Construis le plan de la ville à partir d'un fichier xml.
     *
     * @throws JDOMException Problème survenu lors de lu parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     */
    public static PlanDeVille ouvrirPlanDeVille(File planXML) throws JDOMException, IOException {
        // TODO : validation du fichier xml en remplaçant la ligne suivante par validerFichierXMl("fichier.xsd",planXML)
        Document document = (Document) new SAXBuilder().build(planXML);

        PlanDeVille planDeVille = new PlanDeVille();

        Element rootElement = document.getRootElement();

        // Récuperation des Intersections
        List<Element> intersectionList = rootElement.getChildren("Noeud");
        for (Element e : intersectionList) {
            //System.out.println(e.getAttribute("id")+" - " + e.getAttribute("x") +" - " + e.getAttribute("y"));
            int idIntersection = e.getAttribute("id").getIntValue();
            int xIntersection = e.getAttribute("x").getIntValue();
            int yIntersection = e.getAttribute("y").getIntValue();
            Intersection intersection = new Intersection(idIntersection,xIntersection,yIntersection);

            // Récuperation des troncons de chaque Intersection
            List<Element> tronconList = e.getChildren("LeTronconSortant");
            for (Element troncon: tronconList ) {
                //System.out.println(troncon.getAttribute("nomRue"));


            }

        }


        return planDeVille;
    }


    /**
     * Valide un fichier XML avec le fichier XSD fourni.
     *
     * @return Renvoie le document correspondant si la validation est effective. null Sinon
     */
    private static Document validerFichierXML(String nomFichierXSD, File fichierXML) {

        File fichierXSD = new File(nomFichierXSD);
        Document document = null;
        try {
            XMLReaderJDOMFactory factory = new XMLReaderXSDFactory(fichierXSD);
            SAXBuilder saxBuilder = new SAXBuilder(factory);
            document = saxBuilder.build(fichierXML);
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return document;
    }
}
