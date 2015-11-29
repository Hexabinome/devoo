package vue;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import modele.xmldata.Demande;
import modele.xmldata.Fenetre;
import modele.xmldata.Livraison;
import vue.vuetextuelle.DetailFenetre;
import vue.vuetextuelle.DetailLivraison;
import vue.vuetextuelle.ObjetVisualisable;
import vue.vuetextuelle.ObjetVisualisable.CouleurTexte;
import vue.vuetextuelle.Visiteur;
import controleur.ActivationObserverInterface;
import controleur.ControleurInterface;
import controleur.ModelObserveur;

/**
 * Controleur de la TreeTableView qui affiche les livraisons et les horaires.
 * Elle passe par le controleur principale de la vue quand elle reçoie des
 * actions.
 */
public class VueTextuelle implements Initializable, Visiteur, ActivationObserverInterface,
        ModelObserveur {

    @FXML
    TreeTableView<ObjetVisualisable> tableViewFenetre;

    /**
     * Element racine de la Table qui contient tous les autres éléments.Il ne sera pas affiché dans la table
     */
    private final TreeItem<ObjetVisualisable> elementRacine = new TreeItem<>();

    /**
     * Controleur principale
     */
    private ControleurInterface controleurApplication;

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

    private VueGraphiqueAideur vueGraphique;

    public void setAideurVueGraphique(VueGraphiqueAideur vueGraphique) {
        this.vueGraphique = vueGraphique;
    
    }


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
        initialiserColonneLivraison();
        initialiserColonneHoraire();
        initialiserEcouteurs();
    }

    /**
     * Contruis la table des livraisons
     *
     * @param demande La demande de livraison chargée à partir d'un fichier XML
     */
    protected void construireVueTableLivraion(Demande demande) {
        // On recupère l'entrepot
        //TreeItem<ObjetVisualisable> entropt = new TreeItem<>(new DetailFenetre(demande.getFenetres().get(0)));
        //elementRacine.getChildren().add(entropt);
        for (Fenetre f : demande.getFenetres().subList(0, demande.getFenetres().size())) {
            elementRacine.getChildren().add(construireFenetreItem(f));
        }
    }

    private void initialiserEcouteurs() {
        colonneLivraison.setCellFactory(
                param -> new TableCellSpecial());
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
        fenetre.getListeLivraisons().forEach((integer, livraison1) -> {
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

    public void setControleurApplication(ControleurInterface controleurApplication) {
        this.controleurApplication = controleurApplication;
    }

    @Override
    public void notifierLesObserveurs(boolean disabled) {
        if (disabled) {
            effacerVueTableLivraison();
        }

    }

    /**
     * Notification déclenchée lors d'un changement dans le model
     */
    @Override
    public void notificationModelAChange() {
        effacerVueTableLivraison();
        Demande demandeModifiee = controleurApplication.getModel().getDemande();
        construireVueTableLivraion(demandeModifiee);
        
        vueGraphique.nettoyerAffichage();
        vueGraphique.afficherPlan();
        vueGraphique.construireDemande(demandeModifiee);
        vueGraphique.construireTournee(controleurApplication.getModel().getTournee());
    }

    public void initialiserObserveurs() {
        controleurApplication.ajouterDesactObserver(this);
        controleurApplication.ajouterModelObserver(this);
    }

    @Override
    public void visit(DetailFenetre detailFenetre) {
        // TODO : completer si besoin
        System.out.println(detailFenetre.getFenetre());

    }

    @Override
    public void visit(DetailLivraison detailLivraison) {
        controleurApplication.cliqueSurLivraison(detailLivraison.getLivraison().getId());
    }

    /**
     * Gestion du clic et hover sur les elements de la table. En créant une autre classe séparée, on n'aurait
     * pas pu acceder aux membres de la classe VueTableLivraisonControleur d'autant plus que la gestion des évenements
     * est intimement liée à la classe
     */
    private class TableCellSpecial extends TreeTableCell<ObjetVisualisable, String> {

        public TableCellSpecial() {
             initialiserClic();
            initialiserHover();

        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            // Pour afficher l'entrepot
            if (item != null && item.startsWith("-1")) {
                setText("Entrepot");
            } else {
                setText(item);
            }
            
           
            ObjetVisualisable obj = getTreeTableRow().getItem();
            if (obj == null) {
            	return;
            }
            if (obj instanceof DetailLivraison) {
            	DetailLivraison detail = (DetailLivraison)obj;
            	if (detail.getLivraison().estEnRetard()) {
                	obj.setCouleurDefaut(CouleurTexte.RETARD);
            	} else {
            		obj.setCouleurDefaut(CouleurTexte.NON_SURBRILLANCE);
            	}
            }
        	setSurbrillance(obj.getCouleurDefaut());
        }

        private void initialiserClic() {
            setOnMouseClicked(event -> {
                ObjetVisualisable objetVisualisable = getTreeTableRow().getTreeItem().getValue();
                if (objetVisualisable instanceof DetailLivraison)
                    controleurApplication.cliqueSurLivraison(((DetailLivraison)objetVisualisable).getLivraison().getId());
            });

        }

        private void initialiserHover() {
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
