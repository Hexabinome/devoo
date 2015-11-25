package modele.xmldata;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author maxou
 */
public class GrapheRealisation implements Graphe
{

    private Chemin[][] chemins;
    //dictionnaire pour rechercher l'id dans la matrice d'une intersection (depart d'un chemin)
    private final HashMap<Integer, Integer> idIntersectionToIdMatrice;
    private int nombreCheminInserer = 0;

    /**
     * Cree un graphe
     *
     * @param nbSommets
     */
    public GrapheRealisation(int nbSommets)
    {
        chemins = new Chemin[nbSommets][nbSommets];
        idIntersectionToIdMatrice = new HashMap<>();
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
        if (depart > chemins.length || arrivee > chemins.length || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null)
            return Integer.MAX_VALUE;

        return (int) chemins[depart][arrivee].getCout();
    }

    @Override
    public boolean estArc(int depart, int arrivee)
    {
        if (depart > chemins.length || arrivee > chemins.length || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null)
            return false;
        return !Objects.equals(depart, arrivee);
    }

    public Chemin getChemin(int idLivraisonDepart, int idLivraisonArrivee)
    {
        if (idIntersectionToIdMatrice.get(idLivraisonDepart) == null || idIntersectionToIdMatrice.get(idLivraisonArrivee) == null || idIntersectionToIdMatrice.get(idLivraisonDepart) > chemins.length || idIntersectionToIdMatrice.get(idLivraisonDepart) > chemins.length)
            return null;
        return chemins[idIntersectionToIdMatrice.get(idLivraisonDepart)][idIntersectionToIdMatrice.get(idLivraisonArrivee)];
    }

    public Chemin getCheminGrapheIndice(int depart, int arrivee)
    {
        if (depart > chemins.length || depart > chemins.length || depart < 0 || arrivee < 0)
            return null;
        return chemins[depart][arrivee];
    }

    public void setChemins(Chemin[][] chemins)
    {
        this.chemins = chemins;
    }

    public void setChemin(Chemin chemin)
    {
        Integer i, j;

        if ((i = idIntersectionToIdMatrice.get(chemin.getIdDepart())) == null) {
            i = nombreCheminInserer;
            idIntersectionToIdMatrice.put(chemin.getIdDepart(), nombreCheminInserer++);
        }
        if ((j = idIntersectionToIdMatrice.get(chemin.getIdFin())) == null) {
            j = nombreCheminInserer;
            idIntersectionToIdMatrice.put(chemin.getIdFin(), nombreCheminInserer++);
        }

        if (i < chemins.length && j < chemins.length)
            chemins[i][j] = chemin;
    }

    public void setChemin(Chemin chemin, int livraisonDepartId, int livraisonArriveeId)
    {
    }

    /**
     * Retourne l'indice dans la matrice de solution à partir de l'id de
     * livraison
     *
     * @param idLivraison
     * @return
     */
    public int getIndiceFromIdLivraison(int idLivraison)
    {
        return idIntersectionToIdMatrice.get(idLivraison);
    }

    /**
     * Creer et remplir dictionnaire pour trouver l'id d'une interseciton a
     * parti de sa position dans la matrice. Cette map est rempli quand la
     * creation du graphe a ete termine. On en a besoin notamment pour affichier
     * la solution calculle par TSP.
     */
    public Map<Integer, Integer> getIntersectionDictionnaire()
    {
        Map<Integer, Integer> dictionnaire = new LinkedHashMap<>();
        idIntersectionToIdMatrice.keySet().stream().forEach((cle) -> {
            dictionnaire.put(idIntersectionToIdMatrice.get(cle), cle);
        });
        return dictionnaire;
    }

}
