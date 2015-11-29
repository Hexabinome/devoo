package controleur.commande;

import controleur.ControleurDonnees;
import modele.xmldata.Fenetre;
import modele.xmldata.Livraison;

/**
 * Représente une commande de suppression de livraisons
 */
public class CommandeSupprimerLivraison implements Commande
{

    /**
     * Lien vers le controleur de données
     */
    private final ControleurDonnees controleurDonnees;

    /**
     * La livraison supprimée
     */
    private final Livraison livraisonSupprimee;

    /**
     * La fenetre dans laquelle la livraison se trouve
     */
    private final Fenetre fenetreDeLaLivraison;

    /**
     * Identifiant de la livraison qui se trouve
     */
    private int idLivraisonAvant;

    public CommandeSupprimerLivraison(ControleurDonnees controleurDonnees, int idLivraison)
    {
        this.controleurDonnees = controleurDonnees;
        this.livraisonSupprimee = controleurDonnees.getModele().getDemande().identifierLivraison(idLivraison);
        this.fenetreDeLaLivraison = controleurDonnees.getModele().getDemande().getFenetreDeLivraison(idLivraison);
    }

    @Override
    public boolean estAnnulable()
    {
        return true;
    }

    @Override
    public void executer() throws CommandeException
    {

    	if(livraisonSupprimee == null)
    	{
    		controleurDonnees.notifierAllMessageObserveurs("La livraison selectionné n'existe pas");
    		return;
    	}
    	
    	if (livraisonSupprimee.getId() == -1) {
    		controleurDonnees.notifierAllMessageObserveurs("Il est interdit de supprimer l'entrepôt.");
    		return;
    	}
    	
        // Vérifier qu'on a le droit de supprimer la livraison
        Fenetre f = controleurDonnees.getModele().getDemande().getFenetreDeLivraison(livraisonSupprimee.getId());
        if (f.getListeLivraisons().size() <= 1) {
            controleurDonnees.notifierAllMessageObserveurs("Il est interdit de supprimer la dernière livraison dans une fenêtre.");
        }

        idLivraisonAvant = controleurDonnees.getModele().removeLivraison(livraisonSupprimee.getId());
        controleurDonnees.getModele().remplirHoraires();
        controleurDonnees.notifyAllModelObserveurs();
        controleurDonnees.notifyAllAnnulerObserveurs(false);
        controleurDonnees.notifierAllMessageObserveurs(
        		String.format("La livraison %d, à l'adresse %d et pour le client %d a été supprimée avec succès !",
        				livraisonSupprimee.getId(),
        				livraisonSupprimee.getAdresse(),
        				livraisonSupprimee.getClientId())
        		);


        if (controleurDonnees.getHist().estVideCommandesARetablir()){
            controleurDonnees.notifyAllRetablirObserveurs(true);
        }
    }

    @Override
    public void annuler()
    {
        controleurDonnees.getModele().addLivraison(idLivraisonAvant, fenetreDeLaLivraison, livraisonSupprimee);
        controleurDonnees.notifyAllModelObserveurs();
        controleurDonnees.notifyAllRetablirObserveurs(false);

        if (controleurDonnees.getHist().estVideCommandesAAnnuler())
            controleurDonnees.notifyAllAnnulerObserveurs(true);
    }

}
