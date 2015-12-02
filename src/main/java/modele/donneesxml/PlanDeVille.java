package modele.donneesxml;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Réprensente le plan de la ville chargé à partir d'un fichier XML. C'est à
 * dire: Toutes les intersections. Cette classe permet egalement de récupérer
 * une intersection par rapport à son identifiant. Cette classe n'est pas à confondre avec
 * un graphe qu'on utilise pour calculer le chemin optimal avec les algorithmes du
 * TSP et dijkstra.
 *
 * @author Mohamed El Mouctar HAIDARA / maxou
 */
public class PlanDeVille implements Serializable {

    /**
     * Représente l'ensemble des intersections du plan sous la forme d'une map
     */
    private final Map<Integer, Intersection> intersections;

    /** Constructeur du plan de la ville
     */
    public PlanDeVille() {
        intersections = new HashMap<>();
    }

    /** Constructeur du plan de la ville
     * @param intersections Les intersections dans la ville
     */
    public PlanDeVille(Map<Integer, Intersection> intersections) {
        this.intersections = intersections;
    }

    /**
     * Ajoute une intersection au plan
     *
     * @param intersection L'intersection à ajouter
     */
    public void addInstersection(Intersection intersection) {
        intersections.put(intersection.getId(), intersection);
    }

    /**
     * Récupère l'intersection correspondant à l'id passé en paramètre
     * @param idIntersection L'identifiant de l'intersection
     * @return La livraison sur cette adresse, si elle n'existe pas null
     */
    public Intersection getIntersection(int idIntersection) {
        return intersections.get(idIntersection);
    }

    /**
     * @return Toutes les intersections du plan
     */
    public Map<Integer, Intersection> getIntersections() {
        return Collections.unmodifiableMap(intersections);
    }

    @Override
    public String toString() {
        return "PlanDeVille{"
                + "intersections=" + intersections
                + "}";
    }

}
