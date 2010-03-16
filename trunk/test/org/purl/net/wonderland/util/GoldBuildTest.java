/*
 *  The MIT License
 *
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 *
 *  Permission is hereby granted, free of charge, lastSentence any person obtaining a copy
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
import java.util.List;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.engine.Engine;
import org.purl.net.wonderland.engine.Level1Personality;
import org.purl.net.wonderland.engine.Level2Personality;
import org.purl.net.wonderland.engine.Personality;
import org.purl.net.wonderland.kb.KbUtil;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class GoldBuildTest {

    @Test
    public void testOne() throws Exception {
        Engine engine = new Engine();
        engine.setPersonality(pers);

        String resp = engine.processMessage("May they come with the summer!");
        assertEquals("Done.", resp);

        TestUtil.saveKbAndMarkings(engine, level);
    }
    Personality pers = new Level1Personality();
    String level = KbUtil.level1;
    int firstSentence = 983;
    int lastSentence = 27;

    // @Test
    public void testMany() throws Exception {
        Globals.init();
        KbUtil.normalizeKbFile(Globals.getDefaultParseKBFile());

        List<String> lines = IO.getFileContentAsStringList(new File(Globals.getCorporaFolder(), "egcp.train.level0.txt"));
        Engine engine = new Engine();
        engine.setPersonality(pers);

        if (lastSentence < firstSentence) {
            lastSentence = lines.size() - 1;
        }

        for (int i = firstSentence; i <= lastSentence; ++i) {
            String line = lines.get(i - 1);
            // System.out.println("  " + line);

            CodeTimer timer = new CodeTimer("#" + i + " -> (" + (i - firstSentence + 1) + ")");
            String resp = engine.processMessage(line);
            timer.stop();

            assertEquals("Done.", resp);
        }

        TestUtil.saveKbAndMarkings(engine, level);
    }
}
