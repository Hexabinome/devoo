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

    private int idDepart;

    private int idArrive;

    public int getIdDepart() {
        return idDepart;
    }

    public void setIdDepart(int idDepart) {
        this.idDepart = idDepart;
    }

    public int getIdArrive() {
        return idArrive;
    }

    public void setIdArrive(int idArrive) {
        this.idArrive = idArrive;
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
