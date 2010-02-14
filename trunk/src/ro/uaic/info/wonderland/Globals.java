/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Iulian
 */
public final class Globals {

    public static boolean testDebug = false;
    private static File dataFolder = new File("./data/");

    public static String getDataFolder() {
        String dataPath = System.getProperty("wonderland.data.path");
        if (dataPath == null) {
            try {
                dataPath = dataFolder.getCanonicalPath();
            } catch (IOException ex) {
                System.out.println("Error reading the data folder.");
                exit();
            }
        }
        return dataPath;
    }

    public static String getStanfordParserPath() {
        return getDataFolder() + "/englishPCFG.ser.gz";
    }

    public static String getDefaultParseKBPath() {

        return getDataFolder() + "/defaultparsekb.cogxml";
    }

    public static String getCoGuiLauncherPath() {
        return getDataFolder() + "/cogui-launcher.bat";
    }

    public static String getJwnlPropertiesPath() {
        return getDataFolder() + "/jwnl_properties.xml";
    }

    public static File getGoldCorpusFile() {
        return new File(dataFolder, "corpora/gold.xml");
    }

    public static void exit() {
        System.exit(1);
    }
}
