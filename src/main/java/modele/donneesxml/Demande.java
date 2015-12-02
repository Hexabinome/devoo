package modele.donneesxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Demande de livraison, chargé à partir d'un fichier XML
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public class Demande implements Serializable {

    /** Entrepot de la demande de livraison, point de départ de la tournée ensuite généré, ainsi que point d'arrivée final */
    private final Intersection entrepot;

    /** Liste des fenêtres qui composent la demande */
    private final List<Fenetre> fenetres;

    /**
     * Lors de la construction d'une demande, on ajoute automatiquement
     * l'entrepot comme premiere fenêtre
     *
     * @param entrepot L'entrepot, début de la demande
     * @param fenetres La liste des fenêtres de la demande
     */
    public Demande(Intersection entrepot, List<Fenetre> fenetres) {
        this.entrepot = entrepot;
        Fenetre fenetreEntrepot = new Fenetre(0, 0);

        Livraison livraisonEntrepot = new Livraison(-1, -1, entrepot.getId());
        fenetreEntrepot.ajouterLivraison(livraisonEntrepot.getId(), livraisonEntrepot);

        this.fenetres = new ArrayList<>();
        this.fenetres.add(fenetreEntrepot);
        this.fenetres.addAll(fenetres);
    }

    /** Renvoie l'entrepot de la demande
     * @return L'entrepot de la demande de livraison, point de départ et d'arrivée des livraisons
     */
    public Intersection getEntrepot() {
        return entrepot;
    }

    /** Renvoie la liste des fenêtres de la demande
     * @return Liste en lecture seule des fenêtres
     */
    public List<Fenetre> getFenetres() {
        return Collections.unmodifiableList(fenetres);
    }

    @Override
    public String toString() {
        return "Demande{"
                + "entrepot=" + entrepot
                + ", fenetres=" + fenetres
                + '}';
    }

    /**
     * On parcourt la liste de fenêtres pour calculer le graphe. Chaque fenêtre
     * insère dans le graphe des chemins en fonction de ces points de livraisons
     * et les points de livraisons de la fenêtre suivante. La première fenêtre
     * est l'entrepot
     *
     * @param plan Le plan de la ville
     * @return Le graphe de réalisation
     */
    public GrapheRealisation creerGraphe(PlanDeVille plan) {
        GrapheRealisation graphe = new GrapheRealisation(getNombreLivraison());

        for (int iFenetre = 0; iFenetre < fenetres.size() - 1; iFenetre++) {
            fenetres.get(iFenetre).calculerChemins(plan, graphe, fenetres.get(iFenetre + 1));
        }

        //Calcule le chemin entre la dernière fenêtre et l'entrepot
        fenetres.get(fenetres.size() - 1).calculerChemins(plan, graphe, fenetres.get(0));

        return graphe;
    }

    /**
     * Retourne le nombre de livraisons de la demande
     * @return Le nombre de livraison de la demande
     */
    private int getNombreLivraison() {
        Set<Integer> livraison = new HashSet<>();

        for (int iFenetre = 0; iFenetre < fenetres.size(); iFenetre++) {
            livraison.addAll((fenetres.get(iFenetre).getListeLivraisons().keySet()));
        }

        return livraison.size();
    }

    /**
     * Supprime une livraison de la demande
     * @param livraisonId L'identifiant de la livraison à supprimer
     */
    public void supprimerLivraision(int livraisonId) {
        fenetres.forEach(fenetre -> fenetre.supprimerLivraison(livraisonId));
    }

    /**
     * Récupère une livraison à partir de son identifiant.
     *
     * @param idLivraison L'identifiant de la livraison
     * @return La livraison si elle existe, null si non
     */
    public Livraison identifierLivraison(int idLivraison) {
        Livraison result = null;

        for (Fenetre f : fenetres) {
            result = (f.getListeLivraisons().keySet().contains(idLivraison) ? f.getLivraison(idLivraison) : result);
        }

        return result;
    }

    /**
     * Renvoie la fenêtre à laquelle appartient la livraison
     *
     * @param idLivraison L'identifiant de la livraison qui est dans la fenêtre
     * @return La fenêtre ou null si la livraison n'est pas trouvée
     */
    public Fenetre getFenetreDeLivraison(int idLivraison) {
        for (Fenetre f : fenetres) {
            if (f.getListeLivraisons().keySet().contains(idLivraison)) {
                return f;
            }
        }
        throw new RuntimeException("Fenetre introuvable pour livraison: " + idLivraison);
    }

}
