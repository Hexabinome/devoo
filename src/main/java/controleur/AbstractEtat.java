package controleur;

/**
 * Cette classe implemente les functinoalites qui fonctionnent la meme manier
 * (independant de la realisation d'etat)
 *
 * @author Maxou
 */
public abstract class AbstractEtat implements EtatInterface
{

    @Override
    public boolean cliqueAnnuler()
    {
        //TODO: place state independent undo logic here
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public boolean cliqueRetablir()
    {
        //TODO: place state independent undo logic here
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
