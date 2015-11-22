package modele.xmldata;

/**
 * Représente le visiteur du pattern visiteur.
 * Dans notre cas on veut récuperer quelques infos à afficher au niveau de l'interface graphique.
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public interface Visiteur {

    String visit(Fenetre fenetre);

    String visit(Livraison livraison);
}
