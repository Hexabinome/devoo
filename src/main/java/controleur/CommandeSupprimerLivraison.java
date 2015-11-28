package controleur;

/**
 * Représente une commande de suppression de livraisons
 */
public class CommandeSupprimerLivraison implements Commande {


    private final ControleurDonnees controleurDonnees;

    private final int idLivraison;

    public CommandeSupprimerLivraison(ControleurDonnees controleurDonnees, int idLivraison) {
        this.controleurDonnees = controleurDonnees;
        this.idLivraison = idLivraison;
    }

    @Override
    public boolean estAnnulable() {
        return true;
    }

    @Override
    public void executer() throws CommandException {

        controleurDonnees.getModele().removeLivraison(idLivraison);
        controleurDonnees.notifyAllAnnulerObserveurs(false);
        controleurDonnees.notifyAllModelObserveurs();

    }

    @Override
    public void annuler() {

    }
}
