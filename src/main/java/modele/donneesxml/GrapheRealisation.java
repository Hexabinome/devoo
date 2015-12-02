package modele.donneesxml;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * Réalisation de l'interface de graphe
 * @author maxou
 */
public class GrapheRealisation implements Graphe, Serializable {

    /** Les chemins empreintés du graphe */
    private final Chemin[][] chemins;

    // Pour pouvoir traduire les identifiants des livraisons en identifiant matrice (utilise par TSP), et également l'inverse, on utilise deux hashmaps.
    private final HashMap<Integer, Integer> idLivraisonToIdMatrice;
    private final HashMap<Integer, Integer> idMatriceToIdLivraison;
    
    /** Le nombre de chemin dans le graphe */
    private int nombreCheminInserer = 0;
    
    /** Le nombre de sommet du graphe */
    private int nbSommets;

    /**
     * Crée un graphe. Comme on doit definir la taille de la matrice
     * représentant les chemins entre les livraisons d'abord, on va prendre en
     * compte qu'un utilisateur peut ajouter des nouvelles livraisons plus
     * tard. Par contre pour limiter la taille de la matrice, on ne permet pas
     * d'ajouter plus de 100 livraisons additionelles. On peut justifier cette
     * déçision avec la fait qu'une tournée qui était modifiée 100 fois n'a plus
     * beaucoup avoir avec la demande initiale. Du coup, l'utilisateur doit mieux
     * créer un nouveau fichier de livraisons.
     *
     * @param nbSommets Le nombre de sommet du graphe, permettant de prévoir le nombre de chemin
     */
    public GrapheRealisation(int nbSommets) {
        this.nbSommets = nbSommets;
        chemins = new Chemin[nbSommets + 100][nbSommets + 100];
        idLivraisonToIdMatrice = new HashMap<>();
        idMatriceToIdLivraison = new HashMap<>();
    }

    @Override
    public int getNbSommets() {
        return nbSommets;
    }

    @Override
    public int getCout(int depart, int arrivee) {
        if (depart > chemins.length || arrivee > chemins.length || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null) {
            return Integer.MAX_VALUE;
        }

        return (int) chemins[depart][arrivee].getCout();
    }

    @Override
    public boolean estArc(int depart, int arrivee) {
        if (depart >= nbSommets || arrivee >= nbSommets || depart < 0 || arrivee < 0 || chemins[depart][arrivee] == null) {
            return false;
        }

        //TODO: changer cette ligne incomprehensible!
        return !Objects.equals(depart, arrivee);
    }

    /** Retrouve le chemin entre deux livraisons
     * @param idLivraisonDepart Identifiant de la livraison de départ
     * @param idLivraisonArrivee Identifiant de la livraison d'arrivée
     * @return Le chemin entre les livraisons
     */
    public Chemin getChemin(int idLivraisonDepart, int idLivraisonArrivee) {
        if (!idLivraisonToIdMatrice.containsKey(idLivraisonDepart)) {
            throw new RuntimeException("Livraison id " + idLivraisonDepart + " est inconnue au graphe");
        }
        if (!idLivraisonToIdMatrice.containsKey(idLivraisonArrivee)) {
            throw new RuntimeException("Livraison id " + idLivraisonArrivee + " est inconnue au graphe");
        }
        int idMatriceDepart = idLivraisonToIdMatrice.get(idLivraisonDepart);
        int idMatriceArrivee = idLivraisonToIdMatrice.get(idLivraisonArrivee);
        if (idMatriceArrivee >= nbSommets || idMatriceDepart >= nbSommets) {
            throw new RuntimeException("Cette adresse de matrice n'a pas encore été activée");
        }

        return chemins[idMatriceDepart][idMatriceArrivee];
    }

    /**
     * Insère un chemin dans la matrice faisant la correspondance entre les identifiants des
     * livraisons et les indices dans la matrice
     *
     * @param chemin Le chemin a placé
     * @param livraisonDepartId L'identifiant de la livraison de départ
     * @param livraisonArriveeId L'identifiant de la livraison d'arrivée
     */
    public void setChemin(Chemin chemin, int livraisonDepartId, int livraisonArriveeId) {
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

        if (i >= nbSommets || j >= nbSommets) {
            nbSommets++;
        }

        chemins[i][j] = chemin;
    }

    /**
     * Retourne l'indice dans la matrice de solution à partir de l'identifiant de
     * livraison
     *
     * @param idLivraison L'identifiant de la livraison
     * @return L'indice dans la matrice la livraison
     */
    public int getIndiceFromIdLivraison(int idLivraison) {
        return idLivraisonToIdMatrice.get(idLivraison);
    }

    /**
     * Créer et remplir dictionnaire pour trouver l'id d'une livraison à partir
     * de sa position dans la matrice. Le map dont on a besoin pour faire ça est
     * rempli pendant la création du graphe.
     */
    public void creerInverseLivraisonDictionnaire() {
        idLivraisonToIdMatrice.keySet().forEach(cle -> idMatriceToIdLivraison.put(idLivraisonToIdMatrice.get(cle), cle));
    }

    /** Retrouve l'indice de la livraison grâce à sa position dans la matrice
     * @param idMatrice Position de la livraison dans la matrice
     * @return L'identifiant de la livraison
     */
    public int getIdLivraisonParIdMatrice(int idMatrice) {
        return idMatriceToIdLivraison.get(idMatrice);
    }
    
}
