package aibroker.engines.markets.sibex;

import aibroker.engines.Transaction;
import aibroker.model.BidAsk;
import aibroker.model.SequenceDescriptor;
import aibroker.util.Moment;

public class Record {

    private final Moment moment;
    private String       symbol;
    private String       expiryMonth;
    private float        bidPrice;
    private float        askPrice;
    private float        lastPrice;
    private int          volume;
    private int          trades;
    private int          openInt;

    public Record() {
        moment = Moment.getNow();
    }

    public float getAskPrice() {
        return askPrice;
    }

    public float getBidPrice() {
        return bidPrice;
    }

    public String getExpiryMonth() {
        return expiryMonth;
    }

    public String getId() {
        return SequenceDescriptor.getName(symbol, expiryMonth);
    }

    public float getLastPrice() {
        return lastPrice;
    }

    public Moment getMoment() {
        return moment;
    }

    public String getName() {
        return SequenceDescriptor.getName(getSymbol(), getExpiryMonth());
    }

    public int getOpenInt() {
        return openInt;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getTrades() {
        return trades;
    }

    public int getVolume() {
        return volume;
    }

    public void setAskPrice(final float askPrice) {
        this.askPrice = askPrice;
    }

    public void setBidPrice(final float bidPrice) {
        this.bidPrice = bidPrice;
    }

    public void setExpiryMonth(final String expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public void setLastPrice(final float lastPrice) {
        this.lastPrice = lastPrice;
    }

    public void setOpenInt(final int openInt) {
        this.openInt = openInt;
    }

    public void setSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public void setTrades(final int trades) {
        this.trades = trades;
    }

    public void setVolume(final int volume) {
        this.volume = volume;
    }

    public BidAsk toBidAsk() {
        final BidAsk ba = new BidAsk(getName());
        ba.setAsk(getAskPrice());
        ba.setBid(getBidPrice());
        return ba;
    }

    @Override
    public String toString() {
        return "Record [id=" + getId() + ", moment=" + moment + ", symbol=" + symbol + ", expiryMonth=" + expiryMonth + ", lastPrice=" + lastPrice + ", volume=" + volume + ", trades=" + trades + ", openInt=" + openInt + "]";
    }

    public Transaction toTransaction() {
        final Transaction t = new Transaction(getName());
        t.setMoment(getMoment());
        t.setPrice(getLastPrice());
        t.setVolume(getVolume());
        t.setOpenInt(getOpenInt());
        return t;
    }

    public Transaction toTransaction(final Record prevRecord) {
        final Transaction t = toTransaction();
        t.setVolume(t.getVolume() - prevRecord.getVolume());
        return t;
    }

}
