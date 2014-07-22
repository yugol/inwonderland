package aibroker.model.drivers.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.util.BrokerException;
import aibroker.util.Moment;

public class CsvReader {

    public static Quotes readQuotes(final File csv) {
        final Quotes quotes = new Quotes();
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(csv));
            String line;
            while ((line = csvReader.readLine()) != null) {
                if (line.startsWith("Symbol,Date")) {
                    continue;
                }
                final String[] chunks = line.split(CsvDatabase.DELIMITER);
                Ohlc ohlc = null;
                if (chunks.length == 9) {
                    // reading full OHLC
                    ohlc = new Ohlc(
                            Moment.fromIso(chunks[1].trim() + " " + chunks[2].trim()), // date-time
                            Float.parseFloat(chunks[3].trim()), // open
                            Float.parseFloat(chunks[4].trim()), // high
                            Float.parseFloat(chunks[5].trim()), // low
                            Float.parseFloat(chunks[6].trim()), // close
                            Integer.parseInt(chunks[7].trim()), // volume
                            Integer.parseInt(chunks[8].trim()) // open interest
                    );
                } else if (chunks.length == 8) {
                    // reading futures OHLC
                    ohlc = new Ohlc(
                            Moment.fromIso(chunks[1].trim()), // date
                            Float.parseFloat(chunks[2].trim()), // open
                            Float.parseFloat(chunks[3].trim()), // high
                            Float.parseFloat(chunks[4].trim()), // low
                            Float.parseFloat(chunks[5].trim()), // close
                            Integer.parseInt(chunks[6].trim()), // volume
                            Integer.parseInt(chunks[7].trim()) // open interest
                    );
                } else if (chunks.length == 7) {
                    // reading regular OHLC
                    ohlc = new Ohlc(
                            Moment.fromIso(chunks[1].trim()), // date
                            Float.parseFloat(chunks[2].trim()), // open
                            Float.parseFloat(chunks[3].trim()), // high
                            Float.parseFloat(chunks[4].trim()), // low
                            Float.parseFloat(chunks[5].trim()), // close
                            Integer.parseInt(chunks[6].trim()) // volume
                    );
                } else if (chunks.length == 6) {
                    // reading futures TICK
                    ohlc = new Ohlc(
                            Moment.fromIso(chunks[1].trim() + " " + chunks[2].trim()), // date-time
                            Float.parseFloat(chunks[3].trim()), // price
                            Integer.parseInt(chunks[4].trim()), // volume
                            Integer.parseInt(chunks[5].trim()) // open interest
                    );
                } else if (chunks.length == 5) {
                    // reading futures TICK
                    ohlc = new Ohlc(
                            Moment.fromIso(chunks[1].trim() + " " + chunks[2].trim()), // date-time
                            Float.parseFloat(chunks[3].trim()), // price
                            Integer.parseInt(chunks[4].trim()) // volume
                    );
                }
                quotes.add(ohlc);
            }
        } catch (final IOException e) {
            throw new BrokerException(e);
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (final IOException e) {
                    throw new BrokerException(e);
                }
            }
        }
        return quotes;
    }

}
