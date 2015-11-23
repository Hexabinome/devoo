package vue.vueTextuelle;

import modele.xmldata.Fenetre;

/**
 * Created by elmhaidara on 23/11/15.
 */
public class DetailFenetre extends ObjetVisualisable {

    private final Fenetre fenetre;

    public DetailFenetre(Fenetre fenetre) {
        this.fenetre = fenetre;
    }

    @Override
    public String afficherCaracteriquesGloable() {
        return convertirEnHeureLisible(fenetre.getTimestampDebut()) + " - " + convertirEnHeureLisible(
                fenetre.getTimestampFin());
    }

    @Override
    public String afficherCaracteriqueSpeciale() {
        // Pour la fenetre ya rien de particulier à afficher
        return "";
    }

    /**
     * Convertis un temps en seconde en HH:mm:ss
     */
    private static String convertirEnHeureLisible(int tempsEnSeconde) {
        int heure = tempsEnSeconde / 3600;
        int mn = (tempsEnSeconde % 3600) / 60;
        int sec = tempsEnSeconde % 60;
        return String.format("%02d:%02d:%02d", heure, mn, sec);
    }

    @Override
    public void accepter(Visiteur v) {
        v.visit(this);
    }

    public Fenetre getFenetre() {
        return fenetre;
    }
}
