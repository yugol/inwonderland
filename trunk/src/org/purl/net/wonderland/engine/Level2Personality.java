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
package org.purl.net.wonderland.engine;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import java.util.List;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.Proc;
import org.purl.net.wonderland.kb.ProcList;
import org.purl.net.wonderland.kb.ProcManager;
import org.purl.net.wonderland.kb.ProcUtil;
import org.purl.net.wonderland.kb.ProjectionSolver;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level2Personality extends Level1Personality {

    protected ProjectionSolver projSlv = null;
    protected ProcManager procMgr = null;

    @Override
    public void setKb(Wkb kb) {
        super.setKb(kb);
        try {
            projSlv = new ProjectionSolver(kb);
            procMgr = new ProcManager(kb);
        } catch (Exception ex) {
            System.err.println("Could not set knowledge base");
            Configuration.handleException(ex);
        }
    }

    @Override
    public String getWelcomeMessage() {
        return "recognizing tenses, finding collocations, logics";
    }

    @Override
    public String getFullName() {
        return "Level 2";
    }

    @Override
    public String getName() {
        return "(test) L2";
    }

    @Override
    public String getId() {
        return WkbConstants.LEVEL2;
    }

    @Override
    public String processMessage(String message) throws Exception {
        List<CGraph> facts = parseMessage(message);
        projSlv.reset();
        for (CGraph fact : facts) {
            kb.addFact(fact, WkbConstants.LEVEL1);

            fact = WkbUtil.duplicate(fact);
            processFact(fact);
            kb.addFact(fact, WkbConstants.LEVEL2);
        }
        return "Done.";
    }

    protected void processMoods(CGraph fact) throws Exception {
        applyAllNonOverlappingMatches(procMgr.getProcSet(WkbUtil.procSetMoods), fact);
    }

    protected void processCollocations(CGraph fact) throws Exception {
        applyAllNonOverlappingMatches(procMgr.getProcSet(WkbUtil.procSetCollocations), fact);
    }

    protected void processArticles(CGraph fact) throws Exception {
        applyAllNonOverlappingMatches(procMgr.getProcSet(WkbUtil.procSetArticles), fact);
    }

    protected void applyAllNonOverlappingMatches(ProcList procs, CGraph fact) throws Exception {
        WkbUtil.setAllConclusion(fact, false);
        List<Proc> matches = projSlv.findMatches(procs, fact);
        for (Proc match : matches) {
            if (match != null) {
                for (Projection proj : match.getProjections()) {
                    ProcUtil.applyProcMatch(fact, proj, match, true, kb.getVocabulary().getConceptTypeHierarchy());
                }
            }
        }
        WkbUtil.setAllConclusion(fact, false);
    }

    protected void applyFirstMatch(ProcList procs, CGraph fact) throws Exception {
        List<Proc> matches = projSlv.findMatches(procs, fact);
        apply_just_one_match:
        for (Proc match : matches) {
            if (match != null) {
                for (Projection proj : match.getProjections()) {
                    ProcUtil.applyProcMatch(fact, proj, match, false, kb.getVocabulary().getConceptTypeHierarchy());
                    break apply_just_one_match;
                }
            }
        }
    }

    protected void processFact(CGraph fact) throws Exception {
        processArticles(fact);
        // processMoods(fact);
        // processCollocations(fact);
    }
}
