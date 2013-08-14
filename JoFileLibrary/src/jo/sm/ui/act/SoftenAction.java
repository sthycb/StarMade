package jo.sm.ui.act;

import java.awt.event.ActionEvent;

import jo.sm.data.SparseMatrix;
import jo.sm.ship.data.Block;
import jo.sm.ship.logic.HullLogic;
import jo.sm.ui.RenderFrame;

@SuppressWarnings("serial")
public class SoftenAction extends GenericAction
{
    private RenderFrame mFrame;
    
    public SoftenAction(RenderFrame frame)
    {
        mFrame = frame;
        setName("Soften");
        setToolTipText("Convert all hardened hull blocks to unhardened hull blocks");
    }

    @Override
    public void actionPerformed(ActionEvent ev)
    {
        SparseMatrix<Block> grid = mFrame.getClient().getGrid();
        if (grid == null)
            return;
        HullLogic.unpower(grid);
        mFrame.getClient().setGrid(grid);
    }

}
