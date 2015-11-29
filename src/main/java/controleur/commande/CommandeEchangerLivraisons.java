package controleur.commande;

import controleur.ControleurDonnees;

public class CommandeEchangerLivraisons implements Commande {

	private final ControleurDonnees donnees;
	private final int idLivraison1;
	private final int idLivraison2;
        private int nouvelleLivraisonId1;
        private int nouvelleLivraisonId2;
	
	public CommandeEchangerLivraisons(ControleurDonnees donnees, int idLivraison1, int idLivraison2) {
		this.donnees = donnees;
		this.idLivraison1 = idLivraison1;
		this.idLivraison2 = idLivraison2;
	}

	@Override
	public void executer() throws CommandeException {
		nouvelleLivraisonId1 = donnees.getModele().echangerLivraisons(idLivraison1, idLivraison2, Integer.MIN_VALUE, Integer.MIN_VALUE);
                nouvelleLivraisonId2 = nouvelleLivraisonId1-1;
	}

	@Override
	public void annuler() {
		donnees.getModele().echangerLivraisons(nouvelleLivraisonId1, nouvelleLivraisonId2, idLivraison1, idLivraison2);
                donnees.notifierAllMessageObserveurs("L'echange a ete annullee.");
                donnees.notifyAllModelObserveurs();
	}
	
	@Override
	public boolean estAnnulable() {
		return true;
	}
}
