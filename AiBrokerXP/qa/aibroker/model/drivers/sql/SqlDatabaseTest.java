package aibroker.model.drivers.sql;

import static org.junit.Assert.assertNotNull;
import java.io.File;
import java.util.Calendar;
import org.junit.Test;
import aibroker.Context;
import aibroker.model.Sequence;
import aibroker.model.SequenceDescriptor;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Grouping;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.util.FileUtil;
import aibroker.util.Moment;
import aibroker.util.convenience.Databases;

public class SqlDatabaseTest {

    public final static File DB_FILE = new File(Context.getQuotesFolder(), "test." + FileUtil.QDB_EXTENSION);

    // @Test
    public void testAddSequence() throws Exception {
        final SqlDatabase sqlDb = new SqlDatabase(DB_FILE);

        SequenceDescriptor b = new SequenceDescriptor("sif5");
        b.name("SIF5");
        b.market(Market.REGS);
        b.grouping(Grouping.OHLC);
        b.sampling(Sampling.DAILY);
        b.feed(Feed.ORIG);
        b.favourite(false);
        sqlDb.add(b);

        b = new SequenceDescriptor("DESIF5", new Moment(2007, Calendar.JUNE, 30));
        b.name("DESIF5-IUN07");
        b.market(Market.FUTURES);
        b.grouping(Grouping.TICK);
        b.sampling(Sampling.SECOND);
        b.feed(Feed.ORIG);
        b.multiplier(1000D);
        b.margin(500D);
        b.favourite(true);
        sqlDb.add(b);

        sqlDb.close();
    }

    // @Test
    public void testJoin() throws Exception {
        final SqlDatabase sqlDb = (SqlDatabase) Databases.SQL_DEFAULT.getInstance();
        final SequenceSelector sel = new SequenceSelector();
        sel.setMarket(Market.FUTURES);
        sel.setSymbol("DEAPL");
        sel.setFeed(Feed.ORIG);
        sel.setSampling(Sampling.SECOND);
        sel.setGrouping(Grouping.TICK);

        sel.setJoinSettlements(true);
        final Sequence deapl = sqlDb.getSequence(sel);
        deapl.dumpCsv();
        sqlDb.close();
    }

    @Test
    public void testSqlDatabase() throws Exception {
        final SqlDatabase sqlDb = new SqlDatabase(DB_FILE);
        assertNotNull(sqlDb);
        System.out.println(sqlDb.countRows());
        sqlDb.close();
    }

}
