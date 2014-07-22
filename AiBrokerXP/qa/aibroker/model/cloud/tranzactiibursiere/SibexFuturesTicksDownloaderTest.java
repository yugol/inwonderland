package aibroker.model.cloud.tranzactiibursiere;

import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import aibroker.model.Ohlc;
import aibroker.model.domains.Updater;
import aibroker.model.update.CloudDataSource;
import aibroker.util.Moment;

public class SibexFuturesTicksDownloaderTest {

    @Test
    public void testDownloadQuotesFor() throws Exception {
        final CloudDataSource source = CloudDataSource.getDataSource(Updater.SIBEX_FUT_TICK);
        final Moment date = Moment.fromIso("2014-01-03");
        List<Ohlc> quotes = source.getQuotes("EUR/JPY_RON", "MAR14", Moment.fromIso("2014-01-01"));
        quotes = source.getQuotes("EUR/JPY_RON", "MAR14", Moment.fromIso("2014-01-02"));
        quotes = source.getQuotes("EUR/JPY_RON", "MAR14", Moment.fromIso("2014-01-03"));
        for (final Ohlc ohlc : quotes) {
            System.out.println(ohlc.toString());
        }
        date.add(Calendar.DAY_OF_YEAR, 1);
    }

}
