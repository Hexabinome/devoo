package modele;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author mhaidara
 */
public class Fenetre /*implements Comparable<Fenetre>*/{
    
    private final int heureDebut;    
    private final int heureFin;
    private final Map<Integer, Livraison> livraisons;
    
    public Fenetre(int timestampDebut, int timestampFin) {
        this.heureDebut = timestampDebut;
        this.heureFin = timestampFin;
        
        livraisons = new LinkedHashMap<>();
    }

    public int getTimestampDebut() {
        return heureDebut;
    }

    public int getTimestampFin() {
        return heureFin;
    }
    
    protected void effacerLivraison(int livrasionId)
    {
        livraisons.remove(livrasionId);
    }
    
    public void ajouterLivraison(int id, Livraison livraison)
    {
        livraisons.put(id, livraison);
    }

    public Map<Integer, Livraison> getLivraisons() {
        return Collections.unmodifiableMap(livraisons);
    }
}
