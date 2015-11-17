package modele;

/**
 *
 * @author mhaidara
 */
public class Livraison {

    /**
     * Identifiant de la livraison
     */
    private int id;

    /**
     * Identifiant du client de la livraison
     */
    private int clientId;

    /**
     * Identifiant de l'intersection où s'effectue la livraison
     */
    private int adresse;

    /* TODO : peut etre qu'on a pas besoin de mettre la fenetre ici. On a une reférence circulaire avec la fenetre qui contient aussi une liste de Livraison */
    /**
     * La fenêtre dans laquelle la livraison se trouve.
     *
     */
    private Fenetre fenetre;

    public Livraison(int id, int clientId, int adresse, Fenetre fenetre) {
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
    
    public int getAdresse() {
        return adresse;
    }

    public void setAdresse(int adresse) {
        this.adresse = adresse;
    }
    
}
