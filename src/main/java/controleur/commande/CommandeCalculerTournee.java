package controleur.commande;

import controleur.ControleurDonnees;

/**
 *
 * @author Max Schiedermeier
 */
public class CommandeCalculerTournee extends CommandeNonAnnulable
{

    private final ControleurDonnees controleurDonnees;

    public CommandeCalculerTournee(ControleurDonnees controleurDonnees)
    {
        this.controleurDonnees = controleurDonnees;
    }

    @Override
    public void executer() throws CommandeException
    {
        //calculer la tournee
        controleurDonnees.getModele().calculerTournee();

        //notifier la vue que maintenant il y a un model qui on peut afficher / des horaires prevus
        controleurDonnees.notifierObserveursModele();

        //notifier la vue que maintenant on peut interagir avec les elements prinicpaux.
        controleurDonnees.notifierObserveursActivation(false);
        
        controleurDonnees.notifierObserveursMessage("Tournée calculée avec succès !");
    }

}
