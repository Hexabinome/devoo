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
        donnees.notifierAllMessageObserveurs("[ECHANGE] Veuillez choisir la deuxième livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour revenir au choix de la première livraison.");
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
		donnees.ajouterCommande(cmdEchanger);
		donnees.effacerCommandesARetablir();

    	EtatInterface nouvelEtat = new EtatEchange(donnees);
    	donnees.notifierAllMessageObserveurs(String.format("[ECHANGE] Les livraisons %d <-> %d ont été échangées avec succès. Veuillez choisir la première livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour revenir au choix de la première livraison.", idLivraison, livraisonId));
    	return nouvelEtat;
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
	                if(donnees.getModele().getDemande().getEntrepot().getId() == intersectionId) {
	                	donnees.notifierAllMessageObserveurs("[ECHANGE] Déplacement de l'entrepôt impossible. Veuillez choisir la deuxième livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour revenir au choix de la première livraison.");
	                    return this;
	                }
                            
    				Commande cmdEchanger = new CommandeEchangerLivraisons(donnees, idLivraison, l.getId());
    				try {
    					cmdEchanger.executer();
                        donnees.ajouterCommande(cmdEchanger);
						donnees.effacerCommandesARetablir();
    				} catch (CommandeException e) {
    					// TODO message
    					e.printStackTrace();
    				}
    				EtatInterface nouvelEtat = new EtatEchange(donnees);
    				donnees.notifierAllMessageObserveurs(String.format("[ECHANGE] Les livraisons %d <-> %d ont été échangées avec succès. Veuillez choisir la première livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour sortir au mode d'échange.", idLivraison, l.getId()));
    				return nouvelEtat;
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

	@Override
	public EtatInterface clicDroit() {
		return new EtatEchange(donnees);
	}
}
