
package xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Ouvre un fichier XML
 */
public class OuvreurDeFichier { // singleton


    private static OuvreurDeFichier instance = null;

    private static SAXBuilder builder = new SAXBuilder();


    private static File file = new File("samples/livraison10x10-1.xml");

    public static OuvreurDeFichier getInstance(){
        if(instance == null)
            instance = new OuvreurDeFichier();
        return instance;
    }

    private OuvreurDeFichier(){}

   


}
