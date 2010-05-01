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

import edu.stanford.nlp.util.StringUtils;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import fr.lirmm.rcr.cogui2.kernel.solver.SolverCogitant;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcManager {

    private Map<String, List<Procedure>> procs = new Hashtable<String, List<Procedure>>();
    private final SolverCogitant solver = new SolverCogitant();
    private final WKB kb;

    public ProcManager(WKB kb) {
        this.kb = kb;
    }

    public int getProcCount() {
        int count = 0;
        for (List<Procedure> set : procs.values()) {
            count += set.size();
        }
        return count;
    }

    private void addProcedure(String set, Procedure t) {
        if (!procs.containsKey(set)) {
            procs.put(set, new ArrayList<Procedure>());
        }
        if (t != null) {
            procs.get(set).add(t);
        }
    }

    public void readAllProceduresFromKb() throws Exception {
        readProcedureSet(WKBUtil.procSetArticles);
        readProcedureSet(WKBUtil.procSetMoods);
        readProcedureSet(WKBUtil.procSetCollocations);
    }

    private void readProcedureSet(String set) throws Exception {
        String setId = WKBUtil.toProcName(set, null);
        addProcedure(set, null);
        List<Rule> rules = kb.getProcRules(set);
        for (Rule rule : rules) {
            String name = rule.getName().substring(setId.length());
            Procedure proc = buildProcedure(rule, name);
            addProcedure(set, proc);
        }
        sortProcedureSet(set);
    }

    private Procedure buildProcedure(Rule rule, String name) {
        CGraph lhs = new CGraph(UUID.randomUUID().toString(), name, "lhs", "fact");
        CGraph rhs = new CGraph(UUID.randomUUID().toString(), name, "rhs", "fact");

        Iterator<Concept> cit = rule.iteratorConcept();
        while (cit.hasNext()) {
            Concept c = cit.next();
            if (c.isHypothesis()) {
                lhs.addVertex(c);
            } else {
                rhs.addVertex(c);
            }
        }

        Iterator<Relation> rit = rule.iteratorRelation();
        while (rit.hasNext()) {
            Relation r = rit.next();
            if (r.isHypothesis()) {
                lhs.addVertex(r);
            } else {
                rhs.addVertex(r);
            }
        }

        Iterator<CREdge> eit = rule.iteratorEdge();
        while (eit.hasNext()) {
            CREdge e = eit.next();
            Concept c = rule.getConcept(e);
            Relation r = rule.getRelation(e);
            if (c.isHypothesis()) {
                lhs.addEdge(c.getId(), r.getId(), e.getNumOrder());
            } else {
                rhs.addEdge(c.getId(), r.getId(), e.getNumOrder());
            }
        }

        Map<Concept, Concept> rhsLhsMap = new Hashtable<Concept, Concept>();
        Iterator<String> it = rule.iteratorHypothesisFrontier();
        while (it.hasNext()) {
            String id = it.next();
            // System.out.println(id);
            Concept l = lhs.getConcept(id);
            Concept r = rhs.getConcept(rule.getConclusionOf(id));
            rhsLhsMap.put(r, l);
        }

        Procedure proc = new ProcImpl(lhs, rhs, rhsLhsMap);
        return proc;
    }

    private String[] getDesc(String str) {
        String[] desc = new String[2];
        int varpos = str.lastIndexOf("var_");
        if (varpos == 0) {
            desc[1] = str.substring(4);
        } else if (varpos < 0) {
            desc[0] = str;
        } else {
            desc[0] = str.substring(0, varpos - 1);
            desc[1] = str.substring(varpos + 4);
        }
        return desc;
    }

    public Procedure findMatch(String procSet, CGraph cg, Set<Procedure> exclude) throws Exception {
        CodeTimer timer = new CodeTimer("projection");
        resetSolver();

        Procedure match = null;
        if (procs.get(procSet) != null) {
            for (Procedure proc : procs.get(procSet)) {
                if (exclude != null && exclude.contains(proc)) {
                    continue;
                }
                CGraph lhs = proc.getLhs();
                List<Projection> projections = solver.getProjections(lhs, cg);
                if (projections.size() > 0) {
                    proc.setProjections(projections);
                    match = proc;
                    break;
                } else {
                    proc.setProjections(null);
                    if (exclude != null && exclude.contains(proc)) {
                        exclude.add(proc);
                    }
                }
            }
        }

        timer.stop();
        return match;
    }

    public List<Procedure> findMatches(String procSet, CGraph cg) throws Exception {
        // CodeTimer timer = new CodeTimer("projection");
        // resetSolver();

        List<Procedure> matches = new ArrayList<Procedure>();
        if (procs.get(procSet) != null) {
            for (Procedure proc : procs.get(procSet)) {
                CGraph lhs = proc.getLhs();
                List<Projection> projections = solver.getProjections(lhs, cg);
                if (projections.size() > 0) {
                    proc.setProjections(projections);
                    matches.add(proc);
                } else {
                    proc.setProjections(null);
                }
            }
        }

        // timer.stop();
        return matches;
    }

    public List<Procedure> findMatches(String set, String id) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    public void resetSolver() throws Exception {
        if (solver.isConnected()) {
            solver.disconnect();
        }
        solver.connect();
        solver.commitVocabulary(kb.getVocabulary());
    }

    private void sortProcedureSet(String set) {
        List<Procedure> procSet = procs.get(set);
        Collections.sort(procSet, new PriorityComparator());
        /*
        System.out.println("= " + set + " =");
        for (Procedure proc : procSet) {
        System.out.println(proc.getId() + " - " + proc.getLhsComplexity());
        }
         */
    }
}
