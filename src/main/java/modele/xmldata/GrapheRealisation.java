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
    private int nbSommets;

    /**
     * Cree un graphe. Comme on doit definier la taille de la matrix
     * representant les chemins entre les livraisons d'abord, on va prendre en
     * compte que un utilisateur peut ajouter des nouvelles livraisons plsu
     * tard. Par contre pour limiter la taille de la matrice, on ne permet pas
     * d'ajouter plus que 100 livraisons aditionelles. On peut justifier cette
     * decision avec la feit que une tournee qui etait modifie 100 fois n'a plus
     * beaucoup avoir avec la dmenade initiale. Du coup l'utilisateur doit mieux
     * creer une nouvelle fichier de livraisons.
     *
     * @param nbSommets
     */
    public GrapheRealisation(int nbSommets)
    {
        this.nbSommets = nbSommets;
        chemins = new Chemin[nbSommets + 100][nbSommets + 100];
        idLivraisonToIdMatrice = new HashMap<>();
        idMatriceToIdLivraison = new HashMap<>();
    }

    @Override
    public int getNbSommets()
    {
        return nbSommets;
    }

    @Override
    public int getCout(int depart, int arrivee)
    {
        if (depart > chemins.length || arrivee > chemins.length || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null)
            return Integer.MAX_VALUE;

        return (int) chemins[depart][arrivee].getCout();
    }

    @Override
    public boolean estArc(int depart, int arrivee)
    {
        if (depart >= nbSommets || arrivee >= nbSommets || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null)
            return false;

        //TODO: changer cette ligne incomprehensible!
        return !Objects.equals(depart, arrivee);
    }

    public Chemin getChemin(int idLivraisonDepart, int idLivraisonArrivee)
    {
        if (!idLivraisonToIdMatrice.containsKey(idLivraisonDepart))
            throw new RuntimeException("Livraison id " + idLivraisonDepart + " est inconnu a graphe");
        if (!idLivraisonToIdMatrice.containsKey(idLivraisonArrivee))
            throw new RuntimeException("Livraison id " + idLivraisonArrivee + " est inconnu a graphe");
        int idMatriceDepart = idLivraisonToIdMatrice.get(idLivraisonDepart);
        int idMatriceArrivee = idLivraisonToIdMatrice.get(idLivraisonArrivee);
        if (idMatriceArrivee >= nbSommets || idMatriceDepart >= nbSommets)
            throw new RuntimeException("Cette adresse de matrice n'a pas encore ete activee");

        return chemins[idMatriceDepart][idMatriceArrivee];
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

        if (i >= nbSommets || j >= nbSommets)
            nbSommets++;

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
