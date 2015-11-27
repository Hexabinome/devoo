package controleur;

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
    public void executer() throws CommandException
    {
        //calculer la tournee
        controleurDonnees.getModele().calculerTournee();

        //notifier la vue que maintenant il y a un model qui on peut afficher / des horaires prevus
        controleurDonnees.notifyAllModelObserveurs();

        //notifier la vue que maintenant on peut interagir avec les elements prinicpaux.
        controleurDonnees.notifyAllActObserveurs(false);
    }

}
