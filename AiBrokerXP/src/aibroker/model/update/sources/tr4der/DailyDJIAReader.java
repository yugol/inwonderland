package aibroker.model.update.sources.tr4der;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
import org.jumpmind.symmetric.csv.CsvReader;
import org.slf4j.Logger;
import aibroker.Context;
import aibroker.model.Ohlc;
import aibroker.model.QuotesDatabase;
import aibroker.model.Sequence;
import aibroker.model.SequenceBuilder;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Grouping;
import aibroker.model.drivers.csv.CsvDatabase;
import aibroker.util.Moment;
import aibroker.util.convenience.Stocks;

public class DailyDJIAReader {

    public static void getQuotes(final QuotesDatabase qDb) {
        logger.debug("Downloading daily quotes for DJI ...");
        File quotesFile = null;
        try {
            quotesFile = File.createTempFile("$$$", UUID.randomUUID().toString());
            FileUtils.copyURLToFile(new URL("http://www.tr4der.com/download/historical-prices/%5EDJI/"), quotesFile);
            final CsvReader reader = new CsvReader(quotesFile.getAbsolutePath(), CsvDatabase.DELIMITER.charAt(0));
            reader.readHeaders();

            Sequence sequence = qDb.getSequence(SequenceSelector.fromName(Stocks.DEDJIA_RON.supportSymbol));
            if (sequence == null) {
                sequence = qDb.add(new SequenceBuilder(Stocks.DEDJIA_RON.supportSymbol).grouping(Grouping.OHLC));
            }
            sequence.getQuotes().clear();

            while (reader.readRecord()) {
                final Moment moment = Moment.fromCompactIso(reader.get("<DTYYYYMMDD>"));
                final float open = Float.parseFloat(reader.get("<OPEN>"));
                final float high = Float.parseFloat(reader.get("<HIGH>"));
                final float low = Float.parseFloat(reader.get("<LOW>"));
                final float close = Float.parseFloat(reader.get("<CLOSE>"));
                final int volume = 0; //Integer.parseInt(reader.get("<VOL>"));
                final Ohlc quote = new Ohlc(moment, open, high, low, close, volume);
                sequence.getQuotes().add(quote);
            }

            reader.close();
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (quotesFile != null) {
                quotesFile.delete();
            }
        }
    }

    private static final Logger logger = Context.getLogger(DailyDJIAReader.class);

}
