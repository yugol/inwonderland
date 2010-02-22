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
    private static File dataFolder = new File("./res/");
    public static boolean useMorphAdornerTagsInWordForm = false;

    public static String getResFolder() {
        String dataPath = System.getProperty("wonderland.data.path");
        if (dataPath == null) {
            try {
                dataPath = dataFolder.getCanonicalPath();
            } catch (IOException ex) {
                System.err.println("Error reading the data folder.");
                System.err.println(ex);
                exit();
            }
        }
        return dataPath;
    }

    public static File getStanfordParserFile() {
        return new File(getResFolder(), "englishPCFG.ser.gz");
    }

    public static File getStanfordPostaggerFile() {
        // return new File(getResFolder(), "bidirectional-distsim-wsj-0-18.tagger");
        return new File(getResFolder(), "/left3words-wsj-0-18.tagger");
    }

    public static File getDefaultParseKBFile() {
        return new File(getResFolder(), "defaultparsekb.cogxml");
    }

    public static File getCoGuiLauncherFile() {
        return new File(getResFolder(), "cogui-launcher.bat");
    }

    public static File getJwnlPropertiesFile() {
        return new File(getResFolder(), "jwnl_properties.xml");
    }

    public static File getCorporaFolder() {
        return new File(dataFolder, "corpora");
    }

    public static File getMorphologyFolder() {
        return new File(getResFolder(), "morphology");
    }

    public static void exit() {
        System.exit(1);
    }
}
