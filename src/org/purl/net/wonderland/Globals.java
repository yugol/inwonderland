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
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public final class Globals {

    private static final String resFolderKey = "resFolder";
    private static final String lgParserFolderKey = "lgParserFolder";
    public static boolean testDebug = false;
    private static String resFolder = null;
    private static String lgParserFolder = null;

    private static void readParameters(Properties projProp) {
        resFolder = projProp.getProperty(resFolderKey);
        System.out.println(resFolder);
        lgParserFolder = projProp.getProperty(lgParserFolderKey);
    }

    static {
        Properties projProp = new Properties();
        try {
            File cfg = new File(System.getProperty("user.home"), "wonderland.properties");
            if (!cfg.exists()) {
                cfg.createNewFile();
            }

            Reader reader = new FileReader(cfg);
            projProp.load(reader);
            reader.close();
            readParameters(projProp);

            if (resFolder == null) {
                projProp.put(resFolderKey, new File(System.getProperty("user.dir"), "res").getCanonicalPath());
                projProp.put(lgParserFolderKey, "%see - MorphAdorner%");
                Writer writer = new FileWriter(cfg);
                projProp.store(writer, "");
                writer.close();
                readParameters(projProp);
            }

        } catch (IOException ex) {
            System.err.println("Could not load properties file.");
            System.err.println(ex);
            exit();
        }

        try {
            CodeTimer timer = new CodeTimer("loading cogitatnt.dll");
            System.load(new File(Globals.getResPath(), "cogitant.dll").getCanonicalPath());
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error loading cogitant.dll");
            System.err.println(ex);
            exit();
        }
    }

    public static String getResPath() {
        return resFolder;
    }

    public static File getStanfordParserFile() {
        return new File(getResPath(), "englishPCFG.ser.gz");
    }

    public static File getCollocationsFile() {
        return new File(getResPath(), "morphology/collocations.lst");
    }

    public static File getStanfordPostaggerFile() {
        // return new File(getResPath(), "bidirectional-distsim-wsj-0-18.tagger");
        return new File(getResPath(), "left3words-wsj-0-18.tagger");
    }

    public static File getDefaultParseKBFile() {
        return new File(getResPath(), "defaultparsekb.cogxml");
    }

    public static File getCoGuiLauncherFile() {
        return new File(getResPath(), "cogui-launcher.bat");
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

    public static String getLgParserPath() {
        return lgParserFolder;
    }

    public static void exit() {
        System.exit(1);
    }
}
