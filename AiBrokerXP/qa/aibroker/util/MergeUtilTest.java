package aibroker.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;

public class MergeUtilTest {

    @Test
    public void testMergeQuotes_1() {
        final Quotes oldQuotes = new Quotes();
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:01"), 0, 0));
        final Quotes newQuotes = new Quotes();
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:02"), 1, 1));
        final Quotes mergedQuotes = MergeUtil.mergeQuotes(oldQuotes, newQuotes);
        assertEquals(4, mergedQuotes.size());
    }

    @Test
    public void testMergeQuotes_2() {
        final Quotes oldQuotes = new Quotes();
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:01"), 0, 0));
        final Quotes newQuotes = new Quotes();
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:02"), 1, 1));
        final Quotes mergedQuotes = MergeUtil.mergeQuotes(oldQuotes, newQuotes);
        assertEquals(4, mergedQuotes.size());
    }

    @Test
    public void testMergeQuotes_3() {
        final Quotes oldQuotes = new Quotes();
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:01"), 0, 0));
        final Quotes newQuotes = new Quotes();
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:02"), 1, 1));
        final Quotes mergedQuotes = MergeUtil.mergeQuotes(oldQuotes, newQuotes);
        assertEquals(3, mergedQuotes.size());
        assertEquals(oldQuotes.get(1).moment.getBeginningOfDay(), mergedQuotes.get(1).moment.getBeginningOfDay());
    }

    @Test
    public void testMergeQuotes_4() {
        final Quotes oldQuotes = new Quotes();
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:01"), 0, 0));
        final Quotes newQuotes = new Quotes();
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:02"), 1, 1));
        final Quotes mergedQuotes = MergeUtil.mergeQuotes(oldQuotes, newQuotes);
        assertEquals(3, mergedQuotes.size());
        assertEquals(oldQuotes.get(1).moment.getBeginningOfDay(), mergedQuotes.get(1).moment.getBeginningOfDay());
    }

    @Test
    public void testMergeQuotes_5() {
        final Quotes oldQuotes = new Quotes();
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-03", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-03", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-03", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-07", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-07", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-08", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-12", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-12", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-12", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-14", "00:00:01"), 0, 0));
        oldQuotes.add(new Ohlc(Moment.fromIso("2000-01-14", "00:00:01"), 0, 0));
        final Quotes newQuotes = new Quotes();
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-01", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-02", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-03", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-05", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-07", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-08", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-08", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-08", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-09", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-11", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-12", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-13", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-13", "00:00:02"), 1, 1));
        newQuotes.add(new Ohlc(Moment.fromIso("2000-01-14", "00:00:02"), 1, 1));
        final Quotes mergedQuotes = MergeUtil.mergeQuotes(oldQuotes, newQuotes);
        for (final Ohlc ohcl : mergedQuotes) {
            System.out.println(ohcl.moment.toIsoDatetime());
        }
        assertEquals(20, mergedQuotes.size());
    }

}
