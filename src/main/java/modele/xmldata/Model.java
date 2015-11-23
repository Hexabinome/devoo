package modele.xmldata;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import modele.business.TSP;
import modele.business.TSP1;

/**
 *
 * @author Max Schiedermeier
 */
public class Model implements ModelLecture
{

    private final PlanDeVille plan;
    private final Demande demande;
    private GrapheRealisation graphe;
    private TSP tsp;

    public Model(PlanDeVille plan, Demande demande)
    {
        this.plan = plan;
        this.demande = demande;
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

    public void removeLivraison(int id)
    {
        //TODO: (cette methode ne fait pas parti du noyau... pas prioritaire maintenant.)
        // Quoi on doit faire ici:
        // + effacer la livraison dans demande
        // + mis a jour du graphe (supprimer les liasion qui utilisent l'intersection utilise pas la livraison)
        // + soit (1) effacer tournee / soit (2) recalculer tournee avec TSP -> encore a discuter mais a mon avis (2)
    }

    public void addLivraison(int previousId, int intersectionId)
    {
        //TODO: (cette methode ne fait pas parti du noyau... pas prioritaire maintenant.)
        // Quoi on doit faire ici:
        // + ajouter la livraison dans la bonne fenter dans demande
        // + mis a jour du graphe (calculer distance vers chque intersection deja utilise pour une livraison dans graphe)
        // + soit (1) effacer tournee / soit (2) recalculer tournee avec TSP -> encore a discuter mais a mon avis (2)
    }

    public void calculerTournee()
    {
        graphe = demande.creerGraphe(plan);

        // apres avoir calcule le graphe il faut appeler TSP ici.
        tsp = new TSP1();
        tsp.chercheSolution(1000, graphe);

        //des que le TSP a fini il faut stoquer les horaires de passage dans l'objet demande
        remplirHoraires();
    }

    @Override
    public List<List<Integer>> getTournee()
    {
        List<List<Integer>> tournee = new LinkedList<>();
        int compteurDesLivraisons = 0;

        /**
         * pour toutes les fentres de la demande:
         */
        int livraisonDepart;
        int livraisonArrivee = tsp.getSolution(compteurDesLivraisons++);
        Iterator<Fenetre> iter = demande.getFenetres().iterator();
        while (iter.hasNext()) {
            List<Integer> routePartielle = new LinkedList<>();

            iter.next();
            int livraisonsFenetre = iter.next().getLivraisons().size();
            /**
             * demander TSP a quelle ordre on doit parcourir les destinations de
             * cette fenetre
             */
            while (livraisonsFenetre > 0) {
                livraisonsFenetre--;
                livraisonDepart = livraisonArrivee;
                livraisonArrivee = tsp.getSolution(compteurDesLivraisons);

                // demander Graphe du chemin pour passer du depart a l'arrivee
                Chemin chemin = graphe.getChemin(livraisonDepart, livraisonDepart);
                for (Troncon troncon : chemin.getTroncons()) {
                    routePartielle.add(troncon.getIdDestination());
                }

            }
            tournee.add(routePartielle);
        }
        return tournee;
    }

    private void remplirHoraires()
    {
        //TODO: replace mock implementation by data actually derived from TSP sollution
        demande.getFenetres().stream().forEach((fenetre) -> {
            fenetre.getLivraisons().values().stream().forEach((livraison) -> {
                livraison.setHoraireDePassage((int)(Math.random() * 3600));
            });
        });
    }

}
