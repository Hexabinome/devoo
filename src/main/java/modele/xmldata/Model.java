package modele.xmldata;

/**
 *
 * @author Max Schiedermeier
 */
public class Model implements ModelLecture
{

    private final PlanDeVille plan;
    private final Demande demande;
    private Graphe graphe;

    public Model(PlanDeVille plan, Demande demande)
    {
        this.plan = plan;
        this.demande = demande;
    }

    @Override
    public Graphe getGraphe()
    {
        return graphe;
    }

    public void setGraphe(Graphe graphe)
    {
        this.graphe = graphe;
    }

    @Override
    public PlanDeVille getPlan()
    {
        return plan;
    }

    @Override
    public Demande getDemande()
    {
        return demande;
    }

}
