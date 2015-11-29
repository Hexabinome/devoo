package modele.xmldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Demande de livraison charge a partir d'un fichier XML
 *
 * @author Mohamed El Mouctar HAIDARA
 */
public class Demande
{

    private final Intersection entrepot;

    private final List<Fenetre> fenetres;

    /**
     * Lors de la consturction d'une demande, on ajoute automatiquement
     * l'entrepot comme premiere fenêtre
     *
     * @param entrepot
     * @param fenetres
     */
    public Demande(Intersection entrepot, List<Fenetre> fenetres)
    {
        this.entrepot = entrepot;
        Fenetre fenetreEntrepot = new Fenetre(0, 0);

        Livraison livraisonEntrepot = new Livraison(-1, -1, entrepot.getId());
        fenetreEntrepot.ajouterLivraison(livraisonEntrepot.getId(), livraisonEntrepot);

        this.fenetres = new ArrayList<>();
        this.fenetres.add(fenetreEntrepot);
        this.fenetres.addAll(fenetres);
    }

    public Intersection getEntrepot()
    {
        return entrepot;
    }

    public List<Fenetre> getFenetres()
    {
        return Collections.unmodifiableList(fenetres);
    }

    @Override
    public String toString()
    {
        return "Demande{"
                + "entrepot=" + entrepot
                + ", fenetres=" + fenetres
                + '}';
    }

    /**
     * On parcour la liste de fenêtre pour calculer le graphe. Chaque fenêtre
     * insert dans le graphe des Chemins en fonciton de ces points de livraisons
     * et les points de livraisons de la fenêtre suivante La première fenêtre
     * est l'entrepot
     *
     * @param plan
     * @return
     */
    public GrapheRealisation creerGraphe(PlanDeVille plan)
    {
        GrapheRealisation graphe = new GrapheRealisation(getNombreLivraison());

        for (int iFenetre = 0; iFenetre < fenetres.size() - 1; iFenetre++) {
            fenetres.get(iFenetre).calculerChemins(plan, graphe, fenetres.get(iFenetre + 1));
        }

        //Calcule le chemin entre la dernière fenêtre et l'entrepot
        fenetres.get(fenetres.size() - 1).calculerChemins(plan, graphe, fenetres.get(0));

        return graphe;
    }

    private int getNombreLivraison()
    {
        Set<Integer> livraison = new HashSet<>();

        for (int iFenetre = 0; iFenetre < fenetres.size(); iFenetre++) {
            livraison.addAll((fenetres.get(iFenetre).getListeLivraisons().keySet()));
        }

        return livraison.size();
    }

    public void supprimerLivraision(int livraisonId)
    {
        fenetres.stream().forEach((fenetre) -> {
            fenetre.supprimerLivraison(livraisonId);
        });
    }

    /**
     * Recuperer une livraison a parti de son id.
     *
     * @param idLivraison
     * @return la livraison si elle existe, null sinon
     */
    public Livraison identifierLivraison(int idLivraison)
    {
        Livraison result = null;

        for (Fenetre f : fenetres) {
            result = (f.getListeLivraisons().keySet().contains(idLivraison) ? f.getLivraison(idLivraison) : result);
        }

        return result;
    }

    /**
     * Renvoie la fenêtre à laquelle appartient la livraison
     *
     * @param idLivraison L'identifiant de la livraison qui est dans la fenêtre
     * @return La fenêtre ou null si livraison non trouvée
     */
    public Fenetre getFenetreDeLivraison(int idLivraison)
    {
        for (Fenetre f : fenetres) {
            if (f.getListeLivraisons().keySet().contains(idLivraison))
                return f;
        }
        throw new RuntimeException("Fenetre introuvable pour livraison: " + idLivraison);
    }

	public int getMaxIdLivraison() {
		int idLivraisonMax = 0;
		for(Fenetre f: fenetres)
		{
			int idMax = f.getMaxIdLivraison();
			if(idLivraisonMax < idMax)
				idLivraisonMax = idMax;
				
		}
		return idLivraisonMax;
	}

}
