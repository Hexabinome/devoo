package vue.vueTextuelle;

import modele.xmldata.Fenetre;

/**
 * Gére l'affichage sous forme textuelle des details d'une fenêtre de livraison dans la TreeTableView.
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
        // Pour la fenetre ya rien de particulier à afficher dans ce cas
        return "";
    }

    /**
     * Convertis un temps en seconde en chaine de caractère sous la forme HH:mm:ss
     * @param tempsEnSeconde temps à convertir
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
