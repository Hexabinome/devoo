import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modele.persistence.DeserialiseurXML;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;

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
        launch(args);
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            PlanDeVille planDeVille = DeserialiseurXML.ouvrirPlanDeVille(
                    classLoader.getResourceAsStream("samples/plan10x10.xml"));
            DeserialiseurXML.ouvrirLivraison(classLoader.getResourceAsStream("samples/livraison10x10-1.xml"),
                    planDeVille);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            System.err.println("Pas valide car : ");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Probleme lors du parsing de l'heure");
            e.printStackTrace();
        }

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("fxml/RootLayout.fxml"));

        primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(root, 1000, 668));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(668);
        primaryStage.show();
    }
}
