/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.emo;

import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class EmoOntologyTest {

    public EmoOntologyTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getEmoCategories method, of class EmoOntology.
     */
    @Test
    public void testOntology() throws FileNotFoundException {
        EmoOntology instance = new EmoOntology();
        assertEquals(16, instance.getEmoCategories().length);
        assertEquals(1, instance.getEmoCategories()[0].getIndex());
        assertEquals(64, instance.getEmotions().length);
        for (Emotion emo : instance.getEmotions()) {
            System.out.println(emo.getCode() + " -> " + emo.getLabel());
        }
    }

    @Test
    public void testCategorisation() throws FileNotFoundException {
        EmoOntology ont = new EmoOntology();
        int cat = ont.getEmoGEWCategory(36, -7);
        assertEquals(16, cat);
        cat = ont.getEmoGEWCategory(-36, -7);
        assertEquals(9, cat);
        cat = ont.getEmoGEWCategory(61, 12);
        assertEquals(1, cat);
        cat = ont.getEmoGEWCategory(-20, 31);
        assertEquals(6, cat);
    }
}
