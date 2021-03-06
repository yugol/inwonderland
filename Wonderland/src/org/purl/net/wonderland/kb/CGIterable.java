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
import fr.lirmm.rcr.cogui2.kernel.model.KnowledgeBase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class CGIterable implements Iterable<CGraph> {

    List<CGraph> graphs;

    public CGIterable(KnowledgeBase kb, String set) {
        graphs = new ArrayList<CGraph>(1000);
        Iterator<CGraph> it = kb.getFactGraphSet().iteratorGraphs();
        while (it.hasNext()) {
            CGraph cg = it.next();
            if (cg.getSet().equals(set)) {
                graphs.add(cg);
            }
        }
        Collections.sort(graphs, new CGraphNameComparer());
        this.graphs = Collections.unmodifiableList(graphs);
    }

    public Iterator<CGraph> iterator() {
        return graphs.iterator();
    }
}

class CGraphNameComparer implements Comparator<CGraph> {

    public int compare(CGraph o1, CGraph o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
