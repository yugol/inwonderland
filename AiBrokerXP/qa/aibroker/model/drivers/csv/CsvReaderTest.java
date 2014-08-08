package aibroker.model.drivers.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.io.File;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Quotes;
import aibroker.model.Seq;
import aibroker.model.SeqSel;
import aibroker.model.domains.Market;
import aibroker.model.drivers.sql.SqlDb;

public class CsvReaderTest {

    @Test
    public void testReadQuotes_sdohlcv() {
        final File csv = new File(TestConfig.TEST_CSV_DB_FOLDER + "/BRK.EOD.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(62, quotes.size());
    }

    @Test
    public void testReadQuotes_sdohlcvi() {
        final File csv = new File(TestConfig.TEST_CSV_DB_FOLDER + "/DEBRK-IUN12.EOD.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(1, quotes.size());
    }

    @Test
    public void testReadQuotes_sdtcvi() {
        final File csv = new File(TestConfig.TEST_CSV_DB_FOLDER + "/DEBRK-IUN12.T.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(1, quotes.size());
    }

    @Test
    public void testReadQuotes_sdtohlcvi() {
        final File csv = new File(TestConfig.TEST_CSV_DB_FOLDER + "/BRK.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(62, quotes.size());
    }

    @Test
    public void testReadSibexFuturesLog() throws Exception {
        final File log = new File(TestConfig.TEST_RES_FOLDER + "/sibex-quotes-2014-07-21.csv");
        final SqlDb sqlDb = new SqlDb(TestConfig.TEST_SQL_DB_FILE);
        sqlDb.drop();
        CsvReader.readSibexFuturesLog(sqlDb, log);
        final SeqSel sSel = new SeqSel();
        sSel.setMarket(Market.FUTURES);
        sSel.setName("DEDJIA_RON-SEP14");
        final Seq seq = sqlDb.getSequence(sSel);
        assertNotNull(seq);
    }

}
