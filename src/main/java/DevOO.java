import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;
import xml.DeserialiseurXML;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Point d'entrée de l'application
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
        } catch (SAXException | IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            System.err.println("Pas valide car : ");
            e.printStackTrace();
        }

    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/RootLayout.fxml"));
        primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(root, 1000, 668));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(668);
        primaryStage.show();
    }
}
