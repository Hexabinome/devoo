package vue;

import controleur.ControleurInterface;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;
import modele.xmldata.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controleur de la TreeTableView qui affiche les livraisons et les horaires.
 * Elle passe par le controleur principale de la vue quand elle reçoie des actions.
 */
public class VueLivraisonHoraireControleur implements Initializable, Visiteur {

    @FXML
    TreeTableView<Visitable> tableViewFenetre;

    private ControleurInterface controleurInterface;

    /**
     * Colonne livraison de la vue à gauche
     */
    private final TreeTableColumn<Visitable, String> colonneLivraison = new TreeTableColumn<>("Livraisons");

    /**
     * Colonne horaire de passage de la vue à gauche
     */
    private final TreeTableColumn<Visitable, String> colonneHoraire = new TreeTableColumn<>("Horaires de passage");


    private FenetrePrincipale mediateur;

    public void initialiserMediateur(FenetrePrincipale fenetrePrincipale) {
        this.mediateur = fenetrePrincipale;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colonneLivraison.setPrefWidth(161);
        colonneHoraire.setPrefWidth(161);
        tableViewFenetre.getColumns().addAll(colonneLivraison, colonneHoraire);
    }

    @Override
    public String visit(Fenetre fenetre) {
        int debut = fenetre.getTimestampDebut();
        int fin = fenetre.getTimestampFin();

        return convertirEnHeureLisible(debut) + " - " + convertirEnHeureLisible(fin);
    }

    protected void construireVueLivraion(Demande demande) {
        TreeItem<Visitable> dummyRoot = new TreeItem<>();
        for (Fenetre f : demande.getFenetres()) {
            dummyRoot.getChildren().add(construireFenetreItem(f));
        }

        //livraisonColum.setResizable(false);
        colonneLivraison.setCellValueFactory((TreeTableColumn.CellDataFeatures<Visitable, String> param)
                -> new ReadOnlyStringWrapper(param.getValue().getValue().accepter(this)));
        /*colonneLivraison.setCellFactory(
                new Callback<TreeTableColumn<Visitable, String>, TreeTableCell<Visitable, String>>() {
                    @Override
                    public TreeTableCell<Visitable, String> call(TreeTableColumn<Visitable, String> param) {
                        return new TreeTableCell<Visitable, String>(){
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
                });*/

        // On n'affiche pas le root car c'est pas la peine
        // http://stackoverflow.com/questions/22893461/javafx8-treetableview-multiple-root-items
        tableViewFenetre.setRoot(dummyRoot);
        dummyRoot.setExpanded(true);
        tableViewFenetre.setShowRoot(false);

    }


    /**
     * Contruis une item correspondant à une fenetre et ses enfants
     */
    private static TreeItem<Visitable> construireFenetreItem(Fenetre fenetre) {

        // Récuperation des livraisons de la fenetre
        List<Livraison> livraisonList = new ArrayList<>();
        fenetre.getLivraisons().forEach((integer, livraison1) -> {
            livraisonList.add(livraison1);
        });

        // Construction des items de chaque livraison
        TreeItem<Visitable> rootItem = new TreeItem<>(fenetre);
        for (Livraison l : livraisonList) {

            TreeItem<Visitable> livraisonTreeItem = new TreeItem<>(l);
            rootItem.getChildren().add(livraisonTreeItem);
        }

        return rootItem;
    }


    @Override
    public String visit(Livraison livraison) {
        return livraison.getId() + " - Client " + livraison.getClientId() + " à " + livraison.getAdresse();
    }

    /**
     * Convertis un temps en seconde en HH:mm:ss
     */
    private static String convertirEnHeureLisible(int tempsEnSeconde) {
        int heure = tempsEnSeconde / 3600;
        int mn = (tempsEnSeconde % 3600) / 60;
        int sec = tempsEnSeconde % 60;
        return String.format("%02d:%02d:%02d", heure, mn, sec);
    }

    public void setControleurInterface(ControleurInterface controleurInterface) {
        this.controleurInterface = controleurInterface;
    }
}
