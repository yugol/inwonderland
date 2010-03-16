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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.engine.Engine;
import org.purl.net.wonderland.engine.Level1Personality;
import org.purl.net.wonderland.engine.Level2Personality;
import org.purl.net.wonderland.engine.Personality;
import org.purl.net.wonderland.kb.KbUtil;

/**
 *
 * @author Iulian
 */
public class GoldTest {

    String level = KbUtil.level1;
    Personality pers = new Level1Personality();
    String corpusFileName = "egcp.train." + level + ".xml";
    int firstSentence = 1;
    int lastSentence = 1201;
    //
    private static final NumberFormat timeFormatter = new DecimalFormat("0.0000");

    @Test
    public void testGoldCorpus() throws Exception {
        System.out.println("Testing Gold Corpus - " + corpusFileName);

        Globals.init();
        KbUtil.normalizeKbFile(Globals.getDefaultParseKBFile());

        File corpusFile = new File(Globals.getCorporaFolder(), corpusFileName);
        Corpus.normalizeCorpusFile(corpusFile);

        List<String> plain = IO.getFileContentAsStringList(new File(Globals.getCorporaFolder(), "egcp.train.level0.txt"));
        Corpus corpus = new Corpus();
        corpus.buildFrom(corpusFile);
        Engine engine = new Engine();
        engine.setPersonality(pers);

        if (lastSentence < firstSentence) {
            lastSentence = corpus.getSentenceCount();
        }
        List<Integer> errSentences = new ArrayList<Integer>();
        int errorCount = 0;
        int wordCount = 0;
        int sentenceCount = 0;
        double totalTime = 0;
        for (int i = firstSentence; i <= lastSentence; ++i) {
            boolean printed = false;
            String sentence = plain.get(i - 1);

            CodeTimer timer = new CodeTimer("#" + i + " -> (" + (i - firstSentence + 1) + ")");
            engine.processMessage(sentence);
            timer.stop();
            totalTime += timer.getSeconds();

            WTagging[] expected = corpus.getSentencePosProps(i);
            WTagging[] actual = engine.getFactWTaggings(i - firstSentence + 1, false, level);

            sentenceCount += 1;
            wordCount += expected.length;

            // check chunk count
            if (expected.length != actual.length) {
                System.err.println("");
                System.err.println("ERROR in sentence [" + i + "]:");
                System.err.println(sentence);
                System.err.println("    expected " + expected.length + " tokens");
                System.err.println("    found " + actual.length + " tokens");
                System.err.println("");
                errorCount += Math.abs(expected.length - actual.length);
                errSentences.add(i);
                continue;
            }

            // check individual words
            int tmpErrCount = errorCount;
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
            }
            if (tmpErrCount != errorCount) {
                errSentences.add(i);
            }
        }

        TestUtil.saveKbAndMarkings(engine, level);

        if (errSentences.size() > 0) {
            System.out.println("");
            System.out.println("");
            System.out.println("Errorneous sentences:");
            System.out.println("-===================-");
            System.out.println("");
            for (int i : errSentences) {
                System.out.println(i);
            }
        }

        System.out.println("");
        System.out.println("");
        System.out.println("  Errors: " + errorCount + " errors in " + errSentences.size() + " sentences, for " + wordCount + " words in " + sentenceCount + " sentences");
        System.out.println("Duration: " + timeFormatter.format(totalTime / wordCount) + " seconds per word, " + timeFormatter.format(totalTime / sentenceCount) + " seconds per sentence");

        // fail if at least one error
        assertEquals(0, errorCount);
    }
}
