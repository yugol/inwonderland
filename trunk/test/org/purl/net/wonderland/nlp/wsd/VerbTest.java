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
import edu.stanford.nlp.trees.TreePrint;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.resources.StanfordParserWrapper;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class VerbTest {

    public VerbTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    // @Test
    public void testAllVerbs() throws Exception {
        for (File file : Globals.getPropBankDataFolder().listFiles()) {
            String lemma = file.getName();
            int endIndex = lemma.lastIndexOf(".xml");
            if (endIndex > 0) {
                lemma = lemma.substring(0, endIndex);
                Verb v = new Verb(lemma);
                System.out.println("");
            }
        }
        for (String tag : WsdManager.syntaxTags) {
            System.out.println(tag);
        }
    }

    // @Test
    public void testOneVerb() throws Exception {
        Verb v = new Verb("abduct");
        System.out.println("");
    }

    @Test
    public void testOneVerbToProcs() throws Exception {
        Verb v = new Verb("abduct");
        for (VerbFrame frame : v.getFrames()) {
            for (FrameExample example : frame.getExamples()) {
                if (example.getArgs().isEmpty()) {
                    String text = example.getText();
                    System.out.println(text);
                    List<? extends HasWord> tokens = StanfordParserWrapper.tokenize(text);
                    Tree parse = StanfordParserWrapper.parse(tokens);
                    parse = parse.flatten();
                    parse.pennPrint();

                    List<FrameExample.FrameEntry> frameEntryes = example.getFrame();
                    int frameCursor = 0;
                    List<Tree> bfs = parse.getChildrenAsList();
                    int parseCursor = 0;

                    DONE_SENTENCE:
                    while (frameCursor < frameEntryes.size() && parseCursor < bfs.size()) {
                        FrameExample.FrameEntry entry = frameEntryes.get(frameCursor);
                        String entryLabel = entry.getPhrase();
                        while (!entryLabel.equals("NP")) {
                            ++frameCursor;
                            if (frameCursor >= frameEntryes.size()) {
                                break DONE_SENTENCE;
                            }
                            entry = frameEntryes.get(frameCursor);
                            entryLabel = entry.getPhrase();
                        }

                        Tree node = bfs.get(parseCursor);
                        String nodeLabel = node.label().value();

                        if (nodeLabel.equals("NP")) {
                            String phrase = yieldToString(node.yield());
                            example.getArgs().put(frame.getRoles().get(entry.getValue()), phrase);
                            ++parseCursor;
                            ++frameCursor;
                        }

                        bfs.addAll(bfs.get(parseCursor).getChildrenAsList());
                        printBfs(bfs);
                        ++parseCursor;
                    }
                }
            }
        }
        System.out.println("");
    }

    private void printBfs(List<Tree> bfs) {
        for (Tree node : bfs) {
            System.out.print(node.label().value());
            System.out.print(" ");
        }
        System.out.println("");
    }

    private String yieldToString(Sentence<HasWord> yield) {
        StringBuilder text = new StringBuilder();
        for (HasWord word : yield) {
            if (text.length() > 0) {
                text.append(" ");
            }
            text.append(word.word());
        }
        return text.toString();
    }
}
