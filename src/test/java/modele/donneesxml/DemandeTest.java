package modele.donneesxml;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 *
 * @author robinroyer
 */
public class DemandeTest {
    
       //-----------TEST  demande
        /*
            +Demande(Intersection entrepot, List<Fenetre> fenetres): ctor
            +getEntrepot(): Intersection
            +getFenetres(): Collection<Fenetre>
        */

	@Test
	public void calculerCheminsTest()
	{
		Fenetre fenetre0 = new Fenetre(1448105516, 1448105516);
		Fenetre fenetre1 = new Fenetre(1448105516, 1448105616);
		Fenetre fenetre2 = new Fenetre(1448105616, 1448105716);
		
		Troncon a = new Troncon("A", 1, 1, 2);
		Troncon b = new Troncon("B", 1, 1, 3);
		Troncon c = new Troncon("C", 1, 1, 4);
		Troncon d = new Troncon("D", 1, 1, 4);
		Troncon e = new Troncon("E", 1, 1, 4);  
		Troncon f = new Troncon("F", 1, 1, 5);
		Troncon g = new Troncon("G", 1, 1, 6);
		Troncon h = new Troncon("H", 1, 1, 7);
		Troncon i = new Troncon("I", 1, 1, 8);
		Troncon j = new Troncon("J", 1, 1, 9);
		Troncon k = new Troncon("K", 1, 1, 10);		
		Troncon l = new Troncon("L", 1, 1, 8);
		Troncon m = new Troncon("M", 1, 1, 11);
		Troncon n = new Troncon("N", 1, 1, 11);
		Troncon o = new Troncon("O", 1, 1, 9);
		Troncon p = new Troncon("P", 1, 1, 4);
		Troncon q = new Troncon("Q", 1, 1, 5);
		Troncon r = new Troncon("R", 1, 1, 1);
		
		Intersection i1 = new Intersection(1, 1, 1); 
		i1.ajouterTroncon(2, a);
		i1.ajouterTroncon(5, f);
		i1.ajouterTroncon(6, g);
		
		Intersection i2 = new Intersection(2, 1, 1); 
		i2.ajouterTroncon(3, b);
		i2.ajouterTroncon(4, d);
		
		Intersection i3 = new Intersection(3, 1, 1); 
		i3.ajouterTroncon(4, c);
		
		Intersection i4 = new Intersection(4, 1, 1);
		i4.ajouterTroncon(5, q);
		i4.ajouterTroncon(9, j);
		
		Intersection i5 = new Intersection(5, 1, 1);  
		i5.ajouterTroncon(4, e);
		i5.ajouterTroncon(1, r);
		
		Intersection i6 = new Intersection(6, 1, 1); 
		i6.ajouterTroncon(7, h);
		
		Intersection i7 = new Intersection(7, 1, 1); 
		i7.ajouterTroncon(8, i);

		Intersection i8 = new Intersection(8, 1, 1);
		
		Intersection i9 = new Intersection(9, 1, 1);
		i9.ajouterTroncon(4, p);
		i9.ajouterTroncon(10, k);
		i9.ajouterTroncon(11, n);
		
		Intersection i10 = new Intersection(10, 1, 1);
		i10.ajouterTroncon(11, m);
		i10.ajouterTroncon(9, l);
		
		Intersection i11 = new Intersection(11, 1, 1);
		i11.ajouterTroncon(9, o);

		PlanDeVille plan = new PlanDeVille();
		plan.ajouterInstersection(i1);
		plan.ajouterInstersection(i2);
		plan.ajouterInstersection(i3);
		plan.ajouterInstersection(i4);
		plan.ajouterInstersection(i5);
		plan.ajouterInstersection(i6);
		plan.ajouterInstersection(i7);
		plan.ajouterInstersection(i8);
		plan.ajouterInstersection(i9);
		plan.ajouterInstersection(i10);
		plan.ajouterInstersection(i11);
		
		fenetre0.ajouterLivraison(1, new Livraison(101, 201, 1));
		
		fenetre1.ajouterLivraison(2, new Livraison(102, 202, 2));
		fenetre1.ajouterLivraison(4, new Livraison(104, 204, 4));
		fenetre1.ajouterLivraison(5, new Livraison(103, 203, 5));
		
		fenetre2.ajouterLivraison(10, new Livraison(105, 205, 10));
		fenetre2.ajouterLivraison(11, new Livraison(106, 206, 11));
		
		GrapheRealisation graphe = new GrapheRealisation(6);
		
		ArrayList<Fenetre> fenetres = new ArrayList<Fenetre>();
		fenetres.add(fenetre0);
		fenetres.add(fenetre1);
		fenetres.add(fenetre2);
		
		Demande demande = new Demande(i1, fenetres);

		graphe = demande.creerGraphe(plan);

		ArrayList<Troncon> troncon = new ArrayList<>();

		assertEquals(graphe.getChemin(101, 102).getCout(), 1, 0);
		troncon.add(a);
		assertEquals(graphe.getChemin(101, 102).getTroncons().containsAll(troncon), true);
		assertEquals(graphe.getChemin(101, 102).getTroncons().size(), troncon.size());
		
		troncon = new ArrayList<>();

		assertEquals(graphe.getChemin(101, 105).getCout(), 1, 0);
		troncon.add(f);
		assertEquals(graphe.getChemin(101, 105).getTroncons().containsAll(troncon), true);
		assertEquals(graphe.getChemin(101, 105).getTroncons().size(), troncon.size());
		
		troncon = new ArrayList<>();
		
		assertEquals(graphe.getChemin(102, 104).getCout(), 1, 0);
		troncon.add(d);
		assertEquals(graphe.getChemin(102, 104).getTroncons().containsAll(troncon), true);
		assertEquals(graphe.getChemin(102, 104).getTroncons().size(), troncon.size());
		
		troncon = new ArrayList<>();
		
		assertEquals(graphe.getChemin(104, 1010).getCout(), 2, 0);
		troncon.add(j);
		troncon.add(k);
		assertEquals(graphe.getChemin(104, 1010).getTroncons().containsAll(troncon), true);
		assertEquals(graphe.getChemin(104, 1010).getTroncons().size(), troncon.size());
		
		troncon = new ArrayList<>();
		
		assertEquals(graphe.getChemin(104, 105).getCout(), 1, 0);
		troncon.add(q);
		assertEquals(graphe.getChemin(104, 105).getTroncons().containsAll(troncon), true);
		assertEquals(graphe.getChemin(104, 105).getTroncons().size(), troncon.size());
		
		troncon = new ArrayList<>();
		
		assertEquals(graphe.getChemin(1011, 101).getCout(), 4, 0);
		troncon.add(o);
		troncon.add(p);
		troncon.add(q);
		troncon.add(r);
		assertEquals(graphe.getChemin(1011, 101).getTroncons().containsAll(troncon), true);
		assertEquals(graphe.getChemin(1011, 101).getTroncons().size(), troncon.size());
	}
}
