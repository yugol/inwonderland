/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.kb;

import edu.stanford.nlp.trees.TypedDependency;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlReader;
import fr.lirmm.rcr.cogui2.kernel.io.CogxmlWriter;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ro.uaic.info.wonderland.Globals;
import ro.uaic.info.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public class EngineKnowledgeBase {

    static final String sentenceSetName = "sentences";
    static NumberFormat formatter = new DecimalFormat("000");
    static String posConceptType = ctLabel2ctId("Pos");
    static String spTagConceptType = ctLabel2ctId("SpTag");
    static String caseConceptType = ctLabel2ctId("Case");
    static String comparisonConceptType = ctLabel2ctId("Comparison");
    static String genderConceptType = ctLabel2ctId("Gender");
    static String moodConceptType = ctLabel2ctId("Mood");
    static String numberConceptType = ctLabel2ctId("Number");
    static String personConceptType = ctLabel2ctId("Person");
    static String tenseConceptType = ctLabel2ctId("Tense");

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

    public EngineKnowledgeBase() throws Exception {
        openKb(null);
    }

    public void openKb(File file) throws Exception {
        if (file == null) {
            file = Globals.getDefaultParseKBFile();
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

    public CGraph addSentenceFact(WTagging[] words, List<TypedDependency> deps) {
        String sentId = toSentenceIndex(++sentenceFactCount);
        CGraph cg = new CGraph(toSentenceId(sentId), sentId, sentenceSetName, "fact");

        for (int i = 0; i < words.length; ++i) {
            WTagging tagging = words[i];
            Concept c = new Concept(tagging.getForm() + "-" + tagging.getPennTag() + "-" + (i + 1));
            vocabulary.addIndividual(tagging.getLemma(), tagging.getLemma(), posConceptType, language);
            String[] types = null;
            if (tagging.getPos() == null) {
                types = new String[]{tagging.getPennTag()};
            } else {
                types = tagging.asStringArray();
            }
            for (int t = 0; t < types.length; ++t) {
                types[t] = ctLabel2ctId(types[t]);
            }
            c.setType(types);
            c.setIndividual(tagging.getLemma());
            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = getConcept(cg, getConceptIndex(tdep.gov().nodeString())).getId();
            String dep = getConcept(cg, getConceptIndex(tdep.dep().nodeString())).getId();
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

    public CGraph getSentenceFact(int idx) {
        return kb.getFactGraph(toSentenceId(idx));
    }

    public String getConceptTypeLabel(String ct) {
        return vocabulary.getConceptTypeLabel(ct, language);
    }

    public int getSentenceFactCount() {
        return sentenceFactCount;
    }

    private WTagging conceptLabelsToWTagging(Concept c, boolean newTagsOnly) {
        WTagging prop = new WTagging();
        Hierarchy cth = vocabulary.getConceptTypeHierarchy();
        for (String type : c.getType()) {
            // System.out.println(type + " : " + c.getId());
            try {
                if (cth.isKindOf(type, posConceptType)) {
                    if (newTagsOnly && cth.isKindOf(type, spTagConceptType)) {
                        prop = new WTagging();
                        break;
                    } else {
                        prop.setPos(vocabulary.getConceptTypeLabel(type, language));
                    }
                } else if (cth.isKindOf(type, caseConceptType)) {
                    prop.setWcase(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, comparisonConceptType)) {
                    prop.setComp(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, genderConceptType)) {
                    prop.setGender(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, moodConceptType)) {
                    prop.setMood(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, numberConceptType)) {
                    prop.setNumber(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, personConceptType)) {
                    prop.setPerson(vocabulary.getConceptTypeLabel(type, language));
                } else if (cth.isKindOf(type, tenseConceptType)) {
                    prop.setTense(vocabulary.getConceptTypeLabel(type, language));
                }
            } catch (RuntimeException ex) {
                System.err.println("At concept: " + type + " : " + c.getId());
                throw ex;
            }
        }
        prop.setForm(c.getId().split("-")[0]);
        prop.setLemma(c.getIndividual());
        prop.setPennTag(getPennTag(c.getId()));
        return prop;
    }

    public WTagging[] getSentencePosProps(int idx, boolean newTagsOnly) {
        CGraph cg = getSentenceFact(idx);
        WTagging[] props = new WTagging[cg.getConcepts().size()];
        for (int j = 1; j <= props.length; ++j) {
            props[j - 1] = conceptLabelsToWTagging(getConcept(cg, j), newTagsOnly);
        }
        return props;
    }

    private Concept getConcept(CGraph cg, int j) {
        for (Concept c : cg.getConcepts()) {
            if (j == getConceptIndex(c.getId())) {
                return c;
            }
        }
        return null;
    }

    private String getPennTag(String id) {
        int to = id.lastIndexOf('-');
        int from = id.lastIndexOf('-', to - 1) + 1;
        return id.substring(from, to);
    }

    private int getConceptIndex(String id) {
        int idxPos = id.lastIndexOf('-') + 1;
        int index = Integer.parseInt(id.substring(idxPos));
        return index;
    }
}
