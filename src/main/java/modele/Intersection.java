package modele;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mhaidara
 */
public class Intersection {
    
    private int id;
    
    private int x;
    
    private int y;
    
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

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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
