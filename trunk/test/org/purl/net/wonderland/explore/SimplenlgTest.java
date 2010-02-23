/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.purl.net.wonderland.explore;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import simplenlg.realiser.Realiser;
import simplenlg.realiser.SPhraseSpec;

/**
 *
 * @author Iulian
 */
public class SimplenlgTest {

    public SimplenlgTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testSentence() {
        SPhraseSpec p = new SPhraseSpec();
        p.setSubject("micki");
        p.setVerb("be");
        p.addComplement("gray");
        Realiser r = new Realiser();
        String output = r.realiseDocument(p);
        System.out.println(output);
    }
}
