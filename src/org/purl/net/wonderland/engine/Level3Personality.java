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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.purl.net.wonderland.kb.ProcList;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.nlg.SentenceBuilder;
import org.purl.net.wonderland.nlp.resources.SynsetWrapper;
import org.purl.net.wonderland.nlp.resources.WordNetWrapper;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level3Personality extends Level2Personality {

    @Override
    public String getWelcomeMessage() {
        return "disambiguation, semantic role identification";
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
    protected void handleFact(CGraph fact) throws Exception {
        super.handleFact(fact);
        fact = WkbUtil.duplicate(fact);
        processFactLevel3(fact);
        memory.getStorage().addFact(fact, WkbConstants.LEVEL3);
    }

    @Override
    protected void postProcessFacts() throws Exception {
    }

    protected void processFactLevel3(CGraph fact) throws Exception {
        processFactLevel2(fact);
        loadWordNetSenses(fact);
        inferThematicRolesTypes(fact);
        cleanConceptTypes(fact);
        projSlv.reset();
        disambiguate(fact);
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
            WkbUtil.normalizeConceptType(c, cth);
            checkSenses(c, cth);
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

    private void checkSenses(Concept c, Hierarchy cth) {
        List<String> senses = new ArrayList<String>();
        for (String type : c.getType()) {
            if (cth.isKindOf(type, WkbConstants.WN_SENSE_CT)) {
                senses.add(type);
            }
        }

        Issue issue = null;
        switch (senses.size()) {
            case 0:
                issue = new Issue(c.getId(), getCurrentFactId(), Issue.Type.NO_SENSE_AMBIGUITY);
                memory.add(issue);
                break;

            case 1:
                SynsetWrapper sense = new SynsetWrapper(WordNetWrapper.lookup(WkbUtil.toConceptType(senses.get(0))));
                SentenceBuilder sb = new SentenceBuilder();
                sb.setSubject(c.getIndividual());
                String ex = sense.getFirstExplanation();
                if (ex != null) {
                    sb.setVerb("be");
                    sb.addComplement(ex);
                } else {
                    sb.setVerb("as in");
                    sb.addComplement(ex);
                }
                report.add(sb.toString());
                break;

            default:
                issue = new Issue(c.getId(), getCurrentFactId(), Issue.Type.SENSE_AMBIGUITY);
                memory.add(issue);
        }
    }
}
