package aibroker.analysis;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import aibroker.Context;
import aibroker.model.SeqSel;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.SqlDb;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.util.convenience.Databases;

public class BasicQuotesReportTest {

    @BeforeClass
    public static void atTheBeginning() throws SQLException {
        sqlDb = new SqlDb(Databases.SQL_DEFAULT.url);
        SS.setSymbol("TLV");
        SS.setMarket(Market.REGS);
        SS.setFeed(Feed.ORIG);
        SS.setSampling(Sampling.DAILY);
    }

    @AfterClass
    public static void atTheEnd() throws SQLException {
        sqlDb.close();
    }

    private static SqlDb        sqlDb;
    private static final SeqSel SS = new SeqSel();

    @Test
    public void testFill() throws IOException {
        final SqlSeq seq = sqlDb.getSequence(SS);
        final BasicQuotesReport report = new BasicQuotesReport(sqlDb, seq);
        report.fill();
        final File file = new File(Context.getExportFolder(), "_" + report.getNameHint());
        report.save(file);
        // System.out.println("Saved in: " + file.getCanonicalPath());
        assertTrue(file.exists());
        file.deleteOnExit();
    }

}
