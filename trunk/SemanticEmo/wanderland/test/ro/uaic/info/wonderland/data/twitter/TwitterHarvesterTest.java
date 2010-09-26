/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.uaic.info.wonderland.data.twitter;

import java.util.List;
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
public class TwitterHarvesterTest {

    public TwitterHarvesterTest() {
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
     * Test of harvest method, of class TwitterHarvester.
     */
    @Test
    public void testHarvest() {
        System.out.println("harvest");
        String topic = "cern";
        TwitterHarvester instance = new TwitterHarvester();
        List result = instance.harvest(topic);
        assertTrue(result.size() > 0);
    }

}