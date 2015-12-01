package controleur.commande;

/**
 * Représente une exception lancée lors de l'exécution d'une commande
 * @author Maxou
 */
public class CommandeException extends Exception
{
    /**
	 * Identifiant de sérialisation généré automatiquement à partir du contenu de la classe
	 */
	private static final long serialVersionUID = -1870801120990068433L;

	/**
	 * Construction d'une exception d'une commande
	 * @param message Le message d'erreur
	 */
	public CommandeException(String message) {
        super(message);
    }

}
