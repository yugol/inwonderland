package aibroker.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class MomentTest {

    @Test
    public void testGetDaysBetween() {
        final Moment from = Moment.getBeginningOfToday();
        final Moment to = Moment.getEndOfToday();
        assertEquals(0, Moment.getDaysBetween(from, to));
    }

}
