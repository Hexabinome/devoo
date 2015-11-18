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
    
    //-----------TEST  d'intersection 
        /*  +Intersection(id:int,x:int,y:int)
            +addTroncon(int id, Troncon troncon):void
            +getId():int
            +getX():int
            +getY():int
            +getTroncon(cibleId:int):Troncon
            0 aLiaison(cibleId:int):boolean
            +toString():String
        */
    
    //-----------TEST  plan de ville
        /** +PlanDeVille(Map<Integer, Intersection> intersections)
            +addInstersection(Intersection intersection):void
            +getIntersection(int id):Intersection
            +getIntersections():Map<Integer, Intersection>
            +toString():String
         */
    
    //-----------TEST  troncon 
        /*
            +Troncon(String nomRue, float vitesse, float longueur, int idDestination): ctor
            +getNomRue(): String
            +setNomRue(String nomRue): void
            +getVitesse(): float
            +getLongueur(): float
            +getDuree(): float
            +toString(): String
        */
    
    //-----------TEST  demande
        /*
            +Demande(Intersection entrepot, List<Fenetre> fenetres): ctor
            +getEntrepot(): Intersection
            +getFenetres(): Collection<Fenetre>
        */
    
    
    //-----------TEST  Fenetre
        /*
            +Fenetre(int timestampDebut, int timestampFin)
            +getTimestampDebut():int
            +getTimestampFin():int
            + effacerLivraison(livrasionId:int):void  
            +ajouterLivraison(id:int,livraison:Livraison):void
            +getLivraisons():Map<Integer, Livraison>
            +toString():String
        */
    
    //-----------TEST  Livraison
        /*
            +Livraisons(int id, int clientId, int idIntersection): ctor
            +getId(): int
            +getClientId(): int
            +getAdresse(): int
        */
    


  }

}

