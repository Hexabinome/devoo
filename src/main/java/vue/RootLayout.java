package vue;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import modele.persistence.DeserialiseurXML;
import modele.xmldata.Fenetre;
import modele.xmldata.Intersection;
import modele.xmldata.PlanDeVille;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import controleur.ControleurInterface;

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

    @FXML
    private Pane canvasGraphique;

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

    @FXML
	void clic_ajouterLivraison() throws JDOMException, IOException, SAXException {
		PlanDeVille planDeVille = DeserialiseurXML.ouvrirPlanDeVille(ClassLoader.getSystemClassLoader().getResourceAsStream("samples/plan10x10.xml"));
		construireGraphe(planDeVille);
	}
	
	@FXML
	void clic_echangerLivraison() {
		System.out.println(canvasGraphique.getHeight());
		System.out.println(canvasGraphique.getWidth());
	}
	
	private void construireGraphe(PlanDeVille plan) {
		
		Map<Integer, Intersection> toutesIntersections = plan.getIntersections();
		Collection<Ellipse> intersectionsGraphiques = new ArrayList<Ellipse>();
		
		int xMaxIntersection = 0;
		int yMaxIntersection = 0;
		for (Intersection i : toutesIntersections.values()) {
			xMaxIntersection = Math.max(xMaxIntersection, i.getX());
			yMaxIntersection = Math.max(yMaxIntersection, i.getY());
			intersectionsGraphiques.add(construireEllipse(i));
		}
		
		for (Ellipse e : intersectionsGraphiques) {
			afficherEllipse(e, xMaxIntersection, yMaxIntersection);
		}
	}
	
	private final double DIAMETRE_INTERSECTION = 7;
	private final int MARGE_INTERSECTION = 50;
	
	private Ellipse construireEllipse(Intersection i) {
		Ellipse intersection = new Ellipse(i.getX(), i.getY(), DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
		intersection.setFill(Color.YELLOW);
		return intersection;
	}
	
	private void afficherEllipse(Ellipse e, int xMax, int yMax)	{
		double newX = e.getCenterX() * canvasGraphique.getWidth() / (xMax + MARGE_INTERSECTION);
		double newY = e.getCenterY() * canvasGraphique.getHeight() / (yMax + MARGE_INTERSECTION);
		
		e.setCenterX(newX);
		e.setCenterY(newY);
		
		canvasGraphique.getChildren().add(e);
	}

}
