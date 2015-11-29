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
public class CommandeChargerDemande extends CommandeNonAnnulable
{

    private final ControleurDonnees controleurDonnees;
    private final File livraisonsFichier;

    public CommandeChargerDemande(ControleurDonnees controleurDonnees, File livraisons)
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
            controleurDonnees.notifyAllCalculerTourneeObserveurs(false);
            
            //notifier les observeurs que il y a un model maintenant
            controleurDonnees.notifyAllModelObserveurs();

            controleurDonnees.notifierAllMessageObserveurs(String.format("Demande de livraisons (%s) chargée avec succès !", livraisonsFichier.getName()));
        }
        catch (SAXException | ExceptionXML | IOException | JDOMException | ParseException ex) {
            throw new CommandeException(ex.getMessage());
        }
    }

}
