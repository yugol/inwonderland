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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.kb.Match;
import org.purl.net.wonderland.kb.Matches;
import org.purl.net.wonderland.kb.ProcUtil;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.nlg.SentenceBuilder;
import org.purl.net.wonderland.nlp.wsd.WsdProc;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level4Personality extends Level3Personality {

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
        processFactLevel4(fact);
        CGraph lowConf = memory.getStorage().getLowConfGraph();
        merge(fact, lowConf);
        return fact;
    }

    private void processFactLevel4(CGraph fact) {
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            defaultSolveIssues(c, fact);
        }
    }

    private void defaultSolveIssues(Concept c, CGraph fact) {
        Issues issues = memory.getIssues(c.getId());
        if (issues != null) {

            // find sense
            String sense = null;
            List<Issue> senseIssues = issues.getIssues(IssueSense.class);
            if (senseIssues.size() > 0) {
                List<String> conceptSenses = WkbUtil.getSenseTypes(c);
                List<String> wnSenses = memory.getDeclarative().getImportWordNetSenses(c);
                if (wnSenses != null) {
                    if (conceptSenses.size() > 0) {
                        for (String senseType : wnSenses) {
                            if (conceptSenses.contains(senseType)) {
                                sense = senseType;
                                break;
                            }
                        }
                    } else if (wnSenses.size() > 0) {
                        sense = wnSenses.get(0);
                    }
                }
            }

            // find thematic roles
            Match match = null;
            List<Issue> themrolesIssues = issues.getIssues(IssueThemrole.class);
            if (themrolesIssues.size() > 0) {
                IssueThemrole issue = (IssueThemrole) themrolesIssues.get(0);
                Matches matches = issue.getMatches();
                if (matches != null && matches.size() > 0) {
                    match = matches.get(0);
                    if (sense != null) {
                        for (Match m : matches) {
                            if (((WsdProc) m.getProcedure()).getSenseTypes().contains(sense)) {
                                match = m;
                                break;
                            }
                        }
                    }
                }
            }

            // solve issues
            if (match != null) {
                ProcUtil.applyProcMatch(fact, match, false, memory.getCth());
            }
            if (sense != null) {
                WkbUtil.setSenseType(c, sense);
            } else {
                WkbUtil.normalizeConceptType(c, memory.getCth());
            }
        }
    }

    private void merge(CGraph fact, CGraph lowConf) {
        Entities entities = memory.getEntities();
        Map<String, String> refMap = new HashMap<String, String>();
        Hierarchy cth = memory.getCth();

        // find matches
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            String conceptId = c.getId();
            Chunk ck = new Chunk(c, getCurrentFactId(), memory.getCth());
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
                Issue issue = new IssueCoreference(c, getCurrentFactId(), cth, matches);
                memory.add(issue);
            } else {
                Chunk ref = matches.get(0);
                refMap.put(conceptId, ref.getConceptId());
                reportCoreference(ck, ref);
                if (matches.size() > 1) {
                    Issue issue = new IssueCoreference(c, getCurrentFactId(), cth, matches);
                    memory.add(issue);
                }
            }
        }

        // merge fact
        conceptIterator = fact.iteratorConcept();
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

    protected void reportCoreference(Chunk ck, Chunk ref) {
        if (W.reportCorefernces) {
            SentenceBuilder sb = new SentenceBuilder();
            sb.setSubject("'" + ck.getLemma() + "'");
            sb.setVerb("reffer");
            sb.addComplement("to '" + ref.getLemma() + "'");
            report.add(sb.toString());
        }
    }
}
