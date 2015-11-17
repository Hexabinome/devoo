package modele.business;

import java.util.Iterator;
import java.util.List;
import modele.xmldata.Intersection;
import modele.xmldata.Model;

/**
 *
 * @author Max Schiedermeier
 */
public class GrapheCreateurDummy implements GrapheCreateur
{

    //Todo add interface that hides what must not be touched (basically all content that comes from the xmls)
    private final Model model;

    public GrapheCreateurDummy(Model model)
    {
        this.model = model;
    }

    /**
     * Utilise le model (les donnes extrahes du XML) pour creer et ajouter un
     * Graphe dans le model.
     */
    @Override
    public void creerGraphe()
    {
        //TODO;
        //create a valid dummy graph here. maybe just 2 or three nodes and the valid time to pass between them...
    }
    
        /**
     * Sommation des durees de toutes les troncons sur le chemin.
     *
     * @param chemin
     * @return
     */
    private static int calculerDureeTotale(List<Intersection> chemin)
    {
        int resultat = 0;

        Iterator<Intersection> iter = chemin.iterator();
        Intersection depart;
        Intersection cible = iter.next();

        while (iter.hasNext()) {
            depart = cible;
            cible = iter.next();
            resultat += depart.getTroncon(cible.getId()).getDuree();
        }

        return resultat;
    }

}
