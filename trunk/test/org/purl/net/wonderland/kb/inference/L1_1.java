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

import org.purl.net.wonderland.kb.inference.provided.DefaultInference;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.kb.EngineKB;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class L1_1 extends DefaultInference {

    public void apply(CGraph resultGraph) {
        for (Projection p : projections) {
            apply(p, resultGraph);
        }
    }

    private void apply(Projection p, CGraph resultGraph) {
        String subj = ((Concept) p.getTarget("_c1")).getIndividual();
        String vb = ((Concept) p.getTarget("_c2")).getIndividual();
        String obj = ((Concept) p.getTarget("_c3")).getIndividual();

        Concept c1 = new Concept("_c1");
        c1.setType(ekb.importWordNetHypernymHierarchy(subj, POS.NOUN));
        c1.setIndividual(subj);

        Concept c2 = new Concept("_c2");
        c2.setType(ekb.importWordNetHypernymHierarchy(obj, POS.NOUN));
        c2.setIndividual(obj);

        Concept c3 = new Concept("_c3");
        c3.setType(EngineKB.toConceptTypeId("aff"));

        Relation r = new Relation("_r1");
        r.addType(ekb.addWRelation(vb, null));

        resultGraph.addVertex(c1);
        resultGraph.addVertex(c2);
        resultGraph.addVertex(c3);
        resultGraph.addVertex(r);

        resultGraph.addEdge("_c3", "_r1", 1);
        resultGraph.addEdge("_c1", "_r1", 2);
        resultGraph.addEdge("_c2", "_r1", 3);
    }
}
