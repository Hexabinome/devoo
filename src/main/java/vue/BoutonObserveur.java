package vue;

import controleur.observable.ActivationObservableInterface;
import javafx.scene.control.Button;

/**
 * Bouton particulier qui observe les modifications au niveau du modèle pour savoir si elle doit s'activer ou pas.
 * @author Max Schiedermeier
 */
public class BoutonObserveur extends Button implements ActivationObservableInterface {

    @Override
    public void notifierObserveursActivation(boolean disabled) {
        setDisable(disabled);
    }

}
