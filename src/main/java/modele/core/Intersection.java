package modele;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author mhaidara
 */
public class Intersection {
    
    private final int id;
    private final int x;
    private final int y;
    
    /**
     * On stoque chaque troncon sortant, par l'id de sa cible dans un map.
     */
    private Map<Integer, Troncon> troncons;
    
    public Intersection(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        
        troncons = new LinkedHashMap<>();
    }


    public void addTroncon(int id, Troncon troncon){
        troncons.put(id, troncon);
    }
    
    
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    protected Troncon getTroncon(int cibleId)
    {
        return troncons.get(cibleId);
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", tronconsSortants=" + troncons +
                '}';
    }
}
