package modele.xmldata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chemin
{

    private float cout;
    private final ArrayList<Troncon> troncons;
    private final int idDepart;
    private final int idFin;

    public Chemin(float cout, ArrayList<Troncon> troncons, int idDepart, int idFin)
    {
        super();
        this.cout = cout;
        this.troncons = troncons;
        this.idDepart = idDepart;
        this.idFin = idFin;

        troncons = new ArrayList<>();
    }

    public float getCout()
    {
        return cout;
    }

    public void setCout(float cout)
    {
        this.cout = cout;
    }

    public List<Troncon> getTroncons()
    {
        return Collections.unmodifiableList(troncons);
    }

    /*
    public void setTroncons(ArrayList<Troncon> troncons)
    {
        this.troncons = troncons;
    }*/

    public int getIdDepart()
    {
        return idDepart;
    }

    public int getIdFin()
    {
        return idFin;
    }

}
