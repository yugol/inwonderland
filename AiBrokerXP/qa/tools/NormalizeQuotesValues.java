package tools;

import java.sql.SQLException;
import aibroker.model.Ohlc;
import aibroker.model.QuotesDb;
import aibroker.model.Sequence;
import aibroker.model.SequenceSelector;
import aibroker.model.domains.Market;
import aibroker.model.drivers.sql.SqlSequence;
import aibroker.util.Moment;
import aibroker.util.convenience.Databases;

public class NormalizeQuotesValues {

    public static void main(final String... args) throws SQLException {
        final QuotesDb qdb = Databases.SQL_DEFAULT.getInstance();
        final SequenceSelector EURUSD = new SequenceSelector();
        EURUSD.setMarket(Market.FUTURES);
        EURUSD.setSymbol("EUR/USD_RON");
        EURUSD.setSettlement(Moment.fromIso("2012-12-02"));
        final Sequence seq = qdb.getSequence(EURUSD);
        System.out.println(seq.toString());
        for (final Ohlc ohlc : seq.getQuotes()) {
            if (ohlc.open > 10000) {
                ohlc.open = ohlc.open / 10000;
            }
            if (ohlc.high > 10000) {
                ohlc.high = ohlc.high / 10000;
            }
            if (ohlc.low > 10000) {
                ohlc.low = ohlc.low / 10000;
            }
            if (ohlc.close > 10000) {
                ohlc.close = ohlc.close / 10000;
            }
        }
        // CsvWriter.writeQuotes(System.out, seq);
        ((SqlSequence) seq).updateQuotes();
        System.out.println("Done.");
    }

}
