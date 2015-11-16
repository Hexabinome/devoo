package modele;

/**
 * Répresente un tronçon de rue entre deux intersections.
 * @author Mohamed El Mouctar HAIDARA
 */
public class Troncon {

    /**
     * Nom de la rue dans lequel le tronçon se trouve.
     */
    private String nomRue;

    /**
     * Vitesse moyenne de circulation sur unt tronçon.
     */
    private float vitesse;

    /**
     * Longueur du tronçon.
     */
    private float longueur;

    /**
     * Durée de traversée du tronçon.
     */
    private float duree;

    /**
     * Identifiant de l'intersection de départ du tronçon.
     */
    private int idDepart;

    /**
     * Identifiiant de l'intersection d'arrivée du tronçon.
     */
    private int idDestination;

    public Troncon(String nomRue, float vitesse, float longueur, float duree, int idDestination) {
        this.nomRue = nomRue;
        this.vitesse = vitesse;
        this.longueur = longueur;
        this.duree = duree;
        this.idDestination = idDestination;
    }

    public int getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(int idDepart) {
        this.idDepart = idDepart;
    }

    public int getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(int idDestination) {
        this.idDestination = idDestination;
    }
    
    
    
    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    public float getVitesse() {
        return vitesse;
    }

    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }

    public float getLongueur() {
        return longueur;
    }

    public void setLongueur(float longueur) {
        this.longueur = longueur;
    }

    public float getDuree() {
        return duree;
    }

    public void setDuree(float duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Troncon{" +
                "nomRue='" + nomRue + '\'' +
                ", vitesse=" + vitesse +
                ", longueur=" + longueur +
                ", duree=" + duree +
                ", idDestination=" + idDestination +
                '}';
    }
}
