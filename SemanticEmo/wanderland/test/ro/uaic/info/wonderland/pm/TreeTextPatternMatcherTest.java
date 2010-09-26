/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.uaic.info.wonderland.pm;

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
public class TreeTextPatternMatcherTest {

    public TreeTextPatternMatcherTest() {
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
     * Test of match method, of class TreeTextPatternMatcher.
     */
    @Test
    public void testEmotions() {
        TreeTextPatternMatcher instance = new TreeTextPatternMatcher();
        Object obj;
        boolean bool;
        
        bool = instance.add("anger", "angry");
        assertTrue(bool);
        bool = instance.add("ang", "angry2");
        assertTrue(bool);
        obj = instance.match("anger");
        assertEquals("angry", obj);
        obj = instance.match("angry");
        assertNull(obj);
        
        bool = instance.add("angr*", "angry");
        assertTrue(bool);
        obj = instance.match("ang");
        assertEquals("angry2", obj);
        obj = instance.match("angr");
        assertEquals("angry", obj);
        obj = instance.match("angry");
        assertEquals("angry", obj);
        obj = instance.match("angrysor");
        assertEquals("angry", obj);
    }

    @Test
    public void testStar() {
        TreeTextPatternMatcher instance = new TreeTextPatternMatcher();
        Object obj;
        boolean bool;

        obj = instance.match("");
        assertNull(obj);

        bool = instance.add("", "Thing");
        assertFalse(bool);
        bool = instance.add("*", "Thing");
        assertTrue(bool);
        obj = instance.match("anything");
        assertEquals("Thing", obj);
        obj = instance.match("");
        assertEquals("Thing", obj);

        bool = instance.add("soul", "soul1");
        assertFalse(bool);
    }

    @Test
    public void testSoul() {
        TreeTextPatternMatcher instance = new TreeTextPatternMatcher();
        Object obj;
        boolean bool;

        bool = instance.add("soul", "soul1");
        assertTrue(bool);
        bool = instance.add("soulful*", "soul2");
        assertTrue(bool);
        bool = instance.add("so*", "soul4");
        assertFalse(bool);
        bool = instance.add("so", "soul3");
        assertTrue(bool);
        bool = instance.add("soulful*", "soul2");
        assertFalse(bool);

        obj = instance.match("so");
        assertEquals("soul3", obj);
        obj = instance.match("soul");
        assertEquals("soul1", obj);
        obj = instance.match("soulful");
        assertEquals("soul2", obj);
        obj = instance.match("soulfulness");
        assertEquals("soul2", obj);
        obj = instance.match("sou");
        assertNull(obj);
        obj = instance.match("soulf");
        assertNull(obj);
        obj = instance.match("soulfus");
        assertNull(obj);
    }

}