package vue;

import controleur.ControleurInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import modele.xmldata.Fenetre;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Cette classe joue le rôle de bindind pour la fenetre principale de l'application.
 * C'est ici qu'on spécifiera les écouteurs et consorts.
 * Remarque : Les écouteurs peuvent être spécifiés directement dans le fichier xml aussi
 */
public class RootLayout implements Initializable {

    /**
     * Vue à gauche qui affiche les livraisons
     */
    @FXML
    private TreeTableView<Fenetre> tableViewFenetre;


    /**
     * Controleur à appeler en cas de besoin
     */
    ControleurInterface controleurInterface;

    public void setControleurInterface(ControleurInterface controleurInterface) {
        this.controleurInterface = controleurInterface;
    }

    public RootLayout() {
    }

    /**
     * Ecouteur pour ouvrir le plan
     */
    @FXML
    private void ouvrirPlan(ActionEvent actionEvent) {
        ouvrirSelectionneurDeFichier();

    }

    /**
     * Ecouteur pour la demande de livraison
     */
    @FXML
    private void ouvrirDemande(ActionEvent actionEvent) {
        ouvrirSelectionneurDeFichier();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //resources.

    }

    /**
     * Ouvre une boite de dialogue pour choisir un fichier
     * http://stackoverflow.com/questions/25491732/how-do-i-open-the-javafx-filechooser-from-a-controller-class
     */
    private File ouvrirSelectionneurDeFichier() {
        FileChooser fileChooser = new FileChooser();
        //  Filtrage de l'extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fichier xml (*.xml)","*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Affichage de la boite de dialogque + récuperation du fichier choisi
        File file = fileChooser.showOpenDialog(tableViewFenetre.getScene().getWindow());

        return file;
    }
}
