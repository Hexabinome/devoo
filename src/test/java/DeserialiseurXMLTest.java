import java.io.IOException;
import java.text.ParseException;
import modele.persistence.DeserialiseurXML;
import modele.xmldata.Demande;
import modele.xmldata.Model;
import modele.xmldata.ModelLecture;
import modele.xmldata.PlanDeVille;
import modele.xmldata.Troncon;
import org.jdom2.JDOMException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Test de la classe Deserialiseur XMl
 * @author Royer Robin
 */
public class DeserialiseurXMLTest {


    @Test
    public void TestOuvrirPlanDeVille() throws JDOMException, IOException, SAXException, ParseException{
      PlanDeVille ville = DeserialiseurXML.ouvrirPlanDeVille(ClassLoader.getSystemResourceAsStream("samples/planTestLight.xml"));

      //init troncons
      Troncon troncon1 = new Troncon("v0",4,150,1);
      Troncon troncon2 = new Troncon("v0",5,100,4);
      Troncon troncon3 = new Troncon("v0",6,200,6);
      Troncon troncon4 = new Troncon("v0",7,300,9);

      //test de l'intersection stockees
      assertEquals("La coordonnee en x de l'intersection doit etre recuperer", 20, ville.getIntersection(5).getX());
      assertEquals("La coordonnee en y de l'intersection doit etre recuperer", 30, ville.getIntersection(5).getY());
      assertEquals("L'id de l'intersection doit etre correcte", 1, ville.getIntersection(1).getId());
      assertEquals("L'id de l'intersection doit etre correcte", 4, ville.getIntersection(4).getId());
      assertEquals("L'id de l'intersection doit etre correcte", 5, ville.getIntersection(5).getId());
      assertEquals("L'id de l'intersection doit etre correcte", 6, ville.getIntersection(6).getId());
      assertEquals("L'id de l'intersection doit etre correcte", 9, ville.getIntersection(9).getId());
      //test des troncons lies a l'intersection
      assertEquals("L'intersection stockees doit avoir ses troncons", troncon1, ville.getIntersection(5).getTroncon(1));
      assertEquals("L'intersection stockees doit avoir ses troncons", troncon2, ville.getIntersection(5).getTroncon(4));
      assertEquals("L'intersection stockees doit avoir ses troncons", troncon3, ville.getIntersection(5).getTroncon(6));
      assertEquals("L'intersection stockees doit avoir ses troncons", troncon4, ville.getIntersection(5).getTroncon(9));
    }


    @Test
    public void TestOuvrirLivraison() throws JDOMException, IOException, SAXException, ParseException{
      PlanDeVille ville = DeserialiseurXML.ouvrirPlanDeVille(ClassLoader.getSystemResourceAsStream("samples/planTest.xml"));
      Demande demande = DeserialiseurXML.ouvrirLivraison(ClassLoader.getSystemResourceAsStream("samples/livraisonTest.xml"), ville);

      assertEquals("fail", 0, demande);
    }




}