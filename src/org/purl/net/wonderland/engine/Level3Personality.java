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
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.kb.ProcList;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level3Personality extends Level2Personality {

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
        return "(test) L3";
    }

    @Override
    public String getId() {
        return WkbConstants.LEVEL3;
    }

    @Override
    protected void processFact(CGraph fact) throws Exception {
        super.processFact(fact);

        fact = WkbUtil.duplicate(fact);
        loadSenses(fact);
        projSlv.reset();
        disambiguate(fact);
        memory.getStorage().addFact(fact, WkbConstants.LEVEL3);
    }

    private void loadSenses(CGraph fact) {
        Wkb storage = memory.getStorage();
        Hierarchy cth = storage.getVocabulary().getConceptTypeHierarchy();

        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            String lemma = c.getIndividual();
            String[] types = c.getType();

            String[] senses = null;
            if (cth.isKindOf(types, WkbConstants.NOUN_CT)) {
                if (cth.isKindOf(types, WkbConstants.COMMONNOUN_CT)) {
                    senses = storage.importWordNetHypernymHierarchy(lemma, POS.NOUN);
                } else if (cth.isKindOf(types, WkbConstants.PROPERNOUN_CT)) {
                }
            } else if (cth.isKindOf(types, WkbConstants.VERB_CT)) {
                senses = storage.importWordNetHypernymHierarchy(lemma, POS.VERB);
            } else if (cth.isKindOf(types, WkbConstants.ADJECTIVE_CT)) {
                senses = storage.importWordNetHypernymHierarchy(lemma, POS.ADJECTIVE);
            } else if (cth.isKindOf(types, WkbConstants.ADVERB_CT)) {
                senses = storage.importWordNetHypernymHierarchy(lemma, POS.ADVERB);
            }

            if (senses != null) {
                WkbUtil.joinSetType(c, types, senses);
            }
        }
    }

    private void disambiguate(CGraph fact) throws Exception {
        Hierarchy cth = memory.getStorage().getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            String lemma = c.getIndividual();
            String[] types = c.getType();

            if (cth.isKindOf(types, WkbConstants.VERB_CT)) {
                ProcList wsdProcs = memory.getLtm().getProcedural().getWsd().getVerbProcs(lemma);
                applyFirstMatch(wsdProcs, fact);
            }
        }
    }
}
