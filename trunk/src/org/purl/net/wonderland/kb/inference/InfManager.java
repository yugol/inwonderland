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
package org.purl.net.wonderland.kb.inference;

import edu.stanford.nlp.util.StringUtils;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.solver.SolverCogitant;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.purl.net.wonderland.util.IO;

/**
 *
 * @author Iulian
 */
public class InfManager {

    public static String toClassName(String group, int id) {
        return StringUtils.capitalize(group.toLowerCase()) + "_" + id;
    }
    private Map<String, Set<Inference>> inferences = new Hashtable<String, Set<Inference>>();
    private final SolverCogitant solver = new SolverCogitant();
    private KnowledgeBase kb;
    private final InfFactory infFactory;

    public InfManager(KnowledgeBase kb, InfFactory infFactory) {
        this.kb = kb;
        this.infFactory = infFactory;
    }

    public void setKb(KnowledgeBase kb) {
        solver.disconnect();
        this.kb = kb;
    }

    public void add(CGraph cg) {
        String[] chunks = cg.getName().split("_", 3);
        try {
            String group = chunks[0].toLowerCase();
            int id = Integer.parseInt(chunks[1]);
            String className = toClassName(group, id);
            Inference inf = infFactory.createInference(className);
            inf.init(kb, cg, id);
            add(group, inf);
        } catch (Exception ex) {
            System.err.println("Error adding rule: " + cg.getName());
            System.err.println(ex);
        }
    }

    public List<Inference> findMatches(String group, CGraph cg) throws Exception {
        if (!solver.isConnected()) {
            solver.connect();
            solver.commitVocabulary(kb.getVocabulary());
            solver.resetCommitedGraphs();
        }
        List<Inference> matches = new ArrayList<Inference>();
        for (Inference t : inferences.get(group)) {
            CGraph lhs = t.getLhs();
            List<Projection> projections = solver.getProjections(lhs, cg);
            if (projections.size() > 0) {
                t.setProjections(projections);
                matches.add(t);
            } else {
                t.setProjections(null);
            }
        }
        solver.removeGraph(cg);
        return matches;
    }

    private void add(String group, Inference t) {
        if (!inferences.containsKey(group)) {
            inferences.put(group, new TreeSet<Inference>());
        }
        inferences.get(group).add(t);
    }

    public int getInfCount() {
        int count = 0;
        for (String group : inferences.keySet()) {
            count += inferences.get(group).size();
        }
        return count;
    }
}
