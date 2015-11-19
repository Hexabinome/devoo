
import controleur.Controleur;
import controleur.ControleurInterface;
import java.io.File;
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
       File plan = new File("samples/plan10x10.xml");
       controleur.chargerPlan(plan);
        
       assertEquals("le plan aurait du etre charger", 0, 1);
    }
    
    @Test 
    public void testChargerLivraison() {
       ControleurInterface controleur = new Controleur ();
       File livraisons = new File("samples/livraison10x10-1.xml");
       controleur.chargerPlan(livraisons);
       
       assertEquals("le livraisons aurait du etre chargees", 0, 1);
        
        
    }
    
}
