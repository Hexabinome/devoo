package controleur.etat;

import java.io.File;

import controleur.ControleurDonnees;
import controleur.commande.CommandeException;
import controleur.commande.Commande;
import controleur.commande.CommandeSupprimerLivraison;

/**
 * On se retrouve dans ce état après avoir cliqué sur le bouton Supprimer livraisons.
 * @author Maxou
 */
public class EtatSuppression extends AbstractEtat
{

    private final ControleurDonnees donnees;

    public EtatSuppression(ControleurDonnees donnees)
    {
        this.donnees = donnees;
        donnees.notifierObserveursChargementDuPlan(false);
        donnees.notifierObserveursDuPlan(false);
        donnees.notifierObserveursMessage("[SUPPRESSION] Souhaitez-vous supprimer une livraison ? Choisissez dans la liste à gauche la livraison que vous voulez supprimer. Clic droit pour sortir du mode de suppression.");
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        try {
            Commande commande = new CommandeSupprimerLivraison(donnees,livraisonId);
            commande.executer();
            donnees.ajouterCommande(commande);
            donnees.effacerCommandesARetablir();

        } catch (CommandeException e) {
        	donnees.notifierObserveursMessage(e.getMessage());
        	// La commande a échoué. Le message sera afficher dans la barre de status
        }
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandeException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandeException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        donnees.notifierObserveursMessage("[SUPPRESSION] Veuillez choisir une livraison dans la liste à gauche. Clic droit pour sortir du mode de suppression");
        return this;
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        // Ne fais rien
        return this;
    }

    @Override
    public EtatInterface clicDroit() {
        donnees.notifierObserveursMessage(TEXTE_ETAT_PRINCIPAL);
        return new EtatPrincipal(donnees);
    }

    @Override
    public EtatInterface cliqueAnnuler()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueRetablir()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
