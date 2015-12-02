package modele.donneesxml;
import modele.donneesxml.Chemin;
import modele.donneesxml.GrapheRealisation;
import modele.donneesxml.Troncon;
import static org.junit.Assert.assertEquals;

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
        
       assertEquals("On peut bien accéder à des chemins (nulls par défaut)", graphe.getChemin(1, 1), null);
       assertEquals("On peut bien accéder à des chemins (nulls si index out of bound)", graphe.getChemin(11, 11), null);
    }   
    
    
  @Test
  public void testAjoutChemin() {
  	GrapheRealisation graphe = new GrapheRealisation(2);
  	
    assertEquals("On peut bien accéder à des chemins (nulls par défaut)", graphe.getChemin(1, 1), null);
    
    Chemin chemin = new Chemin(1, new ArrayList<Troncon>(), 1, 2);
    
  	graphe.setChemin(chemin, 3, 4);
      
    assertEquals("On peut bien accéder au chemin ajouté", graphe.getChemin(3, 4), chemin);
      
  }  
}
