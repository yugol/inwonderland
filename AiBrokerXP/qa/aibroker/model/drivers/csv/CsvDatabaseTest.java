package aibroker.model.drivers.csv;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Sequence;
import aibroker.model.SequenceSelector;

public class CsvDatabaseTest {

    private final CsvDatabase csvDb = new CsvDatabase(TestConfig.CSV_TEST_DATABASE_FOLDER);

    @Test
    public void testCsvDatabase() {
        assertEquals(2, csvDb.getSequenceCount());
    }

    @Test
    public void testIO() {
        final Sequence sequence = csvDb.getSequence(SequenceSelector.fromName("BRK"));
        assertEquals(62, sequence.getQuotes().size());
    }

}
