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
package org.purl.net.wonderland.nlp.resources;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class VerbNetWrapperTest {

    public VerbNetWrapperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testGetFileList() {
        List result = VerbNetWrapper.getFileList();
        assertEquals(270, result.size());
    }

    @Test
    public void testGetVerbClasses() {
        VerbForm result = VerbNetWrapper.getVerbClasses("make");
        assertEquals(3, result.getVnClasses().size());
        assertEquals(8, result.getWnSenses("build-26.1-1").size());

        result = VerbNetWrapper.getVerbClasses("abound");
        assertEquals(1, result.getVnClasses().size());
        assertEquals(2, result.getWnSenses("swarm-47.5.1-2-1").size());

        result = VerbNetWrapper.getVerbClasses("wriggle_out");
        assertEquals(1, result.getVnClasses().size());
        assertEquals(0, result.getWnSenses("withdraw-82-1").size());


    }
}
