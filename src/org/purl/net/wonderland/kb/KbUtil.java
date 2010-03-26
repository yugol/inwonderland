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
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import org.purl.net.wonderland.nlp.WTagging;
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
public final class KbUtil {

    // some concept types from the knowledge base support
    public static final String Top = toConceptTypeId("Top");
    public static final String Pos = toConceptTypeId("Pos");
    public static final String SpTag = toConceptTypeId("SpTag");
    public static final String Case = toConceptTypeId("Case");
    public static final String Comparison = toConceptTypeId("Comparison");
    public static final String Gender = toConceptTypeId("Gender");
    public static final String Mood = toConceptTypeId("Mood");
    public static final String Number = toConceptTypeId("Number");
    public static final String Person = toConceptTypeId("Person");
    public static final String Tense = toConceptTypeId("Tense");
    public static final String Article = toConceptTypeId("Article");
    // POS concept types
    public static final String Nn = toConceptTypeId("Nn");
    public static final String Vb = toConceptTypeId("Vb");
    public static final String Rb = toConceptTypeId("Rb");
    public static final String Pn = toConceptTypeId("Pn");
    public static final String Jj = toConceptTypeId("Jj");
    public static final String NnPRP = toConceptTypeId("NnPRP");
    public static final String JjPOS = toConceptTypeId("JjPOS");
    // fact levels
    public static final String level1 = "level1";
    public static final String level2 = "level2";
    // procedures
    public static final String proc = "proc_";
    public static final String procSetArticles = "article";
    public static final String procSetMoods = "mood";
    public static final String procSetCollocations = "collo";
    // other
    private static final NumberFormat idLabelNumberFormatter = new DecimalFormat("00000");
    private static final NumberFormat senseNumberFormatter = new DecimalFormat("00000000");

    public static String newUniqueId() {
        return UUID.randomUUID().toString();
    }

    public static String toIdIndex(int num) {
        return idLabelNumberFormatter.format(num);
    }

    public static String toLevel1FactId(int num) {
        return "l1_" + toIdIndex(num);
    }

    public static String toLevel2FactId(int num) {
        return "l2_" + toIdIndex(num);
    }

    public static String toConceptTypeId(String ctl) {
        return "ct_" + handleQuotes(ctl);
    }

    public static String toRelationTypeId(String ctl) {
        return "rt_" + ctl;
    }

    public static String toProcName(String set, String name) {
        return proc + set + "_" + ((name != null) ? (name) : (""));
    }

    public static String toConceptId(WTagging tagging, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(handleQuotes(tagging.getForm()));
        sb.append("#");
        sb.append(index);
        sb.append("=[");
        if (tagging.getPennTag() != null) {
            sb.append(handleQuotes(tagging.getPennTag()));
        }
        sb.append("]{");
        if (tagging.getPartsOfSpeech() != null) {
            sb.append(handleQuotes(tagging.getPartsOfSpeech()));
        }
        sb.append("}");
        return sb.toString();
    }

    public static String toRelationId(String label, int index) {
        return label + "~" + index;
    }

    public static String handleQuotes(String ctl) {
        if ("''".equals(ctl)) {
            ctl = "-CLQ-";
        } else if ("``".equals(ctl)) {
            ctl = "-OPQ-";
        }
        return ctl.replace("'", "`");
    }

    public static String getConceptForm(String id) {
        int end = id.lastIndexOf("#");
        if (end >= 0) {
            return id.substring(0, end);
        }
        return null;
    }

    public static int getConceptIndex(String id) {
        int beg = id.lastIndexOf('#') + 1;
        int end = id.lastIndexOf('=');
        int index = Integer.parseInt(id.substring(beg, end));
        return index;
    }

    public static String getConceptPennTag(String id) {
        int beg = id.lastIndexOf("[") + 1;
        int end = id.lastIndexOf("]");
        if (beg < end) {
            return id.substring(beg, end);
        } else {
            return null;
        }
    }

    public static String getConceptMaTag(String id) {
        int beg = id.lastIndexOf("{") + 1;
        int end = id.lastIndexOf("}");
        if (beg < end) {
            return id.substring(beg, end);
        } else {
            return null;
        }
    }

    public static int getLabelIndex(String label) {
        int beg = label.lastIndexOf("-") + 1;
        return Integer.parseInt(label.substring(beg));
    }

    public static String toSenseName(String particle, long offset) {
        return particle + senseNumberFormatter.format(offset);
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
        CGraph cg2 = new CGraph(newUniqueId(), cg.getName(), null, cg.getNature());

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

    private static void normalizeIndividuals(File kbFile) throws Exception {
        Document xmlDoc = XML.readXmlFile(kbFile);
        NodeList markers = xmlDoc.getElementsByTagName("marker");
        for (int i = 0; i < markers.getLength(); ++i) {
            Element marker = (Element) markers.item(i);
            String label = marker.getAttribute("label");
            String id = marker.getAttribute("id");
            replaceIndividualsIds(xmlDoc, id, label);
            marker.setAttribute("id", label);
            marker.setAttribute("idType", KbUtil.Top);
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
            if (label.indexOf(proc) == 0) {
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

    public static void applyProcedure(CGraph fact, Projection proj, Procedure proc, boolean markingConcepts, Hierarchy cth) {
        CGraph rhsFact = proc.getRhs();
        Set<Concept> delete = new HashSet<Concept>();
        Set<String> peers = new TreeSet<String>();
        Set<Concept> insert = new HashSet<Concept>();
        Map<String, String> update = new Hashtable<String, String>();

        // assume all concepts from LHS are to be deleted
        Iterator<Concept> cit = proc.getLhs().iteratorConcept();
        while (cit.hasNext()) {
            Concept lhs = cit.next();
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            if (actual == null || delete.contains(actual)) {
                return;
            }
            if (!cth.isKindOf(actual.getType(), lhs.getType())) {
                // projection only checks relation arguments
                return;
            }
            if (markingConcepts) {
                if (actual.isConclusion()) {
                    return;
                }
            }
            delete.add(actual);
        }

        // finally execute procedure
        // System.out.println("*" + proc.getId());

        // mark all actual lhs concepts
        if (markingConcepts) {
            for (Concept c : delete) {
                c.setConclusion(true);
            }
        }

        // correct delete, update, insert collections
        cit = rhsFact.iteratorConcept();
        while (cit.hasNext()) {
            Concept rhs = cit.next();
            Concept lhs = proc.getRhsLhsConceptMap().get(rhs);
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            if (actual != null) {
                update.put(rhs.getId(), actual.getId());
                delete.remove(actual);
                peers.add(actual.getId());
            } else {
                insert.add(rhs);
            }
        }

        // delete concepts
        for (Concept c : delete) {
            List<Relation> from = new ArrayList<Relation>();
            List<Relation> to = new ArrayList<Relation>();
            List<Concept> toConcepts = new ArrayList<Concept>();

            Iterator<CREdge> eit = fact.iteratorEdge(c.getId());
            while (eit.hasNext()) {
                CREdge edge = eit.next();
                Relation r = fact.getRelation(edge);
                int numOrder = edge.getNumOrder();
                if (numOrder == 1) {
                    to.add(r);
                    Iterator<CREdge> it = fact.iteratorEdge(r.getId());
                    while (it.hasNext()) {
                        CREdge e = it.next();
                        if (e.getNumOrder() != 1) {
                            toConcepts.add(fact.getConcept(e));
                        }
                    }
                } else {
                    from.add(r);
                }
            }

            fact.removeVertex(c.getId());
            for (Relation r : to) {
                fact.removeVertex(r.getId());
            }
            for (Relation r : from) {
                // assuming binary relations only
                Concept fromConcept = fact.getConcept(fact.iteratorAdjacents(r.getId()).next());
                String[] rType = r.getType();
                fact.removeVertex(r.getId());
                for (Concept toConcept : toConcepts) {
                    r = new Relation(KbUtil.newUniqueId());
                    r.setType(rType);
                    fact.addVertex(r);
                    fact.addEdge(fromConcept.getId(), r.getId(), 1);
                    fact.addEdge(toConcept.getId(), r.getId(), 2);
                }
            }
        }

        // delete relations between remaining LHS concepts (if there are links in RHS)
        if (rhsFact.iteratorRelation().hasNext()) {

            // identify relations to be deleted
            List<String> remove = new ArrayList<String>();
            for (String rhsId : update.keySet()) {
                String actualId = update.get(rhsId);
                peers.remove(actualId);
                Iterator<String> rIt = fact.iteratorAdjacents(actualId);
                while (rIt.hasNext()) {
                    String rId = rIt.next();
                    Iterator<String> cIt = fact.iteratorAdjacents(rId);
                    while (cIt.hasNext()) {
                        String cId = cIt.next();
                        if (peers.contains(cId)) {
                            remove.add(rId);
                            break;
                        }
                    }
                }
                peers.add(actualId);
            }

            // delete relations
            for (String rId : remove) {
                fact.removeVertex(rId);
            }
        }

        // update concepts
        for (String rhsId : update.keySet()) {
            Concept rhs = rhsFact.getConcept(rhsId);
            Concept actual = fact.getConcept(update.get(rhsId));

            String[] rhsTypes = rhs.getType();
            Arrays.sort(rhsTypes);
            int idxPos = Arrays.binarySearch(rhsTypes, KbUtil.Pos);
            if (idxPos < 0) {
                // no Pos - replace lhs types with rhs types
                actual.setType(rhsTypes);
            } else {
                if (rhsTypes.length > 1) {
                    // add all rhs types but Pos to lhs
                    String[] actualTypes = actual.getType();
                    String[] lhsTypes = new String[actualTypes.length + rhsTypes.length - 1];
                    System.arraycopy(actualTypes, 0, lhsTypes, 0, actualTypes.length);
                    int where = actualTypes.length;
                    for (int i = 0; i < rhsTypes.length; ++i) {
                        if (i != idxPos) {
                            lhsTypes[where] = rhsTypes[i];
                            ++where;
                        }
                    }
                    actual.setType(lhsTypes);
                }
            }

            if (!rhs.isGeneric()) {
                actual.setIndividual(rhs.getIndividual());
            }
        }

        // add new concepts
        for (Concept rhs : insert) {
            Concept actual = new Concept(KbUtil.newUniqueId());
            actual.setType(rhs.getType());
            actual.setIndividual(rhs.getIndividual());
            fact.addVertex(actual);
            update.put(rhs.getId(), actual.getId());
        }

        // add new relations
        Iterator<CREdge> eIt = rhsFact.iteratorEdge();
        while (eIt.hasNext()) {
            CREdge edge = eIt.next();

            Concept rhsConcept = rhsFact.getConcept(edge);
            Relation rhsRelation = rhsFact.getRelation(edge);

            String actualConceptId = update.get(rhsConcept.getId());
            String actualRelationId = update.get(rhsRelation.getId());
            if (actualRelationId == null) {
                Relation actualRelation = new Relation(KbUtil.newUniqueId());
                actualRelation.setType(rhsRelation.getType());
                fact.addVertex(actualRelation);
                actualRelationId = actualRelation.getId();
                update.put(rhsRelation.getId(), actualRelationId);
            }

            fact.addEdge(actualConceptId, actualRelationId, edge.getNumOrder());
        }
    }
}
