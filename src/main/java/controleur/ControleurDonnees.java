package controleur;

import java.util.ArrayList;
import java.util.Collection;

import controleur.observateur.*;
import modele.donneesxml.Modele;
import modele.donneesxml.PlanDeVille;
import controleur.commande.Commande;
import controleur.commande.Historique;

/**
 * Cette classe contient les données nécessaires pour la gestion des états. On
 * pourrait dire qu'elle représente seulement des données et devrait du coup
 * mieux être située dans le package modèle. Par contre elle est liée à une
 * seule IHM, du coup il y a une bonne raison pour la laisser ici dans le
 * controleur.
 *
 * @author Maxou
 */
public class ControleurDonnees {

    /**
     *  Représente les fichiers XMLs chargés. Peut aussi calculer et stoquer une solution
     */
    private Modele modele;

    /**
     * Collection des observateurs (pour GUI avec functionalités réduites si plan et livraisons ne sont pas encore chargés)
     */
    private final Collection<ActivationObservableInterface> activationObservateurs = new ArrayList<ActivationObservableInterface>();

    /**
     * Collection des observateurs pour le modèle
     */
    private final Collection<ModeleObservateur> modeleObservateurs = new ArrayList<ModeleObservateur>();

    /**
     * Collection des observateurs pour la possibilité d'annuler des intéractions effectuées
     */
    private final Collection<AnnulerCommandeObservateur> annulerCommandeObservateurs = new ArrayList<AnnulerCommandeObservateur>();

    /**
     * Collection des observateurs pour la possibilité de rétablir des intéractions effectuées.
     */
    private final Collection<RetablirCommandeObservateur> retablirCommandeObservateurs = new ArrayList<RetablirCommandeObservateur>();
    
    /**
     * Collection des observateurs des modifications du plan
     */
    private final Collection<ActivationOuvrirDemandeObservateur> planObservateurs = new ArrayList<ActivationOuvrirDemandeObservateur>();
    
    /**
     * Collection des  observateurs du chargement du plan
     */
    private final Collection<ActivationOuvrirPlanObservateur> chargementPlanObservateurs = new ArrayList<ActivationOuvrirPlanObservateur>();
    
    /**
     * Collection des observateurs d'activation/désactivation de composants
     */
    private final Collection<ActivationObservableInterface> tourneeObservateurs = new ArrayList<ActivationObservableInterface>();
    
    /**
     * Collection des observateurs des messages envoyés à la vue
     */
    private final Collection<MessageObservateur> messageObservateurs = new ArrayList<MessageObservateur>();
    
    /**
     * Collection des observateurs d'activation des fonctionnalités
     */
    private final Collection<ActivationFonctionnalitesObservateur> activationFonctionnalitesObservateurs = new ArrayList<ActivationFonctionnalitesObservateur>();

    private final Collection<PlanChargeObservateur> planChargeObservateurs = new ArrayList<>();
    
    /**
     * Le plan de la ville
     */
    private PlanDeVille plan = null;
    
    /**
     * L'historique des commandes
     */
    private Historique hist = new Historique();
    
    /**
     * Retourne le modèle associé
     * @return Le modèle associé
     */
    public Modele getModele() {
        return modele;
    }
    
    /**
     * Affecte le modèle
     * @param modele Le nouveau modèle
     */
    public void setModele(Modele modele) {
        this.modele = modele;
    }
    
    /**
     * Retourne le plan de la ville
     * @return Le plan de la ville
     */
    public PlanDeVille getPlan() {
        return plan;
    }
    
    /**
     * Affecte le plan de la ville
     * @param plan Le nouveau plan de la ville
     */
    public void setPlan(PlanDeVille plan) {
        this.plan = plan;
    }
    
    public Historique getHist()
    {
        return hist;
    }
    
    /**
     * Affecte l'historique
     * @param hist Le nouvel historique
     */
    public void setHistorique(Historique hist) {
        this.hist = hist;
    }
    
    /**
     * Ajoute un observateur d'activation
     * @param obs L'objet observateur
     */
    public void ajouterActivationObservateur(ActivationObservableInterface obs) {
        activationObservateurs.add(obs);
    }
    
    /**
     * Ajoute un observateur du modèle
     * @param obs L'objet observateur
     */
    public void ajouterModeleObservateur(ModeleObservateur obs) {
        modeleObservateurs.add(obs);
    }
    
    /**
     * Ajoute un observateur de la commande annuler
     * @param obs L'objet observateur
     */
    public void ajouterAnnulerCommandeObservateur(AnnulerCommandeObservateur obs) {
        annulerCommandeObservateurs.add(obs);
    }
    
    /**
     * Ajoute un observateur de la commande rétablir
     * @param obs L'objet observateur
     */
    public void ajouterRetablirCommandeObservateur(RetablirCommandeObservateur obs) {
        retablirCommandeObservateurs.add(obs);
    }
    
    public void ajouterPlanObservateur(ActivationOuvrirDemandeObservateur planObserveur)
    {
        planObservateurs.add(planObserveur);
    }
    
    /**
     * Ajoute un observateur du chargement du plan
     * @param chargementPlanObserveur L'objet observateur
     */
    public void ajouterChargementPlanObservateur(ActivationOuvrirPlanObservateur chargementPlanObserveur) {
    	chargementPlanObservateurs.add(chargementPlanObserveur);
    }
    
    /**
     * Ajoute un observateur pour l'activation de la génération de la tournée
     * @param tourneeObserveur L'objet observateur
     */
    void ajouterTourneeObservateur(ActivationObservableInterface tourneeObserveur) {
        tourneeObservateurs.add(tourneeObserveur);
    }
    
    /**
     * Ajoute un observateur pour l'envoie d'un message
     * @param obs L'objet observateur
     */
    void ajouterMessageObservateur(MessageObservateur obs) {
        messageObservateurs.add(obs);
    }
    
    /**
     * Ajoute un observateur pour activer les boutons des fonctionnalités
     * @param observeur L'objet observateur
     */
    void ajouterActivationFonctionnalitesObservateurs(ActivationFonctionnalitesObservateur observeur) {
    	activationFonctionnalitesObservateurs.add(observeur);
    }
    
    /**
     * Ajoute une commande à l'historique
     * @param commande Une commande exécutée
     */
    public void ajouterCommande(Commande commande) {
        hist.ajouterCommande(commande);
    }
    
    /**
     * Notifie les observateurs du plan
     * @param activer Vrai s'il faut envoyer un message d'activation aux observateurs
     */
    public void notifierObservateurOuvrirDemande(boolean activer) {
        planObservateurs.forEach(planObserveur -> planObserveur.notifierObservateurOuvrirDemande(activer));
    }
    
    /**
     * Notifie les observateurs du chargement du plan
     * @param activer Vrai s'il faut envoyer un message d'activation aux observateurs
     */
    public void notifierObservateurOuvrirPlan(boolean activer) {
    	chargementPlanObservateurs.forEach(chargementPlanObserveur -> chargementPlanObserveur.notifierObservateursOuvrirPlan(activer));
    }
    
    /**
     * Notifie les observateurs de l'activation
     * @param etat Vrai s'il faut activer les observateurs
     */
    public void notifierObservateursActivation(boolean etat) {
        activationObservateurs.forEach(obs -> obs.notifierObservateursActivation(etat));
    }
    
    /**
     * Notifie les observateurs du changemetn du modèle
     */
    public void notifierObservateursModele() {
        modeleObservateurs.forEach(obs -> obs.notifierObservateursModele());
    }
    
    /**
     * Notifie les observateurs qu'il y a eu une annulation
     * @param activation Vrai si les observateurs doivent s'activer dans ce cas d'annulation
     */
    public void notifierObservateursAnnuler(boolean activation) {
        annulerCommandeObservateurs.forEach(obs -> obs.notifierObservateurAnnulerCommande(activation));
    }
    
    /**
     * Notifie les observateurs qu'il y eu un rétablissement
     * @param activation Vrai si les observateurs doivent s'activer dans ce cas de rétablissement
     */
    public void notifierObservateursRetablir(boolean activation) {
        retablirCommandeObservateurs.forEach(obs -> obs.notifierObservateurRetablirCommande(activation));
    }
    
    /**
     * Notifie les observateurs du calcul de la tournée
     * @param activation Vrai si les observateurs doivent s'activer
     */
    public void notifierObservateursCalculTournee(boolean activation) {
        tourneeObservateurs.forEach(obs -> obs.notifierObservateursActivation(activation));
    }
    
    /**
     * Notifie les observateurs qu'il y a un message
     * @param message Le message envoyé
     */
    public void notifierObservateursMessage(String message) {
        messageObservateurs.forEach(obs -> obs.notifierObservateursMessage(message));
    }

    public void notifierPlanChargeObservateur(){
        planChargeObservateurs.forEach(obs -> obs.notifierObservateursPlanCharge());
    }

    public void ajouterPlanChargeObservateur(PlanChargeObservateur planChargeObservateur){
        planChargeObservateurs.add(planChargeObservateur);
    }
    
    /**
     * Notifie les observateurs d'un changement pour les boutons de fonctionnalités
     * @param activation Vrai si les boutons des fonctionnalités doivent s'activer
     */
    public void notifierObservateursFonctionnalites(boolean activation) {
    	activationFonctionnalitesObservateurs.forEach(obs -> obs.notifierObservateursFonctionnalites(activation));
    }

    /**
     * Efface la liste des commandes à retablir et notifie la vue qu'elle doit désactiver l'élément du menu correspondant
     */
    public void effacerCommandesARetablir() {
        hist.effacerCommandeARetablir();
        notifierObservateursRetablir(true);
    }

    /**
     * Efface la liste des commandes à annuler et notifie la vue qu'il doir desactiver l'élément du menu correspondant
     */
    public void effacerCommandeAAnnuler() {
        hist.effacerCommandesAAnnuler();
        notifierObservateursAnnuler(true);
    }

    /**
     * Efface l'historique (vide les commandes annulable et rétablissable(?))
     */
    public void effacerHistorique() {
        effacerCommandeAAnnuler();
        effacerCommandesARetablir();
    }
}
