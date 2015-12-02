package vue;

/**
 * Cette classe permet de visualiser une fenêtre (de livraison) ou une livraison sous forme textuelle.
 * Cette visualisation peut se faire de deux façons façons différentes en fonction du type concret de l'objet.
 */
public abstract class ObjetVisualisable {

    /**
     * @return Les caractéristiques globales de l'élément affiché
     */
    abstract public String afficherCaracteriquesGlobales();

    /**
     * @return Les caractéristiques spéciales de l'élément affiché
     */
    abstract public String afficherCaracteriquesSpeciales();

    /** Couleur du texte */
    private CouleurTexte couleurDefaut = CouleurTexte.NON_SURBRILLANCE;

    /**
     * @param couleur La nouvelle couleur
     */
    public void setCouleurDefaut(CouleurTexte couleur) {
        couleurDefaut = couleur;
    }

    /**
     * @return Retourne la couleur actuelle
     */
    public CouleurTexte getCouleurDefaut() {
        return couleurDefaut;
    }

    /**
     * Convertit un temps en seconde en chaine de caractère sous la forme HH:mm:ss
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
