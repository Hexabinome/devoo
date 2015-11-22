package modele.xmldata;

/**
 * Représente un object visitable pour le pattern visiteur. Le pattern visiteur sera utile au niveau de l'interface
 * graphique. A implementer par toutes les classes susceptibles d'etre visitées par quelconque methode.
 *
 * @author Mohamed El Mouctar HAIDAR
 */
public interface Visitable {
    String accepter(Visiteur v);
}
