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

import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcImpl implements Proc {

    private final String id;
    private final CGraph lhs;
    private final CGraph rhs;
    private final Map<Concept, Concept> rhsLhsMapping;
    private final double lhsComplexity;
    private final double priority;

    public String getId() {
        return id;
    }

    public ProcImpl(CGraph lhs, CGraph rhs, Map<Concept, Concept> conceptMap) {
        double tempPriority = 0.5;
        String tempId = lhs.getName();
        int priSepPos = tempId.lastIndexOf("_");
        if (priSepPos >= 0) {
            String priSepStr = tempId.substring(priSepPos + 1);
            try {
                tempPriority = Double.parseDouble(priSepStr);
                tempId = tempId.substring(0, priSepPos);
            } catch (NumberFormatException ex) {
            }
        }

        double tempComplexity = 0;
        Iterator<Concept> cIt = lhs.iteratorConcept();
        while (cIt.hasNext()) {
            tempComplexity += 1;
            cIt.next();
        }
        Iterator<Relation> rIt = lhs.iteratorRelation();
        while (rIt.hasNext()) {
            tempComplexity += 1;
            rIt.next();
        }
        Iterator<CREdge> eIt = lhs.iteratorEdge();
        while (eIt.hasNext()) {
            tempComplexity += 1;
            eIt.next();
        }

        this.id = tempId;
        this.priority = tempPriority;
        this.lhs = lhs;
        this.rhs = rhs;
        this.rhsLhsMapping = conceptMap;
        this.lhsComplexity = tempComplexity;
    }

    public CGraph getLhs() {
        return lhs;
    }

    public CGraph getRhs() {
        return rhs;
    }

    public Map<Concept, Concept> getRhsLhsMap() {
        return rhsLhsMapping;
    }

    public double getPriority() {
        return priority;
    }

    public double getComplexity() {
        return lhsComplexity;
    }

    public Rule getRule(String setName) {
        Rule rule = Rule.createRule(id, WkbUtil.toProcName(setName, id), lhs, rhs);
        for (Concept c : rhsLhsMapping.keySet()) {
            String conc = c.getId();
            String hypt = rhsLhsMapping.get(c).getId();
            rule.addCouple(hypt, conc);
        }
        return rule;
    }
}
