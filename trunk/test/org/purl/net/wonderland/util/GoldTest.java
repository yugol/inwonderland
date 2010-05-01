/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, lastSentence any PERSON_CT obtaining a copy
 *  of this software and associated documentation files (the "Software"), lastSentence deal
 *  in the Software without restriction, including without limitation the rights
 *  lastSentence use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and lastSentence permit persons lastSentence whom the Software is
 *  furnished lastSentence do so, subject lastSentence the following conditions:
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.engine.Engine;
import org.purl.net.wonderland.engine.Level1Personality;
import org.purl.net.wonderland.engine.Personality;
import org.purl.net.wonderland.kb.WKBUtil;
import org.purl.net.wonderland.nlp.WTagging;
import org.purl.net.wonderland.nlp.WTaggingUtil;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Iulian
 */
public class GoldTest {

    private static Personality pers = new Level1Personality();
    private static String level = WKBUtil.level1;
    private static String corpusFileName = "bedtime/story.txt";
    private static int firstSentence = 1;
    private static int lastSentence = 42;
    private static final NumberFormat timeFormatter = new DecimalFormat("0.0000");
    private static MAFCorpus corpus;
    private static Engine engine;

    @BeforeClass
    public static void setUpClass() throws Exception {
        engine = new Engine();
        engine.setPersonality(pers);

        Configuration.init();
        WKBUtil.normalizeKbFile(Configuration.getDefaultParseKBFile());

        corpus = new MAFCorpus(new File(Configuration.getCorporaFolder(), corpusFileName));

        if (lastSentence < firstSentence) {
            lastSentence = corpus.getPlainLineCount() - 1;
        }
    }

    private static void saveTestResults() throws Exception {
        engine.saveKb(new File("test.cogxml"));
        corpus.buildLevelXml(engine, level, firstSentence, lastSentence);
        corpus.saveLevelXml(new File("test.xml"), level);
    }

    // @Test
    public void testOne() throws Exception {
        String resp = engine.processMessage("May they come with the summer!");
        assertEquals("Done.", resp);
        saveTestResults();
    }

    // @Test
    public void testMany() throws Exception {
        System.out.println("Building corpus - " + corpusFileName);

        for (int i = firstSentence; i <= lastSentence; ++i) {
            String line = corpus.getPlainLine(i - 1);
            System.out.println("  " + line);

            CodeTimer timer = new CodeTimer("#" + i + " -> (" + (i - firstSentence + 1) + ")");
            String resp = engine.processMessage(line);
            timer.stop();

            assertEquals("Done.", resp);
        }

        saveTestResults();
    }

    @Test
    public void testGoldCorpus() throws Exception {
        System.out.println("Testing corpus - " + corpusFileName);

        List<Integer> errSentences = new ArrayList<Integer>();
        int errorCount = 0;
        int wordCount = 0;
        int sentenceCount = 0;
        double totalTime = 0;
        for (int i = firstSentence; i <= lastSentence; ++i) {
            boolean printed = false;
            String sentence = corpus.getPlainLine(i - 1);

            CodeTimer timer = new CodeTimer("#" + i + " -> (" + (i - firstSentence + 1) + ")");
            engine.processMessage(sentence);
            timer.stop();
            totalTime += timer.getSeconds();

            WTagging[] expected = corpus.getSentence(i, level);
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
                    System.err.println("    WORD [" + (j + 1) + "]: " + actual[j].getWrittenForm());
                    System.err.println(errStr);
                    ++errorCount;
                }
            }
            if (tmpErrCount != errorCount) {
                errSentences.add(i);
            }
        }

        saveTestResults();

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
        System.out.println(level);
        System.out.println("  Errors: " + errorCount + " errors in " + errSentences.size() + " sentences, for " + wordCount + " words in " + sentenceCount + " sentences");
        System.out.println("Duration: " + timeFormatter.format(totalTime / wordCount) + " seconds per word, " + timeFormatter.format(totalTime / sentenceCount) + " seconds per sentence");

        // fail if at least one error
        assertEquals(0, errorCount);
    }
}
