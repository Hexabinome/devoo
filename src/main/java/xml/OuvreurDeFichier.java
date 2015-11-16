
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

    
    public static void ouvrirLivraison( File livraisonXml){
        // TODO:validifcation du fichier xml      
        try {            
            Document document = (Document) builder.build(livraisonXml);
            Element journeeType = document.getRootElement();
            //entrepot   
            System.out.println("entrepot" + journeeType.getChild("Entrepot").getAttribute("adresse").getIntValue());
            //plage horaires
            Element plageHoraires = journeeType.getChild("PlagesHoraires");
            //plages
            List<Element> listePlage = plageHoraires.getChildren("Plage");

            for (Element plage : listePlage){
                System.out.println("heure de debut : " + plage.getAttributeValue("heureDebut"));
                System.out.println("Heure de fin : " + plage.getAttribute("heureFin"));
                //livraisons
                Element livraisons = plage.getChild("Livraisons");
                //livraison
                List<Element> listeLivraison = livraisons.getChildren("Livraison");
                for (Element livraison : listeLivraison) {
                    System.out.println("id : " + livraison.getAttribute("id").getIntValue());
                    System.out.println("client : " + livraison.getAttribute("client").getIntValue());
                    System.out.println("adresse : " + livraison.getAttribute("adresse").getIntValue());
                }
            }
        } catch (JDOMException ex) {
            ex.printStackTrace(); 
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
