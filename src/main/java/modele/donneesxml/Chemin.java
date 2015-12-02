package modele.donneesxml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Objet représentant le chemin entre deux livraisons
 */
public class Chemin implements Serializable {

    /** Le coût du chemin */
    private float cout;
    
    /** Les tronçons par lesquelles passent le chemin */
    private final ArrayList<Troncon> troncons;
    
    /** Identifiant de la livraison de départ */
    private final int idDepart;
    
    /** Identifiant de la livraison d'arrivée */
    private final int idFin;

    /** Constructeur d'un chemin
     * @param cout Le coût du chemin
     * @param troncons Liste des tronçons par lesquelles passe le chemin
     * @param idDepart Identifiant de la livraison de départ
     * @param idFin Identifiant de la livraison d'arrivée
     */
    public Chemin(float cout, ArrayList<Troncon> troncons, int idDepart, int idFin) {
        this.cout = cout;
        this.troncons = troncons;
        this.idDepart = idDepart;
        this.idFin = idFin;
    }

    /** Renvoie le coût du chemin
     * @return Le coût du chemin
     */
    public float getCout() {
        return cout;
    }

    /** Affecte le coût du chemin
     * @param cout Nouveau coût du chemin
     */
    public void setCout(float cout) {
        this.cout = cout;
    }

    /** Renvoie la liste des tronçons
     * @return Liste en lecture seule des tronçons par lesquelles passe le chemin
     */
    public List<Troncon> getTroncons() {
        return Collections.unmodifiableList(troncons);
    }

    /** Renvoie l'identifiant de la livraison de départ
     * @return L'identifiant de la livraison de départ
     */
    public int getIdDepart() {
        return idDepart;
    }

    /** Renvoie l'identifiant de la livraison d'arrivée
     * @return L'identifiant de la livraison d'arrivée
     */
    public int getIdFin() {
        return idFin;
    }

}
