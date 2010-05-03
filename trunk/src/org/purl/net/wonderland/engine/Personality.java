/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
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
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.WKBUtil;
import org.purl.net.wonderland.kb.WKB;
import org.purl.net.wonderland.kb.Proc;
import org.purl.net.wonderland.kb.ProjectionSolver;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian
 */
public abstract class Personality {

    protected WKB kb = null;
    protected ProjectionSolver projSlv = null;
    protected ProcManager procMgr = null;

    public void setKb(WKB kb) {
        this.kb = kb;
        try {
            projSlv = new ProjectionSolver(kb);
            procMgr = new ProcManager(kb);
        } catch (Exception ex) {
            System.err.println("Could not set knowledge base");
            Configuration.handleException(ex);
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
            facts.add(kb.buildFactGraph(sentence, deps));
        }

        return facts;
    }

    protected void processMoods(CGraph fact) throws Exception {
        applyProcSet(fact, WKBUtil.procSetMoods);
    }

    protected void processCollocations(CGraph fact) throws Exception {
        applyProcSet(fact, WKBUtil.procSetCollocations);
    }

    protected void processArticles(CGraph fact) throws Exception {
        applyProcSet(fact, WKBUtil.procSetArticles);
    }

    protected void applyProcSet(CGraph fact, String procSet) throws Exception {
        WKBUtil.setAllConclusion(fact, false);
        List<Proc> matches = projSlv.findMatches(procMgr.getProcSet(procSet), fact);
        for (Proc match : matches) {
            if (match != null) {
                // System.out.println("procedure: " + match.getId());
                for (Projection proj : match.getProjections()) {
                    WKBUtil.applyProcedure(fact, proj, match, true, kb.getVocabulary().getConceptTypeHierarchy());
                }
            }
        }
        WKBUtil.setAllConclusion(fact, false);
    }
}
