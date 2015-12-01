package modele.persistance;

/**
 * Cette répresente une erreur survenue lors du parsing/chargement d'un plan de ville ou d'une demande de livraison.
 * Elle ne concerne pas les erreurs de mal formation du document mais certaines erreurs liées à la semantique (une intersection pointe sur elle-même par exemple).
 * @author Mohamed El Mouctar HAIDARA
 */
public class ExceptionXML extends Exception {

    /** L'identifiant de sérialisation généré automatiquement */
	private static final long serialVersionUID = 5195134836250283131L;

	/**
     * Constructeur de l'exception XML
     * @param message Le message d'erreur
     */
    public ExceptionXML(String message) {
        super(message);
    }
}
