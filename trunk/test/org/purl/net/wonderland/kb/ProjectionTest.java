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
package org.purl.net.wonderland.kb;

import java.io.File;
import java.util.List;
import org.junit.Test;
import org.purl.net.wonderland.W;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProjectionTest {

    @Test
    public void testProjection() throws Exception {
        Wkb kb = new Wkb(new File(W.getTestDataFolder(), "bedtime.cogxml"));
        ProcManager procMgr = new ProcManager();
        procMgr.putProcList(WkbUtil.PROC_SET_ARTICLES, kb.getProcRules(WkbUtil.PROC_SET_ARTICLES));
        assertEquals(2, procMgr.getProcCount());

        ProjectionSolver solver = new ProjectionSolver(kb);
        solver.reset();
        MatchList matches = solver.findMatches(procMgr.getProcSet(WkbUtil.PROC_SET_ARTICLES),
                kb.getFactGraph(WkbUtil.toFactId(4, WkbConstants.LEVEL1)));
        assertEquals(1, matches.size());

        for (Match m : matches) {
            m.getProcedure();
        }
    }
}
