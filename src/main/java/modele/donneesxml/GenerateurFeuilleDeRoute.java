package modele.donneesxml;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/** S'occupe de la génération de la feuille de route
 */
public class GenerateurFeuilleDeRoute {

	/**
	 * Génère la feuille de route
	 * @param modele Le modèle
	 * @param livraisonTournee La tournée calculée
	 * @return Une chaine de caractère contenant tout le fichier à écrire
	 */
	public static String genererFeuilleDeRoute(ModeleLecture modele, List<List<Livraison>> livraisonTournee) {
        StringBuilder feuilleDeRoute = new StringBuilder(400);
        List<List<Integer>> intersectionTournee = modele.getTournee();
        Livraison entrepot = livraisonTournee.get(0).get(0);
        int intersectionCourante = entrepot.getAdresse();

        // Entête
        feuilleDeRoute.append("Feuille de route du ")
                .append(new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()))
                .append(" à ")
                .append(convertirEnHeureLisible(entrepot.getHoraireDePassage()))
                .append(System.lineSeparator()).append(System.lineSeparator());

        feuilleDeRoute.append("Départ prévu depuis l'entrepôt à l'adresse : ").append(intersectionCourante)
                .append(System.lineSeparator());

        for (int iFenetre = 0; iFenetre < intersectionTournee.size() - 1; ++iFenetre) {
            // Entête fenêtre
            Fenetre f = modele.getDemande().getFenetres().get(iFenetre + 1);
            feuilleDeRoute.append(System.lineSeparator())
            		.append("Fenêtre ").append(iFenetre + 1)
                    .append(" prévue de ").append(convertirEnHeureLisible(f.getTimestampDebut()))
                    .append(" à ").append(convertirEnHeureLisible(f.getTimestampFin()))
                    .append(System.lineSeparator());

            // Chaque intersection
            intersectionTournee.get(iFenetre);
            for (int iTournee = 0; iTournee < intersectionTournee.get(iFenetre).size(); ++iTournee) {
                int intersectionCible = intersectionTournee.get(iFenetre).get(iTournee);
                Troncon troncon = modele.getPlan().getIntersection(intersectionCourante).getTroncon(intersectionCible);
                // Trajet de l'intersection courante à la suivante 
                feuilleDeRoute.append(iTournee + 1).append(". ")
                        .append(VERBES_CIRCULATION[(int) Math.round(Math.random() * (VERBES_CIRCULATION.length - 1))])
                        .append(" dans la rue ").append(troncon.getNomRue())
                        .append(" pour ").append(VERBES_DESTINATION[(int) Math.round(Math.random() * (VERBES_DESTINATION.length - 1))])
                        .append(" à l'adresse ").append(intersectionCible)
                        .append(System.lineSeparator());

                intersectionCourante = intersectionCible;

                // On regarde si l'intersection est une livraison
                Livraison livraison = null;
                for (Livraison l : f.getListeLivraisons().values()) {
                    if (l.getAdresse() == intersectionCourante) {
                        livraison = l;
                        break;
                    }
                }
                if (livraison != null) {
                    feuilleDeRoute.append("* ").append(convertirEnHeureLisible(livraison.getHoraireDePassage()))
                    		.append(" -> ").append(convertirEnHeureLisible(livraison.getHoraireDePassage() + 600))
                            .append(" - Effectuez la livraison ").append(livraison.getId())
                            .append(" chez le client ").append(livraison.getClientId())
                            .append(" à l'adresse ").append(livraison.getAdresse())
                            .append(System.lineSeparator());
                }
            }
        }

        // Retour à l'entrepôt
        feuilleDeRoute.append(System.lineSeparator())
        		.append("Retour à l'entrepôt :")
                .append(System.lineSeparator());
        List<Integer> derniereFenetre = intersectionTournee.get(intersectionTournee.size() - 1);
        for (int iTournee = 0; iTournee < derniereFenetre.size(); ++iTournee) {
            int intersectionCible = derniereFenetre.get(iTournee);
            Troncon troncon = modele.getPlan().getIntersection(intersectionCourante).getTroncon(intersectionCible);
            // Trajet de l'intersection courante à la suivante 
            feuilleDeRoute.append(iTournee + 1).append(". ")
                    .append(VERBES_CIRCULATION[(int) Math.round(Math.random() * (VERBES_CIRCULATION.length - 1))])
                    .append(" dans la rue ").append(troncon.getNomRue())
                    .append(" pour ").append(VERBES_DESTINATION[(int) Math.round(Math.random() * (VERBES_DESTINATION.length - 1))])
                    .append(" l'adresse ").append(intersectionCible)
                    .append(System.lineSeparator());

            intersectionCourante = intersectionCible;
        }

        // Fin de feuille de route
        feuilleDeRoute.append(System.lineSeparator())
                .append("Cette feuille a été générée automatiquement par l'application Optimod'Lyon - H4105");

        return feuilleDeRoute.toString();
    }

    /**
     * Ex : VERBE dans la rue 10 pour arriver à l'adresse
     */
    private static final String[] VERBES_CIRCULATION = new String[] {
        "Tournez",
        "Continuez",
        "Avancez",
        "Allez"
    };

    /** Ex : Tournez dans le rue 15 pour rejoindre */
    private static final String[] VERBES_DESTINATION = new String[] {
        "rejoindre",
        "arriver à"
    };

    /**
     * Convertit un temps en seconde en chaine de caractère sous la forme HH:mm:ss
     * @param tempsEnSeconde temps à convertir
     * @return Une chaîne de caractère contenant l'heure
     */
    private static String convertirEnHeureLisible(int tempsEnSeconde) {
        int heure = tempsEnSeconde / 3600;
        int mn = (tempsEnSeconde % 3600) / 60;
        int sec = tempsEnSeconde % 60;
        return String.format("%02d:%02d:%02d", heure, mn, sec);
    }
}
