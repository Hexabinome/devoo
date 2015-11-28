package modele.persistance;

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
 * DeserialiseurXML comme son nom l'indique permet de déserialiser un fichier
 * xml en créant les objets correspondant. Dans notre cas, elle déserialisera le
 * Plan de la ville et la demande de livraison. Cette classe utilise des schémas
 * XML (XSD) pour valider les fichiers XML en entrée. L'utilisation
 * d'InputStream se justifie par le fait que en créant un du projet jar, on ne
 * pourra pas accéder aux schémas sous la forme de File sans toucher au système
 * de fichier du système.
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public class DeserialiseurXML
{

    /**
     * Constructeur privé. Pas besoin d'instancier la class
     */
    private DeserialiseurXML()
    {
    }

    /**
     * Construis le plan de la ville à partir d'un fichier xml.
     *
     * @param planXML le fichier xml répresentant le plan de la ville.
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException Problème survenu lors de la lecture du fichier
     * @throws SAXException Problème survenu lors de la validation par le schéma
     * XSD
     */
    public static PlanDeVille ouvrirPlanDeVille(InputStream planXML)
            throws JDOMException, IOException, SAXException, ExceptionXML
    {
        InputStream XSDPLAN = ClassLoader.getSystemResourceAsStream("xsd/validateurPlan.xsd");
        Document document = validerFichierXML(XSDPLAN, planXML);
        // System.out.println(fichierValidationLivraisons);
        PlanDeVille planDeVille = new PlanDeVille();

        Element elementPere = document.getRootElement();

        // Récuperation des Intersections
        List<Element> intersectionList = elementPere.getChildren("Noeud");
        for (Element e : intersectionList) {
            //System.out.println(e.getAttribute("id")+" - " + e.getAttribute("x") +" - " + e.getAttribute("y"));
            int idIntersection = e.getAttribute("id").getIntValue();
            int xIntersection = e.getAttribute("x").getIntValue();
            int yIntersection = e.getAttribute("y").getIntValue();
            Intersection intersection = new Intersection(idIntersection, xIntersection, yIntersection);

            // Récuperation des troncons sortant de chaque Intersection
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
                int idIntersectionDestination = elementTroncon.getAttribute("idNoeudDestination").getIntValue();
                if (idIntersectionDestination == idIntersection)
                    throw new ExceptionXML("Impossible de charger le plan : une intersection plointe vers elle même");

                Troncon tronconSortant = new Troncon(nomRue, vitesse, longueur, idIntersectionDestination);

                // Ajout du troncon sortant à l'intersection
                intersection.addTroncon(idIntersectionDestination, tronconSortant);
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
     * @throws IOException Problème survenu lors de la lecture du fichier
     * @throws SAXException Problème survenu lors de la validation par le schéma
     * XSD
     */
    public static PlanDeVille ouvrirPlanDeVille(File planXML)
            throws JDOMException, IOException, SAXException, ExceptionXML
    {
        InputStream inputStream = new FileInputStream(planXML);
        return ouvrirPlanDeVille(inputStream);
    }

    /**
     * Construis le plan des livraisons à partir d'un fichier xml.
     *
     * @param livraisonXml le fichier xml répresentant la demande de livraison
     * @param planDeVille le plan de la ville préalablement chargé
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException Problème survenu lors de la lecture du fichier
     */
    public static Demande ouvrirLivraison(InputStream livraisonXml, final PlanDeVille planDeVille)
            throws SAXException, IOException, JDOMException, ParseException, ExceptionXML
    {

        InputStream XSDLIVRAISON = ClassLoader.getSystemResourceAsStream(
                "xsd/validateurLivraisons.xsd");
        Document document = validerFichierXML(XSDLIVRAISON, livraisonXml);
        Element journeeType = document.getRootElement();

        // Récuperation de l'entrepot
        // System.out.println("entrepot : " + journeeType.getChild("Entrepot").getAttribute("adresse").getIntValue());
        int idEntrepot = journeeType.getChild("Entrepot").getAttribute("adresse").getIntValue();
        Intersection intersectionEntrepot = planDeVille.getIntersection(idEntrepot);
        if (intersectionEntrepot == null)
            throw new ExceptionXML(
                    "Il semblerait que l'entrepot ne se trouve pas dans la ville. Veuillez vérifier votre fichier");

        // Récuperation des plages ou fenetre
        List<Element> listePlage = journeeType.getChild("PlagesHoraires").getChildren();

        Fenetre nouvelleFenetre;
        List<Fenetre> listeFenetre = new ArrayList<>();
        Livraison livraison;

        /**
         * les id's des livraisons stoques dans les xml sont unique dans le
         * "scope" d'une fenetre. Par contre quand on a plusieurs fenetres, ces
         * IDs ne sont plus unique. Du coup pour avoirt des ids uniques dans le
         * modele un utilise l'id ET l'index de la fenetre pour creer une
         * livraison unique.
         */
        int fenetreCompteur = 0;
        for (Element elementFenetre : listePlage) {
            fenetreCompteur++;

            int heureDebut = convertirHeureEnSeconde(elementFenetre.getAttributeValue("heureDebut"));
            int heureFin = convertirHeureEnSeconde(elementFenetre.getAttributeValue("heureFin"));

            if (heureDebut >= heureFin)
                throw new ExceptionXML(
                        "Une des fenêtres est incorrecte. L'heure de début est supérieure ou égale à l'heure de fin");

            nouvelleFenetre = new Fenetre(heureDebut, heureFin);

            // Récuperation des livraison
            List<Element> listeLivraison = elementFenetre.getChild("Livraisons").getChildren();

            for (Element elementLivraison : listeLivraison) {
                /* System.out.println("id : " + elementLivraison.getAttribute("id").getIntValue());
                 System.out.println("client : " + elementLivraison.getAttribute("client").getIntValue());
                 System.out.println("adresse : " + elementLivraison.getAttribute("adresse").getIntValue()); */

                int idLivraison = elementLivraison.getAttribute("id").getIntValue();

                /**
                 * on ajoute 10000 l'index du fenetre. Comme ca les ids
                 * restent unique (supposant qu'on a moins de 10000 livraisons
                 * pas fenetre, mais ca semble realiste)
                 */
                idLivraison += fenetreCompteur * 10000;
                int idClient = elementLivraison.getAttribute("client").getIntValue();
                int idIntersection = elementLivraison.getAttribute("adresse").getIntValue();

                // vérification de la présence de l'intersection dans la ville
                if (planDeVille.getIntersection(idIntersection) == null)
                    throw new ExceptionXML(
                            "Impossible de charger la demande de livraison. L'intersection : " + idIntersection + " ne se trouve pas dans la ville.");

                livraison = new Livraison(idLivraison, idClient, idIntersection);

                nouvelleFenetre.ajouterLivraison(idLivraison, livraison);
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
     * @throws IOException Problème survenu lors de la lecture du fichier
     */
    public static Demande ouvrirLivraison(File livraisonXml, final PlanDeVille planDeVille)
            throws SAXException, IOException, JDOMException, ParseException, ExceptionXML
    {

        InputStream inputStream = new FileInputStream(livraisonXml);

        return ouvrirLivraison(inputStream, planDeVille);
    }

    /**
     * Valide un fichier XML avec le fichier XSD fourni.
     *
     * @param xsdStream Le schéma XSD à utiliser.
     * @param fichierXML Le fichier XML à valider.
     * @return Renvoie le document correspondant si la validation est effective.
     * @throws JDOMException Si la validation du XML a échoué.
     * @throws IOException S'il y a eu une erreur de lecture.
     */
    private static Document validerFichierXML(InputStream xsdStream, InputStream fichierXML)
            throws JDOMException, IOException
    {

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
    private static int convertirHeureEnSeconde(String heureMnSec)
    {
        String[] decoupage = heureMnSec.split(":");

        int heure = Integer.parseInt(decoupage[0]);
        int mn = Integer.parseInt(decoupage[1]);
        int sec = Integer.parseInt(decoupage[2]);

        return heure * 3600 + mn * 60 + sec;
    }

}
