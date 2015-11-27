package controleur;

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
    public void executer() throws CommandException
    {
        try {
            PlanDeVille plan = controleurDonnees.getPlan();
            Demande demande = DeserialiseurXML.ouvrirLivraison(livraisonsFichier, plan);
            controleurDonnees.setModele(new Modele(plan, demande));

            //calculer la tournee
            controleurDonnees.getModele().calculerTournee();

            //notifier la vue que maintenant on peux interagir avec les elements prinicpaux.
            controleurDonnees.notifyAllActObserveurs(false);

            //notifier la vue que le modele a change
            controleurDonnees.notifyAllModelObserveurs();
        }
        catch (SAXException | ExceptionXML | IOException | JDOMException | ParseException ex) {
            throw new CommandException(ex.getMessage());
        }
    }

}