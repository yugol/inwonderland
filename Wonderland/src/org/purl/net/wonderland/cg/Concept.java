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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Concept extends Vertex {

    private final ConceptTypeSet types = new ConceptTypeSet();
    private Individual individual = null;
    private final Set<Edge> edges = new HashSet<Edge>();

    public Concept() {
        super();
    }

    public Concept(String id) {
        super(id);
    }

    public Individual getIndividual() {
        return individual;
    }

    public void setIndividual(Individual individual) {
        this.individual = individual;
    }

    public void addType(ConceptType type) {
        types.add(type);
    }

    void addEdge(Edge edge) {
        edges.add(edge);
    }

    public ConceptTypeSet getTypes() {
        return types;
    }

    @Override
    public Collection<Edge> getEdges() {
        return edges;
    }

    @Override
    public String toString() {
        return individual.getLabel();
    }


}
