package modele.xmldata;

/**
 *
 * @author Max Schiedermeier
 */
public class Model {

    private final PlanDeVille plan;
    private final Demande demande;
    
    Model(PlanDeVille plan, Demande demande)
    {
        this.plan = plan;
        this.demande = demande;
    }
}
