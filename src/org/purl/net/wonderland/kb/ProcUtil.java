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
package org.purl.net.wonderland.kb;

import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.CREdge;
import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import fr.lirmm.rcr.cogui2.kernel.model.Relation;
import fr.lirmm.rcr.cogui2.kernel.util.Hierarchy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcUtil {

    public static void applyProcMatch(CGraph fact, Projection proj, Proc proc, boolean markingConcepts, Hierarchy cth) {
        CGraph lhsFact = proc.getLhs();
        CGraph rhsFact = proc.getRhs();

        Set<Concept> conceptsToDelete = new HashSet<Concept>();
        Map<String, String> conceptsRhsActual = new Hashtable<String, String>();
        Set<Concept> conceptsToAdd = new HashSet<Concept>();

        // assume all concepts from LHS are to be deleted
        Iterator<Concept> cit = proc.getLhs().iteratorConcept();
        while (cit.hasNext()) {
            Concept lhs = cit.next();
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            if (actual == null || conceptsToDelete.contains(actual)) {
                return;
            }
            if (!cth.isKindOf(actual.getType(), lhs.getType())) {
                // projection only checks relation arguments
                return;
            }
            if (markingConcepts) {
                if (actual.isConclusion()) {
                    // the concept has already been processed in a previous rule
                    return;
                }
            }
            conceptsToDelete.add(actual);
        }

        // mark all actual lhs concepts
        if (markingConcepts) {
            for (Concept c : conceptsToDelete) {
                c.setConclusion(true);
            }
        }

        // build delete, update, add lists
        cit = rhsFact.iteratorConcept();
        while (cit.hasNext()) {
            Concept rhs = cit.next();
            Concept lhs = proc.getRhsLhsConceptMap().get(rhs);
            Concept actual = (Concept) proj.getTarget(lhs.getId());
            if (actual != null) {
                conceptsRhsActual.put(rhs.getId(), actual.getId());
                conceptsToDelete.remove(actual);
            } else {
                conceptsToAdd.add(rhs);
            }
        }

        // delete lhs relations from fact
        Iterator<Relation> lhrRelations = lhsFact.iteratorRelation();
        while (lhrRelations.hasNext()) {
            Relation r = lhrRelations.next();
            Concept[] args = getArgs(r, lhsFact);
            for (int i = 0; i < args.length; i++) {
                args[i] = (Concept) proj.getTarget(args[i].getId());
            }
            for (Relation actual : getRelationsOfIdenticalType(args, r, fact)) {
                fact.removeVertex(actual.getId());
            }
        }

        // delete concepts
        for (Concept c : conceptsToDelete) {
            Iterator<String> adjacentRelationIterator = fact.iteratorAdjacents(c.getId());
            while (adjacentRelationIterator.hasNext()) {
                fact.removeVertex(adjacentRelationIterator.next());
            }
            fact.removeVertex(c.getId());
        }

        // update concepts
        for (String rhsId : conceptsRhsActual.keySet()) {

            // update types
            Concept rhs = rhsFact.getConcept(rhsId);
            Concept actual = fact.getConcept(conceptsRhsActual.get(rhsId));

            String[] rhsTypes = rhs.getType();
            Arrays.sort(rhsTypes);

            int idxPos = Arrays.binarySearch(rhsTypes, WkbConstants.PROCOP_KEEP_CT);
            if (idxPos >= 0) {
                // keep original types
            }
            idxPos = Arrays.binarySearch(rhsTypes, WkbConstants.PROCOP_REPLACE_CT);
            if (idxPos >= 0) {
                actual.setType(getNoOpTypes(rhsTypes));
            }
            idxPos = Arrays.binarySearch(rhsTypes, WkbConstants.PROCOP_ADD_CT);
            if (idxPos >= 0) {
                String[] actualTypes = actual.getType();
                rhsTypes = getNoOpTypes(rhsTypes);
                String[] lhsTypes = new String[actualTypes.length + rhsTypes.length];
                System.arraycopy(actualTypes, 0, lhsTypes, 0, actualTypes.length);
                System.arraycopy(rhsTypes, 0, lhsTypes, actualTypes.length, rhsTypes.length);
                actual.setType(lhsTypes);
            }

            // update individual
            if (!rhs.isGeneric()) {
                actual.setIndividual(rhs.getIndividual());
            }
        }

        // add concepts
        for (Concept rhs : conceptsToAdd) {
            Concept actual = new Concept(WkbUtil.newUniqueId());
            actual.setType(rhs.getType());
            actual.setIndividual(rhs.getIndividual());
            fact.addVertex(actual);
            conceptsRhsActual.put(rhs.getId(), actual.getId());
        }

        // add rhs relations
        Iterator<Relation> rhsRelations = rhsFact.iteratorRelation();
        while (rhsRelations.hasNext()) {
            Relation rhsRelation = rhsRelations.next();
            Concept[] args = getArgs(rhsRelation, rhsFact);
            for (int i = 0; i < args.length; i++) {
                args[i] = fact.getConcept(conceptsRhsActual.get(args[i].getId()));
            }
            List<Relation> actual = getRelationsOfIdenticalType(args, rhsRelation, fact);
            if (actual.size() == 0) {
                addNewRelation(rhsRelation, args, fact);
            }
        }

    }

    private static String[] getNoOpTypes(String[] types) {
        List<String> noOpTypes = new ArrayList<String>();
        for (String type : types) {
            if (type.indexOf(WkbConstants.PROCOP_CT) != 0) {
                noOpTypes.add(type);
            }
        }
        return noOpTypes.toArray(new String[noOpTypes.size()]);
    }

    private static Concept[] getArgs(Relation r, CGraph cg) {
        int argCount = 0;
        Concept[] tmpArgs = new Concept[WkbUtil.MAX_RELATION_ARG_COUNT];
        Iterator<CREdge> argEdges = cg.iteratorEdge(r.getId());
        while (argEdges.hasNext()) {
            CREdge argEdge = argEdges.next();
            Concept arg = cg.getConcept(argEdge);
            int pos = argEdge.getNumOrder() - 1;
            tmpArgs[pos] = arg;
            ++argCount;
        }
        Concept[] args = new Concept[argCount];
        System.arraycopy(tmpArgs, 0, args, 0, argCount);
        return args;
    }

    private static List<Relation> getRelationsOfIdenticalType(Concept[] args, Relation sample, CGraph cg) {
        List<Relation> relations = new ArrayList<Relation>();
        String type = sample.getType()[0];
        Iterator<String> relationIdIterator = cg.iteratorAdjacents(args[0].getId());
        while (relationIdIterator.hasNext()) {
            String relationId = relationIdIterator.next();
            Relation r = cg.getRelation(relationId);
            if (r.getType()[0].equals(type)) {
                boolean isMatch = true;
                Iterator<CREdge> relationEdge = cg.iteratorEdge(relationId);
                while (relationEdge.hasNext()) {
                    CREdge edge = relationEdge.next();
                    int pos = edge.getNumOrder() - 1;
                    Concept c = cg.getConcept(edge);
                    if (args[pos] != c) {
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch) {
                    relations.add(r);
                }
            }
        }
        return relations;
    }

    private static void addNewRelation(Relation r, Concept[] args, CGraph cg) {
        String newRelationId = WkbUtil.newUniqueId();
        Relation newRelation = new Relation(newRelationId);
        newRelation.setType(r.getType());
        cg.addVertex(newRelation);
        for (int i = 0; i < args.length; i++) {
            cg.addEdge(args[i].getId(), newRelationId, i + 1);
        }
    }
}
