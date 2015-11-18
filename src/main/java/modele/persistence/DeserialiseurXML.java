package modele.persistence;

import modele.xmldata.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * DeserialiseurXML comme son nom l'indique permet de déserialiser un fichier xml en créant les objets correspondant.
 * Dans notre cas, elle déserialisera le Plan de la ville et la demande de livraison.
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public class DeserialiseurXML {

    /**
     * XML schema definition pour la validation des entrées en XML.
     * On récupère les fichiers comme InputStream dans les ressources ainsi en packageant il n'y aura pas de soucis.
     */
    private static final InputStream XSDPLAN = ClassLoader.getSystemResourceAsStream("xsd/validateurPlan.xsd");
    private static final InputStream XSDLIVRAISON = ClassLoader.getSystemResourceAsStream(
            "xsd/validateurLivraisons.xsd");


    /**
     * Constructeur privé. Pas besoin d'instancier la classe
     */
    private DeserialiseurXML() {
    }

    /**
     * Construis le plan de la ville à partir d'un fichier xml.
     *
     * @param planXML le fichier xml répresentant le plan de la ville.
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     * @throws SAXException  Problème survenu lors de la validation par le schéma XSD
     */
    public static PlanDeVille ouvrirPlanDeVille(InputStream planXML) throws JDOMException, IOException, SAXException {
        Document document = validerFichierXML(XSDPLAN, planXML);
        // System.out.println(fichierValidationLivraisons);
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

                Troncon tronconSortant = new Troncon(nomRue, vitesse, longueur, idDestination);

                // Ajout du troncon sortant à l'intersection
                intersection.addTroncon(idDestination, tronconSortant);
            }
            planDeVille.addInstersection(intersection);
        }
        //  System.out.println(planDeVille);
        return planDeVille;
    }

    /**
     * Construis le plan de la ville à partir d'un fichier xml.
     *
     * @param planXML le fichier xml répresentant le plan de la ville.
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     * @throws SAXException  Problème survenu lors de la validation par le schéma XSD
     */
    public static PlanDeVille ouvrirPlanDeVille(File planXML) throws JDOMException, IOException, SAXException {
        InputStream inputStream = new FileInputStream(planXML);
        return ouvrirPlanDeVille(inputStream);
    }

    /**
     * Construis le plan des livraisons à partir d'un fichier xml.
     *
     * @param livraisonXml le fichier xml répresentant la demande de livraison
     * @param planDeVille  le plan de la ville préalablement chargé
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     */
    public static Demande ouvrirLivraison(InputStream livraisonXml, final PlanDeVille planDeVille)
            throws SAXException, IOException, JDOMException, ParseException {

        Document document = validerFichierXML(XSDLIVRAISON, livraisonXml);
        Element journeeType = document.getRootElement();

        // Récuperation de l'entrepot
        // System.out.println("entrepot : " + journeeType.getChild("Entrepot").getAttribute("adresse").getIntValue());
        int idEntrepot = journeeType.getChild("Entrepot").getAttribute("adresse").getIntValue();
        Intersection intersectionEntrepot = planDeVille.getIntersection(idEntrepot);
        if (intersectionEntrepot == null) {
            // TODO : peut etre utilisé une autre exception
            throw new JDOMException(
                    "Il semblerait que l'entrepot ne se trouve pas dans la ville. Veuillez vérifier votre fichier");
        }

        // Récuperation des plages ou fenetre
        List<Element> listePlage = journeeType.getChild("PlagesHoraires").getChildren();

        Fenetre nouvelleFenetre;
        List<Fenetre> listeFenetre = new ArrayList<>();
        Livraison livraison;

        for (Element elementFenetre : listePlage) {
            //   System.out.println(elementFenetre);
            //  System.out.println("heure de debut : " + elementFenetre.getAttributeValue("heureDebut"));
            // System.out.println("Heure de fin : " + elementFenetre.getAttributeValue("heureFin"));

            int heureDebut = convertirHeureEnSeconde(elementFenetre.getAttributeValue("heureDebut"));
            int heureFIn = convertirHeureEnSeconde(elementFenetre.getAttributeValue("heureFin"));

            nouvelleFenetre = new Fenetre(heureDebut, heureDebut);

            // Récuperation des livraison
            List<Element> listeLivraison = elementFenetre.getChild("Livraisons").getChildren();

            for (Element elementLivraison : listeLivraison) {
               /* System.out.println("id : " + elementLivraison.getAttribute("id").getIntValue());
                System.out.println("client : " + elementLivraison.getAttribute("client").getIntValue());
                System.out.println("adresse : " + elementLivraison.getAttribute("adresse").getIntValue()); */

                int id = elementLivraison.getAttribute("id").getIntValue();
                int idClient = elementLivraison.getAttribute("client").getIntValue();
                int idIntersection = elementLivraison.getAttribute("adresse").getIntValue();
                livraison = new Livraison(id, idClient, idIntersection);

                nouvelleFenetre.ajouterLivraison(id, livraison);
            }

            listeFenetre.add(nouvelleFenetre);
        }

        return new Demande(intersectionEntrepot, listeFenetre);
    }

    /**
     * Construis le plan des livraisons à partir d'un fichier xml.
     *
     * @param planDeVille le plan de la ville préalablement chargé
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     */
    public static Demande ouvrirLivraison(File livraisonXml, final PlanDeVille planDeVille)
            throws SAXException, IOException, JDOMException, ParseException {

        InputStream inputStream = new FileInputStream(livraisonXml);

        return ouvrirLivraison(inputStream, planDeVille);
    }

    /**
     * Valide un fichier XML avec le fichier XSD fourni.
     *
     * @param xsdStream  Le schéma XSD à utiliser.
     * @param fichierXML Le fichier XML à valider.
     * @return Renvoie le document correspondant si la validation est effective.
     * @throws JDOMException Si la validation du XML a échoué.
     * @throws IOException   S'il y a eu une erreur de lecture.
     */
    private static Document validerFichierXML(InputStream xsdStream, InputStream fichierXML)
            throws JDOMException, IOException {

        Document document = null;
        XMLReaderJDOMFactory factory = new XMLReaderXSDFactory(new StreamSource(xsdStream));
        SAXBuilder saxBuilder = new SAXBuilder(factory);
        document = saxBuilder.build(fichierXML);

        return document;
    }

    /**
     * Convertis un String sous la fomre HH:mm:ss en séconde
     *
     * @param heureMnSec Chaine de caractère à convertir
     * @return
     */
    private static int convertirHeureEnSeconde(String heureMnSec) {
        String[] decoupage = heureMnSec.split(":");

        int heure = Integer.parseInt(decoupage[0]);
        int mn = Integer.parseInt(decoupage[1]);
        int sec = Integer.parseInt(decoupage[2]);

        return heure * 3600 + mn * 60 + sec;
    }

}
