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
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper.VerbForm;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
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
        return factCount[WkbUtil_.toLevelIndex(level)];
    }

    public void setFactCount(int value, String level) {
        factCount[WkbUtil_.toLevelIndex(level)] = value;
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
            individualId = WkbUtil_.handleQuotes(name);
            vocabulary.addIndividual(individualId, individualId, WkbConstants.TOP_CT, language);
        }
        return individualId;
    }

    public String addConceptType(String name, String parentId) {
        String ctId = WkbUtil_.toConceptTypeId(name);
        vocabulary.addConceptType(ctId, name, null, language);
        if (parentId == null) {
            vocabulary.getConceptTypeHierarchy().addEdge(ctId, WkbConstants.TOP_CT);
        } else {
            vocabulary.getConceptTypeHierarchy().addEdge(ctId, parentId);
        }
        return ctId;
    }

    public CGraph buildFactGraph(List<WTagging> words, List<TypedDependency> deps) {
        CGraph cg = new CGraph(WkbUtil_.newUniqueId(), null, null, "fact");

        for (int i = 0; i < words.size(); ++i) {
            WTagging tagging = words.get(i);
            Concept c = new Concept(WkbUtil_.toConceptId(tagging, i + 1));
            String individualId = addIndividual(tagging.getLemma());
            String[] types = null;
            if (tagging.getPartOfSpeech() == null) {
                types = new String[]{WkbUtil_.toConceptTypeId(tagging.getPennTag())};
            } else {
                types = tagging.asTypes();
            }
            c.setType(types);
            c.setIndividual(individualId);
            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = getConcept(cg, WkbUtil_.retrieveIndexFromLabel(tdep.gov().nodeString())).getId();
            String dep = getConcept(cg, WkbUtil_.retrieveIndexFromLabel(tdep.dep().nodeString())).getId();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = WkbUtil_.toRelationTypeId(relationTypeLabel);
            String relationId = WkbUtil_.newUniqueId();

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
            if (idx == WkbUtil_.getConceptIndex(c.getId())) {
                return c;
            }
        }
        return null;
    }

    private String importWordNetHypernymHierarchy(Synset sense, POS posType, String particle, String parentId) {
        String senseName = WordNetWrapper.toWordNetOffsetKeyAlpha(particle, sense.getOffset());
        String senseId = WkbUtil_.toConceptTypeId(senseName);
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

    public String[] importWordNetHypernymHierarchy(String word, POS posType) {
        String parentLabel = null;
        String parentId = null;
        String particle = null;
        if (posType == POS.NOUN) {
            parentLabel = WkbConstants.WN_NOUN;
            parentId = WkbUtil_.toConceptTypeId(parentLabel);
        } else if (posType == POS.ADJECTIVE) {
            parentLabel = WkbConstants.WN_ADJECTIVE;
            parentId = WkbUtil_.toConceptTypeId(parentLabel);
        } else if (posType == POS.ADVERB) {
            parentLabel = WkbConstants.WN_ADVERB;
            parentId = WkbUtil_.toConceptTypeId(parentLabel);
        } else if (posType == POS.VERB) {
            parentLabel = WkbConstants.WN_VERB;
            parentId = WkbUtil_.toConceptTypeId(parentLabel);
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
        set = WkbUtil_.toProcName(set, null);
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
            String vcType = importVerbNetClassHierarchy(vc, WkbUtil_.toConceptTypeId(WkbConstants.VN_VERB));
            types.add(vcType);
            for (String vs : vf.getWnSenses(vc)) {
                Synset sense = WordNetWrapper.lookup(vs);
                String senseType = importWordNetHypernymHierarchy(sense, POS.VERB, "v", WkbConstants.VN_VERB_CT);
                types.add(senseType);
            }
        }

        return types.toArray(new String[]{});
    }

    private String importVerbNetClassHierarchy(String verbClassName, String parentId) {
        String verbClassId = WkbUtil_.toConceptTypeId(verbClassName);
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
        int idx = WkbUtil_.getConceptIndex(c.getId());
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
        fact.setId(WkbUtil_.toFactId(factNumber, level));
        fact.setName(WkbUtil_.toIdIndex(factNumber));
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
}