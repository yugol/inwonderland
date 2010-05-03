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
package org.purl.net.wonderland.engine;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.Iterator;
import java.util.List;
import org.purl.net.wonderland.kb.WKBUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level3Personality extends Personality {

    @Override
    public String getWelcomeMessage() {
        return "disambiguating";
    }

    @Override
    public String getFullName() {
        return "Level 3";
    }

    @Override
    public String getName() {
        return "(test) L2";
    }

    @Override
    public String getId() {
        return WKBUtil.level3;
    }

    @Override
    public String processMessage(String message) throws Exception {
        List<CGraph> facts = parseMessage(message);
        projSlv.reset();
        for (CGraph fact : facts) {
            kb.addFact(fact, WKBUtil.level1);

            fact = WKBUtil.duplicate(fact);
            processArticles(fact);
            // processMoods(fact);
            // processCollocations(fact);
            kb.addFact(fact, WKBUtil.level2);

            fact = WKBUtil.duplicate(fact);
            disambiguate(fact);
            kb.addFact(fact, WKBUtil.level3);
        }
        return "Done.";
    }

    private void disambiguate(CGraph fact) {
        Hierarchy cth = kb.getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> it = fact.iteratorConcept();
        while (it.hasNext()) {
            Concept c = it.next();
            String lemma = c.getIndividual();
            String[] types = c.getType();

            if (cth.isKindOf(types, WKBUtil.VERB_CT)) {
            }
        }
    }
}
