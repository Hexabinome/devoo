package controleur;

import java.io.File;

import controleur.commande.CommandeException;
import controleur.observable.ActivationFonctionnalitesObservableInterface;
import controleur.observable.ActivationObservableInterface;
import controleur.observable.AnnulerCommandeObservableInterface;
import controleur.observable.ChargementPlanObservableInterface;
import controleur.observable.MessageObservableInterface;
import controleur.observable.ModeleObservableInterface;
import controleur.observable.PlanObservableInterface;
import controleur.observable.RetablirCommandeObservableInterface;
import modele.xmldata.ModeleLecture;
import modele.xmldata.PlanDeVille;

/**
 * Interface le contrôleur
 * @author Maxou
 */
public interface ControleurInterface {

    /**
     * Ajoute un observateur de l'activation
     * @param observeur
     */
    void ajouterActivationObserveur(ActivationObservableInterface observeur);

    /**
     * Ajoute un observateur au changement du modèle
     * @param observeur
     */
    void ajouterModeleObserveur(ModeleObservableInterface observeur);

    /**
     * Ajoute un observateur des changement du plan
     * @param planObserveur
     */
    void ajouterPlanObserveur(PlanObservableInterface planObserveur);
    
    /**
     * Ajoute un observateur du chargement du plan
     * @param chargementPlanObserveur
     */
    void ajouterChargementPlanObserveur(ChargementPlanObservableInterface chargementPlanObserveur);
    
    /**
     * Ajoute un observateur à la tournée
     * @param tourneeObserveur
     */
    void ajouterTourneeObserveur(ActivationObservableInterface tourneeObserveur);

    /**
     * Ajoute un observateur à l'annulation d'une commande
     * @param annulerCommandeObserveur
     */
    void ajouterAnnulerCommandeObserveur(AnnulerCommandeObservableInterface annulerCommandeObserveur);

    /**
     * Ajoute un observateur au rétablissement d'une commande
     * @param retablirCommandeObserveur
     */
    void ajouterRetablirCommandeObserveur(RetablirCommandeObservableInterface retablirCommandeObserveur);
    
    /**
     * Ajoute un observateur à l'activation des fonctionnalités
     * @param obs
     */
    void ajouterActivationFonctionnalitesObserveur(ActivationFonctionnalitesObservableInterface obs);
    
    /**
     * Ajoute un observateur des messages envoyés
     * @param obs
     */
    public void ajouterMessageObserveur(MessageObservableInterface obs);

    /**
     * Appel quand il y a un clic sur plan
     * @param intersectionId  L'identifiant de l'intersection cliqué
     */
    void clicSurPlan(int intersectionId);
    
    /**
     * Appel quand il y a un clic sur une livraison
     * @param livraisonId L'identifiant de la livraison
     */
    void clicSurLivraison(int livraisonId);

    /**
     * Appel lors d'un clic sur Annuler
     */
    void clicAnnuler();

    /**
     * Appel lors d'un clic sur Rétablir
     */
    void clicRetablir();

    /**
     * Cette methode essaye de convertir un fichier XML dans sa représentation
     * d'objets.
     *
     * @param fichierPlan Objet File qui représente le fichier XML
     * @throws Exception Lance une exception s'il y a une erreur lors du chargement des objets
     */
    void chargerPlan(File fichierPlan) throws Exception;

    /**
     * Cette methode essaye de convertir un fichier XML dans sa représentation
     * d'objets.
     *
     * @param fichierLivraisons Objet File qui représente le fichier XML
     * @throws Exception Lance une exception s'il y a une erreur lors du chargement des objets
     */
    void chargerLivraisons(File fichierLivraisons) throws Exception;

    /**
     * Appel lors du clic pour passer dans le mode d'ajout
     */
    void clicOutilAjouter();

    /**
     * Appel lors du clic pour passer dans le mode de suppression
     */
    void clicOutilSupprimer();

    /**
     * Appel lors du clic pour passer dans le mode d'échange
     */
    void clicOutilEchanger();

    /**
     * Appel lors du clic droit
     */
    void clicDroit();
    
    /**
     * Retourn le modèle
     * @return Le modèle actuel en lecture
     */
    ModeleLecture getModele();
    
    /**
     * Retourne le plande la ville
     * @return Le plan de la ville actuellement chargé
     */
    PlanDeVille getPlanDeVille();

    /**
     * Appel lors du clic sur le calcul de la tournée
     */
    void clicCalculTournee();
    
	/**
	 * Génère la feuille de route
	 * @param fichier Le fichier dans lequel on devra écrire la feuille de route
	 * @throws CommandeException Une erreur lors de l'exécution de la commande de génération
	 */
	void genererFeuilleDeRoute(File fichier) throws CommandeException;
}
