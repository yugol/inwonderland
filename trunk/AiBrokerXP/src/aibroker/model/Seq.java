package aibroker.model;

import java.util.List;
import aibroker.model.domains.Updater;
import aibroker.model.drivers.csv.CsvWriter;
import aibroker.util.Moment;

public abstract class Seq {

    protected final QuotesDb qDb;
    protected final String   symbol;
    protected Moment         settlement;
    protected Updater        updater;
    protected Quotes         quotes;

    protected Seq(final QuotesDb qDb, final SeqDesc sb) {
        this.qDb = qDb;
        symbol = sb.getSymbol().trim().toUpperCase();
        settlement = sb.getSettlement();
        updater = sb.getUpdater();
    }

    public void addQuotes(final List<Ohlc> quotes) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void addQuotes(final Ohlc ohlc) throws Exception {
        final Quotes quotes = new Quotes(1);
        quotes.add(ohlc);
        addQuotes(quotes);
    }

    public void clearQuotes() {
        quotes = null;
    }

    public void deleteQuotes(final Moment date) throws Exception {
        throw new UnsupportedOperationException();
    }

    public void dumpCsv() {
        CsvWriter.writeQuotes(System.out, this);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null) { return false; }
        if (other instanceof Seq) { return ((Seq) other).getName().equalsIgnoreCase(getName()); }
        return false;
    }

    public QuotesDb getDatabase() {
        return qDb;
    }

    public Ohlc getLastQuote() {
        if (getQuotes() != null && !quotes.isEmpty()) { return quotes.get(quotes.size() - 1); }
        return null;
    }

    public String getName() {
        return SeqDesc.getName(symbol, settlement);
    }

    public Quotes getQuotes() {
        if (quotes == null) {
            quotes = qDb.readQuotes(this);
        }
        return quotes;
    }

    public Moment getSettlement() {
        return settlement;
    }

    public String getSettlementString() {
        if (settlement != null) { return SeqDesc.toSettlementString(settlement); }
        return null;
    }

    public String getSymbol() {
        return symbol;
    }

    public Updater getUpdater() {
        return updater;
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public boolean isRegular() {
        return settlement == null;
    }

    public void setQuotes(final Quotes quotes) {
        this.quotes = quotes;
    }

    public void setUpdater(final Updater updater) {
        this.updater = updater;
    }

    @Override
    public String toString() {
        return getName();
    }

}
