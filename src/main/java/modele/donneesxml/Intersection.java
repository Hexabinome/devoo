package modele.donneesxml;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Cette classe représente une intersection dans le plan de ville. (A ne pas
 * confondre avec un noeud du graphe).
 *
 * @author mhaidara / maxou
 */
public class Intersection  implements Serializable {

    /** Identifiant de l'intersection */
    private final int id;
    
    /** Coordonnées en abscisse de l'intersection */
    private final int x;
    
    /** Coordonnées en ordonnée de l'intersection */
    private final int y;

    /**
     * On stoque chaque troncon sortant, par l'id de sa cible dans un map.
     */
    private final Map<Integer, Troncon> troncons;

    /** Constructeur d'un intersection
     * @param id Identifiant
     * @param x Abscisse
     * @param y Ordonnée
     */
    public Intersection(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;

        troncons = new LinkedHashMap<>();
    }

    /** Ajoute un tronçon à l'intersection vers une autre intersection.
     * Remplace l'ancien tronçon si il existe déjà
     * @param id Identifiant de l'intersection destination
     * @param troncon Le nouveau tronçon
     */
    public void addTroncon(int id, Troncon troncon) {
        troncons.put(id, troncon);
    }

    /**
     * @return L'identifiant de l'intersection
     */
    public int getId() {
        return id;
    }

    /**
     * @return La coordonnée en abscisse de l'intersection
     */
    public int getX() {
        return x;
    }

    /**
     * @return La coordonnée en ordonnée de l'intersection
     */
    public int getY() {
        return y;
    }

    /** 
     * @param cibleId L'identifiant de l'intersection cible
     * @return Le tronçon demandé ou null si non trouvé
     */
    public Troncon getTroncon(int cibleId) {
        return troncons.get(cibleId);
    }

    /**
     * @param cibleId Identifiant de l'intersection cible
     * @return Vrai s'il existe un tronçon entre les deux livraisons
     */
    protected boolean aLiaison(int cibleId) {
        return troncons.keySet().contains(cibleId);
    }

    /** 
     * @return Le tronçon le moins couteux partant de cette intersection
     */
    public float getMinCout() {
    	float coutMini= Float.MAX_VALUE;
    	
    	for(Entry<Integer, Troncon> troncon: troncons.entrySet()) {
    		if(troncon.getValue().getCout() < coutMini) {
    			coutMini = troncon.getValue().getCout();
    		}
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

	/**
	 * @return Tous les tronçons de l'intersection
	 */
	public Map<Integer, Troncon> getTroncons() {
		return troncons;
	}

}
