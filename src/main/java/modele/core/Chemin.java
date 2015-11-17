package modele.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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
    
    //private final List<Intersection> chemin; 
    private final List<Intersection> intersections;

    public Chemin(Livraison depart, Livraison arrivee, List<Intersection> stations) {
        
        duree = calculerDureeTotale(stations);
        
        this.depart = depart;
        this.arrivee = arrivee;
        
        intersections = new LinkedList<>();
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

    public Collection<Intersection> getIntersectionIds() {
        return Collections.unmodifiableCollection(intersections);
    }
    
    /**
     * Sommation des durees de toutes les troncons sur le chemin.
     * 
     * @param chemin
     * @return 
     */
    private static int calculerDureeTotale(List<Intersection> chemin)
    {
        int resultat = 0;
    
        Iterator<Intersection> iter = chemin.iterator();
        Intersection depart;
        Intersection cible = iter.next();
        
        while(iter.hasNext())
        {
            depart = cible;
            cible = iter.next();
            resultat += depart.getTroncon(cible.getId()).getDuree();
        }
     
        return resultat;
    }
}
