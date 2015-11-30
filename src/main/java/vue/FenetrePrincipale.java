package vue;

import controleur.Controleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Cette classe crée la fenetre principale avec ses enfants. Elle se charge aussi de créer le controleur.
 */
public class FenetrePrincipale extends Application {


    private final int LARGEUR_FENETRE = 1000;

    private final int HAUTEUR_FENETRE = 668;

    /**
     * Lance l'application
     * @param primaryStage objet père qui contient la fenetre principale
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Chargement de la fenetre principale
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/VuePrincipale.fxml"));
        BorderPane root = fxmlLoader.load();
        VuePrincipale vuePrincipaleControleur = (VuePrincipale) fxmlLoader.getController();

        // Création du controleur de l'application
        Controleur controleurApplication = new Controleur();

        // Passage du controleur de l'application au controleur de la vue
        vuePrincipaleControleur.setControleurApplication(controleurApplication);
        vuePrincipaleControleur.setVueGraphiqueControleurApplication(controleurApplication);
        vuePrincipaleControleur.initialiserMediateur(this);
        vuePrincipaleControleur.initialiserObserveurs();

        // Chargement de la vue des livraison
        FXMLLoader fxmlLoader2 = new FXMLLoader(
                getClass().getClassLoader().getResource("fxml/VueTextuelle.fxml"));
        AnchorPane anchorPane = fxmlLoader2.load();
        VueTextuelle vueTextuelle = (VueTextuelle) fxmlLoader2.getController();
        vueTextuelle.setAideurVueGraphique(vuePrincipaleControleur.getAideurVueGraphique());
        vueTextuelle.setControleurApplication(controleurApplication);
        vueTextuelle.initialiserMediateur(this);
        vueTextuelle.initialiserObserveurs();

        BorderPane centerBorderPane = (BorderPane) root.getCenter();
        centerBorderPane.setLeft(anchorPane.getChildren().get(0));

        primaryStage.setTitle("Optimod'Lyon");
        primaryStage.setScene(new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE));
        primaryStage.setMinWidth(LARGEUR_FENETRE);
        primaryStage.setMinHeight(HAUTEUR_FENETRE);

        primaryStage.widthProperty().addListener(vuePrincipaleControleur.ecouteurDeRedimensionnement);
        primaryStage.heightProperty().addListener(vuePrincipaleControleur.ecouteurDeRedimensionnement);

        primaryStage.show();
    }

}
