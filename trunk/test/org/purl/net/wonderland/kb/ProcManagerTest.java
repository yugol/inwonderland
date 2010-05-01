/*
 *  The MIT License
 * 
 *  Copyright 2010 Iulian Goriac <iulian.goriac@gmail.com>.
 * 
 *  Permission is hereby granted, free of charge, to any PERSON_CT obtaining a copy
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
package org.purl.net.wonderland.kb;

import org.purl.net.wonderland.kb.Procedure;
import org.purl.net.wonderland.kb.ProcManager;
import org.purl.net.wonderland.kb.WKB;
import java.io.File;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.WKBUtil;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class ProcManagerTest {

    public ProcManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testReadProcsFromKb() throws Exception {
        WKB kb = new WKB(new File(Configuration.getTestFolder(), "bedtime.cogxml"));
        ProcManager procMgr = new ProcManager(kb);
        procMgr.readAllProceduresFromKb();
        assertEquals(1, procMgr.getProcCount());
        List<Procedure> matches = procMgr.findMatches(WKBUtil.procSetMoods, WKBUtil.toLevel1FactId(1));
        assertEquals(1, matches.size());
    }
}
