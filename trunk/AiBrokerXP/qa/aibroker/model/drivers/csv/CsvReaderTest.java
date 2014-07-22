package aibroker.model.drivers.csv;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Quotes;
import aibroker.model.drivers.csv.CsvReader;

public class CsvReaderTest {

    @Test
    public void testReadQuotes_sdohlcv() {
        final File csv = new File(TestConfig.CSV_TEST_DATABASE_FOLDER + "/BRK.EOD.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(62, quotes.size());
    }

    @Test
    public void testReadQuotes_sdohlcvi() {
        final File csv = new File(TestConfig.CSV_TEST_DATABASE_FOLDER + "/DEBRK-IUN12.EOD.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(1, quotes.size());
    }

    @Test
    public void testReadQuotes_sdtcvi() {
        final File csv = new File(TestConfig.CSV_TEST_DATABASE_FOLDER + "/DEBRK-IUN12.T.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(1, quotes.size());
    }

    @Test
    public void testReadQuotes_sdtohlcvi() {
        final File csv = new File(TestConfig.CSV_TEST_DATABASE_FOLDER + "/BRK.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(62, quotes.size());
    }

}
