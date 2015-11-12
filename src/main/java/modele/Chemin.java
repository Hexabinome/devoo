package modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mhaidara
 */
public class Chemin {
    
    private float duree;
    
    private Livraison depart;
    
    private Livraison arrivee;
    
    private List<Troncon> troncons;

    public Chemin(float duree, Livraison depart, Livraison arrivee) {
        this.duree = duree;
        this.depart = depart;
        this.arrivee = arrivee;
        
        troncons = new ArrayList<>();
    }

    public float getDuree() {
        return duree;
    }

    public void setDuree(float duree) {
        this.duree = duree;
    }

    public Livraison getDepart() {
        return depart;
    }

    public void setDepart(Livraison depart) {
        this.depart = depart;
    }

    public Livraison getArrivee() {
        return arrivee;
    }

    public void setArrivee(Livraison arrivee) {
        this.arrivee = arrivee;
    }

    public List<Troncon> getTroncons() {
        return troncons;
    }

    public void setTroncons(List<Troncon> troncons) {
        this.troncons = troncons;
    }
    
    
    
    
}
