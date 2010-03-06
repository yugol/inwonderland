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

import fr.lirmm.rcr.cogui2.kernel.model.Concept;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import fr.lirmm.rcr.cogui2.kernel.model.Projection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcImpl implements Procedure {

    private final String id;
    private final CGraph lhs;
    private final CGraph rhs;
    private final Map<Concept, Concept> rhsLhsConceptMap;
    List<Projection> projections;

    public String getId() {
        return id;
    }

    public ProcImpl(CGraph lhs, CGraph rhs, Map<Concept, Concept> conceptMap) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.rhsLhsConceptMap = conceptMap;
        this.id = lhs.getName();
    }

    public CGraph getLhs() {
        return lhs;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

    public List<Projection> getProjections() {
        return projections;
    }

    public CGraph getRhs() {
        return rhs;
    }

    public Map<Concept, Concept> getRhsLhsConceptMap() {
        return rhsLhsConceptMap;
    }
}