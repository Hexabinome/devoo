package modele;

/**
 *
 * @author mhaidara
 */
public class Troncon {
    
    private String nomRue;
    
    private float vitesse;
    
    private float longueur;
    
    private float duree;

    private Intersection depart;

    private Intersection arrive;

    public Intersection getDepart() {
        return depart;
    }

    public void setDepart(Intersection depart) {
        this.depart = depart;
    }

    public Intersection getArrive() {
        return arrive;
    }

    public void setArrive(Intersection arrive) {
        this.arrive = arrive;
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
    
    
}
