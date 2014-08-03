package aibroker.model;

import static org.junit.Assert.assertEquals;
import java.util.Calendar;
import org.junit.Test;
import aibroker.util.Moment;

public class MomentTest {

    @Test
    public void testFromIso() {
        Moment tp = Moment.fromIso("0001-01-01");
        assertEquals("0001-01-01", tp.toIsoDate());
        assertEquals("00:00:00", tp.toIsoTime());
        assertEquals("0001-01-01 00:00:00", tp.toIsoDatetime());

        tp = Moment.fromIso("00:00:00");
        assertEquals("0001-01-01", tp.toIsoDate());
        assertEquals("00:00:00", tp.toIsoTime());
        assertEquals("0001-01-01 00:00:00", tp.toIsoDatetime());

        tp = Moment.fromIso("0001-01-01 00:00:00");
        assertEquals("0001-01-01", tp.toIsoDate());
        assertEquals("00:00:00", tp.toIsoTime());
        assertEquals("0001-01-01 00:00:00", tp.toIsoDatetime());

        tp = Moment.fromIso("2012-05-18");
        assertEquals("2012-05-18", tp.toIsoDate());
        assertEquals("00:00:00", tp.toIsoTime());
        assertEquals("2012-05-18 00:00:00", tp.toIsoDatetime());

        tp = Moment.fromIso("19:14:07");
        assertEquals("0001-01-01", tp.toIsoDate());
        assertEquals("19:14:07", tp.toIsoTime());
        assertEquals("0001-01-01 19:14:07", tp.toIsoDatetime());

        tp = Moment.fromIso("2012-05-18 19:14:07");
        assertEquals("2012-05-18", tp.toIsoDate());
        assertEquals("19:14:07", tp.toIsoTime());
        assertEquals("2012-05-18 19:14:07", tp.toIsoDatetime());
    }

    @Test
    public void testGetDaysBetween() {
        final Moment from = Moment.getBeginningOfToday();
        final Moment to = Moment.getEndOfToday();
        assertEquals(0, Moment.getDaysBetween(from, to));
    }

    @Test
    public void testTimePoint() {
        final Moment tp = new Moment();
        assertEquals(10, tp.toIsoDate().length());
        assertEquals(8, tp.toIsoTime().length());
        assertEquals(19, tp.toIsoDatetime().length());
    }

    @Test
    public void testTimePointIntIntInt() {
        Moment tp = new Moment(2012, Calendar.MAY, 18);
        assertEquals("2012-05-18", tp.toIsoDate());
        assertEquals("00:00:00", tp.toIsoTime());
        assertEquals("2012-05-18 00:00:00", tp.toIsoDatetime());

        tp = new Moment(1, Calendar.JANUARY, 1);
        assertEquals("0001-01-01", tp.toIsoDate());
        assertEquals("00:00:00", tp.toIsoTime());
        assertEquals("0001-01-01 00:00:00", tp.toIsoDatetime());
    }

    @Test
    public void testTimePointIntIntIntIntIntInt() {
        Moment tp = new Moment(2012, Calendar.MAY, 18, 19, 14, 7);
        assertEquals("2012-05-18", tp.toIsoDate());
        assertEquals("19:14:07", tp.toIsoTime());
        assertEquals("2012-05-18 19:14:07", tp.toIsoDatetime());

        tp = new Moment(1, Calendar.JANUARY, 1, 0, 0, 0);
        assertEquals("0001-01-01", tp.toIsoDate());
        assertEquals("00:00:00", tp.toIsoTime());
        assertEquals("0001-01-01 00:00:00", tp.toIsoDatetime());
    }

}
