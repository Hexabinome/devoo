package modele;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mhaidara
 */
public class Chemin {
    
    private float duree;
    
    //ToDo: check if needed
    private final Livraison depart;
    
    //ToDo: check if needed
    private final Livraison arrivee;
    
    //ToDo: check if needed
    //private final List<Troncon> troncons;
    
    //private final List<Intersection> chemin; 
    private final List<Integer> intersectionIds;

    public Chemin(Livraison depart, Livraison arrivee, List<Integer> chemin) {
        
        //TODO calculer duree totale
        duree = 42;
        
        this.depart = depart;
        this.arrivee = arrivee;
        
        //troncons = new ArrayList<>();
        intersectionIds = new LinkedList<>();
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

    public Livraison getArrivee() {
        return arrivee;
    }

    public Collection<Integer> getIntersectionIds() {
        return Collections.unmodifiableCollection(intersectionIds);
    }
}
