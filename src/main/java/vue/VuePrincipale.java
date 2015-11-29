package vue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import controleur.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modele.xmldata.ModeleLecture;

import org.controlsfx.dialog.ExceptionDialog;

/**
 * Cette classe joue le rôle de binding pour la fenetre principale de
 * l'application. C'est ici qu'on spécifiera les écouteurs et consorts. Remarque
 * : Les écouteurs peuvent être spécifiés directement dans le fichier xml aussi
 *
 * @author David
 */
public class VuePrincipale implements Initializable, PlanObserveur, ModelObserveur, AnnulerCommandeObserveur,
        RetablirCommandeObserveur {

    /**
     * Mediateur : permet de communiquer avec les autres controleurs
     */
    private FenetrePrincipale mediateur;

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
    private ControleurInterface controleurInterface;

    /**
     * Partie droite de la fenêtre, affichant de la graphe du plan de la ville
     * et des livraisons
     */
    @FXML
    private StackPane canvasGraphique;

    @FXML
    private Group group;

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
    private ObserverButton ajouterLivraisonBouton;

    /**
     * Bouton qui permet d'echanger des livraisons
     */
    @FXML
    private ObserverButton echangerLivraisonsBouton;

    /**
     * Bouton qui permet de supprimer des livraisons
     */
    @FXML
    private ObserverButton supprimerLivraisonBouton;

    /**
     * Bouton qui permet de generer le feuille de route
     */
    @FXML
    private ObserverButton genererFeuilleBouton;

    @FXML
    private ObserverButton calculerTourneeBouton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Slider sliderZoom;
    
    @FXML
    private Text message;

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

    public void initialiserMediateur(FenetrePrincipale fenetrePrincipale) {
        this.mediateur = fenetrePrincipale;
    }

    /**
     * Met à jour le controleur d'interface
     *
     * @param controleurInterface Le nouveau controleur d'interface
     */
    public void setControleurInterface(ControleurInterface controleurInterface) {
        this.controleurInterface = controleurInterface;
    }

    /**
     * Appelée quand l'utilisateur clique sur "Ouvrir plan de ville"
     */
    @FXML
    private void ouvrirPlan(ActionEvent actionEvent) {
        File file = ouvrirSelectionneurDeFichier("Choissiez le plan de la ville");
        if (file != null) {
            Exception messageErreur = controleurInterface.chargerPlan(file);
            if (messageErreur != null)
                ouvrirErreurFichier(messageErreur, file.getName());
        }
    }

    /**
     * Appelée quand l'utilisateur clique sur "Ouvrir demande de livraisons"
     */
    @FXML
    private void ouvrirDemande(ActionEvent actionEvent) {
        File file = ouvrirSelectionneurDeFichier("Choisissez la demande de livraison");
        if (file != null) {
            Exception exception = controleurInterface.chargerLivraisons(file);
            if (exception != null)
                ouvrirErreurFichier(exception, file.getName());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vueGraphique = new VueGraphiqueAideur(canvasGraphique, group, scrollPane, sliderZoom);
    }

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
        controleurInterface.cliqueOutilAjouter();
    }

    /**
     * Appelée quand l'utilisateur clique sur le bouton "Echanger livraisons"
     */
    @FXML
    void clic_echangerLivraison() {
        controleurInterface.cliqueOutilEchanger();
    }

    /**
     * Appelée quand l'utilisateur clique sur le bouton "Supprimer livraisons"
     */
    @FXML
    void clic_supprimerLivraison() {
        controleurInterface.cliqueOutilSupprimer();
    }

    @FXML
    void clic_calculer_tournee() {
        controleurInterface.cliqueCalculerTourne();
    }
    
    @FXML
    void clic_genererFeuilleRoute() {
    	File fichier = ouvrirEnregistreurDeFichier("Enregistrer la feuille de route");
    	if (fichier != null) {
    		try (FileWriter ecriveurDeFichier = new FileWriter(fichier)) {
    			String feuille = controleurInterface.genererFeuilleDeRoute();
        		ecriveurDeFichier.write(feuille);
    		} catch (IOException e) {
    			ouvrirErreurFichier(e, fichier.getAbsolutePath());
			}
    	}
    }

    @FXML
    void annuler(){
        controleurInterface.cliqueAnnuler();
    }

    @FXML
    void retablir(){
        controleurInterface.cliqueRetablir();
    }

    /**
     * Ouvre une boite de dialogue pour choisir un fichier
     *
     * @param titreDialogue Le titre du sélectionneur de fichier
     */
    private File ouvrirSelectionneurDeFichier(String titreDialogue) {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle(titreDialogue);
        //  Filtrage de l'extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fichier xml (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Affichage de la boite de dialogque + récuperation du fichier choisi
        return fileChooser.showOpenDialog(group.getScene().getWindow());
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
    	
    	return fileChooser.showSaveDialog(group.getScene().getWindow());
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
        exceptionDialog.initOwner(group.getScene().getWindow());

        exceptionDialog.showAndWait();
    }

    public void initialiserObserveurs() {
        controleurInterface.ajouterDesactObserver(ajouterLivraisonBouton);
        controleurInterface.ajouterDesactObserver(echangerLivraisonsBouton);
        controleurInterface.ajouterDesactObserver(supprimerLivraisonBouton);
        controleurInterface.ajouterDesactObserver(genererFeuilleBouton);
        controleurInterface.ajouterTourneeObserveur(calculerTourneeBouton);
        controleurInterface.ajouterPlanObserveur(this);
        controleurInterface.ajouterModelObserver(this);
        controleurInterface.ajouterAnnulerCommandeObserveur(this);
        controleurInterface.ajouterRetablirCommandeObserveur(this);
    }

    @Override
    public void notificationPlanAChange() {
        vueGraphique.construireGraphe(controleurInterface.getPlanDeVille());
        //activation de menu element graphique qui permet de charger un fichier de livraison
        //(Ici on peut le faire sans appel observeur, parce qu' a parti d'ici on possede toujours un plan valide.)
        menuFichier.getItems().get(1).setDisable(false);

    }

    @Override
    public void notificationModelAChange() {
        ModeleLecture modele = controleurInterface.getModel();

        if (modele.getTournee() != null)
            vueGraphique.construireTournee(modele.getTournee());

        vueGraphique.construireDemande(modele.getDemande());
    }

    @Override
    public void notificationAnnulerCommande(boolean activation) {
        menuEdition.getItems().get(0).setDisable(activation);
    }

    @Override
    public void notificationRetablirCommande(boolean activation) {
        menuEdition.getItems().get(1).setDisable(activation);
    }
}
