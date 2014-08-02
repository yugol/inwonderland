package aibroker.model.update.sources.bvb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import aibroker.model.SequenceDescriptor;

public class BvbSequenceDescriptionReaderTest {

    @Test
    public void testReadDescription_FP() throws IOException {
        final SequenceDescriptor sDesc = BvbSequenceDescriptionReader.readDescription("FP");
        assertEquals(100, sDesc.getBlockSize());
        assertTrue(sDesc.getLastPrice() < 1f);
    }

    @Test
    public void testReadDescription_TLV() throws IOException {
        final SequenceDescriptor sDesc = BvbSequenceDescriptionReader.readDescription("TLV");
        assertEquals(10, sDesc.getBlockSize());
        assertTrue(sDesc.getLastPrice() > 1f);
    }

}
