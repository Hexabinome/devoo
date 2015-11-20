package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author Maxou
 */
class EtatPrincipal implements EtatInterface
{

    public EtatPrincipal()
    {
    }

    @Override
    public EtatInterface cliqueAnnuler()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueRetablir()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueSurListItem(int livraisonId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface chargerPlan(File plan) throws JDOMException, SAXException, IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface chargerLivraisons(File livraisons) throws JDOMException, SAXException, ParseException, IOException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueSurPlan(int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public EtatInterface cliqueCalculerTournee()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
