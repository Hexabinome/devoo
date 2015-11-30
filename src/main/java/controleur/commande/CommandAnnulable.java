package controleur.commande;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import modele.xmldata.Modele;

/**
 *
 * @author Max Schiedermeier
 */
public abstract class CommandAnnulable implements Commande
{

    private Modele modelCopie;

    @Override
    public boolean estAnnulable()
    {
        return true;
    }

    void backupModele(Modele modele)
    {
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
            modelCopie = (Modele) new ObjectInputStream(bais).readObject();
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
        return modelCopie;
    }

}
