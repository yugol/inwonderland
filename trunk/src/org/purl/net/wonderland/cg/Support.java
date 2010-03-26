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

    private final Map<String, ConceptType> conceptTypes = new HashMap<String, ConceptType>();
    private final Map<String, RelationType> relationTypes = new HashMap<String, RelationType>();
    private final Map<String, Individual> individuals = new HashMap<String, Individual>();

    public Map<String, ConceptType> getConceptTypes() {
        return conceptTypes;
    }

    public Map<String, Individual> getIndividuals() {
        return individuals;
    }

    public Map<String, RelationType> getRelationTypes() {
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
}
