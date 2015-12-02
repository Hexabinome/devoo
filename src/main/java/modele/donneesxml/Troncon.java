package modele.donneesxml;

import java.io.Serializable;

/**
 * Représente un tronçon de rue entre deux intersections.
 * @author Mohamed El Mouctar HAIDARA / maxou
 */
public class Troncon  implements Serializable {

    /**
     * Nom de la rue dans lequel se trouve le tronçon.
     */
    private String nomRue;

    /**
     * Vitesse moyenne de circulation sur le troncon.
     */
    private final float vitesse;

    /**
     * Longueur du tronçon.
     */
    private final float longueur;

    /**
     * Durée de traversée du tronçon.
     */
    private final float duree;
    
    /** L'identifiant de l'intersection cible */
    private final int idDestination;

    /**
     * Constructeur d'un tronçon
     * @param nomRue Le nom de la rue
     * @param vitesse La vitesse moyenne sur le tronçon
     * @param longueur La longueur du tronçon
     * @param idDestination L'identifiant de l'intersection cible
     */
    public Troncon(String nomRue, float vitesse, float longueur, int idDestination) {
        this.nomRue = nomRue;
        this.vitesse = vitesse;
        this.longueur = longueur;
        this.duree = longueur / vitesse;
        this.idDestination = idDestination;
    }
    
    /**
     * @return Le nom de la rue
     */
    public String getNomRue() {
        return nomRue;
    }

    /**
     * @param nomRue Le nouveau nom de rue
     */
    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    /**
     * @return La vitesse sur tronçon
     */
    public float getVitesse() {
        return vitesse;
    }

    /**
     * @return La longueur du tronçon
     */
    public float getLongueur() {
        return longueur;
    }

    /**
     * @return La durée sur le tronçon
     */
    public float getDuree() {
        return duree;
    }

    @Override
    public String toString() {
        return "Troncon{" +
                "nomRue='" + nomRue + '\'' +
                ", vitesse=" + vitesse +
                ", longueur=" + longueur +
                ", duree=" + duree +
                '}';
    }
    
    /**
     * @return Le coût du tronçon, selon la vitesse et la durée
     */
    public float getCout() {
    	return vitesse * duree;
    }

    /**
     * @return L'identifiant de l'intersection cible
     */
    public int getIdDestination() {
        return idDestination;
    }
    
}
