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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import org.purl.net.wonderland.cg.CogxmlIO;
import org.purl.net.wonderland.cg.KnowledgeBase;
import org.purl.net.wonderland.nlp.resources.VerbNetWrapper;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class VerbTest {

    //@Test
    public void discoverVerbs() throws Exception {
        List<String> verbs = new ArrayList<String>(VerbNetWrapper.getVerbIndex().keySet());
        Collections.sort(verbs);
        String startFrom = "spawn";
        for (String lemma : verbs) {
            if (startFrom.compareTo(lemma) <= 0) {
                Verb v = new Verb(lemma);
                System.out.println("* " + v.getLemma());
                v.getVnClases();
                v.buildProcRules();
            }
        }
    }

    // @Test
    public void testOneVerb() throws Exception {
        Verb v = new Verb("steal");
        System.out.println(v.getLemma());
    }

    @Test
    public void testOneVerbToProcs() throws Exception {
        Verb v = new Verb("know");
        List<KnowledgeBase> rules = v.buildProcRules();
        for (int i = 0; i < rules.size(); i++) {
            File file = new File("rules." + v.getLemma() + "-" + i + ".cogxml");
            CogxmlIO.writeCogxmlFile(rules.get(i), file);
        }
        v.storeProcRules(new File("rules." + v.getLemma() + ".cogxml"));
    }
}
