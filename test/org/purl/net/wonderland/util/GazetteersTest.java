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
package org.purl.net.wonderland.util;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian Goriac <iulian.goriac@gmail.com>
 */
public class GazetteersTest {

    @Test
    public void wtagging() {
        assertEquals(15, Gazetteers.personalPronoun.keySet().size());
    }

    @Test
    public void sets() {
        assertTrue(Gazetteers.femaleFirstName.contains("elizabeth"));
    }

    @Test
    public void selRestr() {
        assertEquals(30, Gazetteers.selRestrs.size());
        assertEquals("ct_abstract", Gazetteers.selRestrs.get("ct_n00002137"));
    }

    @Test
    public void getSenses() {
        assertEquals(5, Gazetteers.senseFile2wns.size());
        assertEquals("ct_n00007846", Gazetteers.senseFile2wns.get("lastName").get(0));
        List<String> senses = Gazetteers.getWNSensesFor("elizabeth");
        assertEquals(2, senses.size());
    }

    @Test
    public void pb2vn() {
        assertEquals(1, Gazetteers.pb2vn.size());
        assertEquals("own-100", Gazetteers.pb2vn.get("own.01"));
    }
}
