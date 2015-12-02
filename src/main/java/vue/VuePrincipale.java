package vue;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controleur.observateur.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modele.donneesxml.ModeleLecture;

import org.controlsfx.dialog.ExceptionDialog;

import controleur.ControleurInterface;
import controleur.commande.CommandeException;

/**
 * Cette classe joue le rôle de binding pour la fenetre principale de
 * l'application. C'est ici qu'on spécifiera les écouteurs et consorts. Remarque
 * : Les écouteurs peuvent être spécifiés directement dans le fichier xml aussi
 *
 * @author David
 */
public class VuePrincipale implements Initializable, ActivationOuvrirDemandeObservateur, ActivationOuvrirPlanObservateur,
        ModeleObservateur, AnnulerCommandeObservateur,
        RetablirCommandeObservateur, ActivationFonctionnalitesObservateur, PlanChargeObservateur {
    /**
     * Largeur de la boîte de dialogue d'erreur
     */
    private final double ERROR_DIALOG_WIDTH = 250;
    /**
     * Hauteur de la boîte de dialogue d'erreur
     */
    private final double ERROR_DIALOG_HEIGHT = 450;

    /**
     * Controleur déléguant la logique applicative à la couche controleur
     */
    private ControleurInterface controleurApplication;

    /**
     * Partie droite de la fenêtre, affichant de la graphe du plan de la ville
     * et des livraisons
     */
    @FXML
    private StackPane canvasGraphique;

    /**
     * Ce groupe représente le groupe d'ellipse (noeu du graphe) qui se construit dans la vue graphique
     */
    @FXML
    private Group groupEllipseVueGraphique;

    /**
     * Réference vers la vue graphique
     */
    private VueGraphiqueAideur vueGraphique;

    /**
     * Le menu fichier en haut
     */
    @FXML
    private Menu menuFichier;

    /**
     * Le menu edition en haut
     */
    @FXML
    private Menu menuEdition;

    /**
     * Bouton qui permet d'ajouter des livraisons
     */
    @FXML
    private BoutonObservateur ajouterLivraisonBouton;

    /**
     * Bouton qui permet d'echanger des livraisons
     */
    @FXML
    private BoutonObservateur echangerLivraisonsBouton;

    /**
     * Bouton qui permet de supprimer des livraisons
     */
    @FXML
    private BoutonObservateur supprimerLivraisonBouton;

    /**
     * Bouton qui permet de generer le feuille de route
     */
    @FXML
    private BoutonObservateur genererFeuilleBouton;

    /** Bouton de calcul de tournée */
    @FXML
    private BoutonObservateur calculerTourneeBouton;

    /** Barre de défilement */
    @FXML
    private ScrollPane scrollPane;

    /** Le slider de zoom */
    @FXML
    private Slider sliderZoom;
    
    /** Barre de status de l'application en bas de l'écran */
    @FXML
    private ObserveurMessageChamps message;

    private File file;

    /**
     * Méthode appelée lors du redimensionnement de la fenêtre. Elle replace les
     * arrêtes du graphe à leur bonne position
     */
    final ChangeListener<Number> ecouteurDeRedimensionnement = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {
        	
            vueGraphique.nettoyerAffichage();
            vueGraphique.afficherPlan();
            vueGraphique.afficherDemande();
            vueGraphique.afficherTournee();
        }

    };

    /**
     * Met à jour le controleur de l'application
     *
     * @param controleurApplication Le nouveau controleur d'interface
     */
    public void setControleurApplication(ControleurInterface controleurApplication) {
        this.controleurApplication = controleurApplication;
    }

    /**+
     * Met à jour le controleur de l'application pour la vue graphique
     */
    public void setVueGraphiqueControleurApplication(ControleurInterface controleurApplication){
        vueGraphique.setControleurApplication(controleurApplication);
    }

    /**
     * Appelée quand l'utilisateur clique sur "Ouvrir plan de ville"
     */
    @FXML
    private void ouvrirPlan() {
        file = ouvrirSelectionneurDeFichier("Choissiez le plan de la ville");
        if(file != null){
            try {
                controleurApplication.chargerPlan(file);
            } catch (Exception e) {
                ouvrirErreurFichier(e, file.getName());
            }
        }
    }

    /**
     * Appelée quand l'utilisateur clique sur "Ouvrir demande de livraisons"
     */
    @FXML
    private void ouvrirDemande() {
        file = ouvrirSelectionneurDeFichier("Choisissez la demande de livraison");
        if(file != null){
            try {
                controleurApplication.chargerLivraisons(file);
            } catch (Exception e) {
                ouvrirErreurFichier(e, file.getName());
            }
        }
    }
    
    /** Affiche la boîte de dialogue "A propos"
     */
    @FXML
    public void aPropos() {
        Alert aProposDialog = new Alert(AlertType.INFORMATION);
        aProposDialog.setTitle("A propos");
        aProposDialog.setHeaderText(TEXTE_APROPOS_HEADER);
        aProposDialog.setContentText(TEXTE_APROPOS);
        aProposDialog.setResizable(false);

        aProposDialog.showAndWait();
    }
    
    /** L'entete de l'à propos */
    private final String TEXTE_APROPOS_HEADER = String.format("Optimod'Lyon - H4105%sGérer vos livraisons de façon optimale !", System.lineSeparator());
    /** Contenu de l'à propos */
    private final String TEXTE_APROPOS = new StringBuilder().append("Réalisé par l'hexanôme H4105 de l'INSA Lyon (2015) :")
    										.append(System.lineSeparator())
    										.append("Alexis Andra").append(System.lineSeparator())
    										.append("Jolan Cornevin").append(System.lineSeparator())
    										.append("Mohamed Haidara").append(System.lineSeparator())
    										.append("Alexis Papin").append(System.lineSeparator())
    										.append("Robin Royer").append(System.lineSeparator())
    										.append("Maximilian Schiedermeier").append(System.lineSeparator())
    										.append("David Wobrock").append(System.lineSeparator())
    										.toString();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vueGraphique = new VueGraphiqueAideur(canvasGraphique, groupEllipseVueGraphique, scrollPane, sliderZoom);
    }

    /**
     * @return La vue graphique
     */
    public VueGraphiqueAideur getAideurVueGraphique() {
        return vueGraphique;
    }

    /**
     * Appelée quand l'utilisateur clique sur "Quitter dans le menu Fichier"
     */
    @FXML
    void quitterApplication() {
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Quitter");
        confirmationDialog.setHeaderText("Êtes-vous sûr(e) de vouloir quitter l'application ?");
        confirmationDialog.setResizable(false);

        Optional<ButtonType> resultat = confirmationDialog.showAndWait();
        if (resultat.get() == ButtonType.OK)
            System.exit(0);
    }

    /**
     * Appelée quand l'utilisateur clique sur le bouton "Ajouter livraisons"
     */
    @FXML
    void clic_ajouterLivraison() {
        controleurApplication.clicOutilAjouter();
    }

    /**
     * Appelée quand l'utilisateur clique sur le bouton "Echanger livraisons"
     */
    @FXML
    void clic_echangerLivraison() {
        controleurApplication.clicOutilEchanger();
    }

    /**
     * Appelée quand l'utilisateur clique sur le bouton "Supprimer livraisons"
     */
    @FXML
    void clic_supprimerLivraison() {
        controleurApplication.clicOutilSupprimer();
    }

    /** Evènement clic sur le bouton de calcul de tournée
     */
    @FXML
    void clic_calculer_tournee() {
        controleurApplication.clicCalculTournee();
    }
    
    /** Evènement clic sur la génération de la feuille de route
     */
    @FXML
    void clic_genererFeuilleRoute() {
    	File fichier = ouvrirEnregistreurDeFichier("Enregistrer la feuille de route");
    	if (fichier != null) {
    		try {
    			controleurApplication.genererFeuilleDeRoute(fichier);
    		} catch (CommandeException e) {
    			ouvrirErreurFichier(e, fichier.getName());
    		}
    	}
    }

    /** 
     * Clic sur l'annulation ou Ctrl+Z
     */
    @FXML
    void annuler() {
        controleurApplication.clicAnnuler();
    }

    /**
     * Clic sur le rétablissement ou Ctrl+Y
     */
    @FXML
    void retablir() {
        controleurApplication.clicRetablir();
    }

    /**
     * Ouvre une boite de dialogue pour choisir un fichier
     *
     * @param titreDialogue Le titre du sélectionneur de fichier
     *                      @return file fichier selectionné ou null si l'utilisateur a cliqué sur annuler
     */
    private File ouvrirSelectionneurDeFichier(String titreDialogue) {

        FileChooser fileChooser = new FileChooser();
        
        if(file != null){
            File existDirectory = file.getParentFile();
            fileChooser.setInitialDirectory(existDirectory);
        }
        fileChooser.setTitle(titreDialogue);
        //  Filtrage de l'extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fichier xml (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Affichage de la boite de dialogque + récuperation du fichier choisi
        return fileChooser.showOpenDialog(groupEllipseVueGraphique.getScene().getWindow());
    }
    
    /**
     * Ouvre une boîte de dialogue pour enregistrer un fichier
     * @param titreDialogue Le titre de la boôte de dialogue
     * @return Le nom et chemin du fichier choisi
     */
    private File ouvrirEnregistreurDeFichier(String titreDialogue) {
    	FileChooser fileChooser = new FileChooser();
    	
    	fileChooser.setTitle(titreDialogue);
    	ExtensionFilter extensionFilter = new ExtensionFilter("Fichier texte (*.txt)", "*.txt");
    	fileChooser.getExtensionFilters().add(extensionFilter);
    	
    	return fileChooser.showSaveDialog(groupEllipseVueGraphique.getScene().getWindow());
    }

    /**
     * Ouvre une boîte de dialogue d'exception modale afin de signalier à
     * l'utilisateur une erreur avec un fichier XML
     *
     * @param message Le message à afficher
     * @param fichier Le nom du fichier qui a généré l'erreur
     */
    private void ouvrirErreurFichier(Exception message, String fichier) {

        ExceptionDialog exceptionDialog = new ExceptionDialog(message);
        exceptionDialog.setTitle("Erreur");
        exceptionDialog.setHeaderText("Problème avec le fichier : " + "'" + fichier + "'");
        exceptionDialog.setWidth(ERROR_DIALOG_WIDTH);
        exceptionDialog.setHeight(ERROR_DIALOG_HEIGHT);
        exceptionDialog.setResizable(false);
        exceptionDialog.initOwner(groupEllipseVueGraphique.getScene().getWindow());

        exceptionDialog.showAndWait();
    }

    /**
     * Initalise les differents obserserveurs de la vue principale
     */
    public void initialiserObserveurs() {

        // Observateur pour les boutons
        controleurApplication.ajouterActivationObserveur(ajouterLivraisonBouton);
        controleurApplication.ajouterActivationObserveur(echangerLivraisonsBouton);
        controleurApplication.ajouterActivationObserveur(supprimerLivraisonBouton);
        controleurApplication.ajouterActivationObserveur(genererFeuilleBouton);
        controleurApplication.ajouterTourneeObserveur(calculerTourneeBouton);


        controleurApplication.ajouterActivationFonctionnalitesObserveur(this);
        controleurApplication.ajouterPlanObserveur(this);
        controleurApplication.ajouterModeleObserveur(this);

        // Observateur undo/redo
        controleurApplication.ajouterAnnulerCommandeObserveur(this);
        controleurApplication.ajouterRetablirCommandeObserveur(this);

        // Observateur message affiché en bas
        controleurApplication.ajouterMessageObserveur(message);
        controleurApplication.ajouterChargementPlanObserveur(this);


        controleurApplication.ajouterPlanChargeObserveur(this);
    }

    @Override
    public void notifierObservateurOuvrirDemande(boolean activer) {
        //activation de l'élement du menu qui permet de charger un fichier de livraison
        menuFichier.getItems().get(1).setDisable(!activer);
    }

	@Override
	public void notifierObservateursOuvrirPlan(boolean activer) {
		menuFichier.getItems().get(0).setDisable(!activer);
	}

    @Override
    public void notifierObservateursModele() {
        ModeleLecture modele = controleurApplication.getModele();

        vueGraphique.nettoyerAffichage();
        vueGraphique.afficherPlan();
        vueGraphique.construireDemande(modele.getDemande());
        vueGraphique.construireTournee(modele.getTournee());
        vueGraphique.desactiverSurbrillance();
    }
    
	@Override
	public void notifierObservateursFonctionnalites(boolean activer) {
		this.ajouterLivraisonBouton.setDisable(!activer);
		this.echangerLivraisonsBouton.setDisable(!activer);
		this.supprimerLivraisonBouton.setDisable(!activer);
		this.genererFeuilleBouton.setDisable(!activer);
	}
	
    @Override
    public void notifierObservateurAnnulerCommande(boolean activation) {
        menuEdition.getItems().get(0).setDisable(activation);
    }

    @Override
    public void notifierObservateurRetablirCommande(boolean activation) {
        menuEdition.getItems().get(1).setDisable(activation);
    }

    @Override
    public void notifierObservateursPlanCharge() {
        vueGraphique.nettoyerAffichage();
        vueGraphique.construireGraphe(controleurApplication.getPlanDeVille());
    }
}
