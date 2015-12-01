package vue;

import controleur.observable.MessageObservableInterface;
import javafx.scene.text.Text;

/**
 *
 * @author Max Schiedermeier
 */
public class ObserveurMessageChamps extends Text implements MessageObservableInterface {

    @Override
    public void notifierObserveursMessage(String message) {
        setText(message);
    }

}
