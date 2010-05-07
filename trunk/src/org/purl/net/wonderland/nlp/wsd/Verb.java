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
package org.purl.net.wonderland.nlp.wsd;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.purl.net.wonderland.W;
import org.purl.net.wonderland.WonderlandException;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbUtil;
import org.purl.net.wonderland.nlp.wsd.PbExample.RoleData;
import org.purl.net.wonderland.util.IdUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class Verb {

    private final String lemma;
    private List<VnClass> vnClases = null;

    public Verb(String lemma) throws Exception {
        this.lemma = lemma;
    }

    public List<VnClass> getVnClases() {
        if (vnClases == null) {
            vnClases = VnClass.makeClassesFor(lemma);
        }
        return vnClases;
    }

    public List<Rule> buildProcRules() {
        List<Rule> rules = new ArrayList<Rule>();
        for (VnClass vnClass : vnClases) {
            for (VnFrame vnFrame : vnClass.getFrames()) {
                for (VnExample example : vnFrame.getExamples()) {
                    System.out.println("");
                    System.out.println("***************");
                    System.out.println(vnClass.getId());
                    System.out.println("***************");
                    example.makeSense(vnClass.getMembers());
                }
            }
        }
        return rules;
    }

    public List<Rule> getVerbNetProcs(WsdPersonality pers) {
        Hierarchy cth = pers.getKb().getVocabulary().getConceptTypeHierarchy();
        List<Rule> procs = new ArrayList<Rule>();
        for (PbRoleset roleset : new ArrayList<PbRoleset>()) {
            for (PbExample example : roleset.getExamples()) {
                String text = null;
                try {
                    if (example.getType() == PbExample.Type.VerbNet) {
                        text = example.getText();
                        CGraph cg = pers.parse(text);
                        Rule proc = new Rule(IdUtil.newId(), WkbUtil.toProcName(lemma, IdUtil.newId()));


                        // find VERB
                        Concept verb = null;
                        List<Concept> eligible = new ArrayList<Concept>();
                        Iterator<Concept> cit = cg.iteratorConcept();
                        String verbLemma = example.getVerbLemma();
                        while (cit.hasNext()) {
                            Concept c = cit.next();
                            if (cth.isKindOf(c.getType(), WkbConstants.VERB_CT)) {
                                String individual = c.getIndividual();
                                if (individual.equals(verbLemma)) {
                                    verb = c;
                                    break;
                                }
                                if (roleset.getVnClasses().get(0).getMembers().contains(individual)) {
                                    eligible.add(c);
                                }
                            }
                        }
                        if (verb == null) {
                            if (eligible.size() == 1) {
                                verb = eligible.get(0);
                            } else {
                                throw new WonderlandException("verb not found");
                            }
                        }


                        // create VERB couple
                        Concept[] vHyptConc = createVerbConceptHyptConc(pers.getKb(), proc, roleset);


                        // get context
                        Iterator<String> rit = cg.iteratorAdjacents(verb.getId());
                        while (rit.hasNext()) {
                            Relation dep = cg.getRelation(rit.next());

                            List<Concept> connectedConcepts = getConnectedConcepts(cg, verb, dep);

                            example.resetRoleHits();
                            for (Concept connectedConcept : connectedConcepts) {
                                String individual = connectedConcept.getIndividual();
                                Map<VerbRole, String> args = example.getArgs();
                                for (VerbRole role : args.keySet()) {
                                    String roleText = args.get(role);
                                    if (roleText.indexOf(individual) >= 0) {
                                        role.incrementHits();
                                    }
                                }
                            }

                            VerbRole role = example.getBestRole();
                            if (role != null) {
                                RoleData roleData = example.getRoleData(role);

                                Relation[] rHyptConc = createRelationHyptConc(proc, role, dep);

                                Iterator<CREdge> eit = cg.iteratorEdge(dep.getId());
                                while (eit.hasNext()) {
                                    CREdge edge = eit.next();
                                    int numOrder = edge.getNumOrder();
                                    Concept c = cg.getConcept(edge);
                                    if (c == verb) {
                                        proc.addEdge(vHyptConc[0].getId(), rHyptConc[0].getId(), numOrder);
                                        proc.addEdge(vHyptConc[1].getId(), rHyptConc[1].getId(), 1);
                                    } else {
                                        if (roleData.getPrep() == null) {
                                            Concept[] cHyptConc = createConceptHyptConc(proc);
                                            proc.addEdge(cHyptConc[0].getId(), rHyptConc[0].getId(), numOrder);
                                            proc.addEdge(cHyptConc[1].getId(), rHyptConc[1].getId(), 2);
                                            proc.addCouple(cHyptConc[0].getId(), cHyptConc[1].getId());
                                        } else {
                                            Concept cHypt = new Concept(IdUtil.newId());
                                            cHypt.setType(WkbConstants.ADPOSITION_CT);
                                            cHypt.setIndividual(c.getIndividual());
                                            cHypt.setHypothesis(true);
                                            proc.addVertex(cHypt);
                                            proc.addEdge(cHypt.getId(), rHyptConc[0].getId(), numOrder);

                                            // add level 2
                                            Iterator<String> rit2 = cg.iteratorAdjacents(c.getId());
                                            while (rit2.hasNext()) {
                                                Relation dep2 = cg.getRelation(rit2.next());
                                                if (dep2 != dep) {

                                                    Relation rHypt = new Relation(IdUtil.newId());
                                                    rHypt.setType(dep2.getType());
                                                    rHypt.setHypothesis(true);
                                                    proc.addVertex(rHypt);

                                                    Iterator<CREdge> eit2 = cg.iteratorEdge(dep2.getId());
                                                    while (eit2.hasNext()) {
                                                        CREdge edge2 = eit2.next();
                                                        int numOrder2 = edge2.getNumOrder();
                                                        Concept c2 = cg.getConcept(edge2);
                                                        if (c2 == c) {
                                                            proc.addEdge(cHypt.getId(), rHypt.getId(), numOrder2);
                                                        } else {
                                                            Concept[] cHyptConc2 = createConceptHyptConc(proc);
                                                            proc.addEdge(cHyptConc2[0].getId(), rHypt.getId(), numOrder);
                                                            proc.addEdge(cHyptConc2[1].getId(), rHyptConc[1].getId(), 2);
                                                            proc.addCouple(cHyptConc2[0].getId(), cHyptConc2[1].getId());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }


                        // done
                        procs.add(proc);
                    }

                } catch (Exception ex) {
                    System.err.println(text);
                    W.reportExceptionConsole(ex);
                }
            }
        }
        return procs;
    }

    private Concept[] createVerbConceptHyptConc(Wkb kb, Rule proc, PbRoleset frame) {
        Concept hypt, conc;

        hypt = new Concept(IdUtil.newId());
        hypt.setIndividual(kb.addIndividual(lemma));
        hypt.setType(WkbConstants.VERB_CT);
        hypt.setHypothesis(true);
        hypt.setIndividual(kb.addIndividual(lemma));
        proc.addVertex(hypt);

        conc = new Concept(IdUtil.newId());
        conc.setType(WkbConstants.PROCOP_ADD_CT);
        for (String sense : frame.getVnClasses().get(0).getWnSenses()) {
            String ctId = kb.addConceptType(sense, null);
            conc.addType(ctId);
        }
        conc.setConclusion(true);
        proc.addVertex(conc);

        proc.addCouple(hypt.getId(), conc.getId());

        Concept[] hyptConc = new Concept[2];
        hyptConc[0] = hypt;
        hyptConc[1] = conc;
        return hyptConc;
    }

    private Concept[] createConceptHyptConc(Rule proc) {
        Concept hypt, conc;

        hypt = new Concept(IdUtil.newId());
        hypt.setType(WkbConstants.LINKARG_CT);
        hypt.setHypothesis(true);
        proc.addVertex(hypt);

        conc = new Concept(IdUtil.newId());
        conc.setType(WkbConstants.PROCOP_KEEP_CT);
        conc.setConclusion(true);
        proc.addVertex(conc);

        Concept[] hyptConc = new Concept[2];
        hyptConc[0] = hypt;
        hyptConc[1] = conc;
        return hyptConc;
    }

    private Relation[] createRelationHyptConc(Rule proc, VerbRole role, Relation prototype) {
        Relation hypt, conc;

        hypt = new Relation(IdUtil.newId());
        hypt.setType(prototype.getType());
        hypt.setHypothesis(true);
        proc.addVertex(hypt);

        conc = new Relation(IdUtil.newId());
        conc.setType(WkbUtil.toRelationTypeId(role.getVnType()));
        conc.setConclusion(true);
        proc.addVertex(conc);

        Relation[] hyptConc = new Relation[2];
        hyptConc[0] = hypt;
        hyptConc[1] = conc;
        return hyptConc;
    }

    private List<Concept> getConnectedConcepts(CGraph cg, Concept start, Relation direction) {
        List<Concept> cAdjacents = new ArrayList<Concept>();

        start.setConclusion(true);
        direction.setConclusion(true);

        Queue<String> bfs = new LinkedList<String>();
        bfs.add(direction.getId());

        while (bfs.peek() != null) {
            Iterator<String> it = cg.iteratorAdjacents(bfs.remove());
            while (it.hasNext()) {
                String id = it.next();
                Concept c = cg.getConcept(id);
                if (c != null) {
                    if (c.isHypothesis()) {
                        c.setConclusion(true);
                        bfs.add(c.getId());
                        cAdjacents.add(c);
                    }
                } else {
                    Relation r = cg.getRelation(id);
                    if (r.isHypothesis()) {
                        r.setConclusion(true);
                        bfs.add(r.getId());
                    }
                }
            }
        }

        WkbUtil.setAllConclusion(cg, false);
        return cAdjacents;
    }

    String getLemma() {
        return lemma;
    }
}
