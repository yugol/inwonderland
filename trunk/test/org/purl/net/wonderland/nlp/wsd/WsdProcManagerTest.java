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
import org.junit.Test;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.Proc;
import org.purl.net.wonderland.kb.ProcList;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbUtil;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class WsdProcManagerTest {

    @Test
    public void testGetVerbProcs() throws Exception {
        Wkb kb = new Wkb(Configuration.getDefaultParseKBFile());
        WsdProcManager wdsMgr = new WsdProcManager(kb);
        String lemma = "have";
        ProcList procs = wdsMgr.getVerbProcs(lemma);
        assertNotNull(procs);
        for (Proc proc : procs.getProcs()) {
            kb.addRule(proc.getRule(procs.getName()));
        }
        File kbFile = new File("wsd.cogxml");
        kb.save(kbFile);
        WkbUtil.normalizeKbFile(kbFile);
    }
}
