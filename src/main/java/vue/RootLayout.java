package vue;

import controleur.ControleurInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.stage.FileChooser;
import modele.persistence.DeserialiseurXML;
import modele.xmldata.Fenetre;
import modele.xmldata.Intersection;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Cette classe joue le rôle de binding pour la fenetre principale de l'application.
 * C'est ici qu'on spécifiera les écouteurs et consorts.
 * Remarque : Les écouteurs peuvent être spécifiés directement dans le fichier xml aussi
 */
public class RootLayout implements Initializable {

    private final double DIAMETRE_INTERSECTION = 7;
    private final int MARGE_INTERSECTION = 30;
    /**
     * Controleur à appeler en cas de besoin
     */
    ControleurInterface controleurInterface;
    /**
     * Vue à gauche qui affiche les livraisons
     */
    @FXML
    private TreeTableView<Fenetre> tableViewFenetre;

    @FXML
    private Pane canvasGraphique;

    private Collection<Ellipse> intersectionsGraphiques = new ArrayList<Ellipse>();
    private double echelleXIntersection = 0;
    private double echelleYIntersection = 0;
    final ChangeListener<Number> ecouteurDeRedimensionnement = new ChangeListener<Number>() {

        @Override
        public void changed(ObservableValue<? extends Number> observable,
                            Number oldValue, Number newValue) {

            canvasGraphique.getChildren().clear();
            afficherToutesEllipses();
        }
    };

    public RootLayout() {
    }

    public void setControleurInterface(ControleurInterface controleurInterface) {
        this.controleurInterface = controleurInterface;
    }

    /**
     * Ecouteur pour ouvrir le plan
     */
    @FXML
    private void ouvrirPlan(ActionEvent actionEvent) {
        File file = ouvrirSelectionneurDeFichier("Choissiez le plan de la ville");
        if (file != null) {
            controleurInterface.chargerPlan(file);
        }
    }

    /**
     * Ecouteur pour la demande de livraison
     */
    @FXML
    private void ouvrirDemande(ActionEvent actionEvent) {
        File file = ouvrirSelectionneurDeFichier(
                "Choisissez la demande de livraison");
        if (file != null) {
            controleurInterface.chargerLivraisons(file);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //resources.

    }

    @FXML
    void clic_ajouterLivraison() throws JDOMException, IOException, SAXException {
        PlanDeVille planDeVille = DeserialiseurXML.ouvrirPlanDeVille(
                ClassLoader.getSystemClassLoader().getResourceAsStream("samples/plan10x10.xml"));
        construireGraphe(planDeVille);
    }

    @FXML
    void clic_echangerLivraison() {
        System.out.println(canvasGraphique.getHeight());
        System.out.println(canvasGraphique.getWidth());
    }

    private void construireGraphe(PlanDeVille plan) {

        Map<Integer, Intersection> toutesIntersections = plan.getIntersections();
        intersectionsGraphiques = new ArrayList<Ellipse>();

        echelleXIntersection = 0;
        echelleYIntersection = 0;
        for (Intersection i : toutesIntersections.values()) {
            echelleXIntersection = Math.max(echelleXIntersection, i.getX());
            echelleYIntersection = Math.max(echelleYIntersection, i.getY());
            intersectionsGraphiques.add(construireEllipse(i));
        }

        afficherToutesEllipses();
    }

    private Ellipse construireEllipse(Intersection i) {
        Ellipse intersection = new Ellipse(i.getX(), i.getY(), DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
        intersection.setFill(Color.YELLOW);
        return intersection;
    }

    private void afficherToutesEllipses() {
        for (Ellipse e : intersectionsGraphiques)
            afficherEllipse(e);

        echelleXIntersection = 0;
        echelleYIntersection = 0;
        for (Ellipse e : intersectionsGraphiques) {
            echelleXIntersection = Math.max(echelleXIntersection, e.getCenterX());
            echelleYIntersection = Math.max(echelleYIntersection, e.getCenterY());
        }
    }

    private void afficherEllipse(Ellipse e) {
        double newX = e.getCenterX() * canvasGraphique.getWidth() / (echelleXIntersection + MARGE_INTERSECTION);
        double newY = e.getCenterY() * canvasGraphique.getHeight() / (echelleYIntersection + MARGE_INTERSECTION);

        e.setCenterX(newX);
        e.setCenterY(newY);

        canvasGraphique.getChildren().add(e);
    }

    /**
     * Ouvre une boite de dialogue pour choisir un fichier
     * http://stackoverflow.com/questions/25491732/how-do-i-open-the-javafx-filechooser-from-a-controller-class
     *
     * @param titreDialogue
     */
    protected File ouvrirSelectionneurDeFichier(String titreDialogue) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(titreDialogue);
        //  Filtrage de l'extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fichier xml (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Affichage de la boite de dialogque + récuperation du fichier choisi

        return fileChooser.showOpenDialog(tableViewFenetre.getScene().getWindow());
    }

}
