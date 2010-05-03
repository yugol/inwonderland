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

import fr.lirmm.rcr.cogui2.kernel.model.Rule;
import java.io.File;
import java.util.List;
import org.junit.Test;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.WKB;
import org.purl.net.wonderland.kb.WKBUtil;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class VerbTest {

    // @Test
    public void testAllVerbs() throws Exception {
        for (File file : Configuration.getPropBankDataFolder().listFiles()) {
            String lemma = file.getName();
            int endIndex = lemma.lastIndexOf(".xml");
            if (endIndex > 0) {
                lemma = lemma.substring(0, endIndex);
                Verb v = new Verb(lemma);
                System.out.println(">- " + v.getLemma());
            }
        }
        for (String tag : WSDProcManager.syntaxTags) {
            System.out.println(tag);
        }
    }

    @Test
    public void testOneVerb() throws Exception {
        Verb v = new Verb("abduct");
        System.out.println(v.getLemma());
    }

    // @Test
    public void testOneVerbToProcs() throws Exception {
        WSDPersonality pers = new WSDPersonality();
        Verb v = new Verb("abduct");
        List<Rule> procs = v.getVerbNetProcs(pers);
        WKB kb = pers.getKb();
        for (Rule proc : procs) {
            kb.addRule(proc);
        }
        File file = new File("test." + v.getLemma() + ".cogxml");
        kb.save(file);
        // IO.writeProcs(procs, WSDProcManager.pers.getKb(), file);
        WKBUtil.normalizeKbFile(file);
    }
}