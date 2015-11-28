package modele.xmldata;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import modele.business.TSP;
import modele.business.TSP1;

/**
 *
 * @author Max Schiedermeier
 */
public class Modele implements ModeleLecture
{

    private final PlanDeVille plan;
    private final Demande demande;
    private GrapheRealisation graphe;
    private TSP tsp;

    //cette liste des listes stoque pour chque fenetre les livraisons a effecturer dans une tournee calculle par TSP (sans l'entrepot au debout et a la fin de sla tournee)
    private List<List<Livraison>> livraisonTournee;

    //cette liste des listes stoque pour chque fenetre les intersetions a parcourir dans une tournee calculle par TSP (incluant l'entrepot au debout et a la fin de sla tournee)
    private List<List<Integer>> intersectionTournee;

    public Modele(PlanDeVille plan, Demande demande)
    {
        this.plan = plan;
        this.demande = demande;
        tsp = null;
        intersectionTournee = null;
    }

    public void setGraphe(GrapheRealisation graphe)
    {
        this.graphe = graphe;
    }

    @Override
    public PlanDeVille getPlan()
    {
        return plan;
    }

    @Override
    public Demande getDemande()
    {
        return demande;
    }

    /**
     *
     * @param idLivraison
     */
    public void removeLivraison(int idLivraison)
    {
        //parcourir la tournee calculle par tsp et supprimer la livraison specifiee
        livraisonTournee.stream().forEach((fenetre) -> {
            Livraison livraison = null;

            for (Livraison l : fenetre) {
                if (l.getId() == idLivraison)
                    livraison = l;
            }

            fenetre.remove(livraison);
        });

        //mettre a jour la tournee et les horaires
        intersectionTournee = creerIntersectionTournee();
        remplirHoraires();
    }

    /**
     * Ajoute la solution dans le tsp, aprés le previousId. Ajoute aussi
     * l'intersection dans la fenêtre de previousId.
     *
     * @param idLivraisonAvant
     * @param intersectionId
     */
    public void addLivraison(int idLivraisonAvant, int intersectionId)
    {

    }

    public void calculerTournee()
    {
        graphe = demande.creerGraphe(plan);
        graphe.creerInverseLivraisonDictionnaire();

        // apres avoir calcule le graphe il faut qu'on appele TSP ici.
        tsp = new TSP1();
        tsp.chercheSolution(1000, graphe);

        //stoque l'ordre des livraisons (calcule par TSP) dans le modele
        creerLivraisonTournee();

        //creer a parti des listes des livraisonsid, la tournee (granularite intersecitons)
        intersectionTournee = creerIntersectionTournee();
        //egalement calculer les horaires de passage pour chaque livraison
        remplirHoraires();
    }

    /**
     * Cette methode sert a creer une liste des livraionsID dans l'ordre
     * determine par TSP, pour une fenetre.
     *
     * @param fenetre
     * @param indiceDebutSolutionTsp
     * @return
     */
    private List<Livraison> getLivraisonFromSolutionTsp(Fenetre fenetre, int indiceDebutSolutionTsp)
    {
        List<Livraison> listLivraison = new ArrayList<>();

        for (int iSolution = indiceDebutSolutionTsp; iSolution < indiceDebutSolutionTsp + fenetre.getNbLivraison(); iSolution++) {
            int idLivraison = graphe.getIdLivraisonParIdMatrice(tsp.getSolution(iSolution));
            listLivraison.add(fenetre.getLivraison(idLivraison));
        }

        return listLivraison;
    }

    /**
     * Cette methode convert le resultat du TSP dans une liste des listes dont
     * chaque liste interne represente les livraisons a effectuer dans une
     * fenetre. L'ordre des livraisons correspond a l'ordre determine par TSP.
     *
     * Cette methode doit etre appele apres que une seul fois (directement apres
     * le fin du calcule TSP). On n'a plus besoin de cette methode quand
     * l'utilisateur decide de manipuler la tournee calculle a la main.
     */
    private void creerLivraisonTournee()
    {
        if (tsp == null)
            throw new RuntimeException("TSP n'a pas encore calcule la tournee");

        livraisonTournee = new LinkedList<>();

        // compteur pour iterer sur la solution cree par TSP
        int compteurSolutionTSP = 0;
        List<Fenetre> listFenetres = new LinkedList<>();
        listFenetres.addAll(demande.getFenetres());

        //ajouter les livraisons de chaque fenetre dans l'ordre calcule par TSP
        for (Fenetre fenetre : listFenetres) {

            List<Livraison> fenetreTspLivraisons = getLivraisonFromSolutionTsp(fenetre, compteurSolutionTSP);
            compteurSolutionTSP += fenetreTspLivraisons.size();
            livraisonTournee.add(fenetreTspLivraisons);
        }

        //ajouter une derniere fentre avec une seule livraison (retour a l'entrepot)
        List<Livraison> retourEntrepot = new LinkedList<>();
        //recuperer la livraison fantastique du premier fenetre (seul livraison au premiere fenetre = entrepot)
        retourEntrepot.add(listFenetres.get(0).getListeLivraisons().values().iterator().next());
        livraisonTournee.add(retourEntrepot);
    }

    /**
     * Cette methode utilise la liste des listes des livraisons pour calculer
     * une tournee (granularite intersection). Ca nous permet de facilement
     * mettre ajour la tournee si le utilisateur decide d'ajouter une nouvelle
     * livraisons.
     */
    private List<List<Integer>> creerIntersectionTournee()
    {
        intersectionTournee = new LinkedList<>();

        //depart est l'entrepot (seul livraison dans premiere fenetre)
        Livraison depart = livraisonTournee.get(0).get(0);

        boolean entrepot = true;
        for (List<Livraison> fenetre : livraisonTournee) {
            if (entrepot)
                //on doit sauter la premiere fenetre (l'algo desous utilise toujours une fenetre et sa fenetre precedente)
                entrepot = false;
            else {
                List<Integer> sousTournee = creerSousTournee(depart, fenetre);

                //depart de prochaine fenetre est la derniere livraison de cette fenetre
                depart = fenetre.get(fenetre.size() - 1);

                //ajouter la liste cree pour cette fenetre a la liste principale
                intersectionTournee.add(sousTournee);
            }

        }

        return intersectionTournee;
    }

    private List<Integer> creerSousTournee(Livraison depart, List<Livraison> sousTourneeLivraisons)
    {
        // Ce liste represente tous les intersections (dans la bonne ordre) qui on doit parcourir pour effecture les livraisons prevus.
        List<Integer> sousTournee = new LinkedList<>();

        //Pour chaque chemin entre les livraisons prevus: ajoute les intersection sur le chemin a la sous tournee
        for (Livraison arrivee : sousTourneeLivraisons) {
            Chemin chemin = graphe.getChemin(depart.getId(), arrivee.getId());

            //pour toutes les troncons sur le chemin, ajoute le arrivee
            for (Troncon troncon : chemin.getTroncons()) {
                sousTournee.add(troncon.getIdDestination());
            }

            //mis a jour du depart
            depart = arrivee;
        }

        return sousTournee;
    }

    private void remplirHoraires()
    {
        //TODO: replace mock implementation by data actually derived from TSP sollution
        demande.getFenetres().stream().forEach((fenetre) -> {
            fenetre.getListeLivraisons().values().stream().forEach((livraison) -> {
                livraison.setHoraireDePassage((int) (Math.random() * 3600));
            });
        });
    }

    @Override
    public List<List<Integer>> getTournee()
    {
        if (intersectionTournee == null)
            return null;

        List<List<Integer>> inmodifiableTournee = new LinkedList<>();

        for (List<Integer> sousTournee : intersectionTournee) {
            inmodifiableTournee.add(Collections.unmodifiableList(sousTournee));
        }
        return Collections.unmodifiableList(inmodifiableTournee);

    }

}
