package modele.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import modele.xmldata.Demande;
import modele.xmldata.Fenetre;
import modele.xmldata.Intersection;
import modele.xmldata.Livraison;
import modele.xmldata.PlanDeVille;
import modele.xmldata.Troncon;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaderJDOMFactory;
import org.jdom2.input.sax.XMLReaderXSDFactory;
import org.xml.sax.SAXException;

/**
 * DeserialiseurXML comme son nom l'indique permet de déserialiser un fichier
 * xml en créant les objets correspondant. Dans notre cas, il déserialisera le
 * plan de la ville et la demande de livraison. Cette classe utilise des schémas
 * XML (XSD) pour valider les fichiers XML en entrée.
 * Implémenter comme Singleton
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public class DeserialiseurXML {

    /**
     * Constructeur privé pour le patron de conception singleton
     */
    private DeserialiseurXML() { }
    
    /** L'instance unique du désérialiseur XML */
    private static DeserialiseurXML instance = null;
    
    /**
     * Permet de récupérer l'instance unique du désérérialiseur
     * @return L'instance unique cette classe
     */
    public static DeserialiseurXML getInstance() {
    	if (instance == null) {
    		instance = new DeserialiseurXML();
    	}
    	return instance;
    }
    
    private final String XSD_PLAN = "xsd/validateurPlan.xsd";
    private final String XSD_LIVRAISONS = "xsd/validateurLivraisons.xsd";

    /**
     * Construit le plan de la ville à partir d'un fichier xml.
     *
     * @param planXML le fichier xml répresentant le plan de la ville.
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     * @throws SAXException  Problème survenu lors de la validation par le schéma XSD
     */
    public PlanDeVille ouvrirPlanDeVille(final InputStream planXML)
            throws JDOMException, IOException, SAXException, ExceptionXML {
    	
        // Validation du fichier XML avec le schéma
        Document document = validerFichierXML(XSD_PLAN, planXML);

        PlanDeVille planDeVille = new PlanDeVille();

        // Chargement de l'élément racine
        Element elementRacine = document.getRootElement();

        // Récuperation des Intersections
        List<Element> intersectionList = elementRacine.getChildren(XMLTags.NOEUD);
        for (Element e : intersectionList) {
        	Intersection intersection = chargerIntersection(e);
            planDeVille.addInstersection(intersection);
        }
        
        return planDeVille;
    }
    
	/**
     * Construit le plan de la ville à partir d'un fichier xml.
     *
     * @param planXML le fichier xml répresentant le plan de la ville.
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     * @throws SAXException  Problème survenu lors de la validation par le schéma XSD
     */
    public PlanDeVille ouvrirPlanDeVille(final File planXML)
            throws JDOMException, IOException, SAXException, ExceptionXML {
        InputStream inputStream = new FileInputStream(planXML);
        return ouvrirPlanDeVille(inputStream);
    }
    

	/**
	 * Charge une intersection à partir d'un élément XML
	 * @param e L'élément XML "Noeud"
	 * @return Un objet intersection
	 * @throws DataConversionException S'il y a un problème de conversion : une donnée n'est pas dans le bon type
	 * @throws ExceptionXML Si un élément XML est introuvable
	 */
	private Intersection chargerIntersection(Element e) throws DataConversionException, ExceptionXML {
	     int idIntersection = e.getAttribute(XMLTags.INTERSECTION_ID).getIntValue();
         int xIntersection = e.getAttribute(XMLTags.INTERSECTION_X).getIntValue();
         int yIntersection = e.getAttribute(XMLTags.INTERSECTION_Y).getIntValue();
         Intersection intersection = new Intersection(idIntersection, xIntersection, yIntersection);

         // Récuperation des troncons sortant de chaque Intersection
         List<Element> tronconList = e.getChildren(XMLTags.TRONCON_SORTANT);
         for (Element elementTroncon : tronconList) {
         	// Chargement du tronçon
             Troncon tronconSortant = chargerTroncon(elementTroncon, idIntersection); 

             // Ajout du troncon sortant à l'intersection
             intersection.addTroncon(tronconSortant.getIdDestination(), tronconSortant);
         }
         
         return intersection;
	}

    /**
     * Charge un tronçon à partir d'un élément XML LeTronconSortant
     * @param elementTroncon L'élément XML LeTronconSortant
     * @param intersectionSource L'intersection d'où vient le tronçon
     * @return Le tronçon créé
     * @throws ExceptionXML S'il y a une erreur du format du XML
     * @throws DataConversionException Si une valeur n'est pas dans le type attendu
     */
    private Troncon chargerTroncon(Element elementTroncon, int intersectionSource) throws ExceptionXML, DataConversionException {
        String nomRue = elementTroncon.getAttributeValue(XMLTags.NOM_RUE);

        // Récuperation de la vitesse et de la longueur du troncon sous forme de String
        String vitesseString = elementTroncon.getAttributeValue(XMLTags.TRONCON_VITESSE).replace(',', '.');
        String longueurString = elementTroncon.getAttributeValue(XMLTags.TRONCON_LONGUEUR).replace(',', '.');

        // Conversion des String en float
        float vitesse = Float.parseFloat(vitesseString);
        float longueur = Float.parseFloat(longueurString);

        int idIntersectionDestination = elementTroncon.getAttribute(XMLTags.TRONCON_DESTINATION).getIntValue();
        if (idIntersectionDestination == intersectionSource) {
            throw new ExceptionXML("Impossible de charger le plan : une intersection plointe vers elle même");
        }
    	
    	return new Troncon(nomRue, vitesse, longueur, idIntersectionDestination);
    }

    /**
     * Construit la demande de livraisons à partir d'un fichier xml.
     *
     * @param livraisonXml le fichier xml répresentant la demande de livraison
     * @param planDeVille  le plan de la ville préalablement chargé
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     */
    public Demande ouvrirDemande(final InputStream livraisonXml, final PlanDeVille planDeVille)
            throws SAXException, IOException, JDOMException, ParseException, ExceptionXML {

        // Chargement du validateur xsd et validation
        Document document = validerFichierXML(XSD_LIVRAISONS, livraisonXml);
        Element journeeType = document.getRootElement();

        // Récupération de l'entrepôt
        Intersection intersectionEntrepot = chargerEntrepot(journeeType, planDeVille);

        // Récuperation des plages ou fenetre
        List<Element> listePlage = journeeType.getChild(XMLTags.PLAGES_HORAIRES).getChildren();

        /* Les ids des livraisons stockées dans les fichiers XML sont uniques par fenêtre.
            Du coup pour avoir des ids uniques dans le modele un utilise l'id ET l'index de la fenetre pour creer une livraison. 
         */
        int fenetreCompteur = 0;
        List<Fenetre> listeFenetre = new ArrayList<>();
        for (Element elementFenetre : listePlage) {
        	Fenetre nouvelleFenetre = chargerFenetre(fenetreCompteur++, elementFenetre, planDeVille);
            listeFenetre.add(nouvelleFenetre);
        }

        return new Demande(intersectionEntrepot, listeFenetre);
    }
    
	/**
     * Construit la demande de livraisons à partir d'un fichier xml.
     *
     * @param planDeVille le plan de la ville préalablement chargé
     * @throws JDOMException Problème survenu lors de du parsing
     * @throws IOException   Problème survenu lors de la lecture du fichier
     */
    public Demande ouvrirDemande(final File livraisonXml, final PlanDeVille planDeVille)
            throws SAXException, IOException, JDOMException, ParseException, ExceptionXML {

        InputStream inputStream = new FileInputStream(livraisonXml);
        return ouvrirDemande(inputStream, planDeVille);
    }

	/**
	 * Charge l'entrepot de la demande de livraison
	 * @param journeeType L'élément racine du document
	 * @param planDeVille Le plan de la ville déjà chargé
	 * @return L'intersection où il y l'entrepot
	 * @throws ExceptionXML Si le fichier est mal formé
	 * @throws DataConversionException Si une valeur a un type inattendu
	 */
	private Intersection chargerEntrepot(Element journeeType, PlanDeVille planDeVille) throws ExceptionXML, DataConversionException 	{
		   int idEntrepot = journeeType.getChild(XMLTags.ENTREPOT).getAttribute(XMLTags.ENTREPOT_ADRESSE).getIntValue();
	        Intersection intersectionEntrepot = planDeVille.getIntersection(idEntrepot);
	        if (intersectionEntrepot == null) {
	            throw new ExceptionXML("Il semblerait que l'entrepot ne se trouve pas dans la ville. Veuillez vérifier votre fichier");
	        }
	        
	        return intersectionEntrepot;
	}
    
	/**
	 * Charge la fenêtre de livraisons
	 * @param numeroFenetre Le numéro de la fenêtre à charger
	 * @param elementFenetre L'élément XML de la fenêtre
	 * @param planDeVille Le plan de la ville déjà chargé
	 * @return La nouvelle fenêtre
	 * @throws DataConversionException Si une valeur a un type inattendu 
	 * @throws ExceptionXML Si il y a une erreur de format XML
	 */
	private Fenetre chargerFenetre(int numeroFenetre, Element elementFenetre, PlanDeVille planDeVille)
			throws DataConversionException, ExceptionXML 	{
		
        int heureDebut = convertirHeureEnSeconde(elementFenetre.getAttributeValue(XMLTags.FENETRE_HEURE_DEBUT));
        int heureFin = convertirHeureEnSeconde(elementFenetre.getAttributeValue(XMLTags.FENETRE_HEURE_FIN));

        if (heureDebut >= heureFin) {
            throw new ExceptionXML("Une des fenêtres est incorrecte. L'heure de début est supérieure ou égale à l'heure de fin");
        }

        Fenetre nouvelleFenetre = new Fenetre(heureDebut, heureFin);

        // Récuperation des livraison
        List<Element> listeLivraison = elementFenetre.getChild(XMLTags.FENETRE_LIVRAISONS).getChildren();

        for (Element elementLivraison : listeLivraison) {
            Livraison livraison = chargerLivraison(elementLivraison, numeroFenetre, planDeVille);
            nouvelleFenetre.ajouterLivraison(livraison.getId(), livraison);
        }
        
        return nouvelleFenetre;
	}

	/**
	 * Charge une livraison
	 * @param elementLivraison L'élément XML de la livraison
	 * @param numeroFenetre Le numéro de la fenêtre à laquelle appartient la livraison
	 * @param planDeVille Le plan de la ville
	 * @return La nouvelle livraison
	 * @throws DataConversionException Si une valeur n'a pas le type attendu 
	 * @throws ExceptionXML Si le fichier XML est mal formé
	 */
	private Livraison chargerLivraison(Element elementLivraison, int numeroFenetre, PlanDeVille planDeVille)
			throws DataConversionException, ExceptionXML {
		
        int idLivraison = elementLivraison.getAttribute(XMLTags.LIVRAISON_ID).getIntValue();

        /* On ajoute 10000 l'index de fenêtre. Comme ça les ids
           restent uniques (supposant qu'on a moins de 10000 livraisons
           par fenêtre, ça semble réaliste)
         */
        idLivraison += numeroFenetre * 10000;
        int idClient = elementLivraison.getAttribute(XMLTags.LIVRAISON_CLIENT).getIntValue();
        int idIntersection = elementLivraison.getAttribute(XMLTags.LIVRAISON_ADRESSE).getIntValue();

        // Vérification de la présence de l'intersection dans la ville
        if (planDeVille.getIntersection(idIntersection) == null) {
            throw new ExceptionXML(String.format("Impossible de charger la demande de livraison. L'intersection : %d ne se trouve pas dans la ville.", idIntersection));
        }
        
        return new Livraison(idLivraison, idClient, idIntersection);
	}

    /**
     * Valide un fichier XML avec le fichier XSD fourni.
     *
     * @param xsdResourceName  Le nom schéma XSD à utiliser.
     * @param fichierXML Le fichier XML à valider.
     * @return Renvoie le document correspondant si la validation est effective.
     * @throws JDOMException Si la validation du XML a échoué.
     * @throws IOException   S'il y a eu une erreur de lecture.
     */
    private Document validerFichierXML(final String xsdResourceName, final InputStream fichierXML)
            throws JDOMException, IOException {

        InputStream xsdStream = ClassLoader.getSystemResourceAsStream(xsdResourceName);
        
        XMLReaderJDOMFactory factory = new XMLReaderXSDFactory(new StreamSource(xsdStream));
        SAXBuilder saxBuilder = new SAXBuilder(factory);
        
        Document document = saxBuilder.build(fichierXML);

        return document;
    }

    /**
     * Convertit un String sous la fomre HH:mm:ss en séconde
     *
     * @param heureMnSec Chaine de caractère à convertir
     * @return Le timestamp en seconde
     */
    private int convertirHeureEnSeconde(String heureMnSec) {
        String[] decoupage = heureMnSec.split(":");

        int heure = Integer.parseInt(decoupage[0]);
        int mn = Integer.parseInt(decoupage[1]);
        int sec = Integer.parseInt(decoupage[2]);

        return heure * 3600 + mn * 60 + sec;
    }
    
    /**
     * Constantes de tous les éléments que l'on peut rencontrer dans les fichiers XML
     */
    private static class XMLTags {
    	
		/** Noeud */
    	private final static String NOEUD = "Noeud";
    	/** id */
    	private final static String INTERSECTION_ID = "id";
    	/** x */
    	private final static String INTERSECTION_X = "x";
    	/** y */
    	private final static String INTERSECTION_Y = "y";
    	
    	/** LeTronconSortant */
    	private static final String TRONCON_SORTANT = "LeTronconSortant";
    	/** nomRue */
    	private static final String NOM_RUE = "nomRue";
    	/** vitesse */
    	private static final String TRONCON_VITESSE = "vitesse";
    	/** longueur */
    	private static final String TRONCON_LONGUEUR = "longueur";
    	/** idNoeudDestination */
    	private static final String TRONCON_DESTINATION = "idNoeudDestination";
    	
    	/** Entrepot */
    	private static final String ENTREPOT = "Entrepot";
    	/** adresse */
    	private static final String ENTREPOT_ADRESSE = "adresse";
    	
    	/** PlagesHoraires */
    	private static final String PLAGES_HORAIRES = "PlagesHoraires";
    	
    	/** heureDebut */
    	private static final String FENETRE_HEURE_DEBUT = "heureDebut";
    	/** heureFin */
    	private static final String FENETRE_HEURE_FIN = "heureFin";
    	/** Livraisons */
    	private static final String FENETRE_LIVRAISONS = "Livraisons";
    	
    	/** id */
    	private static final String LIVRAISON_ID = "id";
    	/** client */
    	private static final String LIVRAISON_CLIENT = "client";
    	/** adresse */
    	private static final String LIVRAISON_ADRESSE = "adresse";
    }

}
