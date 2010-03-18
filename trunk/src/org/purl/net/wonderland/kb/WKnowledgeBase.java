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
import java.util.Iterator;
import java.util.List;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.nlp.resources.VerbForm;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class WKnowledgeBase {

    private static final String storySetName = "story";
    private static final String allName = "all";
    private static final String allId = storySetName + "_" + allName;
    private final String language;
    private final KnowledgeBase kb;
    private final Vocabulary vocabulary;
    private int level1FactCount;
    private int level2FactCount;

    public int getLevel1FactCount() {
        return level1FactCount;
    }

    public void setLevel1FactCount(int sentenceCount) {
        this.level1FactCount = sentenceCount;
    }

    public int getLevel2FactCount() {
        return level2FactCount;
    }

    public void setLevel2FactCount(int level2FactCount) {
        this.level2FactCount = level2FactCount;
    }

    public int getFactCount() {
        return level1FactCount + level2FactCount;
    }

    public String getLanguage() {
        return language;
    }

    public WKnowledgeBase(File cogxml) throws Exception {
        this(cogxml, "en");
    }

    public WKnowledgeBase(File cogxml, String lang) throws Exception {
        language = lang;
        level1FactCount = 0;
        level2FactCount = 0;

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
            if (cg.getSet().equals(KbUtil.level1)) {
                ++level1FactCount;
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
            cg = new CGraph(allId, allName, KbUtil.level2, "fact");
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

    public CGraph getFactGraph(String toLevel1FactId) {
        return kb.getFactGraph(toLevel1FactId);
    }

    public CGraph buildFactGraph(List<WTagging> words, List<TypedDependency> deps) {
        CGraph cg = new CGraph(KbUtil.newUniqueId(), null, null, "fact");

        for (int i = 0; i < words.size(); ++i) {
            WTagging tagging = words.get(i);
            Concept c = new Concept(KbUtil.toConceptId(tagging, i + 1));
            String individualId = KbUtil.handleQuotes(tagging.getLemma());
            vocabulary.addIndividual(individualId, individualId, KbUtil.Top, language);
            String[] types = null;
            if (tagging.getPos() == null) {
                types = new String[]{KbUtil.toConceptTypeId(tagging.getPennTag())};
            } else {
                types = tagging.asTypes();
            }
            c.setType(types);
            c.setIndividual(individualId);
            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = getConcept(cg, KbUtil.getLabelIndex(tdep.gov().nodeString())).getId();
            String dep = getConcept(cg, KbUtil.getLabelIndex(tdep.dep().nodeString())).getId();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = KbUtil.toRelationTypeId(relationTypeLabel);
            String relationId = KbUtil.toRelationId(relationTypeLabel, (i + 1));

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
            if (idx == KbUtil.getConceptIndex(c.getId())) {
                return c;
            }
        }
        return null;
    }

    private String importWordNetHypernymHierarchy(Synset sense, POS posType, String particle, String parentId) {
        String senseName = KbUtil.toSenseName(particle, sense.getOffset());
        String senseId = KbUtil.toConceptTypeId(senseName);
        if (vocabulary.conceptTypeIdExist(senseId)) {
            return senseId;
        }

        Pointer[] ptrs = sense.getPointers(PointerType.HYPERNYM);
        if (ptrs.length > 0) {
            Synset hypernym = WordNetWrapper.lookup(ptrs[0].getTargetOffset(), posType);
            parentId = importWordNetHypernymHierarchy(hypernym, posType, particle, parentId);
        }

        String lemma = sense.getWord(0).getLemma().toLowerCase();
        // String label = "[" + lemma + "] " + KbUtil.handleQuotes(sense.getGloss());

        vocabulary.addConceptType(senseId, senseName, lemma, language);
        vocabulary.getConceptTypeHierarchy().addEdge(senseId, parentId);

        return senseId;
    }

    String[] importWordNetHypernymHierarchy(String word, POS posType) {
        String parentLabel = null;
        String parentId = null;
        String particle = null;
        if (posType == POS.NOUN) {
            parentLabel = "wnNn";
            particle = "n";
            parentId = KbUtil.toConceptTypeId(parentLabel);
        } else if (posType == POS.ADJECTIVE) {
            parentLabel = "wnJj";
            particle = "a";
            parentId = KbUtil.toConceptTypeId(parentLabel);
        } else if (posType == POS.ADVERB) {
            parentLabel = "wnRb";
            particle = "r";
            parentId = KbUtil.toConceptTypeId(parentLabel);
        } else if (posType == POS.VERB) {
            parentLabel = "wnVb";
            particle = "v";
            parentId = KbUtil.toConceptTypeId(parentLabel);
        } else {
            return null;
        }

        ArrayList<String> senseTypes = new ArrayList<String>(20);
        try {
            for (Synset sense : WordNetWrapper.getSenses(word, posType)) {
                String senseType = importWordNetHypernymHierarchy(sense, posType, particle, parentId);
                senseTypes.add(senseType);
            }
        } catch (RuntimeException ex) {
            System.err.println(ex);
            return null;
        }
        return senseTypes.toArray(new String[]{});
    }

    public List<Rule> getProcRules(String set) {
        List<Rule> rules = new ArrayList<Rule>();
        Iterator<CGraph> it = kb.getRuleSet().iteratorGraphs();
        while (it.hasNext()) {
            CGraph rule = it.next();
            if (rule.getName().indexOf(set) == 0) {
                rules.add((Rule) rule);
            }
        }
        return rules;
    }

    String[] importVerbNetHierarchy(String verb) {
        List<String> types = new ArrayList<String>();

        VerbForm vf = VerbNetWrapper.getVerbClasses(verb);
        if (vf == null) {
            return null;
        }

        for (String vc : vf.getVnClasses()) {
            String vcType = importVerbNetClassHierarchy(vc, KbUtil.toConceptTypeId("vnVb"));
            types.add(vcType);
            for (String vs : vf.getWnSenses(vc)) {
                Synset sense = WordNetWrapper.lookup(vs);
                String senseType = importWordNetHypernymHierarchy(sense, POS.VERB, "v", KbUtil.toConceptTypeId("wnVb"));
                types.add(senseType);
            }
        }

        return types.toArray(new String[]{});
    }

    private String importVerbNetClassHierarchy(String verbClassName, String parentId) {
        String verbClassId = KbUtil.toConceptTypeId(verbClassName);
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
        WTagging prop = new WTagging();
        Hierarchy cth = vocabulary.getConceptTypeHierarchy();
        String id = c.getId();
        for (String type : c.getType()) {
            try {
                if (cth.isKindOf(type, KbUtil.Pos)) {
                    if (wPosTagsOnly && cth.isKindOf(type, KbUtil.SpTag)) {
                        prop = new WTagging();
                        break;
                    } else {
                        prop.setPos(vocabulary.getConceptTypeLabel(type, language));
                    }
                } else if (cth.isKindOf(type, KbUtil.Case)) {
                    prop.setWcase(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, KbUtil.Comparison)) {
                    prop.setComp(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, KbUtil.Gender)) {
                    prop.setGender(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, KbUtil.Mood)) {
                    prop.setMood(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, KbUtil.Number)) {
                    prop.setNumber(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, KbUtil.Person)) {
                    prop.setPerson(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, KbUtil.Tense)) {
                    prop.setTense(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, KbUtil.Article)) {
                    prop.setArticle(vocabulary.getConceptTypeLabel(type, language));
                } else {
                    prop.addMoreType(vocabulary.getConceptTypeLabel(type, language));
                }
            } catch (RuntimeException ex) {
                System.err.println("At concept: " + type + " : " + id + " -> " + KbUtil.getConceptForm(id));
                throw ex;
            }
        }
        prop.setForm(KbUtil.getConceptForm(id));
        prop.setLemma(c.getIndividual());
        prop.setPennTag(KbUtil.getConceptPennTag(id));
        prop.setPartsOfSpeech(KbUtil.getConceptMaTag(id));
        return prop;
    }

    public void addFact(CGraph fact, String set) {
        if (set.equals(KbUtil.level1)) {
            int factCount = getLevel1FactCount() + 1;
            fact.setId(KbUtil.toLevel1FactId(factCount));
            fact.setName(KbUtil.toIdIndex(factCount));
            fact.setSet(set);
            kb.addGraph(fact);
            setLevel1FactCount(factCount);
        } else if (set.equals(KbUtil.level2)) {
            int factCount = getLevel2FactCount() + 1;
            fact.setId(KbUtil.toLevel2FactId(factCount));
            fact.setName(KbUtil.toIdIndex(factCount));
            fact.setSet(set);
            kb.addGraph(fact);
            setLevel2FactCount(factCount);
        }
    }

    public void addSenses(CGraph message) {
        Hierarchy cth = kb.getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> it = message.iteratorConcept();
        while (it.hasNext()) {
            Concept c = it.next();
            String lemma = c.getIndividual();
            String[] types = c.getType();
            String[] senses = null;

            if (cth.isKindOf(types, KbUtil.Vb)) {
            }

            /*
            if (cth.isKindOf(types, KbUtil.Nn)) {
            senses = importWordNetHypernymHierarchy(lemma, POS.NOUN);
            } else if (cth.isKindOf(types, KbUtil.Vb)) {
            senses = importVerbNetHierarchy(lemma);
            if (senses == null) {
            senses = importWordNetHypernymHierarchy(lemma, POS.VERB);
            }
            } else if (cth.isKindOf(types, KbUtil.Jj)) {
            senses = importWordNetHypernymHierarchy(lemma, POS.ADJECTIVE);
            } else if (cth.isKindOf(types, KbUtil.Rb)) {
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
            if (cth.isKindOf(type, KbUtil.Gender)) {
                attr.setGender(vocabulary.getConceptTypeLabel(type, language));
            } else if (cth.isKindOf(type, KbUtil.Number)) {
                attr.setNumber(vocabulary.getConceptTypeLabel(type, language));
            }
        }
        return attr;
    }
}
