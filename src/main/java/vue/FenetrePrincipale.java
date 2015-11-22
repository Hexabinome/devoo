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

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Chargement de la fenetre principale
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/RootLayout.fxml"));
        BorderPane root = fxmlLoader.load();
        RootLayout vueControleur = (RootLayout) fxmlLoader.getController();

        // Création du controleur de l'application
        Controleur controleur = new Controleur();

        // Passage du controleur de l'application au controleur de la vue
        vueControleur.setControleurInterface(controleur);
        vueControleur.initialiserObserveurs();


        // Chargement de la vue des livraison
        FXMLLoader fxmlLoader2 = new FXMLLoader(
                getClass().getClassLoader().getResource("fxml/VueLivraisonHoraire.fxml"));
        AnchorPane anchorPane = fxmlLoader2.load();
        VueLivraisonHoraireControleur vueLivraisonHoraireControleur = (VueLivraisonHoraireControleur) fxmlLoader2.getController();
        vueLivraisonHoraireControleur.setControleurInterface(controleur);

        BorderPane centerBorderPane = (BorderPane) root.getCenter();
        centerBorderPane.setLeft(anchorPane.getChildren().get(0));

        primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE));
        primaryStage.setMinWidth(LARGEUR_FENETRE);
        primaryStage.setMinHeight(HAUTEUR_FENETRE);

        primaryStage.widthProperty().addListener(vueControleur.ecouteurDeRedimensionnement);
        primaryStage.heightProperty().addListener(vueControleur.ecouteurDeRedimensionnement);

        primaryStage.show();
    }


}
