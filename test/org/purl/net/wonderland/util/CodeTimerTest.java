/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.purl.net.wonderland.util;

import org.purl.net.wonderland.util.CodeTimer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class CodeTimerTest {

    public CodeTimerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testStop() {
        CodeTimer instance = new CodeTimer("test");
        instance.stop();
    }

}