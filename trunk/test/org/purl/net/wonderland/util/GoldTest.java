/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, mergeWtags, publish, distribute, sublicense, and/or sell
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

import org.purl.net.wonderland.nlp.WTaggingUtil;
import java.io.File;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.engine.Engine;
import org.purl.net.wonderland.engine.Level2Personality;
import org.purl.net.wonderland.engine.Personality;
import org.purl.net.wonderland.kb.KbUtil;

/**
 *
 * @author Iulian
 */
public class GoldTest {

    String level = KbUtil.level2;
    Personality pers = new Level2Personality();
    String corpusFileName = "egcp.train.level2.xml";
    int firstSentence = 1;
    int lastSentence = 66;

    @Test
    public void testGoldCorpus() throws Exception {
        System.out.println("testGoldCorpus");

        List<String> plain = IO.getFileContentAsStringList(new File(Globals.getCorporaFolder(), "egcp.train.level0.txt"));
        Corpus corpus = new Corpus();
        corpus.buildFrom(new File(Globals.getCorporaFolder(), corpusFileName));
        Engine engine = new Engine();
        engine.setPersonality(pers);

        if (lastSentence < firstSentence) {
            lastSentence = corpus.getSentenceCount();
        }
        int errorCount = 0;
        int wordCount = 0;
        int sentenceCount = 0;
        for (int i = firstSentence; i <= lastSentence; ++i) {
            // System.out.println("Sentence: " + i);

            boolean printed = false;
            String sentence = plain.get(i - 1);

            engine.processMessage(sentence);
            WTagging[] expected = corpus.getSentencePosProps(i);
            WTagging[] actual = engine.getFactWTaggings(i - firstSentence + 1, false, level);

            if (expected.length != actual.length) {
                System.err.println("");
                System.err.println("ERROR in sentence [" + i + "]:");
                System.err.println(sentence);
                System.err.println("    expected " + expected.length + " tokens");
                System.err.println("    found " + actual.length + " tokens");
                System.err.println("");
                errorCount += expected.length;
                continue;
            }

            // assertEquals("At sentence " + i, expected.length, actual.length);

            for (int j = 0; j < actual.length; ++j) {
                String errStr = WTaggingUtil.areConsistent(expected[j], actual[j]);
                if (errStr != null) {
                    if (!printed) {
                        System.err.println("");
                        System.err.println("ERROR in sentence [" + i + "]:");
                        System.err.println(sentence);
                        printed = true;
                    }
                    System.err.println("    WORD [" + (j + 1) + "]: " + actual[j].getForm());
                    System.err.println(errStr);
                    ++errorCount;
                }
                ++wordCount;
            }
            ++sentenceCount;
        }
        TestUtil.saveKbAndMarkings(engine, level);
        System.out.println("\n\nResults: " + errorCount + " error(s), for " + wordCount + " words in " + sentenceCount + " sentences.");
        assertEquals(0, errorCount);
    }
}
