package vue;

import controleur.ActivationObserveurInterface;
import javafx.scene.control.Button;

/**
 * Bouton particulier qui observe les modifications au niveau du modèle pour savoir si elle doit s'activer ou pas.
 * @author Max Schiedermeier
 */
public class BoutonObserveur extends Button implements ActivationObserveurInterface {

    @Override
    public void notifierLesObserveursActivation(boolean disabled) {
        setDisable(disabled);
    }

}
