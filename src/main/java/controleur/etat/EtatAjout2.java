package controleur.etat;

import controleur.ControleurDonnees;
import controleur.commande.Commande;
import controleur.commande.CommandeAjouterLivraison;
import controleur.commande.CommandeException;

import java.io.File;

/**
 * Cet état correspond à l'état où  l'on doit choisir la livraison qui se trouve avant la livraison que l'on souhaite ajouter
 * Cet état exécutera ensuite
 * @author Maxou
 */public class EtatAjout2 extends AbstractEtat {


    private ControleurDonnees controleurDonnees;
    private int idIntersectionLivraison;

    public EtatAjout2(ControleurDonnees controleurDonnees, int idIntersectionLivraison){
        this.controleurDonnees = controleurDonnees;
        this.idIntersectionLivraison = idIntersectionLivraison;
    }

    @Override
    public EtatInterface cliqueSurLivraison(int idLivraisonAvant) {
        Commande commande = new CommandeAjouterLivraison(controleurDonnees,idLivraisonAvant,idIntersectionLivraison);
        try {
            commande.executer();
        } catch (CommandeException e) {
            e.printStackTrace();
            return this;
        }
        controleurDonnees.ajouterCommande(commande);
        controleurDonnees.effacerCommandesARetablir();
        return new EtatAjout(controleurDonnees);
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException {
        throw new RuntimeException("Cet etat ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException {
        throw new RuntimeException("Cet etat ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId) {
        throw new RuntimeException("Cet etat ne permet pas d'interagir avec le plan");
    }

    @Override
    public EtatInterface cliqueCalculerTournee() {
        throw new RuntimeException("Cet etat ne permet pas de calculer la tournée");
    }

    @Override
    public EtatInterface clicDroit() {
        return new EtatAjout(controleurDonnees);
    }
}
