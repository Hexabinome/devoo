package controleur;

import modele.xmldata.Livraison;

/**
 * Représente une commande de suppression de livraisons
 */
public class CommandeSupprimerLivraison implements Commande {


    private final ControleurDonnees controleurDonnees;

    private final Livraison livraisonSupprimee;

    public CommandeSupprimerLivraison(ControleurDonnees controleurDonnees, int idLivraison) {
        this.controleurDonnees = controleurDonnees;
        livraisonSupprimee = this.controleurDonnees.getModele().getDemande().identifierLivraison(idLivraison);

    }

    @Override
    public boolean estAnnulable() {
        return true;
    }

    @Override
    public void executer() throws CommandException {

        controleurDonnees.getModele().removeLivraison(livraisonSupprimee.getId());
        controleurDonnees.notifyAllAnnulerObserveurs(false);
        controleurDonnees.notifyAllModelObserveurs();

    }

    @Override
    public void annuler() {
        // TODO : appeler le modele pour rajouter la livraison et ne pas calculer toute la tournee
    }
}
