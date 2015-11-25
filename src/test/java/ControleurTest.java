
import controleur.Controleur;
import controleur.ControleurInterface;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
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
	
	Observer observer;
	boolean miseAJourAppelee;
	
	@Before
	public void setUp(){
		miseAJourAppelee = false;
		observer = new Observer(){public void update(Observable o, Object arg){miseAJourAppelee = true;}};
	}
        
    @Test
    public void testchargerPlan() {
       Controleur controleur = new Controleur ();
       controleur.addObserver(observer);
       File plan = new File("samples/plan10x10-1.xml");
       controleur.chargerPlan(plan);
        
       assert(miseAJourAppelee);
    }
    
    @Test
    public void testchargerLivraisons() {
       Controleur controleur = new Controleur ();
       controleur.addObserver(observer);
       
       File plan = new File("samples/plan10x10-1.xml");
       controleur.chargerPlan(plan);
       
       File livraisons = new File("samples/livraison10x10-3.xml");
       controleur.chargerLivraisons(livraisons);
       
       assert(miseAJourAppelee);
    }    
}
