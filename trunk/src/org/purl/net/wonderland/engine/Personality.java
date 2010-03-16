/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, mergeWtags, publish, distribute, sublicense, and/or sell
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
package org.purl.net.wonderland.engine;

import edu.stanford.nlp.trees.TypedDependency;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.KbUtil;
import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.purl.net.wonderland.kb.generators.ProcManager;
import org.purl.net.wonderland.kb.generators.Procedure;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/*
Bashful	Long beard	Brown top, green hat, long eyelashes
Doc	Short beard	Red tunic, brown hat, glasses
Dopey	Beardless	Green tunic, purple hat, big ears
Grumpy	Long beard	Red tunic, brown hat, scowl
Happy	Short beard	Brown top, orange headpiece, smile
Sleepy	Long beard	Green top, blue hat, heavy eyelids
Sneezy	Short beard	brown jacket, orange headpiece, red nose
 */
/**
 *
 * @author Iulian
 */
public abstract class Personality {

    protected WKnowledgeBase kb = null;
    protected ProcManager procMgr = null;
    protected ReferenceSolver refSlv = null;

    public void setKb(WKnowledgeBase kb) {
        this.kb = kb;
        try {
            procMgr = new ProcManager(kb);
            procMgr.readAllProceduresFromKb();
            refSlv = new ReferenceSolver(kb);
        } catch (Exception ex) {
            System.err.println("Could not set knowledge base");
            ex.printStackTrace(System.err);
            Globals.exit();
        }
    }

    public abstract String getWelcomeMessage();

    public abstract String getFullName();

    public abstract String getName();

    public abstract String getId();

    public abstract String processMessage(String message) throws Exception;

    protected List<CGraph> parseMessage(String message) {
        List<CGraph> facts = new ArrayList<CGraph>();

        for (List<WTagging> sentence : Pipeline.tokenizeAndSplit(message)) {
            Object[] parse = Pipeline.parse(sentence);
            sentence = (List<WTagging>) parse[0];
            List<TypedDependency> deps = (List<TypedDependency>) parse[1];
            facts.add(buildFactGraph(sentence, deps));
        }

        return facts;
    }

    private CGraph buildFactGraph(List<WTagging> words, List<TypedDependency> deps) {
        Vocabulary vocabulary = kb.getVocabulary();
        CGraph cg = new CGraph(KbUtil.newUniqueId(), null, null, "fact");

        for (int i = 0; i < words.size(); ++i) {
            WTagging tagging = words.get(i);
            Concept c = new Concept(KbUtil.toConceptId(tagging, i + 1));
            String individualId = KbUtil.handleQuotes(tagging.getLemma());
            vocabulary.addIndividual(individualId, individualId, KbUtil.Top, kb.getLanguage());
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

    protected void processMoods(CGraph fact) throws Exception {
        applyProcSet(fact, KbUtil.procSetMoods);
    }

    protected void processCollocations(CGraph fact) throws Exception {
        applyProcSet(fact, KbUtil.procSetCollocations);
    }

    protected void processArticles(CGraph fact) throws Exception {
        applyProcSet(fact, KbUtil.procSetArticles);
    }

    protected void applyProcSet(CGraph fact, String procSet) throws Exception {
        KbUtil.setAllConclusion(fact, false);
        List<Procedure> matches = procMgr.findMatches(procSet, fact);
        for (Procedure match : matches) {
            if (match != null) {
                // System.out.println("procedure: " + match.getId());
                for (Projection proj : match.getProjections()) {
                    KbUtil.applyProcedure(fact, proj, match, true, kb.getVocabulary().getConceptTypeHierarchy());
                }
            }
        }
        KbUtil.setAllConclusion(fact, false);
    }
}
