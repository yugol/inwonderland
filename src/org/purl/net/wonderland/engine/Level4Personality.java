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
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level4Personality extends Level3Personality {

    private List<Chunk> factChunks = new ArrayList<Chunk>();

    @Override
    public String getWelcomeMessage() {
        return "combine facts";
    }

    @Override
    public String getFullName() {
        return "Level 4";
    }

    @Override
    public String getName() {
        return "(test) L4";
    }

    @Override
    public String getId() {
        return WkbConstants.LEVEL4;
    }

    @Override
    protected CGraph handleFact(CGraph fact) throws Exception {
        fact = super.handleFact(fact);
        fact = WkbUtil.duplicate(fact);
        factChunks.clear();
        processFactLevel4(fact);
        CGraph lowConf = memory.getStorage().getLowConfGraph();
        merge(fact, lowConf);
        return fact;
    }

    private void processFactLevel4(CGraph fact) {
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            disambiguateToFirstSense(c);
            Chunk ck = new Chunk(c, memory.getCth());
            factChunks.add(ck);
        }
    }

    private void disambiguateToFirstSense(Concept c) {
        List<Issue> issues = memory.getSenseIssues(c.getId());
        if (issues != null) {
            List<String> senses = memory.getDeclarative().getImportWordNetSenses(c);
            if (senses != null) {
                WkbUtil.setSense(c, memory.getCth(), senses.get(0));
                reportSense(c);
            }
        }
    }

    private void merge(CGraph fact, CGraph lowConf) {
        Entities entities = memory.getEntities();
        Map<String, String> refMap = new HashMap<String, String>();

        // find matches
        Hierarchy cth = memory.getCth();
        for (Chunk ck : factChunks) {
            String conceptId = ck.getId();
            List<Chunk> matches = null;

            if (cth.isKindOf(ck.getPartOfSpeech(), WkbConstants.PROPERNOUN_CT)) {
                matches = entities.findMatchesProperNoun(ck);
                if (matches == null) {
                    entities.add(ck);
                }
            } else if (cth.isKindOf(ck.getPartOfSpeech(), WkbConstants.NOUN_CT)) {
                entities.add(ck);
            } else if (cth.isKindOf(ck.getPartOfSpeech(), WkbConstants.PRONOUN_CT)) {
                matches = entities.findMatchesPronoun(ck);
            } else if (cth.isKindOf(ck.getPartOfSpeech(), WkbConstants.POSSESIVEADJECIVE_CT)) {
                matches = entities.findMatchesPronoun(ck);
            } else {
                refMap.put(conceptId, conceptId);
                continue;
            }

            if (matches == null) {
                refMap.put(conceptId, conceptId);
                Issue issue = new IssueCoreference(ck.getId(), getCurrentFactId(), matches);
                memory.add(issue);
            } else {
                refMap.put(conceptId, matches.get(0).getId());
                if (matches.size() > 1) {
                    Issue issue = new IssueCoreference(ck.getId(), getCurrentFactId(), matches);
                    memory.add(issue);
                }
            }
        }

        // merge fact
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept factConcept = conceptIterator.next();
            String refId = refMap.get(factConcept.getId());
            Concept refConcept = lowConf.getConcept(refId);
            if (refConcept == null) {
                refConcept = fact.getConcept(refId);
                Concept c = new Concept(refId);
                c.setType(refConcept.getType());
                c.setIndividual(refConcept.getIndividual());
                lowConf.addVertex(refConcept);
            }
        }

        Iterator<Relation> relationIterator = fact.iteratorRelation();
        while (relationIterator.hasNext()) {
            Relation factRelation = relationIterator.next();
            String relationId = factRelation.getId();
            Relation r = new Relation(relationId);
            r.setType(factRelation.getType());
            lowConf.addVertex(r);

            Iterator<CREdge> edgeIterator = fact.iteratorEdge(relationId);
            while (edgeIterator.hasNext()) {
                CREdge edge = edgeIterator.next();
                String refId = refMap.get(fact.getConcept(edge).getId());
                lowConf.addEdge(refId, relationId, edge.getNumOrder());
            }
        }
    }
}
