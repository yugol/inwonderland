package aibroker.util;

import aibroker.model.Ohlc;
import aibroker.model.Quotes;

public class MergeUtil {

    public static Quotes mergeQuotes(final Quotes oldQuotes, final Quotes newQuotes) {
        final Quotes dest = new Quotes();
        oldQuotes.sort();
        newQuotes.sort();
        int oldIdx = 0;
        int newIdx = 0;
        while (oldIdx < oldQuotes.size() && newIdx < newQuotes.size()) {
            Ohlc oldOhlc = oldQuotes.get(oldIdx);
            Ohlc newOhlc = newQuotes.get(newIdx);
            Moment oldMoment = oldOhlc.moment.getBeginningOfDay();
            Moment newMoment = newOhlc.moment.getBeginningOfDay();
            int cmp = oldMoment.compareTo(newMoment);
            if (cmp <= 0) {
                while (cmp == 0) {
                    ++newIdx;
                    if (newIdx < newQuotes.size()) {
                        newOhlc = newQuotes.get(newIdx);
                        newMoment = newOhlc.moment.getBeginningOfDay();
                        cmp = oldMoment.compareTo(newMoment);
                    } else {
                        break;
                    }
                }
                do {
                    dest.add(oldOhlc);
                    ++oldIdx;
                    if (oldIdx < oldQuotes.size()) {
                        oldOhlc = oldQuotes.get(oldIdx);
                        oldMoment = oldOhlc.moment.getBeginningOfDay();
                        cmp = oldMoment.compareTo(newMoment);
                    } else {
                        break;
                    }
                } while (cmp < 0);
            } else if (cmp > 0) {
                do {
                    dest.add(newOhlc);
                    ++newIdx;
                    if (newIdx < newQuotes.size()) {
                        newOhlc = newQuotes.get(newIdx);
                        newMoment = newOhlc.moment.getBeginningOfDay();
                        cmp = oldMoment.compareTo(newMoment);
                    } else {
                        break;
                    }
                } while (cmp > 0);
            }
        }
        while (oldIdx < oldQuotes.size()) {
            dest.add(oldQuotes.get(oldIdx++));
        }
        while (newIdx < newQuotes.size()) {
            dest.add(newQuotes.get(newIdx++));
        }
        return dest;
    }

}
