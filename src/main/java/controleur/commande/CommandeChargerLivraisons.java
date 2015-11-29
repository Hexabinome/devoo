package controleur.commande;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import modele.persistance.DeserialiseurXML;
import modele.persistance.ExceptionXML;
import modele.xmldata.Demande;
import modele.xmldata.Modele;
import modele.xmldata.PlanDeVille;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import controleur.ControleurDonnees;

/**
 *
 * @author Max Schiedermeier
 */
public class CommandeChargerLivraisons extends CommandeNonAnnulable
{

    private final ControleurDonnees controleurDonnees;
    private final File livraisonsFichier;

    public CommandeChargerLivraisons(ControleurDonnees controleurDonnees, File livraisons)
    {
        this.controleurDonnees = controleurDonnees;
        livraisonsFichier = livraisons;
    }

    @Override
    public void executer() throws CommandeException
    {
        try {
            PlanDeVille plan = controleurDonnees.getPlan();
            Demande demande = DeserialiseurXML.ouvrirLivraison(livraisonsFichier, plan);
            controleurDonnees.setModele(new Modele(plan, demande));

            //permettre de calculer la tournee
            controleurDonnees.notifyAllCalculerTourneeObserveurs(true);
            
            //notifier les observeurs que il y a un model maintenant
            controleurDonnees.notifyAllModelObserveurs();
        }
        catch (SAXException | ExceptionXML | IOException | JDOMException | ParseException ex) {
            throw new CommandeException(ex.getMessage());
        }
    }

}
