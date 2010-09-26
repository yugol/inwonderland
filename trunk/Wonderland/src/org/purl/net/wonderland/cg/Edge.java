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

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Edge {

    private final Vertex relation;
    private final Vertex concept;
    private final int label;

    public Edge(Relation relation, Concept concept, int label) {
        this.relation = relation;
        this.concept = concept;
        this.label = label;
    }

    public Concept getConcept() {
        return (Concept) concept;
    }

    public Relation getRelation() {
        return (Relation) relation;
    }

    public int getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return concept.hashCode() * relation.hashCode() * label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        if (this.relation != other.relation && (this.relation == null || !this.relation.equals(other.relation))) {
            return false;
        }
        if (this.concept != other.concept && (this.concept == null || !this.concept.equals(other.concept))) {
            return false;
        }
        if (this.label != other.label) {
            return false;
        }
        return true;
    }

    public Vertex getOther(Vertex v) {
        if (v == concept) {
            return relation;
        }
        if (v == relation) {
            return concept;
        }
        return null;
    }
}
