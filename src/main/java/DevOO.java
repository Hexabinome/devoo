import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xml.OuvreurDeFichier;

/**
 * Base control class for the DevOO project
 *
 * @author Maximilian Schiedermeier
 */
public class DevOO extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        OuvreurDeFichier fichier = new OuvreurDeFichier() ;
        fichier.ouvrirLivraison(new File("samples/livraison10x10-2.xml"));  
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/RootLayout.fxml"));
        primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(root, 1200, 768));
        primaryStage.show();
    }
}
