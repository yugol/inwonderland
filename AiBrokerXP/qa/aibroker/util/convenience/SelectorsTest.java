package aibroker.util.convenience;

import org.junit.Test;
import aibroker.model.SequenceSelector;

public class SelectorsTest {

    @Test
    public void testValueOf() {
        final SequenceSelector selector = Selectors.valueOf("DEAPL");
        System.out.println(selector.getName());
    }

    @Test
    public void testValues() {
        for (final SequenceSelector value : Selectors.values()) {
            System.out.println(value.getName());
        }
    }

}
