package aibroker.model.cloud.sources.yahoo;

import java.io.File;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.jumpmind.symmetric.csv.CsvReader;
import aibroker.model.Ohlc;
import aibroker.model.cloud.CloudDataSource;
import aibroker.model.drivers.csv.CsvDb;
import aibroker.util.Moment;

public class YahooDailyDownloader extends CloudDataSource {

    private static final int                  CASH_SIZE = 150;

    private static final YahooDailyDownloader instance  = new YahooDailyDownloader();

    public static YahooDailyDownloader getInstance() {
        return instance;
    }

    private YahooDailyDownloader() {
    }

    @Override
    protected void downloadQuotesFor(final String symbol, final String settlement, final Moment date) throws Exception {
        final Moment firstDate = date.getBeginningOfDay();
        final Moment lastDate = date.getEndOfDay();
        lastDate.add(Calendar.DAY_OF_YEAR, CASH_SIZE);

        File quotesFile = null;
        try {
            final StringBuilder url = new StringBuilder("http://ichart.finance.yahoo.com/table.csv?");
            url.append("s=").append(symbol);
            url.append("&c=").append(firstDate.get(Calendar.YEAR));
            url.append("&a=").append(firstDate.get(Calendar.MONTH));
            url.append("&b=").append(firstDate.get(Calendar.DAY_OF_MONTH));
            url.append("&g=").append("d");
            url.append("&f=").append(lastDate.get(Calendar.YEAR));
            url.append("&d=").append(lastDate.get(Calendar.MONTH));
            url.append("&e=").append(lastDate.get(Calendar.DAY_OF_MONTH));
            url.append("&ignore=").append(".csv");

            quotesFile = File.createTempFile("$$$", UUID.randomUUID().toString());
            FileUtils.copyURLToFile(new URL(url.toString()), quotesFile);
            final CsvReader reader = new CsvReader(quotesFile.getAbsolutePath(), CsvDb.DELIMITER.charAt(0));
            reader.readHeaders();

            while (reader.readRecord()) {
                final Moment moment = Moment.fromIso(reader.get("Date"));
                final float open = Float.parseFloat(reader.get("Open"));
                final float high = Float.parseFloat(reader.get("High"));
                final float low = Float.parseFloat(reader.get("Low"));
                final float close = Float.parseFloat(reader.get("Close"));
                final int volume = (int) (Long.parseLong(reader.get("Volume")) / 1000);

                final Ohlc quote = new Ohlc(moment, open, high, low, close, volume);
                addQuote(symbol, settlement, quote);
            }

            reader.close();
        } finally {
            if (quotesFile != null) {
                quotesFile.delete();
            }
        }

        lastDate.add(Calendar.DAY_OF_YEAR, -1);
        while (firstDate.compareTo(lastDate) < 0) {
            addDate(symbol, settlement, firstDate);
            firstDate.add(Calendar.DAY_OF_YEAR, 1);
        }
        sortQuotes(symbol, settlement);
    }

}
