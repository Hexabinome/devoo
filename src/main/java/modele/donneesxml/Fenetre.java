package modele.donneesxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Une fenêtre correspond à une periode de temps fixe avec une nombre des
 * livraisons prévus.
 *
 * @author jolan
 */
public class Fenetre implements Serializable {

    /** Heure de début de la livraison (en secondes) */
    private final int heureDebut;
    /** Heure de fin de la livraison (en secondes) */
    private final int heureFin;

    /** Stoque les livraisons par rapport à leur identifiant */
    private final Map<Integer, Livraison> livraisons;

    /**
     * Constructeur de la fenêtre
     * @param timestampDebut début de la fenêtre (en secondes)
     * @param timestampFin fin de la fenêtre (en secondes)
     */
    public Fenetre(int timestampDebut, int timestampFin) {
        this.heureDebut = timestampDebut;
        this.heureFin = timestampFin;

        livraisons = new LinkedHashMap<>();
    }

    /** Retourne l'heure de début de la fenêtre (en secondes)
     * @return L'heure de début de la fenêtre (en secondes)
     */
    public int getTimestampDebut() {
        return heureDebut;
    }

    /** Retourne l'heure de fin de la fenêtre (en secondes)
     * @return L'heure de fin de la fenêtre (en secondes)
     */
    public int getTimestampFin() {
        return heureFin;
    }

    /**
     * Supprime une livraison de la fenêtre. Supprime rien si l'identifiant n'existe pas dans la fenêtre
     * @param livrasionId L'identifiant de la fenêtre
     */
    protected void effacerLivraison(int livrasionId) {
        livraisons.remove(livrasionId);
    }

    /** Ajoute une livraison dans la fenêtre, supprime l'ancienne si une avec le même identifiant existait déjà
     * @param id L'identifiant de la nouvelle livraison
     * @param livraison La nouvelle livraison
     */
    public void ajouterLivraison(int id, Livraison livraison) {
        livraisons.put(id, livraison);
    }

    /** Retourne les paires d'identifiants et livraisons
     * @return Une map en lecture seule des identifiants et livraisons associées
     */
    public Map<Integer, Livraison> getListeLivraisons() {
        return Collections.unmodifiableMap(livraisons);
    }

    /**
     * Retourne une livraison
     * @param idLivraison L'identifiant de la livraison à retourner
     * @return La livraison ou null si celle-ci n'existe pas
     */
    public Livraison getLivraison(int idLivraison) {
        return livraisons.get(idLivraison);
    }

    @Override
    public String toString() {
        return "Fenetre{"
                + "heureDebut=" + heureDebut
                + ", heureFin=" + heureFin
                + ", livraisons=" + livraisons
                + '}';
    }

    /**
     * Pour intersection-livraison, on va calculer une liste de chemins vers
     * tous les noeuds de cette fenêtre et de la fenêtre suivante
     *
     * @param plan Le plan de la ville
     * @param graphe in/out Le graphe de réalisation
     * @param fNext La prochaine fenêtre
     */
    public void calculerChemins(PlanDeVille plan, GrapheRealisation graphe, Fenetre fNext) {
        //Récupère toutes les livraisons avec les quels on doit calculer le plus court chemin.
        Set<Integer> intersectionsRecherchee = new HashSet<>();

        Map<Integer, Collection<Livraison>> intersectionVersLivraisons = new LinkedHashMap<>();
        //remplit liste de toutes les livraisons prevus, dans un Map dont kle est l'intersectionId de la livraison
        for (Livraison livraison : livraisons.values()) {
            if (intersectionVersLivraisons.get(livraison.getAdresse()) == null)
                //ajouter nouveau entry
                intersectionVersLivraisons.put(livraison.getAdresse(), new LinkedList<>());

            intersectionVersLivraisons.get(livraison.getAdresse()).add(livraison);
        }
        for (Livraison livraison : fNext.getListeLivraisons().values()) {
            if (intersectionVersLivraisons.get(livraison.getAdresse()) == null)
                //ajouter nouveau entry
                intersectionVersLivraisons.put(livraison.getAdresse(), new LinkedList<>());

            intersectionVersLivraisons.get(livraison.getAdresse()).add(livraison);
        }

        //stoque toutes les livraisons de cette et la prochaine fenetre
        Collection<Livraison> livraisonsArrivees = new LinkedList<>();
        livraisonsArrivees.addAll(livraisons.values());
        livraisonsArrivees.addAll(fNext.getListeLivraisons().values());
        for (Livraison l : livraisonsArrivees) {
            intersectionsRecherchee.add(l.getAdresse());
        }

        // pour chaque livraison de cette fenetre:
        for (Livraison livraison : livraisons.values()) {
            // recupere l'intersection
            Intersection intersection = plan.getIntersection(livraison.getAdresse());

            // utilise dijkstra pour calculer le plus court chemin vers chaque intersection...
            // TODO: Change order: either dont calculate or stoque, but don't calculate something and throw it away afterwards.
            // TODO: Better store everything calculated...
            for (Chemin chemin : dijkstra(intersection, plan)) {
                // ... et stoque le chemin ssi l'intersection correspond a une livrasion de cette ou la prochaine fenetre
                if (intersectionsRecherchee.contains(chemin.getIdFin())) {
                    //TODO: trouver toutes les livraisons qui sont prevu pour l'intersection a la fin du chemin.
                    chemin.getIdFin();

                    for (Livraison cibleLivraison : intersectionVersLivraisons.get(chemin.getIdFin())) //TODO: For optimasation: Cannot store all resultats in one matriax, TSP relies on receiving livr., only.
                    {
                        graphe.setChemin(chemin, livraison.getId(), cibleLivraison.getId());
                    }
                }

            }
        }
    }

    /** Algorithme de dijkstra
     * @param intersectionDepart L'intersection de départ
     * @param plan Le plan de la ville
     * @return Une collection des chemins à empreinter
     */
    public static Collection<Chemin> dijkstra(Intersection intersectionDepart, PlanDeVille plan) {
        //INITIALISATION

        //Map de chemin intermédiaires et finaux
        //La clé est l'id de l'intersection (cible) au quel correspond le chemin 
        //le chemin contient tous tronçons par lesquels on est passé 
        //+ l'id de l'interserction de départ et la clef de la map comme intersection d'arrivé
        //TODO changer en set
        Map<Integer, Chemin> mapChemins = new HashMap<>();

        // chemin vers se meme
        mapChemins.put(intersectionDepart.getId(),
                new Chemin(0, new ArrayList<>(), intersectionDepart.getId(), intersectionDepart.getId()));

        Comparator<Intersection> comparator = new CoutComparator();

        //Liste des toutes les intersection qui sont encore a faire avec dijkstra
        PriorityQueue<Intersection> queue = new PriorityQueue<>(1000, comparator);
        queue.add(intersectionDepart);

        //ALGO
        while (!queue.isEmpty()) {
            Intersection intersection = queue.poll();

            //Pour toutes les intersections suivantes
            for (Intersection intersectionSuivante : getListeIntersectionSuivante(intersection, plan)) {

                //On récupére le chemin en cours de contruction dans la map
                Chemin chemin = mapChemins.get(intersection.getId());

                //Calcule d'information pour le nouveau chemin
                //On créer une nouvelle arrayList pour ne pas modifier celles qu'on récupères
                ArrayList<Troncon> listeTronconsEnCours = new ArrayList<>();
                listeTronconsEnCours.addAll(chemin.getTroncons());

                //Tronçon qu'on va traverser en allant au suivant
                Troncon tronconTraverser = intersection.getTroncon(intersectionSuivante.getId());
                listeTronconsEnCours.add(tronconTraverser);

                float cout = chemin.getCout() + tronconTraverser.getCout();

                //Insertion ou remplacement si le cout est inf�rieur du nouveau chemin dans la map
                Chemin cheminDejaInsere = mapChemins.get(intersectionSuivante.getId());
                if (cheminDejaInsere != null) {
                    if (cheminDejaInsere.getCout() > cout)
                        mapChemins.put(intersectionSuivante.getId(),
                                new Chemin(cout, listeTronconsEnCours, chemin.getIdDepart(), intersectionSuivante.getId()));
                }
                else {
                    queue.add(intersectionSuivante);
                    mapChemins.put(intersectionSuivante.getId(),
                            new Chemin(cout, listeTronconsEnCours, chemin.getIdDepart(), intersectionSuivante.getId()));
                }
            }
            //intersection est maintenant noir. On peut maintenant le pop de la liste des intersection recherché

        }

        //Parcourir la map pour récupérer juste la liste des chemins finaux
        return mapChemins.values();
    }

    /**
     * Retourne une liste des intersections suivant l'intersection en paramètre
     *
     * @param intersection L'intersection de début
     * @param plan Le plan de ville
     * @return Une liste d'intersection
     */
    public static List<Intersection> getListeIntersectionSuivante(Intersection intersection, PlanDeVille plan) {
    	List<Intersection> intersections = new ArrayList<>();

        for (Integer idIntersection : intersection.getTroncons().keySet()) {
            intersections.add(plan.getIntersection(idIntersection));
        }

        return intersections;
    }

    /** Supprime une livraison si celle si existe, sinon rien ne se passe
     * @param livraisonId L'identifiant de la livraison à supprimer
     */
    void supprimerLivraison(int livraisonId) {
        if (livraisons.keySet().contains(livraisonId)) {
            livraisons.remove(livraisonId);
        }
    }

    /**
     * Retourne le nombre de livraisons
     * @return Le nombre de livraisons
     */
    public int getNbLivraison() {
        return livraisons.size();
    }
    
    /**
     * Classe utile pour la priority Queue. Le but est de lui fournir un
     * comparateur pour qu'elle s'ordonne. L'intersection en paramètres ne doit
     * contenir qu'un seul tronçon
     *
     * @author Djowood
     */
    public static class CoutComparator implements Comparator<Intersection> {

        @Override
        public int compare(Intersection x, Intersection y) {
            if (x.getMinCout() != Float.MAX_VALUE && y.getMinCout() != Float.MAX_VALUE) {
                //Retourne un nombre négatif (donc faux si le coût est inférieur), positif si suppérieur et 0 si égale.
                return (int) (x.getMinCout() - y.getMinCout());
            }
            return 0;
        }
        
    }
    
}
