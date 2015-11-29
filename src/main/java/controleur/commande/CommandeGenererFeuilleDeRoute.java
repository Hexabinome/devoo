package controleur.commande;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import modele.xmldata.GenerateurFeuilleDeRoute;
import controleur.ControleurDonnees;

public class CommandeGenererFeuilleDeRoute extends CommandeNonAnnulable {

	private ControleurDonnees controleurDonnees;
	private File fichier;
	
	public CommandeGenererFeuilleDeRoute(ControleurDonnees donnees, File fichier) {
		this.controleurDonnees = donnees;
		this.fichier = fichier;
	}
	
	@Override
	public void executer() throws CommandeException {
		
		try (FileWriter ecriveurDeFichier = new FileWriter(fichier)) {
			String feuille = GenerateurFeuilleDeRoute.genererFeuilleDeRoute(controleurDonnees.getModele(), controleurDonnees.getModele().getLivraisonsTournee());
    		ecriveurDeFichier.write(feuille);
    		controleurDonnees.notifierAllMessageObserveurs(String.format("Feuille de route (%s) générée avec succès !", fichier.getName()));
		} catch (Exception e) {
			throw new CommandeException(e.getMessage());
		}
		
	}

}
