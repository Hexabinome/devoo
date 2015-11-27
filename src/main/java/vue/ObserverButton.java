package vue;

import controleur.ActivationObserverInterface;
import javafx.scene.control.Button;

/**
 *
 * @author Max Schiedermeier
 */
public class ObserverButton extends Button implements ActivationObserverInterface{

    @Override
    public void notifierLesObserveurs(boolean disabled)
    {
        setDisable(disabled);
    }

}
