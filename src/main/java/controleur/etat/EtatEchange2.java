package controleur.etat;

import java.io.File;
import java.util.List;

import modele.donneesxml.Livraison;
import controleur.ControleurDonnees;
import controleur.commande.Commande;
import controleur.commande.CommandeEchangerLivraisons;
import controleur.commande.CommandeException;

/**
 * Le second état d'échange de livraisons
 */
public class EtatEchange2 implements EtatInterface {

    /** Le contrôleur de données */
    private ControleurDonnees donnees;
    
    /** L'identifiant de la première livraison */
    private int idLivraison1;

    /**
     * Le constructeur du deuxième état d'échange
     * @param controleurDonnees Le contrôleur de données
     * @param idLivraison1 L'identifiant de la première livraison, déterminé pendant le premier état d'échange
     */
    public EtatEchange2(ControleurDonnees controleurDonnees, int idLivraison1) {
        this.donnees = controleurDonnees;
        this.idLivraison1 = idLivraison1;
        donnees.notifierObservateursMessage("[ECHANGE] Veuillez choisir la deuxième livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour revenir au choix de la première livraison.");
    }

    @Override
    public EtatInterface clicSurLivraison(int livraisonId2) {

        Commande cmdEchanger = new CommandeEchangerLivraisons(donnees, idLivraison1, livraisonId2);
        try {
            cmdEchanger.executer();
            donnees.ajouterCommande(cmdEchanger);
            donnees.effacerCommandesARetablir();
            return new EtatEchange(donnees);
        } catch (CommandeException e) {
            donnees.notifierObservateursMessage(e.getMessage());
        }
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) {
        throw new RuntimeException("Cet état ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) {
        throw new RuntimeException("Cet état ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface clicSurPlan(int intersectionId) {
        // Il faut trouver si l'intersection est une livraison
    	
    	// Ignorer si c'etait l'entrepot
        if (donnees.getModele().getDemande().getEntrepot().getId() == intersectionId) {
            donnees.notifierObservateursMessage("[ECHANGE] Déplacement de l'entrepôt impossible. Veuillez choisir la deuxième livraison en cliquant sur le plan ou sur la liste à gauche. Clic droit pour revenir au choix de la première livraison.");
            return this;
        }
        List<List<Livraison>> livraisons = donnees.getModele().getLivraisonsTournee();
        for (List<Livraison> fenetre : livraisons) {
            for (Livraison l : fenetre) {
                if (l.getAdresse() == intersectionId) {
                    return clicSurLivraison(l.getId());
                }
            }
        }

        //return this; // TODO retour normal ou exception ?
        throw new RuntimeException("L'intersection n'est pas une livraison");
    }

    @Override
    public EtatInterface clicCalculerTournee() {
        throw new RuntimeException("Cet état ne permet pas de calculer la tournée");
    }

    @Override
    public EtatInterface clicDroit() {
        return new EtatEchange(donnees);
    }

}
