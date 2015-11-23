package controleur;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import modele.persistance.DeserialiseurXML;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author Max Schiedermeier
 */
public class CommandChargerPlan extends UninvertibelCommand
{

    private final ControleurDonnees controleurDonnees;
    private final File planFichier;

    public CommandChargerPlan(ControleurDonnees controleurDonnees, File plan)
    {
        this.controleurDonnees = controleurDonnees;
        planFichier = plan;
    }

    @Override
    public void executer() throws JDOMException, IOException, SAXException, ParseException
    {
        //remplacer plan qui est charge d'un nouveau plan (ssi le chargement du xml a reussi)
        controleurDonnees.setPlan(DeserialiseurXML.ouvrirPlanDeVille(planFichier));
        controleurDonnees.notifyAllActObserveurs(true);
    }

}
