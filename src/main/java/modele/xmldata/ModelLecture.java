package modele.xmldata;

import java.util.List;

/**
 *
 * @author maxou
 */
public interface ModelLecture
{
    public PlanDeVille getPlan();

    public Demande getDemande();
    
    public List<List<Integer>> getTournee();
}
