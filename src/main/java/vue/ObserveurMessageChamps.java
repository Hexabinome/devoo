package vue;

import controleur.observateur.MessageObservateur;
import javafx.scene.text.Text;

/**
 *
 * @author Max Schiedermeier
 */
public class ObserveurMessageChamps extends Text implements MessageObservateur {

    @Override
    public void notifierObservateursMessage(String message) {
        setText(message);
    }

}
