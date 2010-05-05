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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.util.CodeTimer;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class VerbNetWrapper {

    public static class VerbForm {

        private final String lemma;
        private final Map<String, Set<String>> vnClasses = new Hashtable<String, Set<String>>();

        public VerbForm(String lemma) {
            this.lemma = lemma;
        }

        public String getLemma() {
            return lemma;
        }

        public Set<String> getVnClasses() {
            return vnClasses.keySet();
        }

        public void addVnClass(String vc) {
            if (!vnClasses.containsKey(vc)) {
                vnClasses.put(vc, new HashSet<String>());
            }
        }

        public void addWnSense(String vc, String wnSense) {
            addVnClass(vc);
            vnClasses.get(vc).add(wnSense);
        }

        public Set<String> getWnSenses(String vc) {
            return vnClasses.get(vc);
        }
    }

    public static class VerbNetClassFile {

        private final Document xmlDoc;

        public VerbNetClassFile(String verbClass) throws Exception {
            File classFile = new File(W.getVerbNetDataFolder(), verbClass);
            xmlDoc = XML.readXmlFile(classFile);
        }

        public List<VerbForm> getMembers() {
            List<VerbForm> members = new ArrayList<VerbForm>();
            NodeList memberNodes = xmlDoc.getElementsByTagName("MEMBER");
            for (int i = 0; i < memberNodes.getLength(); ++i) {
                Element member = (Element) memberNodes.item(i);
                VerbForm vf = new VerbForm(member.getAttribute("name").replaceAll("\\?", ""));
                Element verbClass = (Element) member.getParentNode().getParentNode();
                String vc = verbClass.getAttribute("ID");
                vf.addVnClass(vc);
                String[] wnSenses = member.getAttribute("wn").split(" ");
                for (String wnSense : wnSenses) {
                    if (wnSense.length() > 0) {
                        wnSense = wnSense.replaceAll("\\?", "");
                        vf.addWnSense(vc, WordNetWrapper.senseKeyToOffsetKeyAlpha(wnSense));
                    }
                }
                vf.addVnClass(vc);
                members.add(vf);
            }
            return members;
        }
    }
    private static List<String> fileList;
    private static Map<String, VerbForm> verbForms;

    public static List<String> getFileList() {
        return fileList;
    }

    static {
        try {
            CodeTimer timer = new CodeTimer("VerbNetWrapper");
            File fileIndex = W.res(W.RES_VN_FILE_INDEX_FILE_PATH);
            if (!fileIndex.exists()) {
                buildVerbNetFileList(fileIndex);
            }
            loadFileIndex();
            File verbIndex = W.res(W.RES_VN_VERB_INDEX_FILE_PATH);
            if (!verbIndex.exists()) {
                buildVerbNetVerbIndex(verbIndex);
            }
            loadVerbIndex();
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error initializing VerbNetWrapper");
            W.handleException(ex);
        }
    }

    public static VerbForm getVerbClasses(String lemma) {
        return verbForms.get(lemma);
    }

    public static void init() {
    }

    private static void loadFileIndex() throws FileNotFoundException, IOException {
        fileList = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(W.res(W.RES_VN_FILE_INDEX_FILE_PATH)));
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
        BufferedReader reader = new BufferedReader(new FileReader(W.res(W.RES_VN_VERB_INDEX_FILE_PATH)));
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

    public static File getClassFile(String vncls) {
        String[] vnclsParts = vncls.split("-");
        if (vnclsParts.length > 0) {
            vncls = vnclsParts[0];
            for (String fName : fileList) {
                String verbClass = fName.substring(fName.indexOf('-') + 1, fName.lastIndexOf('.'));
                if (verbClass.equals(vncls)) {
                    return new File(W.getVerbNetDataFolder(), fName);
                }
            }
        }
        return null;
    }

    public static List<String> getClassesLike(String vncls) {
        List<String> classes = new ArrayList<String>();
        for (String fName : fileList) {
            String verbClass = fName.substring(fName.indexOf('-') + 1, fName.lastIndexOf('.'));
            if (verbClass.indexOf(vncls) == 0 && (verbClass.length() == vncls.length() || verbClass.charAt(vncls.length()) == '.')) {
                classes.add(verbClass);
            }
        }
        if (classes.size() > 0) {
            return classes;
        }
        return null;
    }

    public static void buildVerbNetFileList(File indexFile) throws FileNotFoundException {
        int fileCount = 0;
        PrintWriter fout = new PrintWriter(indexFile);
        File dataFolder = W.getVerbNetDataFolder();
        for (File f : dataFolder.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();
                if (name.lastIndexOf(".xml") == (name.length() - 4)) {
                    fout.println(name);
                    ++fileCount;
                }
            }
        }
        fout.close();
        System.out.println("Found " + fileCount + " VerbNet verb class files.");
    }

    public static void buildVerbNetVerbIndex(File verbIndex) throws Exception {
        PrintWriter fout = new PrintWriter(verbIndex);
        Map<String, VerbForm> localVerbForms = new Hashtable<String, VerbForm>();
        for (String name : VerbNetWrapper.getFileList()) {
            VerbNetClassFile verbClass = new VerbNetClassFile(name);
            for (VerbForm member : verbClass.getMembers()) {
                VerbForm vf = localVerbForms.get(member.getLemma());
                if (vf == null) {
                    localVerbForms.put(member.getLemma(), member);
                } else {
                    String vc = member.getVnClasses().iterator().next();
                    for (String vs : member.getWnSenses(vc)) {
                        vf.addWnSense(vc, vs);
                    }
                }
            }
        }
        for (VerbForm vf : localVerbForms.values()) {
            for (String vc : vf.getVnClasses()) {
                Set<String> senses = vf.getWnSenses(vc);
                if (senses.size() > 0) {
                    for (String vs : senses) {
                        fout.println(vf.getLemma() + "," + vc + "," + vs);
                    }
                } else {
                    fout.println(vf.getLemma() + "," + vc + ",");
                    System.err.println(vf.getLemma());
                }
            }
        }
        fout.close();
    }
}
