package aibroker.model.cloud.sources.bvb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import aibroker.model.SeqDesc;

public class BvbSeqDescriptionReaderTest {

    @Test
    public void testReadDescription_FP() throws IOException {
        final SeqDesc sDesc = BvbSeqDescriptionReader.readDescription("FP");
        assertEquals("FP", sDesc.getSymbol());
        assertEquals("FONDUL PROPRIETATEA", sDesc.getName());
        assertEquals(100, sDesc.getBlockSize());
        assertTrue(sDesc.getLastPrice() < 1f);
    }

    @Test
    public void testReadDescription_TLV() throws IOException {
        final SeqDesc sDesc = BvbSeqDescriptionReader.readDescription("TLV");
        assertEquals("TLV", sDesc.getSymbol());
        assertEquals("BANCA TRANSILVANIA S.A.", sDesc.getName());
        assertEquals(10, sDesc.getBlockSize());
        assertTrue(sDesc.getLastPrice() > 1f);
    }

}
