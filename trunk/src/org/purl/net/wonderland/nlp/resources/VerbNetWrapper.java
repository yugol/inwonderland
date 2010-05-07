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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.util.CodeTimer;
import org.purl.net.wonderland.util.IO;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public final class VerbNetWrapper {

    public static String normalizeName(String str) {
        return str.replaceAll("\\?", "");
    }

    public static String normalizeClassId(String id) {
        int pos = id.indexOf("-");
        return id.substring(pos + 1);
    }
    private static List<String> fileList;
    private static Map<String, Set<String>> verbIndex;

    public static void init() {
    }

    static {
        try {
            CodeTimer timer = new CodeTimer("VerbNetWrapper");

            // build file list
            fileList = new ArrayList<String>();
            for (File f : W.getVerbNetDataFolder().listFiles()) {
                if (f.isFile()) {
                    String name = f.getName();
                    if (name.lastIndexOf(".xml") == (name.length() - 4)) {
                        fileList.add(name);
                    }
                }
            }
            Collections.sort(fileList);

            // build verb index
            File file = W.res(W.RES_VN_VERB_LIST_FILE_PATH);
            if (!file.exists()) {
                buildVerbIndexFile(file);
            }
            verbIndex = IO.readCsvFileAsMapOneToManySet(file);


            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error initializing VerbNetWrapper");
            W.handleException(ex);
        }
    }

    public static List<String> getFileList() {
        return fileList;
    }

    public static Map<String, Set<String>> getVerbIndex() {
        return verbIndex;
    }

    private static void buildVerbIndexFile(File indexFile) throws Exception {
        Map<String, Collection<String>> verbs = new HashMap<String, Collection<String>>();

        for (String fileName : getFileList()) {
            File file = new File(W.getVerbNetDataFolder(), fileName);
            Document xmlDoc = XML.readXmlFile(file);

            NodeList memberNodes = xmlDoc.getElementsByTagName("MEMBER");
            for (int i = 0; i < memberNodes.getLength(); i++) {

                Element memberElement = (Element) memberNodes.item(i);
                String name = memberElement.getAttribute("name");
                name = VerbNetWrapper.normalizeName(name);

                Element vnclassElement = (Element) memberElement.getParentNode().getParentNode();
                String id = vnclassElement.getAttribute("ID");
                id = normalizeClassId(id);

                if (verbs.containsKey(name)) {
                    verbs.get(name).add(id);
                } else {
                    Set<String> ids = new HashSet<String>();
                    ids.add(id);
                    verbs.put(name, ids);
                }
            }
        }

        IO.writeMapToCsvFile(verbs, indexFile);
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
}
