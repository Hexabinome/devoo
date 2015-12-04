package controleur.etat;

import controleur.ControleurDonnees;
import controleur.commande.Commande;
import controleur.commande.CommandeAjouterLivraison;
import controleur.commande.CommandeException;

import java.io.File;

/**
 * Cet état correspond à l'état où l'on doit choisir la livraison qui se trouve
 * avant la livraison que l'on souhaite ajouter Cet état exécutera ensuite réellement l'ajout
 *
 * @author Maxou
 */
public class EtatAjout2 implements EtatInterface {

    /** Le contrôleur de donénes */
    private ControleurDonnees controleurDonnees;
    
    /** L'identifiant de l'intersection où sera la nouvelle livraison */
    private int idIntersectionLivraison;

    /**
     * Constructeur du deuxième état d'ajout
     * @param controleurDonnees Le contrôleur de données
     * @param idIntersectionLivraison L'identifiant venant du premier état d'ajout
     */
    public EtatAjout2(ControleurDonnees controleurDonnees, int idIntersectionLivraison) {
        this.controleurDonnees = controleurDonnees;
        this.idIntersectionLivraison = idIntersectionLivraison;
        controleurDonnees.notifierObservateursMessage("[AJOUT] Veuillez maintenant cliquer sur la livraison dans la liste à gauche qui précèdera la nouvelle livraison. Clic droit pour revenir à l'étape précédente pour choisir l'intersection.");
    }

    @Override
    public EtatInterface clicSurLivraison(int idLivraisonAvant) {
        Commande commande = new CommandeAjouterLivraison(controleurDonnees, idLivraisonAvant, idIntersectionLivraison);
        try {
            commande.executer();
            controleurDonnees.ajouterCommande(commande);
            controleurDonnees.effacerCommandesARetablir();
            controleurDonnees.notifierObservateursMessage(String.format("[AJOUT] Nouvelle livraison à l'adresse %d créée, suivant la livraison %d. Cliquez sur le plan pour choisir une autre intersection pour créer une livraison ou clic droit pour sortir du mode d'ajout.", idIntersectionLivraison, idLivraisonAvant));
            return new EtatAjout(controleurDonnees);
        } catch (CommandeException e) {
            controleurDonnees.notifierObservateursMessage(e.getMessage());
        }
        return this;
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
        controleurDonnees.notifierObservateursMessage("[AJOUT] Veuillez choisir une livraison en cliquant la liste à gauche. Clic droit pour revenir à la sélection du l'adresse de la livraison.");
        return this;
    }

    @Override
    public EtatInterface clicCalculerTournee() {
        throw new RuntimeException("Cet état ne permet pas de calculer la tournée");
    }

    @Override
    public EtatInterface clicDroit() {
        return new EtatAjout(controleurDonnees);
    }
    
}
