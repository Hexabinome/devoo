package vue;

import controleur.Controleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FenetrePrincipale extends Application {

    private RootLayout vueControleur;

    private final int LARGEUR_FENETRE = 1000;

    private final int HAUTEUR_FENETRE = 668;

    @Override
    public void start(Stage primaryStage) throws Exception {

        // Chargement de la fenetre principale
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/RootLayout.fxml"));
        Parent root = fxmlLoader.load();
        vueControleur = (RootLayout) fxmlLoader.getController();

        // Création du controleur de l'application
        Controleur controleur = new Controleur();

        // Passage du controleur de l'application au controleur de la vue
        vueControleur.setControleurInterface(controleur);

        primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(root, LARGEUR_FENETRE, HAUTEUR_FENETRE));
        primaryStage.setMinWidth(LARGEUR_FENETRE);
        primaryStage.setMinHeight(HAUTEUR_FENETRE);

        primaryStage.widthProperty().addListener(vueControleur.ecouteurDeRedimensionnement);
        primaryStage.heightProperty().addListener(vueControleur.ecouteurDeRedimensionnement);

        primaryStage.show();
    }
}
