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

import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class WordNetWrapperTest {

    public WordNetWrapperTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSenseToId() {
        String id = WordNetWrapper.senseToId("insolvent%1:18:00");
        assertEquals("n09838370", id);
        Synset sense = WordNetWrapper.lookup(id);
        assertEquals("insolvent", sense.getWord(1).getLemma());

        id = WordNetWrapper.senseToId("inspect%2:31:00");
        assertEquals("v00697062", id);
        sense = WordNetWrapper.lookup(id);
        assertEquals("inspect", sense.getWord(3).getLemma());

        id = WordNetWrapper.senseToId("insolvent%3:00:00");
        assertEquals("a02026442", id);
        sense = WordNetWrapper.lookup(id);
        assertEquals("insolvent", sense.getWord(0).getLemma());

        id = WordNetWrapper.senseToId("insomuch%4:02:00");
        assertEquals("r00379233", id);
        sense = WordNetWrapper.lookup(id);
        assertEquals("insomuch", sense.getWord(0).getLemma());
    }

    @Test
    public void testLookup() throws JWNLException {
        String word = "door";
        IndexWord result = WordNetWrapper.lookup(word, POS.NOUN);
        assertNotNull(result);
        Synset[] senses = result.getSenses();
        assertEquals(5, senses.length);
        /*
        System.out.println("Key: " + result.getKey());
        System.out.println("Lemma: " + result.getLemma());
        for (Synset sense : senses) {
        System.out.println(sense.getGloss());
        Pointer[] ptrs = sense.getPointers(PointerType.HYPERNYM);
        for (Pointer ptr : ptrs) {
        long offset = ptr.getTargetOffset();
        sense = WordNetWrapper.lookup(offset, POS.NOUN);
        System.out.println("   " + sense.getWord(0).getLemma());
        }
        }
         */
    }
}
