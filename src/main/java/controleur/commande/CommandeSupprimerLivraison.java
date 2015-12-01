package controleur.commande;

import controleur.ControleurDonnees;
import modele.xmldata.Fenetre;
import modele.xmldata.Livraison;

/**
 * Représente une commande de suppression de livraisons
 */
public class CommandeSupprimerLivraison extends CommandeAnnulable {

    /**
     * Le controleur de données
     */
    private final ControleurDonnees controleurDonnees;

    /**
     * L'identifiant de la livraison supprimée
     */
    private int idLivraisonSupprime;
    
    /**
     * L'identifiant de la livraison précédent la livraison supprimée
     */
    private int idLivraisonAvant;

    /**
     * Constructeur de la commande de suppression d'une livraison
     * @param controleurDonnees Le contrôleur de données
     * @param idLivraison L'identifiant de la livraison a supprimé
     */
    public CommandeSupprimerLivraison(ControleurDonnees controleurDonnees, int idLivraison) {
        this.controleurDonnees = controleurDonnees;
        this.idLivraisonSupprime = idLivraison;
    }

    @Override
    public boolean estAnnulable() {
        return true;
    }

    @Override
    public void executer() throws CommandeException {
        Livraison livraisonSupprimee = controleurDonnees.getModele().getDemande().identifierLivraison(idLivraisonSupprime);
        
    	if(livraisonSupprimee == null) {
    		throw new CommandeException("[SUPPRESSION] La livraison sélectionnée n'existe pas.");
    	}
    	
    	if (livraisonSupprimee.getId() == -1) {
    		throw new CommandeException("[SUPPRESSION] Il est interdit de supprimer l'entrepôt.");
    	}
    	
        // Vérifier qu'on a le droit de supprimer la livraison
        Fenetre f = controleurDonnees.getModele().getDemande().getFenetreDeLivraison(livraisonSupprimee.getId());
        if (f.getListeLivraisons().size() <= 1) {
            throw new CommandeException("[SUPPRESSION] Il est interdit de supprimer la dernière livraison dans une fenêtre.");
        }
        
        //Garder une copie de la modele
        super.backupModele(controleurDonnees.getModele());

        idLivraisonAvant = controleurDonnees.getModele().supprimerLivraison(livraisonSupprimee.getId());
        controleurDonnees.getModele().remplirHoraires();
        controleurDonnees.notifierObserveursModele();
        controleurDonnees.notifierObserveursAnnuler(false);
        controleurDonnees.notifierObserveursMessage(
        		String.format("[SUPPRESSION] La livraison %d, à l'adresse %d et pour le client %d a été supprimée avec succès !",
        				livraisonSupprimee.getId(),
        				livraisonSupprimee.getAdresse(),
        				livraisonSupprimee.getClientId())
        		);


        if (controleurDonnees.getHist().estVideCommandesARetablir()) {
            controleurDonnees.notifierObserveursRetablir(true);
        }
    }

    @Override
    public void annuler()
    {
        // controleurDonnees.getModele().ajouterLivraison(idLivraisonAvant, fenetreDeLaLivraison, livraisonSupprimee);
        controleurDonnees.setModele(super.getModelCopie());
        
        controleurDonnees.notifierObserveursModele();
        controleurDonnees.notifierObserveursRetablir(false);

        if (controleurDonnees.getHist().estVideCommandesAAnnuler()) {
            controleurDonnees.notifierObserveursAnnuler(true);
        }
    }

}
