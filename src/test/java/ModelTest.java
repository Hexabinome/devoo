/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.text.ParseException;
import modele.persistence.DeserialiseurXML;
import modele.xmldata.Demande;
import modele.xmldata.Model;
import modele.xmldata.ModelLecture;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author robinroyer
 */
public class ModelTest {
  @Test
  public void TestModelPlan() throws JDOMException, IOException, SAXException, ParseException{
    // initialisation
    PlanDeVille ville = DeserialiseurXML.ouvrirPlanDeVille(ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
    Demande demande = DeserialiseurXML.ouvrirLivraison(ClassLoader.getSystemResourceAsStream("samples/livraison10x10-1.xml"), ville);
    ModelLecture monModel = new Model(ville, demande);

    //test
    assertEquals("Le model doit avoir le plan de ville obtenu a la lecture xml", ville, monModel.getPlan());
    assertEquals("Le model doit avoir les livraisons obtenus a la lecture xml", demande, monModel.getDemande());
  }
}

