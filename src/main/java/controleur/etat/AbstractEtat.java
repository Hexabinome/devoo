package controleur.etat;

import controleur.commande.Commande;
import controleur.commande.CommandeGenererFeuilleDeRoute;


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
    
    protected final static String TEXTE_ETAT_PRINCIPAL = "Choisissez une action à effectuer pour modifier la tournée à votre guise.";

}
