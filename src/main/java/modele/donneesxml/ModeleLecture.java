package modele.donneesxml;

import java.util.List;

/**
 * Interface pour lire le modèle
 * @author maxou
 */
public interface ModeleLecture {

    /**
     * @return Le plan de la ville
     */
    PlanDeVille getPlan();

    /**
     * @return La demande de livraison
     */
    Demande getDemande();

    /**
     * Cette méthode permet d'accéder à la tournée (représentée par une liste
     * de listes). Chaque liste interne représente les intersections à parcourir
     * pour réaliser les livraisons d'une fenêtre. Dans le cas où la tournee n'a pas
     * encore été calculé, cette methode va retourner null.
     *
     * @return Une représentation des intersections, par fenêtres, à parcourir ou null.
     */
    List<List<Integer>> getTournee();

    // TODO génération feuille de route en lecture ??
	/** Génère la feuille de route
	 * @return Chaîne de caractère bien formée, prête à être écrite dans un fichier
	 */
	String genererFeuilleDeRoute();
}
