
import controleur.Controleur;
import controleur.ControleurInterface;
import controleur.ModelObserveur;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author robinroyer
 */
public class ControleurTest {
     
    /*  TODO : TEST LES METHODES
        +ajouterDesactObserver():void
        +ajouterModelObserver():void
        +cliqueSurPlan():void
        +cliqueAnnuler():boolean
        +cliqueOutilAjouter():void
        +cliqueOutilSupprimer():void
        +cliqueOutilEchanger():void
        +getModel():ModelLecture
        +cliqueCalculerTourne():void 
    */

    ModelObserveur observer;
    boolean miseAJourAppelee;
    ControleurInterface controleurInterface;

    @Before
    public void setUp() {

        controleurInterface = new Controleur();
        miseAJourAppelee = false;
        observer = new ModelObserveur() {
            @Override
            public void notifierLesOberseursDuModel() {
                miseAJourAppelee = true;
            }
        };
        controleurInterface.ajouterModelObserver(observer);
    }

    @Test
    public void testchargerPlan() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);
        assert (miseAJourAppelee);
    }

    @Test
    public void testchargerLivraisons() {
        File plan = new File("samples/plan10x10.xml");
        controleurInterface.chargerPlan(plan);

        File livraisons = new File("samples/livraison10x10-3.xml");
        controleurInterface.chargerLivraisons(livraisons);

        assert (miseAJourAppelee);
    }


}
