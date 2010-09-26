/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland;

/**
 *
 * @author Iulian
 */
public final class Globals {

    public static boolean testDebug = true;
    public static final String ontologyPrefix = "http://www.semanticweb.org/ontologies/2010/0/Ontology1262693413691.owl";
    public static final String ontologyIdPrefix = ontologyPrefix + "#";
    static String wonderlandDataPath = "C:/Users/Iulian/Projects/wanderland/data/";
    private static String dataFolder = wonderlandDataPath;

    public static void setDataFolder(String path) {
        dataFolder = path;
    }

    public static String getDataFolder() {
        String dataPath = System.getProperty("wonderland.data.path");
        if (dataPath == null) {
            dataPath = dataFolder;
        }
        return dataPath;
        // return "C:/Users/Iulian/Projects/wanderland/data/";
    }

    public static String getAffectOntologyPath() {
        return getDataFolder() + "Affect_Ontology1262693413691.owl";
    }

    public static String getStanfordParserPath() {
        return getDataFolder() + "englishPCFG.ser.gz";
    }

    public static String getWordNetDictFolder() {
        // -Dwordnet.database.dir=C:/WordNet-3.0/dict/
        return System.getProperty("wordnet.database.dir");
    }

    public static String getDefaultParseKBPath() {
        return getDataFolder() + "defaultparsekb.cogxml";
    }

    public static void exit() {
        System.exit(1);
    }
}
