package vue;

import controleur.observateur.ActivationFonctionnalitesObservateur;
import javafx.scene.control.Button;

/**
 * Bouton particulier qui observe les modifications au niveau du modèle pour savoir s'il doit s'activer ou pas.
 * @author Max Schiedermeier
 */
public class BoutonObservateur extends Button implements ActivationFonctionnalitesObservateur {

    @Override
    public void notifierObservateursActivation(boolean disabled) {
        setDisable(disabled);
    }

}
