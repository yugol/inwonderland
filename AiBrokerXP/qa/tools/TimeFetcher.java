package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.drivers.csv.CsvReader;
import aibroker.model.drivers.csv.CsvWriter;
import aibroker.util.Moment;

public class TimeFetcher {

    public static void main(final String... args) throws FileNotFoundException {
        final File inFile = new File("/home/iulian/AiBrokerXP/quotes/archive/SIBEX.BVB 2009-04-01 ~ 2009-06-30 - notime/DESIF5-IUN09.T.csv");
        final File outFile = new File((String) null);
        final PrintStream out = new PrintStream(outFile);
        final Quotes originalQuotes = CsvReader.readQuotes(inFile);
        final Moment firstDay = originalQuotes.get(0).moment.getBeginningOfDay();
        final Moment lastDay = originalQuotes.get(originalQuotes.size() - 1).moment.getEndOfDay();
        while (firstDay.getTimeInMillis() <= lastDay.getTimeInMillis()) {
            final List<Ohlc> tmp = originalQuotes.sliceDay(firstDay);
            if (tmp != null) {
                System.out.println(firstDay.getBeginningOfDay());

                final Moment bod = new Moment(firstDay.getYear(), firstDay.getMonth(), firstDay.getDayOfMonth(), 10, 00, 01);
                final Moment eod = new Moment(firstDay.getYear(), firstDay.getMonth(), firstDay.getDayOfMonth(), 15, 59, 59);
                final double step = (double) (eod.getTimeInMillis() - bod.getTimeInMillis()) / (tmp.size() + 1) / 1000;

                final Quotes dailyQuotes = new Quotes();
                for (int i = 0; i < tmp.size(); ++i) {
                    final Moment m = new Moment(firstDay.getYear(), firstDay.getMonth(), firstDay.getDayOfMonth(), 10, 00, 01);
                    m.add(Calendar.SECOND, (int) (step * i));
                    final Ohlc quote = tmp.get(i);
                    final Ohlc ohlc = new Ohlc(m, quote.close, quote.volume, quote.openInt);
                    dailyQuotes.add(ohlc);
                }

                CsvWriter.writeQuotes(out, "DESIF5-IUN09", dailyQuotes);
            }
            firstDay.add(Calendar.DAY_OF_YEAR, 1);
        }
        out.close();
    }

}
