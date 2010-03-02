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
package org.purl.net.wonderland.kb.generators;

import org.purl.net.wonderland.kb.WKnowledgeBase;
import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class GenManagerTest {

    public GenManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testReadGenerators() throws Exception {
        WKnowledgeBase kb = new WKnowledgeBase(new File(Globals.getTestFolder(), "bedtime.cogxml"));
        GenManager gm = new GenManager(kb);
        gm.readGenerators(new File(Globals.getTestFolder(), "test_generators.xml"));
        assertEquals(1, gm.getGenCount());
    }
}
