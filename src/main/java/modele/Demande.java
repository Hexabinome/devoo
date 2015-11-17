/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Demande de livraison chargé à partir d'un fichier XML
 * @author Mohamed El Mouctar HAIDARA
 */
public class Demande {
    
    private Intersection entrepot;
    
    private List<Fenetre> fenetres;
    
    private List<Chemin> tournee;

    public Demande(Intersection entrepot, List<Fenetre> fenetres) {
        this.entrepot = entrepot;
        this.fenetres = fenetres;
        tournee = new ArrayList<>();
    }

    public Intersection getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Intersection entrepot) {
        this.entrepot = entrepot;
    }

    public List<Fenetre> getFenetres() {
        return fenetres;
    }

    public void setFenetres(List<Fenetre> fenetres) {
        this.fenetres = fenetres;
    }

    public List<Chemin> getTournee() {
        return tournee;
    }

    public void setTournee(List<Chemin> tournee) {
        this.tournee = tournee;
    }
    
}
