package modele.xmldata;

import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author maxou
 */
public class GrapheRealisation implements Graphe
{
    private final Chemin[][] chemins;

    //pour pouvoir tradiure les ids des livraisons en ids matrice (utilise par TSP) et egalement l'inverse on utilise deux hashmaps.
    private final HashMap<Integer, Integer> idLivraisonToIdMatrice;
    private final HashMap<Integer, Integer> idMatriceToIdLivraison;
    private int nombreCheminInserer = 0;

    /**
     * Cree un graphe
     *
     * @param nbSommets
     */
    public GrapheRealisation(int nbSommets)
    {
        chemins = new Chemin[nbSommets][nbSommets];
        idLivraisonToIdMatrice = new HashMap<>();
        idMatriceToIdLivraison = new HashMap<>();
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
        if (!idLivraisonToIdMatrice.containsKey(idLivraisonDepart))
            throw new RuntimeException("Livraison id " + idLivraisonDepart + " est inconnu a graphe");
        if (!idLivraisonToIdMatrice.containsKey(idLivraisonArrivee))
            throw new RuntimeException("Livraison id " + idLivraisonArrivee + " est inconnu a graphe");
        return chemins[idLivraisonToIdMatrice.get(idLivraisonDepart)][idLivraisonToIdMatrice.get(idLivraisonArrivee)];
    }

    public Chemin getCheminGrapheIndice(int depart, int arrivee)
    {
        if (depart > chemins.length || depart > chemins.length || depart < 0 || arrivee < 0)
            return null;
        return chemins[depart][arrivee];
    }

    /**
     * Insert un chemin dans la matrice Fait la correspondance entre les id des
     * livraisons et les indices dans la matrice
     *
     * @param chemin
     * @param livraisonDepartId
     * @param livraisonArriveeId
     */
    public void setChemin(Chemin chemin, int livraisonDepartId, int livraisonArriveeId)
    {
        Integer i, j;

        //Test si l'id de la livraison est déjà dans la Map de correspondance ou non 
        if ((i = idLivraisonToIdMatrice.get(livraisonDepartId)) == null) {
            i = nombreCheminInserer;
            idLivraisonToIdMatrice.put(livraisonDepartId, nombreCheminInserer++);
        }
        if ((j = idLivraisonToIdMatrice.get(livraisonArriveeId)) == null) {
            j = nombreCheminInserer;
            idLivraisonToIdMatrice.put(livraisonArriveeId, nombreCheminInserer++);
        }

        if (i < chemins.length && j < chemins.length)
            chemins[i][j] = chemin;
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
        return idLivraisonToIdMatrice.get(idLivraison);
    }

    /**
     * Creer et remplir dictionnaire pour trouver l'id d'une livraison a parti
     * de sa position dans la matrice. Le map dont on a besoin pour faire ca est
     * rempli pendant la creation du graphe.
     */
    public void creerInverseLivraisonDictionnaire()
    {
        idLivraisonToIdMatrice.keySet().stream().forEach((cle) -> {
            idMatriceToIdLivraison.put(idLivraisonToIdMatrice.get(cle), cle);
        });

    }

    public int getIdLivraisonParIdMatrice(int idMatrice)
    {
        return idMatriceToIdLivraison.get(idMatrice);
    }

}
