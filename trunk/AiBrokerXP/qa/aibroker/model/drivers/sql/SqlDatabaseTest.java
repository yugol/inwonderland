package aibroker.model.drivers.sql;

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

public class SqlDatabaseTest {

    @Test
    public void testAddSequence() throws Exception {
        final SqlDb sqlDb = new SqlDb(Databases.SQL_DEFAULT.url);

        SeqDesc b = new SeqDesc("sif5");
        b.name("SIF5");
        b.market(Market.REGS);
        b.grouping(Grouping.OHLC);
        b.sampling(Sampling.DAILY);
        b.feed(Feed.ORIG);
        b.favourite(false);
        sqlDb.add(b);

        b = new SeqDesc("DESIF5", new Moment(2007, Calendar.JUNE, 30));
        b.name("DESIF5-IUN07");
        b.market(Market.FUTURES);
        b.grouping(Grouping.TICK);
        b.sampling(Sampling.SECOND);
        b.feed(Feed.ORIG);
        b.multiplier(1000D);
        b.margin(500D);
        b.favourite(true);
        b.support("ABC");
        sqlDb.add(b);

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
        deapl.dumpCsv();
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
