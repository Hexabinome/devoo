package modele.xmldata;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Cette class represente une intersection dans le plan de ville. (Pas a
 * confondre avec un neud du graphe).
 *
 * @author mhaidara / maxou
 */
public class Intersection  implements Serializable{

    private final int id;
    private final int x;
    private final int y;

    /**
     * On stoque chaque troncon sortant, par l'id de sa cible dans un map.
     */
    private final Map<Integer, Troncon> troncons;

    public Intersection(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;

        troncons = new LinkedHashMap<>();
    }

    public void addTroncon(int id, Troncon troncon) {
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

    public Troncon getTroncon(int cibleId) {
        return troncons.get(cibleId);
    }

    protected boolean aLiaison(int cibleId) {
        return troncons.keySet().contains(cibleId);
    }

    public float getMinCout()
    {
    	float coutMini= Float.MAX_VALUE;
    	
    	for(Entry<Integer, Troncon> troncon: troncons.entrySet())
    	{
    		if(troncon.getValue().getCout() < coutMini)
    			coutMini = troncon.getValue().getCout();
    	}
    	
    	return coutMini;
    }

    @Override
    public String toString() {
        return "Intersection{"
                + "id=" + id
                + ", x=" + x
                + ", y=" + y
                + ", tronconsSortants=" + troncons
                + '}';
    }

	public Map<Integer, Troncon> getTroncons() {
		return troncons;
	}

}
