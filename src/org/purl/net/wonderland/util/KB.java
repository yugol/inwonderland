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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import org.purl.net.wonderland.kb.KbUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian
 */
public class KB {

    public static void normalizeConceptTypes(File kbFile) throws FileNotFoundException, IOException {
        String kb = IO.getFileContentAsString(kbFile);

        Map<String, String> replaces = new Hashtable<String, String>();
        int pos = kb.indexOf("<ctype");
        while (pos > 0) {
            int from = kb.indexOf("\"", pos) + 1;
            pos = kb.indexOf("\"", from);
            String ct = kb.substring(from, pos);

            from = kb.indexOf("\"", pos + 1) + 1;
            pos = kb.indexOf("\"", from);
            String label = kb.substring(from, pos);

            replaces.put(ct, KbUtil.toConceptTypeId(label));
            pos = kb.indexOf("<ctype", pos);
        }

        for (String key : replaces.keySet()) {
            String val = replaces.get(key);
            // System.out.println(key + " -> " + val);
            kb = kb.replace(key, val);
        }

        IO.writeStringToFile(kb, kbFile);
    }

    public static void normalizeRelationTypes(File kbFile) throws FileNotFoundException, IOException {
        String kb = IO.getFileContentAsString(kbFile);

        Map<String, String> replaces = new Hashtable<String, String>();
        int pos = kb.indexOf("<rtype");
        while (pos > 0) {
            int from = kb.indexOf("\"", pos) + 1;
            pos = kb.indexOf("\"", from);
            String ct = kb.substring(from, pos);

            pos = kb.indexOf("label", pos);
            from = kb.indexOf("\"", pos) + 1;
            pos = kb.indexOf("\"", from);
            String label = kb.substring(from, pos);

            replaces.put(ct, KbUtil.toRelationTypeId(label));
            pos = kb.indexOf("<rtype", pos);
        }

        for (String key : replaces.keySet()) {
            String val = replaces.get(key);
            // System.out.println(key + " -> " + val);
            kb = kb.replace(key, val);
        }

        IO.writeStringToFile(kb, kbFile);
    }

    public static void normalizeIndividuals(File kbFile) throws Exception {
        Document xmlDoc = IO.readXmlFile(kbFile);
        NodeList markers = xmlDoc.getElementsByTagName("marker");
        for (int i = 0; i < markers.getLength(); ++i) {
            Element marker = (Element) markers.item(i);
            String label = marker.getAttribute("label");
            String id = marker.getAttribute("id");
            replaceIndividualsIds(xmlDoc, id, label);
            marker.setAttribute("id", label);
            marker.setAttribute("idType", KbUtil.Top);
        }
        IO.writeXmlFile(xmlDoc, kbFile);
    }

    private static void replaceIndividualsIds(Document xmlDoc, String id, String label) {
        NodeList concepts = xmlDoc.getElementsByTagName("concept");
        for (int i = 0; i < concepts.getLength(); ++i) {
            Element concept = (Element) concepts.item(i);
            String idMarker = concept.getAttribute("idMarker");
            if (idMarker.equals(id)) {
                concept.setAttribute("idMarker", label);
            }
        }
    }
}
