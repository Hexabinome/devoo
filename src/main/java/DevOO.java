import controleur.Controleur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modele.persistence.DeserialiseurXML;
import modele.xmldata.Chemin;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;
import vue.RootLayout;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/RootLayout.fxml"));
        BorderPane rootPane = fxmlLoader.load();
        RootLayout rootLayout = fxmlLoader.<RootLayout>getController();
        rootLayout.setControleurInterface(new Controleur());

        primaryStage.setTitle("OptimodLyon");
        primaryStage.setScene(new Scene(rootPane, 1000, 668));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(668);
        primaryStage.show();
    }
}
