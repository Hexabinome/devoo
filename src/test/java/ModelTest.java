/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import modele.persistance.DeserialiseurXML;
import modele.xmldata.Chemin;
import modele.xmldata.Demande;
import modele.xmldata.Fenetre;
import modele.xmldata.GrapheRealisation;
import modele.xmldata.Intersection;
import modele.xmldata.Livraison;
import modele.xmldata.Model;
import modele.xmldata.ModelLecture;
import modele.xmldata.PlanDeVille;
import modele.xmldata.Troncon;

import org.jdom2.JDOMException;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author robinroyer
 */
public class ModelTest {
	  @Test
	  public void TestModelPlan() throws JDOMException, IOException, SAXException, ParseException{
	    // initialisation
	    PlanDeVille ville = DeserialiseurXML.ouvrirPlanDeVille(ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
	    Demande demande = DeserialiseurXML.ouvrirLivraison(ClassLoader.getSystemResourceAsStream("samples/livraison10x10-1.xml"), ville);
	    ModelLecture monModel = new Model(ville, demande);

	    //test
	    assertEquals("Le model doit avoir le plan de ville obtenu a la lecture xml", ville, monModel.getPlan());
	    assertEquals("Le model doit avoir les livraisons obtenus a la lecture xml", demande, monModel.getDemande());
	  }
	  
	  @Test
	  public void TestCalculerTournee() 
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
			i1.addTroncon(2, a);
			i1.addTroncon(5, f);
			i1.addTroncon(6, g);
			
			Intersection i2 = new Intersection(2, 1, 1); 
			i2.addTroncon(3, b);
			i2.addTroncon(4, d);
			
			Intersection i3 = new Intersection(3, 1, 1); 
			i3.addTroncon(4, c);
			
			Intersection i4 = new Intersection(4, 1, 1);
			i4.addTroncon(5, q);
			i4.addTroncon(9, j);
			
			Intersection i5 = new Intersection(5, 1, 1);  
			i5.addTroncon(4, e);
			i5.addTroncon(1, r);
			
			Intersection i6 = new Intersection(6, 1, 1); 
			i6.addTroncon(7, h);
			
			Intersection i7 = new Intersection(7, 1, 1); 
			i7.addTroncon(8, i);

			Intersection i8 = new Intersection(8, 1, 1);
			
			Intersection i9 = new Intersection(9, 1, 1);
			i9.addTroncon(4, p);
			i9.addTroncon(10, k);
			i9.addTroncon(11, n);
			
			Intersection i10 = new Intersection(10, 1, 1);
			i10.addTroncon(11, m);
			i10.addTroncon(9, l);
			
			Intersection i11 = new Intersection(11, 1, 1);
			i11.addTroncon(9, o);

			PlanDeVille plan = new PlanDeVille();
			plan.addInstersection(i1);
			plan.addInstersection(i2);
			plan.addInstersection(i3);
			plan.addInstersection(i4);
			plan.addInstersection(i5);
			plan.addInstersection(i6);
			plan.addInstersection(i7);
			plan.addInstersection(i8);
			plan.addInstersection(i9);
			plan.addInstersection(i10);
			plan.addInstersection(i11);
			
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
			
			Model monModel = new Model(plan, demande);
			
			monModel.calculerTournee();
			List<Troncon> troncons = new ArrayList<Troncon>();
			/*
			for(Chemin chemin :chemins)
			{
				troncons.addAll(chemin.getTroncons());
			}*/
			System.out.println(troncons);
	  }
}

