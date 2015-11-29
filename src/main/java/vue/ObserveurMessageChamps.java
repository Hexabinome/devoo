package vue;

import controleur.MessageObserveur;
import javafx.scene.text.Text;

/**
 *
 * @author Max Schiedermeier
 */
public class ObserveurMessageChamps extends Text implements MessageObserveur {

    @Override
    public void notifierMessagObserveur(String message)
    {
        setText(message);
    }

}
