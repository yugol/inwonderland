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
package org.purl.net.wonderland.engine;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.File;
import java.util.List;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.kb.KbUtil;
import org.purl.net.wonderland.util.Corpus;
import org.purl.net.wonderland.util.IO;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class EngineTest {

    Personality pers = new Level2Personality();
    String level = KbUtil.level2;
    static File candidateFile = new File("test.xml");

    public EngineTest() {
        candidateFile.delete();
    }

    private void mergeToCandidate(Engine instance) throws ParserConfigurationException, SAXException, IOException {
        Corpus candidate = new Corpus();
        candidate.buildFrom(candidateFile);
        candidate.addKnowledgeBase(instance, level);
        candidate.writeToFile(candidateFile);
    }

    // @Test
    public void testOne() throws Exception {
        Engine instance = new Engine();
        instance.setPersonality(pers);

        String resp = instance.processMessage("This school will hold more than one thousand pupils.");
        assertEquals("Done.", resp);

        File file = new File("test.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }

    @Test
    public void testMany() throws Exception {
        int from = 28;
        int to = 28;

        List<String> lines = IO.getFileContentAsStringList(new File(Globals.getCorporaFolder(), "egcp.train.level0.txt"));
        Engine instance = new Engine();
        instance.setPersonality(pers);

        from -= 1;
        to -= 1;
        if (to < 0) {
            to = lines.size() - 1;
        }
        if (to < from) {
            to = from;
        }

        int kbIndex = 0;
        for (int i = from; i <= to; ++i) {
            ++kbIndex;
            System.out.println("At line: [" + (i + 1) + "] -> (" + kbIndex + ")");
            String line = lines.get(i);
            System.out.println("  " + line);
            String resp = instance.processMessage(line);
            assertEquals("Done.", resp);
        }

        File file = new File("test.cogxml");
        instance.saveKb(file);
        mergeToCandidate(instance);
    }
}
