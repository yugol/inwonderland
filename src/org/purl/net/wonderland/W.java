/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.purl.net.wonderland;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.purl.net.wonderland.nlp.Gazetteers;
import org.purl.net.wonderland.nlp.resources.MorphAdornerWrapper;
import org.purl.net.wonderland.nlp.resources.StanfordParserWrapper;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.CodeTimer;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian
 */
public final class W {

    private static final String CONFIG_FILE_ATTR_NAME = "wonderland.config";
    private static final String RES_FOLDER_NAME = "res";
    private static final String MANUAL = "manual";
    private static final String AUTOMATIC = "automatic";
    private static final String DEFAULT_RES_WN_DATA_FOLDER_PATH = "WordNet-3.0/dict";
    private static final String DEFAULT_RES_VN_DATA_FOLDER_PATH = "verbnet/data";
    private static final String DEFAULT_RES_PB_DATA_FOLDER_PATH = "propbank";
    // relative paths
    private static final String RES_COGUI_FILE_PATH = "cogui/cogitant.dll";
    private static final String RES_TEST_FOLDER_PATH = "test";
    private static final String RES_JWNL_PROP_FILE_PATH = "jwnl_properties.xml";
    private static final String RES_WKB_FILE_PATH = "defaultparsekb.cogxml";
    private static final String RES_CORPORA_FOLDER_PATH = "corpora";
    private static final String RES_WSD_FOLDER_PATH = "wsd";
    private static final String RES_GAZETTEERS_FOLDER_PATH = "gazetteers";
    private static final String RES_VN_INDEX_FOLDER_PATH = RES_GAZETTEERS_FOLDER_PATH + "/verbnet";
    public static final String RES_VN_FILE_INDEX_FILE_PATH = RES_VN_INDEX_FOLDER_PATH + "/file_index.csv";
    public static final String RES_VN_VERB_INDEX_FILE_PATH = RES_VN_INDEX_FOLDER_PATH + "/verb_index.csv";
    public static final String RES_SP_FILE_PATH = "stanfordparser/englishPCFG.ser.gz";
    public static final String RES_MORPHOLOGY_FOLDER_PATH = RES_GAZETTEERS_FOLDER_PATH + "/morphology";
    public static final String RES_SENSES_MANUAL_FOLDER_PATH = RES_GAZETTEERS_FOLDER_PATH + "/senses/" + MANUAL;
    public static final String RES_SENSES_AUTOMATIC_FOLDER_PATH = RES_GAZETTEERS_FOLDER_PATH + "/senses/" + AUTOMATIC;
    public static final String RES_COLLOCATIONS_FOLDER_PATH = RES_GAZETTEERS_FOLDER_PATH + "/collocations";
    public static final String RES_WSD_VERB_MANUAL_FOLDER_PATH = RES_WSD_FOLDER_PATH + "/procs/" + MANUAL + "/verb";
    public static final String RES_WSD_VERB_AUTOMATIC_FOLDER_PATH = RES_WSD_FOLDER_PATH + "/procs/" + AUTOMATIC + "/verb";
    public static final String RES_BEDTIME_CORPUS = RES_CORPORA_FOLDER_PATH + "/bedtime/story.txt";
    //
    public static boolean testDebug = false;
    private static final String resFolderKey = "resFolder";
    private static final String wnDataFolderKey = "wordNetDataFolder";
    private static final String vnDataFolderKey = "verbNetDataFolder";
    private static final String pbDataFolderKey = "propBankDataFolder";
    //
    private static File resFolder = null;
    private static File vnDataFolder = null;
    private static File pbDataFolder = null;

    private static boolean configure(Properties projProp) {
        resFolder = new File(projProp.getProperty(resFolderKey));
        setWnDataFolder(new File(projProp.getProperty(wnDataFolderKey)));
        vnDataFolder = new File(projProp.getProperty(vnDataFolderKey));
        pbDataFolder = new File(projProp.getProperty(pbDataFolderKey));
        return checkConfigure();
    }

    private static boolean configure() {
        resFolder = new File(System.getProperty("user.dir"), RES_FOLDER_NAME);
        setWnDataFolder(new File(resFolder, DEFAULT_RES_WN_DATA_FOLDER_PATH));
        vnDataFolder = new File(resFolder, DEFAULT_RES_VN_DATA_FOLDER_PATH);
        pbDataFolder = new File(resFolder, DEFAULT_RES_PB_DATA_FOLDER_PATH);
        return checkConfigure();
    }

    private static boolean checkConfigure() {
        if (!resFolder.exists()) {
            System.err.println("Error: could not find Resource folder.");
            return false;
        }
        if (!getWordNetFolder().exists()) {
            System.err.println("Error: could not find WordNet data folder");
            return false;
        }
        if (!getVerbNetDataFolder().exists()) {
            System.err.println("Warning: could not find VerbNet data folder");
        }
        if (!getPropBankDataFolder().exists()) {
            System.err.println("Warning: could not find PropBank data folder");
        }
        return true;
    }

    static {
        boolean configured = false;

        try {
            String configFile = System.getProperty(CONFIG_FILE_ATTR_NAME);
            if (configFile != null) {
                File cfg = new File(configFile);
                if (cfg.exists()) {
                    Properties projProp = new Properties();
                    Reader reader = new FileReader(cfg);
                    projProp.load(reader);
                    reader.close();
                    configured = configure(projProp);
                }
            }
        } catch (IOException ex) {
            handleException(ex);
        }

        if (!configured) {
            configured = configure();
        }

        if (!configured) {
            System.err.println("Invalid configuration: exiting");
            exit();
        }

        try {
            CodeTimer timer = new CodeTimer("loading cogitatnt.dll");
            System.load(new File(resFolder, RES_COGUI_FILE_PATH).getCanonicalPath());
            timer.stop();
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public static void init() {
        // CollocationsManager.init();
        Gazetteers.init();
        WordNetWrapper.init();
        VerbNetWrapper.init();
        StanfordParserWrapper.init();
        MorphAdornerWrapper.init();
    }

    public static void reportExceptionConsole(Throwable t) {
        System.err.println(t.getMessage());
        t.printStackTrace(System.err);
    }

    public static void reportExceptionGui(Throwable t) {
        JOptionPane.showMessageDialog(null, t.toString(), t.getMessage(), JOptionPane.ERROR_MESSAGE);
    }

    public static void handleException(Throwable t) {
        reportExceptionConsole(t);
        reportExceptionGui(t);
        exit();
    }

    public static void exit() {
        System.exit(1);
    }

    public static File getWordNetFolder() {
        try {
            Document xmlDoc = XML.readXmlFile(getJwnlPropertiesFile());
            NodeList paramElements = xmlDoc.getElementsByTagName("param");
            for (int i = 0; i < paramElements.getLength(); i++) {
                Element paramElement = (Element) paramElements.item(i);
                String nameAttribute = paramElement.getAttribute("name");
                if (nameAttribute.equals("dictionary_path")) {
                    String path = paramElement.getAttribute("value");
                    return new File(path);
                }
            }
            throw new WonderlandException("Cannot get WordNet data folder");
        } catch (Exception ex) {
            handleException(ex);
        }
        return null;
    }

    private static void setWnDataFolder(File file) {
        try {
            Document xmlDoc = XML.readXmlFile(getJwnlPropertiesFile());
            NodeList paramElements = xmlDoc.getElementsByTagName("param");
            for (int i = 0; i < paramElements.getLength(); i++) {
                Element paramElement = (Element) paramElements.item(i);
                String nameAttribute = paramElement.getAttribute("name");
                if (nameAttribute.equals("dictionary_path")) {
                    String path = file.getAbsolutePath();
                    paramElement.setAttribute("value", path);
                    break;
                }
            }
            XML.writeXmlFile(xmlDoc, getJwnlPropertiesFile());
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public static File res(String relative) {
        return new File(resFolder, relative);
    }

    public static File res(String relative1, String relative2) {
        return new File(res(relative1), relative2);
    }

    public static File getVerbNetDataFolder() {
        return vnDataFolder;
    }

    public static File getPropBankDataFolder() {
        return pbDataFolder;
    }

    public static File getTestDataFolder() {
        return res(RES_TEST_FOLDER_PATH);
    }

    public static File getJwnlPropertiesFile() {
        return res(RES_JWNL_PROP_FILE_PATH);
    }

    public static File getDefaultWkbFile() {
        return new File(resFolder, RES_WKB_FILE_PATH);
    }
}
