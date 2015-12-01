package controleur;

import java.util.ArrayList;
import java.util.Collection;

import modele.xmldata.Modele;
import modele.xmldata.PlanDeVille;
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
     * Collection des observeurs (pour GUI avec functionalités réduites si plan et livraisons ne sont pas encore chargés)
     */
    private final Collection<ActivationObserveurInterface> activationObserveurs = new ArrayList<ActivationObserveurInterface>();

    /**
     * Collection des observeurs pour le modèle
     */
    private final Collection<ModelObserveur> modelObserveurs = new ArrayList<ModelObserveur>();

    /**
     * Collection des observeurs pour la possibilité d'annuler des intéractions effectuées
     */
    private final Collection<AnnulerCommandeObserveur> annulerCommandeObserveurs = new ArrayList<AnnulerCommandeObserveur>();

    /**
     * Collection des observeurs pour la possibilité de rétablir des intéractions effectuées.
     */
    private final Collection<RetablirCommandeObserveur> retablirCommandeObserveurs = new ArrayList<RetablirCommandeObserveur>();
    
    /**
     * Collection des observeurs des modifications du plan
     */
    private final Collection<PlanObserveur> planObserveurs = new ArrayList<PlanObserveur>();
    
    /**
     * Collection des  observeurs du chargement du plan
     */
    private final Collection<ChargementPlanObserveurInterface> chargementPlanObserveurs = new ArrayList<ChargementPlanObserveurInterface>();
    
    /**
     * Collection des observeurs d'activation/désactivation de composants
     */
    private final Collection<ActivationObserveurInterface> tourneeObserveurs = new ArrayList<ActivationObserveurInterface>();
    
    /**
     * Collection des observeurs des messages envoyés
     */
    private final Collection<MessageObserveur> messageObserveurs = new ArrayList<MessageObserveur>();
    
    /**
     * Collection des observeurs d'activation des fonctionnalités
     */
    private final Collection<ActivationFonctionnalitesObserveurInterface> activationFonctionnalitesObserveurs = new ArrayList<ActivationFonctionnalitesObserveurInterface>();
    
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
    public void ajouterActivationObserveur(ActivationObserveurInterface obs) {
        activationObserveurs.add(obs);
    }
    
    /**
     * Ajoute un observateur du modèle
     * @param obs L'objet observateur
     */
    public void ajouterModeleObserveur(ModelObserveur obs) {
        modelObserveurs.add(obs);
    }
    
    /**
     * Ajoute un observateur de la commande annuler
     * @param obs L'objet observateur
     */
    public void ajouterAnnulerCommandeObserveur(AnnulerCommandeObserveur obs) {
        annulerCommandeObserveurs.add(obs);
    }
    
    /**
     * Ajoute un observateur de la commande rétablir
     * @param obs L'objet observateur
     */
    public void ajouterRetablirCommandeObserveur(RetablirCommandeObserveur obs) {
        retablirCommandeObserveurs.add(obs);
    }
    
    public void ajouterPlanObserveur(PlanObserveur planObserveur)
    {
        planObserveurs.add(planObserveur);
    }
    
    /**
     * Ajoute un observateur du chargement du plan
     * @param chargementPlanObserveur L'objet observateur
     */
    public void ajouterChargementPlanObserveur(ChargementPlanObserveurInterface chargementPlanObserveur) {
    	chargementPlanObserveurs.add(chargementPlanObserveur);
    }
    
    /**
     * Ajoute un observateur pour l'activation de la génération de la tournée
     * @param tourneeObserveur L'objet observateur
     */
    void ajouterTourneeObserveur(ActivationObserveurInterface tourneeObserveur) {
        tourneeObserveurs.add(tourneeObserveur);
    }
    
    /**
     * Ajoute un observateur pour l'envoie d'un message
     * @param obs L'objet observateur
     */
    void ajouterMessageObserveur(MessageObserveur obs) {
        messageObserveurs.add(obs);
    }
    
    /**
     * Ajoute un observateur pour activer les boutons des fonctionnalités
     * @param observeur L'objet observateur
     */
    void ajouterActivationFonctionnalitesObserveurs(ActivationFonctionnalitesObserveurInterface observeur) {
    	activationFonctionnalitesObserveurs.add(observeur);
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
    public void notifierObserveursDuPlan(boolean activer) {
        planObserveurs.forEach(planObserveur -> planObserveur.notificationPlanAChange(activer));
    }
    
    /**
     * Notifie les observateurs du chargement du plan
     * @param activer Vrai s'il faut envoyer un message d'activation aux observateurs
     */
    public void notifierObserveursChargementDuPlan(boolean activer) {
    	chargementPlanObserveurs.forEach(chargementPlanObserveur -> chargementPlanObserveur.notificationActiverChargementPlan(activer));
    }
    
    /**
     * Notifie les observateurs de l'activation
     * @param etat Vrai s'il faut activer les observateurs
     */
    public void notifierObserveursActivation(boolean etat) {
        activationObserveurs.forEach(obs -> obs.notifierLesObserveursActivation(etat));
    }
    
    /**
     * Notifie les observateurs du changemetn du modèle
     */
    public void notifierObserveursModele() {
        modelObserveurs.forEach(obs -> obs.notificationModelAChange());
    }
    
    /**
     * Notifie les observateurs qu'il y a eu une annulation
     * @param activation Vrai si les observateurs doivent s'activer dans ce cas d'annulation
     */
    public void notifierObserveursAnnuler(boolean activation) {
        annulerCommandeObserveurs.forEach(obs -> obs.notificationAnnulerCommande(activation));
    }
    
    /**
     * Notifie les observateurs qu'il y eu un rétablissement
     * @param activation Vrai si les observateurs doivent s'activer dans ce cas de rétablissement
     */
    public void notifierObserveursRetablir(boolean activation) {
        retablirCommandeObserveurs.forEach(obs -> obs.notificationRetablirCommande(activation));
    }
    
    /**
     * Notifie les observateurs du calcul de la tournée
     * @param activation Vrai si les observateurs doivent s'activer
     */
    public void notifierObserveursCalculTournee(boolean activation) {
        tourneeObserveurs.forEach(obs -> obs.notifierLesObserveursActivation(activation));
    }
    
    /**
     * Notifie les observateurs qu'il y a un message
     * @param message Le message envoyé
     */
    public void notifierObserveursMessage(String message) {
        messageObserveurs.forEach(obs -> obs.notifierMessagObserveur(message));
    }
    
    /**
     * Notifie les observateurs d'un changement pour les boutons de fonctionnalités
     * @param activation Vrai si les boutons des fonctionnalités doivent s'activer
     */
    public void notifierObserveursFonctionnalites(boolean activation) {
    	activationFonctionnalitesObserveurs.forEach(obs -> obs.notifierLesObserveursFonctionnalites(activation));
    }

    /**
     * Efface la liste des commandes à retablir et notifie la vue qu'elle doit désactiver l'élément du menu correspondant
     */
    public void effacerCommandesARetablir() {
        hist.effacerCommandeARetablir();
        notifierObserveursRetablir(true);
    }

    /**
     * Efface la liste des commandes à annuler et notifie la vue qu'il doir desactiver l'élément du menu correspondant
     */
    public void effacerCommandeAAnnuler() {
        hist.effacerCommandesAAnnuler();
        notifierObserveursAnnuler(true);
    }

    /**
     * Efface l'historique (vide les commandes annulable et rétablissable(?))
     */
    public void effacerHistorique() {
        effacerCommandeAAnnuler();
        effacerCommandesARetablir();
    }
}
