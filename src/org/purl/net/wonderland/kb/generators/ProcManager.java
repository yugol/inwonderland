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
package org.purl.net.wonderland.kb.generators;

import org.purl.net.wonderland.kb.WKnowledgeBase;
import org.purl.net.wonderland.kb.KbUtil;
import aminePlatform.kernel.lexicons.Identifier;
import aminePlatform.util.cg.CG;
import edu.stanford.nlp.util.StringUtils;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import fr.lirmm.rcr.cogui2.kernel.model.Vocabulary;
import fr.lirmm.rcr.cogui2.kernel.solver.SolverCogitant;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.purl.net.wonderland.util.CodeTimer;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcManager {

    private Map<String, List<Procedure>> procs = new Hashtable<String, List<Procedure>>();
    private final SolverCogitant solver = new SolverCogitant();
    private final WKnowledgeBase kb;

    public int getProcCount() {
        int count = 0;
        for (List<Procedure> set : procs.values()) {
            count += set.size();
        }
        return count;
    }

    private void addProcedure(String group, Procedure t) {
        if (!procs.containsKey(group)) {
            procs.put(group, new ArrayList<Procedure>());
        }
        procs.get(group).add(t);
    }

    public ProcManager(WKnowledgeBase kb) {
        this.kb = kb;
    }

    public void readAllProceduresFromKb() throws Exception {
        readProcedureSet(KbUtil.procSetTenses);
        readProcedureSet(KbUtil.procSetCollocations);
    }

    private void readProcedureSet(String set) throws Exception {
        String setId = KbUtil.proc + "_" + set + "_";
        List<Rule> rules = kb.getProcRules(setId);
        for (Rule rule : rules) {
            String name = rule.getName().substring(setId.length());
            Procedure proc = buildProcedure(rule, name);
            addProcedure(set, proc);
        }
        sortProcedureSet(set);
    }

    private Procedure buildProcedure(Rule rule, String name) {
        CGraph lhs = new CGraph(UUID.randomUUID().toString(), name, "lhs", "fact");
        CGraph rhs = new CGraph(UUID.randomUUID().toString(), name, "rhs", "fact");

        Iterator<Concept> cit = rule.iteratorConcept();
        while (cit.hasNext()) {
            Concept c = cit.next();
            if (c.isHypothesis()) {
                lhs.addVertex(c);
            } else {
                rhs.addVertex(c);
            }
        }

        Iterator<Relation> rit = rule.iteratorRelation();
        while (rit.hasNext()) {
            Relation r = rit.next();
            if (r.isHypothesis()) {
                lhs.addVertex(r);
            } else {
                rhs.addVertex(r);
            }
        }

        Iterator<CREdge> eit = rule.iteratorEdge();
        while (eit.hasNext()) {
            CREdge e = eit.next();
            Concept c = rule.getConcept(e);
            Relation r = rule.getRelation(e);
            if (c.isHypothesis()) {
                lhs.addEdge(c.getId(), r.getId(), e.getNumOrder());
            } else {
                rhs.addEdge(c.getId(), r.getId(), e.getNumOrder());
            }
        }

        Map<Concept, Concept> rhsLhsMap = new Hashtable<Concept, Concept>();
        Iterator<String> it = rule.iteratorHypothesisFrontier();
        while (it.hasNext()) {
            String id = it.next();
            // System.out.println(id);
            Concept l = lhs.getConcept(id);
            Concept r = rhs.getConcept(rule.getConclusionOf(id));
            rhsLhsMap.put(r, l);
        }

        Procedure proc = new ProcImpl(lhs, rhs, rhsLhsMap);
        return proc;
    }

    public void readProcedures(File file) throws Exception {
        ProcParser parser = new ProcParser();
        Vocabulary voc = kb.getVocabulary();
        Iterator<String> r = voc.getRelationTypeHierarchy().iteratorVertex();
        while (r.hasNext()) {
            String rt = r.next();
            parser.addRelationType(voc.getRelationTypeLabel(rt, kb.getLanguage()));
        }
        parser.parse(file);
        for (int i = 0; i < parser.getNameList().size(); ++i) {
            Procedure proc = buildProcedures(parser, parser.getNameList().get(i), parser.getLhsList().get(i), parser.getRhsList().get(i));
            addProcedure("amine", proc);
        }
    }

    private Procedure buildProcedures(ProcParser parser, String name, CG lhsa, CG rhsa) {
        CGraph lhsc = new CGraph(UUID.randomUUID().toString(), name, "lhs", "fact");
        Map<aminePlatform.util.cg.Concept, Concept> lhsMap = mapGraph(lhsa, lhsc, parser);
        CGraph rhsc = new CGraph(UUID.randomUUID().toString(), name, "rhs", "fact");
        Map<aminePlatform.util.cg.Concept, Concept> rhsMap = mapGraph(rhsa, rhsc, parser);

        Map<Concept, Concept> rhsLhsMap = new Hashtable<Concept, Concept>();
        for (aminePlatform.util.cg.Concept lac : lhsMap.keySet()) {
            // System.out.println(parser.getTypeName(lac.getType()));
            Object lObj = lac.getDescriptor();
            if (lObj != null) {
                Concept lcc = lhsMap.get(lac);
                String[] lDesc = getDesc(((Identifier) lObj).getName());
                if (lDesc[0] != null) {
                    // System.out.println("individual: " + lDesc[0]);
                    lcc.setIndividual(lDesc[0]);
                }
                if (lDesc[1] != null) {
                    // System.out.println("var: " + lDesc[1]);
                    for (aminePlatform.util.cg.Concept rac : rhsMap.keySet()) {
                        Object rObj = rac.getDescriptor();
                        if (rObj != null) {
                            String[] rDesc = getDesc(((Identifier) rObj).getName());
                            if (lDesc[1].equals(rDesc[1])) {
                                Concept rcc = rhsMap.get(rac);
                                rhsLhsMap.put(rcc, lcc);
                                System.out.println("Added map: " + StringUtils.join(lcc.getType(), ";") + " : " + lcc.getIndividual() + " -> " + StringUtils.join(rcc.getType(), ";") + " : " + rcc.getIndividual());
                            }
                        }
                    }
                }
            }
        }
        for (aminePlatform.util.cg.Concept rac : rhsMap.keySet()) {
            Object rObj = rac.getDescriptor();
            if (rObj != null) {
                String[] rDesc = getDesc(((Identifier) rObj).getName());
                if (rDesc[0] != null) {
                    Concept rcc = rhsMap.get(rac);
                    rcc.setIndividual(rDesc[0]);
                }
            }
        }

        Procedure proc = new ProcImpl(lhsc, rhsc, rhsLhsMap);
        return proc;
    }

    private String[] getDesc(String str) {
        String[] desc = new String[2];
        int varpos = str.lastIndexOf("var_");
        if (varpos == 0) {
            desc[1] = str.substring(4);
        } else if (varpos < 0) {
            desc[0] = str;
        } else {
            desc[0] = str.substring(0, varpos - 1);
            desc[1] = str.substring(varpos + 4);
        }
        return desc;
    }

    private Map<aminePlatform.util.cg.Concept, Concept> mapGraph(CG amineCG, CGraph cogitantCG, ProcParser parser) {
        Map<aminePlatform.util.cg.Concept, Concept> acMap = new Hashtable<aminePlatform.util.cg.Concept, Concept>();
        if (amineCG != null) {
            Enumeration it = amineCG.getConcepts();
            while (it.hasMoreElements()) {
                aminePlatform.util.cg.Concept ac = (aminePlatform.util.cg.Concept) it.nextElement();
                String type = parser.getTypeName(ac.getType());
                if (type.indexOf("ct_") == 0) {
                    type = type.substring(3);
                }
                String[] types = type.split("__");
                System.out.print(StringUtils.join(types, ";") + ": ");
                System.out.println(ac.getDescriptor());
                Concept cc = new Concept(UUID.randomUUID().toString());
                for (int i = 0; i < types.length; ++i) {
                    types[i] = KbUtil.toConceptTypeId(types[i]);
                }
                cc.setType(types);
                cogitantCG.addVertex(cc);
                acMap.put(ac, cc);
            }
            it = amineCG.getRelations();
            while (it.hasMoreElements()) {
                aminePlatform.util.cg.Relation ar = (aminePlatform.util.cg.Relation) it.nextElement();
                String type = parser.getTypeName(ar.getType());
                // System.out.println(typees);
                Relation cr = new Relation(UUID.randomUUID().toString());
                cr.setType(KbUtil.toRelationTypeId(type));
                cogitantCG.addVertex(cr);
                cogitantCG.addEdge(acMap.get(ar.getSourceConcept()).getId(), cr.getId(), 1);
                cogitantCG.addEdge(acMap.get(ar.getTargetConcept()).getId(), cr.getId(), 1);
            }
        }
        System.out.println("");
        return acMap;
    }

    public Procedure findMatch(String procSet, CGraph cg, Set<Procedure> exclude) throws Exception {
        CodeTimer timer = new CodeTimer("projection");
        reConnectToSolver();

        Procedure match = null;
        if (procs.get(procSet) != null) {
            for (Procedure proc : procs.get(procSet)) {
                if (exclude != null && exclude.contains(proc)) {
                    continue;
                }
                CGraph lhs = proc.getLhs();
                List<Projection> projections = solver.getProjections(lhs, cg);
                if (projections.size() > 0) {
                    proc.setProjections(projections);
                    match = proc;
                    break;
                } else {
                    proc.setProjections(null);
                    if (exclude != null && exclude.contains(proc)) {
                        exclude.add(proc);
                    }
                }
            }
        }

        timer.stop();
        return match;
    }

    public List<Procedure> findMatches(String procSet, CGraph cg) throws Exception {
        // CodeTimer timer = new CodeTimer("projection");
        reConnectToSolver();

        List<Procedure> matches = new ArrayList<Procedure>();
        if (procs.get(procSet) != null) {
            for (Procedure proc : procs.get(procSet)) {
                CGraph lhs = proc.getLhs();
                List<Projection> projections = solver.getProjections(lhs, cg);
                if (projections.size() > 0) {
                    proc.setProjections(projections);
                    matches.add(proc);
                } else {
                    proc.setProjections(null);
                }
            }
        }

        // timer.stop();
        return matches;
    }

    public List<Procedure> findMatches(String set, String id) throws Exception {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    private void reConnectToSolver() throws Exception {
        if (solver.isConnected()) {
            solver.disconnect();
        }
        solver.connect();
        solver.commitVocabulary(kb.getVocabulary());
    }

    private void sortProcedureSet(String set) {
        List<Procedure> procSet = procs.get(set);
        Collections.sort(procSet, new PriorityComparator());
        /*
        System.out.println("= " + set + " =");
        for (Procedure proc : procSet) {
        System.out.println(proc.getId() + " - " + proc.getLhsComplexity());
        }
         */
    }
}
