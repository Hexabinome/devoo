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
       controleurInterface.chargerPlan(null);

    }

    /**
     * Ecouteur pour la demande de livraison
     */
    @FXML
    private void ouvrirDemande(ActionEvent actionEvent) {
        controleurInterface.chargerLivraisons(null);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //resources.

    }


}
