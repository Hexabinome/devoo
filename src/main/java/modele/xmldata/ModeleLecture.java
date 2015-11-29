package modele.xmldata;

import java.util.List;

/**
 * @author maxou
 */
public interface ModeleLecture
{

    PlanDeVille getPlan();

    Demande getDemande();

    /**
     * Cette methode permette d'acceder a la tournee (representee par une Liste
     * des Listes). Chaque Liste interne represente les intersection a parcourir
     * pour realiser les livraisons d'une fenetre. En cas que la tournee n'a pas
     * encore ete calcule, cette methode va returner null.
     *
     * @return null / une representation des intersections par fenetres a
     * parcourir.
     */
    List<List<Integer>> getTournee();

	String genererFeuilleDeRoute();

}
