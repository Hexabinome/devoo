package modele.xmldata;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
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
        int indiceLivraison = graphe.getIndiceFromIdLivraison(previousId);

        tsp.ajouterSolution(indiceLivraison, indiceLivraison);
        //On ajout la solution dans la fenêtre
        for (int iFenetre = 0; iFenetre < demande.getFenetres().size(); iFenetre++) {
            //On a trouvé la fenêtre qui contient la previousId livraison
            if (((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).getLivraisons().get(previousId) != null) {
                Livraison livraison = new Livraison(((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).getLivraisons().size(), 0, intersectionId);
                ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).getLivraisons().put(livraison.getId(), livraison);

                //TODO à optimiser (pas desoin de rappeller dijkstra pour toutes les intersections
                if (iFenetre == demande.getFenetres().size() - 1)
                    ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).calculerChemins(plan, graphe, ((ArrayList<Fenetre>) demande.getFenetres()).get(0));
                else
                    ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre).calculerChemins(plan, graphe, ((ArrayList<Fenetre>) demande.getFenetres()).get(iFenetre + 1));
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

        //les ids calcule par TSP sont unique, mais il s'agit pas des id's d;intersections. On utilise un dictionnaire pour les identifier.
        Map<Integer, Integer> graphDictionnaire = graphe.getIntersectionDictionnaire();

        //compteur pour iterer sur la solution cree par TSP
        int tspCompteur = 0;

        //initilaiser avec l'id de l'enrepot
        int livraisonDepart = tsp.getSolution(tspCompteur++);

        //pour chaque fenetre... (sauf le premier qui contient que l'entrepot)
        boolean premiereFenetre = true;
        for (Fenetre fenetre : demande.getFenetres()) {
            //ajouter toutes les intersecitons qui on doit parcourir pour realiser le resultat du TSP
            //(dans le premiere fentre il y a que l'entrepot)
            List<Integer> sousTournee = new LinkedList<>();

            // en cas de premier fenetre
            if (premiereFenetre) {
                //ajouter que l'entrepot
                premiereFenetre = false;
                sousTournee.add(graphDictionnaire.get(livraisonDepart));
            }
            // pour toutes les autres fenetres
            else
                // TODO: Verifier que ca se plante pas si il y a deux livraisons pour une seul intersection
                // recoupererer les prochaines n elements de la sollution de tsp, dont n est egal a la numero des livraisons attendu dans cette fenetre. Ca nous donne l'ordre pour parcourir les livraisons de cette fenetre.
                for (int livraisonComteur = 0; livraisonComteur < fenetre.getLivraisons().size(); livraisonComteur++) {
                    //recuperer prochain livraison prevu
                    int livraisonArrivee = tsp.getSolution(tspCompteur++);

                    //recuperer chemin etrne depart et arrivee
                    Chemin chemin = graphe.getCheminGrapheIndice(livraisonDepart, livraisonArrivee);

                    //ajouter chaque intersection qui on passe en suivant chemin
                    for (Troncon troncon : chemin.getTroncons()) {
                        sousTournee.add(troncon.getIdDestination());
                    }

                    //mis a jour de depart et arrivee
                    livraisonDepart = livraisonArrivee;
                }

            //ajouter la liste cree pour cette fenetre a la liste principale
            tournee.add(sousTournee);
        }

        /*
         int livraisonArrivee = tsp.getSolution(0);
         sousTournee.add(graphDictionnaire.get(tsp.getSolution(0)));

         //pour chaque sollution calculle par TSP...
         for (int i = 1; i < graphDictionnaire.keySet().size(); i++) {

         livraisonDepart = livraisonArrivee;
         livraisonArrivee = tsp.getSolution(i);

         // ... ajouter tous les intersections sur le chemin entre depart et arrive
         Chemin chemin = graphe.getCheminGrapheIndice(livraisonDepart, livraisonArrivee);
         for (Troncon troncon : chemin.getTroncons()) {
         sousTournee.add(troncon.getIdDestination());
         }
         }

         //TODO: vorifier si on doit retourne a l'entrepot a la fin
         tournee.add(sousTournee);
         */ return tournee;

    }

    private void remplirHoraires()
    {
        //TODO: replace mock implementation by data actually derived from TSP sollution
        demande.getFenetres().stream().forEach((fenetre) -> {
            fenetre.getLivraisons().values().stream().forEach((livraison) -> {
                livraison.setHoraireDePassage((int) (Math.random() * 3600));
            });
        });
    }

}
