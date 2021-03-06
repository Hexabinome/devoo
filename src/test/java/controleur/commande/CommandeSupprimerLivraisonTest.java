package controleur.commande;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import controleur.ControleurDonnees;
import modele.donneesxml.Demande;
import modele.donneesxml.Fenetre;
import modele.donneesxml.GrapheRealisation;
import modele.donneesxml.Intersection;
import modele.donneesxml.Livraison;
import modele.donneesxml.Modele;
import modele.donneesxml.PlanDeVille;
import modele.donneesxml.Troncon;

/**
 */
public class CommandeSupprimerLivraisonTest {
	
	private ControleurDonnees donnees = null;
	
	@Before
	public void miseEnPlace() {
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
		
		donnees = new ControleurDonnees();
		donnees.setPlan(plan);
		donnees.setModele(modele);
	}
	
	@Test
	public void supprimerLivraisonPremiereTest() {
		// *** Vérification que tout est bien chargé ***
		verificationOrigine(donnees.getModele().getLivraisonsTournee());

		// *** Exécution de la commande suppression ***
		CommandeSupprimerLivraison cmdSupp = new CommandeSupprimerLivraison(donnees, 2);
		try {
			cmdSupp.executer();
		} catch (CommandeException e) {
			fail(e.getMessage());
		}
		
		// *** Vérification ***
		verificationUneSuppressionPremiere(donnees.getModele().getLivraisonsTournee());

		// ** Tente 3 annulation/rétablissement **
		for (int i = 0; i < 3; ++i) {
			// *** Annulation ***
			cmdSupp.annuler();
			
			// *** Vérification ***
			verificationOrigine(donnees.getModele().getLivraisonsTournee());
			
			// *** Refaire ***
			try {
				cmdSupp.executer();
			} catch (CommandeException e) {
				fail(String.format("Refaire #%d suppression a échoué : %s", i, e.getMessage()));
			}
			
			// *** Vérification ***
			verificationUneSuppressionPremiere(donnees.getModele().getLivraisonsTournee());
		}
	}
	
	private void verificationUneSuppressionPremiere(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(1, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertEquals(3, l1.getId());

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}
	
	@Test
	public void supprimerLivraisonGeneralTest() {
		// *** Vérification que tout est bien chargé ***
		verificationOrigine(donnees.getModele().getLivraisonsTournee());

		// *** Exécution de la commande suppression ***
		CommandeSupprimerLivraison cmdSupp = new CommandeSupprimerLivraison(donnees, 3);
		try {
			cmdSupp.executer();
		} catch (CommandeException e) {
			fail(e.getMessage());
		}
		
		// *** Vérification ***
		verificationUneSuppression(donnees.getModele().getLivraisonsTournee());

		// ** Tente 3 annulation/rétablissement **
		for (int i = 0; i < 3; ++i) {
			// *** Annulation ***
			cmdSupp.annuler();
			
			// *** Vérification ***
			verificationOrigine(donnees.getModele().getLivraisonsTournee());
			
			// *** Refaire ***
			try {
				cmdSupp.executer();
			} catch (CommandeException e) {
				fail(String.format("Refaire #%d suppression a échoué : %s", i, e.getMessage()));
			}
			
			// *** Vérification ***
			verificationUneSuppression(donnees.getModele().getLivraisonsTournee());
		}
	}
	
	private void verificationOrigine(List<List<Livraison>> livraisonsTournee) {
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
	
	private void verificationUneSuppression(List<List<Livraison>> livraisonsTournee) {
		assertEquals(3, livraisonsTournee.size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		
		assertEquals(1, livraisonsTournee.get(0).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr = livraisonsTournee.get(0).get(0);
		assertEquals(-1, entr.getId());
		
		assertEquals(1, livraisonsTournee.get(1).size());
		Livraison l1 = livraisonsTournee.get(1).get(0);
		assertEquals(2, l1.getId());

		assertEquals(1, livraisonsTournee.get(2).size()); // 1 seule livraison dans la première fenêtre => entrepôt
		Livraison entr2 = livraisonsTournee.get(2).get(0);
		assertEquals(-1, entr2.getId());
	}
}
