package modele.xmldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Une Fenetre correspond a une periode de temps fixe avec une nombre des livraisons prevus.
 * 
 * @author mhaidara / maxou
 */
public class Fenetre {
    
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

    @Override
    public String toString() {
        return "Fenetre{" +
                "heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                ", livraisons=" + livraisons +
                '}';
    }
    
    /**
     * Pour chaque points de livraison, on va calculer une liste de chemim vers tous les noeuds de cette fenêtre et de la fenêtre suivante
     * @param plan
     * @param graphe
     * @param fNext
     */
	public void calculerChemins(PlanDeVille plan, GrapheRealisation graphe, Fenetre fNext) {
		//Récupère toutes les intersections avec les quels on doit calculer le plus court chemin.
		Set<Integer> intersectionsRecherchee = livraisons.keySet();
		intersectionsRecherchee.addAll(fNext.getLivraisons().keySet());
		
		for(Map.Entry<Integer, Livraison> livraison : livraisons.entrySet())
		{
			//Recupration de l'intersection de la livraison
			Intersection intersection = plan.getIntersection(livraison.getKey());
			
			for(Chemin chemin : dijkstra(intersection, intersectionsRecherchee, plan))
			{
				graphe.setChemin(chemin);
			}
		}
	}
	
	private List<Chemin> dijkstra(Intersection intersectionDepart, Set<Integer> intersectionsRecherchee, PlanDeVille plan)
	{
		//INITIALISATION
		
		//Map de chemin intermédiaires et finaux
			//La clé est l'id de l'intersection au quel correspond le chemin 
			//le chemin contient tous tronçons par lesquels on est passé 
			//+ l'id de l'interserction de départ et la clef de la map comme intersection d'arrivé
        Map<Integer, Chemin> chemins = new HashMap<>();
		chemins.put(intersectionDepart.getId(), 
					new Chemin(0, new ArrayList<Troncon>(), intersectionDepart.getId(), intersectionDepart.getId()));
		
		Comparator<Intersection> comparator = new CoutComparator();
        PriorityQueue<Intersection> queue = new PriorityQueue<Intersection>(1000, comparator);
        queue.add(intersectionDepart);
		
        //ALGO
        while(!queue.isEmpty())
        {
        	Intersection intersection = queue.poll();
        	
        	//Pour toutes les intersections suivantes
    		for(Intersection intersectionSuivante : getListeIntersectionSuivante(intersection, plan))
    		{
    			queue.add(intersectionSuivante);
    			//On récupére le chemin en cours de contruction dans la map
    			Chemin chemin = chemins.get(intersection.getId());
    			
    			//Calcule d'information pour le nouveau chemin
    			ArrayList<Troncon> listeTronconsEnCours = (ArrayList<Troncon>) chemin.getTroncons();
    			
    			Troncon tronconTraverser = intersection.getTroncon(intersectionSuivante.getId());
    			listeTronconsEnCours.add(tronconTraverser);
    			
    			float cout = chemin.getCout() + tronconTraverser.getCout();
    			
    			//Insertion ou remplacement si le cout est inf�rieur du nouveau chemin dans la map
    			Chemin cheminDejaInserer = chemins.get(intersectionSuivante.getId());
    			if(cheminDejaInserer != null)
    			{
    				if(cheminDejaInserer.getCout() > cout)
    				{	chemins.put(intersectionSuivante.getId(), 
            					new Chemin(cout, listeTronconsEnCours, chemin.getIdDepart(), intersectionSuivante.getId()));
    				}
    			}
    			else
    			{
    				chemins.put(intersectionSuivante.getId(), 
        					new Chemin(cout, listeTronconsEnCours, chemin.getIdDepart(), intersectionSuivante.getId()));
    			}
    		}
        }
		
		//Parcourir la map pour récupérer juste la liste des chemins finaux
        
		return null;
	}
	
	/**
	 * Retourne une liste des intersections suivant
	 * @param intersection
	 * @param plan
	 * @return
	 */
    public ArrayList<Intersection> getListeIntersectionSuivante(Intersection intersection, PlanDeVille plan)
    {
    	ArrayList<Intersection> intersections = new ArrayList<>();
    	
    	for(Integer idIntersection: intersection.getTroncons().keySet())
    	{
    		intersections.add(plan.getIntersection(idIntersection));
    	}
    	
    	return intersections;
    }
	
	/**
	 * Classe utile pour la priority Queue. 
	 * Le but est de lui fournir un comparateur pour qu'elle s'ordonne. 
	 * L'intersection en paramètres ne doit contenir qu'un seul tronçon
	 * @author Djowood
	 *
	 */
	public class CoutComparator implements Comparator<Intersection>
	{
	    @Override
	    public int compare(Intersection x, Intersection y)
	    {
	        if(x.getMinCout() != Float.MAX_VALUE && y.getMinCout() != Float.MAX_VALUE)
	        {
	        	//Retourne un nombre négatif (donc faux si le coût est inférieur), positif si suppérieur et 0 si égale.
	        	return (int)(x.getMinCout() - y.getMinCout());
	        }
	        return 0;
	    }
	}
}
