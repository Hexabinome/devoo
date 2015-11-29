package modele.xmldata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import modele.business.TSP;
import modele.business.TSP1;

/**
 *
 * @author Maxou
 */
public class Modele implements ModeleLecture
{

    private final PlanDeVille plan;
    private final Demande demande;
    private GrapheRealisation graphe;
    private TSP tsp;

    private int customLivraisonCompteur = Integer.MAX_VALUE;

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

    public List<List<Livraison>> getLivraisonsTournee()
    {
        return livraisonTournee;
    }

    /**
     *
     * @param idLivraison
     */
    public int removeLivraison(int idLivraison)
    {
        //identifier la livraison
        Livraison liv = demande.identifierLivraison(idLivraison);

        //recuperer l'id de la livraison avant
        int idLivraisonAvant = recupererLivraisonApresOuAvant(liv, true).getId();

        //parcourir la tournee calculle par tsp et supprimer la livraison specifiee
        livraisonTournee.stream().forEach((fenetre) -> {
            fenetre.remove(liv);
        });

        //supprimer la livraision (dans la demande)
        demande.supprimerLivraision(idLivraison);

        //mettre a jour la tournee et les horaires
        intersectionTournee = creerIntersectionTournee();
        remplirHoraires();

        return idLivraisonAvant;
    }

    /**
     * Ajoute la solution dans le tsp, aprés le previousId. Ajoute aussi
     * l'intersection dans la fenêtre de previousId.
     *
     * @param idLivraisonAvant
     * @param intersectionId
     */
    public void addLivraison(int idLivraisonAvant, Fenetre fenetre, Livraison nouvelleLivraison)
    {
        // d'abord recuperer la livraison avant
        Livraison livraisonAvant = demande.identifierLivraison(idLivraisonAvant);

        //manipuler la tournee cree par TSP en ajoutant la nouvelle livraison dans le bon endroit (apres la livraison precendente)
        fenetre.ajouterLivraison(nouvelleLivraison.getId(), nouvelleLivraison);

        //calculer et stoquer le chemin vers la nouvelle livraison
        //TODO: c'est ppossible qu;on a pas beson de faire ca, le permier appel de dijkstra a deja calcule ca, si on stoquait le resultat on peut eviter cette re-calculation
        Collection<Chemin> cheminsSortantDeLivraisonAvant = Fenetre.dijkstra(plan.getIntersection(livraisonAvant.getAdresse()), plan);
        cheminsSortantDeLivraisonAvant.stream().filter((c) -> (c.getIdFin() == nouvelleLivraison.getAdresse())).forEach((c) -> {
            graphe.setChemin(c, livraisonAvant.getId(), nouvelleLivraison.getId());
        });

        //calculer et stoquer le chemin de la nouvelle livraison vers la livraison apres
        Livraison livraisonApres = recupererLivraisonApresOuAvant(livraisonAvant, false);
        int idIintersectionApres = livraisonApres.getAdresse();
        Collection<Chemin> cheminsSortantDeNouvelleLiv = Fenetre.dijkstra(plan.getIntersection(nouvelleLivraison.getAdresse()), plan);
        cheminsSortantDeNouvelleLiv.stream().filter((c) -> (c.getIdFin() == idIintersectionApres)).forEach((c) -> {
            graphe.setChemin(c, nouvelleLivraison.getId(), livraisonApres.getId());
        });

        //MAJ de la tournee (par rapport aux livraisons)
        insererLivraisionDansTournee(livraisonAvant, nouvelleLivraison);

        //MAJ de la tournee (par rapport aux intersections)
        intersectionTournee = creerIntersectionTournee();

        //MAJ des horraires de passage
        remplirHoraires();
    }

    /**
     * Ajouter une nouvelle livraison dans la bonne position (c'est a dire
     * directement apres une livraison deja existant) dans la tournee
     *
     * @param livraisonAvant
     * @param nouvelleLivraison
     */
    private void insererLivraisionDansTournee(Livraison livraisonAvant, Livraison nouvelleLivraison)
    {
        livraisonTournee.stream().filter((sousTournee) -> (sousTournee.contains(livraisonAvant))).forEach((sousTournee) -> {
            int position = sousTournee.indexOf(livraisonAvant);
            sousTournee.add(position + 1, nouvelleLivraison);
        });
    }

    /**
     * Cherche la livraison directement apres une livraison contenu dans la
     * tournee. C'est possible que cette livraion est dans la fenetre apres.
     *
     * @param livraison
     * @return
     */
    private Livraison recupererLivraisonApresOuAvant(Livraison livraison, boolean avant)
    {
        //d'abourd on va creer une nouvelle liste  contennant toutes les livraisons (sans connaisance des fenetreas). Celle ci est plus facile a iterer.
        List<Livraison> totalList = new LinkedList<>();
        for (List<Livraison> fenetre : livraisonTournee) {
            totalList.addAll(fenetre);
        }
        Iterator<Livraison> iter = totalList.iterator();
        Livraison liv = iter.next();
        Livraison livAvant = null;
        while (liv != livraison && iter.hasNext()) {
            livAvant = liv;
            liv = iter.next();
        }
        if (!avant) {
            if (iter.hasNext())
                return iter.next();
            throw new RuntimeException("Livraison ne fait pas parti de la tounree");
        }
        else
            return livAvant;

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

    public void remplirHoraires()
    {
        int heure = demande.getFenetres().get(1).getTimestampDebut();
        int intersectionCourante = demande.getEntrepot().getId();

        demande.getFenetres().get(0).getLivraison(-1).setHoraireDePassage(heure);

        for (int iFenetre = 1; iFenetre < demande.getFenetres().size(); ++iFenetre) {
            Fenetre fenetre = demande.getFenetres().get(iFenetre);
            List<Integer> dejaVisites = new ArrayList<>();

            for (int prochaineIntersection : intersectionTournee.get(iFenetre - 1)) {
                float dureeTroncon = plan.getIntersection(intersectionCourante).getTroncon(prochaineIntersection).getDuree();

                heure += dureeTroncon;
                intersectionCourante = prochaineIntersection;

                // Mise à jour de l'horaire de passage si on est sur une livraison
                for (Livraison l : fenetre.getListeLivraisons().values()) {
                    if (l.getAdresse() == intersectionCourante && !dejaVisites.contains(l.getAdresse())) {
                        l.setRetard(false);
                        if (heure < fenetre.getTimestampDebut())
                            heure = fenetre.getTimestampDebut();
                        else if (heure > fenetre.getTimestampFin())
                            l.setRetard(true);
                        l.setHoraireDePassage(heure);
                        dejaVisites.add(l.getAdresse());
                        break;
                    }
                }
            }
        }
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

    /**
     * Génère la feuille de route de la demande de livraison
     *
     * @return Une chaîne de caractère formatée correctement
     */
    @Override
    public String genererFeuilleDeRoute()
    {
        return GenerateurFeuilleDeRoute.genererFeuilleDeRoute(this, livraisonTournee);
    }

    /**
     * Quand on cree une nouvelle livraison on a besoin d'une id unique. Cette
     * compteur aide a recuperer cette id.
     *
     * @return
     */
    public int getProchainCustomLivraisonId()
    {
        return customLivraisonCompteur--;
    }

    /**
     *
     *
     * @param idLivraison1
     * @param idLivraison2
     */
    public void echangerLivraisons(int idLivraison1, int idLivraison2)
    {
        Livraison l1 = demande.identifierLivraison(idLivraison1);
        Livraison l2 = demande.identifierLivraison(idLivraison2);
        Livraison livrAvantL1 = recupererLivraisonApresOuAvant(l1, true);
        Livraison livrAvantL2 = recupererLivraisonApresOuAvant(l2, true);
        int clientId1 = l1.getClientId();
        int clientId2 = l2.getClientId();
        int intersection1 = l1.getAdresse();
        int intersection2 = l2.getAdresse();
        Fenetre f1 = demande.getFenetreDeLivraison(idLivraison1);
        Fenetre f2 = demande.getFenetreDeLivraison(idLivraison2);

        removeLivraison(idLivraison1);
        removeLivraison(idLivraison2);
        
        Livraison nouvelleLivraison1 = new Livraison(getProchainCustomLivraisonId(), clientId2, intersection2);
        Livraison nouvelleLivraison2 = new Livraison(getProchainCustomLivraisonId(), clientId1, intersection1);
        
        addLivraison(livrAvantL1.getId(), f1, nouvelleLivraison1);
        addLivraison(livrAvantL2.getId(), f2, nouvelleLivraison2);
    }
/*
    public void echangerLivraisons(int idLivraison1, int idLivraison2, int nouvelleId1, int nouvelleId2)
    {

    }
*/
}
