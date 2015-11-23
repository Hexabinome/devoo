package vue.vueTextuelle;

/**
 * Représente un object visitable pour le pattern visiteur. Le pattern visiteur sera utile au niveau de l'interface
 * graphique. A implementer par toutes les classes susceptibles d'etre visitées par quelconque methode.
 * Ici un object visitable peut etre visiter de deux façons differentes d'où le fait qu'il y a deux methodes accepter
 *
 * @author Mohamed El Mouctar HAIDAR
 */
public interface Visitable {

    /**
     * Permet de récuperer l'object visitable
     */
    void accepter(Visiteur v);

}
