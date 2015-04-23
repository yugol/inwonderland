package ess.common.actions;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import aibroker.util.Moment;

public class ActionResultTest {

    @Test
    public void testParseDateTime() {
        final Moment expected = Moment.fromIso("2015-04-23 23:52:35");
        final Moment actual = ActionResult.parseDateTime("You already served milk in last 24 hours. You may serve one after 2015-04-23 23:52:35");
        assertEquals(expected, actual);
    }
}
