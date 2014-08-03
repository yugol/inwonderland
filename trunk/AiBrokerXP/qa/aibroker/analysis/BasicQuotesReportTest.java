package aibroker.analysis;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import aibroker.Context;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Feed;
import aibroker.model.domains.Market;
import aibroker.model.domains.Sampling;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.convenience.Databases;

public class BasicQuotesReportTest {

    private static final SequenceSelector SS = new SequenceSelector();

    static {
        SS.setSymbol("TLV");
        SS.setMarket(Market.REGS);
        SS.setFeed(Feed.ORIG);
        SS.setSampling(Sampling.DAILY);
    }

    @Test
    public void testFill() throws IOException {
        final SqlSequence seq = Databases.DEFAULT().getSequence(SS);
        final BasicQuotesReport report = new BasicQuotesReport(Databases.DEFAULT(), seq);
        report.fill();
        final File file = new File(Context.getExportFolder(), "_" + report.getNameHint());
        report.save(file);
        System.out.println("Saved in: " + file.getCanonicalPath());
    }

}
