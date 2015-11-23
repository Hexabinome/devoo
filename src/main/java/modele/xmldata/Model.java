package modele.xmldata;

import java.util.ArrayList;
import java.util.List;

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

    public Model(PlanDeVille plan, Demande demande)
    {
        this.plan = plan;
        this.demande = demande;
    }

    @Override
    public Graphe getGraphe()
    {
        return graphe;
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

    public List<Chemin> calculerTournee()
    {
        graphe = demande.creerGraphe(plan);

        // apres avoir calcule le graphe il faut appeler TSP ici.
        TSP tsp = new TSP1();
        tsp.chercheSolution(1000, graphe);

        ArrayList<Integer> listeIdSolutions = new ArrayList<Integer>();
        listeIdSolutions.add(tsp.getSolution(0));
        
        ArrayList<Chemin> listeChemin = new ArrayList<Chemin>();
        
        //Récupère toutes les solutions
        for(int iSolution=1; iSolution < graphe.getNbSommets(); iSolution ++)
        {
        	listeIdSolutions.add(tsp.getSolution(iSolution));
        	listeChemin.add(graphe.getCheminGrapheIndice(listeIdSolutions.get(iSolution-1), listeIdSolutions.get(iSolution)));
        }
        
        return listeChemin;
    }
}
