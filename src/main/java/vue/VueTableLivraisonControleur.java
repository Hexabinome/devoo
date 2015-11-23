package vue;

import controleur.ControleurInterface;
import controleur.MainActivationObserverInterface;
import controleur.ModelObserver;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;
import modele.xmldata.Demande;
import modele.xmldata.Fenetre;
import modele.xmldata.Livraison;
import modele.xmldata.Visiteur;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controleur de la TreeTableView qui affiche les livraisons et les horaires.
 * Elle passe par le controleur principale de la vue quand elle reçoie des
 * actions.
 */
public class VueTableLivraisonControleur implements Initializable, Visiteur, MainActivationObserverInterface,
        ModelObserver {

    @FXML
    TreeTableView<ObjetVisualisable> tableViewFenetre;

    /**
     * Element racine de la Table qui contient tous les autres éléments.Il ne sera pas affiché dans la table
     */
    private TreeItem<ObjetVisualisable> elementRacine = new TreeItem<>();

    /**
     * Controleur principale
     */
    private ControleurInterface controleurInterface;

    /**
     * Colonne livraison de la vue à gauche
     */
    @FXML
    private TreeTableColumn<ObjetVisualisable, String> colonneLivraison;

    /**
     * Colonne horaire de passage de la vue à gauche
     */
    @FXML
    private TreeTableColumn<ObjetVisualisable, String> colonneHoraire;

    /**
     * Mediateur pour la communication avec les autres controleurs de vue
     */
    private FenetrePrincipale mediateur;

    public void initialiserMediateur(FenetrePrincipale fenetrePrincipale) {
        this.mediateur = fenetrePrincipale;
    }

    /**
     * Initialise les caractéristiques de la table
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewFenetre.setRoot(elementRacine);
        elementRacine.setExpanded(true);
        tableViewFenetre.setShowRoot(false);
        initialiserEcouteurDeClic();
        initialiserColonneLivraison();
        initialiserColonneHoraire();
        //ajouterStyleCssColonneLivraison();
    }

    /**
     * Contruis la table des livraisons
     *
     * @param demande La demande de livraison chargée à partir d'un fichier XML
     */
    protected void construireVueTableLivraion(Demande demande) {
        // TODO : afficher l'entrepo
        for (Fenetre f : demande.getFenetres()) {
            elementRacine.getChildren().add(construireFenetreItem(f));
        }
    }

    private void ajouterStyleCssColonneLivraison() {
        // TODO : à completer pour le hover ou des trucs du genre
        colonneLivraison.setCellFactory(
                new Callback<TreeTableColumn<ObjetVisualisable, String>, TreeTableCell<ObjetVisualisable, String>>() {
                    @Override
                    public TreeTableCell<ObjetVisualisable, String> call(
                            TreeTableColumn<ObjetVisualisable, String> param) {
                        return new TreeTableCell<ObjetVisualisable, String>() {
                            @Override
                            protected void updateItem(String item, boolean empty) {

                                super.updateItem(item, empty);
                                setText(item);
                                String style = null;
                                style = "-fx-font-weight: bold; -fx-text-fill: skyblue; -fx-underline: true;";
                                setStyle(style);
                            }
                        };
                    }
                });
    }

    /**
     * Initialise l'écouteur de clique sur un élement de la table
     */
    private void initialiserEcouteurDeClic() {
        tableViewFenetre.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    //newValue.getValue().accepterVisiteurObjet(this);
                });
    }

    /**
     * Initialise la methode de remplissage de la colonne 'Livraisons'
     */
    private void initialiserColonneLivraison() {
        colonneLivraison.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetVisualisable, String> param)
                -> new ReadOnlyStringWrapper(param.getValue().getValue().afficherCaracteriquesGloable()));
    }

    /**
     * Initialise la methode de remplissage de la colonne 'Horaire de passage'
     */
    private void initialiserColonneHoraire() {
        colonneHoraire.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetVisualisable, String> param)
                -> new ReadOnlyStringWrapper(param.getValue().getValue().afficherCaracteriqueSpeciale()));
    }

    /**
     * Efface le contenu de la table de livraison table de livraison
     */
    private void effacerVueTableLivraison() {
        elementRacine.getChildren().clear();
    }

    /**
     * Contruis un élement correspondant à une fenetre et ses enfants
     */
    private static TreeItem<ObjetVisualisable> construireFenetreItem(Fenetre fenetre) {

        // Récuperation des livraisons de la fenetre
        List<Livraison> livraisonList = new ArrayList<>();
        fenetre.getLivraisons().forEach((integer, livraison1) -> {
            livraisonList.add(livraison1);
        });

        // Construction des items de chaque livraison
        TreeItem<ObjetVisualisable> elementRacine = new TreeItem<>(new DetailFenetre(fenetre));
        for (Livraison l : livraisonList) {

            TreeItem<ObjetVisualisable> livraisonTreeItem = new TreeItem<>(new DetailLivraison(l));
            elementRacine.getChildren().add(livraisonTreeItem);
        }

        elementRacine.setExpanded(true);
        return elementRacine;
    }

    @Override
    public void recupererObject(Fenetre fenetre) {
        //TODO : Faire un un truc sur la map
        System.out.println(fenetre);
    }

    @Override
    public void recupererObject(Livraison livraison) {
        //TODO : Faire un un truc sur la map
        System.out.println(livraison);
        controleurInterface.cliqueSurLivraison(livraison.getId());
    }

    public void setControleurInterface(ControleurInterface controleurInterface) {
        this.controleurInterface = controleurInterface;
    }

    @Override
    public void notifyObserver(boolean disabled) {
        if (disabled) {
            effacerVueTableLivraison();
        }

    }

    /**
     * Notification déclenchée lors d'un changement dans le model
     */
    @Override
    public void notifyObserver() {
        effacerVueTableLivraison();
        construireVueTableLivraion(controleurInterface.getModel().getDemande());
    }

    public void initialiserObserveurs() {
        controleurInterface.ajouterDesactObserver(this);
        controleurInterface.ajouterModelObserver(this);
    }

}