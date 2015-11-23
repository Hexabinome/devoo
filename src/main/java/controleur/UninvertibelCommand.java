package controleur;

/**
 *
 * @author Max Schiedermeier
 */
public abstract class UninvertibelCommand implements Command
{

    @Override
    public boolean isUndoable()
    {
        return false;
    }

    @Override
    public void annuler()
    {
        throw new RuntimeException("Il n'est pas possible d'annuler ce command.");
    }


}
