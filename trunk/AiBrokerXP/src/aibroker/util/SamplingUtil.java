package aibroker.util;

import java.util.Calendar;
import java.util.Iterator;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.domains.Sampling;

public final class SamplingUtil {

    public static Quotes resample(final Quotes baseQuotes, final Sampling baseSampling, final Sampling derivedSampling) {
        final int cmp = baseSampling.compareTo(derivedSampling);
        if (cmp == 0) {
            return baseQuotes;
        } else if (cmp < 0) {
            final SamplingUtil util = new SamplingUtil(baseQuotes, derivedSampling);
            util.resample();
            return util.getDerivedQuotes();
        } else {
            throw new BrokerException("Cannot refine sampling " + baseSampling + " to " + derivedSampling);
        }
    }

    private final Quotes   baseQuotes;
    private final Quotes   derivedQuotes;
    private final Sampling derivedSampling;

    private long           from = 0;
    private long           to   = 0;
    private long           ohlcTime;

    private Moment         moment;
    private float          open;
    private float          high;
    private float          low;
    private float          close;
    private int            volume;
    private int            openInt;

    private SamplingUtil(final Quotes baseQuotes, final Sampling derivedSampling) {
        this.baseQuotes = baseQuotes;
        this.derivedSampling = derivedSampling;
        derivedQuotes = new Quotes();
    }

    private Moment calculateInterval(final Moment m) {
        if (Sampling.MIN_1 == derivedSampling) {
            final Moment foo = new Moment(m.getTimeInMillis());
            foo.set(Calendar.SECOND, 0);
            from = foo.getTimeInMillis();
            foo.add(Calendar.SECOND, 59);
            to = foo.getTimeInMillis();
        } else if (Sampling.MIN_5 == derivedSampling) {
            final Moment foo = new Moment(m.getTimeInMillis());
            foo.set(Calendar.SECOND, 0);
            foo.set(Calendar.MINUTE, foo.get(Calendar.MINUTE) / 5 * 5);
            from = foo.getTimeInMillis();
            foo.add(Calendar.SECOND, 299);
            to = foo.getTimeInMillis();
        } else if (Sampling.MIN_10 == derivedSampling) {
            final Moment foo = new Moment(m.getTimeInMillis());
            foo.set(Calendar.SECOND, 0);
            foo.set(Calendar.MINUTE, foo.get(Calendar.MINUTE) / 10 * 10);
            from = foo.getTimeInMillis();
            foo.add(Calendar.SECOND, 599);
            to = foo.getTimeInMillis();
        } else if (Sampling.MIN_15 == derivedSampling) {
            final Moment foo = new Moment(m.getTimeInMillis());
            foo.set(Calendar.SECOND, 0);
            foo.set(Calendar.MINUTE, foo.get(Calendar.MINUTE) / 15 * 15);
            from = foo.getTimeInMillis();
            foo.add(Calendar.SECOND, 899);
            to = foo.getTimeInMillis();
        } else if (Sampling.MIN_30 == derivedSampling) {
            final Moment foo = new Moment(m.getTimeInMillis());
            foo.set(Calendar.SECOND, 0);
            foo.set(Calendar.MINUTE, foo.get(Calendar.MINUTE) / 30 * 30);
            from = foo.getTimeInMillis();
            foo.add(Calendar.SECOND, 1799);
            to = foo.getTimeInMillis();
        } else if (Sampling.HOURLY == derivedSampling) {
            final Moment foo = new Moment(m.getTimeInMillis());
            foo.set(Calendar.MINUTE, 0);
            foo.set(Calendar.SECOND, 0);
            from = foo.getTimeInMillis();
            foo.add(Calendar.SECOND, 3599);
            to = foo.getTimeInMillis();
        } else if (Sampling.DAILY == derivedSampling) {
            from = m.getBeginningOfDay().getTimeInMillis();
            to = m.getEndOfDay().getTimeInMillis();
        } else if (Sampling.WEEKLY == derivedSampling) {
            final Moment foo = m.getBeginningOfDay();
            while (foo.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                foo.add(Calendar.DAY_OF_YEAR, -1);
            }
            from = foo.getTimeInMillis();
            foo.add(Calendar.DAY_OF_YEAR, 7);
            to = foo.getEndOfDay().getTimeInMillis();
        } else if (Sampling.MONTHLY == derivedSampling) {
            final Moment foo = m.getBeginningOfDay();
            foo.set(Calendar.DAY_OF_MONTH, 1);
            from = foo.getTimeInMillis();
            foo.add(Calendar.MONTH, 1);
            foo.add(Calendar.DAY_OF_YEAR, -1);
            to = foo.getEndOfDay().getTimeInMillis();
        } else if (Sampling.QUARTERLY == derivedSampling) {
            final int year = m.getYear();
            final int month = m.getMonth() / 3 * 3;
            from = new Moment(year, month, 1).getTimeInMillis();
            final Moment foo = new Moment(m.getYear(), month + 3, 1);
            foo.add(Calendar.DAY_OF_YEAR, -1);
            to = foo.getEndOfDay().getTimeInMillis();
        } else if (Sampling.YEARLY == derivedSampling) {
            final Moment foo = new Moment(m.getYear(), 0, 1);
            from = foo.getTimeInMillis();
            foo.add(Calendar.YEAR, 1);
            foo.add(Calendar.DAY_OF_YEAR, -1);
            to = foo.getEndOfDay().getTimeInMillis();
        } else {
            throw new BrokerException("Unsupported sampling " + derivedSampling);
        }
        // System.out.println(new Moment(from).toIsoDatetime() + " --> " + new Moment(to).toIsoDatetime());
        return new Moment(from);
    }

    private Quotes getDerivedQuotes() {
        return derivedQuotes;
    }

    private void resample() {
        final Iterator<Ohlc> it = baseQuotes.iterator();
        while (it.hasNext()) {
            final Ohlc ohlc = it.next();
            ohlcTime = ohlc.moment.getTimeInMillis();
            if (from <= ohlcTime && ohlcTime <= to) {
                if (high < ohlc.high) {
                    high = ohlc.high;
                }
                if (low > ohlc.low) {
                    low = ohlc.low;
                }
                close = ohlc.close;
                volume += ohlc.volume;
                openInt = ohlc.openInt;
            } else {
                final Moment nextMoment = calculateInterval(ohlc.moment);
                if (moment != null) {
                    derivedQuotes.add(new Ohlc(moment, open, high, low, close, volume, openInt));
                }
                moment = nextMoment;
                open = ohlc.open;
                high = ohlc.high;
                low = ohlc.low;
                close = ohlc.close;
                volume = ohlc.volume;
                openInt = ohlc.openInt;
            }
        }
        if (moment != null) {
            derivedQuotes.add(new Ohlc(moment, open, high, low, close, volume, openInt));
        }
    }

}
