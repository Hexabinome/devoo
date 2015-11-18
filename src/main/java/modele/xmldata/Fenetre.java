package modele.xmldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public void calculerChemins(PlanDeVille plan, GrapheRealisation graphe) {
		for(Map.Entry<Integer, Livraison> livraison : livraisons.entrySet())
		{
			//Récupération de l'intersection de la livraison
			Intersection intersection = plan.getIntersection(livraison.getKey());
			
			for(Chemin chemin : dijkstra(intersection))
			{
				graphe.setChemin(chemin);
			}
		}
	}
	
	private List<Chemin> dijkstra(Intersection intersectionDepart)
	{
		//TODO appel du dijkstra récursif.
		//récupérer la liste des chemin 
		
		return null;
	}
}
