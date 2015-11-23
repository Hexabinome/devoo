package modele.xmldata;

/**
 * Represente un livraison a effectuer.
 *
 * @author mhaidara / maxou
 */
public class Livraison
{

    //une fois la tourne a ete calcule on peut associer une horarie de passage a chaque livraison
    private int horaireDePassage;

    public int getHoraireDePassage()
    {
        return horaireDePassage;
    }

    public void setHoraireDePassage(int horaireDePassage)
    {
        this.horaireDePassage = horaireDePassage;
    }

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

    public Livraison(int idLivraison, int clientId, int idIntersection)
    {
        this.id = idLivraison;
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

    @Override
    public String toString()
    {
        return "Livraison{"
                + "id=" + id
                + ", clientId=" + clientId
                + ", adresse=" + adresse
                + '}';
    }

}
