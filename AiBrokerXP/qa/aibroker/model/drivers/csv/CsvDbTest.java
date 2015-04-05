package aibroker.model.drivers.csv;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Seq;
import aibroker.model.SeqSel;

public class CsvDbTest {

    private final CsvDb csvDb = new CsvDb(TestConfig.TEST_CSV_DB_FOLDER);

    @Test
    public void testCsvDatabase() {
        assertEquals(2, csvDb.getSequenceCount());
    }

    @Test
    public void testIO() {
        final Seq sequence = csvDb.getSequence(SeqSel.fromName("BRK"));
        assertEquals(62, sequence.getQuotes().size());
    }

}