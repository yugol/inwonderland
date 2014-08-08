package aibroker.util.convenience;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aibroker.model.SeqSel;

public class SelectorsTest {

    public static void main(final String... args) {
        for (final SeqSel value : Selectors.values()) {
            System.out.println(value.getName());
        }
    }

    @Test
    public void testValueOf() {
        final SeqSel selector = Selectors.valueOf("DEAPL");
        assertEquals("DEAPL", selector.getName());
    }

}
