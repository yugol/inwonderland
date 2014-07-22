package aibroker.model;

import aibroker.util.Moment;

public class Ohlc implements Comparable<Ohlc> {

    public Moment moment;
    public float  open;
    public float  high;
    public float  low;
    public float  close;
    public int    volume;
    public int    openInt;

    public Ohlc(final Moment moment, final float open, final float high, final float low, final float close, final int volume) {
        this(moment, open, high, low, close, volume, 0);
    }

    public Ohlc(final Moment moment, final float open, final float high, final float low, final float close, final int volume, final int openInt) {
        this.moment = moment;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.openInt = openInt;
    }

    public Ohlc(final Moment moment, final float tick, final int volume) {
        this(moment, tick, tick, tick, tick, volume, 0);
    }

    public Ohlc(final Moment moment, final float tick, final int volume, final int openInt) {
        this(moment, tick, tick, tick, tick, volume, openInt);
    }

    @Override
    public int compareTo(final Ohlc other) {
        return moment.compareTo(other.moment);
    }

    @Override
    public String toString() {
        return "Ohlc [moment=" + moment + ", open=" + open + ", high=" + high + ", low=" + low + ", close=" + close + ", volume=" + volume + ", openInt=" + openInt + "]";
    }

}
