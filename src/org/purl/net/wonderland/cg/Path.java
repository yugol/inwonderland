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

import edu.northwestern.at.utils.Compare;
import java.util.ArrayList;
import java.util.List;
import org.purl.net.wonderland.WonderlandRuntimeException;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Path implements Comparable<Path> {

    private final List<Vertex> vertices;
    private final List<Edge> edges;

    public Path(List<Vertex> vertices) {
        this.vertices = vertices;
        this.edges = new ArrayList<Edge>();
        findEdges();
    }

    private void findEdges() {
        for (int i = 0; i < vertices.size() - 1; i++) {
            Vertex v1 = vertices.get(i);
            Vertex v2 = vertices.get(i + 1);
            for (Edge edge : v1.getEdges()) {
                if (edge.getOther(v1) == v2) {
                    edges.add(edge);
                    break;
                }
            }
        }
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public int size() {
        return vertices.size();
    }

    public Vertex getVertex(int i) {
        return vertices.get(i);
    }

    public Edge getEdge(int i) {
        return edges.get(i);
    }

    public int compareTo(Path o) {
        return Compare.compare(size(), o.size());
    }

    public void trim(int requiredSize) {
        if (requiredSize < 0) {
            throw new WonderlandRuntimeException("invalid argument " + requiredSize);
        }
        int sz = size();
        while (sz > requiredSize) {
            --sz;
            vertices.remove(sz);
            edges.remove(sz - 1);
        }
    }

    public Vertex getFirstVertex() {
        return vertices.get(0);
    }

    public Vertex getLastVertex() {
        return vertices.get(vertices.size() - 1);
    }
}
