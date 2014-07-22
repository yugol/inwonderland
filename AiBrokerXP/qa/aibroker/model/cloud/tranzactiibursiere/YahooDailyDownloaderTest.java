package aibroker.model.cloud.tranzactiibursiere;

import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import aibroker.model.Ohlc;
import aibroker.model.domains.Updater;
import aibroker.model.update.CloudDataSource;
import aibroker.util.Moment;

public class YahooDailyDownloaderTest {

    @Test
    public void testDownload() throws Exception {
        final CloudDataSource source = CloudDataSource.getDataSource(Updater.YAHOO_DAILY);
        final Moment date = Moment.fromIso("2014-01-01");
        for (int i = 0; i < 100; ++i) {
            final List<Ohlc> quotes = source.getQuotes("FB", null, date);
            if (quotes.size() == 1) {
                System.out.println(quotes.get(0).toString());
            }
            date.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

}
