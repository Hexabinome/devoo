package controleur.commande;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import modele.donneesxml.Demande;
import modele.donneesxml.Modele;
import modele.donneesxml.PlanDeVille;
import modele.persistance.DeserialiseurXML;
import modele.persistance.ExceptionXML;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import controleur.ControleurDonnees;

/**
 * La commande de chargement de la demande
 * @author Max Schiedermeier
 */
public class CommandeChargerDemande extends CommandeNonAnnulable {

    /**
     * Le contrôleur de données
     */
    private final ControleurDonnees controleurDonnees;
    
    /**
     * Le fichier de livraion
     */
    private final File livraisonsFichier;

    /**
     * Constructeur de la commande de chargement de la demande
     * @param controleurDonnees Le contrôleur de données
     * @param livraisons Le fichier de demande de livraisons
     */
    public CommandeChargerDemande(ControleurDonnees controleurDonnees, File livraisons) {
        this.controleurDonnees = controleurDonnees;
        livraisonsFichier = livraisons;
    }

    @Override
    public void executer() throws CommandeException {
        try {
            PlanDeVille plan = controleurDonnees.getPlan();
            Demande demande = DeserialiseurXML.getInstance().ouvrirDemande(livraisonsFichier, plan);
            controleurDonnees.setModele(new Modele(plan, demande));

            // Permettre de calculer la tournee
            controleurDonnees.notifierObserveursCalculTournee(false);
            
            // Notifier les observeurs que il y a un model maintenant
            controleurDonnees.notifierObserveursModele();

            controleurDonnees.notifierObserveursMessage(String.format("Demande de livraisons (%s) chargée avec succès ! Veuillez calculer la tournée maintenant.", livraisonsFichier.getName()));
        } catch (SAXException | ExceptionXML | IOException | JDOMException | ParseException ex) {
            throw new CommandeException(ex.getMessage());
        }
    }
}
