package aibroker.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SeqSelTest {

    @Test
    public void testFromName() {
        final SeqSel selector = SeqSel.fromName("DESIF5-IUN12");
        assertEquals("DESIF5", selector.getSymbol());
        assertEquals("DESIF5-IUN12", selector.getName());
    }

}
