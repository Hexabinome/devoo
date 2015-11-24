package vue.vuetextuelle;

/**
 * Cette classe permet de visualer une Fenetre (de livraison) ou une livraison sous forme textuelle.
 * Cette visualisation peut se faire de deux façons façons differentes.Ces objets sont aussi visitable
 */
public abstract class ObjetVisualisable implements Visitable {
    abstract public String afficherCaracteriquesGloable();

    abstract public String afficherCaracteriqueSpeciale();
}
