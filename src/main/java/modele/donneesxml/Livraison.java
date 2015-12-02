package modele.donneesxml;

import java.io.Serializable;

/**
 * Représente une livraison à effectuer.
 *
 * @author mhaidara / maxou
 */
public class Livraison implements Serializable {

    /** L'horaire de passe de la livraison, une fois que la tournée a été calculée */
    private int horaireDePassage;
    
    /**
     * Si la livraison a du retard, après que la tournée ait été calculée
     */
    private boolean retard = false;

    /**
     * @return L'horaire de passage
     */
    public int getHoraireDePassage() {
        return horaireDePassage;
    }

    /** Affecte l'heure de passage de la livraison
     * @param horaireDePassage La nouvelle horaire de passage
     */
    public void setHoraireDePassage(int horaireDePassage) {
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

    /** Constructeur de la livraison
     * @param idLivraison Identifiant de la livraison
     * @param clientId Identifiant du client à livrer
     * @param idIntersection Identifiant de l'intersection où se situe la livraison
     */
    public Livraison(int idLivraison, int clientId, int idIntersection) {
        this.id = idLivraison;
        this.clientId = clientId;
        this.adresse = idIntersection;
    }

    /**
     * @return L'identifiant de la livraison
     */
    public int getId() {
        return id;
    }

    /**
     * @return L'identifiant du client à livrer
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * @return L'identifiant de l'intersection où se situe la livraison
     */
    public int getAdresse() {
        return adresse;
    }

    @Override
    public String toString() {
        return "Livraison{"
                + "id=" + id
                + ", clientId=" + clientId
                + ", adresse=" + adresse
                + '}';
    }

	/**
	 * @return Vrai si le passage à la livraison sera en retard
	 */
	public boolean estEnRetard() {
		return retard;
	}
	
	/** Détermine si la livraison est en retard
	 * @param retard Le retard
	 */
	public void setRetard(boolean retard) {
		this.retard = retard;
	}

}
