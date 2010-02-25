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
package org.purl.net.wonderland.kb.inference;

import org.purl.net.wonderland.kb.inference.provided.DefaultInfFactory;
import fr.lirmm.rcr.cogui2.kernel.model.CGraph;
import java.io.File;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.purl.net.wonderland.Globals;
import org.purl.net.wonderland.util.IO;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class InfKBTest {

    public InfKBTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSomeMethod() throws Exception {
        File cogxml = new File(Globals.getTestFolder(), "bedtime.cogxml");
        InfKB ikb = new InfKB(cogxml, new DefaultInfFactory(IO.getClassPathRoot(this.getClass())));
        assertEquals(1, ikb.getInfMgr().getInfCount());
        for (CGraph cg : ikb.getFactSetOrderedByNameIterator("level1")) {
            String name = cg.getName();
            System.out.println(name);
            List<Inference> infs = ikb.findMatches("l1", cg);
            int infCount = infs.size();
            System.out.println("  " + infCount + " matches");
            if (name.equals("0001")) {
                assertEquals(1, infCount);
            }
            for (Inference inf : infs) {
                inf.apply(ikb.getResultGraph());
            }
        }
        cogxml = new File(Globals.getTestFolder(), "bedtime.testout.cogxml");
        ikb.save(cogxml);
    }
}
