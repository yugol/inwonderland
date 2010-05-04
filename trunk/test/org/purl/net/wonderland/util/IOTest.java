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
package org.purl.net.wonderland.util;

import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.Configuration;
import org.purl.net.wonderland.kb.WkbConstants;
import org.purl.net.wonderland.kb.Wkb;
import org.purl.net.wonderland.kb.WkbUtil_;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class IOTest {

    public IOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testWriteProcs() throws Exception {
        Wkb kb = new Wkb(Configuration.getDefaultParseKBFile());
        File file = new File("test.rules.cogxml");
        IO.writeProcs(WkbUtil_.toProcName(WkbUtil_.procSetArticles, null), kb, file);
    }

    /*
    @Test
    public void testReadProcs() throws Exception {
    System.out.println("readProcs");
    Wkb kb = null;
    File file = null;
    IO.readProcs(kb, file);
    fail("The test case is a prototype.");
    }
     * 
     */
}
