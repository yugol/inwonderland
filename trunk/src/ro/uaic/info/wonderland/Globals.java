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

    public static File getStanfordParserFile() {
        return new File(getDataFolder(), "englishPCFG.ser.gz");
    }

    public static File getStanfordPostaggerFile() {
        return new File(getDataFolder(), "bidirectional-distsim-wsj-0-18.tagger");
        // return getDataFolder() + "/left3words-wsj-0-18.tagger";
    }

    public static File getDefaultParseKBFile() {
        return new File(getDataFolder(), "defaultparsekb.cogxml");
    }

    public static File getCoGuiLauncherFile() {
        return new File(getDataFolder(), "cogui-launcher.bat");
    }

    public static File getJwnlPropertiesFile() {
        return new File(getDataFolder(), "jwnl_properties.xml");
    }

    public static File getCorporaFolder() {
        return new File(dataFolder, "corpora");
    }

    public static File getMorphologyFolder() {
        return new File(getDataFolder(), "morphology");
    }

    public static void exit() {
        System.exit(1);
    }
}
