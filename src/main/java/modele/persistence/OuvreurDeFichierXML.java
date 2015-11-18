package modele.persistence;

import javafx.stage.FileChooser;
import modele.xmldata.Demande;
import modele.xmldata.PlanDeVille;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

/**
 * Ouvre un fichier XML
 */
public class OuvreurDeFichierXML { // singleton

    private static OuvreurDeFichierXML instance = null;

    protected static OuvreurDeFichierXML getInstance() {
        if (instance == null)
            instance = new OuvreurDeFichierXML();
        return instance;
    }

    private OuvreurDeFichierXML() {
    }

    /**
     * Ouvre une boite de dialogue pour choisir un fichier
     * http://stackoverflow.com/questions/25491732/how-do-i-open-the-javafx-filechooser-from-a-controller-class
     */
    protected File ouvrirSelectionneurDeFichier() {
        FileChooser fileChooser = new FileChooser();
        //  Filtrage de l'extension
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Fichier xml (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        // Affichage de la boite de dialogque + récuperation du fichier choisi

        return fileChooser.showOpenDialog(null);
    }


}
