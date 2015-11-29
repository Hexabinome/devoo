package controleur.commande;

import java.io.File;
import java.io.IOException;

import modele.persistance.DeserialiseurXML;
import modele.persistance.ExceptionXML;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import controleur.ControleurDonnees;

/**
 *
 * @author Max Schiedermeier
 */
public class CommandeChargerPlan extends CommandeNonAnnulable
{

    private final ControleurDonnees controleurDonnees;
    private final File planFichier;

    public CommandeChargerPlan(ControleurDonnees controleurDonnees, File plan)
    {
        this.controleurDonnees = controleurDonnees;
        planFichier = plan;
    }

    @Override
    public void executer() throws CommandeException
    {
        try {
            //remplacer plan qui est charge d'un nouveau plan (ssi le chargement du xml a reussi)
            controleurDonnees.setPlan(DeserialiseurXML.ouvrirPlanDeVille(planFichier));
            controleurDonnees.notifyAllActObserveurs(true);
            controleurDonnees.notifierLesObserveursDuPlan(); // notification des observeurs du plan
            controleurDonnees.notifyAllCalculerTourneeObserveurs(true);
            controleurDonnees.notifierAllMessageObserveurs(String.format("Plan de la ville (%s) chargé avec succès !", planFichier.getName()));
        }
        catch (JDOMException | IOException | SAXException | ExceptionXML ex) {
            throw new CommandeException(ex.getMessage());
        }
    }

}
