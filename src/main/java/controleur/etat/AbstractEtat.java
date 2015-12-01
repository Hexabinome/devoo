package controleur.etat;


/**
 * Cette classe implémente les fonctionnalités qui fonctionnent de la même manière
 * (independant de la realisation d'etat)
 *
 * @author Maxou
 */
public abstract class AbstractEtat implements EtatInterface
{

    @Override
    public EtatInterface clicAnnuler() {
        throw new UnsupportedOperationException("Cette action n'est pas supportée");
    }

    @Override
    public EtatInterface clicRetablir() {
    	throw new UnsupportedOperationException("Cette action n'est pas supportée");
    }
    
    /** Le texte à afficher dans l'état principal */
    protected final static String TEXTE_ETAT_PRINCIPAL = "Choisissez une action à effectuer pour modifier la tournée à votre guise.";

}
