/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.nlp.resources;

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
    public void testLookup() {
        String word = "nil";
        new MorphAdornerWrapper();
        IndexWord result = WordNetWrapper.lookup(word, POS.ADJECTIVE);
        assertNull(result);
    }

    //@Test
    public void testSenses() {
        System.out.println("senses");
        IndexWord word = null;
        Synset[] expResult = null;
        Synset[] result = WordNetWrapper.senses(word);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    //@Test
    public void testContains() {
        System.out.println("contains");
        String str = "";
        boolean expResult = false;
        boolean result = WordNetWrapper.contains(str);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }
}
