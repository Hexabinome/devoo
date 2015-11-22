package vue;

import controleur.ControleurInterface;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import modele.persistance.DeserialiseurXML;
import modele.xmldata.*;
import org.controlsfx.dialog.ExceptionDialog;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.*;
import javafx.scene.control.Menu;

/**
 * Cette classe joue le rôle de binding pour la fenetre principale de
 * l'application. C'est ici qu'on spécifiera les écouteurs et consorts. Remarque
 * : Les écouteurs peuvent être spécifiés directement dans le fichier xml aussi
 */
//TODO : split thi class
/**
 * @author David
 */
public class RootLayout implements Initializable
{

    /**
     * La taille sur l'interface graphique d'une intersection du plan de la
     * ville
     */
    private final double DIAMETRE_INTERSECTION = 7;
    /**
     * La marge à laisser sur les côté du canvas graphique afin d'avoir plus du
     * lisibilité
     */
    private final int MARGE_INTERSECTION = 30;

    /**
     * Largeur de la boîte de dialogue d'erreur
     */
    private final double ERROR_DIALOG_WIDTH = 250;
    /**
     * Hauteur de la boîte de dialogue d'erreur
     */
    private final double ERROR_DIALOG_HEIGHT = 450;

    /**
     * Controleur déléguant la logique applicative à la couche controleur
     */
    ControleurInterface controleurInterface;

    /**
     * Vue à gauche qui affiche les livraisons
     */
    @FXML
    private TreeTableView<Object> tableViewFenetre;

    /**
     * Colonne livraison de la vue à gauche
     */
    private final TreeTableColumn<Object, String> colonneLivraison = new TreeTableColumn<>("Livraisons");

    /**
     * Colonne horaire de passage de la vue à gauche
     */
    private final TreeTableColumn<Object, String> colonneHoraire = new TreeTableColumn<>("Horaires de passage");

    /**
     * Partie droite de la fenêtre, affichant de la graphe du plan de la ville
     * et des livraisons
     */
    @FXML
    private Pane canvasGraphique;
    
    /**
     * Le menu en haut
     */
    @FXML
    private Menu menuFichier;

    /**
     * Contient tous les points graphiques actuellement afficher grâce à leur
     * id, et pour chaque intersection, ses arcs graphiques ainsi que
     * l'intersection ciblée
     */
    private Map<Integer, Pair<Ellipse, Collection<Integer>>> intersectionsGraphiques = new HashMap<Integer, Pair<Ellipse, Collection<Integer>>>();

    /**
     * Contient l'échelle X actuelle par rapport à laquelle les intersections
     * sont affichés
     */
    private double echelleXIntersection = 0;
    /**
     * Contient l'échelle Y actuelle par rapport à laquelle les intersections
     * sont affichés
     */
    private double echelleYIntersection = 0;

    /**
     * Met à jour le controleur d'interface
     *
     * @param controleurInterface Le nouveau controleur d'interface
     */
    public void setControleurInterface(ControleurInterface controleurInterface)
    {
        this.controleurInterface = controleurInterface;
    }

    /**
     * Ecouteur pour ouvrir le plan
     */
    @FXML
    private void ouvrirPlan(ActionEvent actionEvent)
    {
        File file = ouvrirSelectionneurDeFichier("Choissiez le plan de la ville");
        if (file != null) {
            Exception messageErreur = controleurInterface.chargerPlan(file);
            if (messageErreur != null)
                ouvrirAlerteXML(messageErreur, file.getName());
            else {
                construireGraphe(controleurInterface.getPlanDeVille());

                //activation de menu element graphique qui permet de charger un fichier de livraison
                //(Ici on peut le faire sans appel observeur, parce qu' a parti d'ici on possede toujours un plan valide.)
                menuFichier.getItems().get(1).setDisable(false);
            }
        }
    }

    /**
     * Ecouteur pour la demande de livraison
     */
    @FXML
    private void ouvrirDemande(ActionEvent actionEvent)
    {
        File file = ouvrirSelectionneurDeFichier(
                "Choisissez la demande de livraison");
        if (file != null) {
            Exception exception = controleurInterface.chargerLivraisons(file);
            if (exception != null)
                ouvrirAlerteXML(exception, file.getName());
            else {
                // TODO : remplir la partie à gauche
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        colonneLivraison.setPrefWidth(161);
        colonneHoraire.setPrefWidth(161);
        tableViewFenetre.getColumns().addAll(colonneLivraison, colonneHoraire);
        try {
            PlanDeVille planDeVille = DeserialiseurXML.ouvrirPlanDeVille(
                    ClassLoader.getSystemResourceAsStream("samples/plan20x20.xml"));
            Demande demande = DeserialiseurXML.ouvrirLivraison(
                    ClassLoader.getSystemResourceAsStream("samples/livraison20x20-2.xml"), planDeVille);
            construireVueLivraion(demande);
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (SAXException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void clic_ajouterLivraison()
    {
        // TODO : aller à l'état ajouter + supprimer les lignes en dessous (servant de test uniquement)
        // @N'importe qui va implementer ca: En fait c'est pas ici qu'on change l'etat, on seulement demande le controleur.
        // Tu appeles "controleur.cliqueOutilAjouter". Et c'est deja tout. 
        // Le controleur sera deja notifie des que l'utilisatuer clique sur le plan pour choissir
        // la destionation de la livraison. [Maxou]
        try {
            construireGraphe(DeserialiseurXML.ouvrirPlanDeVille(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("samples/plan10x10.xml")));
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void clic_echangerLivraison()
    {
        // TODO : aller à l'état d'échange
    }

    @FXML
    void clic_supprimerLivraison()
    {
        // TODO : aller à l'état de suppression
    }

    /**
     * Méthode appelée lors du redimensionnement de la fenêtre. Elle replace les
     * arrêtes du graphe à leur bonne position
     */
    final ChangeListener<Number> ecouteurDeRedimensionnement = new ChangeListener<Number>()
    {

        @Override
        public void changed(ObservableValue<? extends Number> observable,
                Number oldValue, Number newValue)
        {

            canvasGraphique.getChildren().clear();
            afficherPlan();
        }

    };

    /**
     * Construit et affiche le graphe du plan de la ville sur le canvas
     * graphique de la fenêtre
     *
     * @param plan Le plan de la ville, chargée par le couche controleur et
     * persistance
     */
    private void construireGraphe(PlanDeVille plan)
    {
        canvasGraphique.getChildren().clear();

        Map<Integer, Intersection> toutesIntersections = plan.getIntersections();
        intersectionsGraphiques = new HashMap<Integer, Pair<Ellipse, Collection<Integer>>>();

        echelleXIntersection = 0;
        echelleYIntersection = 0;
        // Construction de toutes les intersections
        for (Intersection i : toutesIntersections.values()) {
            echelleXIntersection = Math.max(echelleXIntersection, i.getX());
            echelleYIntersection = Math.max(echelleYIntersection, i.getY());

            Collection<Integer> destinations = new ArrayList<Integer>(); // Tous les tronçons de destinations
            destinations.addAll(i.getTroncons().keySet());

            intersectionsGraphiques.put(i.getId(),
                    new Pair<Ellipse, Collection<Integer>>(construireEllipse(i), destinations));
        }

        afficherPlan();
    }

    /**
     * Construit un point graphique pour une intersection du plan de la ville
     *
     * @param i L'intersection
     * @return Le point du graphe, à sa position du fichier XML du plan de la
     * ville
     */
    private Ellipse construireEllipse(Intersection i)
    {
        Ellipse intersection = new Ellipse(i.getX(), i.getY(), DIAMETRE_INTERSECTION, DIAMETRE_INTERSECTION);
        intersection.setFill(Color.YELLOW);
        return intersection;
    }

    /**
     * Affiche tous les points du plan et met à jour la taille du canvas
     * graphique. Les points sont toujours affichés par rapport : (leur taille
     * initiale dans le fichier XML / la plus grande taille dans le fichier XML)
     * => (la nouvelle taille / la taille du canvas)
     */
    private void afficherPlan()
    {
        // Affichage (+ mise à l'échelle) des intersections
        for (Pair<Ellipse, Collection<Integer>> pair : intersectionsGraphiques.values()) {
            afficherEllipse(pair.getKey());
        }

        // Affichage (+ mise à l'échalle) des tronçons
        for (Pair<Ellipse, Collection<Integer>> pair : intersectionsGraphiques.values()) {
            for (int destination : pair.getValue()) {
                afficherTroncon(pair.getKey(), intersectionsGraphiques.get(destination).getKey());
            }
        }

        // Mise à jour de l'échelle sur laquelle sont affichées les intersections du plan
        echelleXIntersection = 0;
        echelleYIntersection = 0;
        for (Pair<Ellipse, Collection<Integer>> pair : intersectionsGraphiques.values()) {
            echelleXIntersection = Math.max(echelleXIntersection, pair.getKey().getCenterX());
            echelleYIntersection = Math.max(echelleYIntersection, pair.getKey().getCenterY());
        }

        // Put nodes to the front
        for (Node n : canvasGraphique.getChildrenUnmodifiable()) {
            if (n instanceof Ellipse)
                n.toFront();
        }
    }

    /**
     * Affiche une ellipse sur l'interface, en fonction de la taille actuelle
     *
     * @param e Le point à afficher
     */
    private void afficherEllipse(Ellipse e)
    {
        double newX = e.getCenterX() * canvasGraphique.getWidth() / (echelleXIntersection + MARGE_INTERSECTION);
        double newY = e.getCenterY() * canvasGraphique.getHeight() / (echelleYIntersection + MARGE_INTERSECTION);

        e.setCenterX(newX);
        e.setCenterY(newY);

        canvasGraphique.getChildren().add(e);
    }

    /**
     * Affiche le lien entre les deux intersections dans une direction
     *
     * @param debut Le point de début
     * @param cible Le point de destination
     */
    private void afficherTroncon(Ellipse debut, Ellipse cible)
    {
        double p1X = debut.getCenterX();
        double p1Y = debut.getCenterY();
        double p2X = cible.getCenterX();
        double p2Y = cible.getCenterY();

        Line ligne = new Line(p1X, p1Y, p2X, p2Y);
        ligne.setStroke(Color.ORANGE);

        canvasGraphique.getChildren().add(ligne);

        // Création flèche
        double longueur = 8 * Math.sqrt(3);
        double demiLongueur = 4;

        double Ux = p2X - p1X;
        double Uy = p2Y - p1Y;

        double distance = Math.sqrt((Ux * Ux) + (Uy * Uy));

        Ux /= distance;
        Uy /= distance;

        double Vx = -Uy;
        double Vy = Ux;

        double extremiteFleche1X = p2X - longueur * Ux + demiLongueur * Vx;
        double extremiteFleche1Y = p2Y - longueur * Uy + demiLongueur * Vy;
        double extremiteFleche2X = p2X - longueur * Ux - demiLongueur * Vx;
        double extremiteFleche2Y = p2Y - longueur * Uy - demiLongueur * Vy;

        Polygon fleche = new Polygon(p2X, p2Y, extremiteFleche1X, extremiteFleche1Y, extremiteFleche2X,
                extremiteFleche2Y);
        fleche.setFill(Color.RED);

        canvasGraphique.getChildren().add(fleche);
    }

    /**
     * Ouvre une boite de dialogue pour choisir un fichier
     * http://stackoverflow.com/questions/25491732/how-do-i-open-the-javafx-filechooser-from-a-controller-class
     *
     * @param titreDialogue Le titre du sélectionneur de fichier
     */
    private File ouvrirSelectionneurDeFichier(String titreDialogue)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(titreDialogue);
        //  Filtrage de l'extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fichier xml (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Affichage de la boite de dialogque + récuperation du fichier choisi
        return fileChooser.showOpenDialog(tableViewFenetre.getScene().getWindow());
    }

    /**
     * Ouvre une boîte de dialogue d'exception modale afin de signalier à
     * l'utilisateur une erreur avec un fichier XML
     *
     * @param message Le message à afficher
     * @param fichier Le nom du fichier qui a généré l'erreur
     */
    private void ouvrirAlerteXML(Exception message, String fichier)
    {

        ExceptionDialog exceptionDialog = new ExceptionDialog(message);
        exceptionDialog.setTitle("Erreur");
        exceptionDialog.setHeaderText("Problème avec le fichier xml : " + "'" + fichier + "'");
        exceptionDialog.setWidth(ERROR_DIALOG_WIDTH);
        exceptionDialog.setHeight(ERROR_DIALOG_HEIGHT);
        exceptionDialog.setResizable(false);
        exceptionDialog.initOwner(tableViewFenetre.getScene().getWindow());

        exceptionDialog.showAndWait();
    }

    private void construireVueLivraion(Demande demande)
    {
        TreeItem<Object> dummyRoot = new TreeItem<>();
        for (Fenetre f : demande.getFenetres()) {
            dummyRoot.getChildren().add(construireFenetreItem(f));
        }

        //livraisonColum.setResizable(false);
        colonneLivraison.setCellValueFactory((TreeTableColumn.CellDataFeatures<Object, String> param)
                -> {
                    Object objetDeLigneCourante = param.getValue().getValue();
                    String stringLigneCourante = "";
                    if (objetDeLigneCourante instanceof Fenetre)
                        stringLigneCourante = ((Fenetre) objetDeLigneCourante).toString();
                    else if (objetDeLigneCourante instanceof Livraison)
                        stringLigneCourante = ((Livraison) objetDeLigneCourante).toString();
                    return new ReadOnlyStringWrapper(stringLigneCourante);
                });

        // On n'affiche pas le root car c'est pas la peine
        // http://stackoverflow.com/questions/22893461/javafx8-treetableview-multiple-root-items
        tableViewFenetre.setRoot(dummyRoot);
        dummyRoot.setExpanded(true);
        tableViewFenetre.setShowRoot(false);

    }

    /**
     * Contruis une item correspondant à une fenetre et ces enfants
     */
    private TreeItem<Object> construireFenetreItem(Fenetre fenetre)
    {

        // Récuperation des livraisons de la fenetre
        List<Livraison> livraisonList = new ArrayList<>();
        fenetre.getLivraisons().forEach((integer, livraison1) -> {
            livraisonList.add(livraison1);
        });

        // Construction des items de chaque livraison
        TreeItem<Object> rootItem = new TreeItem<>(fenetre);
        for (Livraison l : livraisonList) {

            TreeItem<Object> livraisonTreeItem = new TreeItem<>(l);
            rootItem.getChildren().add(livraisonTreeItem);
        }

        return rootItem;
    }

    private static String convertirTimeStampEnHoraire(int timestamp)
    {
        // TODO
        return null;
    }

}
