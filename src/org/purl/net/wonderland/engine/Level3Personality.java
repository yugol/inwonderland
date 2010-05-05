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
import org.purl.net.wonderland.kb.ProcList;
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
        loadWordNetSenses(fact);
        inferThematicRolesTypes(fact);
        cleanConceptTypes(fact);
        projSlv.reset();
        disambiguate(fact);
        memory.getStorage().addFact(fact, WkbConstants.LEVEL3);
    }

    private void loadWordNetSenses(CGraph fact) {
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            List<String> senses = memory.getDeclarative().getImportWordNetSenses(c);
            if (senses != null) {
                for (String type : senses) {
                    c.addType(type);
                }
            }
        }
    }

    private void inferThematicRolesTypes(CGraph fact) {
        Hierarchy cth = memory.getStorage().getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            String[] types = c.getType();
            if (cth.isKindOf(types, WkbConstants.NOUN_CT)
                    || cth.isKindOf(types, WkbConstants.ADJECTIVE_CT)) {
                List<String> trts = memory.getDeclarative().deriveThematicRolesTypes(types);
                for (String type : trts) {
                    c.addType(type);
                }
            }
        }
    }

    private void cleanConceptTypes(CGraph fact) {
        Hierarchy cth = memory.getStorage().getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            WkbUtil.normalizeConcept(c, cth);
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
                ProcList wsdProcs = memory.getProcedural().getWsd().getVerbProcs(lemma);
                applyFirstMatch(wsdProcs, fact);
            }
        }
    }
}
