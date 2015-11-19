/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author maex
 */
public interface EtatInterface
{
    public void cliqueSurPlan(int x, int y);

    public void cliqueSurListItem(int livraisonId);

    public boolean cliqueAnnuler();

    public boolean cliqueRetablir();

    public PlanDeVille chargerPlan(File plan) throws JDOMException, SAXException, IOException;

    public Model chargerLivraisons(File livraisons, PlanDeVille plan) throws JDOMException, SAXException, ParseException, IOException;
}
