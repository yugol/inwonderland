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

import fr.lirmm.rcr.cogui2.kernel.io.CogxmlReader;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;
import org.purl.net.wonderland.nlp.resources.verbnet.VerbForm;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class WKnowledgeBase {

    private static final String allName = "all";
    private static final String allId = KbUtil.level2 + "_" + allName;
    private final String language;
    private final KnowledgeBase kb;
    private final Vocabulary vocabulary;
    private int level1FactCount;
    private int level2FactCount;

    public int getSentenceCount() {
        return level1FactCount;
    }

    public void setSentenceCount(int sentenceCount) {
        this.level1FactCount = sentenceCount;
    }

    public int getLevel2FactCount() {
        return level2FactCount;
    }

    public void setLevel2FactCount(int level2FactCount) {
        this.level2FactCount = level2FactCount;
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

    public String[] importWordNetHypernymHierarchy(String word, POS posType) {
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

    public List<Rule> getGeneratorRules(String set) {
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

    public String[] importVerbNetHierarchy(String verb) {
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
}
