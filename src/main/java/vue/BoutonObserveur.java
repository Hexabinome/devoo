package vue;

import controleur.ActivationObserverInterface;
import javafx.scene.control.Button;

/**
 * Bouton particulier qui observe les modifications au niveau du modèle pour savoir si elle doit s'activer ou pas.
 * @author Max Schiedermeier
 */
public class BoutonObserveur extends Button implements ActivationObserverInterface{

    @Override
    public void notifierLesObserveurs(boolean disabled)
    {
        setDisable(disabled);
    }

}
