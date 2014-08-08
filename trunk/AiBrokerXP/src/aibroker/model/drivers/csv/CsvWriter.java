package aibroker.model.drivers.csv;

import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.Locale;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.Seq;

public final class CsvWriter {

    private static final DecimalFormat UI_PRICE_FORMATTER   = new DecimalFormat("######0.0000");
    private static final DecimalFormat UI_VOLUME_FORMATTER  = new DecimalFormat("#########0");
    private static final DecimalFormat UI_OPENINT_FORMATTER = new DecimalFormat("#######0");

    public static String getQuotesHeader() {
        return "  Date         Time     | Open         | High         | Low          | Close        | Volume     | OpenInt.";
    }

    public static void writeQuotes(final PrintStream out, final Seq sequence) {
        final String symbol = sequence.getName();
        final Quotes quotes = sequence.getQuotes();
        writeQuotes(out, symbol, quotes);
    }

    public static void writeQuotes(final PrintStream out, final String symbol, final Quotes quotes) {
        final StringBuilder sb = new StringBuilder();
        for (int qNo = 0; qNo < quotes.size(); ++qNo) {
            final Ohlc ohlc = quotes.get(qNo);
            if (symbol != null) {
                sb.append(symbol);
                sb.append(CsvDb.DELIMITER);
            }
            sb.append(ohlc.moment.toIsoDate());
            sb.append(CsvDb.DELIMITER).append(ohlc.moment.toIsoTime());
            sb.append(CsvDb.DELIMITER).append(String.format(Locale.ENGLISH, "%8.4f", ohlc.open));
            sb.append(CsvDb.DELIMITER).append(String.format(Locale.ENGLISH, "%8.4f", ohlc.high));
            sb.append(CsvDb.DELIMITER).append(String.format(Locale.ENGLISH, "%8.4f", ohlc.low));
            sb.append(CsvDb.DELIMITER).append(String.format(Locale.ENGLISH, "%8.4f", ohlc.close));
            sb.append(CsvDb.DELIMITER).append(String.format(Locale.ENGLISH, "%10d", ohlc.volume));
            sb.append(CsvDb.DELIMITER).append(String.format(Locale.ENGLISH, "%8d", ohlc.openInt));
            sb.append("\n");
        }
        out.print(sb);
    }

    public static void writeQuotesData(final StringBuffer out, final Quotes quotes) {
        for (int i = quotes.size() - 1; i >= 0; --i) {
            final Ohlc ohlc = quotes.get(i);
            out.append("  ");
            out.append(ohlc.moment.toIsoDatetime().replace(" ", " , "));
            out.append(" , ");
            out.append(formatPrice(ohlc.open));
            out.append(" , ");
            out.append(formatPrice(ohlc.high));
            out.append(" , ");
            out.append(formatPrice(ohlc.low));
            out.append(" , ");
            out.append(formatPrice(ohlc.close));
            out.append(" , ");
            out.append(formatVolume(ohlc.volume));
            out.append(" , ");
            out.append(formatOpenInt(ohlc.openInt));
            out.append("\n");
        }
    }

    private static String formatOpenInt(final int volume) {
        String rep = UI_OPENINT_FORMATTER.format(volume);
        final int padSize = 8 - rep.length();
        if (padSize > 0) {
            final StringBuilder buff = new StringBuilder(" ");
            for (int i = 1; i < padSize; ++i) {
                buff.append(" ");
            }
            buff.append(rep);
            rep = buff.toString();
        }
        return rep;
    }

    private static String formatPrice(final float price) {
        String rep = UI_PRICE_FORMATTER.format(price);
        final int padSize = 12 - rep.length();
        if (padSize > 0) {
            final StringBuilder buff = new StringBuilder(" ");
            for (int i = 1; i < padSize; ++i) {
                buff.append(" ");
            }
            buff.append(rep);
            rep = buff.toString();
        }
        return rep;
    }

    private static String formatVolume(final int volume) {
        String rep = UI_VOLUME_FORMATTER.format(volume);
        final int padSize = 10 - rep.length();
        if (padSize > 0) {
            final StringBuilder buff = new StringBuilder(" ");
            for (int i = 1; i < padSize; ++i) {
                buff.append(" ");
            }
            buff.append(rep);
            rep = buff.toString();
        }
        return rep;
    }

}
