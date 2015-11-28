package vue.vuetextuelle;

/**
 * Cette classe permet de visualer une Fenetre (de livraison) ou une livraison sous forme textuelle.
 * Cette visualisation peut se faire de deux façons façons differentes. Ces objets sont aussi Visitable (lors d'un clique
 * par exemple)
 */
public abstract class ObjetVisualisable implements Visitable {
    abstract public String afficherCaracteriquesGloable();

    abstract public String afficherCaracteriqueSpeciale();
    
    /**
     * Convertis un temps en seconde en chaine de caractère sous la forme HH:mm:ss
     * @param tempsEnSeconde temps à convertir
     */
    protected static String convertirEnHeureLisible(int tempsEnSeconde) {
        int heure = tempsEnSeconde / 3600;
        int mn = (tempsEnSeconde % 3600) / 60;
        int sec = tempsEnSeconde % 60;
        return String.format("%02d:%02d:%02d", heure, mn, sec);
    }
}
