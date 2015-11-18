package modele.xmldata;

import java.util.List;

public class Chemin {
	private int cout;
    private List<Troncon> troncons;
    private int idDepart;
    private int idFin;
    
	public Chemin(int cout, List<Troncon> troncons, int idDepart, int idFin) {
		super();
		this.cout = cout;
		this.troncons = troncons;
		this.idDepart = idDepart;
		this.idFin = idFin;
	}
	public int getCout() {
		return cout;
	}
	public void setCout(int cout) {
		this.cout = cout;
	}
	public List<Troncon> getTroncons() {
		return troncons;
	}
	public void setTroncons(List<Troncon> troncons) {
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
