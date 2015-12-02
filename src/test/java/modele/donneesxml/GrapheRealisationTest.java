package modele.donneesxml;
import modele.donneesxml.Chemin;
import modele.donneesxml.GrapheRealisation;
import modele.donneesxml.Troncon;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

/**
 *
 * @author jolan
 */
public class GrapheRealisationTest {
             
    @Test
    public void testConstuireGraphe() {
    	GrapheRealisation graphe = new GrapheRealisation(10);
        
    	try {
    		graphe.getChemin(1, 1);
    		fail("ne devrait pas atteindre ce code");
    	} catch (RuntimeException e) { }
    	
    	try {
    		graphe.getChemin(11, 11);
    		fail("ne devrait pas atteindre ce code");
    	} catch (RuntimeException e) { }
    }
    
    
  @Test
  public void testAjoutChemin() {
  	GrapheRealisation graphe = new GrapheRealisation(2);
  	
  	try {
  		graphe.getChemin(1, 1);
  		fail("ne devrait pas atteindre ce code");
  	} catch (RuntimeException e) { }
    
    Chemin chemin = new Chemin(1, new ArrayList<Troncon>(), 1, 2);
    
  	graphe.setChemin(chemin, 3, 4);
      
    assertEquals("On peut bien accéder au chemin ajouté", graphe.getChemin(3, 4), chemin);
      
  }  
}
