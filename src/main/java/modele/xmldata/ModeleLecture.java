package modele.xmldata;

import java.util.List;

/**
 * @author maxou
 */
public interface ModeleLecture {
    PlanDeVille getPlan();

    Demande getDemande();

    List<List<Integer>> getTournee();
}
