/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controleur;

import java.io.IOException;
import java.text.ParseException;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 *
 * @author maxou
 */
public interface Commande
{

    /**
     * Il y a des commandes qui on ne peut pas annuler, ep.e. charger un fichier
     * xml ou calculer la tournee.
     *
     * @return boolean, si cette type de commande est annulable
     */
    boolean estAnnulable();

    /**
     * La creation d'une commande ne declenche pas automatiquement son execution
     * (c'est notamment utile pour l'historique, qund on veut reexecuter une
     * commande)
     */
    void executer() throws CommandException;

    /**
     * Ssi un command est annulable on peut revenir dans un ancien etat du model
     * en appelant cette methode.
     */
    void annuler();

}
