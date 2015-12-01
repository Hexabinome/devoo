package controleur.etat;


import java.io.File;
import java.util.List;

import modele.xmldata.Livraison;
import controleur.ControleurDonnees;
import controleur.commande.CommandeException;

/**
 * Le premier état de la phase d'échange de livraisons
 * @author Maxou
 */
public class EtatEchange extends AbstractEtat {

    /** Le contôleur de données */
    private final ControleurDonnees donnees;

    /**
     * Le constructeur du premier état d'échange
     * @param donnees Le contrôleur de données
     */
    public EtatEchange(ControleurDonnees donnees) {
        this.donnees = donnees;
        donnees.notifierObserveursOuvrirPlan(false);
        donnees.notifierObserveurOuvrirDemande(false);
        this.donnees.notifierObserveursMessage("[ECHANGE] Souhaitez-vous échanger deux livraisons ? Veuillez choisir la première livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour sortir du mode d'échange.");
    }

    @Override
    public EtatInterface clicSurLivraison(int livraisonId) {
    	return new EtatEchange2(donnees, livraisonId);
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException {
    	throw new RuntimeException("Cet état ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException {
    	throw new RuntimeException("Cet état ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface clicSurPlan(int intersectionId) {
    	// Il faut trouver si l'intersection est une livraison
    	
    	// Ignorer si c'est l'entrepot
        if (donnees.getModele().getDemande().getEntrepot().getId() == intersectionId) {
            donnees.notifierObserveursMessage("[ECHANGE] Déplacement de l'entrepôt impossible. Veuillez choisir la première livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour sortir du mode d'échange.");
            return this;
        }
    	
    	List<List<Livraison>> livraisons = donnees.getModele().getLivraisonsTournee();
    	// Pour chaque livraison dans chaque fenêtre, si l'adresse est l'intersection, alors nous avons cliqué sur cette livraison
    	for (List<Livraison> fenetre : livraisons) {
    		for (Livraison l : fenetre) {
    			if (l.getAdresse() == intersectionId) {
    				return new EtatEchange2(donnees, l.getId());
    			}
    		}
    	}

    	// Sinon on reste dans l'état actuel
    	//return this; // TODO reste dans l'état ou exception ?
        throw new RuntimeException("L'intersection n'est pas une livraison");
    }

    @Override
    public EtatInterface clicCalculerTournee() {
    	throw new RuntimeException("Cet état ne permet pas de calculer la tournée");
    }

    @Override
    public EtatInterface clicDroit() {
        donnees.notifierObserveursMessage(TEXTE_ETAT_PRINCIPAL);
        return new EtatPrincipal(donnees);
    }
    
}
