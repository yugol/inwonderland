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
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.KbUtil;
import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.purl.net.wonderland.kb.generators.Procedure;
import org.purl.net.wonderland.kb.generators.ProcManager;

/**
 *
 * @author Iulian
 */
public class EtoGleem extends Personality {

    private ProcManager genRuleMgr;

    @Override
    public String getWelcomeMessage() {
        StringBuffer sb = new StringBuffer();
        sb.append("Hi!\n"
                + "Welcome to Wanderland, the place where meaning has no boundary!\n"
                + "We are glad that you are interested in our world.\n"
                + "I will be your facilitator. My name is ");
        sb.append(getFullName());
        sb.append(" but you can call me ");
        sb.append(getName());
        sb.append(".\nIs there anything you would preffer us to talk about?");
        return sb.toString();
    }

    @Override
    public String getFullName() {
        return "Eto Gleem";
    }

    @Override
    public String getName() {
        return "Eto";
    }

    @Override
    public String getId() {
        return "etogleem";
    }

    @Override
    public void setKb(WKnowledgeBase kb) {
        super.setKb(kb);
        genRuleMgr = new ProcManager(kb);
        try {
            genRuleMgr.readGenerators(KbUtil.level1);
        } catch (Exception ex) {
            System.err.println("Could not read generation rules from knowledge base");
            System.err.println(ex);
            Globals.exit();
        }
    }

    @Override
    public String processMessages(List<CGraph> messages) throws Exception {
        for (CGraph message : messages) {
            findMeaning(message);
            List<Procedure> rules = genRuleMgr.findMatches(KbUtil.level1, message);
            for (Procedure rule : rules) {
                List<Projection> matches = rule.getProjections();
                for (Projection match : matches) {
                    CGraph fact = extractFact(rule, match);
                    apply(fact);
                }
            }
        }
        return "Done.";
    }

    private CGraph extractFact(Procedure rule, Projection match) {
        CGraph fact = new CGraph(KbUtil.newUniqueId(), null, null, "fact");

        CGraph rhs = rule.getRhs();
        Map<String, String> idMap = new Hashtable<String, String>();

        for (Concept rhsConcept : rhs.getConcepts()) {
            Concept lhsConcept = rule.getRhsLhsConceptMap().get(rhsConcept);
            Concept matchConcept = (Concept) match.getTarget(lhsConcept.getId());
            String[] type = matchConcept.getType();
            String individual = matchConcept.getIndividual();
            // TODO: import WordNet hierarchy
            Concept c = new Concept(KbUtil.newUniqueId());
            c.setType(type);
            c.setIndividual(individual);
            fact.addVertex(c);
            idMap.put(rhsConcept.getId(), c.getId());
        }

        for (Relation rhsRelation : rhs.getRelations()) {
            Relation r = new Relation(KbUtil.newUniqueId());
            r.setType(rhsRelation.getType());
            fact.addVertex(r);
            idMap.put(rhsRelation.getId(), r.getId());
        }

        for (CREdge rhsEdge : rhs.getEdges()) {
            String conceptId = idMap.get(rhs.getConcept(rhsEdge).getId());
            String relationId = idMap.get(rhs.getRelation(rhsEdge).getId());
            int numOrder = rhsEdge.getNumOrder();
            fact.addEdge(conceptId, relationId, numOrder);
        }

        return fact;
    }

    private void apply(CGraph fact) {
        int factId = kb.getLevel2FactCount() + 1;
        fact.setId(KbUtil.toLevel2FactId(factId));
        fact.setName(KbUtil.toIdIndex(factId));
        fact.setSet(KbUtil.level2);
        kb.addGraph(fact);
        kb.setLevel2FactCount(factId);
    }

    protected void findMeaning(CGraph message) {
        Hierarchy cth = kb.getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> it = message.iteratorConcept();
        while (it.hasNext()) {
            Concept c = it.next();
            String individual = c.getIndividual();
            String[] types = c.getType();
            String[] moreTypes = null;
            if (cth.isKindOf(types, KbUtil.Nn)) {
                moreTypes = kb.importWordNetHypernymHierarchy(individual, POS.NOUN);
            } else if (cth.isKindOf(types, KbUtil.Vb)) {
                moreTypes = kb.importVerbNetHierarchy(individual);
                if (moreTypes == null) {
                    moreTypes = kb.importWordNetHypernymHierarchy(individual, POS.VERB);
                }
            } else if (cth.isKindOf(types, KbUtil.Jj)) {
                moreTypes = kb.importWordNetHypernymHierarchy(individual, POS.ADJECTIVE);
            } else if (cth.isKindOf(types, KbUtil.Rb)) {
                moreTypes = kb.importWordNetHypernymHierarchy(individual, POS.ADVERB);
            }
            if (moreTypes != null) {
                String[] allTypes = new String[types.length + moreTypes.length];
                System.arraycopy(types, 0, allTypes, 0, types.length);
                System.arraycopy(moreTypes, 0, allTypes, types.length, moreTypes.length);
                c.setType(allTypes);
            }
        }
    }
}
