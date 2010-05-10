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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import org.purl.net.wonderland.WonderlandRuntimeException;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ConceptualGraph extends BasicIdentifiable {

    private final Map<String, Concept> concepts = new LinkedHashMap<String, Concept>();
    private final Map<String, Relation> relations = new LinkedHashMap<String, Relation>();
    private final Set<Edge> edges = new HashSet<Edge>();

    public ConceptualGraph() {
        setLabel(getId());
        setSet(Support.DEFAULT_SET);
    }

    public ConceptualGraph(String id) {
        super(id);
    }

    public Map<String, Concept> getConcepts() {
        return concepts;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Map<String, Relation> getRelations() {
        return relations;
    }

    public void add(Concept concept) {
        concepts.put(concept.getId(), concept);
    }

    public void add(Relation relation) {
        relations.put(relation.getId(), relation);
    }

    public void add(Edge edge) {
        if (concepts.containsKey(edge.getConcept().getId())
                && relations.containsKey(edge.getRelation().getId())) {
            edges.add(edge);
            edge.getRelation().addEdge(edge);
            edge.getConcept().addEdge(edge);
        } else {
            throw new WonderlandRuntimeException("vertex is not in graph");
        }
    }

    private void cleanPrev() {
        for (Vertex v : concepts.values()) {
            v.prev = null;
        }
        for (Vertex v : relations.values()) {
            v.prev = null;
        }
    }

    public Path findPath(Vertex from, Vertex to) {
        if (from == null || to == null) {
            return null;
        }

        List<Vertex> vertices = new ArrayList<Vertex>();

        if (from == to) {
            vertices.add(to);
        } else {
            cleanPrev();
            Queue<Vertex> queue = new ArrayDeque<Vertex>();

            from.prev = from;
            queue.add(from);

            FIND_PATH:
            while (queue.size() > 0) {
                from = queue.poll();
                for (Edge edge : from.getEdges()) {
                    Vertex adj = edge.getOther(from);
                    if (adj.prev == null) {
                        if (adj == to) {
                            vertices.add(adj);
                            while (from.prev != from) {
                                vertices.add(from);
                                from = from.prev;
                            }
                            vertices.add(from);
                            Collections.reverse(vertices);
                            break FIND_PATH;
                        } else {
                            adj.prev = from;
                            queue.add(adj);
                        }
                    }
                }
            }

        }
        return (vertices.size() > 0) ? (new Path(vertices)) : (null);
    }
}
