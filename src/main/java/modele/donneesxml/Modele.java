package modele.donneesxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import modele.metier.TSP;
import modele.metier.TSP1;

/**
 * Classe interfaçant tout le modèle pour une instance de l'application
 * @author Maxou
 */
public class Modele implements ModeleLecture, Serializable {

    /** Le plan de la ville */
    private final PlanDeVille plan;
    
    /** La demande de livraison */
    private final Demande demande;
    /** Le graphe de réalisation */
    private GrapheRealisation graphe = null;
    private TSP tsp = null;
    
    /** Entier de début des nouvelles livraisons créées */
    private int customLivraisonCompteur = 420000;

    /** Liste de listes qui stoque pour chaque fenêtre les livraisons à effectuer dans une tournée calculée par TSP (sans l'entrepôt au début et a la fin de la tournée) */
    private List<List<Livraison>> livraisonTournee;

    /** Liste de listes qui stoque pour chaque fenêtre les intersections à parcourir dans une tournée calculée par TSP (incluant l'entrepôt au debut et à la fin de la tournée)  */
    private List<List<Integer>> intersectionTournee;

    /** Renseigne si une livraison a été supprimée dernièrement, pour pouvoir recalculer un identifiant de livraison en cas d'ajout */
    boolean aUneLivraisonSupprimer = false;

    /** Constructeur du modèle
     * @param plan Plan de la ville 
     * @param demande Demande de livraison
     */
    public Modele(PlanDeVille plan, Demande demande) {
        this.plan = plan;
        this.demande = demande;
    }

    /** Affecte un nouveau graphe
     * @param graphe
     */
    public void setGraphe(GrapheRealisation graphe) {
        this.graphe = graphe;
    }

    @Override
    public PlanDeVille getPlan() {
        return plan;
    }

    @Override
    public Demande getDemande() {
        return demande;
    }

    /**
     * @return La liste de listes de livraisons de la tournée
     */
    public List<List<Livraison>> getLivraisonsTournee() {
        return livraisonTournee;
    }

    /**
     * Supprime une livraison de la demande
     * @param idLivraison L'identifiant de la livraison à supprimer
     */
    public int supprimerLivraison(int idLivraison) {
        aUneLivraisonSupprimer = true;
        // Identifier la livraison
        Livraison liv = demande.identifierLivraison(idLivraison);

        // Récupérer l'id de la livraison avant
        Livraison livraisonAvant = recupererLivraisonApresOuAvant(liv, true);

        // Vérifier que la livriason avant connait le chemin vers la livraison après (il peut arriver que le chemin soit inconnu, 
        // si la livraison après est une livraison qui a été ajoutée à la main)
        Livraison livraisonApres = recupererLivraisonApresOuAvant(liv, false);
        if (graphe.getChemin(livraisonAvant.getId(), livraisonApres.getId()) == null) {
            Collection<Chemin> cheminsSortantDeLivraisonAvant = Fenetre.dijkstra(plan.getIntersection(livraisonAvant.getAdresse()), plan);
            //Pour tous les chemins sortant de la "livraison d'avant" dont l'id de fin est l'id de la nouvelle livraison, on l'ajout dans le graphe
            cheminsSortantDeLivraisonAvant.stream().filter(c -> (c.getIdFin() == livraisonApres.getAdresse())).forEach((c) -> 
                graphe.setChemin(c, livraisonAvant.getId(), livraisonApres.getId()));
        }

        // Parcourir la tournée calculée par tsp et supprimer la livraison specifiée
        livraisonTournee.stream().forEach(fenetre -> fenetre.remove(liv));

        // Supprimer la livraison (dans la demande)
        demande.supprimerLivraision(idLivraison);

        // Mettre à jour la tournée et les horaires
        intersectionTournee = creerIntersectionTournee();
        remplirHoraires();

        return livraisonAvant.getId();
    }

    /**
     * Ajoute la solution dans le tsp, après une livraison. Ajoute aussi
     * l'intersection dans la fenêtre spécifiée
     *
     * @param idLivraisonAvant L'identifiant de la livraison avant
     * @param fenetre La fenêtre de la nouvelle livraison
     * @param nouvelleLivraison La nouvelle livraison
     */
    public void ajouterLivraison(int idLivraisonAvant, Fenetre fenetre, Livraison nouvelleLivraison) {
        // D'abord recuperer la livraison avant
        Livraison livraisonAvant = demande.identifierLivraison(idLivraisonAvant);

        // Manipuler la tournee cree par TSP en ajoutant la nouvelle livraison dans le bon endroit (apres la livraison precendente)
        fenetre.ajouterLivraison(nouvelleLivraison);

        // Calculer et stoquer le chemin vers la nouvelle livraison
        //TODO: c'est possible que l'on ai pas besoin de faire cela, le premier appel de dijkstra l'a déjà fait.
        // Si l'on stoquait le résultat on peut éviter ce calcul
        Collection<Chemin> cheminsSortantDeLivraisonAvant = Fenetre.dijkstra(plan.getIntersection(livraisonAvant.getAdresse()), plan);
        // Pour tous les chemins sortant de la "livraison d'avant" dont l'id de fin est l'id de la nouvelle livraison, on l'ajout dans le graphe
        cheminsSortantDeLivraisonAvant.stream().filter(c -> (c.getIdFin() == nouvelleLivraison.getAdresse())).forEach((c) -> 
            graphe.setChemin(c, livraisonAvant.getId(), nouvelleLivraison.getId()));

        // Calculer et stoquer le chemin de la nouvelle livraison vers la livraison après
        Livraison livraisonApres = recupererLivraisonApresOuAvant(livraisonAvant, false);
        int idIintersectionApres = livraisonApres.getAdresse();
        Collection<Chemin> cheminsSortantDeNouvelleLiv = Fenetre.dijkstra(plan.getIntersection(nouvelleLivraison.getAdresse()), plan);
        cheminsSortantDeNouvelleLiv.stream().filter(c -> (c.getIdFin() == idIintersectionApres)).forEach((c) -> 
            graphe.setChemin(c, nouvelleLivraison.getId(), livraisonApres.getId()));

        // MAJ de la tournee (par rapport aux livraisons)
        insererLivraisionDansTournee(livraisonAvant, nouvelleLivraison);

        // MAJ de la tournee (par rapport aux intersections)
        intersectionTournee = creerIntersectionTournee();

        // MAJ des horaires de passage
        remplirHoraires();
    }

    /**
     * Ajouter une nouvelle livraison dans la bonne position (c'est a dire
     * directement après une livraison déjà existant) dans la tournée
     *
     * @param livraisonAvant La livraison précédente
     * @param nouvelleLivraison La nouvelle livraison
     */
    private void insererLivraisionDansTournee(Livraison livraisonAvant, Livraison nouvelleLivraison) {
        livraisonTournee.stream().filter(sousTournee -> (sousTournee.contains(livraisonAvant))).forEach((sousTournee) -> {
            int position = sousTournee.indexOf(livraisonAvant);
            sousTournee.add(position + 1, nouvelleLivraison);
        });
    }

    /**
     * Cherche la livraison directement après ou avant une livraison contenue dans la
     * tournée. C'est possible que cette livraion soit dans la fenêtre après.
     *
     * @param livraison La livraison de référence
     * @param avant Si vrai, on récupère la livraison précédente. Sinon la livraison suivante
     * @return La livraison précédente ou suivante
     */
    private Livraison recupererLivraisonApresOuAvant(Livraison livraison, boolean avant) {
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
            if (iter.hasNext()) {
                return iter.next();
            }
            throw new RuntimeException("Livraison ne fait pas parti de la tounree");
        }
        else {
            return livAvant;
        }

    }

    /** Calcul la tournée
     */
    public void calculerTournee() {
        graphe = demande.creerGraphe(plan);
        graphe.creerInverseLivraisonDictionnaire();

        // Après avoir calculé le graphe il faut qu'on appelle TSP
        tsp = new TSP1();
        tsp.chercheSolution(1000, graphe);

        // Stoque l'ordre des livraisons (calcule par TSP) dans le modèle
        creerLivraisonTournee();

        // Créer à partir des listes des identifiants de livraison, la tournée (granularité intersection)
        intersectionTournee = creerIntersectionTournee();
        
        // Egalement calculer les horaires de passage pour chaque livraison
        remplirHoraires();
    }

    /**
     * Cette méthode sert à créer une liste des identifiants de livraison dans l'ordre
     * determiné par TSP, pour une fenêtre.
     *
     * @param fenetre Le fenêtre cible
     * @param indiceDebutSolutionTsp L'indice de début pour le TSP
     * @return La liste de livraison dans l'ordre optimal pour la fenêtre
     */
    private List<Livraison> getLivraisonFromSolutionTsp(Fenetre fenetre, int indiceDebutSolutionTsp) {
        List<Livraison> listLivraison = new ArrayList<>();

        for (int iSolution = indiceDebutSolutionTsp; iSolution < indiceDebutSolutionTsp + fenetre.getNbLivraison(); iSolution++) {
            int idLivraison = graphe.getIdLivraisonParIdMatrice(tsp.getSolution(iSolution));
            listLivraison.add(fenetre.getLivraison(idLivraison));
        }

        return listLivraison;
    }

    /**
     * Cette méthode convertit le résultat du TSP dans une liste de listes.
     * Chaque liste interne représente les livraisons à effectuer dans une
     * fenêtre. L'ordre des livraisons correspond a l'ordre determiné par TSP.
     *
     * Cette méthode doit etre appelé directement après la fin du calcul TSP
     * On n'a plus besoin de cette methode quand l'utilisateur decide de
     * manipuler la tournée calculée à la main.
     */
    private void creerLivraisonTournee() {
        if (tsp == null) {
            throw new RuntimeException("TSP n'a pas encore calcule la tournee");
        }

        livraisonTournee = new LinkedList<>();

        // Compteur pour itérer sur la solution créée par TSP
        int compteurSolutionTSP = 0;
        List<Fenetre> listFenetres = new LinkedList<>();
        listFenetres.addAll(demande.getFenetres());

        // Ajouter les livraisons de chaque fenêtre dans l'ordre calculé par TSP
        for (Fenetre fenetre : listFenetres) {
            List<Livraison> fenetreTspLivraisons = getLivraisonFromSolutionTsp(fenetre, compteurSolutionTSP);
            compteurSolutionTSP += fenetreTspLivraisons.size();
            livraisonTournee.add(fenetreTspLivraisons);
        }

        // Ajouter une dernière fenêtre avec une seule livraison (retour a l'entrepôt)
        List<Livraison> retourEntrepot = new LinkedList<>();
        // Récupérer la livraison de la première fenêtre (seule livraison de la première fenêtre = entrepôt)
        retourEntrepot.add(listFenetres.get(0).getListeLivraisons().values().iterator().next());
        livraisonTournee.add(retourEntrepot);
    }

    /**
     * Cette méthode utilise la liste des listes des livraisons pour calculer
     * une tournée (granularité intersection). Cela nous permet de facilement
     * mettre à jour la tournee si le utilisateur décide d'ajouter ou supprimer
     * une nouvelle livraison.
     */
    private List<List<Integer>> creerIntersectionTournee() {
        intersectionTournee = new LinkedList<>();

        // Départ est l'entrepôt (seule livraison dans la première fenêtre)
        Livraison depart = livraisonTournee.get(0).get(0);

        boolean entrepot = true;
        for (List<Livraison> fenetre : livraisonTournee) {
            if (entrepot) {
                // On doit sauter la première fenêtre (l'algo desous utilise toujours une fenêtre et sa fenêtre précédente)
                entrepot = false;
            }
            else {
                List<Integer> sousTournee = creerSousTournee(depart, fenetre);

                // Départ de prochaine fenêtre est la dernière livraison de cette fenêtre
                depart = fenetre.get(fenetre.size() - 1);

                // Ajouter la liste créée pour cette fenêtre à la liste principale
                intersectionTournee.add(sousTournee);
            }

        }

        return intersectionTournee;
    }

    /** Créer une sous tournée
     * @param depart La livraison de départ de la sous-tournée
     * @param sousTourneeLivraisons La liste des livraisons faisant partie de cette sous tournée
     * @return La liste des intersections dans l'ordre optimal
     */
    private List<Integer> creerSousTournee(Livraison depart, List<Livraison> sousTourneeLivraisons) {
        // Cette liste représente toutes les intersections (dans le bon ordre) qu'on doit parcourir pour effectuer les livraisons prévues.
        List<Integer> sousTournee = new LinkedList<>();

        // Pour chaque chemin entre les livraisons prévues: ajoute les intersections sur le chemin à la sous tournée
        for (Livraison arrivee : sousTourneeLivraisons) {
            Chemin chemin = graphe.getChemin(depart.getId(), arrivee.getId());

            if (chemin == null) {
                throw new RuntimeException("Une action precedente a oublie a completer la graphe: Il n y a aucune chemin entre " + depart.getId() + " et " + arrivee.getId());
            }

            // Pour tous les tronçons sur le chemin, ajoute l'arrivée
            for (Troncon troncon : chemin.getTroncons()) {
                sousTournee.add(troncon.getIdDestination());
            }

            // Mise à jour du départ
            depart = arrivee;
        }

        return sousTournee;
    }

    /** Remplit les horaires de chaque livraison après le calcul de la tournée. Détermine s'il y a du retard ou non
     */
    public void remplirHoraires() {        
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
                        if (heure < fenetre.getTimestampDebut()) {
                            heure = fenetre.getTimestampDebut();
                        }
                        else if (heure > fenetre.getTimestampFin()) {
                            l.setRetard(true);
                        }
                        l.setHoraireDePassage(heure);
                        heure += 600; // Livraison = 10 minutes
                        dejaVisites.add(l.getAdresse());
                        break;
                    }
                }
            }
        }
    }

    @Override
    public List<List<Integer>> getTournee() {
        if (intersectionTournee == null) {
            return null;
        }

        List<List<Integer>> inmodifiableTournee = new LinkedList<>();

        for (List<Integer> sousTournee : intersectionTournee) {
            inmodifiableTournee.add(Collections.unmodifiableList(sousTournee));
        }
        return Collections.unmodifiableList(inmodifiableTournee);

    }

    /**
     * Quand on crée une nouvelle livraison on a besoin d'un identifiant unique. Ce
     * compteur aide à recuperer cette identifiant.
     *
     * @return un identifiant unique
     */
    public int getProchainIdCustomLivraison() {
        customLivraisonCompteur--;
        return customLivraisonCompteur;
    }

    /**
     * Echange deux livraisons
     *
     * @param idLivraison1 Première livraison
     * @param idLivraison2 Deuxième livraison
     */
    public void echangerLivraisons(int idLivraison1, int idLivraison2)
    {
        if (demande.getEntrepot().getId() == idLivraison1 || demande.getEntrepot().getId() == idLivraison2) {
            throw new RuntimeException("Il est interdit de déplacer l'entrepôt.");
        }

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

        supprimerLivraison(idLivraison1);
        supprimerLivraison(idLivraison2);
        
        int nouvelleLivraisonId1 = getProchainIdCustomLivraison();
        int nouvelleLivraisonId2 = getProchainIdCustomLivraison();
        
        Livraison nouvelleLivraison1 = new Livraison(nouvelleLivraisonId1, clientId2, intersection2);
        Livraison nouvelleLivraison2 = new Livraison(nouvelleLivraisonId2, clientId1, intersection1);

        ajouterLivraison(livrAvantL1.getId(), f1, nouvelleLivraison1);
        ajouterLivraison(livrAvantL2.getId(), f2, nouvelleLivraison2);
    }

}
