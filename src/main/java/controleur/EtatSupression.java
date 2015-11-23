package controleur;

import java.io.File;

/**
 *
 * @author Maxou
 */
public class EtatSupression extends AbstractEtat
{

    private final ControleurDonnees donnees;

    EtatSupression(ControleurDonnees donnees)
    {
        this.donnees = donnees;
    }

    @Override
    public EtatInterface cliqueSurLivraison(int livraisonId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
