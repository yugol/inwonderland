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

import java.io.File;
import org.junit.Test;
import org.purl.net.wonderland.W;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class PathTest {

    private ConceptualGraph cg;
    private KnowledgeBase kb;
    private Concept _i;
    private Concept _own;
    private Concept _12;
    private Concept _ox;
    private Concept _point;
    private Relation _nsubj;
    private Relation _num;
    private Relation _dobj;
    private Path path;

    public PathTest() throws Exception {
        File cogxmlFile = new File(W.getTestDataFolder(), "path-1.cogxml");
        kb = CogxmlIO.readCogxmlFile(cogxmlFile);
        cg = kb.getFacts().values().iterator().next().iterator().next();
        _i = cg.getConcepts().get("fJe5ZjUekbxKaPoW4eHr");
        _own = cg.getConcepts().get("2fYBb4yTQmDr4K3Lta0f");
        _12 = cg.getConcepts().get("8fFj00WA3EgAqClyTgCG");
        _ox = cg.getConcepts().get("MC2Lyca8OUzaKDOl0oQv");
        _point = cg.getConcepts().get("s5qGIn88zCRBKC14HsSi");
        _nsubj = cg.getRelations().get("2wVRn9D7ojo349JsbR7T");
        _num = cg.getRelations().get("luTqVPnFQvrr4a5ry1fq");
        _dobj = cg.getRelations().get("3OdDzYhoDvMrqSG1NcVT");
    }

    @Test
    public void testFindPath5() throws Exception {
        path = cg.findPath(_num, _nsubj);
        assertEquals(5, path.size());

        assertEquals(_num, path.getVertex(0));
        assertEquals(_ox, path.getVertex(1));
        assertEquals(_dobj, path.getVertex(2));
        assertEquals(_own, path.getVertex(3));
        assertEquals(_nsubj, path.getVertex(4));

        assertEquals(1, path.getEdge(0).getLabel());
        assertEquals(0, path.getEdge(1).getLabel());
        assertEquals(1, path.getEdge(2).getLabel());
        assertEquals(1, path.getEdge(3).getLabel());
    }

    @Test
    public void testFindPath3() throws Exception {

        path = cg.findPath(_num, _dobj);
        assertEquals(3, path.size());
        assertEquals(_num, path.getVertex(0));
        assertEquals(_ox, path.getVertex(1));
        assertEquals(_dobj, path.getVertex(2));

        path = cg.findPath(_12, _ox);
        assertEquals(3, path.size());
        assertEquals(_12, path.getVertex(0));
        assertEquals(_num, path.getVertex(1));
        assertEquals(_ox, path.getVertex(2));
    }

    @Test
    public void testFindPath2() throws Exception {
        path = cg.findPath(_num, _ox);
        assertEquals(2, path.size());
        assertEquals(_num, path.getVertex(0));
        assertEquals(_ox, path.getVertex(1));

        path = cg.findPath(_i, _nsubj);
        assertEquals(2, path.size());
        assertEquals(_i, path.getVertex(0));
        assertEquals(_nsubj, path.getVertex(1));
    }

    @Test
    public void testFindPath1() throws Exception {
        path = cg.findPath(_i, _i);
        assertEquals(1, path.size());
        assertEquals(_i, path.getVertex(0));

        path = cg.findPath(_nsubj, _nsubj);
        assertEquals(1, path.size());
        assertEquals(_nsubj, path.getVertex(0));
    }

    @Test
    public void testFindPath0() throws Exception {
        path = cg.findPath(null, null);
        assertNull(path);

        path = cg.findPath(null, _point);
        assertNull(path);

        path = cg.findPath(_dobj, null);
        assertNull(path);

        path = cg.findPath(_point, _nsubj);
        assertNull(path);
    }

    @Test
    public void testFindPathCycle() throws Exception {
        File cogxmlFile = new File(W.getTestDataFolder(), "path-2.cogxml");
        KnowledgeBase kbCycle = CogxmlIO.readCogxmlFile(cogxmlFile);
        ConceptualGraph cgCycle = kbCycle.getFacts().values().iterator().next().iterator().next();
        Concept _consider = cgCycle.getConcepts().get("p1Dyy2pfzNVCKJHQ5Akq");
        Concept _do = cgCycle.getConcepts().get("AEAtZGt7NzZ5KiQSC4B2");

        path = cg.findPath(_consider, _do);
        assertEquals(3, path.size());
        assertEquals(_consider, path.getVertex(0));
    }
}
