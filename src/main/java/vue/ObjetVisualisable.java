package vue;

/**
 * Cette classe permet de visualer une Fenetre (de livraison) ou une livraison sous forme textuelle.
 * Cette visualisation peut se faire de deux façons façons differentes en fonction du type concrêt de l'objet.
 */
public abstract class ObjetVisualisable {


    abstract public String afficherCaracteriquesGloable();

    abstract public String afficherCaracteriqueSpeciale();

    private CouleurTexte couleurDefaut = CouleurTexte.NON_SURBRILLANCE;

    public void setCouleurDefaut(CouleurTexte couleur) {
        couleurDefaut = couleur;
    }

    public CouleurTexte getCouleurDefaut() {
        return couleurDefaut;
    }

    /**
     * Convertis un temps en seconde en chaine de caractère sous la forme HH:mm:ss
     *
     * @param tempsEnSeconde temps à convertir
     */
    protected static String convertirEnHeureLisible(int tempsEnSeconde) {
        int heure = tempsEnSeconde / 3600;
        int mn = (tempsEnSeconde % 3600) / 60;
        int sec = tempsEnSeconde % 60;
        return String.format("%02d:%02d:%02d", heure, mn, sec);
    }

    /**
     * Différentes couleurs possibles pour un élément dans la liste.
     */
    public enum CouleurTexte {
        SURBRILLANCE,
        NON_SURBRILLANCE,
        RETARD
    }
}
