package controleur.commande;

import controleur.ControleurDonnees;

/**
 * La commande de calcul de tournée
 * @author Max Schiedermeier
 */
public class CommandeCalculerTournee extends CommandeNonAnnulable {

    /**
     * Le contrôleur de données
     */
    private final ControleurDonnees controleurDonnees;

    /**
     * Constructeur de la commande de calcul de tournée
     * @param controleurDonnees Le contrôleur de données
     */
    public CommandeCalculerTournee(ControleurDonnees controleurDonnees) {
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public void executer() throws CommandeException {
        // Calcul de la tournée
        controleurDonnees.getModele().calculerTournee();

        // Notifier la vue que maintenant il y a un modèle qu'on peut afficher / des horaires prevus
        controleurDonnees.notifierObservateursModele();

        // Notifier la vue que maintenant on peut interagir avec les elements prinicpaux.
        controleurDonnees.notifierObservateursActivation(false);
        
        controleurDonnees.notifierObservateursMessage("Tournée calculée avec succès !");
    }

}
