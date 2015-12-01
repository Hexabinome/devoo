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
    private final int idIntersectionLivraison;
    //private final Livraison livraisonAjoutee;
    //private final Fenetre fenetre;

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
        this.idIntersectionLivraison = idIntersectionLivraison;

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
        
        Fenetre fenetre = controleurDonnees.getModele().getDemande().getFenetreDeLivraison(idLivraisonAvant);
        Livraison livraisonAjoutee = new Livraison(controleurDonnees.getModele().getProchainIdCustomLivraison(fenetre), -1, idIntersectionLivraison);
        
        controleurDonnees.getModele().ajouterLivraison(idLivraisonAvant, fenetre, livraisonAjoutee);
        controleurDonnees.notifierObserveursModele();
        controleurDonnees.notifierObserveursAnnuler(false);

        if (controleurDonnees.getHist().estVideCommandesARetablir())
            controleurDonnees.notifierObserveursRetablir(true);
    }

    @Override
    public void annuler()
    {
        //controleurDonnees.getModele().supprimerLivraison(livraisonAjoutee.getId());
        controleurDonnees.setModele(super.getModelCopie());

        controleurDonnees.notifierObserveursModele();
        controleurDonnees.notifierObserveursRetablir(false);

        if (controleurDonnees.getHist().estVideCommandesAAnnuler())
            controleurDonnees.notifierObserveursAnnuler(true);
    }

}
