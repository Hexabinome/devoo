package modele.xmldata;

import java.util.ArrayList;
import java.util.List;

public class Chemin {
	private float cout;
    private ArrayList<Troncon> troncons;
    private int idDepart;
    private int idFin;
    
	public Chemin(float cout, ArrayList<Troncon> troncons, int idDepart, int idFin) {
		super();
		this.cout = cout;
		this.troncons = troncons;
		this.idDepart = idDepart;
		this.idFin = idFin;
		
		troncons = new ArrayList<Troncon>();
	}
	public float getCout() {
		return cout;
	}
	public void setCout(float cout) {
		this.cout = cout;
	}
	public List<Troncon> getTroncons() {
		return troncons;
	}
	public void setTroncons(ArrayList<Troncon> troncons) {
		this.troncons = troncons;
	}
	public int getIdDepart() {
		return idDepart;
	}
	public void setIdDepart(int idDepart) {
		this.idDepart = idDepart;
	}
	public int getIdFin() {
		return idFin;
	}
	public void setIdFin(int idFin) {
		this.idFin = idFin;
	}
    
    
}
