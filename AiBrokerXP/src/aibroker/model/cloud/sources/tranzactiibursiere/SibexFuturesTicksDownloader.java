package aibroker.model.cloud.sources.tranzactiibursiere;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.jumpmind.symmetric.csv.CsvReader;
import aibroker.model.Ohlc;
import aibroker.model.cloud.CloudDataSource;
import aibroker.model.drivers.csv.CsvDb;
import aibroker.util.Moment;

public class SibexFuturesTicksDownloader extends CloudDataSource {

    private static final SibexFuturesTicksDownloader instance = new SibexFuturesTicksDownloader();

    public static SibexFuturesTicksDownloader getInstance() {
        return instance;
    }

    private SibexFuturesTicksDownloader() {
    }

    @Override
    public List<Ohlc> getQuotes(String symbol, final String settlement, final Moment date) throws Exception {
        if (symbol.length() > 10) {
            symbol = symbol.substring(0, 10);
        }
        return super.getQuotes(symbol, settlement, date);
    }

    @Override
    protected void downloadQuotesFor(final String dummySymbol, final String dummySettlement, final Moment date) throws Exception {
        File quotesFile = null;
        try {
            final StringBuilder url = new StringBuilder();
            url.append("http://www.tranzactiibursiere.ro/detalii/tranzactii_futures_csv?lang=en&symbol=&settle_date=&date=");
            url.append(date.toIsoDate());
            url.append("&x=%2C");

            quotesFile = File.createTempFile("$$$", UUID.randomUUID().toString());
            FileUtils.copyURLToFile(new URL(url.toString()), quotesFile);
            final CsvReader reader = new CsvReader(quotesFile.getAbsolutePath(), CsvDb.DELIMITER.charAt(0));
            reader.readHeaders();

            while (reader.readRecord()) {
                final String symbol = reader.get("Simbol");
                final String settlement = reader.get("Scadenta");
                final Moment moment = Moment.fromIso(reader.get("Data"), reader.get("Ora"));
                final float tick = Float.parseFloat(reader.get("Pret"));
                final int volume = Integer.parseInt(reader.get("Volum"));
                final int openInt = Integer.parseInt(reader.get("OpenInt"));
                final Ohlc quote = new Ohlc(moment, tick, volume, openInt);
                addQuote(symbol, settlement, quote);
            }

            reader.close();
        } finally {
            if (quotesFile != null) {
                quotesFile.delete();
            }
        }
        addDate(date);
        sortQuotes();
    }

}
