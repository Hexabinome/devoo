package controleur.etat;

import java.io.File;

import controleur.commande.CommandeException;

/**
 * L'interface de tous les états contenant chaque action exécutables par une action (qu'elle fasse quelque chose ou non)
 * @author Maxou
 */
public interface EtatInterface {

    /**
     * Effectue l'action de l'état actuel après avoir cliquer sur une livraison
     * @param livraisonId L'identifiant de la livraison cliqué
     * @return Le nouvel état
     */
    EtatInterface clicSurLivraison(int livraisonId);

    /**
     * Effectue l'action de l'état actuel après avoir charger un plan
     * @param plan Le fichier contenant le plan
     * @return Le nouvel état
     * @throws CommandeException S'il y a une erreur lors du chargement
     */
    EtatInterface chargerPlan(File plan) throws CommandeException;

    /**
     * Effectue l'action de l'état actuel après avoir charger une demande de livraisons
     * @param livraisons Le fichier contenant la demande de livraison
     * @return Le nouvel état
     * @throws CommandeException S'il y a une erreur lors du chargement
     */
    EtatInterface chargerLivraisons(File livraisons) throws CommandeException;

    /**
     * Effectue l'action de l'état actuel après avoir cliqué sur le plan
     * @param intersectionId L'identifiant de l'intersection cliqué
     * @return Le nouvel état
     */
    EtatInterface clicSurPlan(int intersectionId);

    /**
     * Effectue l'action de l'état actuel après avoir demander le calcul de la tournée
     * @return Le nouvel état
     */
    EtatInterface clicCalculerTournee();

    /**
     * Effectue l'action de l'état actuel après avoir fait un clic droits
     * @return Le nouvel état
     */
    EtatInterface clicDroit();

}
