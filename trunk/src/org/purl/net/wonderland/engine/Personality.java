/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, mergeWtags, publish, distribute, sublicense, and/or sell
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

import edu.stanford.nlp.trees.TypedDependency;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.didion.jwnl.data.POS;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.KbUtil;
import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.purl.net.wonderland.kb.generators.ProcManager;
import org.purl.net.wonderland.kb.generators.Procedure;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.WTaggingUtil;

/*
Bashful	Long beard	Brown top, green hat, long eyelashes
Doc	Short beard	Red tunic, brown hat, glasses
Dopey	Beardless	Green tunic, purple hat, big ears
Grumpy	Long beard	Red tunic, brown hat, scowl
Happy	Short beard	Brown top, orange headpiece, smile
Sleepy	Long beard	Green top, blue hat, heavy eyelids
Sneezy	Short beard	brown jacket, orange headpiece, red nose
 */
/**
 *
 * @author Iulian
 */
public abstract class Personality {

    protected WKnowledgeBase kb = null;
    protected ProcManager procMgr = null;

    public void setKb(WKnowledgeBase kb) {
        this.kb = kb;
        procMgr = new ProcManager(kb);
        try {
            procMgr.readAllProceduresFromKb();
        } catch (Exception ex) {
            System.err.println("Could not read generation rules from knowledge base");
            System.err.println(ex);
            Globals.exit();
        }
    }

    public abstract String getWelcomeMessage();

    public abstract String getFullName();

    public abstract String getName();

    public abstract String getId();

    public abstract String processMessage(String message) throws Exception;

    protected List<CGraph> parseMessage(String message) {
        List<CGraph> facts = new ArrayList<CGraph>();

        for (List<WTagging> sentence : Pipeline.tokenizeAndSplit(message)) {
            Object[] parse = Pipeline.parse(sentence);
            sentence = (List<WTagging>) parse[0];
            List<TypedDependency> deps = (List<TypedDependency>) parse[1];
            facts.add(buildFactGraph(sentence, deps));
        }

        return facts;
    }

    private CGraph buildFactGraph(List<WTagging> words, List<TypedDependency> deps) {
        Vocabulary vocabulary = kb.getVocabulary();
        CGraph cg = new CGraph(KbUtil.newUniqueId(), null, null, "fact");

        for (int i = 0; i < words.size(); ++i) {
            WTagging tagging = words.get(i);
            Concept c = new Concept(KbUtil.toConceptId(tagging, i + 1));
            String individualId = KbUtil.handleQuotes(tagging.getLemma());
            vocabulary.addIndividual(individualId, individualId, KbUtil.Top, kb.getLanguage());
            String[] types = null;
            if (tagging.getPos() == null) {
                types = new String[]{KbUtil.toConceptTypeId(tagging.getPennTag())};
            } else {
                types = tagging.asTypes();
            }
            c.setType(types);
            c.setIndividual(individualId);
            cg.addVertex(c);
        }

        for (int i = 0; i < deps.size(); ++i) {
            TypedDependency tdep = deps.get(i);

            String gov = getConcept(cg, KbUtil.getLabelIndex(tdep.gov().nodeString())).getId();
            String dep = getConcept(cg, KbUtil.getLabelIndex(tdep.dep().nodeString())).getId();
            String relationTypeLabel = tdep.reln().getShortName();
            String relationType = KbUtil.toRelationTypeId(relationTypeLabel);
            String relationId = KbUtil.toRelationId(relationTypeLabel, (i + 1));

            Relation r = new Relation(relationId);
            r.addType(relationType);
            cg.addVertex(r);

            cg.addEdge(dep, relationId, 1);
            cg.addEdge(gov, relationId, 2);
        }

        return cg;
    }

    private Concept getConcept(CGraph cg, int idx) {
        for (Concept c : cg.getConcepts()) {
            if (idx == KbUtil.getConceptIndex(c.getId())) {
                return c;
            }
        }
        return null;
    }

    protected void addFact(CGraph fact, String set) {
        if (set.equals(KbUtil.level1)) {
            int factCount = kb.getLevel1FactCount() + 1;
            fact.setId(KbUtil.toLevel1FactId(factCount));
            fact.setName(KbUtil.toIdIndex(factCount));
            fact.setSet(set);
            kb.addGraph(fact);
            kb.setLevel1FactCount(factCount);
        } else if (set.equals(KbUtil.level2)) {
            int factCount = kb.getLevel2FactCount() + 1;
            fact.setId(KbUtil.toLevel2FactId(factCount));
            fact.setName(KbUtil.toIdIndex(factCount));
            fact.setSet(set);
            kb.addGraph(fact);
            kb.setLevel2FactCount(factCount);
        }
    }

    protected void processSyntax(CGraph fact) throws Exception {
        List<Procedure> matches = procMgr.findMatches(KbUtil.procSyntaxSet, fact);
        for (Procedure proc : matches) {
            for (Projection proj : proc.getProjections()) {
                applyProcedure(fact, proj, proc);
            }
        }
    }

    protected void addSenses(CGraph message) {
        Hierarchy cth = kb.getVocabulary().getConceptTypeHierarchy();
        Iterator<Concept> it = message.iteratorConcept();
        while (it.hasNext()) {
            Concept c = it.next();
            String individual = c.getIndividual();
            String[] types = c.getType();
            String[] senseTypes = null;
            if (cth.isKindOf(types, KbUtil.Nn)) {
                senseTypes = kb.importWordNetHypernymHierarchy(individual, POS.NOUN);
            } else if (cth.isKindOf(types, KbUtil.Vb)) {
                senseTypes = kb.importVerbNetHierarchy(individual);
                if (senseTypes == null) {
                    senseTypes = kb.importWordNetHypernymHierarchy(individual, POS.VERB);
                }
            } else if (cth.isKindOf(types, KbUtil.Jj)) {
                senseTypes = kb.importWordNetHypernymHierarchy(individual, POS.ADJECTIVE);
            } else if (cth.isKindOf(types, KbUtil.Rb)) {
                senseTypes = kb.importWordNetHypernymHierarchy(individual, POS.ADVERB);
            }
            if (senseTypes != null) {
                String[] allTypes = new String[types.length + senseTypes.length];
                System.arraycopy(types, 0, allTypes, 0, types.length);
                System.arraycopy(senseTypes, 0, allTypes, types.length, senseTypes.length);
                c.setType(allTypes);
            }
        }
    }

    private void applyProcedure(CGraph fact, Projection proj, Procedure proc) {
        Set<Concept> insert = new HashSet<Concept>();
        Set<Concept> delete = new HashSet<Concept>();
        Map<Concept, Concept> update = new Hashtable<Concept, Concept>();

        Iterator<Concept> cit = proc.getLhs().iteratorConcept();
        while (cit.hasNext()) {
            Concept lhs = cit.next();
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            delete.add(actual);
        }

        cit = proc.getRhs().iteratorConcept();
        while (cit.hasNext()) {
            Concept rhs = cit.next();
            Concept lhs = proc.getRhsLhsConceptMap().get(rhs);
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            if (actual != null) {
                update.put(actual, rhs);
                delete.remove(actual);
            } else {
                insert.add(rhs);
            }
        }

        for (Concept c : delete) {
            List<Relation> from = new ArrayList<Relation>();
            List<Relation> to = new ArrayList<Relation>();
            List<Concept> toConcepts = new ArrayList<Concept>();

            Iterator<CREdge> eit = fact.iteratorEdge(c.getId());
            while (eit.hasNext()) {
                CREdge edge = eit.next();
                Relation r = fact.getRelation(edge);
                int numOrder = edge.getNumOrder();
                if (numOrder == 1) {
                    to.add(r);
                    Iterator<CREdge> it = fact.iteratorEdge(r.getId());
                    while (it.hasNext()) {
                        CREdge e = it.next();
                        if (e.getNumOrder() != 1) {
                            toConcepts.add(fact.getConcept(e));
                        }
                    }
                } else {
                    from.add(r);
                }
            }

            WTagging cTagging = kb.conceptLabelsToWTagging(c, false);
            for (Concept toConcept : toConcepts) {
                WTagging toTagging = kb.conceptLabelsToWTagging(toConcept, false);
                WTaggingUtil.mergeWtags(cTagging, toTagging);
                toConcept.setType(toTagging.asTypes());
            }
            fact.removeVertex(c.getId());

            for (Relation r : to) {
                fact.removeVertex(r.getId());
            }

            for (Relation r : from) {
                for (Concept toConcept : toConcepts) {
                    fact.addEdge(toConcept.getId(), r.getId(), 2);
                }
            }
        }

        for (Concept c : update.keySet()) {
            Concept rhs = update.get(c);
            WTagging cTagging = kb.conceptLabelsToWTagging(c, false);
            WTagging rhsTagging = kb.conceptLabelsToWTagging(rhs, false);
            WTaggingUtil.mergeWtags(rhsTagging, cTagging);
            c.setType(cTagging.asTypes());
        }

    }
}