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

package org.purl.net.wonderland.util;

import java.io.File;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.engine.MessageProcessor;

/**
 *
 * @author Iulian
 */
public class GoldTest {

    @Test
    public void testGoldCorpus() throws Exception {
        System.out.println("testGoldCorpus");
        Globals.useMorphAdornerTagsInWordForm = true;

        List<String> plain = IO.getFileContentAsStringList(new File(Globals.getCorporaFolder(), "egcp.train.level0.txt"));
        Corpus level1 = new Corpus();
        level1.buildFrom(new File(Globals.getCorporaFolder(), "egcp.train.level1.xml"));
        MessageProcessor msgProc = new MessageProcessor();

        int firstSentence = 1;
        int lastSentence = 0;
        if (lastSentence < firstSentence) {
            lastSentence = level1.getSentenceCount();
        }
        int errorCount = 0;
        int wordCount = 0;
        int sentenceCount = 0;
        for (int i = firstSentence; i <= lastSentence; ++i) {
            // System.out.println("Sentence: " + i);

            boolean printed = false;
            String sentence = plain.get(i - 1);

            msgProc.processMessage(sentence);
            WTagging[] expected = level1.getSentencePosProps(i);
            WTagging[] actual = msgProc.getKb().getSentenceWTaggings(i - firstSentence + 1, false);
            assertEquals("At sentence " + i, expected.length, actual.length);

            for (int j = 0; j < actual.length; ++j) {
                String errStr = WTaggingUtil.areConsistent(expected[j], actual[j]);
                if (errStr != null) {
                    if (!printed) {
                        System.out.println("");
                        System.out.println("ERROR in sentence [" + i + "]:");
                        System.out.println(sentence);
                        printed = true;
                    }
                    System.out.println("    WORD [" + (j + 1) + "]: " + actual[j].getForm());
                    System.out.println(errStr);
                    ++errorCount;
                }
                ++wordCount;
            }
            ++sentenceCount;
        }
        TestUtil.saveKbAndMarkings(msgProc);
        System.out.println("\n\nResults: " + errorCount + " error(s), for " + wordCount + " words in " + sentenceCount + " sentences.");
        assertEquals(0, errorCount);
    }
}
