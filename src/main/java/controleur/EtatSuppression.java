package controleur;

import java.io.File;

/**
 *
 * @author Maxou
 */
public class EtatSuppression extends AbstractEtat
{

    private final ControleurDonnees donnees;

    EtatSuppression(ControleurDonnees donnees)
    {
        this.donnees = donnees;
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        try {
            Commande commande = new CommandeSupprimerLivraison(donnees,livraisonId);
            commande.executer();
            donnees.ajouterCommande(commande);

        } catch (CommandException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws CommandException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws CommandException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueSurPlan(int intersectionId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        // Ne fais rien
        return this;
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
