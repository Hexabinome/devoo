package modele.persistance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;

import modele.donneesxml.Demande;
import modele.donneesxml.PlanDeVille;
import modele.donneesxml.Troncon;

import org.jdom2.JDOMException;
import org.jdom2.input.JDOMParseException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test de la classe Deserialiseur XMl
 *
 * @author Royer Robin
 */
public class DeserialiseurXMLTest {


    @Test
    public void TestOuvrirPlanDeVille() throws JDOMException, IOException, SAXException, ParseException, ExceptionXML {
        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestLight.xml"));

        //init troncons
        Troncon troncon15 = new Troncon("r0", 1, 150, 5);
        Troncon troncon45 = new Troncon("v0", 2, 100, 5);
        Troncon troncon65 = new Troncon("q0", 7, 200, 5);
        Troncon troncon95 = new Troncon("h0", 8, 300, 5);
        Troncon troncon51 = new Troncon("v0", 3, 150, 1);
        Troncon troncon54 = new Troncon("v0", 4, 100, 4);
        Troncon troncon56 = new Troncon("v0", 5, 200, 6);
        Troncon troncon59 = new Troncon("v0", 6, 300, 9);

        //test des coordonnes de la premiere et derniere intersection
        assertEquals("La coordonnee en x de l'intersection 5 doit etre stockee", 20, ville.getIntersection(1).getX());
        assertEquals("La coordonnee en y de l'intersection 5 doit etre stockee", 15, ville.getIntersection(1).getY());
        assertEquals("La coordonnee en x de l'intersection doit etre stockee", 20, ville.getIntersection(9).getX());
        assertEquals("La coordonnee en y de l'intersection doit etre stockee", 60, ville.getIntersection(9).getY());
        //test de presence de toutes les intersections
        assertEquals("L'id de l'intersection doit etre correcte", 1, ville.getIntersection(1).getId());
        assertEquals("L'id de l'intersection doit etre correcte", 4, ville.getIntersection(4).getId());
        assertEquals("L'id de l'intersection doit etre correcte", 5, ville.getIntersection(5).getId());
        assertEquals("L'id de l'intersection doit etre correcte", 6, ville.getIntersection(6).getId());
        assertEquals("L'id de l'intersection doit etre correcte", 9, ville.getIntersection(9).getId());
        //test des nom de troncons partant de l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon51.getNomRue(),
                ville.getIntersection(5).getTroncon(1).getNomRue());
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon54.getNomRue(),
                ville.getIntersection(5).getTroncon(4).getNomRue());
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon56.getNomRue(),
                ville.getIntersection(5).getTroncon(6).getNomRue());
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon59.getNomRue(),
                ville.getIntersection(5).getTroncon(9).getNomRue());
        //test des vitesses de troncons partant de l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon51.getVitesse(),
                ville.getIntersection(5).getTroncon(1).getVitesse(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon54.getVitesse(),
                ville.getIntersection(5).getTroncon(4).getVitesse(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon56.getVitesse(),
                ville.getIntersection(5).getTroncon(6).getVitesse(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon59.getVitesse(),
                ville.getIntersection(5).getTroncon(9).getVitesse(), 0);
        //test des longueurs de troncons partant de  l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon51.getLongueur(),
                ville.getIntersection(5).getTroncon(1).getLongueur(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon54.getLongueur(),
                ville.getIntersection(5).getTroncon(4).getLongueur(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon56.getLongueur(),
                ville.getIntersection(5).getTroncon(6).getLongueur(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon59.getLongueur(),
                ville.getIntersection(5).getTroncon(9).getLongueur(), 0);
        //test des durees de troncons partant de l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon51.getDuree(),
                ville.getIntersection(5).getTroncon(1).getDuree(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon54.getDuree(),
                ville.getIntersection(5).getTroncon(4).getDuree(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon56.getDuree(),
                ville.getIntersection(5).getTroncon(6).getDuree(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon59.getDuree(),
                ville.getIntersection(5).getTroncon(9).getDuree(), 0);
        //test des noms de troncons lies a l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon15.getNomRue(),
                ville.getIntersection(1).getTroncon(5).getNomRue());
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon45.getNomRue(),
                ville.getIntersection(4).getTroncon(5).getNomRue());
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon65.getNomRue(),
                ville.getIntersection(6).getTroncon(5).getNomRue());
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon95.getNomRue(),
                ville.getIntersection(9).getTroncon(5).getNomRue());

        //test des vitesse de troncons lies a l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon15.getVitesse(),
                ville.getIntersection(1).getTroncon(5).getVitesse(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon45.getVitesse(),
                ville.getIntersection(4).getTroncon(5).getVitesse(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon65.getVitesse(),
                ville.getIntersection(6).getTroncon(5).getVitesse(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon95.getVitesse(),
                ville.getIntersection(9).getTroncon(5).getVitesse(), 0);

        //test des longueurs de troncons lies a l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon15.getLongueur(),
                ville.getIntersection(1).getTroncon(5).getLongueur(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon45.getLongueur(),
                ville.getIntersection(4).getTroncon(5).getLongueur(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon65.getLongueur(),
                ville.getIntersection(6).getTroncon(5).getLongueur(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon95.getLongueur(),
                ville.getIntersection(9).getTroncon(5).getLongueur(), 0);

        //test des durees de troncons lies a l'intersection 5
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon15.getDuree(),
                ville.getIntersection(1).getTroncon(5).getDuree(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon45.getDuree(),
                ville.getIntersection(4).getTroncon(5).getDuree(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon65.getDuree(),
                ville.getIntersection(6).getTroncon(5).getDuree(), 0);
        assertEquals("L'intersection stockees doit avoir ses troncons", troncon95.getDuree(),
                ville.getIntersection(9).getTroncon(5).getDuree(), 0);
    }


    @Test
    public void TestOuvrirLivraison() throws JDOMException, IOException, SAXException, ParseException, ExceptionXML {
        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTest.xml"));
        Demande demande = DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonTest.xml"), ville);

        //test si l'entrepot a le bon id d'intercetion
        assertEquals("l'entrepot doit etre stocke", 5, demande.getEntrepot().getId());
        //test si les fenetres sont bien stockees
        assertEquals("La fenetre 1 doit avoir le bon timestamp de debut", 8 * 3600, demande.getFenetres().get(1).getTimestampDebut());
        assertEquals("La fenetre 1 doit avoir le bon timestamp de fin", 12 * 3600, demande.getFenetres().get(1).getTimestampFin());

        assertEquals("La fenetre 2 doit avoir le bon timestamp de debut", 13 * 3600, demande.getFenetres().get(2).getTimestampDebut());
        assertEquals("La fenetre 2 doit avoir le bon timestamp de fin", 18 * 3600, demande.getFenetres().get(2).getTimestampFin());
        
        assertEquals("La fenêtre 0 doit avec un timestamp de début à 0", 0, demande.getFenetres().get(0).getTimestampDebut());
        assertEquals("La fenêtre 0 doit avec un timestamp de fin à 0", 0, demande.getFenetres().get(0).getTimestampFin());
    }


    // -------- TEST : ERREUR SUR LE FICHIER PLAN DE VILLE XML ----------
    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVilleSansNoeud()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        //noeud sans livraison
        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur1.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVilleBaliseManquante()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        // une balise noeud fermante manquante

        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur2.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");


    }


    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVilleMauvaisFormat()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        // Données au mauvais format(lettre a la place de chiffre)
        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur3.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = ExceptionXML.class)
    public void TestOuvrirPlanDeVilleErreurDestination()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        //les troncons ont pour destination le meme noeud
        DeserialiseurXML.getInstance().ouvrirPlanDeVille(ClassLoader.getSystemResourceAsStream("samples/planTestErreur4.xml"));
        fail("Le chargement aurait du lever une Exception de type ExceptionXML");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVilleVitesseNegative()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur5.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVilleLongueurNegative()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        // longueurs negatives
        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur6.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVilleNombreFlotant()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        // id et coordonneées flotante
        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur7.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVilleTronconsHorsNoeud()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        // troncon a l'exterieur d'un noeud

        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur8.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeVille2Reseaux()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        // 2 reseaux

        DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/planTestErreur9.xml"));
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test
    public void TestOuvrirPlanDeVilleIdInexistant()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML {
        // troncon avec id de destination inexistant
        try {
            DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                    ClassLoader.getSystemResourceAsStream("samples/planTestErreur10.xml"));
            fail("le chargement devrait lever une erreur");
        } catch (JDOMException | IOException | SAXException e) {
            assertEquals(org.jdom2.input.JDOMParseException.class, e.getClass());
        }
    }

    // -------- TEST : ERREUR SUR LE FICHIER PLAN DE LIVRAISON XML ----------
    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeLivraisonMemeId()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // 2 livraison succesif avec le meme id

        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur1.xml"), ville);
        fail("le chargement devrait lever une erreur");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeLivraisonEntrepotInexistant()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // Adresse de l'entreport inexistant
        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur2.xml"), ville);
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = ExceptionXML.class)
    public void TestOuvrirPlanDeLivraisonFenetreNegative()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // heure de fin avant l'heure de debut
        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur3.xml"), ville);
        fail("Le chargement aurait du lever une Exception de type ExceptionXML");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeLivraisonHorairesNegatifs()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // Les horaires de la fenetre sont negatifs

        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur4.xml"), ville);
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeLivraisonHeureHorsFormat()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // Horaires hors du format 24:60:60

        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur5.xml"), ville);
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeLivraisonSansLivraison()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // fichier xml sans livraisons

        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur6.xml"), ville);
        fail("Le chargement aurait du lever une Exception de type JDOMParseException");

    }

    @Test(expected = ExceptionXML.class)
    public void TestOuvrirPlanDeLivraisonAdresseInexistante()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // adresse de livraison inexistante
        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur7.xml"), ville);
        fail("Le chargement aurait du lever une Exception de type ExceptionXML");

    }

    @Test(expected = JDOMParseException.class)
    public void TestOuvrirPlanDeLivraisonEntrepotManquant()
            throws java.lang.RuntimeException, org.jdom2.JDOMException, java.io.IOException, org.xml.sax.SAXException,
            ExceptionXML, ParseException {
        // la balise entrepot non presente dans le xml

        PlanDeVille ville = DeserialiseurXML.getInstance().ouvrirPlanDeVille(
                ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        DeserialiseurXML.getInstance().ouvrirDemande(
                ClassLoader.getSystemResourceAsStream("samples/livraisonErreur8.xml"), ville);
        fail("le chargement devrait lever une erreur");

    }

}