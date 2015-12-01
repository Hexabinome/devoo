package controleur.commande;

import java.io.File;
import java.io.FileWriter;

import modele.xmldata.GenerateurFeuilleDeRoute;
import controleur.ControleurDonnees;

/**
 * Commande de génération de feuille de route
 * @author David
 */
public class CommandeGenererFeuilleDeRoute extends CommandeNonAnnulable {

	/**
	 * Le contrôleur de données
	 */
	private ControleurDonnees controleurDonnees;
	
	/**
	 * Le fichier a généré
	 */
	private File fichier;
	
	/**
	 * Constructeur de la commande de génération de feuille de route
	 * @param donnees Le contrôleur de données
	 * @param fichier Le fichier a généré
	 */
	public CommandeGenererFeuilleDeRoute(ControleurDonnees donnees, File fichier) {
		this.controleurDonnees = donnees;
		this.fichier = fichier;
	}
	
	@Override
	public void executer() throws CommandeException {

		try (FileWriter ecriveurDeFichier = new FileWriter(fichier)) {
			String feuille = GenerateurFeuilleDeRoute.genererFeuilleDeRoute(controleurDonnees.getModele(), controleurDonnees.getModele().getLivraisonsTournee());
    		ecriveurDeFichier.write(feuille);
    		controleurDonnees.notifierObserveursMessage(String.format("Feuille de route (%s) générée avec succès !", fichier.getName()));
		} catch (Exception e) {
			throw new CommandeException(e.getMessage());
		}
		
	}

}
