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
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.util.Compare;
import org.purl.net.wonderland.util.IdUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcList {

    private final String setName;
    private final String setId;
    private final List<Proc> procs;

    public ProcList(String name) {
        this.setName = name;
        this.setId = WkbUtil.toProcName(name, null);
        this.procs = new ArrayList<Proc>();
    }

    public String getName() {
        return setName;
    }

    public List<Proc> getProcs() {
        return procs;
    }

    public void sort() {
        Collections.sort(procs, new ProcComparator());
    }

    public int size() {
        return procs.size();
    }

    public void add(Rule rule) {
        String procName = rule.getName().substring(setId.length());
        Proc proc = buildProcedure(rule, procName);
        procs.add(proc);
    }

    private Proc buildProcedure(Rule rule, String name) {
        CGraph lhs = new CGraph(IdUtil.newId(), name, "lhs", "fact");
        CGraph rhs = new CGraph(IdUtil.newId(), name, "rhs", "fact");

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

        Proc proc = new ProcImpl(lhs, rhs, rhsLhsMap);
        return proc;
    }
}

class ProcComparator implements Comparator<Proc> {

    public int compare(Proc proc1, Proc proc2) {
        int cmp = Compare.compare(proc1.getPriority(), proc2.getPriority());
        if (cmp != 0) {
            return -cmp;
        }
        cmp = Compare.compare(proc1.getLhsComplexity(), proc2.getLhsComplexity());
        if (cmp == 0) {
            // System.out.println(proc1.getId() + "[" + proc1.getLhsComplexity() + "] <=> " + proc2.getId() + "[" + proc2.getLhsComplexity() + "]");
        }
        return -cmp;
    }
}


