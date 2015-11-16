package xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Created by elmhaidara on 16/11/15.
 */
public class OuvreurDeFichier { // singleton

    private static SAXBuilder builder = new SAXBuilder();

    private static File file = new File("samples/livraison10x10-1.xml");


    public static void ouvrirPlan(){

        try{
            Document document = (Document) builder.build(file);
            Element rootElement = document.getRootElement();


        } catch (IOException e){

        } catch(JDOMException j){

        }
    }




}
