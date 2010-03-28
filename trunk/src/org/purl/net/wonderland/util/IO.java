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
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian
 */
public class IO {

    private static class ProcWriter extends CogxmlWriter {

        public static void writeProcs(String set, WKnowledgeBase kb, File file) throws Exception {
            writeProcs(kb.getProcRules(set), kb, file);
        }

        private static void writeProcs(List<Rule> procs, WKnowledgeBase kb, File file) throws Exception {
            Document xmlDoc = XML.createDocument();
            Element root = xmlDoc.createElement("cogxml");
            xmlDoc.appendChild(root);

            Element vocabularyElement = createSupportElement(xmlDoc, "support", kb.getVocabulary(), false, kb.getLanguage(), false);
            root.appendChild(vocabularyElement);

            for (Rule proc : procs) {
                Element procElement = createRuleElement(xmlDoc, proc);
                root.appendChild(procElement);
            }

            XML.writeXmlFile(xmlDoc, file);
        }
    }

    private static class ProcReader extends CogxmlReader {

        public static void readProcs(WKnowledgeBase kb, File file) throws Exception {
            Vocabulary voc = kb.getVocabulary();
            Document xmlDoc = XML.readXmlFile(file);
            NodeList ruleNodes = xmlDoc.getElementsByTagName("rule");
            for (int i = 0; i < ruleNodes.getLength(); i++) {
                Element ruleElement = (Element) ruleNodes.item(i);
                Rule proc = readRule(ruleElement, voc);
                kb.addRule(proc);
            }
        }
    }

    public static String getFileContentAsString(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        StringBuffer sb = new StringBuffer();
        while ((str = in.readLine()) != null) {
            sb.append(str);
            sb.append("\n");
        }
        in.close();
        return sb.toString();
    }

    public static List<String> getFileContentAsStringList(File file) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(file));
        String str;
        List<String> lines = new ArrayList<String>();
        while ((str = in.readLine()) != null) {
            lines.add(str);
        }
        in.close();
        return lines;
    }

    public static void writeStringToFile(String str, File file) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(str);
        out.close();
    }

    public static String getClassPathRoot(Class<?> c) {
        String cpr = c.getCanonicalName();
        cpr = cpr.substring(0, cpr.lastIndexOf("."));
        return cpr;
    }

    public static void writeProcs(String set, WKnowledgeBase kb, File file) throws Exception {
        ProcWriter.writeProcs(set, kb, file);
    }

    public static void writeProcs(List<Rule> procs, WKnowledgeBase kb, File file) throws Exception {
        ProcWriter.writeProcs(procs, kb, file);
    }

    public static void readProcs(WKnowledgeBase kb, File file) throws Exception {
        ProcReader.readProcs(kb, file);
    }
}
