/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.kb;

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
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.transformations.ITransformation;
import org.purl.net.wonderland.kb.transformations.TransformationManager;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian
 */
public class EngineKnowledgeBase {

    static {
        try {
            CodeTimer timer = new CodeTimer("loading cogitatnt.dll");
            System.load(new File(Globals.getResFolder(), "cogitant.dll").getCanonicalPath());
            timer.stop();
        } catch (Exception ex) {
            System.err.println("Error loading cogitant.dll");
            System.err.println(ex);
            Globals.exit();
        }

    }
    static final String level1 = "level1";
    static final String transformationsSetName = "TRANSFORMATIONS";
    static NumberFormat formatter = new DecimalFormat("0000");
    static String posConceptType = toConceptTypeId("Pos");
    static String spTagConceptType = toConceptTypeId("SpTag");
    static String caseConceptType = toConceptTypeId("Case");
    static String comparisonConceptType = toConceptTypeId("Comparison");
    static String genderConceptType = toConceptTypeId("Gender");
    static String moodConceptType = toConceptTypeId("Mood");
    static String numberConceptType = toConceptTypeId("Number");
    static String personConceptType = toConceptTypeId("Person");
    static String tenseConceptType = toConceptTypeId("Tense");

    private static String removeQuotes(String ctl) {
        if ("''".equals(ctl)) {
            ctl = "-OPQ-";
        } else if ("``".equals(ctl)) {
            ctl = "-CLQ-";
        }
        return ctl.replace("'", "`");
    }

    private static String toIdIndex(int num) {
        return formatter.format(num);
    }

    public static String toLevel1FactId(int num) {
        return "l1_" + toIdIndex(num);
    }

    public static String toConceptTypeId(String ctl) {
        return "ct_" + removeQuotes(ctl);
    }

    public static String toRelationTypeId(String ctl) {
        return "rt_" + ctl;
    }

    private static String toConceptId(WTagging tagging, int index) {
        StringBuilder sb = new StringBuilder();
        sb.append(removeQuotes(tagging.getForm()));
        sb.append("#");
        sb.append(index);
        sb.append("=[");
        if (tagging.getPennTag() != null) {
            sb.append(removeQuotes(tagging.getPennTag()));
        }
        sb.append("]{");
        if (tagging.getPartsOfSpeech() != null) {
            sb.append(removeQuotes(tagging.getPartsOfSpeech()));
        }
        sb.append("}");
        return sb.toString();
    }

    private static String toRelationId(String label, int index) {
        return label + "~" + index;
    }
    private String language = "en";
    private KnowledgeBase kb;
    private Vocabulary vocabulary;
    private int sentenceFactCount = 0;
    private File lastFile = null;
    private boolean dirty = false;
    private TransformationManager tMgr = new TransformationManager();

    public TransformationManager getTransformationManager() {
        return tMgr;
    }

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

    private void loadKb(File file) throws Exception {
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
        tMgr.setKb(kb);
        for (CGraph cg : kb.getFactGraphSet().values()) {
            String setName = cg.getSet();
            if (setName.equals(level1)) {
                sentenceFactCount++;
            } else if (setName.equals(transformationsSetName)) {
                tMgr.add(cg);
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

    public CGraph addSentenceFact(List<WTagging> words, List<TypedDependency> deps) {
        String sentId = toIdIndex(++sentenceFactCount);
        CGraph cg = new CGraph(toLevel1FactId(sentenceFactCount), sentId, level1, "fact");

        for (int i = 0; i < words.size(); ++i) {
            WTagging tagging = words.get(i);
            Concept c = new Concept(toConceptId(tagging, i + 1));
            String individualId = removeQuotes(tagging.getLemma());
            vocabulary.addIndividual(individualId, individualId, posConceptType, language);
            String[] types = null;
            if (tagging.getPos() == null) {
                types = new String[]{tagging.getPennTag()};
            } else {
                types = tagging.asStringArray();
            }
            for (int t = 0; t < types.length; ++t) {
                types[t] = toConceptTypeId(types[t]);
                // System.out.println(types[t] + " : " + tagging.getForm());
            }
            c.setType(types);
            c.setIndividual(individualId);
            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = getConcept(cg, getLabelIndex(tdep.gov().nodeString())).getId();
            String dep = getConcept(cg, getLabelIndex(tdep.dep().nodeString())).getId();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = toRelationTypeId(relationTypeLabel);
            String relationId = toRelationId(relationTypeLabel, (i + 1));

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
        return kb.getFactGraph(toLevel1FactId(idx));
    }

    public int getSentenceFactCount() {
        return sentenceFactCount;
    }

    private WTagging conceptLabelsToWTagging(Concept c, boolean newTagsOnly) {
        WTagging prop = new WTagging();
        Hierarchy cth = vocabulary.getConceptTypeHierarchy();
        String id = c.getId();
        for (String type : c.getType()) {
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
                System.err.println("At concept: " + type + " : " + id + " -> " + getConceptForm(id));
                throw ex;
            }
        }
        prop.setForm(getConceptForm(id));
        prop.setLemma(c.getIndividual());
        prop.setPennTag(getConceptPennTag(id));
        prop.setPartsOfSpeech(getConceptMaTag(id));
        return prop;
    }

    public WTagging[] getSentenceWTaggings(int idx, boolean newTagsOnly) {
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

    private String getConceptForm(String id) {
        int end = id.lastIndexOf("#");
        return id.substring(0, end);
    }

    private int getConceptIndex(String id) {
        int beg = id.lastIndexOf('#') + 1;
        int end = id.lastIndexOf('=');
        int index = Integer.parseInt(id.substring(beg, end));
        return index;
    }

    private String getConceptPennTag(String id) {
        int beg = id.lastIndexOf("[") + 1;
        int end = id.lastIndexOf("]");
        if (beg < end) {
            return id.substring(beg, end);
        } else {
            return null;
        }
    }

    private String getConceptMaTag(String id) {
        int beg = id.lastIndexOf("{") + 1;
        int end = id.lastIndexOf("}");
        if (beg < end) {
            return id.substring(beg, end);
        } else {
            return null;
        }
    }

    private int getLabelIndex(String label) {
        int beg = label.lastIndexOf("-") + 1;
        return Integer.parseInt(label.substring(beg));
    }

    public List<ITransformation> applyTransformations(String group, String graphId) throws Exception {
        CGraph cg = kb.getFactGraph(graphId);
        return tMgr.apply(group, cg);
    }
}
