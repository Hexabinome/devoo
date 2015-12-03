package modele.donneesxml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author robinroyer
 */
public class TronconTest {
    
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
	
	@Test
	public void calculCoutTest() {
		Troncon t = new Troncon("nom", 5, 10, 0);
		assertEquals(10, t.getCout(), 0);
	}
    
}
