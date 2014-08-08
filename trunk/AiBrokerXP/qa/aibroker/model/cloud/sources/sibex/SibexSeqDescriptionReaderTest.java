package aibroker.model.cloud.sources.sibex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import java.io.IOException;
import org.junit.Test;
import aibroker.model.SeqDesc;

public class SibexSeqDescriptionReaderTest {

    @Test
    public void testReadDescription_DEDJIA_RON_SEP14() throws IOException {
        final SeqDesc sDesc = SibexSeqDescriptionReader.readDescription("DEDJIA_RON-SEP14");
        // assertEquals(Market.FUTURES, sDesc.market());
        assertEquals("DEDJIA_RON", sDesc.getSymbol());
        assertEquals("DEDJIA_RON-SEP14", sDesc.getName());
        assertEquals("2014-09-30", sDesc.getSettlement().toIsoDate());
        assertEquals("DJIA", sDesc.getSupport());
        assertEquals("1.0", sDesc.getMultiplier().toString());
        assertEquals("1.26", sDesc.getFee().toString());
        assertNull(sDesc.getMargin());
        // assertEquals(Updater.CACHED_SIBEX_FUT_TICK, sDesc.updater());
    }

}
