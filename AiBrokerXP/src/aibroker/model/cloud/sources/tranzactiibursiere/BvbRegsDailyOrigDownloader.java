package aibroker.model.cloud.sources.tranzactiibursiere;

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

public class BvbRegsDailyOrigDownloader extends CloudDataSource {

    private static final int                        CASH_SIZE = 150;

    private static final BvbRegsDailyOrigDownloader instance  = new BvbRegsDailyOrigDownloader();

    public static BvbRegsDailyOrigDownloader getInstance() {
        return instance;
    }

    BvbRegsDailyOrigDownloader() {
    }

    @Override
    protected void downloadQuotesFor(final String symbol, final String settlement, final Moment date) throws Exception {
        final Moment firstDate = date.getBeginningOfDay();
        final Moment lastDate = date.getEndOfDay();
        lastDate.add(Calendar.DAY_OF_YEAR, CASH_SIZE);

        File quotesFile = null;
        try {
            final StringBuilder url = getDownloadUrl(symbol, firstDate, lastDate);

            quotesFile = File.createTempFile("$$$", UUID.randomUUID().toString());
            FileUtils.copyURLToFile(new URL(url.toString()), quotesFile);
            final CsvReader reader = new CsvReader(quotesFile.getAbsolutePath(), CsvDb.DELIMITER.charAt(0));
            reader.readHeaders();

            while (reader.readRecord()) {
                final Moment moment = Moment.fromIso(reader.get("Data"));
                final float open = Float.parseFloat(reader.get("Desch"));
                final float high = Float.parseFloat(reader.get("Maxim"));
                final float low = Float.parseFloat(reader.get("Minim"));
                final float close = Float.parseFloat(reader.get("Inchid"));
                final int volume = Integer.parseInt(reader.get("Volum"));

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

    protected StringBuilder getDownloadUrl(final String symbol, final Moment firstDate, final Moment lastDate) {
        final StringBuilder url = new StringBuilder();
        url.append("http://www.tranzactiibursiere.ro/detalii/istoric_csv?lang=en&symbol=");
        url.append(symbol);
        url.append("&market=REGS&sdate=");
        url.append(firstDate.toIsoDate());
        url.append("&edate=");
        url.append(lastDate.toIsoDate());
        url.append("&type=original&x=%2C");
        return url;
    }

}
