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
import org.junit.Test;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.engine.Engine;
import org.purl.net.wonderland.engine.Level1Personality;
import org.purl.net.wonderland.engine.Personality;
import org.purl.net.wonderland.kb.WKBUtil;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class GoldBuildTest {

    Personality pers = new Level1Personality();
    String level = WKBUtil.level1;
    String corpusFileName = "bedtime/story.txt";
    int firstSentence = 1;
    int lastSentence = 42;
    //
    private final Engine engine;

    public GoldBuildTest() throws Exception {
        engine = new Engine();
        engine.setPersonality(pers);
    }

    // @Test
    public void testOne() throws Exception {
        String resp = engine.processMessage("May they come with the summer!");
        assertEquals("Done.", resp);

        TestUtil.saveKbAndMarkings(engine, level);
    }

    @Test
    public void testMany() throws Exception {
        Configuration.init();
        WKBUtil.normalizeKbFile(Configuration.getDefaultParseKBFile());

        MAFCorpus corpus = new MAFCorpus(new File(Configuration.getCorporaFolder(), corpusFileName));

        if (lastSentence < firstSentence) {
            lastSentence = corpus.getLineCount() - 1;
        }

        for (int i = firstSentence; i <= lastSentence; ++i) {
            String line = corpus.getPlainLine(i - 1);
            System.out.println("  " + line);

            CodeTimer timer = new CodeTimer("#" + i + " -> (" + (i - firstSentence + 1) + ")");
            String resp = engine.processMessage(line);
            timer.stop();

            assertEquals("Done.", resp);
        }

        engine.saveKb(new File("test.cogxml"));
        corpus.buildLevelXml(engine, level);
        corpus.saveLevelXml(new File("test.xml"), level);
    }
}
