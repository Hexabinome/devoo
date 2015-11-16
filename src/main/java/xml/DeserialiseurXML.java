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
import modele.Demande;

/**
 * DeserialiseurXML comme son nom l'indique permet de déserialiser un fichier xml en créant les objets correspondant.
 * Dans notre cas, elle déserialisera le Plan de la ville et la demande de livraison.
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public class DeserialiseurXML {




    /**
     * Construis le plan de la ville à partir d'un fichier xml.
     *
     * @throws JDOMException Problème survenu lors de du parsing
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
            Intersection intersection = new Intersection(idIntersection, xIntersection, yIntersection);

            // Récuperation des troncons de chaque Intersection
            List<Element> tronconList = e.getChildren("LeTronconSortant");
            for (Element elementTroncon : tronconList) {
                //System.out.println(elementTroncon.getAttribute("nomRue").getValue());
                String nomRue = elementTroncon.getAttributeValue("nomRue");

                // Récuperation de la vitesse et de la longueur du troncon sous forme de String
                String vitesseString = elementTroncon.getAttributeValue("vitesse").replace(',', '.');
                String longueurString = elementTroncon.getAttributeValue("longueur").replace(',', '.');

                // Conversion des String en float
                float vitesse = Float.parseFloat(vitesseString);
                float longueur = Float.parseFloat(longueurString);

                int idDestination = elementTroncon.getAttribute("idNoeudDestination").getIntValue();

                float duree = longueur / vitesse;

                Troncon tronconSortant = new Troncon(nomRue, vitesse, longueur, duree, idDestination);

                // Ajout du troncon sortant à l'intersection
                intersection.addTroncon(tronconSortant);
            }
            planDeVille.addInstersection(intersection);
        }
        System.out.println(planDeVille);
        return planDeVille;
    }


    /**
     * Construis le plan des livraisons à partir d'un fichier xml.
     *
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     */   
    public static Demande ouvrirLivraison( File livraisonXml){
        // TODO:validifcation du fichier xml    
        // TODO: construct Demande and return it
        try {            
            Document document = (Document) new SAXBuilder().build(livraisonXml);
            Element journeeType = document.getRootElement();
            
            //entrepot   
            int entrepot =  journeeType.getChild("Entrepot").getAttribute("adresse").getIntValue();
//            System.out.println(entrepot);
            //plage horaires
            Element plageHoraires = journeeType.getChild("PlagesHoraires");
            //plages
            List<Element> listePlage = plageHoraires.getChildren("Plage");

            for (Element plage : listePlage){
                
                // TODO : utilise le format d'heure
//                System.out.println("heure de debut : " + plage.getAttributeValue("heureDebut"));
//                System.out.println("Heure de fin : " + plage.getAttribute("heureFin"));
                //livraisons
                Element livraisons = plage.getChild("Livraisons");
                //livraison
                List<Element> listeLivraison = livraisons.getChildren("Livraison");
                for (Element livraison : listeLivraison) {
                    
                    int livraisonId = livraison.getAttribute("id").getIntValue();
                    int livraisonClient = livraison.getAttribute("client").getIntValue();
                    int livraisonAdresse = livraison.getAttribute("adresse").getIntValue();
                    
//                    System.out.println(livraisonId);
//                    System.out.println(livraisonClient);
//                    System.out.println(livraisonAdresse);
                }
            }
            
        } catch (JDOMException ex) {
        } catch (IOException ex) {
        }
        return null;
    }
    
    /**
     * Valide un fichier XML avec le fichier XSD fourni.
     *
     * @return Renvoie le document correspondant si la validation est effective.
     */
    private static Document validerFichierXML(String nomFichierXSD, File fichierXML) throws JDOMException, IOException {

        File fichierXSD = new File(nomFichierXSD);
        Document document = null;

        XMLReaderJDOMFactory factory = new XMLReaderXSDFactory(fichierXSD);
        SAXBuilder saxBuilder = new SAXBuilder(factory);
        document = saxBuilder.build(fichierXML);

        return document;
    }

    public void ok(){

    }
}
