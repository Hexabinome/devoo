package controleur.commande;

import controleur.ControleurDonnees;
import modele.xmldata.Fenetre;
import modele.xmldata.Livraison;

/**
 * Répresente une commande d'ajout de livraisons
 */
public class CommandeAjouterLivraison implements Commande {


    private final ControleurDonnees controleurDonnees;
    private final int idLivraisonAvant;
    private final Livraison livraisonAjoutee;
    private final Fenetre fenetre;

    /**
     * Crée une nouvelle de commande d'ajout de livraison
     * @param idLivraisonAvant identifiant de la livraison qui se trouve avant celle qu'on veut ajouter
     * @param idIntersectionLivraison intersection où on veut ajouter la livraison
     *
     */
    public CommandeAjouterLivraison(ControleurDonnees controleurDonnees, int idLivraisonAvant, int idIntersectionLivraison) {
        this.controleurDonnees = controleurDonnees;
        this.idLivraisonAvant = idLivraisonAvant;
        this.fenetre = controleurDonnees.getModele().getDemande().getFenetreDeLivraison(idLivraisonAvant);
        this.livraisonAjoutee = new Livraison(controleurDonnees.getModele().getProchainCustomLivraisonId(),-1,idIntersectionLivraison);
    }

    @Override
    public boolean estAnnulable() {
        return true;
    }

    @Override
    public void executer() throws CommandeException {
        controleurDonnees.getModele().addLivraison(idLivraisonAvant,fenetre,livraisonAjoutee);
        controleurDonnees.notifyAllModelObserveurs();
        controleurDonnees.notifyAllAnnulerObserveurs(false);

        // TODO à completer
    }

    @Override
    public void annuler() {
        controleurDonnees.getModele().removeLivraison(livraisonAjoutee.getId());
        controleurDonnees.notifyAllModelObserveurs();
        controleurDonnees.notifyAllRetablirObserveurs(false);
        // TODO à completer
    }
}
