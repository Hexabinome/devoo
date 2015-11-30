package controleur.commande;

import controleur.ControleurDonnees;

public class CommandeEchangerLivraisons extends CommandAnnulable
{

    private final ControleurDonnees donnees;
    private final int idLivraison1;
    private final int idLivraison2;

    public CommandeEchangerLivraisons(ControleurDonnees donnees, int idLivraison1, int idLivraison2)
    {
        this.donnees = donnees;
        this.idLivraison1 = idLivraison1;
        this.idLivraison2 = idLivraison2;
    }

    @Override
    public void executer() throws CommandeException
    {
        //faire une copie du modele
        super.backupModele(donnees.getModele());
        donnees.getModele().echangerLivraisons(idLivraison1, idLivraison2);
        
        //MAJ des horaires de passage
        donnees.getModele().remplirHoraires();
        
        donnees.notifierAllMessageObserveurs("L'echange a ete effectue.");
        donnees.notifyAllModelObserveurs();
        donnees.notifyAllAnnulerObserveurs(false);

        if (donnees.getHist().estVideCommandesARetablir())
            donnees.notifyAllRetablirObserveurs(true);

    }

    @Override
    public void annuler()
    {
        donnees.setModele(super.getModelCopie());
        //donnees.getModele().echangerLivraisons(nouvelleLivraisonId1, nouvelleLivraisonId2, idLivraison1, idLivraison2);
        donnees.notifierAllMessageObserveurs(String.format("L'échange %d <-> %d a été annulée.", idLivraison1, idLivraison2));
        donnees.notifyAllModelObserveurs();
        
        donnees.notifyAllRetablirObserveurs(false);

        if (donnees.getHist().estVideCommandesAAnnuler())
            donnees.notifyAllAnnulerObserveurs(true);
    }

    @Override
    public boolean estAnnulable()
    {
        return true;
    }

}
