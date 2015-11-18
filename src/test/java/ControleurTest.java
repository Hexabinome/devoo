
import controleur.Controleur;
import controleur.ControleurInterface;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author robinroyer
 */
public class ControleurTest {
     
    /*  TODO : TEST LES METHODES
        +ajouterDesactObserver():void
        +ajouterModelObserver():void
        +cliqueSurPlan():void
        +cliqueAnnuler():boolean
        +cliqueOutilAjouter():void
        +cliqueOutilSupprimer():void
        +cliqueOutilEchanger():void
        +getModel():ModelLecture
        +cliqueCalculerTourne():void 
    */
        
    @Test //chargerPlan(chemin:String):void
    public void testchargerPlan() {
       ControleurInterface controleur = new Controleur ();
       controleur.chargerPlan("samples/plan10x10.xml");
        
       assertEquals("", 0, 1);
    }
    
    @Test 
    public void testChargerLivraison() {
       ControleurInterface controleur = new Controleur ();
       controleur.chargerPlan("samples/livraison10x10-1.xml");
       
       assertEquals("", 0, 1);
        
        
    }
    
}
