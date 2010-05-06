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
package org.purl.net.wonderland.kb;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.util.CodeTimer;
import org.purl.net.wonderland.util.IO;
import org.purl.net.wonderland.util.IdUtil;
import org.purl.net.wonderland.util.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class WkbUtil {

    public static final int MAX_RELATION_ARG_COUNT = 10;
    //
    public static final String PROC = "proc_";
    public static final String PROC_SET_ARTICLES = "article";
    public static final String PROC_SET_MOODS = "mood";
    public static final String PROC_SET_COLLO = "collo";
    //
    private static final NumberFormat idLabelNumberFormatter = new DecimalFormat("00000");
    private static final String CONCEPT_TYPE_PREFIX = "ct_";
    private static final String RELATION_TYPE_PREFIX = "rt_";

    public static String toIdIndex(int num) {
        return idLabelNumberFormatter.format(num);
    }

    public static String toFactId(int num, String level) {
        return "l" + (toLevelIndex(level) + 1) + "_" + toIdIndex(num);
    }

    public static String toConceptTypeId(String ctl) {
        return CONCEPT_TYPE_PREFIX + handleQuotes(ctl);
    }

    public static String toConceptType(String senseType) {
        return senseType.substring(CONCEPT_TYPE_PREFIX.length());
    }

    public static String toRelationTypeId(String ctl) {
        return RELATION_TYPE_PREFIX + ctl;
    }

    public static String toProcName(String set, String name) {
        return PROC + set + "_" + ((name != null) ? (name) : (""));
    }

    public static String toConceptId(WTagging tagging, int index) {
        return IdUtil.newId() + "_" + index;
    }

    public static int getConceptIndex(String id) {
        if (id.length() > IdUtil.UUID_LENGTH) {
            return Integer.parseInt(id.substring(IdUtil.UUID_LENGTH + 1));
        }
        return -1;
    }

    public static String handleQuotes(String ctl) {
        if ("''".equals(ctl)) {
            ctl = "-CLQ-";
        } else if ("``".equals(ctl)) {
            ctl = "-OPQ-";
        }
        return ctl.replace("'", "`");
    }

    public static int retrieveIndexFromLabel(String label) {
        int beg = label.lastIndexOf("-") + 1;
        return Integer.parseInt(label.substring(beg));
    }

    public static void setAllConclusion(CGraph lhs, boolean b) {
        Iterator<Concept> cIt = lhs.iteratorConcept();
        while (cIt.hasNext()) {
            cIt.next().setConclusion(b);
        }
        Iterator<Relation> rIt = lhs.iteratorRelation();
        while (rIt.hasNext()) {
            rIt.next().setConclusion(b);
        }
    }

    public static CGraph duplicate(CGraph cg) {
        CGraph cg2 = new CGraph(IdUtil.newId(), cg.getName(), null, cg.getNature());

        Iterator<Concept> cIt = cg.iteratorConcept();
        while (cIt.hasNext()) {
            Concept c = cIt.next();
            Concept c2 = new Concept(c.getId());
            c2.setType(c.getType());
            c2.setIndividual(c.getIndividual());
            cg2.addVertex(c2);
        }

        Iterator<Relation> rIt = cg.iteratorRelation();
        while (rIt.hasNext()) {
            Relation r = rIt.next();
            Relation r2 = new Relation(r.getId());
            r2.setType(r.getType());
            cg2.addVertex(r2);
        }

        Iterator<CREdge> eIt = cg.iteratorEdge();
        while (eIt.hasNext()) {
            CREdge edge = eIt.next();
            Concept c = cg.getConcept(edge);
            Relation r = cg.getRelation(edge);
            cg2.addEdge(c.getId(), r.getId(), edge.getNumOrder());
        }

        return cg2;
    }

    private static void normalizeConceptTypes(File kbFile) throws FileNotFoundException, IOException {
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

            replaces.put(ct, toConceptTypeId(label));
            pos = kb.indexOf("<ctype", pos);
        }

        for (String key : replaces.keySet()) {
            String val = replaces.get(key);
            // System.out.println(key + " -> " + val);
            kb = kb.replace(key, val);
        }

        IO.writeStringToFile(kb, kbFile);
    }

    private static void normalizeRelationTypes(File kbFile) throws FileNotFoundException, IOException {
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

            replaces.put(ct, toRelationTypeId(label));
            pos = kb.indexOf("<rtype", pos);
        }

        for (String key : replaces.keySet()) {
            String val = replaces.get(key);
            // System.out.println(key + " -> " + val);
            kb = kb.replace(key, val);
        }

        IO.writeStringToFile(kb, kbFile);
    }

    private static void normalizeIndividuals(File kbFile) throws Exception {
        Document xmlDoc = XML.readXmlFile(kbFile);
        NodeList markers = xmlDoc.getElementsByTagName("marker");
        for (int i = 0; i < markers.getLength(); ++i) {
            Element marker = (Element) markers.item(i);
            String label = marker.getAttribute("label");
            String id = marker.getAttribute("id");
            replaceIndividualsIds(xmlDoc, id, label);
            marker.setAttribute("id", label);
            marker.setAttribute("idType", WkbConstants.TOP_CT);
        }
        XML.writeXmlFile(xmlDoc, kbFile);
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

    private static void normalizeRules(File kbFile) throws Exception {
        Document xmlDoc = XML.readXmlFile(kbFile);
        NodeList ruleNodes = xmlDoc.getElementsByTagName("rule");
        for (int i = 0; i < ruleNodes.getLength(); ++i) {
            Element ruleElement = (Element) ruleNodes.item(i);
            Element hyptElement = (Element) ruleElement.getElementsByTagName("hypt").item(0);
            Element graphElement = (Element) hyptElement.getElementsByTagName("graph").item(0);

            String label = graphElement.getAttribute("label");
            if (label.indexOf(PROC) == 0) {
                graphElement.setAttribute("set", label.split("_")[1]);
            }
        }
        XML.writeXmlFile(xmlDoc, kbFile);
    }

    public static void normalizeKbFile(File kbFile) throws Exception {
        CodeTimer timer = new CodeTimer("normalizing " + kbFile.getName());
        normalizeConceptTypes(kbFile);
        normalizeRelationTypes(kbFile);
        normalizeIndividuals(kbFile);
        normalizeRules(kbFile);
        timer.stop();
    }

    public static int toLevelIndex(String level) {
        if (WkbConstants.LEVEL1.equals(level)) {
            return 0;
        }
        if (WkbConstants.LEVEL2.equals(level)) {
            return 1;
        }
        if (WkbConstants.LEVEL3.equals(level)) {
            return 2;
        }
        return -1;
    }

    public static void joinSetType(Concept c, String[] set1, String[] set2) {
        String[] types = new String[set1.length + set2.length];
        System.arraycopy(set1, 0, types, 0, set1.length);
        System.arraycopy(set2, 0, types, set1.length, set2.length);
        c.setType(types);
    }

    public static String getPosParentId(POS posType) {
        String parentId;

        if (posType == POS.NOUN) {
            parentId = WkbUtil.toConceptTypeId(WkbConstants.WN_NOUN);
        } else if (posType == POS.ADJECTIVE) {
            parentId = WkbUtil.toConceptTypeId(WkbConstants.WN_ADJECTIVE);
        } else if (posType == POS.ADVERB) {
            parentId = WkbUtil.toConceptTypeId(WkbConstants.WN_ADVERB);
        } else if (posType == POS.VERB) {
            parentId = WkbUtil.toConceptTypeId(WkbConstants.WN_VERB);
        } else {
            return null;
        }

        return parentId;
    }

    public static void normalizeConcept(Concept c, Hierarchy cth) {
        // keep only UUID as id
        String id = c.getId();
        id = id.substring(0, IdUtil.UUID_LENGTH);
        c.setId(id);

        // remove redundant types (parent type if it has at least one child)
        String[] types = c.getType();
        for (int a = 0; a < types.length; a++) {
            String ta = types[a];
            for (int b = 0; b < types.length; b++) {
                String tb = types[b];
                if (tb != null && (a != b)) {
                    if (cth.isKindOf(tb, ta)) {
                        types[a] = null;
                        break;
                    }
                }
            }
        }
        c.setType(new String[0]);
        for (String type : types) {
            if (type != null) {
                c.addType(type);
            }
        }
    }
}
