package modele.donneesxml;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author robinroyer
 */
public class FenetreTest
{

    //-----------TEST  Fenetre
        /*
     +Fenetre(int timestampDebut, int timestampFin)
     +getTimestampDebut():int
     +getTimestampFin():int
     + effacerLivraison(livrasionId:int):void  
     +ajouterLivraison(id:int,livraison:Livraison):void
     +getLivraisons():Map<Integer, Livraison>
     +toString():String
     */
	@Test
    public void dijkstraTest()
    {
        Troncon a = new Troncon("A", 1, 1, 2);
        Troncon b = new Troncon("B", 1, 1, 3);
        Troncon c = new Troncon("C", 1, 1, 4);
        Troncon d = new Troncon("D", 1, 1, 4);
        Troncon e = new Troncon("E", 1, 1, 4);
        Troncon f = new Troncon("F", 1, 1, 5);
        Troncon g = new Troncon("G", 1, 1, 6);
        Troncon h = new Troncon("H", 1, 1, 7);
        Troncon i = new Troncon("I", 1, 1, 8);

        Intersection i1 = new Intersection(1, 1, 1);
        i1.ajouterTroncon(a);
        i1.ajouterTroncon(f);
        i1.ajouterTroncon(g);

        Intersection i2 = new Intersection(2, 1, 1);
        i2.ajouterTroncon(b);
        i2.ajouterTroncon(d);

        Intersection i3 = new Intersection(3, 1, 1);
        i3.ajouterTroncon(c);

        Intersection i4 = new Intersection(4, 1, 1);

        Intersection i5 = new Intersection(5, 1, 1);
        i5.ajouterTroncon(e);

        Intersection i6 = new Intersection(6, 1, 1);
        i6.ajouterTroncon(h);

        Intersection i7 = new Intersection(7, 1, 1);
        i7.ajouterTroncon(i);

        Intersection i8 = new Intersection(8, 1, 1);

        PlanDeVille plan = new PlanDeVille();
        plan.ajouterInstersection(i1);
        plan.ajouterInstersection(i2);
        plan.ajouterInstersection(i3);
        plan.ajouterInstersection(i4);
        plan.ajouterInstersection(i5);
        plan.ajouterInstersection(i6);
        plan.ajouterInstersection(i7);
        plan.ajouterInstersection(i8);

        ArrayList<Troncon> troncon = new ArrayList<>();

        for (Chemin chemin : Fenetre.dijkstra(i1, plan)) {
            switch (chemin.getIdFin()) {
                case 1:
                    assertEquals(chemin.getCout(), 0, 0);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());
                    break;
                case 2:
                    assertEquals(chemin.getCout(), 1, 0);
                    troncon.add(a);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());
                    troncon = new ArrayList<>();
                    break;
                case 3:
                    assertEquals(chemin.getCout(), 2, 0);
                    troncon.add(a);
                    troncon.add(b);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());

                    troncon = new ArrayList<>();
                    break;
                case 4:
                    assertEquals(chemin.getCout(), 2, 0);
                    troncon.add(a);
                    troncon.add(d);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());

                    troncon = new ArrayList<>();
                    break;
                case 5:
                    assertEquals(chemin.getCout(), 1, 0);
                    troncon.add(f);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());

                    troncon = new ArrayList<>();
                    break;
                case 6:
                    assertEquals(chemin.getCout(), 1, 0);
                    troncon.add(g);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());

                    troncon = new ArrayList<>();
                    break;
                case 7:
                    assertEquals(chemin.getCout(), 2, 0);
                    troncon.add(g);
                    troncon.add(h);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());

                    troncon = new ArrayList<>();
                    break;
                case 8:
                    assertEquals(chemin.getCout(), 3, 0);
                    troncon.add(g);
                    troncon.add(h);
                    troncon.add(i);
                    assertEquals(chemin.getTroncons().containsAll(troncon), true);
                    assertEquals(chemin.getTroncons().size(), troncon.size());

                    troncon = new ArrayList<>();
                    break;
            }

        }
    }

	@Test
	public void livraisonsOperation() {
		Fenetre f = new Fenetre(10, 20);
		f.ajouterLivraison(new Livraison(1, 1, 1));
		f.ajouterLivraison(new Livraison(2, 2, 2));
		f.ajouterLivraison(new Livraison(3, 3, 3));
		
		assertEquals(f.getListeLivraisons().size(), 3);
		f.effacerLivraison(1);
		assertEquals(f.getListeLivraisons().size(), 2);
	}
	
}
