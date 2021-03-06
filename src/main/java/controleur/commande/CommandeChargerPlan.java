package controleur.commande;

import java.io.File;
import java.io.IOException;

import modele.persistance.DeserialiseurXML;
import modele.persistance.ExceptionXML;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import controleur.ControleurDonnees;

/**
 * La commande de chargement d'un plan
 * @author Maxou
 */
public class CommandeChargerPlan extends CommandeNonAnnulable {

    /**
     * Le contrôleur de données
     */
    private final ControleurDonnees controleurDonnees;
    
    /**
     * Le fichier de plan
     */
    private final File planFichier;

    /**
     * Le constructeur du chargement du plan
     * @param controleurDonnees Le contrôleur de données
     * @param plan Le fichier de plan
     */
    public CommandeChargerPlan(ControleurDonnees controleurDonnees, File plan) {
        this.controleurDonnees = controleurDonnees;
        planFichier = plan;
    }

    @Override
    public void executer() throws CommandeException {
        try {
            // Remplacer plan qui est chargé d'un nouveau plan (si et seulement le chargement du xml a reussi)
            controleurDonnees.setPlan(DeserialiseurXML.getInstance().ouvrirPlanDeVille(planFichier));

            controleurDonnees.notifierObservateursActivation(false);
            controleurDonnees.notifierObservateurOuvrirDemande(true);
            controleurDonnees.notifierObservateursCalculTournee(false);
            controleurDonnees.notifierPlanChargeObservateur();
            controleurDonnees.notifierObservateursMessage(String.format("Plan de la ville (%s) chargé avec succès ! Veuillez charger la demande de livraison maintenant.", planFichier.getName()));
            controleurDonnees.effacerHistorique();
        } catch (JDOMException | IOException | SAXException | ExceptionXML ex) {
            throw new CommandeException(ex.getMessage());
        }
    }

}
