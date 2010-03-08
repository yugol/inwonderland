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
package org.purl.net.wonderland.nlp.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.resources.verbnet.VNUtil;
import org.purl.net.wonderland.nlp.resources.verbnet.VerbForm;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class VerbNetWrapper {

    private static List<String> fileList;
    private static Map<String, VerbForm> verbForms;

    public static List<String> getFileList() {
        return fileList;
    }

    static {
        try {
            WordNetWrapper.init();
            CodeTimer timer = new CodeTimer("VerbNetWrapper");
            File fileIndex = Globals.getVerbNetFileIndexFile();
            if (!fileIndex.exists()) {
                VNUtil.buildVerbNetFileList(fileIndex);
            }
            loadFileIndex();
            File verbIndex = Globals.getVerbNetVerbIndexFile();
            if (!verbIndex.exists()) {
                VNUtil.buildVerbNetVerbIndex(verbIndex);
            }
            loadVerbIndex();
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error initializing VerbNetWrapper");
            ex.printStackTrace(System.err);
            Globals.exit();

        }
    }

    public static VerbForm getVerbClasses(String lemma) {
        return verbForms.get(lemma);
    }

    public static void init() {
    }

    private static void loadFileIndex() throws FileNotFoundException, IOException {
        fileList = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(Globals.getVerbNetFileIndexFile()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0) {
                fileList.add(line);
            }
        }
        reader.close();
    }

    private static void loadVerbIndex() throws FileNotFoundException, IOException {
        verbForms = new Hashtable<String, VerbForm>();
        BufferedReader reader = new BufferedReader(new FileReader(Globals.getVerbNetVerbIndexFile()));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] chunks = line.split(",");
            VerbForm vf = verbForms.get(chunks[0]);
            if (vf == null) {
                vf = new VerbForm(chunks[0]);
                verbForms.put(chunks[0], vf);
            }
            vf.addVnClass(chunks[1]);
            if (chunks.length > 2) {
                vf.addWnSense(chunks[1], chunks[2]);
            }
        }
        reader.close();
    }
}