package modele.donneesxml;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author robinroyer
 */
public class IntersectionTest {
    
        //-----------TEST  d'intersection 
        /*  +Intersection(id:int,x:int,y:int)
            +ajouterTroncon(int id, Troncon troncon):void
            +getId():int
            +getX():int
            +getY():int
            +getTroncon(cibleId:int):Troncon
            0 aLiaison(cibleId:int):boolean
            +toString():String
        */
	
	@Test
	public void intersectionGeneralTest() {
		Intersection i = new Intersection(1, 0, 0);
		
		Troncon t1 = new Troncon("rue", 3, 10, 2);
		Troncon t2 = new Troncon("rue2", 5, 50, 4);
		i.ajouterTroncon(2, t1);
		i.ajouterTroncon(4, t2);
		
		assertTrue(i.aLiaison(2));
		assertFalse(i.aLiaison(3));
		
		assertEquals(i.getMinCout(), t1.getCout(), 0);
	}
    
}
