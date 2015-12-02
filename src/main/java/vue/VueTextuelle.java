package vue;

import controleur.ControleurInterface;
import controleur.observateur.ModeleObservateur;
import controleur.observateur.PlanChargeObservateur;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import modele.donneesxml.Demande;
import modele.donneesxml.Fenetre;
import modele.donneesxml.Livraison;
import vue.ObjetVisualisable.CouleurTexte;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Cette classe gère les livraisons et leurs horaires. Elle s'occupe de la vue textuelle qui se trouve à gauche dans la
 * fenêtre principale.
 */
public class VueTextuelle implements Initializable,
        ModeleObservateur, PlanChargeObservateur {

    /**
     * Table principale contenant la liste des livraisons.
     */
    @FXML
    private TreeTableView<ObjetVisualisable> tableViewFenetre;

    /**
     * Element racine de la Table qui contient tous les autres éléments. Il ne sera pas affiché dans la table.
     * Une table a forcément besoin d'un élément principal.
     */
    private final TreeItem<ObjetVisualisable> elementRacine = new TreeItem<>();

    /**
     * Une réference vers le controleur de l'application pour envoyer des messages.
     */
    private ControleurInterface controleurApplication;

    /**
     * La colonne "Livraisons" qui se trouve dans la table des livraisons
     */
    @FXML
    private TreeTableColumn<ObjetVisualisable, String> colonneLivraison;

    /**
     * La colonne "Horaire de passage" qui se trouve dans la table des livraisons
     */
    @FXML
    private TreeTableColumn<ObjetVisualisable, String> colonneHoraire;

    /**
     * Réference vers la vue graphique pour la communication avec celle-ci.
     * Dans d'autre circonstance, on aurait pu utiliser un médiateur pour la communication entre les deux pour ne pas
     * avoir une référence à cette vue ici.
     */
    private VueGraphiqueAideur vueGraphique;

    /**
     * Met à jour la référence vers la vue graphique
     */
    protected void setAideurVueGraphique(VueGraphiqueAideur vueGraphique) {
        this.vueGraphique = vueGraphique;

    }

    /**
     * Méthode appelée automatiquement au chargement du fichier XML
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewFenetre.setRoot(elementRacine);
        elementRacine.setExpanded(true);
        tableViewFenetre.setShowRoot(false);
        initialiserColonneLivraison();
        initialiserColonneHoraire();
        initialiserEcouteursColonneLivraison();
    }

    /**
     * Contruit la table des livraisons
     *
     * @param demande La demande de livraison chargée à partir d'un fichier XML. La demande ne doit pas être null
     */
    protected void construireVueTableLivraion(final Demande demande) {
        for (Fenetre f : demande.getFenetres()) {
            elementRacine.getChildren().add(construireFenetreItem(f));
        }
    }

    /**
     * Initialise les écouteurs (clic, hover) sur la colonne "Livraison" de la table
     */
    private void initialiserEcouteursColonneLivraison() {
        colonneLivraison.setCellFactory(
                param -> new TableCellSpecial());
    }

    /**
     * Initialise la methode de remplissage de la colonne "Livraison".
     * On récupère les caractériques globales des objets contenus dans la table
     */
    private void initialiserColonneLivraison() {
        colonneLivraison.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetVisualisable, String> param)
                -> new ReadOnlyStringWrapper(param.getValue().getValue().afficherCaracteriquesGlobales()));
    }

    /**
     * Initialise la methode de remplissage de la colonne "Horaire de passage".
     * On récupère les caractéristiques spéciales des objets contenus dans la table
     */
    private void initialiserColonneHoraire() {
        colonneHoraire.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetVisualisable, String> param)
                -> new ReadOnlyStringWrapper(param.getValue().getValue().afficherCaracteriquesSpeciales()));
    }

    /**
     * Efface tout le contenu de la table de livraison table de livraison
     */
    private void effacerVueTableLivraison() {
        elementRacine.getChildren().clear();
    }

    /**
     * Contruit un élement de la table (TreeItem) correspondant à une fenetre et ses enfants
     *
     * @param fenetre fenetre à construire sous forme visuelle
     */
    private static TreeItem<ObjetVisualisable> construireFenetreItem(final Fenetre fenetre) {

        // Récuperation des livraisons de la fenetre
        List<Livraison> livraisonList = new ArrayList<>();
        fenetre.getListeLivraisons().forEach((integer, livraison1) -> {
            livraisonList.add(livraison1);
        });

        // Construction des items de chaque livraison
        TreeItem<ObjetVisualisable> elementFenetre = new TreeItem<>(new DetailFenetre(fenetre));

        for (Livraison l : livraisonList) {

            TreeItem<ObjetVisualisable> livraisonTreeItem = new TreeItem<>(new DetailLivraison(l));
            elementFenetre.getChildren().add(livraisonTreeItem);
        }

        // Par défaut l'élément est étendue
        elementFenetre.setExpanded(true);
        return elementFenetre;
    }

    /**
     * Met à jour le controleur de l'application pour la vue textuelle
     *
     * @param controleurApplication controleur initialisé (non null)
     */
    protected void setControleurApplication(ControleurInterface controleurApplication) {
        this.controleurApplication = controleurApplication;
    }


    /**
     * Notification déclenchée lors d'un changement dans le model. Cette peut etre notification est déclenchée que si
     * on a déja chargé un plan et une demande de livraison dans l'application
     */
    @Override
    public void notifierObservateursModele() {
        effacerVueTableLivraison();
        Demande demandeModifiee = controleurApplication.getModele().getDemande();
        construireVueTableLivraion(demandeModifiee);
    }

    /**
     * Ajoute la vue textuelle comme observeurs au près du controleur
     */
    protected void initialiserObserveurs() {
        controleurApplication.ajouterModeleObservateur(this);
        controleurApplication.ajouterPlanChargeObserveur(this);
    }

    /**
     * Efface le contenu de la table à chaque chargement d'un plan.
     */
    @Override
    public void notifierObservateursPlanCharge() {
        effacerVueTableLivraison();
    }


    /**
     * Gestion du clic et hover sur les elements de la table. En créant une autre classe séparée, on n'aurait
     * pas pu acceder aux membres de la classe VueTableLivraisonControleur d'autant plus que la gestion des évenements
     * est intimement liée à cette classe
     */
    private class TableCellSpecial extends TreeTableCell<ObjetVisualisable, String> {

        /**
         * Contructeur : initialisation du clic et du hover
         */
        public TableCellSpecial() {
            initialiserEcouteurDeClic();
            initialiserEcouteurDeHover();
        }

        /**
         * Cette méthode est appelée pour chaque élement qu'on veut afficher dans la colonne 'Livraison' de la table
         *
         * @param item  le texte affiché (peut être null)
         * @param empty booleen qui dit si le texte est vide ou pas
         */
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            // application d'un style par défaut à chaque élément
            setStyle("-fx-background-color: white; -fx-text-fill: black;");

            // Pour afficher l'entrepot
            if (item != null && item.startsWith("-1")) { // Affichage spécial pour l'entrepot
                setText("Entrepot");
            } else {
                setText(item);
            }


            ObjetVisualisable obj = getTreeTableRow().getItem();
            if (obj == null) {
                return;
            }
            // Application d'une couleur de surbrillance en fonction du retard ou non de la livraison.
            if (obj instanceof DetailLivraison) {
                DetailLivraison detail = (DetailLivraison) obj;
                if (detail.getLivraison().estEnRetard()) {
                    obj.setCouleurDefaut(CouleurTexte.RETARD);
                } else {
                    obj.setCouleurDefaut(CouleurTexte.NON_SURBRILLANCE);
                }
            }
            setSurbrillance(obj.getCouleurDefaut());
        }

        /**
         * Initialise l'écouteur de clic
         */
        private void initialiserEcouteurDeClic() {
            setOnMouseClicked(event -> {
                if (getTreeTableRow().getTreeItem() != null) {
                    ObjetVisualisable objetVisualisable = getTreeTableRow().getTreeItem().getValue();
                    if (objetVisualisable instanceof DetailLivraison)
                        controleurApplication.clicSurLivraison(
                                ((DetailLivraison) objetVisualisable).getLivraison().getId());
                }
            });

        }

        /**
         * Initialise l'écouteur de hover (passage de la souris sur un élement
         */
        private void initialiserEcouteurDeHover() {
            setOnMouseEntered(event -> {
                ObjetVisualisable objetSurpasse = getTreeTableRow().getItem();

                if (objetSurpasse == null)
                    return;

                // Change style de la vue textuelle
                setSurbrillance(CouleurTexte.SURBRILLANCE);

                // On met en surbrillance une intersection ou toutes les intersections d'une fenêtre
                if (objetSurpasse instanceof DetailLivraison) {
                    Livraison livraison = ((DetailLivraison) objetSurpasse).getLivraison();
                    vueGraphique.surbrillanceLivraison(livraison);
                } else if (objetSurpasse instanceof DetailFenetre) {
                    Collection<Livraison> livraisons = ((DetailFenetre) objetSurpasse).getFenetre().getListeLivraisons().values();
                    vueGraphique.surbrillanceLivraisons(livraisons);
                }

            });

            setOnMouseExited(event -> {
                ObjetVisualisable obj = getTreeTableRow().getItem();
                if (obj == null)
                    return;
                setSurbrillance(obj.getCouleurDefaut());

                vueGraphique.desactiverSurbrillance();
            });
        }

        /**
         * Applique une couleur à un élement de la table
         *
         * @param couleur couleur à appliquer
         */
        private void setSurbrillance(CouleurTexte couleur) {
            switch (couleur) {
                case SURBRILLANCE:
                    setStyle("-fx-background-color: yellow; -fx-text-fill: black;");
                    break;
                case NON_SURBRILLANCE:
                    setStyle("-fx-background-color: white; -fx-text-fill: black;");
                    break;
                case RETARD:
                    setStyle("-fx-background-color: red; -fx-text-fill: black;");
                    break;
                default:
                    break;
            }
        }

    }
}
