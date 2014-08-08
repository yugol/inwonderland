package aibroker.analysis;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import aibroker.Context;
import aibroker.model.SeqSel;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.SqlSeq;
import aibroker.util.convenience.Databases;

public class BasicQuotesReportTest {

    private static final SeqSel SS = new SeqSel();

    static {
        SS.setSymbol("TLV");
        SS.setMarket(Market.REGS);
        SS.setFeed(Feed.ORIG);
        SS.setSampling(Sampling.DAILY);
    }

    @Test
    public void testFill() throws IOException {
        final SqlSeq seq = Databases.DEFAULT().getSequence(SS);
        final BasicQuotesReport report = new BasicQuotesReport(Databases.DEFAULT(), seq);
        report.fill();
        final File file = new File(Context.getExportFolder(), "_" + report.getNameHint());
        report.save(file);
        // System.out.println("Saved in: " + file.getCanonicalPath());
        assertTrue(file.exists());
        file.deleteOnExit();
    }

}
