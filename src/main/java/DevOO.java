import vue.FenetrePrincipale;

/**
 * Point d'entrée de l'application
 *
 * @author Maximilian Schiedermeier
 */
public class DevOO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
            new Thread() {
                @Override
                public void run() {
                    javafx.application.Application.launch(FenetrePrincipale.class);
                }
            }.start();
    	
    	/*
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            PlanDeVille planDeVille = DeserialiseurXML.ouvrirPlanDeVille(
                    classLoader.getResourceAsStream("samples/plan10x10.xml"));
            DeserialiseurXML.ouvrirLivraison(classLoader.getResourceAsStream("samples/livraison10x10-1.xml"),
                    planDeVille);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            System.err.println("Pas valide car : ");
            e.printStackTrace();
        } catch (ParseException e) {
            System.err.println("Probleme lors du parsing de l'heure");
            e.printStackTrace();
        }*/
    }
}
