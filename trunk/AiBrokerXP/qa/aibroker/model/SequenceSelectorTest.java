package aibroker.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SequenceSelectorTest {

    @Test
    public void testFromName() {
        final SequenceSelector selector = SequenceSelector.fromName("DESIF5-IUN12");
        assertEquals("DESIF5", selector.getSymbol());
        assertEquals("DESIF5-IUN12", selector.getName());
    }

}
