package controleur.commande;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.donneesxml.Demande;
import modele.donneesxml.Fenetre;
import modele.donneesxml.GrapheRealisation;
import modele.donneesxml.Intersection;
import modele.donneesxml.Livraison;
import modele.donneesxml.Modele;
import modele.donneesxml.PlanDeVille;
import modele.donneesxml.Troncon;

import org.junit.Test;

import controleur.ControleurDonnees;

/**
 */
public class CommandeEchangerLivraisonsTest {
	
	@Test
	public void echangeDeuxSuccesivesLivraisonsTest() {
		// *** Mise en place du plan, demande, tournée... ***
		Map<Integer, Intersection> intersections = new HashMap<Integer, Intersection>();
		Intersection i1 = new Intersection(1, 0, 0);
		i1.ajouterTroncon(new Troncon("rue1", 5, 10, 2));
		Intersection i2 = new Intersection(2, 10, 0);
		i2.ajouterTroncon(new Troncon("rue2", 5, 10, 3));
		Intersection i3 = new Intersection(3, 0, 0);
		i3.ajouterTroncon(new Troncon("rue3", 5, 10, 4));
		Intersection i4 = new Intersection(4, 0, 0);
		i4.ajouterTroncon(new Troncon("rue4", 5, 10, 1));
		intersections.put(1, i1);
		intersections.put(2, i2);
		intersections.put(3, i3);
		intersections.put(4, i4);
		PlanDeVille plan = new PlanDeVille(intersections);
		
		List<Fenetre> fenetres = new ArrayList<>();
		Fenetre f = new Fenetre(10, 100);
		f.ajouterLivraison(new Livraison(2, 2, 2));
		f.ajouterLivraison(new Livraison(3, 3, 3));
		fenetres.add(f);
		Demande demande = new Demande(new Intersection(1, 0, 0), fenetres);
		Modele modele = new Modele(plan, demande);
		GrapheRealisation graphe = new GrapheRealisation(4);
		modele.setGraphe(graphe);
		modele.calculerTournee();
		
		ControleurDonnees donnees = new ControleurDonnees();
		donnees.setPlan(plan);
		donnees.setModele(modele);
		
		// *** Vérification que tout est bien chargé ***
		verificationOrigineDeuxSuccessives(donnees.getModele().getLivraisonsTournee());

		// *** Exécution de la commande échange ***
		CommandeEchangerLivraisons cmdEchange = new CommandeEchangerLivraisons(donnees, 2, 3);
		try {
			cmdEchange.executer();
		} catch (CommandeException e) {
			fail(e.getMessage());
		}
		
		// *** Vérification ***
		verificationEchangeDeuxSuccessives(donnees.getModele().getLivraisonsTournee());

		// ** Tente 3 annulation/rétablissement **
		for (int i = 0; i < 3; ++i) {
			// *** Annulation ***
			cmdEchange.annuler();
			
			// *** Vérification ***
			verificationOrigineDeuxSuccessives(donnees.getModele().getLivraisonsTournee());
			
			// *** Refaire ***
			try {
				cmdEchange.executer();
			} catch (CommandeException e) {
				fail(String.format("Refaire #%d échange a échoué : %s", i, e.getMessage()));
			}
			
			// *** Vérification ***
			verificationEchangeDeuxSuccessives(donnees.getModele().getLivraisonsTournee());
		}
	}
	
	private void verificationOrigineDeuxSuccessives(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(2, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertEquals(2, l1.getId());
		Livraison l2 = livraisonsTournee.get(1).get(1);
		assertEquals(3, l2.getId());

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}
	
	private void verificationEchangeDeuxSuccessives(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(1, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertTrue(l1.getId() != 2 && l1.getId() != 3);
		Livraison l2 = livraisonsTournee.get(1).get(1);
		assertTrue(l2.getId() != 2 && l2.getId() != 3);

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}

	@Test
	public void echangeDeuxLivraisonsDontPremiereTest () {
		// *** Mise en place du plan, demande, tournée... ***
		Map<Integer, Intersection> intersections = new HashMap<Integer, Intersection>();
		Intersection i1 = new Intersection(1, 0, 0);
		i1.ajouterTroncon(new Troncon("rue1", 5, 10, 2));
		Intersection i2 = new Intersection(2, 10, 0);
		i2.ajouterTroncon(new Troncon("rue2", 5, 10, 3));
		Intersection i3 = new Intersection(3, 0, 0);
		i3.ajouterTroncon(new Troncon("rue3", 5, 10, 4));
		Intersection i4 = new Intersection(4, 0, 0);
		i4.ajouterTroncon(new Troncon("rue4", 5, 10, 1));
		intersections.put(1, i1);
		intersections.put(2, i2);
		intersections.put(3, i3);
		intersections.put(4, i4);
		PlanDeVille plan = new PlanDeVille(intersections);
		
		List<Fenetre> fenetres = new ArrayList<>();
		Fenetre f = new Fenetre(10, 100);
		f.ajouterLivraison(new Livraison(2, 2, 2));
		f.ajouterLivraison(new Livraison(3, 3, 3));
		f.ajouterLivraison(new Livraison(4, 4, 4));
		fenetres.add(f);
		Demande demande = new Demande(new Intersection(1, 0, 0), fenetres);
		Modele modele = new Modele(plan, demande);
		GrapheRealisation graphe = new GrapheRealisation(4);
		modele.setGraphe(graphe);
		modele.calculerTournee();
		
		ControleurDonnees donnees = new ControleurDonnees();
		donnees.setPlan(plan);
		donnees.setModele(modele);
		
		// *** Vérification que tout est bien chargé ***
		verificationOrigineDontPremiere(donnees.getModele().getLivraisonsTournee());

		// *** Exécution de la commande échange ***
		CommandeEchangerLivraisons cmdEchange = new CommandeEchangerLivraisons(donnees, 2, 4);
		try {
			cmdEchange.executer();
		} catch (CommandeException e) {
			fail(e.getMessage());
		}
		
		// *** Vérification ***
		verificationEchangeDontPremiere(donnees.getModele().getLivraisonsTournee());

		// ** Tente 3 annulation/rétablissement **
		for (int i = 0; i < 3; ++i) {
			// *** Annulation ***
			cmdEchange.annuler();
			
			// *** Vérification ***
			verificationOrigineDontPremiere(donnees.getModele().getLivraisonsTournee());
			
			// *** Refaire ***
			try {
				cmdEchange.executer();
			} catch (CommandeException e) {
				fail(String.format("Refaire #%d échange a échoué : %s", i, e.getMessage()));
			}
			
			// *** Vérification ***
			verificationEchangeDontPremiere(donnees.getModele().getLivraisonsTournee());
		}
	}
	private void verificationOrigineDontPremiere(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(3, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertEquals(2, l1.getId());
		Livraison l2 = livraisonsTournee.get(1).get(1);
		assertEquals(3, l2.getId());
		Livraison l3 = livraisonsTournee.get(1).get(2);
		assertEquals(4, l3.getId());

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}
	
	private void verificationEchangeDontPremiere(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(3, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertTrue(l1.getId() != 4 && l1.getId() != 2);
		Livraison l2 = livraisonsTournee.get(1).get(1);
		assertEquals(3, l2.getId());
		Livraison l3 = livraisonsTournee.get(1).get(2);
		assertTrue(l3.getId() != 4 && l3.getId() != 2);

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}

	@Test
	public void echangeDeuxLivraisonsGeneralTest () {
		// *** Mise en place du plan, demande, tournée... ***
		Map<Integer, Intersection> intersections = new HashMap<Integer, Intersection>();
		Intersection i1 = new Intersection(1, 0, 0);
		i1.ajouterTroncon(new Troncon("rue1", 5, 10, 2));
		Intersection i2 = new Intersection(2, 10, 0);
		i2.ajouterTroncon(new Troncon("rue2", 5, 10, 3));
		Intersection i3 = new Intersection(3, 0, 10);
		i3.ajouterTroncon(new Troncon("rue3", 5, 10, 4));
		Intersection i4 = new Intersection(4, 10, 10);
		i4.ajouterTroncon(new Troncon("rue4", 5, 10, 1));
		i4.ajouterTroncon(new Troncon("rue5", 5, 10, 5));
		Intersection i5 = new Intersection(5, 20, 20);
		i5.ajouterTroncon(new Troncon("rue5", 5, 10, 4));
		intersections.put(1, i1);
		intersections.put(2, i2);
		intersections.put(3, i3);
		intersections.put(4, i4);
		intersections.put(5, i5);
		PlanDeVille plan = new PlanDeVille(intersections);
		
		List<Fenetre> fenetres = new ArrayList<>();
		Fenetre f = new Fenetre(10, 100);
		f.ajouterLivraison(new Livraison(2, 2, 2));
		f.ajouterLivraison(new Livraison(3, 3, 3));
		f.ajouterLivraison(new Livraison(4, 4, 4));
		f.ajouterLivraison(new Livraison(5, 5, 5));
		fenetres.add(f);
		Demande demande = new Demande(new Intersection(1, 0, 0), fenetres);
		Modele modele = new Modele(plan, demande);
		GrapheRealisation graphe = new GrapheRealisation(5);
		modele.setGraphe(graphe);
		modele.calculerTournee();
		
		ControleurDonnees donnees = new ControleurDonnees();
		donnees.setPlan(plan);
		donnees.setModele(modele);
		
		// *** Vérification que tout est bien chargé ***
		verificationOrigine(donnees.getModele().getLivraisonsTournee());

		// *** Exécution de la commande échange ***
		CommandeEchangerLivraisons cmdEchange = new CommandeEchangerLivraisons(donnees, 3, 5);
		try {
			cmdEchange.executer();
		} catch (CommandeException e) {
			fail(e.getMessage());
		}
		
		// *** Vérification ***
		verificationEchange(donnees.getModele().getLivraisonsTournee());

		// ** Tente 3 annulation/rétablissement **
		for (int i = 0; i < 3; ++i) {
			// *** Annulation ***
			cmdEchange.annuler();
			
			// *** Vérification ***
			verificationOrigine(donnees.getModele().getLivraisonsTournee());
			
			// *** Refaire ***
			try {
				cmdEchange.executer();
			} catch (CommandeException e) {
				fail(String.format("Refaire #%d échange a échoué : %s", i, e.getMessage()));
			}
			
			// *** Vérification ***
			verificationEchange(donnees.getModele().getLivraisonsTournee());
		}
	}
	private void verificationOrigine(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(4, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertEquals(2, l1.getId());
		Livraison l2 = livraisonsTournee.get(1).get(1);
		assertEquals(3, l2.getId());
		Livraison l3 = livraisonsTournee.get(1).get(2);
		assertEquals(4, l3.getId());
		Livraison l4 = livraisonsTournee.get(1).get(3);
		assertEquals(5, l4.getId());

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}
	
	private void verificationEchange(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(4, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertEquals(2, l1.getId());
		Livraison l2 = livraisonsTournee.get(1).get(1);
		assertTrue(l2.getId() != 3 && l2.getId() != 5);
		Livraison l3 = livraisonsTournee.get(1).get(2);
		assertEquals(4, l3.getId());
		Livraison l4 = livraisonsTournee.get(1).get(3);
		assertTrue(l4.getId() != 3 && l4.getId() != 5);

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}
	
}
