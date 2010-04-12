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
import org.purl.net.wonderland.nlp.CollocationManager;
import org.purl.net.wonderland.nlp.MorphologicalDatabase;
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
public final class Configuration {

    public static boolean testDebug = false;
    private static final String resFolderKey = "resFolder";
    private static final String lgParserFolderKey = "lgParserFolder";
    private static final String wnDataFolderKey = "wordNetDataFolder";
    private static final String vnDataFolderKey = "verbNetDataFolder";
    private static final String pbDataFolderKey = "propBankDataFolder";
    private static final String ilfWnFolderKey = "ilfWnFolder";
    //
    private static File resFolder = null;
    private static File lgParserFolder = null;
    private static File vnDataFolder = null;
    private static File pbDataFolder = null;
    private static File ilfWnFolder = null;

    private static boolean configure(Properties projProp) {
        resFolder = new File(projProp.getProperty(resFolderKey));
        lgParserFolder = new File(projProp.getProperty(lgParserFolderKey));
        setWnDataFolder(new File(projProp.getProperty(wnDataFolderKey)));
        vnDataFolder = new File(projProp.getProperty(vnDataFolderKey));
        pbDataFolder = new File(projProp.getProperty(pbDataFolderKey));
        ilfWnFolder = new File(projProp.getProperty(ilfWnFolderKey));
        return checkConfigure();
    }

    private static boolean configure() {
        resFolder = new File(System.getProperty("user.dir"), "res");
        lgParserFolder = new File(resFolder, "linkgrammar");
        setWnDataFolder(new File(resFolder, "WordNet-3.0/dict"));
        vnDataFolder = new File(resFolder, "verbnet/data");
        pbDataFolder = new File(resFolder, "propbank");
        ilfWnFolder = new File(resFolder, "ILFWN.v.0.2");
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
        if (!getIlfWnDataFolder().exists()) {
            System.err.println("Warning: could not find ILF-WN data folder");
        }
        return true;
    }

    static {
        boolean configured = false;

        try {
            String configFile = System.getProperty("wonderland.config");
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
            System.load(new File(Configuration.getResPath(), "cogui/cogitant.dll").getCanonicalPath());
            timer.stop();
        } catch (Exception ex) {
            handleException(ex);
        }
    }

    public static void init() {
        CollocationManager.init();
        MorphologicalDatabase.init();
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

    public static String getResPath() {
        return resFolder.getAbsolutePath();
    }

    public static File getStanfordParserFile() {
        return new File(getResPath(), "stanfordparser/englishPCFG.ser.gz");
    }

    public static File getCollocationsFolder() {
        return new File(getResPath(), "morphology");
    }

    public static File getDefaultParseKBFile() {
        return new File(getResPath(), "defaultparsekb.cogxml");
    }

    public static File getJwnlPropertiesFile() {
        return new File(getResPath(), "jwnl_properties.xml");
    }

    public static File getCorporaFolder() {
        return new File(resFolder, "corpora");
    }

    public static File getMorphologyFolder() {
        return new File(getResPath(), "morphology");
    }

    public static File getTestFolder() {
        return new File(getResPath(), "test");
    }

    public static File getLgParserPath() {
        return new File(lgParserFolder, "");
    }

    public static File getVerbNetDataFolder() {
        return new File(vnDataFolder, "");
    }

    public static File getPropBankDataFolder() {
        return new File(pbDataFolder, "");
    }

    public static File getVerbNetIndexFolder() {
        return new File(resFolder, "verbnet");
    }

    public static File getVerbNetFileIndexFile() {
        return new File(getVerbNetIndexFolder(), "file_index.csv");
    }

    public static File getVerbNetVerbIndexFile() {
        return new File(getVerbNetIndexFolder(), "verb_index.csv");
    }

    public static File getVerbProcsFolder() {
        return new File(getResPath(), "wsd/procs/verb");
    }

    public static File getIlfWnDataFolder() {
        return new File(ilfWnFolder, "");
    }
}
