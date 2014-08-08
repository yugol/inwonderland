package aibroker.model.drivers.metastock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import aibroker.model.Seq;
import aibroker.model.SeqSel;
import aibroker.model.cloud.sources.bursanoastra.BursanoastraConfig;
import aibroker.model.domains.Sampling;

public class MetastockDatabaseTest {

    private final MetastockDatabase msDb;

    public MetastockDatabaseTest() {
        msDb = new MetastockDatabase(BursanoastraConfig.METASTOCK_DATABASE_FOLDER);
    }

    @Test
    public void testGetQuotes() {
        for (final Seq sequence : msDb) {
            assertNotNull(sequence.getQuotes());
        }
    }

    @Test
    public void testGetSequenceCount() {
        assertEquals(255, msDb.getSequenceCount());
    }

    @Test
    public void testGetSequenceInt() {
        final MetastockSequence sequence = (MetastockSequence) msDb.getSequence(SeqSel.fromName("AEM"));
        assertEquals("AEM", sequence.getName());
        assertEquals(Sampling.DAILY, sequence.getSampling());
        assertEquals(7, sequence.getFieldCount());
        assertEquals("1998-04-23", sequence.getFirstDate().toIsoDate());
        assertEquals("1999-12-22", sequence.getLastDate().toIsoDate());
    }

}
