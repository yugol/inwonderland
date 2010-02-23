/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.nlp.resources;

import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
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
    public void testLookup() {
        String word = "nil";
        new MorphAdornerWrapper();
        IndexWord result = WordNetWrapper.lookup(word, POS.ADJECTIVE);
        assertNull(result);
    }
}
