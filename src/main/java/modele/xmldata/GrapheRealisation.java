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
    public int getCout(int depart, int arrivee)
    {
    	//TODO gestion de l'erreur
    	//TODO revoir la gestion des couts car chemins return un float
        if(depart > chemins.length || arrivee > chemins.length || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null)
        	return Integer.MAX_VALUE;
        
        return (int)chemins[depart][arrivee].getCout();
    }

    @Override
    public boolean estArc(int depart, int arrivee)
    {
    	if(depart > chemins.length || arrivee > chemins.length || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null)
    		return false;
        return !Objects.equals(depart, arrivee);
    }

	public Chemin getChemin(int idLivraisonDepart, int idLivraisonArrivee) {
    	if(idCheminToIdMatrice.get(idLivraisonDepart) == null || idCheminToIdMatrice.get(idLivraisonArrivee) == null || idCheminToIdMatrice.get(idLivraisonDepart) > chemins.length || idCheminToIdMatrice.get(idLivraisonDepart) > chemins.length)
    		return null;
		return chemins[idCheminToIdMatrice.get(idLivraisonDepart)][idCheminToIdMatrice.get(idLivraisonArrivee)];
	}

	public Chemin getCheminGrapheIndice(int depart, int arrivee) {
		if(depart > chemins.length || depart > chemins.length || depart < 0 || arrivee < 0)
			return null;
		return chemins[depart][arrivee];
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
	
	/**
	 * Retourne l'indice dans la matrice de solution à partir de l'id de livraison
	 * @param idLivraison
	 * @return
	 */
	public int getIndiceFromIdLivraison(int idLivraison)
	{
		return idCheminToIdMatrice.get(idLivraison);
	}
}
