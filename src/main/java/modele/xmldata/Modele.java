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
    private List<List<Integer>> tournee;

    public Modele(PlanDeVille plan, Demande demande)
    {
        this.plan = plan;
        this.demande = demande;
        tsp = null;
        tournee = null;
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
        //TODO: (cette methode ne fait pas parti du noyau... pas prioritaire maintenant.)
        // Quoi on doit faire ici:
        // + effacer la livraison dans demande
        // + mis a jour du graphe (supprimer les liasion qui utilisent l'intersection utilise pas la livraison)
        // + soit (1) effacer tournee / soit (2) recalculer tournee avec TSP -> encore a discuter mais a mon avis (2)

        /*
         int indiceLivraison = graphe.getIndiceFromIdLivraison(idLivraison);
         tsp.supprimerSolution(indiceLivraison);
         */
        //TODO tester si idLivraison est tous seul dans sa fenêtre.
        //Refaire dijkstra entre fenêtre d'avant et fenêtre suivante et tsp.
    }

    /**
     * Ajoute la solution dans le tsp, aprés le previousId. Ajoute aussi
     * l'intersection dans la fenêtre de previousId.
     *
     * @param previousId
     * @param intersectionId
     */
    public void addLivraison(int previousId, int intersectionId)
    {
        //TODO: (cette methode ne fait pas parti du noyau... pas prioritaire maintenant.)
        // Quoi on doit faire ici:
        // + ajouter la livraison dans la bonne fenter dans demande
        // + mis a jour du graphe (calculer distance vers chque intersection deja utilise pour une livraison dans graphe)
        // + soit (1) effacer tournee / soit (2) recalculer tournee avec TSP -> encore a discuter mais a mon avis (2)

        /*
         int indiceLivraison = graphe.getIndiceFromIdLivraison(previousId);

         tsp.ajouterSolution(indiceLivraison, indiceLivraison);
         //On ajout la solution dans la fenêtre
         for (int iFenetre = 0; iFenetre < demande.getFenetres().size(); iFenetre++) {
         //On a trouvé la fenêtre qui contient la previousId livraison
         if (((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).getListeLivraisons().get(previousId) != null) {
         Livraison livraison = new Livraison(((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).getListeLivraisons().size(), 0, intersectionId);
         ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).getListeLivraisons().put(livraison.getId(), livraison);

         //TODO à optimiser (pas desoin de rappeller dijkstra pour toutes les intersections
         if (iFenetre == demande.getFenetres().size() - 1)
         ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).calculerChemins(plan, graphe, ((ArrayList<Fenetre>) demande.getFenetres()).get(0));
         else
         ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).calculerChemins(plan, graphe, ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre + 1));
         }

         }*/
    }

    public void calculerTournee()
    {
        graphe = demande.creerGraphe(plan);
        graphe.creerInverseLivraisonDictionnaire();

        // apres avoir calcule le graphe il faut qu'on appele TSP ici.
        tsp = new TSP1();
        tsp.chercheSolution(1000, graphe);
        tournee = creerIntersectionTournee();

        //des que le TSP a fini il faut stoquer les horaires de passage dans l'objet demande
        remplirHoraires();
    }

    private List<Livraison> getLivraisonFromSolutionTsp(Fenetre fenetre, int indiceDebutSolutionTsp)
    {
        List<Livraison> listLivraison = new ArrayList<>();

        for (int iSolution = indiceDebutSolutionTsp; iSolution < indiceDebutSolutionTsp + fenetre.getNbLivraison(); iSolution++) {
            int idLivraison = graphe.getIdLivraisonParIdMatrice(tsp.getSolution(iSolution));
            listLivraison.add(fenetre.getLivraison(idLivraison));
        }

        return listLivraison;
    }

    private List<List<Integer>> creerIntersectionTournee()
    {
        if (tsp == null)
            return null;

        tournee = new LinkedList<>();

        // compteur pour iterer sur la solution cree par TSP
        int compteurSolutionTSP = 1;

        List<Fenetre> listFenetres = new LinkedList<>();

        listFenetres.addAll(demande.getFenetres());
        Livraison depart = listFenetres.get(0).getListeLivraisons().values().iterator().next();
        Livraison entrepot = depart;

        // pour chaque fenetre... (sauf le premier qui contient que l'entrepot, on l'enleve)
        listFenetres.remove(0);
        for (Fenetre fenetre : listFenetres) {

            List<Livraison> fenetreTspLivraisons = getLivraisonFromSolutionTsp(fenetre, compteurSolutionTSP);
            compteurSolutionTSP += fenetreTspLivraisons.size();

            List<Integer> sousTournee = creerSourTournee(depart, fenetreTspLivraisons);
            depart = fenetreTspLivraisons.get(fenetreTspLivraisons.size() - 1);

            //ajouter la liste cree pour cette fenetre a la liste principale
            tournee.add(sousTournee);
        }

        //ajouter une dernier fenetre fictive pour retourner a l'entrepot
        List retourEntrepotFenetre = new LinkedList<>();
        retourEntrepotFenetre.add(entrepot);
        List<Integer> retourEntrepotSousTournee = creerSourTournee(depart, retourEntrepotFenetre);
        tournee.add(retourEntrepotSousTournee);

        return tournee;
    }

    private List<Integer> creerSourTournee(Livraison depart, List<Livraison> sousTourneeLivraisons)
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
        if (tournee == null)
            return null;

        List<List<Integer>> inmodifiableTournee = new LinkedList<>();

        for (List<Integer> sousTournee : tournee) {
            inmodifiableTournee.add(Collections.unmodifiableList(sousTournee));
        }
        return Collections.unmodifiableList(inmodifiableTournee);

    }

}
