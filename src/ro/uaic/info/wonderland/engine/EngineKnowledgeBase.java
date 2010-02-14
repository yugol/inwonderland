/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import ro.uaic.info.wonderland.analysis.MorphologicalAnalyser;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.TypedDependency;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlReader;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.analysis.PosProp;

/**
 *
 * @author Iulian
 */
public class EngineKnowledgeBase {

    static final String sentenceSetName = "sentences";
    static NumberFormat formatter = new DecimalFormat("000");
    static String posConceptType = ctLabel2ctId("Pos");

    static String toSentenceIndex(int num) {
        return formatter.format(num);
    }

    static String toSentenceId(int num) {
        return "_gs" + toSentenceIndex(num);
    }

    static String toSentenceId(String num) {
        return "_gs" + num;
    }

    public static String ctLabel2ctId(String ctl) {
        return "ct_" + ctl;
    }

    public static String rtLabel2rtId(String ctl) {
        return "rt_" + ctl;
    }
    String language = "en";
    KnowledgeBase kb;
    Vocabulary vocabulary;
    int sentenceFactCount = 0;
    File lastFile = null;
    boolean dirty = false;
    MorphologicalAnalyser ma = new MorphologicalAnalyser();

    public EngineKnowledgeBase() throws Exception {
        openKb(null);
    }

    public void openKb(File file) throws Exception {
        if (file == null) {
            file = new File(Globals.getDefaultParseKBPath());
        } else {
            lastFile = file;
        }
        loadKb(file);
    }

    void loadKb(File file) throws Exception {
        Document doc = CogxmlReader.read(file);
        NodeList supportList = doc.getElementsByTagName("support");
        if (supportList.getLength() == 0) {
            throw new Exception("vocabulary is not define in document");
        }
        Element support_elem = (Element) supportList.item(0);
        vocabulary = CogxmlReader.buildVocabulary(support_elem, true, language);
        Element rootElement = CogxmlReader.getRootElement(doc);
        kb = CogxmlReader.buildKB(rootElement, vocabulary, language, true);

        sentenceFactCount = 0;
        for (CGraph cg : kb.getFactGraphSet().values()) {
            if (cg.getSet().equals(sentenceSetName)) {
                sentenceFactCount++;
            }
        }

        dirty = false;
    }

    public void saveKb(File file) throws Exception {
        if (dirty || file != lastFile) {
            CogxmlWriter.write(file, kb, language);
            lastFile = file;
        }
        dirty = false;
    }

    public KnowledgeBase getKb() {
        return kb;
    }

    public File getLastFile() {
        return lastFile;
    }

    CGraph addSentenceFact(Sentence<TaggedWord> words, List<TypedDependency> deps) {
        String sentId = toSentenceIndex(++sentenceFactCount);
        CGraph cg = new CGraph(toSentenceId(sentId), sentId, sentenceSetName, "fact");

        for (int i = 0; i < words.size(); ++i) {
            TaggedWord word = words.get(i);
            String wordLabel = word.word();
            String wordTag = word.tag();
            String wordId = wordLabel + "-" + (i + 1);
            Concept c = new Concept(wordId);
            PosProp prop = null;

            if (wordTag.indexOf("NN") == 0) { // NN, NNP, NNS, NNPS
                prop = ma.analyzeNoun(wordLabel, wordTag);
            } else if (wordTag.equals("DT")) {
                prop = ma.analyzeDeterminer(wordLabel, wordTag);
            } else if (wordTag.indexOf("PRP") == 0) { // PRP, PRP$
                prop = ma.analyzePersonalPronoun(wordLabel, wordTag);
            } else if (wordTag.indexOf("JJ") == 0) { // JJ, JJS, JJC
                prop = ma.analyzeAdjective(wordLabel, wordTag);
            }

            if (prop != null && prop.type != null) {
                pos2concept(prop, c);
            } else {
                String conceptType = ctLabel2ctId(wordTag);
                vocabulary.addIndividual(wordLabel, wordLabel, posConceptType, language);
                c.setType(conceptType);
                c.setIndividual(wordLabel);
            }

            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = tdep.gov().nodeString();
            String dep = tdep.dep().nodeString();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = rtLabel2rtId(relationTypeLabel);
            String relationId = relationTypeLabel + "~" + (i + 1);

            if (Globals.testDebug) {
                System.out.println(relationType + "(" + gov + ", " + dep + ")");
            }

            Relation r = new Relation(relationId);
            r.addType(relationType);
            cg.addVertex(r);

            cg.addEdge(dep, relationId, 1);
            cg.addEdge(gov, relationId, 2);
        }

        kb.addGraph(cg);
        dirty = true;
        return cg;
    }

    private void pos2concept(PosProp a, Concept c) {
        vocabulary.addIndividual(a.lemma, a.lemma, posConceptType, language);
        c.setType(a.getTypes());
        c.setIndividual(a.lemma);
    }

    public CGraph getSentence(int idx) {
        return kb.getFactGraph(toSentenceId(idx));
    }

    public String getConceptTypeLabel(String ct) {
        return vocabulary.getConceptTypeLabel(ct, language);
    }
}
