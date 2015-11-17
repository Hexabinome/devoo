package modele.xmldata;

/**
 * Represente un livraison a effectuer.
 * 
 * @author mhaidara / maxou
 */
public class Livraison
{

    /**
     * Identifiant de la livraison
     */
    private final int id;

    /**
     * Identifiant du client de la livraison
     */
    private final int clientId;

    /**
     * Identifiant de l'intersection où s'effectue la livraison
     */
    private final int adresse;

    public Livraison(int id, int clientId, int idIntersection)
    {
        this.id = id;
        this.clientId = clientId;
        this.adresse = idIntersection;
    }

    public int getId()
    {
        return id;
    }

    public int getClientId()
    {
        return clientId;
    }

    public int getAdresse()
    {
        return adresse;
    }
}
