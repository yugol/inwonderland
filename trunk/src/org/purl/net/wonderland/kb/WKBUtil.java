/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
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
public final class WKBUtil {

    public static final String TYPE_SEPARATOR = ";";
    public static final String ID_SEPARATOR = " ";
    // morphology types
    public static final String PARTOFSPEECH = "partOfSpeech";
    public static final String GRAMMATICALGENDER = "grammaticalGender";
    public static final String GRAMMATICALNUMBER = "grammaticalNumber";
    public static final String GRAMMATICALCASE = "grammaticalCase";
    public static final String GRAMMATICALTENSE = "grammaticalTense";
    public static final String PERSON = "person";
    public static final String DEGREE = "degree";
    public static final String VERBFORMMOOD = "verbFormMood";
    public static final String DEFINITNESS = "definiteness";
    public static final String ASPECT = "aspect";
    // morphology (POS)
    public static final String ADVERB = "adverb";
    public static final String NOUN = "noun";
    public static final String VERB = "verb";
    public static final String ADJECTIVE = "adjective";
    public static final String PROPERNOUN = "properNoun";
    public static final String POSSESIVEADJECIVE = "possessiveAdjective";
    public static final String PRONOUN = "pronoun";
    public static final String DEMONSTRATIVEDETERMINER = "demonstrativeDeterminer";
    public static final String DEMONSTRATICEPRONOUN = "demonstrativePronoun";
    public static final String CARDINALNUMBER = "cardinalNumeral";
    public static final String GERUND = "gerund";
    public static final String INDICATIVE = "indicative";
    public static final String PAST = "past";
    public static final String PRESENT = "present";
    public static final String SINGULAR = "singular";
    public static final String THIRDPERSON = "thirdPerson";
    public static final String COMMONNOUN = "commonNoun";
    public static final String PLURAL = "plural";
    public static final String MODAL = "modal";
    public static final String INFINITIVE = "infinitive";
    public static final String PARTICIPLE = "participle";
    public static final String SUBORDONATINGCONJUNCTION = "subordinatingConjunction";
    public static final String ADPOSITION = "adposition";
    public static final String COORDINATINGCONJUNCTION = "coordinatingConjunction";
    public static final String COMPARATIVE = "comparative";
    public static final String SUPERLATIVE = "superlative";
    public static final String MANNERADVERB = "mannerAdverb";
    public static final String ORDINALADJECTIVE = "ordinalAdjective";
    public static final String RELATIVEPRONOUN = "relativePronoun";
    public static final String RELATIVEADVERB = "relativeAdverb";
    public static final String POSSESIVEPRONOUN = "possessivePronoun";
    public static final String INTERROGATIVEADVERB = "interrogativeAdverb";
    public static final String EXISTENTIALPRONOUN = "existentialPronoun";
    public static final String GENERALADVERB = "generalAdverb";
    public static final String POSSESIVEPARTICLE = "possessiveParticle";
    public static final String INTERJECTION = "interjection";
    // punctuation
    public static final String POINT = "point";
    public static final String QUOTE = "quote";
    public static final String QUESTIONMARK = "questionMark";
    public static final String COMMA = "comma";
    public static final String EXCLAMATIVEPOINT = "exclamativePoint";
    public static final String OPENPARRENTHESIS = "openParenthesis";
    public static final String CLOSEPARENTHESIS = "closeParenthesis";
    public static final String SEMICOLON = "semiColon";
    public static final String COLON = "colon";
    // some concept types from the knowledge base support
    public static final String TOP_CT = toConceptTypeId("Top");
    public static final String PROCOP_CT = toConceptTypeId("ProcOp");
    public static final String PROCOP_KEEP_CT = toConceptTypeId("ProcOp_Keep");
    public static final String PROCOP_ADD_CT = toConceptTypeId("ProcOp_Add");
    public static final String PROCOP_REPLACE_CT = toConceptTypeId("ProcOp_Replace");
    public static final String LINKARG_CT = toConceptTypeId("LinkArg");
    public static final String SPTAG_CT = toConceptTypeId("SpTag");
    // morphology concept types
    public static final String PARTOFSPEECH_CT = toConceptTypeId(PARTOFSPEECH);
    public static final String GRAMMATICALCASE_CT = toConceptTypeId(GRAMMATICALCASE);
    public static final String DEGREE_CT = toConceptTypeId(DEGREE);
    public static final String GRAMMATICALGENDER_CT = toConceptTypeId(GRAMMATICALGENDER);
    public static final String VERBFORMMOOD_CT = toConceptTypeId(VERBFORMMOOD);
    public static final String GRAMMATICALNUMBER_CT = toConceptTypeId(GRAMMATICALNUMBER);
    public static final String PERSON_CT = toConceptTypeId(PERSON);
    public static final String ASPECT_CT = toConceptTypeId(ASPECT);
    public static final String GRAMMATICALTENSE_CT = toConceptTypeId(GRAMMATICALTENSE);
    public static final String DEFINITNESS_CT = toConceptTypeId(DEFINITNESS);
    public static final String NOUN_CT = toConceptTypeId(NOUN);
    public static final String VERB_CT = toConceptTypeId(VERB);
    public static final String ADVERB_CT = toConceptTypeId(ADVERB);
    public static final String PRONOUN_CT = toConceptTypeId(PRONOUN);
    public static final String ADJECTIVE_CT = toConceptTypeId(ADJECTIVE);
    public static final String PROPERNOUN_CT = toConceptTypeId(PROPERNOUN);
    public static final String POSSESSIVEADVERB_CT = toConceptTypeId(POSSESIVEADJECIVE);
    public static final String ADPOSITION_CT = toConceptTypeId(ADPOSITION);
    public static final String INDICATIVE_CT = toConceptTypeId(INDICATIVE);
    // fact levels
    public static final String level1 = "level1";
    public static final String level2 = "level2";
    public static final String level3 = "level3";
    // procedures
    public static final String proc = "proc_";
    public static final String procSetArticles = "article";
    public static final String procSetMoods = "mood";
    public static final String procSetCollocations = "collo";
    // other
    private static final NumberFormat idLabelNumberFormatter = new DecimalFormat("00000");

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

    public static String toLevel3FactId(int num) {
        return "l3_" + toIdIndex(num);
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
        return index + "-" + handleQuotes(tagging.getWrittenForm());
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

    public static int getConceptIndex(String id) {
        String idStr = id.split("-")[0];
        return Integer.parseInt(idStr);
    }

    public static String getConceptWrittenForm(String id) {
        String writtenForm = id.split("-")[1];
        return writtenForm;
    }

    public static int getLabelIndex(String label) {
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

            replaces.put(ct, WKBUtil.toConceptTypeId(label));
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

            replaces.put(ct, WKBUtil.toRelationTypeId(label));
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
            marker.setAttribute("idType", WKBUtil.TOP_CT);
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

    public static void applyProcMatch(CGraph fact, Projection proj, Proc proc, boolean markingConcepts, Hierarchy cth) {
        CGraph rhsFact = proc.getRhs();
        Set<Concept> conceptsToDelete = new HashSet<Concept>();
        Set<String> peers = new TreeSet<String>();
        Set<Concept> conceptsToInsert = new HashSet<Concept>();
        Map<String, String> conceptsToUpdate = new Hashtable<String, String>();

        // assume all concepts from LHS are to be deleted
        Iterator<Concept> cit = proc.getLhs().iteratorConcept();
        while (cit.hasNext()) {
            Concept lhs = cit.next();
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            if (actual == null || conceptsToDelete.contains(actual)) {
                return;
            }
            if (!cth.isKindOf(actual.getType(), lhs.getType())) {
                // projection only checks relation arguments
                return;
            }
            if (markingConcepts) {
                if (actual.isConclusion()) {
                    // the concept has already been processed in a previous rule
                    return;
                }
            }
            conceptsToDelete.add(actual);
        }

        // mark all actual lhs concepts
        if (markingConcepts) {
            for (Concept c : conceptsToDelete) {
                c.setConclusion(true);
            }
        }

        // build delete, update, insert collections
        cit = rhsFact.iteratorConcept();
        while (cit.hasNext()) {
            Concept rhs = cit.next();
            Concept lhs = proc.getRhsLhsConceptMap().get(rhs);
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            if (actual != null) {
                conceptsToUpdate.put(rhs.getId(), actual.getId());
                conceptsToDelete.remove(actual);
                peers.add(actual.getId());
            } else {
                conceptsToInsert.add(rhs);
            }
        }

        // delete concepts
        for (Concept c : conceptsToDelete) {
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
                    r = new Relation(WKBUtil.newUniqueId());
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
            List<String> relationsToDelete = new ArrayList<String>();
            for (String rhsId : conceptsToUpdate.keySet()) {
                String actualId = conceptsToUpdate.get(rhsId);
                peers.remove(actualId);
                Iterator<String> rIt = fact.iteratorAdjacents(actualId);
                while (rIt.hasNext()) {
                    String rId = rIt.next();
                    Iterator<String> cIt = fact.iteratorAdjacents(rId);
                    while (cIt.hasNext()) {
                        String cId = cIt.next();
                        if (peers.contains(cId)) {
                            relationsToDelete.add(rId);
                            break;
                        }
                    }
                }
                peers.add(actualId);
            }

            // delete relations
            for (String rId : relationsToDelete) {
                fact.removeVertex(rId);
            }
        }

        // update concepts
        for (String rhsId : conceptsToUpdate.keySet()) {
            // update types
            Concept rhs = rhsFact.getConcept(rhsId);
            Concept actual = fact.getConcept(conceptsToUpdate.get(rhsId));

            String[] rhsTypes = rhs.getType();
            Arrays.sort(rhsTypes);

            int idxPos = Arrays.binarySearch(rhsTypes, PROCOP_KEEP_CT);
            if (idxPos >= 0) {
                // keep original types
            }
            idxPos = Arrays.binarySearch(rhsTypes, PROCOP_REPLACE_CT);
            if (idxPos >= 0) {
                actual.setType(getNoOpTypes(rhsTypes));
            }
            idxPos = Arrays.binarySearch(rhsTypes, PROCOP_ADD_CT);
            if (idxPos >= 0) {
                String[] actualTypes = actual.getType();
                rhsTypes = getNoOpTypes(rhsTypes);
                String[] lhsTypes = new String[actualTypes.length + rhsTypes.length];
                System.arraycopy(actualTypes, 0, lhsTypes, 0, actualTypes.length);
                System.arraycopy(rhsTypes, 0, lhsTypes, actualTypes.length, rhsTypes.length);
                actual.setType(lhsTypes);
            }

            // update individual
            if (!rhs.isGeneric()) {
                actual.setIndividual(rhs.getIndividual());
            }
        }

        // add new concepts
        for (Concept rhs : conceptsToInsert) {
            Concept actual = new Concept(WKBUtil.newUniqueId());
            actual.setType(rhs.getType());
            actual.setIndividual(rhs.getIndividual());
            fact.addVertex(actual);
            conceptsToUpdate.put(rhs.getId(), actual.getId());
        }

        // add new relations
        Iterator<CREdge> eIt = rhsFact.iteratorEdge();
        while (eIt.hasNext()) {
            CREdge edge = eIt.next();

            Concept rhsConcept = rhsFact.getConcept(edge);
            Relation rhsRelation = rhsFact.getRelation(edge);

            String actualConceptId = conceptsToUpdate.get(rhsConcept.getId());
            String actualRelationId = conceptsToUpdate.get(rhsRelation.getId());
            if (actualRelationId == null) {
                Relation actualRelation = new Relation(WKBUtil.newUniqueId());
                actualRelation.setType(rhsRelation.getType());
                fact.addVertex(actualRelation);
                actualRelationId = actualRelation.getId();
                conceptsToUpdate.put(rhsRelation.getId(), actualRelationId);
            }

            fact.addEdge(actualConceptId, actualRelationId, edge.getNumOrder());
        }
    }

    private static String[] getNoOpTypes(String[] types) {
        List<String> noOpTypes = new ArrayList<String>();
        for (String type : types) {
            if (type.indexOf(PROCOP_CT) != 0) {
                noOpTypes.add(type);
            }
        }
        return noOpTypes.toArray(new String[noOpTypes.size()]);
    }
}
