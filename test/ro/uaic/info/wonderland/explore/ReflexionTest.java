/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.uaic.info.wonderland.explore;

import java.lang.reflect.Field;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Iulian
 */
public class ReflexionTest {

    static int value = 3;

    @Test
    public void useFields() throws IllegalArgumentException, IllegalAccessException {
        for (Field f : ReflexionTest.class.getDeclaredFields()) {
            System.out.println(f.getName());
            f.set(null, 4);
            Object o = f.get(null);
            System.out.println(o);
            assertEquals(4, value);
        }
    }
}
