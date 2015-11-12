package modele;

/**
 *
 * @author mhaidara
 */
public class Livraison {
    
    private int id;
    
    private int clientId;

    private Intersection adresse;

    private Fenetre fenetre;

    public Livraison(int id, int clientId, Intersection adresse, Fenetre fenetre) {
        this.id = id;
        this.clientId = clientId;
        this.adresse = adresse;
        this.fenetre = fenetre;
    }

    public Fenetre getFenetre() {
        return fenetre;
    }

    public void setFenetre(Fenetre fenetre) {
        this.fenetre = fenetre;
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    
    public Intersection getAdresse() {
        return adresse;
    }

    public void setAdresse(Intersection adresse) {
        this.adresse = adresse;
    }
    
}
