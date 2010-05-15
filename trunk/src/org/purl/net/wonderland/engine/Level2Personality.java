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
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.Match;
import org.purl.net.wonderland.kb.Matches;
import org.purl.net.wonderland.kb.Procs;
import org.purl.net.wonderland.kb.ProcUtil;
import org.purl.net.wonderland.kb.ProjectionSolver;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level2Personality extends Level1Personality {

    protected ProjectionSolver projSlv = null;

    @Override
    public void setMemory(Memory memory) {
        super.setMemory(memory);
        try {
            projSlv = new ProjectionSolver(memory.getStorage());
        } catch (Exception ex) {
            System.err.println("Could not set knowledge base");
            W.handleException(ex);
        }
    }

    @Override
    public String getWelcomeMessage() {
        return "grouping tokens";
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
    protected void preProcessFacts() throws Exception {
        projSlv.reset();
    }

    @Override
    protected CGraph handleFact(CGraph fact) throws Exception {
        fact = super.handleFact(fact);
        fact = WkbUtil.duplicate(fact);
        processFactLevel2(fact);
        memory.getStorage().addFact(fact, WkbConstants.LEVEL2);
        return fact;
    }

    private void processFactLevel2(CGraph fact) throws Exception {
        processArticles(fact);
        // processMoods(fact);
        // processCollocations(fact);
    }

    protected void processMoods(CGraph fact) throws Exception {
        applyAllNonOverlappingMatches(memory.getProcedural().getQuick().getProcSet(WkbUtil.PROC_SET_MOODS), fact);
    }

    protected void processCollocations(CGraph fact) throws Exception {
        applyAllNonOverlappingMatches(memory.getProcedural().getQuick().getProcSet(WkbUtil.PROC_SET_COLLO), fact);
    }

    protected void processArticles(CGraph fact) throws Exception {
        applyAllNonOverlappingMatches(memory.getProcedural().getQuick().getProcSet(WkbUtil.PROC_SET_ARTICLES), fact);
    }

    protected void applyAllNonOverlappingMatches(Procs procs, CGraph fact) throws Exception {
        WkbUtil.setAllConclusion(fact, false);
        Matches matches = projSlv.findMatches(procs, fact);
        for (Match match : matches) {
            ProcUtil.applyProcMatch(fact, match, true, memory.getCth());
        }
        WkbUtil.setAllConclusion(fact, false);
    }
}
