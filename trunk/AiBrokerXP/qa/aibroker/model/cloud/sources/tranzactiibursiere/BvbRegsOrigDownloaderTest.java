package aibroker.model.cloud.sources.tranzactiibursiere;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import aibroker.model.Ohlc;
import aibroker.model.cloud.CloudDataSource;
import aibroker.model.domains.Updater;
import aibroker.util.Moment;

public class BvbRegsOrigDownloaderTest {

    @Test
    public void testDownloadQuotesFor() throws Exception {
        final CloudDataSource source = CloudDataSource.getDataSource(Updater.BVB_REG_DAILY_BASE);
        final Moment date = Moment.fromIso("2001-01-01");
        int counter = 0;
        for (int i = 0; i < 365; ++i) {
            final List<Ohlc> quotes = source.getQuotes("SIF5", null, date);
            if (quotes.size() == 1) {
                // System.out.println(quotes.get(0).toString());
                counter++;
            }
            date.add(Calendar.DAY_OF_YEAR, 1);
        }
        assertEquals(239, counter);
    }

}
