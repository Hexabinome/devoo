
package xml;

import org.jdom2.input.SAXBuilder;

/**
 * Ouvre un fichier XML
 */
public class OuvreurDeFichier { // singleton


    private static OuvreurDeFichier instance = null;

    private static SAXBuilder builder = new SAXBuilder();

    public static OuvreurDeFichier getInstance(){
        if(instance == null)
            instance = new OuvreurDeFichier();
        return instance;
    }

    private OuvreurDeFichier(){}
}
