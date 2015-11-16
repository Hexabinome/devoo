package modele;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mhaidara
 */
public class PlanDeVille {
    
    private Map<Integer,Intersection> intersections;

    public PlanDeVille(){
        intersections = new HashMap<>();
    }

    public PlanDeVille(Map<Integer, Intersection> intersections) {
        this.intersections = intersections;
    }


    public void addInstersection(Intersection intersection){
        intersections.put(intersection.getId(),intersection);
    }

    public Intersection getIntersection(int id){
        return intersections.get(id);
    }


    @Override
    public String toString() {
        return "PlanDeVille{" +
                "intersections=" + intersections +
                '}';
    }
}
