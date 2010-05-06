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

import edu.stanford.nlp.trees.Tree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.purl.net.wonderland.WonderlandRuntimeException;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
class VnExample extends Example {

    class ItemLoad {

        public final VnSyntaxItem syntaxItem;
        public final Tree phrase;
        public final String text;

        public ItemLoad(VnSyntaxItem item, Tree node) {
            this.syntaxItem = item;
            this.phrase = node;
            this.text = joinLemmata(node.yield());
        }
    }
    private final List<VnSyntaxItem> frame;
    private List<ItemLoad> matches;

    VnExample(String text, List<VnSyntaxItem> syntax) {
        super(text);
        this.frame = syntax;
    }

    public void makeSense(List<String> members) {
        System.out.println("");
        System.out.println(text);

        matches = new ArrayList<ItemLoad>();

        List<WTagging> tokens = Pipeline.tokenizeAndSplit(text).get(0);
        Object[] result = Pipeline.parse(tokens);

        Tree parse = (Tree) result[2];
        parse = parse.flatten();
        List<Tree> nodes = parse.getLeaves();
        for (int i = 0; i < tokens.size(); i++) {
            nodes.get(i).label().setValue(tokens.get(i).getLemma());
        }

        List<Tree> bfs = parse.getChildrenAsList();
        int frameCursor = 0;
        int bfsCursor = 0;

        while (frameCursor < frame.size() && bfsCursor < bfs.size()) {

            Tree node = bfs.get(bfsCursor);
            String nodeDesc = node.label().value() + " - " + joinLemmata(node.yield());
            // System.out.println(nodeDesc);

            VnSyntaxItem syntaxItem = frame.get(frameCursor);

            if (isNodeOfType(node, syntaxItem, members)) {
                ItemLoad itemLoad = new ItemLoad(syntaxItem, node);
                matches.add(itemLoad);
                String itemDesc = " * [" + frameCursor + "] " + itemLoad.syntaxItem.getType() + " -> " + itemLoad.text;
                // System.out.println(itemDesc);
                ++frameCursor;
            } else {
                bfs.addAll(bfs.get(bfsCursor).getChildrenAsList());
            }
            ++bfsCursor;
        }

        for (int i = 0; i < frame.size(); i++) {
            System.out.print("[" + i + "] " + frame.get(i).getType());
            if (i < matches.size()) {
                System.out.print(" -> " + matches.get(i).text);
            }
            System.out.println("");
        }

        if (frame.size() != matches.size()) {
            throw new WonderlandRuntimeException("unmatched frame");
        }
    }

    private boolean isNodeOfType(Tree node, VnSyntaxItem item, List<String> members) {
        if (!node.isLeaf()) {

            String itemType = item.getType();
            String nodeLabel = node.label().value();

            if ("NP".equals(itemType)) {
                if ("NP".equals(nodeLabel)) {
                    return true;
                }
            } else if ("VERB".equals(itemType)) {
                String lemma = joinLemmata(node.yield());
                if (members.contains(lemma)) {
                    if ("VBD".equals(nodeLabel)) {
                        return true;
                    }
                    if ("VBP".equals(nodeLabel)) {
                        return true;
                    }
                    if ("VBZ".equals(nodeLabel)) {
                        return true;
                    }
                    if ("VB".equals(nodeLabel)) {
                        return true;
                    }
                }
            } else if ("PREP".equals(itemType)) {
                if ("IN".equals(nodeLabel)) {
                    if (item.getValue() != null) {
                        String lemma = joinLemmata(node.yield());
                        String[] choises = item.getValue().split(" ");
                        if (Arrays.asList(choises).contains(lemma)) {
                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
