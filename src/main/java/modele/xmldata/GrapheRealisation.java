package modele.xmldata;

import java.util.ArrayList;

/**
 *
 * @author maxou
 */
public class GrapheRealisation implements Graphe
{
    Chemin[][] chemins;

    /**
     * Cree un graphe 
     *
     * @param nbSommets
     */
    public GrapheRealisation(int nbSommets)
    {
        chemins = new Chemin[nbSommets][nbSommets];
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
        if(chemins[i][j] == null)
        	return -1;
        return chemins[i][j].getCout();
    }

    @Override
    public boolean estArc(int i, int j)
    {
        if (i < 0 || i >= chemins.length || j < 0 || j >= chemins.length)
            return false;
        //TODO pourquoi faire ça ? 
        return i != j;
    }

	public Chemin getChemin(int i, int j) {
		return chemins[i][j];
	}

	public void setChemins(Chemin[][] chemins) {
		this.chemins = chemins;
	}
	
	public void setChemin(Chemin chemin)
	{
		chemins[chemin.getIdDepart()][chemin.getIdFin()] = chemin;
	}
}
