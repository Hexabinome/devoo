package modele.xmldata;

/**
 * Represente un troncon de rue entre deux intersections.
 * @author Mohamed El Mouctar HAIDARA / maxou
 */
public class Troncon {

    /**
     * Nom de la rue dans lequel le troncon se trouve.
     */
    private String nomRue;

    /**
     * Vitesse moyenne de circulation sur unt troncon.
     */
    private final float vitesse;

    /**
     * Longueur du tronçon.
     */
    private final float longueur;

    /**
     * Duree de traversee du troncon.
     */
    private final float duree;
    
    private final int idDestination;

    public Troncon(String nomRue, float vitesse, float longueur, int idDestination) {
        this.nomRue = nomRue;
        this.vitesse = vitesse;
        this.longueur = longueur;
        this.duree = longueur / vitesse;
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

    public float getLongueur() {
        return longueur;
    }

    public float getDuree() {
        return duree;
    }

    @Override
    public String toString() {
        return "Troncon{" +
                "nomRue='" + nomRue + '\'' +
                ", vitesse=" + vitesse +
                ", longueur=" + longueur +
                ", duree=" + duree +
                '}';
    }
    
    public float getCout()
    {
    	return vitesse * duree;
    }

    public int getIdDestination()
    {
        return idDestination;
    }
    
    
}
