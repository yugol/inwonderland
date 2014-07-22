package aibroker.model;

public class BidAsk {

    private final String symbol;
    private float        ask;
    private float        bid;

    public BidAsk(final String symbol) {
        this.symbol = symbol;
    }

    public float getAsk() {
        return ask;
    }

    public float getBid() {
        return bid;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setAsk(final float ask) {
        this.ask = ask;
    }

    public void setBid(final float bid) {
        this.bid = bid;
    }

}
