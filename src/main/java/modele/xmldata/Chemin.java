package modele.xmldata;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chemin implements Serializable
{

    private float cout;
    private final ArrayList<Troncon> troncons;
    private final int idDepart;
    private final int idFin;

    public Chemin(float cout, ArrayList<Troncon> troncons, int idDepart, int idFin)
    {
        this.cout = cout;
        this.troncons = troncons;
        this.idDepart = idDepart;
        this.idFin = idFin;
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

    public int getIdDepart()
    {
        return idDepart;
    }

    public int getIdFin()
    {
        return idFin;
    }

}
