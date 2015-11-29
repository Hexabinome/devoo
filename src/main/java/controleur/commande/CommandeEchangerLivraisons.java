package controleur.commande;

import controleur.ControleurDonnees;

public class CommandeEchangerLivraisons implements Commande {

	private ControleurDonnees donnees;
	private int idLivraison1;
	private int idLivraison2;
	
	public CommandeEchangerLivraisons(ControleurDonnees donnees, int idLivraison1, int idLivraison2) {
		this.donnees = donnees;
		this.idLivraison1 = idLivraison1;
		this.idLivraison2 = idLivraison2;
	}

	@Override
	public void executer() throws CommandeException {
		donnees.getModele().echangerLivraisons(idLivraison1, idLivraison2);
	}

	@Override
	public void annuler() {
		donnees.getModele().echangerLivraisons(idLivraison2, idLivraison1);
		
	}
	
	@Override
	public boolean estAnnulable() {
		return true;
	}
}
