package jo.sm.logic;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import jo.sm.data.StarMade;

public class StarMadeLogic
{
    private static StarMade mStarMade;
    
    public static synchronized StarMade getInstance()
    {
        if (mStarMade == null)
        {
            mStarMade = new StarMade();
        }
        return mStarMade;
    }
    
    public static void setBaseDir(String baseDir)
    {
        File bd = new File(baseDir);
        if (!bd.exists())
            throw new IllegalArgumentException("Base directory '"+baseDir+"' does not exist");
        getInstance().setBaseDir(bd);
        List<URL> urls = new ArrayList<URL>();
        File pluginDir = new File(baseDir, "jo_mods");
        if (pluginDir.exists())
            for (File jar : pluginDir.listFiles())
                if (jar.getName().endsWith(".jar"))
                {
                    try
                    {
                        urls.add(jar.toURI().toURL());
                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                }
        URLClassLoader modLoader = new URLClassLoader(urls.toArray(new URL[0]), StarMadeLogic.class.getClassLoader());
        getInstance().setModLoader(modLoader);
    }
    
}
