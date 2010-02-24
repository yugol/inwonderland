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
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Iulian
 */
public final class Globals {

    static {
        Properties projProp = new Properties();
        try {
            String[] paths = System.getProperty("java.class.path").split(";");
            for (String path : paths) {
                System.out.println(path);
            }
            // projProp.load(ClassLoader.getSystemResourceAsStream("wonderland.properties"));
            // projProp.list(System.out);
        } catch (Exception ex) {
            System.err.println("Could not load properties file.");
            System.err.println(ex);
            exit();
        }
    }
    public static boolean testDebug = false;
    private static File resFolder = new File("./res/");
    public static boolean useMorphAdornerTagsInWordForm = false;

    public static String getResFolder() {
        String dataPath = System.getProperty("wonderland.data.path");
        if (dataPath == null) {
            try {
                dataPath = resFolder.getCanonicalPath();
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
        return new File(resFolder, "corpora");
    }

    public static File getMorphologyFolder() {
        return new File(getResFolder(), "morphology");
    }

    public static File getTestFolder() {
        return new File(getResFolder(), "test");
    }

    public static void exit() {
        System.exit(1);
    }
}
