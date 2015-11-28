import vue.FenetrePrincipale;

/**
 * Point d'entrée de l'application
 *
 * @author Maxou
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
    }
}
