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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.WonderlandRuntimeException;
import org.purl.net.wonderland.nlp.ParseResult;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.util.IdUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Wkb {

    private static final int levelCount = 3;
    private static final String storySetName = "story";
    private static final String allName = "all";
    private static final String allId = storySetName + "_" + allName;
    private final String language;
    private final KnowledgeBase kb;
    private final Vocabulary vocabulary;
    private int[] factCount = new int[levelCount];

    public int getFactCount() {
        int count = 0;
        for (int i = 0; i < factCount.length; i++) {
            count += factCount[i];
        }
        return count;
    }

    public int getFactCount(String level) {
        return factCount[WkbUtil.toLevelIndex(level)];
    }

    public void setFactCount(int value, String level) {
        factCount[WkbUtil.toLevelIndex(level)] = value;
    }

    public String getLanguage() {
        return language;
    }

    public Wkb(File cogxml) throws Exception {
        this(cogxml, "en");
    }

    public Wkb(File cogxml, String lang) throws Exception {
        language = lang;
        Arrays.fill(factCount, 0);

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
            String cgSet = cg.getSet();
            if (cgSet.equals(WkbConstants.LEVEL1)) {
                ++factCount[0];
            } else if (cgSet.equals(WkbConstants.LEVEL2)) {
                ++factCount[1];
            } else if (cgSet.equals(WkbConstants.LEVEL3)) {
                ++factCount[2];
            }
        }
    }

    /**
     * !!! Will not save edges adjacent to a conclusion relation
     * @param cogxml
     * @throws Exception
     */
    public void save(File cogxml) throws Exception {
        CogxmlWriter.write(cogxml, kb, language);
    }

    public CGraph getKnowledgeGraph() {
        CGraph cg = kb.getFactGraph(allId);
        if (cg == null) {
            cg = new CGraph(allId, allName, WkbConstants.LEVEL2, "fact");
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
            individualId = WkbUtil.handleQuotes(name);
            vocabulary.addIndividual(individualId, individualId, WkbConstants.TOP_CT, language);
        }
        return individualId;
    }

    public String addConceptType(String name, String parentId) {
        String ctId = WkbUtil.toConceptTypeId(name);
        vocabulary.addConceptType(ctId, name, null, language);
        if (parentId == null) {
            vocabulary.getConceptTypeHierarchy().addEdge(ctId, WkbConstants.TOP_CT);
        } else {
            vocabulary.getConceptTypeHierarchy().addEdge(ctId, parentId);
        }
        return ctId;
    }

    public CGraph buildFactGraph(ParseResult parseResult) {
        CGraph cg = new CGraph(IdUtil.newId(), null, null, "fact");

        for (int i = 0; i < parseResult.getTaggedSentence().size(); ++i) {
            WTagging tagging = parseResult.getTaggedWord(i);
            Concept c = new Concept(WkbUtil.toConceptId(tagging, i + 1));
            String individualId = addIndividual(tagging.getLemma());
            String[] types = null;
            if (tagging.getPartOfSpeech() == null) {
                types = new String[]{WkbUtil.toConceptTypeId(tagging.getPennTag())};
            } else {
                types = tagging.asTypes();
            }
            c.setType(types);
            c.setIndividual(individualId);
            cg.addVertex(c);
        }

        for (int i = 0; i < parseResult.getDependencies().size(); ++i) {
            TypedDependency tdep = parseResult.getDependency(i);

            String gov = getConcept(cg, parseResult.getIndex(tdep.gov())).getId();
            String dep = getConcept(cg, parseResult.getIndex(tdep.dep())).getId();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = WkbUtil.toRelationTypeId(relationTypeLabel);
            String relationId = IdUtil.newId();

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
            if (idx == WkbUtil.getConceptIndex(c.getId())) {
                return c;
            }
        }
        return null;
    }

    private String importWordNetHypernymHierarchy(Synset sense, POS posType, String particle, String parentId) {
        String senseName = WordNetWrapper.toWordNetOffsetKeyAlpha(particle, sense.getOffset());
        String senseId = WkbUtil.toConceptTypeId(senseName);
        if (vocabulary.conceptTypeIdExist(senseId)) {
            return senseId;
        }

        Pointer[] ptrs = sense.getPointers(PointerType.HYPERNYM);
        if (ptrs.length > 0) {
            Synset hypernym = WordNetWrapper.lookup(ptrs[0].getTargetOffset(), posType);
            parentId = importWordNetHypernymHierarchy(hypernym, posType, particle, parentId);
        }

        String lemma = sense.getWord(0).getLemma().toLowerCase();
        // String label = "[" + lemma + "] " + WkbConstants.handleQuotes(sense.getGloss());

        vocabulary.addConceptType(senseId, senseName, lemma, language);
        vocabulary.getConceptTypeHierarchy().addEdge(senseId, parentId);

        return senseId;
    }

    public List<String> importWordNetHypernymHierarchy(String word, POS posType) {
        String particle = WordNetWrapper.getAlphaPosParticle(posType);
        String parentId = WkbUtil.getPosParentId(posType);

        ArrayList<String> senseTypes = null;
        try {
            Synset[] senses = WordNetWrapper.getSenses(word, posType);
            if (senses != null) {
                senseTypes = new ArrayList<String>();
                for (Synset sense : senses) {
                    String senseType = importWordNetHypernymHierarchy(sense, posType, particle, parentId);
                    senseTypes.add(senseType);
                }
            }
        } catch (RuntimeException ex) {
            W.reportExceptionConsole(ex);
            senseTypes = null;
        }

        return senseTypes;
    }

    public void importWordNetHypernymHierarchy(List<String> senseTypes) {
        try {
            for (String senseType : senseTypes) {
                String senseKey = WkbUtil.toConceptType(senseType);
                Synset sense = WordNetWrapper.lookup(senseKey);
                POS posType = sense.getPOS();
                String particle = senseKey.substring(0, 1);
                String parentId = WkbUtil.getPosParentId(posType);
                String senseType2 = importWordNetHypernymHierarchy(sense, posType, particle, parentId);
                if (!senseType.equals(senseType2)) {
                    throw new WonderlandRuntimeException("input sense differs from output sense");
                }
            }
        } catch (RuntimeException ex) {
            W.reportExceptionConsole(ex);
        }
    }

    public ProcList getProcRules(String set) {
        ProcList procs = new ProcList(set);
        set = WkbUtil.toProcName(set, null);
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

    public WTagging conceptLabelsToWTagging(Concept c, boolean wPosTagsOnly) {
        WTagging wt = new WTagging();
        Hierarchy cth = vocabulary.getConceptTypeHierarchy();
        int idx = WkbUtil.getConceptIndex(c.getId());
        for (String type : c.getType()) {
            try {
                if (wPosTagsOnly && cth.isKindOf(type, WkbConstants.SPTAG_CT)) {
                    wt = new WTagging();
                    break;
                } else if (cth.isKindOf(type, WkbConstants.PARTOFSPEECH_CT)) {
                    String newPos = vocabulary.getConceptTypeLabel(type, language);
                    String pos = wt.getPartOfSpeech();
                    if (pos == null) {
                        wt.setPartOfSpeech(newPos);
                    } else {
                        wt.setPartOfSpeech(pos + WkbConstants.TYPE_SEPARATOR + newPos);
                    }
                } else if (cth.isKindOf(type, WkbConstants.GRAMMATICALCASE_CT)) {
                    wt.setGrammaticalCase(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.DEGREE_CT)) {
                    wt.setDegree(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.GRAMMATICALGENDER_CT)) {
                    wt.setGrammaticalGender(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.VERBFORMMOOD_CT)) {
                    wt.setVerbFormMood(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.GRAMMATICALNUMBER_CT)) {
                    wt.setGrammaticalNumber(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.PERSON_CT)) {
                    wt.setPerson(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.GRAMMATICALTENSE_CT)) {
                    wt.setGrammaticalTense(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.DEFINITNESS_CT)) {
                    wt.setDefiniteness(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, WkbConstants.ASPECT_CT)) {
                    wt.setAspect(vocabulary.getConceptTypeLabel(type, language));
                } else {
                    wt.addMoreType(vocabulary.getConceptTypeLabel(type, language));
                }
            } catch (RuntimeException ex) {
                System.err.println("At concept: " + type + " : " + idx + " -> " + wt.getLemma());
                throw ex;
            }
        }
        if (idx > 0) {
            wt.setIndex(idx + "");
        }
        wt.setLemma(c.getIndividual());
        return wt;
    }

    public void addFact(CGraph fact, String level) {
        int factNumber = getFactCount(level) + 1;
        fact.setId(WkbUtil.toFactId(factNumber, level));
        fact.setName(WkbUtil.toIdIndex(factNumber));
        fact.setSet(level);
        kb.addGraph(fact);
        setFactCount(factNumber, level);
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
            if (cth.isKindOf(type, WkbConstants.GRAMMATICALGENDER_CT)) {
                attr.setGender(vocabulary.getConceptTypeLabel(type, language));
            } else if (cth.isKindOf(type, WkbConstants.GRAMMATICALNUMBER_CT)) {
                attr.setNumber(vocabulary.getConceptTypeLabel(type, language));
            }
        }
        return attr;
    }

    public void addRule(Rule rule) {
        kb.addRule(rule);
    }

    public int gerRuleCount() {
        return kb.getRuleSet().values().size();
    }

    public int getConceptTypeCount() {
        return vocabulary.getConceptTypeCount();
    }

    public int getRelationTypeCount() {
        return vocabulary.getRelationTypeCount();
    }
}
