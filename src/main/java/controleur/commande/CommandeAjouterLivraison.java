package controleur.commande;

import controleur.ControleurDonnees;
import modele.xmldata.Fenetre;
import modele.xmldata.Livraison;

/**
 * Répresente une commande d'ajout de livraisons
 */
public class CommandeAjouterLivraison extends CommandAnnulable
{

    private final ControleurDonnees controleurDonnees;
    private final int idLivraisonAvant;
    private final Livraison livraisonAjoutee;
    private final Fenetre fenetre;

    /**
     * Crée une nouvelle de commande d'ajout de livraison
     *
     * @param idLivraisonAvant identifiant de la livraison qui se trouve avant
     * celle qu'on veut ajouter
     * @param idIntersectionLivraison intersection où on veut ajouter la
     * livraison
     *
     */
    public CommandeAjouterLivraison(ControleurDonnees controleurDonnees, int idLivraisonAvant, int idIntersectionLivraison)
    {
        this.controleurDonnees = controleurDonnees;
        this.idLivraisonAvant = idLivraisonAvant;
        this.fenetre = controleurDonnees.getModele().getDemande().getFenetreDeLivraison(idLivraisonAvant);
        this.livraisonAjoutee = new Livraison(controleurDonnees.getModele().getProchainIdCustomLivraison(this.fenetre), -1, idIntersectionLivraison);
    }

    @Override
    public boolean estAnnulable()
    {
        return true;
    }

    @Override
    public void executer() throws CommandeException
    {
        //faire backup
        super.backupModele(controleurDonnees.getModele());
        
        controleurDonnees.getModele().ajouterLivraison(idLivraisonAvant, fenetre, livraisonAjoutee);
        controleurDonnees.notifyAllModelObserveurs();
        controleurDonnees.notifyAllAnnulerObserveurs(false);

        if (controleurDonnees.getHist().estVideCommandesARetablir())
            controleurDonnees.notifyAllRetablirObserveurs(true);
    }

    @Override
    public void annuler()
    {
        //controleurDonnees.getModele().supprimerLivraison(livraisonAjoutee.getId());
        controleurDonnees.setModele(super.getModelCopie());

        controleurDonnees.notifyAllModelObserveurs();
        controleurDonnees.notifyAllRetablirObserveurs(false);

        if (controleurDonnees.getHist().estVideCommandesAAnnuler())
            controleurDonnees.notifyAllAnnulerObserveurs(true);
    }

}
