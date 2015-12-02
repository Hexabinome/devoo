package controleur.commande;

import controleur.ControleurDonnees;

/**
 * La commande d'échange de livraison
 * @author David
 */
public class CommandeEchangerLivraisons extends CommandeAnnulable {

    /**
     * Le contrôleur de données
     */
    private final ControleurDonnees donnees;
    
    /**
     * L'identifiant de la première livraison de l'échange
     */
    private final int idLivraison1;
    
    /**
     * L'identifiant de la deuxième livraison de l'échange
     */
    private final int idLivraison2;

    /**
     * Constructeur de la commande d'échange de livraisons
     * @param donnees Le contrôleur de données
     * @param idLivraison1 L'identifiant de la première livraison a échangé
     * @param idLivraison2 L'identifiant de la deuxième livraison a échangé
     */
    public CommandeEchangerLivraisons(ControleurDonnees donnees, int idLivraison1, int idLivraison2) {
        this.donnees = donnees;
        this.idLivraison1 = idLivraison1;
        this.idLivraison2 = idLivraison2;
    }

    @Override
    public void executer() throws CommandeException {
        // Faire une copie du modele
        super.backupModele(donnees.getModele());
        donnees.getModele().echangerLivraisons(idLivraison1, idLivraison2);
        
        // MAJ des horaires de passage
        donnees.getModele().remplirHoraires();
        
        donnees.notifierObservateursMessage(String.format("L'échange %d <-> %d a été effectué.", idLivraison1, idLivraison2));
        donnees.notifierObservateursModele();
        donnees.notifierObservateursAnnuler(false);

        if (donnees.getHist().estVideCommandesARetablir()) {
            donnees.notifierObservateursRetablir(true);
        }
    }

    @Override
    public void annuler() {
        donnees.setModele(super.getModelCopie());
        //donnees.getModele().echangerLivraisons(nouvelleLivraisonId1, nouvelleLivraisonId2, idLivraison1, idLivraison2);
        donnees.notifierObservateursMessage(String.format("L'échange %d <-> %d a été annulé.", idLivraison1, idLivraison2));
        donnees.notifierObservateursModele();
        
        donnees.notifierObservateursRetablir(false);

        if (donnees.getHist().estVideCommandesAAnnuler()) {
            donnees.notifierObservateursAnnuler(true);
        }
    }

    @Override
    public boolean estAnnulable() {
        return true;
    }

}
