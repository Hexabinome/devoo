package controleur.commande;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import modele.donneesxml.Modele;

/**
 * Représente une commande annulable
 * @author Max Schiedermeier
 */
public abstract class CommandeAnnulable implements Commande {

    /**
     * On stocke une copie du modèle
     */
    private Modele modeleCopie;

    void backupModele(Modele modele) {
        // http://stackoverflow.com/questions/64036/how-do-you-make-a-deep-copy-of-an-object-in-java
        
        ObjectOutputStream oos = null;
        try {
            //serialiser modele
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(modele);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();

            //deserializer modele
            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            modeleCopie = (Modele) new ObjectInputStream(bais).readObject();
        }
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to backup model.");
        }
        finally {
            try {
                oos.close();
            }
            catch (IOException ex) {
                throw new RuntimeException("Unable to backup model.");
            }
        }
    }

    Modele getModelCopie()
    {
        return modeleCopie;
    }

}
