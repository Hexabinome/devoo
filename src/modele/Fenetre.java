package modele;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mhaidara
 */
public class Fenetre /*implements Comparable<Fenetre>*/{
    
    private Date heureDebut;
    
    private Date heureFin;

    
    private List<Livraison> livraisons;
    
    public Fenetre(Date heureDebut, Date heureFin) {
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        
        livraisons = new ArrayList<>();
    }

    public Date getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(Date heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Date getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(Date heureFin) {
        this.heureFin = heureFin;
    }

    public List<Livraison> getLivraisons() {
        return livraisons;
    }

    public void setLivraisons(List<Livraison> livraisons) {
        this.livraisons = livraisons;
    }
    
    
    
    
}
