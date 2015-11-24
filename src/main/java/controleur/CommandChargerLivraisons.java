package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import modele.persistance.DeserialiseurXML;
import modele.persistance.ExceptionXML;
import modele.xmldata.Demande;
import modele.xmldata.Model;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author Max Schiedermeier
 */
public class CommandChargerLivraisons extends UninvertibelCommand
{

    private final ControleurDonnees controleurDonnees;
    private final File livraisonsFichier;

    public CommandChargerLivraisons(ControleurDonnees controleurDonnees, File livraisons)
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
            controleurDonnees.setModel(new Model(plan, demande));

            //calculer la tournee
            controleurDonnees.getModel().calculerTournee();

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
