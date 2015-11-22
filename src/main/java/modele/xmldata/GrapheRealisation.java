package modele.xmldata;

import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author maxou
 */
public class GrapheRealisation implements Graphe
{
    private Chemin[][] chemins;
    private final HashMap<Integer, Integer> idCheminToIdMatrice;
    private int nombreCheminInserer = 0;
    /**
     * Cree un graphe 
     *
     * @param nbSommets
     */
    public GrapheRealisation(int nbSommets)
    {
        chemins = new Chemin[nbSommets][nbSommets];
        idCheminToIdMatrice = new HashMap<>();
    }

    @Override
    public int getNbSommets()
    {
        return chemins.length;
    }

    @Override
    public int getCout(int i, int j)
    {
    	//TODO gestion de l'erreur
    	//TODO revoir la gestion des couts car chemins return un float
        if(chemins[idCheminToIdMatrice.get(i)][idCheminToIdMatrice.get(j)] == null)
        	return -1;
        
        return (int)chemins[idCheminToIdMatrice.get(i)][idCheminToIdMatrice.get(j)].getCout();
    }

    @Override
    public boolean estArc(int i, int j)
    {
    	if(idCheminToIdMatrice.get(i) == null || idCheminToIdMatrice.get(j) == null || idCheminToIdMatrice.get(i) > chemins.length || idCheminToIdMatrice.get(i) > chemins.length)
    		return false;
        return !Objects.equals(idCheminToIdMatrice.get(i), idCheminToIdMatrice.get(j));
    }

	public Chemin getChemin(int i, int j) {
    	if(idCheminToIdMatrice.get(i) == null || idCheminToIdMatrice.get(j) == null || idCheminToIdMatrice.get(i) > chemins.length || idCheminToIdMatrice.get(i) > chemins.length)
    		return null;
		return chemins[idCheminToIdMatrice.get(i)][idCheminToIdMatrice.get(j)];
	}

	public void setChemins(Chemin[][] chemins) {
		this.chemins = chemins;
	}
	
	public void setChemin(Chemin chemin)
	{
		Integer i, j;

		if((i = idCheminToIdMatrice.get(chemin.getIdDepart())) == null)
		{
			i = nombreCheminInserer;
			idCheminToIdMatrice.put(chemin.getIdDepart(), nombreCheminInserer++);
		}
		if((j = idCheminToIdMatrice.get(chemin.getIdFin())) == null)
		{
			j = nombreCheminInserer;
			idCheminToIdMatrice.put(chemin.getIdFin(), nombreCheminInserer++);
		}
		
		if(i < chemins.length && j < chemins.length)
			chemins[i][j] = chemin;
	}
}
