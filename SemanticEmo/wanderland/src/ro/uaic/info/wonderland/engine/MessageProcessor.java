/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.engine;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.trees.Tree;
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
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.parse.StanfordParser;

/**
 *
 * @author Iulian
 */
public class MessageProcessor {

    static NumberFormat formatter = new DecimalFormat("000");
    static final String sentenceSetName = "sentences";

    static String toSentenceId(int num) {
        return formatter.format(num);
    }
    String language = "en";
    KnowledgeBase kb = null;
    Map<String, String> conceptTypes = null;
    Map<String, String> relationTypes = null;
    StanfordParser sp = new StanfordParser();
    int sentenceFactCount = 0;
    File lastFile = null;
    boolean dirty = false;
    Personality personality = new EtoGleem();

    public MessageProcessor() throws Exception {
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
        Vocabulary vocabulary = CogxmlReader.buildVocabulary(support_elem, true, language);
        Element rootElement = CogxmlReader.getRootElement(doc);
        kb = CogxmlReader.buildKB(rootElement, vocabulary, language, true);

        cashConceptTypes();
        cashRelationTypes();

        sentenceFactCount = 0;
        for (CGraph cg : kb.getFactGraphSet().values()) {
            if (cg.getSet().equals(sentenceSetName)) {
                sentenceFactCount++;
            }
        }

        dirty = false;
        personality.setKb(kb);
    }

    void cashConceptTypes() {
        conceptTypes = new Hashtable<String, String>();
        Vocabulary voc = kb.getVocabulary();
        for (Iterator<String> i = voc.iteratorConceptTypes(); i.hasNext();) {
            String ct = i.next();
            String label = voc.getConceptTypeLabel(ct, language);
            conceptTypes.put(label, ct);
        }
    }

    void cashRelationTypes() {
        relationTypes = new Hashtable<String, String>();
        Vocabulary voc = kb.getVocabulary();
        for (Iterator<String> i = voc.iteratorRelationTypes(); i.hasNext();) {
            String rt = i.next();
            String label = voc.getRelationTypeLabel(rt, language);
            relationTypes.put(label, rt);
        }
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

    public String processMessage(String msg) {
        String resp = "Done.";

        for (List<? extends HasWord> sent : sp.getSentences(msg)) {
            Tree parse = sp.parse(sent);
            Sentence<TaggedWord> posTags = sp.getPOSTags(parse);
            List<TypedDependency> deps = sp.getDependencies(parse);
            addSentenceFact(posTags, deps);
        }

        dirty = true;
        return resp;
    }

    void addSentenceFact(Sentence<TaggedWord> words, List<TypedDependency> deps) {
        String sentId = toSentenceId(++sentenceFactCount);
        Vocabulary voc = kb.getVocabulary();
        String posConceptType = conceptTypes.get("POSType");

        CGraph cg = new CGraph("_gs" + sentId, sentId, sentenceSetName, "fact");

        for (int i = 0; i < words.size(); ++i) {
            TaggedWord word = words.get(i);

            String wordLabel = word.word();
            String wordId = wordLabel + "-" + (i + 1);
            String conceptTypeLabel = word.tag();
            String conceptType = conceptTypes.get(conceptTypeLabel);
            String individualId = wordLabel; // sentenceFactCount + "-" + wordId;

            if (Globals.testDebug) {
                System.out.println(conceptTypeLabel + ":" + individualId);
                if (!conceptTypes.containsKey(conceptTypeLabel)) {
                    throw new RuntimeException("unknown concept type " + conceptTypeLabel);
                }
            }

            voc.addIndividual(individualId, wordLabel, posConceptType, language);

            Concept c = new Concept(wordId);
            c.addType(conceptType);
            c.setIndividual(individualId);
            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = tdep.gov().nodeString();
            String dep = tdep.dep().nodeString();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = relationTypes.get(relationTypeLabel);
            String relationId = relationTypeLabel + "~" + (i + 1);

            if (Globals.testDebug) {
                System.out.println(relationTypeLabel + "(" + gov + ", " + dep + ")");
                if (!relationTypes.containsKey(relationTypeLabel)) {
                    throw new RuntimeException("unknown relation type " + relationTypeLabel);
                }
            }

            Relation r = new Relation(relationId);
            r.addType(relationType);
            cg.addVertex(r);

            cg.addEdge(dep, relationId, 1);
            cg.addEdge(gov, relationId, 2);
        }

        kb.addGraph(cg);
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
        this.personality.setKb(kb);
    }


}
