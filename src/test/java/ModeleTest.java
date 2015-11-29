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
import modele.persistance.ExceptionXML;
import modele.xmldata.Demande;
import modele.xmldata.Fenetre;
import modele.xmldata.Intersection;
import modele.xmldata.Livraison;
import modele.xmldata.Modele;
import modele.xmldata.ModeleLecture;
import modele.xmldata.PlanDeVille;
import modele.xmldata.Troncon;

import org.jdom2.JDOMException;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author robinroyer, maxou
 */
public class ModeleTest
{

    @Test
    public void TestModelPlan() throws JDOMException, IOException, SAXException, ParseException, ExceptionXML
    {
        // initialisation na parti des fichiers xml
        PlanDeVille ville = DeserialiseurXML.ouvrirPlanDeVille(ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        Demande demande = DeserialiseurXML.ouvrirLivraison(ClassLoader.getSystemResourceAsStream("samples/livraison10x10-1.xml"), ville);
        ModeleLecture monModel = new Modele(ville, demande);

        // tester contructeur du modele
        assertEquals("Le model doit avoir le plan de ville obtenu a la lecture xml", ville, monModel.getPlan());
        assertEquals("Le model doit avoir les livraisons obtenus a la lecture xml", demande, monModel.getDemande());

        // verifier que le modele cree correspod au contenu des fichiers xml
        // le fichier cintient qu'une seule fenetre. Quand meme on attend 2, car le modele doit creer une fenetre supplementaire pour l'entrepot.
        assertEquals("Modele cree a parti d'xml ne contient pas la bonne numero des fenetres.", 2, monModel.getDemande().getFenetres().size());
        assertEquals("Modele cree a parti d'xml ne contient pas la bonne numero des intersections.", 100, monModel.getPlan().getIntersections().size());
    }

    @Test
    public void TestCalculerTournee() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, JDOMException, IOException, SAXException, ExceptionXML, ParseException
    {
        // initialisation a parti des fichiers xml
        PlanDeVille ville = DeserialiseurXML.ouvrirPlanDeVille(ClassLoader.getSystemResourceAsStream("samples/plan10x10.xml"));
        Demande demande = DeserialiseurXML.ouvrirLivraison(ClassLoader.getSystemResourceAsStream("samples/livraison10x10-1.xml"), ville);
        Modele monModel = new Modele(ville, demande);

        //tester si on peut bien calculer la tournee
        monModel.calculerTournee();
    }

    @Test
    public void TestManipulerModele()
    {
        Fenetre fenetre1 = new Fenetre(30000, 40000);
        Fenetre fenetre2 = new Fenetre(50000, 60000);

        // param Troncon: nom, vitesse, taille, id-cible
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

        // param Intersection: id, x, y
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

        fenetre1.ajouterLivraison(102, new Livraison(102, 202, 2));
        fenetre1.ajouterLivraison(104, new Livraison(104, 204, 4));
        Livraison testLivraison = new Livraison(103, 203, 5);
        fenetre1.ajouterLivraison(103, testLivraison);
        fenetre2.ajouterLivraison(205, new Livraison(205, 205, 10));
        fenetre2.ajouterLivraison(206, new Livraison(206, 206, 11));

        ArrayList<Fenetre> fenetres = new ArrayList<>();
        fenetres.add(fenetre1);
        fenetres.add(fenetre2);

        Demande demande = new Demande(i1, fenetres);
        Modele monModele = new Modele(plan, demande);
        
        //creer la tournee et verifier qu'elle est bon
        monModele.calculerTournee();
        List<List<Integer>> tournee = monModele.getTournee();
        
        //on attend 3 sous listes: une pour chaque fenetre et une troisieme pour retourner a l'entrepot
        assertEquals("La tournee ne contient pas le bon valuer des sous-tournees", 3, tournee.size());
        
        //verifier que la tournee passe par l'intersection de la test-livraison 103 (intersection 5)
        assertEquals("", true, tournee.get(0).contains(5));
        
        // maintenant on essaye de manipuler le modele - on efface une livraison, on sait que sans cette livraison il n'est plus necessire de passer par la sa intersection.
        monModele.supprimerLivraison(testLivraison.getId());
        tournee = monModele.getTournee();
        assertEquals("La tounree ne devrait pas passer par l'intersection 5", false, tournee.get(0).contains(5));
    }

}
