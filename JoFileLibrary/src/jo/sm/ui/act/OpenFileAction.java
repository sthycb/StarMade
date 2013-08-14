package jo.sm.ui.act;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import jo.sm.data.SparseMatrix;
import jo.sm.logic.StarMadeLogic;
import jo.sm.ship.data.Block;
import jo.sm.ship.data.Data;
import jo.sm.ship.logic.DataLogic;
import jo.sm.ship.logic.ShipLogic;
import jo.sm.ui.RenderFrame;
import jo.sm.ui.logic.ShipSpec;
import jo.vecmath.Point3i;

@SuppressWarnings("serial")
public class OpenFileAction extends GenericAction
{
    private RenderFrame mFrame;
    
    public OpenFileAction(RenderFrame frame)
    {
        mFrame = frame;
        setName("Open File...");
        setToolTipText("Open a file on disk");
    }

    @Override
    public void actionPerformed(ActionEvent ev)
    {
        JFileChooser chooser = new JFileChooser(StarMadeLogic.getInstance().getBaseDir());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Starmade Ship File", "smd2");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(mFrame);
        if(returnVal == JFileChooser.APPROVE_OPTION) 
        {
           File smb2 = chooser.getSelectedFile();
           try
           {
               FileInputStream fis = new FileInputStream(smb2);
               Data datum = DataLogic.readFile(fis, true);
               Map<Point3i, Data> data = new HashMap<Point3i, Data>();
               data.put(new Point3i(), datum);
               SparseMatrix<Block> grid = ShipLogic.getBlocks(data);
               ShipSpec spec = new ShipSpec();
               spec.setType(ShipSpec.FILE);
               spec.setFile(smb2);
               mFrame.setSpec(spec);
               mFrame.getClient().setGrid(grid);
           }
           catch (IOException e)
           {
               e.printStackTrace();
           }
        }
    }

}
