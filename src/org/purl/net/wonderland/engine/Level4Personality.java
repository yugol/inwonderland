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
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.purl.net.wonderland.kb.Match;
import org.purl.net.wonderland.kb.Matches;
import org.purl.net.wonderland.kb.ProcUtil;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.wsd.WsdProc;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Level4Personality extends Level3Personality {

    @Override
    public String getWelcomeMessage() {
        return "user input interpretation";
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

        FactNature nature = findNature(fact);
        if (nature == FactNature.QUESTION) {
            processQuestion(fact);
            memory.getStorage().addFact(fact, getCurrentFactId(), WkbConstants.LEVEL4);
            answerQuestion(fact);
            return null;
        }

        return fact;
    }

    private void processQuestion(CGraph fact) throws Exception {
        applyAllNonOverlappingMatches(memory.getProcedural().getQuick().getProcSet(WkbUtil.PROC_SET_QUESTION), fact);
        cleanQuestion(fact);
    }

    private void cleanQuestion(CGraph fact) {
        List<Concept> conceptsToDelete = new ArrayList<Concept>();
        Hierarchy cth = memory.getCth();
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            Iterator<CREdge> links = fact.iteratorEdge(c.getId());
            if (!links.hasNext()) {
                cth.isKindOf(c.getType(), WkbConstants.INTERROGATIVEPUNCTUATION_CT);
                conceptsToDelete.add(c);
            }
            c.setIndividual(null);
            List<String> senseTypes = WkbUtil.getSenseTypes(c);
            if (senseTypes.size() == 0) {
                senseTypes.add(WkbConstants.LINKARG_CT);
            }
            c.setType(senseTypes.toArray(new String[senseTypes.size()]));
        }
        for (Concept c : conceptsToDelete) {
            fact.removeVertex(c.getId());
        }
    }

    private void answerQuestion(CGraph fact) throws Exception {
        List<Projection> matches = projSlv.findMatches(fact, memory.getStorage().getLowConfGraph());
        if (matches.size() > 0) {
            List<Concept> wildcards = getWildcards(fact);
            for (Projection match : matches) {
                for (Concept c : wildcards) {
                    Concept answer = (Concept) match.getTarget(c.getId());
                    report.add(answer.getIndividual());
                }
            }
        } else {
            report.add("No answer was found to that question.");
        }
    }

    private List<Concept> getWildcards(CGraph fact) {
        List<Concept> list = new ArrayList<Concept>();
        Iterator<Concept> conceptIterator = fact.iteratorConcept();
        while (conceptIterator.hasNext()) {
            Concept c = conceptIterator.next();
            List<String> type = Arrays.asList(c.getType());
            if (type.contains(WkbConstants.LINKARG_CT)) {
                list.add(c);
            }
        }
        return list;
    }

    protected void processFactLevel4(CGraph fact) {
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
}
