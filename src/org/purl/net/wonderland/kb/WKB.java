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

import org.purl.net.wonderland.nlp.ar.Attr;
import edu.stanford.nlp.trees.TypedDependency;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlReader;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper.VerbForm;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.Formatting;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class WKB {

    private static final int levelCount = 3;
    private static final String storySetName = "story";
    private static final String allName = "all";
    private static final String allId = storySetName + "_" + allName;
    private final String language;
    private final KnowledgeBase kb;
    private final Vocabulary vocabulary;
    private int[] levelFactCount = new int[levelCount];

    public int getLevel1FactCount() {
        return levelFactCount[0];
    }

    public void setLevel1FactCount(int count) {
        this.levelFactCount[0] = count;
    }

    public int getLevel2FactCount() {
        return levelFactCount[1];
    }

    public void setLevel2FactCount(int count) {
        this.levelFactCount[1] = count;
    }

    private int getLevel3FactCount() {
        return levelFactCount[2];
    }

    public void setLevel3FactCount(int count) {
        this.levelFactCount[2] = count;
    }

    public int getFactCount() {
        int factCount = 0;
        for (int count : levelFactCount) {
            factCount += count;
        }
        return factCount;
    }

    public String getLanguage() {
        return language;
    }

    public WKB(File cogxml) throws Exception {
        this(cogxml, "en");
    }

    public WKB(File cogxml, String lang) throws Exception {
        language = lang;
        Arrays.fill(levelFactCount, 0);

        Document doc = CogxmlReader.read(cogxml);
        NodeList supportList = doc.getElementsByTagName("support");
        if (supportList.getLength() == 0) {
            throw new WonderlandException("vocabulary is not define in document");
        }
        Element support_elem = (Element) supportList.item(0);
        vocabulary = CogxmlReader.buildVocabulary(support_elem, true, language);
        Element rootElement = CogxmlReader.getRootElement(doc);
        kb = CogxmlReader.buildKB(rootElement, vocabulary, language, true);

        for (CGraph cg : kb.getFactGraphSet().values()) {
            if (cg.getSet().equals(WKBUtil.level1)) {
                ++levelFactCount[0];
            }
        }
    }

    /**
     * Will not save edges adjacent to a conclusion relation
     * @param cogxml
     * @throws Exception
     */
    public void save(File cogxml) throws Exception {
        CogxmlWriter.write(cogxml, kb, language);
    }

    public CGraph getKnowledgeGraph() {
        CGraph cg = kb.getFactGraph(allId);
        if (cg == null) {
            cg = new CGraph(allId, allName, WKBUtil.level2, "fact");
            kb.addGraph(cg);
        }
        return cg;
    }

    public Iterable<CGraph> getFactSetOrderedByNameIterator(String set) {
        return new CGIterable(kb, set);
    }

    public Vocabulary getVocabulary() {
        return kb.getVocabulary();
    }

    public void addGraph(CGraph cg) {
        kb.addGraph(cg);
    }

    public CGraph getFactGraph(String factId) {
        return kb.getFactGraph(factId);
    }

    public String addIndividual(String name) {
        String individualId = vocabulary.getIndividualId(name, language);
        if (individualId == null) {
            individualId = WKBUtil.handleQuotes(name);
            vocabulary.addIndividual(individualId, individualId, WKBUtil.TOP_CT, language);
        }
        return individualId;
    }

    public String addConceptType(String name, String parentId) {
        String ctId = WKBUtil.toConceptTypeId(name);
        vocabulary.addConceptType(ctId, name, null, language);
        if (parentId == null) {
            vocabulary.getConceptTypeHierarchy().addEdge(ctId, WKBUtil.TOP_CT);
        } else {
            vocabulary.getConceptTypeHierarchy().addEdge(ctId, parentId);
        }
        return ctId;
    }

    public CGraph buildFactGraph(List<WTagging> words, List<TypedDependency> deps) {
        CGraph cg = new CGraph(WKBUtil.newUniqueId(), null, null, "fact");

        for (int i = 0; i < words.size(); ++i) {
            WTagging tagging = words.get(i);
            Concept c = new Concept(WKBUtil.toConceptId(tagging, i + 1));
            String individualId = addIndividual(tagging.getLemma());
            String[] types = null;
            if (tagging.getPartOfSpeech() == null) {
                types = new String[]{WKBUtil.toConceptTypeId(tagging.getPennTag())};
            } else {
                types = tagging.asTypes();
            }
            c.setType(types);
            c.setIndividual(individualId);
            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = getConcept(cg, WKBUtil.getLabelIndex(tdep.gov().nodeString())).getId();
            String dep = getConcept(cg, WKBUtil.getLabelIndex(tdep.dep().nodeString())).getId();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = WKBUtil.toRelationTypeId(relationTypeLabel);
            String relationId = WKBUtil.toRelationId(relationTypeLabel, (i + 1));

            Relation r = new Relation(relationId);
            r.addType(relationType);
            cg.addVertex(r);

            cg.addEdge(dep, relationId, 1);
            cg.addEdge(gov, relationId, 2);
        }

        return cg;
    }

    private Concept getConcept(CGraph cg, int idx) {
        for (Concept c : cg.getConcepts()) {
            if (idx == WKBUtil.getConceptIndex(c.getId())) {
                return c;
            }
        }
        return null;
    }

    private String importWordNetHypernymHierarchy(Synset sense, POS posType, String particle, String parentId) {
        String senseName = WordNetWrapper.toWordNetOffsetKeyAlpha(particle, sense.getOffset());
        String senseId = WKBUtil.toConceptTypeId(senseName);
        if (vocabulary.conceptTypeIdExist(senseId)) {
            return senseId;
        }

        Pointer[] ptrs = sense.getPointers(PointerType.HYPERNYM);
        if (ptrs.length > 0) {
            Synset hypernym = WordNetWrapper.lookup(ptrs[0].getTargetOffset(), posType);
            parentId = importWordNetHypernymHierarchy(hypernym, posType, particle, parentId);
        }

        String lemma = sense.getWord(0).getLemma().toLowerCase();
        // String label = "[" + lemma + "] " + WKBUtil.handleQuotes(sense.getGloss());

        vocabulary.addConceptType(senseId, senseName, lemma, language);
        vocabulary.getConceptTypeHierarchy().addEdge(senseId, parentId);

        return senseId;
    }

    public String[] importWordNetHypernymHierarchy(String word, POS posType) {
        String parentLabel = null;
        String parentId = null;
        String particle = null;
        if (posType == POS.NOUN) {
            parentLabel = "wnNn";
            parentId = WKBUtil.toConceptTypeId(parentLabel);
        } else if (posType == POS.ADJECTIVE) {
            parentLabel = "wnJj";
            parentId = WKBUtil.toConceptTypeId(parentLabel);
        } else if (posType == POS.ADVERB) {
            parentLabel = "wnRb";
            parentId = WKBUtil.toConceptTypeId(parentLabel);
        } else if (posType == POS.VERB) {
            parentLabel = "wnVb";
            parentId = WKBUtil.toConceptTypeId(parentLabel);
        } else {
            return null;
        }

        particle = WordNetWrapper.getAlpha(posType);

        ArrayList<String> senseTypes = new ArrayList<String>(20);
        try {
            for (Synset sense : WordNetWrapper.getSenses(word, posType)) {
                String senseType = importWordNetHypernymHierarchy(sense, posType, particle, parentId);
                senseTypes.add(senseType);
            }
        } catch (RuntimeException ex) {
            Configuration.reportExceptionConsole(ex);
            return null;
        }
        return senseTypes.toArray(new String[]{});
    }

    public ProcList getProcRules(String set) {
        ProcList procs = new ProcList(set);
        set = WKBUtil.toProcName(set, null);
        Iterator<CGraph> it = kb.IteratorRules();
        while (it.hasNext()) {
            CGraph rule = it.next();
            if (rule.getName().indexOf(set) == 0) {
                procs.add((Rule) rule);
            }
        }
        return procs;
    }

    public void deleteRules() {
        List<CGraph> rules = new ArrayList<CGraph>();
        Iterator<CGraph> it = kb.IteratorRules();
        while (it.hasNext()) {
            rules.add(it.next());
        }
        for (CGraph rule : rules) {
            kb.delete(rule);
        }
    }

    String[] importVerbNetHierarchy(String verb) {
        List<String> types = new ArrayList<String>();

        VerbForm vf = VerbNetWrapper.getVerbClasses(verb);
        if (vf == null) {
            return null;
        }

        for (String vc : vf.getVnClasses()) {
            String vcType = importVerbNetClassHierarchy(vc, WKBUtil.toConceptTypeId("vnVb"));
            types.add(vcType);
            for (String vs : vf.getWnSenses(vc)) {
                Synset sense = WordNetWrapper.lookup(vs);
                String senseType = importWordNetHypernymHierarchy(sense, POS.VERB, "v", WKBUtil.toConceptTypeId("wnVb"));
                types.add(senseType);
            }
        }

        return types.toArray(new String[]{});
    }

    private String importVerbNetClassHierarchy(String verbClassName, String parentId) {
        String verbClassId = WKBUtil.toConceptTypeId(verbClassName);
        if (vocabulary.conceptTypeIdExist(verbClassId)) {
            return verbClassId;
        }

        String parentName = verbClassName.substring(0, verbClassName.lastIndexOf('-'));
        if (parentName.indexOf('-') >= 0) {
            parentId = importVerbNetClassHierarchy(parentName, parentId);
        }

        vocabulary.addConceptType(verbClassId, verbClassName, "", language);
        vocabulary.getConceptTypeHierarchy().addEdge(verbClassId, parentId);

        return verbClassId;
    }

    public WTagging conceptLabelsToWTagging(Concept c, boolean wPosTagsOnly) {
        WTagging wt = new WTagging();
        Hierarchy cth = vocabulary.getConceptTypeHierarchy();
        String idx = c.getId();
        for (String type : c.getType()) {
            try {
                if (wPosTagsOnly && cth.isKindOf(type, WKBUtil.SPTAG_CT)) {
                    wt = new WTagging();
                    break;
                } else if (cth.isKindOf(type, WKBUtil.PARTOFSPEECH_CT)) {
                    String newPos = vocabulary.getConceptTypeLabel(type, language);
                    String pos = wt.getPartOfSpeech();
                    if (pos == null) {
                        wt.setPartOfSpeech(newPos);
                    } else {
                        wt.setPartOfSpeech(pos + WKBUtil.TYPE_SEPARATOR + newPos);
                    }
                } else if (cth.isKindOf(type, WKBUtil.GRAMMATICALCASE_CT)) {
                    wt.setGrammaticalCase(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.DEGREE_CT)) {
                    wt.setDegree(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.GRAMMATICALGENDER_CT)) {
                    wt.setGrammaticalGender(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.VERBFORMMOOD_CT)) {
                    wt.setVerbFormMood(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.GRAMMATICALNUMBER_CT)) {
                    wt.setGrammaticalNumber(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.PERSON_CT)) {
                    wt.setPerson(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.GRAMMATICALTENSE_CT)) {
                    wt.setGrammaticalTense(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.DEFINITNESS_CT)) {
                    wt.setDefiniteness(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WKBUtil.ASPECT_CT)) {
                    wt.setAspect(vocabulary.getConceptTypeLabel(type, language));
                } else {
                    wt.addMoreType(vocabulary.getConceptTypeLabel(type, language));
                }
            } catch (RuntimeException ex) {
                System.err.println("At concept: " + type + " : " + idx + " -> " + wt.getLemma());
                throw ex;
            }
        }
        wt.setIndex(idx);
        wt.setLemma(c.getIndividual());
        wt.setWrittenForm(WKBUtil.getConceptWrittenForm(c.getId()));
        return wt;
    }

    public void addFact(CGraph fact, String set) {
        if (set.equals(WKBUtil.level1)) {
            int factCount = getLevel1FactCount() + 1;
            fact.setId(WKBUtil.toLevel1FactId(factCount));
            fact.setName(WKBUtil.toIdIndex(factCount));
            fact.setSet(set);
            kb.addGraph(fact);
            setLevel1FactCount(factCount);
        } else if (set.equals(WKBUtil.level2)) {
            int factCount = getLevel2FactCount() + 1;
            fact.setId(WKBUtil.toLevel2FactId(factCount));
            fact.setName(WKBUtil.toIdIndex(factCount));
            fact.setSet(set);
            kb.addGraph(fact);
            setLevel2FactCount(factCount);
        } else if (set.equals(WKBUtil.level3)) {
            int factCount = getLevel3FactCount() + 1;
            fact.setId(WKBUtil.toLevel3FactId(factCount));
            fact.setName(WKBUtil.toIdIndex(factCount));
            fact.setSet(set);
            kb.addGraph(fact);
            setLevel2FactCount(factCount);
        }
    }

    public void addSenses(CGraph fact) {
        Hierarchy cth = kb.getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> it = fact.iteratorConcept();
        while (it.hasNext()) {
            Concept c = it.next();
            String lemma = c.getIndividual();
            String[] types = c.getType();
            String[] senses = null;

            if (cth.isKindOf(types, WKBUtil.VERB_CT)) {
            }

            /*
            if (cth.isKindOf(types, WKBUtil.NOUN_CT)) {
            senses = importWordNetHypernymHierarchy(lemma, POS.NOUN);
            } else if (cth.isKindOf(types, WKBUtil.VERB_CT)) {
            senses = importVerbNetHierarchy(lemma);
            if (senses == null) {
            senses = importWordNetHypernymHierarchy(lemma, POS.VERB);
            }
            } else if (cth.isKindOf(types, WKBUtil.ADJECTIVE_CT)) {
            senses = importWordNetHypernymHierarchy(lemma, POS.ADJECTIVE);
            } else if (cth.isKindOf(types, WKBUtil.ADVERB_CT)) {
            senses = importWordNetHypernymHierarchy(lemma, POS.ADVERB);
            }
             */

            if (senses != null) {
                String[] allTypes = new String[types.length + senses.length];
                System.arraycopy(types, 0, allTypes, 0, types.length);
                System.arraycopy(senses, 0, allTypes, types.length, senses.length);
                c.setType(allTypes);
            }
        }
    }

    public CGraph getStory() {
        CGraph story = kb.getFactGraph(allId);
        if (story == null) {
            story = new CGraph(allId, allName, storySetName, "fact");
            kb.addGraph(story);
        }
        return story;
    }

    public Attr getAttr(Concept c) {
        Hierarchy cth = vocabulary.getConceptTypeHierarchy();
        Attr attr = new Attr();
        for (String type : c.getType()) {
            if (cth.isKindOf(type, WKBUtil.GRAMMATICALGENDER_CT)) {
                attr.setGender(vocabulary.getConceptTypeLabel(type, language));
            } else if (cth.isKindOf(type, WKBUtil.GRAMMATICALNUMBER_CT)) {
                attr.setNumber(vocabulary.getConceptTypeLabel(type, language));
            }
        }
        return attr;
    }

    public void addRule(Rule rule) {
        kb.addRule(rule);
    }
}
