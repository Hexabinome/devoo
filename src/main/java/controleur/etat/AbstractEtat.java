package controleur.etat;


/**
 * Cette classe implemente les functinoalites qui fonctionnent la meme manier
 * (independant de la realisation d'etat)
 *
 * @author Maxou
 */
public abstract class AbstractEtat implements EtatInterface
{

    @Override
    public EtatInterface cliqueAnnuler()
    {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public EtatInterface cliqueRetablir()
    {
        throw new RuntimeException("Not implemented yet");
    }

}
