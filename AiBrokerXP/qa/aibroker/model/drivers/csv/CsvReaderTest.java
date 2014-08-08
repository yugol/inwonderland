package aibroker.model.drivers.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Quotes;
import aibroker.model.Sequence;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Market;
import aibroker.model.drivers.sql.SqlDatabase;

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

    @Test
    public void testReadSibexFuturesLog() throws Exception {
        final File log = new File(TestConfig.CSV_TEST_DATABASE_FOLDER + "/sibex-quotes-2014-07-21.csv");
        final SqlDatabase sqlDb = new SqlDatabase(TestConfig.SQL_DB_FILE);
        sqlDb.drop();
        CsvReader.readSibexFuturesLog(sqlDb, log);
        final SequenceSelector sSel = new SequenceSelector();
        sSel.setMarket(Market.FUTURES);
        sSel.setName("DEDJIA_RON-SEP14");
        final Sequence seq = sqlDb.getSequence(sSel);
        assertNotNull(seq);
    }

}
