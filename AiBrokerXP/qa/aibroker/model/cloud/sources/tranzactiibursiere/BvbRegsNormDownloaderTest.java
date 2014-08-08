package aibroker.model.cloud.sources.tranzactiibursiere;

import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import aibroker.model.Ohlc;
import aibroker.model.cloud.CloudDataSource;
import aibroker.model.domains.Updater;
import aibroker.util.Moment;

public class BvbRegsNormDownloaderTest {

    @Test
    public void testDownloadQuotesFor() throws Exception {
        final CloudDataSource source = CloudDataSource.getDataSource(Updater.BVB_REG_DAILY_NORM);
        final Moment date = Moment.fromIso("2001-01-01");
        for (int i = 0; i < 100; ++i) {
            final List<Ohlc> quotes = source.getQuotes("SIF5", null, date);
            if (quotes.size() == 1) {
                System.out.println(quotes.get(0).toString());
            }
            date.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

}