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
        controleurDonnees.notifierObserveursModele();

        // Notifier la vue que maintenant on peut interagir avec les elements prinicpaux.
        controleurDonnees.notifierObserveursActivation(false);
        
        controleurDonnees.notifierObserveursMessage("Tournée calculée avec succès !");
    }

}
