package modele.xmldata;

import java.io.Serializable;

/**
 * Represente un livraison a effectuer.
 *
 * @author mhaidara / maxou
 */
public class Livraison implements Serializable
{

    //une fois la tourne a ete calcule on peut associer une horarie de passage a chaque livraison
    private int horaireDePassage;
    
    /**
     * Si la livraison a du retard, après que la tournée ait été calculée
     */
    private boolean retard = false;

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

	public boolean estEnRetard() {
		return retard;
	}
	
	public void setRetard(boolean retard) {
		this.retard = retard;
	}

}
