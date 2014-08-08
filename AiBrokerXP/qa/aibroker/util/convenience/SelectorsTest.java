package aibroker.util.convenience;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aibroker.model.SequenceSelector;

public class SelectorsTest {

    public static void main(final String... args) {
        for (final SequenceSelector value : Selectors.values()) {
            System.out.println(value.getName());
        }
    }

    @Test
    public void testValueOf() {
        final SequenceSelector selector = Selectors.valueOf("DEAPL");
        assertEquals("DEAPL", selector.getName());
    }

}
