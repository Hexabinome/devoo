package modele.xmldata;
import java.util.List;
import java.util.ArrayList;
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
    	
    	int indiceLivraison = graphe.getIndiceFromIdLivraison(idLivraison);
    	tsp.supprimerSolution(indiceLivraison);
    	
    	//TODO tester si idLivraison est tous seul dans sa fenêtre.
    	//Refaire dijkstra entre fenêtre d'avant et fenêtre suivante et tsp.
    }

    /**
     * Ajoute la solution dans le tsp, aprés le previousId. 
     * Ajoute aussi l'intersection dans la fenêtre de previousId.
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
    	int indiceLivraison = graphe.getIndiceFromIdLivraison(previousId);
    	
    	tsp.ajouterSolution(indiceLivraison, indiceLivraison);
    	//On ajout la solution dans la fenêtre
    	for(int iFenetre = 0; iFenetre < demande.getFenetres().size(); iFenetre++)
    	{
    		//On a trouvé la fenêtre qui contient la previousId livraison
    		if(((ArrayList<Fenetre>)demande.getFenetres()).get(iFenetre).getLivraisons().get(previousId) !=null)
    		{
    			Livraison livraison = new Livraison(((ArrayList<Fenetre>)demande.getFenetres()).get(iFenetre).getLivraisons().size(), 0, intersectionId);
    			((ArrayList<Fenetre>)demande.getFenetres()).get(iFenetre).getLivraisons().put(livraison.getId(), livraison);
				
				//TODO à optimiser
    			if(iFenetre == demande.getFenetres().size()-1)
    				((ArrayList<Fenetre>)demande.getFenetres()).get(iFenetre).calculerChemins(plan, graphe, ((ArrayList<Fenetre>)demande.getFenetres()).get(0));
    			else
    				((ArrayList<Fenetre>)demande.getFenetres()).get(iFenetre).calculerChemins(plan, graphe, ((ArrayList<Fenetre>)demande.getFenetres()).get(iFenetre+1));
    		}
    			
    	}
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
