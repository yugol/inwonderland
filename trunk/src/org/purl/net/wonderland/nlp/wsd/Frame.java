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
package org.purl.net.wonderland.nlp.wsd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.purl.net.wonderland.cg.KnowledgeBase;
import org.purl.net.wonderland.cg.Path;
import org.purl.net.wonderland.cg.Rule;
import org.purl.net.wonderland.cg.Vertex;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class Frame {

    private final List<VnSyntaxItem> syntax;
    private int verbIndex;

    public Frame(List<VnSyntaxItem> syntax) {
        this.syntax = syntax;
        findVerbIndex();
    }

    private void findVerbIndex() {
        for (verbIndex = 0; verbIndex < syntax.size(); verbIndex++) {
            VnSyntaxItem item = syntax.get(verbIndex);
            if (item.getType().equals("VERB")) {
                break;
            }
        }
        if (verbIndex >= syntax.size()) {
            verbIndex = -1;
        }
    }

    int size() {
        return syntax.size();
    }

    VnSyntaxItem get(int i) {
        return syntax.get(i);
    }

    boolean isMappedCompletely() {
        for (VnSyntaxItem item : syntax) {
            if (item.getWord() == null) {
                return false;
            }
        }
        return true;
    }

    void remove(int i) {
        syntax.remove(i);
        findVerbIndex();
    }

    public int getVerbIndex() {
        return verbIndex;
    }

    void removeUnmapped() {
        List<VnSyntaxItem> itemsToDelete = new ArrayList<VnSyntaxItem>();
        for (VnSyntaxItem item : syntax) {
            if (item.getWord() == null) {
                itemsToDelete.add(item);
            }
        }
        syntax.removeAll(itemsToDelete);
        findVerbIndex();
    }

    void cleanContext() {
        List<Path> paths = new ArrayList<Path>();
        for (VnSyntaxItem item : syntax) {
            paths.add(item.getPath());
        }
        Collections.sort(paths);

        int[] requiredSizes = new int[paths.size()];
        Arrays.fill(requiredSizes, 3);

        for (int i = 0; i < requiredSizes.length; i++) {
            Path longPath = paths.get(i);
            if (longPath.size() <= 3) {
                requiredSizes[i] = longPath.size();
            } else {
                for (int j = 0; j < i; j++) {
                    Path shortPath = paths.get(j);
                    int minLongPathLen = mostDistantIntersection(shortPath, longPath) + 3;
                    if (minLongPathLen > requiredSizes[i]) {
                        requiredSizes[i] = minLongPathLen;
                    }
                }
            }
        }

        for (int i = 0; i < requiredSizes.length; i++) {
            Path path = paths.get(i);
            int requiredSize = requiredSizes[i];
            if (path.size() > requiredSize) {
                path.trim(requiredSize);
            }
        }
    }

    private static int mostDistantIntersection(Path shortPath, Path longPath) {
        int pos = -1;
        for (int i = 0; i < longPath.size(); i += 2) {
            Vertex lv = longPath.getVertex(i);
            for (int j = 0; j < shortPath.size(); j += 2) {
                Vertex sv = shortPath.getVertex(j);
                if (lv == sv) {
                    pos = i;
                }
            }
        }
        return pos;
    }

    void makeProcRule(String lemma, KnowledgeBase kb) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
