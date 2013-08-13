package jo.sm.ui.act;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JMenu;

public class OpenExistingAction extends AbstractAction
{
    public OpenExistingAction()
    {
        setValue(Action.NAME, "Open...");
    }

    @Override
    public void actionPerformed(ActionEvent ev)
    {
    }

}
