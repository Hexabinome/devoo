package controleur.etat;


import java.io.File;
import java.util.List;

import modele.xmldata.Livraison;
import controleur.ControleurDonnees;
import controleur.commande.CommandeException;

/**
 *
 * @author Maxou
 */
public class EtatEchange extends AbstractEtat
{

    private final ControleurDonnees donnees;

    public EtatEchange(ControleurDonnees donnees)
    {
        this.donnees = donnees;
        donnees.notifierObserveursChargementDuPlan(false);
        donnees.notifierObserveurOuvrirDemande(false);
        this.donnees.notifierObserveursMessage("[ECHANGE] Souhaitez-vous échanger deux livraisons ? Veuillez choisir la première livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour sortir du mode d'échange.");
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
    	return new EtatEchange2(donnees, livraisonId);
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
    	throw new RuntimeException("Cet état ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException
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
                    // Ignorer si c'est l'entrepot
                    if(donnees.getModele().getDemande().getEntrepot().getId() == intersectionId) {
                        donnees.notifierObserveursMessage("[ECHANGE] Déplacement de l'entrepôt impossible. Veuillez choisir la première livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour sortir du mode d'échange.");
                        return this;
                    }
                            
    				return new EtatEchange2(donnees, l.getId());
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
        donnees.notifierObserveursMessage(TEXTE_ETAT_PRINCIPAL);
        return new EtatPrincipal(donnees);
    }

}
