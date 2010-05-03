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

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import fr.lirmm.rcr.cogui2.kernel.solver.SolverCogitant;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProjectionSolver {

    private final WKB kb;
    private final SolverCogitant solver;

    public ProjectionSolver(WKB kb) {
        this.kb = kb;
        this.solver = new SolverCogitant();
    }

    public void reset() throws Exception {
        if (solver.isConnected()) {
            solver.disconnect();
        }
        solver.connect();
        solver.commitVocabulary(kb.getVocabulary());
    }

    public List<Proc> findMatches(ProcList procs, CGraph cg) throws Exception {
        List<Proc> matches = new ArrayList<Proc>();

        for (Proc proc : procs.getProcs()) {
            CGraph lhs = proc.getLhs();
            List<Projection> projections = solver.getProjections(lhs, cg);
            if (projections.size() > 0) {
                proc.setProjections(projections);
                matches.add(proc);
            } else {
                proc.setProjections(null);
            }
        }

        return matches;
    }
}
