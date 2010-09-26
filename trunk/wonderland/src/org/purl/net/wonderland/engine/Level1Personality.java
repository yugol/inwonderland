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
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.Iterator;
import org.purl.net.wonderland.kb.WkbConstants;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level1Personality extends Personality {

    @Override
    public String getWelcomeMessage() {
        return "basic parsing -> CG";
    }

    @Override
    public String getFullName() {
        return "Level 1";
    }

    @Override
    public String getName() {
        return "(test) L1";
    }

    @Override
    public String getId() {
        return WkbConstants.LEVEL1;
    }

    @Override
    protected CGraph handleFact(CGraph fact) throws Exception {
        memory.getStorage().addFact(fact, getCurrentFactId(), WkbConstants.LEVEL1);
        return fact;
    }

    protected FactNature findNature(CGraph fact) {
        Hierarchy cth = memory.getCth();
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            Iterator<CREdge> links = fact.iteratorEdge(c.getId());
            if (!links.hasNext()) {
                if (cth.isKindOf(c.getType(), WkbConstants.INTERROGATIVEPUNCTUATION_CT)) {
                    return FactNature.QUESTION;
                }
            }
        }
        return FactNature.STATEMENT;
    }
}
