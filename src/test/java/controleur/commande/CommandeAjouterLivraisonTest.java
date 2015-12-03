package controleur.commande;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modele.donneesxml.Demande;
import modele.donneesxml.Fenetre;
import modele.donneesxml.Graphe;
import modele.donneesxml.GrapheRealisation;
import modele.donneesxml.Intersection;
import modele.donneesxml.Livraison;
import modele.donneesxml.Modele;
import modele.donneesxml.PlanDeVille;
import modele.donneesxml.Troncon;

import org.junit.Test;

import controleur.ControleurDonnees;

/**
 * Test d'exécution de l'ajout d'une livraison
 * @author David 
 */
public class CommandeAjouterLivraisonTest {
	
	@Test
	public void ajouterLivraisonGeneralTest() {
		
		Map<Integer, Intersection> intersections = new HashMap<Integer, Intersection>();
		Intersection i1 = new Intersection(1, 0, 0);
		i1.ajouterTroncon(2, new Troncon("rue1", 5, 10, 2));
		Intersection i2 = new Intersection(2, 10, 0);
		i2.ajouterTroncon(3, new Troncon("rue2", 5, 10, 3));
		Intersection i3 = new Intersection(1, 0, 0);
		i3.ajouterTroncon(4, new Troncon("rue3", 5, 10, 4));
		Intersection i4 = new Intersection(1, 0, 0);
		i4.ajouterTroncon(1, new Troncon("rue4", 5, 10, 1));
		intersections.put(1, i1);
		intersections.put(2, i2);
		intersections.put(3, i3);
		intersections.put(4, i4);
		PlanDeVille plan = new PlanDeVille(intersections);
		
		List<Fenetre> fenetres = new ArrayList<>();
		Fenetre f = new Fenetre(10, 100);
		f.ajouterLivraison(2, new Livraison(2, 2, 2));
		f.ajouterLivraison(3, new Livraison(3, 3, 3));
		fenetres.add(f);
		Demande demande = new Demande(new Intersection(1, 0, 0), fenetres);
		Modele modele = new Modele(plan, demande);
		GrapheRealisation graphe = new GrapheRealisation(4);
		graphe.setChemin(new Che, livraisonDepartId, livraisonArriveeId);
		modele.setGraphe(graphe);
		modele.calculerTournee();
		
		ControleurDonnees donnees = new ControleurDonnees();
		donnees.setPlan(plan);
		donnees.setModele(modele);

		CommandeAjouterLivraison cmdAjout = new CommandeAjouterLivraison(donnees, 3, 4);
		try {
			cmdAjout.executer();
		} catch (CommandeException e) {
			fail(e.getMessage());
		}
		
		assertEquals(3, donnees.getModele().getLivraisonsTournee().size()); // Toujours 3 fenêtres (entrepot + fenetre 1 + retour)
		Livraison l1 = donnees.getModele().getLivraisonsTournee().get(0).get(0);
		assertEquals(2, l1.getId());
		Livraison l2 = donnees.getModele().getLivraisonsTournee().get(0).get(1);
		assertEquals(3, l2.getId());
		Livraison l3 = donnees.getModele().getLivraisonsTournee().get(0).get(2);
		assertEquals(4, l3.getId());
	}

}
