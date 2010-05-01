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

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.trees.Tree;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.purl.net.wonderland.nlp.Pipeline;
import org.purl.net.wonderland.nlp.WTagging;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class Example {

    public static enum Type {

        PropBank, VerbNet
    };

    public static final class RoleData {

        public static final String sep = "|";
        private final String phrase;
        private final String value;
        private final String[] synrestrs;
        private final String[] selrestrs;
        private RoleData prep = null;

        public RoleData(String phrase, String value, String synrestrs, String selrestrs) {
            this.phrase = phrase;
            this.value = value;
            this.synrestrs = synrestrs.split(sep);
            this.selrestrs = selrestrs.split(sep);
        }

        public String getPhrase() {
            return phrase;
        }

        public String getValue() {
            return value;
        }

        public String[] getSelrestrs() {
            return selrestrs;
        }

        public String[] getSynrestrs() {
            return synrestrs;
        }

        public RoleData getPrep() {
            return prep;
        }

        public void setPrep(RoleData prep) {
            this.prep = prep;
        }
    }
    //
    private final String verbLemma;
    private final Type type;
    private final String text;
    private final Map<Themrole, String> args;
    private final List<RoleData> frame;

    public Example(String verbLemma, Type type, String text) {
        this.verbLemma = verbLemma;
        this.type = type;
        this.text = text;
        this.args = new HashMap<Themrole, String>();
        this.frame = new ArrayList<RoleData>();
    }

    public String getText() {
        return text;
    }

    public Map<Themrole, String> getArgs() {
        return args;
    }

    public List<RoleData> getFrame() {
        return frame;
    }

    public Type getType() {
        return type;
    }

    public String getVerbLemma() {
        return verbLemma;
    }

    public void mapArgs(Map<String, Themrole> roles) {
        if (args.isEmpty()) {
            List<WTagging> tokens = Pipeline.tokenizeAndSplit(text).get(0);
            Object[] result = Pipeline.parse(tokens);
            Tree parse = (Tree) result[2];
            parse = parse.flatten();
            List<Tree> nodes = parse.getLeaves();
            for (int i = 0; i < tokens.size(); i++) {
                nodes.get(i).label().setValue(tokens.get(i).getLemma());
            }

            int frameCursor = 0;
            List<Tree> bfs = parse.getChildrenAsList();
            int parseCursor = 0;

            DONE_SENTENCE:
            while (frameCursor < frame.size() && parseCursor < bfs.size()) {
                Example.RoleData entry = frame.get(frameCursor);
                String entryLabel = entry.getPhrase();
                while (!entryLabel.equals("NP")) {
                    ++frameCursor;
                    if (frameCursor >= frame.size()) {
                        break DONE_SENTENCE;
                    }
                    entry = frame.get(frameCursor);
                    entryLabel = entry.getPhrase();
                }

                Tree node = bfs.get(parseCursor);
                String nodeLabel = node.label().value();

                if (node.isPhrasal() && nodeLabel.equals("NP")) {
                    String phrase = joinLemmata(node.yield());
                    args.put(roles.get(entry.getValue()), phrase);
                    ++parseCursor;
                    ++frameCursor;
                }

                bfs.addAll(bfs.get(parseCursor).getChildrenAsList());
                ++parseCursor;
            }
        }
    }

    private String joinLemmata(Sentence<HasWord> yield) {
        StringBuilder join = new StringBuilder();
        for (HasWord token : yield) {
            if (join.length() > 0) {
                join.append(" ");
            }
            join.append(token.word());
        }
        return join.toString();
    }

    public void resetRoleHits() {
        for (Themrole role : args.keySet()) {
            role.resetHits();
        }
    }

    public Themrole getBestRole() {
        int maxHits = 0;
        Themrole bestRole = null;
        for (Themrole role : args.keySet()) {
            int hits = role.getHits();
            if (hits > maxHits) {
                maxHits = hits;
                bestRole = role;
            } else if (hits == maxHits) {
                bestRole = null;
            }
        }
        return bestRole;
    }

    public RoleData getRoleData(Themrole role) {
        for (RoleData roleData : frame) {
            if (role.getVnThemrole().equals(roleData.getValue())) {
                return roleData;
            }
        }
        return null;
    }
}
