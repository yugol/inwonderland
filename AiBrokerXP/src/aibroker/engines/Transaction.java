package aibroker.engines;

import aibroker.util.Moment;

public class Transaction {

    private final String symbol;
    private Moment       moment;
    private float        price;
    private int          volume;
    private int          openInt;

    public Transaction(final String symbol) {
        this.symbol = symbol;
    }

    public Moment getMoment() {
        return moment;
    }

    public int getOpenInt() {
        return openInt;
    }

    public float getPrice() {
        return price;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getVolume() {
        return volume;
    }

    public void setMoment(final Moment moment) {
        this.moment = moment;
    }

    public void setOpenInt(final int openInt) {
        this.openInt = openInt;
    }

    public void setPrice(final float price) {
        this.price = price;
    }

    public void setVolume(final int volume) {
        this.volume = volume;
    }

    public String toCsv() {
        return symbol + "," + moment.toIsoDate() + "," + moment.toIsoTime() + "," + price + "," + volume + "," + openInt;
    }

    @Override
    public String toString() {
        return "Transaction [symbol=" + symbol + ", moment=" + moment + ", price=" + price + ", volume=" + volume + ", openInt=" + openInt + "]";
    }

}
