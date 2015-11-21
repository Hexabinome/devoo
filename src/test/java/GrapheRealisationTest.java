import modele.xmldata.Chemin;
import modele.xmldata.GrapheRealisation;
import modele.xmldata.Troncon;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jolan
 */
public class GrapheRealisationTest {
             
    @Test
    public void testConstuireGraphe() {
    	GrapheRealisation graphe = new GrapheRealisation(10);
        
       assertEquals("On peut bien accéder à des chemins (nulls par défaut)", graphe.getChemin(1, 1), null);
       assertEquals("On peut bien accéder à des chemins (nulls si index out of bound)", graphe.getChemin(11, 11), null);
    }   
    
    
  @Test
  public void testAjoutChemin() {
  	GrapheRealisation graphe = new GrapheRealisation(2);
  	
    assertEquals("On peut bien accéder à des chemins (nulls par défaut)", graphe.getChemin(1, 1), null);
    
    Chemin chemin = new Chemin(1, new ArrayList<Troncon>(), 1, 2);
    
  	graphe.setChemin(chemin);
      
    assertEquals("On peut bien accéder au chemin ajouté", graphe.getChemin(1, 2), chemin);
     
    Chemin chemin2 = new Chemin(1, new ArrayList<Troncon>(), 11, 2);
     
	graphe.setChemin(chemin2);
       
	assertEquals("Le chemin n'a pas été ajouté car out of bound", graphe.getChemin(11, 2), null);
      
  }  
}
