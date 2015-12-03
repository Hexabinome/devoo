package controleur;
import controleur.Controleur;
import controleur.ControleurInterface;
import controleur.observateur.ModeleObservateur;
import modele.donneesxml.ModeleLecture;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * @author robinroyer
 */
public class ControleurTest
{

    ModeleObservateur observer;
    boolean miseAJourAppelee;
    ControleurInterface controleurInterface;

    @Before
    public void setUp()
    {

        controleurInterface = new Controleur();
        miseAJourAppelee = false;
        observer = new ModeleObservateur()
        {
            @Override
            public void notifierObservateursModele()
            {
                miseAJourAppelee = true;
            }

        };
        controleurInterface.ajouterModeleObservateur(observer);
    }

    /**
     * ***********************************************
     * Tests chargerPlan
     ************************************************
     */
    
     @Test
     public void testChargerPlan() throws Exception {

    	 File plan = new File("src/main/resources/samples/plan10x10.xml");
    	 controleurInterface.chargerPlan(plan);
		
    	 assert (miseAJourAppelee);
     }
     
    /**
     * ***********************************************
     * Tests chargerLivraisons
     ************************************************
     */
    
     @Test
     public void testChargerLivraisons() throws Exception {
 
		 File plan = new File("src/main/resources/samples/plan10x10.xml");
		 controleurInterface.chargerPlan(plan);
		
		 File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
		 controleurInterface.chargerLivraisons(livraisons);
    	 
	     assert (miseAJourAppelee);
     }
    
     @Test
     public void testChargerLivraisonsSansPlan() {
        
	     boolean jetee = false;
	
	     try {
	    	 File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
	    	 controleurInterface.chargerLivraisons(livraisons);
	     } catch (Exception e) {
	    	 jetee = true;
	     }
	     assert(jetee);
     }
     
    /**
     * ***********************************************
     * Tests cliqueCalculerTournee
     ************************************************
     */
    
     @Test
     public void testCliqueCalculerTournee() throws Exception {

		 File plan = new File("src/main/resources/samples/plan10x10.xml");
		 controleurInterface.chargerPlan(plan);
		
	     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
	     controleurInterface.chargerLivraisons(livraisons);
		        
	     controleurInterface.clicCalculTournee();
	
	     assert (miseAJourAppelee);
     }
    
     @Test
     public void testCliqueCalculerTourneeSansPlan() {
        
	     boolean jetee = false;
	
	     try {
	    	 controleurInterface.clicCalculTournee();
	     } catch (RuntimeException e) {
	    	 jetee = true;
	     }
	     assert(jetee);
     }
    
     @Test
     public void testCliqueCalculerTourneeSansLivraisons() {
        
	     boolean jetee = false;
	
	     try {
	    	 File plan = new File("src/main/resources/samples/plan10x10.xml");
	    	 controleurInterface.chargerPlan(plan);
	    	 controleurInterface.clicCalculTournee();
	     } catch (Exception e) {
	    	 jetee = true;
	     }	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueCalculerTourneeApresCalculerTournee() {
        
	     boolean jetee = false;
	
	     try {
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
		     controleurInterface.chargerLivraisons(livraisons);
		     controleurInterface.clicCalculTournee();
		     controleurInterface.clicCalculTournee();
	     } catch (Exception e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueCalculerTourneeApresAjouter() {
        
	     boolean jetee = false;
	
	     try {
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
		     controleurInterface.chargerLivraisons(livraisons);
		     controleurInterface.clicCalculTournee();
		     controleurInterface.clicOutilAjouter();
		     controleurInterface.clicSurPlan(32);
		     controleurInterface.clicSurLivraison(2);
	     } catch (Exception e) {
	    	 jetee = true;
	     }    	
	     assert(jetee);
     }
     
    /**
     * ***********************************************
     * Tests cliqueOutilSupprimer
     ************************************************
     */
    
     @Test
     public void testCliqueOutilSupprimer() throws Exception {
    	 
	     File plan = new File("src/main/resources/samples/plan10x10.xml");
	     controleurInterface.chargerPlan(plan);
	
	     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
	     controleurInterface.chargerLivraisons(livraisons);
	        
	     controleurInterface.clicCalculTournee();
	        
	     controleurInterface.clicOutilSupprimer();
		
		 assert (miseAJourAppelee);
     }
    
     @Test
     public void testCliqueOutilSupprimerSansPlan() {

	     boolean jetee = false;
	
	     try {
	    	 controleurInterface.clicOutilSupprimer();
	     } catch (RuntimeException e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueOutilSupprimerSansLivraisons() {

	     boolean jetee = false;
	
	     try {
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		     controleurInterface.clicOutilSupprimer();
	     } catch (Exception e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
     
    /**
     * ***********************************************
     * Tests cliqueOutilAjouter
     ************************************************
     */
 
     @Test
     public void testCliqueOutilAjouter() throws Exception {
    	 
	     File plan = new File("src/main/resources/samples/plan10x10.xml");
	     controleurInterface.chargerPlan(plan);
	
	     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
	     controleurInterface.chargerLivraisons(livraisons);
	        
	     controleurInterface.clicCalculTournee();
	        
	     controleurInterface.clicOutilAjouter();
		
		 assert (miseAJourAppelee);
     }
    
     @Test
     public void testCliqueOutilAjouterSansPlan() {

	     boolean jetee = false;
	
	     try {
	    	 controleurInterface.clicOutilAjouter();
	     } catch (RuntimeException e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueOutilAjouterSansLivraisons() {

	     boolean jetee = false;
	
	     try {
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		     controleurInterface.clicOutilAjouter();
	     } catch (Exception e) {
	    	 jetee = true;
	     }
	     assert(jetee);
     }

    /**
     * ***********************************************
     * Tests cliqueOutilEchanger
     ************************************************
     */
    
     @Test
     public void testCliqueOutilEchanger() throws Exception {
    	 
	     File plan = new File("src/main/resources/samples/plan10x10.xml");
	     controleurInterface.chargerPlan(plan);
	
	     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
	     controleurInterface.chargerLivraisons(livraisons);
	        
	     controleurInterface.clicCalculTournee();
	        
	     controleurInterface.clicOutilEchanger();
	 
		 assert (miseAJourAppelee);
     }
    
     @Test
     public void testCliqueOutilEchangerSansPlan() {

	     boolean jetee = false;
	
	     try {
	    	 controleurInterface.clicOutilEchanger();
	     } catch (RuntimeException e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueOutilEchangerSansLivraisons() {

	     boolean jetee = false;
	
	     try {
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		     controleurInterface.clicOutilEchanger();
	     } catch (Exception e) {
	    	 jetee = true;
	     }
	    	
     assert(jetee);
     }
   
    /**
     * ***********************************************
     * Tests cliqueSurPlan
     ************************************************
     */
   
     @Test
     public void testCliqueSurPlanAjouter() throws Exception {
    	
	     File plan = new File("src/main/resources/samples/plan10x10.xml");
	     controleurInterface.chargerPlan(plan);
	
	     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
	     controleurInterface.chargerLivraisons(livraisons);
	        
	     controleurInterface.clicCalculTournee();
	        
	     controleurInterface.clicOutilAjouter();
	        
	     controleurInterface.clicSurPlan(32);

	     assert (miseAJourAppelee);
     }
    
    /**
     * ***********************************************
     * Tests cliqueSurLivraison
     ************************************************
     */
   
     @Test
     public void testCliqueSurLivraisonAjouter() throws Exception {
		
	     File plan = new File("src/main/resources/samples/plan10x10.xml");
	     controleurInterface.chargerPlan(plan);
	
	     File livraisons = new File("src/main/resources/samples/livraison10x10-1.xml");
	     controleurInterface.chargerLivraisons(livraisons);
	        
	     controleurInterface.clicCalculTournee();
	        
	     controleurInterface.clicOutilAjouter();
	        
	     controleurInterface.clicSurPlan(32);
	        
	     controleurInterface.clicSurPlan(68);
		     
		  assert (miseAJourAppelee);
     }
    
     @Test
     public void testCliqueSurLivraisonSupprimer() throws Exception {
    	
	     File plan = new File("src/main/resources/samples/plan10x10.xml");
	     controleurInterface.chargerPlan(plan);
	
	     File livraisons = new File("src/main/resources/samples/livraison10x10-3.xml");
	     controleurInterface.chargerLivraisons(livraisons);
	        
	     controleurInterface.clicCalculTournee();
	        
	     controleurInterface.clicOutilSupprimer();
	        
	     controleurInterface.clicSurLivraison(2);
	 
	     assert (miseAJourAppelee);
     }
    
     @Test
     public void testCliqueSurLivraisonEchanger() throws Exception {
    	 
	     File plan = new File("src/main/resources/samples/plan10x10.xml");
	     controleurInterface.chargerPlan(plan);
	
	     File livraisons = new File("src/main/resources/samples/livraison10x10-1.xml");
	     controleurInterface.chargerLivraisons(livraisons);
	        
	     controleurInterface.clicCalculTournee();
	        
	     controleurInterface.clicOutilEchanger();
	        
	     controleurInterface.clicSurPlan(13);
	     controleurInterface.clicSurPlan(68);
	 
	     assert (miseAJourAppelee);
     }
     
    /**
     * ***********************************************
     * Tests cliqueAnnuler
     ************************************************
     */
    
     @Test
     public void testCliqueAnnulerSansPlan() {

	     boolean jetee = false;
	
	     try {
	    	 controleurInterface.clicAnnuler();;
	     } catch (RuntimeException e) {
	     jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueAnnulerSansLivraisons() {

	     boolean jetee = false;
	
	     try {
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		     controleurInterface.clicAnnuler();
	     } catch (Exception e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueAnnulerApresAjouter() throws Exception {
    	 
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		
		     File livraisons = new File("src/main/resources/samples/livraison10x10-1.xml");
		     controleurInterface.chargerLivraisons(livraisons);
		        
		     controleurInterface.clicCalculTournee();
		        
		     controleurInterface.clicOutilAjouter();
		        
		     controleurInterface.clicSurPlan(10);
		        
		     controleurInterface.clicSurPlan(14);
		        
		     controleurInterface.clicAnnuler();
    	 
		 assert (miseAJourAppelee);
     }
     
    /**
     * ***********************************************
     * Tests cliqueRetablir
     ************************************************
     */
    
     @Test
     public void testCliqueRetablirSansPlan() {

	     boolean jetee = false;
	
	     try {
	    	 controleurInterface.clicRetablir();;
	     } catch (RuntimeException e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
     @Test
     public void testCliqueRetablirSansLivraisons() {

	     boolean jetee = false;
	
	     try {
		     File plan = new File("src/main/resources/samples/plan10x10.xml");
		     controleurInterface.chargerPlan(plan);
		     controleurInterface.clicRetablir();
	     } catch (Exception e) {
	    	 jetee = true;
	     }
	    	
	     assert(jetee);
     }
    
    /**
     * ***********************************************
     * Tests getModel
     ************************************************
     */
    @Test
    public void testResultatCalculerLivraison()
    {
        //File plan = new File("src/main/resources/samples/planTest2.xml");
        File plan = new File("target" + File.separator + "classes" + File.separator + "samples" + File.separator + "planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        //File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        File livraisons = new File("target" + File.separator + "classes" + File.separator + "samples" + File.separator + "livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();

        ModeleLecture model = controleurInterface.getModele();
        List<List<Integer>> tournee = model.getTournee();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(4);
        listeTemp.add(5);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(4);
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, tournee);
    }

    @Test
    public void testSuppressionCalculerLivraison()
    {
    	
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();
        ModeleLecture model = controleurInterface.getModele();
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(10103);

        model = controleurInterface.getModele();

        List<List<Integer>> tournee = model.getTournee();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(4);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, tournee);
    }

    /**
     * message d'erreur mais rien n'est censé changer dans la livraison
     */
    @Test
    public void testSuppressionEntrepotCalculerLivraison()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();
        ModeleLecture model = controleurInterface.getModele();
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(1);
        model = controleurInterface.getModele();

        List<List<Integer>> tournee = model.getTournee();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(4);
        listeTemp.add(5);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(4);
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, tournee);
    }

    @Test
    public void testAnnulerSuppressionCalculerLivraison()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();
        ModeleLecture model = controleurInterface.getModele();
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(10103);

        controleurInterface.clicAnnuler();
        model = controleurInterface.getModele();

        List<List<Integer>> tournee = model.getTournee();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(4);
        listeTemp.add(5);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(4);
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, tournee);
    }

    @Test
    public void testAnnulerRetablirSuppressionCalculerLivraison()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();
        ModeleLecture model = controleurInterface.getModele();
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(10103);
        controleurInterface.clicAnnuler();
        controleurInterface.clicRetablir();
        model = controleurInterface.getModele();

        List<List<Integer>> tournee = model.getTournee();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(4);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, tournee);
    }

    /**
     * Test l'ajout juste aprés l'entrepot
     */
    @Test
    public void testAjoutApresEntrepotCalculerLivraison()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest3.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();

        ModeleLecture model = controleurInterface.getModele();

        controleurInterface.clicOutilAjouter();
        controleurInterface.clicSurPlan(2);
        controleurInterface.clicSurLivraison(1);

        model = controleurInterface.getModele();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();

        tourneeAttendue = new ArrayList<>();
        listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(4);
        listeTemp.add(5);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(4);
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, model.getTournee());
    }

    @Test
    public void testAjoutCalculerLivraison()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();

        ModeleLecture model = controleurInterface.getModele();

        controleurInterface.clicOutilAjouter();
        controleurInterface.clicSurPlan(3);
        controleurInterface.clicSurLivraison(10101);

        model = controleurInterface.getModele();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();

        tourneeAttendue = new ArrayList<>();
        listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(3);
        listeTemp.add(4);
        listeTemp.add(5);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(4);
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, model.getTournee());
    }

    @Test
    public void testAnnulerAjoutCalculerLivraison()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();

        ModeleLecture model = controleurInterface.getModele();

        controleurInterface.clicOutilAjouter();
        controleurInterface.clicSurPlan(3);
        controleurInterface.clicSurLivraison(10101);
        controleurInterface.clicAnnuler();

        model = controleurInterface.getModele();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();

        tourneeAttendue = new ArrayList<>();
        listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(4);
        listeTemp.add(5);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(4);
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, model.getTournee());
    }

    @Test
    public void testAnnulerRetablirAjoutCalculerLivraison()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");
        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();

        ModeleLecture model = controleurInterface.getModele();

        controleurInterface.clicOutilAjouter();
        controleurInterface.clicSurPlan(3);
        controleurInterface.clicSurLivraison(10101);
        controleurInterface.clicAnnuler();
        controleurInterface.clicRetablir();

        model = controleurInterface.getModele();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();

        tourneeAttendue = new ArrayList<>();
        listeTemp = new ArrayList<Integer>();
        listeTemp.add(2);
        listeTemp.add(3);
        listeTemp.add(4);
        listeTemp.add(5);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(4);
        listeTemp.add(9);
        listeTemp.add(10);
        listeTemp.add(11);
        tourneeAttendue.add(listeTemp);

        listeTemp = new ArrayList<Integer>();
        listeTemp.add(9);
        listeTemp.add(4);
        listeTemp.add(5);
        listeTemp.add(1);

        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, model.getTournee());
    }

    @Test
    public void testSuppressionAll()
    {
        File plan = new File("src/main/resources/samples/planTest2.xml");
        try {
			controleurInterface.chargerPlan(plan);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        File livraisons = new File("src/main/resources/samples/livraisonTest2.xml");

        try {
			controleurInterface.chargerLivraisons(livraisons);
		} catch (Exception e) {
			fail(e.getMessage());
		}

        controleurInterface.clicCalculTournee();
        ModeleLecture model = controleurInterface.getModele();
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(20105);
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(20104);
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(10103);
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(10102);
        controleurInterface.clicOutilSupprimer();
        controleurInterface.clicSurLivraison(10101);

        model = controleurInterface.getModele();

        List<List<Integer>> tournee = model.getTournee();

        List<List<Integer>> tourneeAttendue = new ArrayList<>();
        List<Integer> listeTemp = new ArrayList<Integer>();
        tourneeAttendue.add(listeTemp);

        assertEquals("La tournee est bien calculé", tourneeAttendue, tournee);
    }

}
