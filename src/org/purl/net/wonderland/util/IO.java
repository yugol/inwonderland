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
package org.purl.net.wonderland.util;

import fr.lirmm.rcr.cogui2.kernel.io.CogxmlReader;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.purl.net.wonderland.kb.Procs;
import org.purl.net.wonderland.kb.Wkb;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian
 */
public class IO {

    private static class ProcReader extends CogxmlReader {

        private static Procs readProcs(String name, File file, Wkb kb) throws Exception {
            Procs procs = new Procs(name);
            Vocabulary voc = kb.getVocabulary();
            Document xmlDoc = XML.readXmlFile(file);
            NodeList ruleNodes = xmlDoc.getElementsByTagName("rule");
            for (int i = 0; i < ruleNodes.getLength(); i++) {
                Element ruleElement = (Element) ruleNodes.item(i);
                Rule rule = readRule(ruleElement, voc);
                procs.add(rule);
            }
            return procs;
        }
    }

    public static String getClassPathRoot(Class<?> c) {
        String cpr = c.getCanonicalName();
        cpr = cpr.substring(0, cpr.lastIndexOf("."));
        return cpr;
    }

    public static String readFileAsString(File file) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }

    public static List<String> readFileAsStringList(File file) throws IOException {
        List<String> list = new ArrayList<String>();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            list.add(line);
        }
        in.close();
        return list;
    }

    public static Map<String, String> readCsvFileAsMapOneToOne(File file) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            String[] chunks = line.split(",");
            map.put(chunks[0].trim(), chunks[1].trim());
        }
        in.close();
        return map;
    }

    public static Map<String, Set<String>> readCsvFileAsMapOneToManySet(File file) throws IOException {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        BufferedReader in = new BufferedReader(new FileReader(file));
        String line;
        while ((line = in.readLine()) != null) {
            String[] chunks = line.split(",");
            Set<String> set = new HashSet<String>();
            for (int i = 1; i < chunks.length; i++) {
                set.add(chunks[i].trim());
            }
            map.put(chunks[0].trim(), set);
        }
        in.close();
        return map;
    }

    public static void writeStringToFile(String str, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(str);
        out.close();
    }

    public static void writeMapToCsvFile(Map<String, Collection<String>> map, File file) throws IOException {
        PrintWriter out = new PrintWriter(file);
        for (String key : map.keySet()) {
            out.print(key);
            for (String val : map.get(key)) {
                out.print(",");
                out.print(val);
            }
            out.println();
        }
        out.close();
    }

    public static Procs readProcs(String name, File file, Wkb kb) throws Exception {
        return ProcReader.readProcs(name, file, kb);
    }
}
