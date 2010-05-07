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
package org.purl.net.wonderland.cg;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Support {

    public static final int MAX_ARITY = 10;
    public static final String TOP_ID = "Top";
    public static final String DEFAULT_SET = "default_set";
    //
    private final ConceptType topConceptType;
    private final RelationType[] topRelationType;
    private final Map<String, ConceptType> conceptTypes = new HashMap<String, ConceptType>();
    private final Map<String, RelationType> relationTypes = new HashMap<String, RelationType>();
    private final Map<String, Individual> individuals = new HashMap<String, Individual>();

    public Support() {
        topConceptType = addConceptType(TOP_ID);
        topRelationType = new RelationType[MAX_ARITY];
    }

    Map<String, ConceptType> getConceptTypes() {
        return conceptTypes;
    }

    Map<String, Individual> getIndividuals() {
        return individuals;
    }

    Map<String, RelationType> getRelationTypes() {
        return relationTypes;
    }

    void add(ConceptType conceptType) {
        conceptTypes.put(conceptType.getId(), conceptType);
    }

    void add(RelationType relationType) {
        relationTypes.put(relationType.getId(), relationType);
    }

    void add(Individual individual) {
        individuals.put(individual.getId(), individual);
    }

    public ConceptType getConceptType(String id) {
        return conceptTypes.get(id);
    }

    public ConceptType addConceptType(String id) {
        ConceptType ct = conceptTypes.get(id);
        if (ct == null) {
            ct = new ConceptType(id);
            ct.setLabel(id);
            add(ct);

            if (!id.equals(TOP_ID)) {
                ct.addParent(topConceptType);
            }
        }
        return ct;
    }

    public Individual getIndividual(String id) {
        return individuals.get(id);
    }

    public Individual addIndividual(String id, ConceptType ct) {
        Individual marker = individuals.get(id);
        if (marker == null) {
            if (ct == null) {
                ct = topConceptType;
            }
            marker = new Individual(id, ct);
            marker.setLabel(id);
            add(marker);
        }
        return marker;
    }

    public RelationType getRelationType(String id) {
        return relationTypes.get(id);
    }

    public RelationType addRelationType(String id, int arity) {
        String topId = TOP_ID + arity;
        RelationType rt = relationTypes.get(id);
        if (rt == null) {
            rt = new RelationType(id, arity);
            ConceptTypeSet[] sig = rt.getSignature();
            for (int i = 0; i < sig.length; i++) {
                sig[i].add(topConceptType);
            }
            rt.setLabel(id);
            add(rt);

            RelationType parent = topRelationType[arity - 1];
            if (!id.equals(topId) && parent == null) {
                parent = addRelationType(topId, arity);
                topRelationType[arity - 1] = parent;
            }
            if (!id.equals(topId)) {
                rt.setParent(parent);
            }
        }
        return rt;
    }
}
