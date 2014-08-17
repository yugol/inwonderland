package aibroker.model.drivers.sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.Calendar;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Seq;
import aibroker.model.SeqDesc;
import aibroker.model.SeqSel;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.util.Moment;
import aibroker.util.convenience.Databases;

public class SqlDbTest {

    @Test
    public void testAddSequence() throws Exception {
        final SqlDb sqlDb = new SqlDb(TestConfig.TEST_SQL_DB_FILE);
        sqlDb.drop();
        assertEquals(0, sqlDb.getSequenceCount());

        SeqDesc b = new SeqDesc("sif5");
        b.setName("SIF5");
        b.setMarket(Market.REGS);
        b.setGrouping(Grouping.OHLC);
        b.setSampling(Sampling.DAILY);
        b.setFeed(Feed.ORIG);
        b.setFavourite(false);
        sqlDb.add(b);
        assertEquals(1, sqlDb.getSequenceCount());

        b = new SeqDesc("DESIF5", new Moment(2007, Calendar.JUNE, 30));
        b.setName("DESIF5-IUN07");
        b.setMarket(Market.FUTURES);
        b.setGrouping(Grouping.TICK);
        b.setSampling(Sampling.SECOND);
        b.setFeed(Feed.ORIG);
        b.setMultiplier(1000D);
        b.setMargin(500D);
        b.setFavourite(true);
        b.setSupport("ABC");
        sqlDb.add(b);
        assertEquals(2, sqlDb.getSequenceCount());

        sqlDb.close();
    }

    @Test
    public void testJoin() throws Exception {
        final SqlDb sqlDb = new SqlDb(Databases.SQL_DEFAULT.url);
        final SeqSel sel = new SeqSel();
        sel.setMarket(Market.FUTURES);
        sel.setSymbol("DEAPL");
        sel.setFeed(Feed.ORIG);
        sel.setSampling(Sampling.SECOND);
        sel.setGrouping(Grouping.TICK);

        sel.setJoinSettlements(true);
        final Seq deapl = sqlDb.getSequence(sel);
        // deapl.dumpCsv();
        assertEquals(106, deapl.getQuotes().size());
        sqlDb.close();
    }

    @Test
    public void testSqlDatabase() throws Exception {
        final SqlDb sqlDb = new SqlDb(TestConfig.TEST_SQL_DB_FILE);
        assertNotNull(sqlDb);
        // System.out.println(sqlDb.countRows());
        sqlDb.close();
    }

}
