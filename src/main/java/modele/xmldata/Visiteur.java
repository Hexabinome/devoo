package modele.xmldata;

/**
 * Représente le visiteur du pattern visiteur.
 * Dans notre cas on veut récuperer quelques infos à afficher au niveau de l'interface graphique.
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public interface Visiteur {

    /**
     * Retourne l'heure de debut et de fin de la livraison sous la forme "HH:mm:ss - HH:mm:ss"
     */
    String recupererInformation(Fenetre fenetre);

    /**
     * Retourne quelques infos sur la livraison
     */
    String recupererInformation(Livraison livraison);

    void recupererObject(Fenetre fenetre);

    void recupererObject(Livraison livraison);
}
