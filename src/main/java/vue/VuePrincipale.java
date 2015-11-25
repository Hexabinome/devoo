package vue;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import modele.xmldata.ModelLecture;

import org.controlsfx.control.StatusBar;
import org.controlsfx.dialog.ExceptionDialog;

import controleur.ControleurInterface;

/**
 * Cette classe joue le rôle de binding pour la fenetre principale de
 * l'application. C'est ici qu'on spécifiera les écouteurs et consorts. Remarque
 * : Les écouteurs peuvent être spécifiés directement dans le fichier xml aussi
 *
 * @author David
 */
public class VuePrincipale implements Initializable {

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
    private Pane canvasGraphique;

    private VueGraphiqueAideur vueGraphique;

    /**
     * Le menu en haut
     */
    @FXML
    private Menu menuFichier;

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
    private StatusBar barreDeStatus;

    /**
     * Méthode appelée lors du redimensionnement de la fenêtre. Elle replace les
     * arrêtes du graphe à leur bonne position
     */
    final ChangeListener<Number> ecouteurDeRedimensionnement = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {

            canvasGraphique.getChildren().clear();
            vueGraphique.afficherPlan();
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
     * Ecouteur pour ouvrir le plan
     */
    @FXML
    private void ouvrirPlan(ActionEvent actionEvent) {
        File file = ouvrirSelectionneurDeFichier("Choissiez le plan de la ville");
        if (file != null) {
            Exception messageErreur = controleurInterface.chargerPlan(file);
            if (messageErreur != null)
                ouvrirAlerteXML(messageErreur, file.getName());
            else {
                vueGraphique.construireGraphe(controleurInterface.getPlanDeVille());

                //activation de menu element graphique qui permet de charger un fichier de livraison
                //(Ici on peut le faire sans appel observeur, parce qu' a parti d'ici on possede toujours un plan valide.)
                menuFichier.getItems().get(1).setDisable(false);
            }
        }
    }

    /**
     * Ecouteur pour la demande de livraison
     */
    @FXML
    private void ouvrirDemande(ActionEvent actionEvent) {
        File file = ouvrirSelectionneurDeFichier("Choisissez la demande de livraison");
        if (file != null) {
            Exception exception = controleurInterface.chargerLivraisons(file);
            if (exception != null)
                ouvrirAlerteXML(exception, file.getName());
            else {
                ModelLecture modele = controleurInterface.getModel();
                vueGraphique.construireTournee(modele.getDemande().getEntrepot(), modele.getTournee(),
                        modele.getDemande());
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vueGraphique = new VueGraphiqueAideur(canvasGraphique);
    }
    
    public VueGraphiqueAideur getAideurVueGraphique() {
    	return vueGraphique;
    }

    @FXML
    void quitterApplication() {
        Alert confirmationDialog = new Alert(AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Quitter");
        confirmationDialog.setHeaderText("Êtes-vous sûr(e) de vouloir quitter l'application ?");
        confirmationDialog.setResizable(false);

        Optional<ButtonType> resultat = confirmationDialog.showAndWait();
        if (resultat.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    @FXML
    void clic_ajouterLivraison() {
        controleurInterface.cliqueOutilAjouter();
    }

    @FXML
    void clic_echangerLivraison() {
        controleurInterface.cliqueOutilEchanger();
    }

    @FXML
    void clic_supprimerLivraison() {
        controleurInterface.cliqueOutilSupprimer();
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
        return fileChooser.showOpenDialog(canvasGraphique.getScene().getWindow());
    }

    /**
     * Ouvre une boîte de dialogue d'exception modale afin de signalier à
     * l'utilisateur une erreur avec un fichier XML
     *
     * @param message Le message à afficher
     * @param fichier Le nom du fichier qui a généré l'erreur
     */
    private void ouvrirAlerteXML(Exception message, String fichier) {

        ExceptionDialog exceptionDialog = new ExceptionDialog(message);
        exceptionDialog.setTitle("Erreur");
        exceptionDialog.setHeaderText("Problème avec le fichier xml : " + "'" + fichier + "'");
        exceptionDialog.setWidth(ERROR_DIALOG_WIDTH);
        exceptionDialog.setHeight(ERROR_DIALOG_HEIGHT);
        exceptionDialog.setResizable(false);
        exceptionDialog.initOwner(canvasGraphique.getScene().getWindow());

        exceptionDialog.showAndWait();
    }


    public void initialiserObserveurs() {
        controleurInterface.ajouterDesactObserver(ajouterLivraisonBouton);
        controleurInterface.ajouterDesactObserver(echangerLivraisonsBouton);
        controleurInterface.ajouterDesactObserver(supprimerLivraisonBouton);
        controleurInterface.ajouterDesactObserver(genererFeuilleBouton);
    }


}
