package controleur.etat;

import java.io.File;
import java.util.List;

import modele.xmldata.Livraison;
import controleur.ControleurDonnees;
import controleur.commande.Commande;
import controleur.commande.CommandeEchangerLivraisons;
import controleur.commande.CommandeException;

public class EtatEchange2 extends AbstractEtat {

	private ControleurDonnees donnees;
    private int idLivraison;

    public EtatEchange2(ControleurDonnees controleurDonnees, int idLivraison){
        this.donnees = controleurDonnees;
        this.idLivraison = idLivraison;
    }
	
    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
    	Commande cmdEchanger = new CommandeEchangerLivraisons(donnees, idLivraison, livraisonId);
    	try {
    		cmdEchanger.executer();
    	} catch (CommandeException e) {
    		// TODO message
    		e.printStackTrace();
    	}
    	
    	return new EtatEchange(donnees);
    }

    @Override
    public EtatInterface chargerPlan(File plan)
    {
    	throw new RuntimeException("Cet état ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons)
    {
    	throw new RuntimeException("Cet état ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
    	// Trouver si l'intersection est une livraison
    	List<List<Livraison>> livraisons = donnees.getModele().getLivraisonsTournee();
    	for (List<Livraison> fenetre : livraisons) {
    		for (Livraison l : fenetre) {
    			if (l.getAdresse() == intersectionId) {
                                //ignorer si c'estait l'entrepot
                                if(donnees.getModele().getDemande().getEntrepot().getId() == intersectionId)
                                {
                                    donnees.notifierAllMessageObserveurs("Deplacement de l'entrepot pas possible.");
                                    return this;
                                }
                            
                                donnees.notifierAllMessageObserveurs("Deuxieme livraison a ete identifie.");
    				Commande cmdEchanger = new CommandeEchangerLivraisons(donnees, idLivraison, l.getId());
    				try {
    					cmdEchanger.executer();
                                        donnees.notifyAllModelObserveurs();
                                        donnees.notifierAllMessageObserveurs("Les livraisons sont maintenant echange.");
    				} catch (CommandeException e) {
    					// TODO message
    					e.printStackTrace();
    				}
    				return new EtatEchange(donnees);
    			}
    		}
    	}
    	
    	throw new RuntimeException("L'intersection n'est pas une livraison");
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
    	throw new RuntimeException("Cet état ne permet pas de calculer la tournée");
    }
}
