package aibroker.model.cloud.sources.tranzactiibursiere;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import java.util.List;
import org.junit.Test;
import aibroker.model.Ohlc;
import aibroker.model.cloud.CloudDataSource;
import aibroker.model.domains.Updater;
import aibroker.util.Moment;

public class SibexFuturesTicksDownloaderTest {

    @Test
    public void testDownloadQuotesFor() throws Exception {
        final CloudDataSource source = CloudDataSource.getDataSource(Updater.SIBEX_FUT_TICK);
        final Moment date = Moment.fromIso("2014-01-03");
        List<Ohlc> quotes = source.getQuotes("EUR/JPY_RON", "MAR14", Moment.fromIso("2014-01-01"));
        quotes = source.getQuotes("EUR/JPY_RON", "MAR14", Moment.fromIso("2014-01-02"));
        assertEquals(0, quotes.size());
        quotes = source.getQuotes("EUR/JPY_RON", "MAR14", Moment.fromIso("2014-01-03"));
        assertEquals(8, quotes.size());
        //for (final Ohlc ohlc : quotes) {
        // System.out.println(ohlc.toString());
        //}
        date.add(Calendar.DAY_OF_YEAR, 1);
    }

}
