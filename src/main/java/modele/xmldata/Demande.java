package modele.xmldata;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Demande de livraison charge a partir d'un fichier XML
 * @author Mohamed El Mouctar HAIDARA
 */
public class Demande {
    
    private final Intersection entrepot;
    
    private final List<Fenetre> fenetres;
    
    public Demande(Intersection entrepot, List<Fenetre> fenetres) {
        this.entrepot = entrepot;
        this.fenetres = fenetres;
    }

    public Intersection getEntrepot() {
        return entrepot;
    }

    public Collection<Fenetre> getFenetres() {
        return Collections.unmodifiableCollection(fenetres);
    }
}
