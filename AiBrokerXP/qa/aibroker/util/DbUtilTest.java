package aibroker.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import java.io.File;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Ohlc;
import aibroker.model.Seq;
import aibroker.model.SeqSel;
import aibroker.model.domains.Market;
import aibroker.model.drivers.sql.SqlDb;

public class DbUtilTest {

    @Test
    public void testAddQuote() throws Exception {
        final SqlDb sqlDb = new SqlDb(TestConfig.TEST_SQL_DB_FILE);
        sqlDb.drop();
        assertEquals(0, sqlDb.getSequenceCount());

        SeqSel sel = DbUtil.addQuote(sqlDb, "AAPL", new Ohlc(Moment.fromIso("2014-07-01"), 2f, 4f, 1f, 3f, 10));
        assertEquals(1, sqlDb.getSequenceCount());
        Seq seq = sqlDb.getSequence(sel);
        assertEquals(1, seq.getQuotes().size());

        sel = DbUtil.addQuote(sqlDb, "AAPL", new Ohlc(Moment.fromIso("2014-07-02"), 2f, 4f, 1f, 3f, 10));
        assertEquals(1, sqlDb.getSequenceCount());
        seq = sqlDb.getSequence(sel);
        assertEquals(2, seq.getQuotes().size());

        sel = DbUtil.addQuote(sqlDb, "TLV", new Ohlc(Moment.fromIso("2014-07-01"), 2f, 4f, 1f, 3f, 10));
        assertEquals(2, sqlDb.getSequenceCount());
        seq = sqlDb.getSequence(sel);
        assertEquals(1, seq.getQuotes().size());

        sel = DbUtil.addQuote(sqlDb, "DEDJIA_RON-SEP14", new Ohlc(Moment.fromIso("2014-07-01 23:00:00"), 4f, 1, 10));
        assertEquals(4, sqlDb.getSequenceCount());
        seq = sqlDb.getSequence(sel);
        assertEquals(1, seq.getQuotes().size());

        sel = DbUtil.addQuote(sqlDb, "DEDJIA_RON-DEC14", new Ohlc(Moment.fromIso("2014-07-01 23:00:01"), 6f, 2, 5));
        assertEquals(5, sqlDb.getSequenceCount());
        seq = sqlDb.getSequence(sel);
        assertEquals(1, seq.getQuotes().size());

        sel = DbUtil.addQuote(sqlDb, "DEDJIA_RON-SEP14", new Ohlc(Moment.fromIso("2014-07-01 23:00:02"), 7f, 3, 2));
        assertEquals(5, sqlDb.getSequenceCount());
        seq = sqlDb.getSequence(sel);
        assertEquals(2, seq.getQuotes().size());

        sqlDb.close();
    }

    // @Test
    public void testReadSibexFuturesLog() throws Exception {
        final File log = new File(TestConfig.TEST_RES_FOLDER + "/sibex-quotes-2014-07-21.csv");
        final SqlDb sqlDb = new SqlDb(TestConfig.TEST_SQL_DB_FILE);
        sqlDb.drop();
        DbUtil.readSibexFuturesLog(sqlDb, log);
        final SeqSel sSel = new SeqSel();
        sSel.setMarket(Market.FUTURES);
        sSel.setName("DEDJIA_RON-SEP14");
        final Seq seq = sqlDb.getSequence(sSel);
        assertNotNull(seq);
    }

    // @Test
    public void testReadSibexLog() throws Exception {
        fail("Not yet implemented");
    }

}
