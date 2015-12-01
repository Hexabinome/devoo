package controleur.etat;

import controleur.ControleurDonnees;
import controleur.commande.Commande;
import controleur.commande.CommandeAjouterLivraison;
import controleur.commande.CommandeException;

import java.io.File;

/**
 * Cet état correspond à l'état où l'on doit choisir la livraison qui se trouve
 * avant la livraison que l'on souhaite ajouter Cet état exécutera ensuite
 *
 * @author Maxou
 */
public class EtatAjout2 extends AbstractEtat {

    private ControleurDonnees controleurDonnees;
    private int idIntersectionLivraison;

    public EtatAjout2(ControleurDonnees controleurDonnees, int idIntersectionLivraison)
    {
        this.controleurDonnees = controleurDonnees;
        this.idIntersectionLivraison = idIntersectionLivraison;
        controleurDonnees.notifierObserveursMessage("[AJOUT] Veuillez maintenant cliquer sur la livraison dans la liste à gauche qui précèdera la nouvelle livraison. Clic droit pour revenir à l'étape précédente pour choisir l'intersection.");
    }

    @Override
    public EtatInterface cliqueSurLivraison(int idLivraisonAvant)
    {
        Commande commande = new CommandeAjouterLivraison(controleurDonnees, idLivraisonAvant, idIntersectionLivraison);
        try {
            commande.executer();
        }
        catch (CommandeException e) {
            return this;
        }
        controleurDonnees.ajouterCommande(commande);
        controleurDonnees.effacerCommandesARetablir();
        EtatInterface etat = new EtatAjout(controleurDonnees);
        controleurDonnees.notifierObserveursMessage(String.format("[AJOUT] Nouvelle livraison à l'adresse %d créée, suivant la livraison %d. Cliquez sur le plan pour choisir une autre intersection pour créer une livraison ou clic droit pour sortir du mode d'ajout.", idIntersectionLivraison, idLivraisonAvant));
        return etat;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
        throw new RuntimeException("Cet etat ne permet pas de charger un plan");
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException
    {
        throw new RuntimeException("Cet etat ne permet pas de charger une demande de livraison");
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        controleurDonnees.notifierObserveursMessage("[AJOUT] Veuillez choisir une livraison en cliquant la liste à gauche. Clic droit pour revenir à la sélection du l'adresse de la livraison.");
        return this;
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new RuntimeException("Cet etat ne permet pas de calculer la tournée");
    }

    @Override
    public EtatInterface clicDroit()
    {
        return new EtatAjout(controleurDonnees);
    }

}
