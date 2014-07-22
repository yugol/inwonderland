package aibroker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import aibroker.model.drivers.csv.CsvWriter;
import aibroker.util.Moment;
import aibroker.util.StringPrintStream;

@SuppressWarnings("serial")
public class Quotes extends ArrayList<Ohlc> {

    public Quotes() {
        super();
    }

    public Quotes(final int initialSize) {
        super(initialSize);
    }

    public List<Ohlc> slice(final Moment first) {
        return slice(first, null);
    }

    public List<Ohlc> slice(final Moment first, final Moment last) {
        int firstIdx = Collections.binarySearch(this, new Ohlc(first, 0, 0));
        if (firstIdx < 0) {
            firstIdx = -firstIdx - 1;
        }
        int lastIdx = size();
        if (last != null) {
            lastIdx = Collections.binarySearch(this, new Ohlc(last, 0, 0));
            if (lastIdx < 0) {
                lastIdx = -lastIdx - 1;
            }
        }
        return subList(firstIdx, lastIdx);
    }

    public List<Ohlc> sliceDay(Moment day) {
        day = day.getBeginningOfDay();
        int firstIdx = -1;
        int lastIdx = -1;
        for (int i = 0; i < size(); ++i) {
            if (firstIdx < 0) {
                if (get(i).moment.getBeginningOfDay().equals(day)) {
                    firstIdx = i;
                }
                continue;
            }
            if (!get(i).moment.getBeginningOfDay().equals(day)) {
                lastIdx = i;
                break;
            }
        }
        if (firstIdx >= 0) {
            if (lastIdx < 0) {
                lastIdx = size();
            }
            return subList(firstIdx, lastIdx);
        }
        return null;
    }

    public void sort() {
        Collections.sort(this);
    }

    @Override
    public String toString() {
        final StringPrintStream sps = StringPrintStream.getNewInstance();
        CsvWriter.writeQuotes(sps, null, this);
        return sps.toString();
    }

}
