import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jdom2.JDOMException;
import xml.DeserialiseurXML;
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
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            DeserialiseurXML.ouvrirPlanDeVille(new File(classLoader.getResource("samples/plan20x20.xml").toURI()));
        } catch (JDOMException | IOException  | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/RootLayout.fxml"));
        primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(root, 1200, 768));
        primaryStage.show();
    }
}
