package controleur.commande;

import controleur.ControleurDonnees;
import modele.donneesxml.Fenetre;
import modele.donneesxml.Livraison;

/**
 * Répresente une commande d'ajout de livraison
 */
public class CommandeAjouterLivraison extends CommandeAnnulable {

    /**
     * Le cotrôleur de données
     */
    private final ControleurDonnees controleurDonnees;
    
    /**
     * L'identifiant de la livraison avant la nouvelle livraison
     */
    private final int idLivraisonAvant;
    
    /**
     * L'identifiant de l'intersection de la nouvelle livraison
     */
    private final int idIntersectionLivraison;

    /**
     * Crée une nouvelle de commande d'ajout de livraison
     *
     * @param idLivraisonAvant identifiant de la livraison qui se trouve avant celle qu'on veut ajouter
     * @param idIntersectionLivraison intersection où on veut ajouter la livraison
     */
    public CommandeAjouterLivraison(ControleurDonnees controleurDonnees, int idLivraisonAvant, int idIntersectionLivraison) {
        this.controleurDonnees = controleurDonnees;
        this.idLivraisonAvant = idLivraisonAvant;
        this.idIntersectionLivraison = idIntersectionLivraison;
    }

    @Override
    public boolean estAnnulable() {
        return true;
    }

    @Override
    public void executer() throws CommandeException {
        // Faire backup
        super.backupModele(controleurDonnees.getModele());
        
        Fenetre fenetre = controleurDonnees.getModele().getDemande().getFenetreDeLivraison(idLivraisonAvant);
        Livraison livraisonAjoutee = new Livraison(controleurDonnees.getModele().getProchainIdCustomLivraison(fenetre), -1, idIntersectionLivraison);
        
        controleurDonnees.getModele().ajouterLivraison(idLivraisonAvant, fenetre, livraisonAjoutee);
        controleurDonnees.notifierObservateursModele();
        controleurDonnees.notifierObservateursAnnuler(false);

        if (controleurDonnees.getHist().estVideCommandesARetablir())
            controleurDonnees.notifierObservateursRetablir(true);
    }

    @Override
    public void annuler() {
        //controleurDonnees.getModele().supprimerLivraison(livraisonAjoutee.getId());
        controleurDonnees.setModele(super.getModelCopie());

        controleurDonnees.notifierObservateursModele();
        controleurDonnees.notifierObservateursRetablir(false);

        if (controleurDonnees.getHist().estVideCommandesAAnnuler()) {
            controleurDonnees.notifierObservateursAnnuler(true);
        }
    }

}
