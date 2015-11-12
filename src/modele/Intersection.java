package modele;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mhaidara
 */
public class Intersection {
    
    private int id;
    
    private int x;
    
    private int y;
    
    private List<Troncon> tronconsSortants;
    
    //private List<Troncon> tronconsEntrants;

    public Intersection(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        
        tronconsSortants = new ArrayList<>();
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
    
    
}
