package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import modele.persistance.DeserialiseurXML;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author Maxou
 */
class EtatInitial extends AbstractEtat
{

    private final ControleurDonnees controleurDonnees;

    public EtatInitial(ControleurDonnees controleurDonnees)
    {
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec la liste.");
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        //remplacer plan qui est charge d'un nouveau plan (ssi le chargement du xml a reussi)
        controleurDonnees.setPlan(DeserialiseurXML.ouvrirPlanDeVille(plan));
        return new EtatPlanCharge(controleurDonnees);
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws JDOMException, SAXException, ParseException, IOException
    {
        throw new RuntimeException("Cet etat ne permet pas de charger un fichier de livraison");
    }

    @Override
    public EtatInterface cliqueSurPlan(int x, int y)
    {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec le plan.");
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new RuntimeException("Cet etat ne permet pas d'calculer la tournee");
    }

}
