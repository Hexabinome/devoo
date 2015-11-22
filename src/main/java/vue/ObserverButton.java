package vue;

import controleur.MainActivationObserverInterface;
import javafx.scene.control.Button;

/**
 *
 * @author Max Schiedermeier
 */
public class ObserverButton extends Button  implements MainActivationObserverInterface{

    @Override
    public void notifyObserver(boolean disabled)
    {
        setDisable(disabled);
    }

}
