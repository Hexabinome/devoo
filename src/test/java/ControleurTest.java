
import controleur.Controleur;
import controleur.ControleurInterface;
import controleur.ModelObserveur;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
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

    ModelObserveur observer;
    boolean miseAJourAppelee;
    ControleurInterface controleurInterface;

    @Before
    public void setUp() {

        controleurInterface = new Controleur();
        miseAJourAppelee = false;
        observer = new ModelObserveur() {
            @Override
            public void notifierLesOberseursDuModel() {
                miseAJourAppelee = true;
            }
        };
        controleurInterface.ajouterModelObserver(observer);
    }
    
    /*************************************************
     * Tests chargerPlan
     *************************************************/

    @Test
    public void testChargerPlan() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);
        assert (miseAJourAppelee);
    }

    
    /*************************************************
     * Tests chargerLivraisons
     *************************************************/
    
    @Test
    public void testChargerLivraisons() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);

        assert (miseAJourAppelee);
    }
    
    @Test
    public void testChargerLivraisonsSansPlan() {
        
    	boolean jetee = false;

    	try {
    		 File livraisons = new File("samples/livraison10x10-3.xml");
    	     controleurInterface.chargerLivraisons(livraisons);
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    
    /*************************************************
     * Tests cliqueCalculerTournee
     *************************************************/

    @Test
    public void testCliqueCalculerTournee() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();

        assert (miseAJourAppelee);
    }
    
    @Test
    public void testCliqueCalculerTourneeSansPlan() {
        
    	boolean jetee = false;

    	try {
    		controleurInterface.cliqueCalculerTourne();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueCalculerTourneeSansLivraisons() {
        
    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
    		controleurInterface.cliqueCalculerTourne();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueCalculerTourneeApresCalculerTournee() {
        
    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
            File livraisons = new File("samples/livraison10x10-3.xml");
            controleurInterface.chargerLivraisons(livraisons);
    		controleurInterface.cliqueCalculerTourne();
    		controleurInterface.cliqueCalculerTourne();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueCalculerTourneeApresAjouter() {
        
    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
            File livraisons = new File("samples/livraison10x10-3.xml");
            controleurInterface.chargerLivraisons(livraisons);
    		controleurInterface.cliqueCalculerTourne();
    		controleurInterface.cliqueOutilAjouter();
            controleurInterface.cliqueSurPlan(32);
            controleurInterface.cliqueSurLivraison(2);
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    /*************************************************
     * Tests cliqueOutilSupprimer
     *************************************************/
    
    @Test
    public void testCliqueOutilSupprimer() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilSupprimer();

        assert (miseAJourAppelee);
    }
    
    @Test
    public void testCliqueOutilSupprimerSansPlan() {

    	boolean jetee = false;

    	try {
    		controleurInterface.cliqueOutilSupprimer();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueOutilSupprimerSansLivraisons() {

    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
    		controleurInterface.cliqueOutilSupprimer();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    /*************************************************
     * Tests cliqueOutilAjouter
     *************************************************/
    
    @Test
    public void testCliqueOutilAjouter() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilAjouter();

        assert (miseAJourAppelee);
    }
    
    @Test
    public void testCliqueOutilAjouterSansPlan() {

    	boolean jetee = false;

    	try {
    		controleurInterface.cliqueOutilAjouter();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueOutilAjouterSansLivraisons() {

    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
    		controleurInterface.cliqueOutilAjouter();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    /*************************************************
     * Tests cliqueOutilEchanger
     *************************************************/
    
    @Test
    public void testCliqueOutilEchanger() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilEchanger();

        assert (miseAJourAppelee);
    }
    
    @Test
    public void testCliqueOutilEchangerSansPlan() {

    	boolean jetee = false;

    	try {
    		controleurInterface.cliqueOutilEchanger();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueOutilEchangerSansLivraisons() {

    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
    		controleurInterface.cliqueOutilEchanger();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    /*************************************************
     * Tests cliqueSurPlan
     *************************************************/
    
    @Test
    public void testCliqueSurPlanAjouter() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilAjouter();
        
        controleurInterface.cliqueSurPlan(32);

        assert (miseAJourAppelee);
    }
    
    /*************************************************
     * Tests cliqueSurLivraison
     *************************************************/
    
    @Test
    public void testCliqueSurLivraisonAjouter() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilAjouter();
        
        controleurInterface.cliqueSurPlan(32);
        
        controleurInterface.cliqueSurLivraison(2);

        assert (miseAJourAppelee);
    }
    
    @Test
    public void testCliqueSurLivraisonSupprimer() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilSupprimer();
        
        controleurInterface.cliqueSurLivraison(2);

        assert (miseAJourAppelee);
    }
    
    @Test
    public void testCliqueSurLivraisonEchanger() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilEchanger();
        
        controleurInterface.cliqueSurLivraison(2);
        controleurInterface.cliqueSurLivraison(3);

        assert (miseAJourAppelee);
    }
    
    /*************************************************
     * Tests cliqueAnnuler
     *************************************************/
    
    @Test
    public void testCliqueAnnulerSansPlan() {

    	boolean jetee = false;

    	try {
    		controleurInterface.cliqueAnnuler();;
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueAnnulerSansLivraisons() {

    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
    		controleurInterface.cliqueAnnuler();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueAnnulerApresAjouter() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);
        
        controleurInterface.cliqueCalculerTourne();
        
        controleurInterface.cliqueOutilAjouter();
        
        controleurInterface.cliqueSurPlan(32);
        
        controleurInterface.cliqueSurLivraison(2);
        
        controleurInterface.cliqueAnnuler();

        assert (miseAJourAppelee);
    }
    
    /*************************************************
     * Tests cliqueRetablir
     *************************************************/
    
    @Test
    public void testCliqueRetablirSansPlan() {

    	boolean jetee = false;

    	try {
    		controleurInterface.cliqueRetablir();;
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    @Test
    public void testCliqueRetablirSansLivraisons() {

    	boolean jetee = false;

    	try {
    		File plan = new File("samples/plan10x10.xml");
            controleurInterface.chargerPlan(plan);
    		controleurInterface.cliqueRetablir();
    	} catch (RuntimeException e) {
    		jetee = true;
    	}
    	
    	assert(jetee);
    }
    
    /*************************************************
     * Tests getModel
     *************************************************/
}
