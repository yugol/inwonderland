package aibroker.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import aibroker.model.Ohlc;
import aibroker.model.Quotes;
import aibroker.model.domains.Sampling;
import aibroker.util.convenience.Databases;
import aibroker.util.convenience.Selectors;

public class SamplingUtilTest {

    @Test
    public void testResample_1_Min() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.MIN_1);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-25 16:38:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("485.5", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("485.5", String.valueOf(ohlc.close));
        assertEquals("1", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResample_10_Min() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.MIN_10);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-25 16:30:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("485.5", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("485.5", String.valueOf(ohlc.close));
        assertEquals("1", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResample_15_Min() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.MIN_15);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-25 16:30:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("486.0", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("486.0", String.valueOf(ohlc.close));
        assertEquals("2", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResample_30_Min() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.MIN_30);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-25 16:30:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("486.0", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("486.0", String.valueOf(ohlc.close));
        assertEquals("2", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResample_5_Min() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.MIN_5);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-25 16:35:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("485.5", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("485.5", String.valueOf(ohlc.close));
        assertEquals("1", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResampleDaily() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.DAILY);
        // System.out.println(derivedQuotes);

        Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-25 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("486.0", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("486.0", String.valueOf(ohlc.close));
        assertEquals("2", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));

        ohlc = derivedQuotes.get(1);
        assertEquals("2013-10-01 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("481.35", String.valueOf(ohlc.open));
        assertEquals("485.65", String.valueOf(ohlc.high));
        assertEquals("481.35", String.valueOf(ohlc.low));
        assertEquals("485.65", String.valueOf(ohlc.close));
        assertEquals("11", String.valueOf(ohlc.volume));
        assertEquals("20", String.valueOf(ohlc.openInt));

        ohlc = derivedQuotes.get(2);
        assertEquals("2013-10-07 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("487.35", String.valueOf(ohlc.open));
        assertEquals("487.35", String.valueOf(ohlc.high));
        assertEquals("487.35", String.valueOf(ohlc.low));
        assertEquals("487.35", String.valueOf(ohlc.close));
        assertEquals("5", String.valueOf(ohlc.volume));
        assertEquals("20", String.valueOf(ohlc.openInt));

        ohlc = derivedQuotes.get(3);
        assertEquals("2013-10-10 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("489.7", String.valueOf(ohlc.open));
        assertEquals("489.7", String.valueOf(ohlc.high));
        assertEquals("489.7", String.valueOf(ohlc.low));
        assertEquals("489.7", String.valueOf(ohlc.close));
        assertEquals("5", String.valueOf(ohlc.volume));
        assertEquals("10", String.valueOf(ohlc.openInt));

        ohlc = derivedQuotes.get(4);
        assertEquals("2013-11-06 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("523.62", String.valueOf(ohlc.open));
        assertEquals("526.67", String.valueOf(ohlc.high));
        assertEquals("523.62", String.valueOf(ohlc.low));
        assertEquals("523.62", String.valueOf(ohlc.close));
        assertEquals("4", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));

        assertEquals(baseQuotes.get(baseQuotes.size() - 1).moment.toIsoDate(), derivedQuotes.get(derivedQuotes.size() - 1).moment.toIsoDate());
    }

    @Test
    public void testResampleEmpty() {
        final Quotes baseQuotes = new Quotes();

        Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.DAILY);
        assertFalse(baseQuotes == derivedQuotes);
        assertEquals(0, derivedQuotes.size());

        derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.SECOND);
        assertTrue(baseQuotes == derivedQuotes);

        try {
            derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.DAILY, Sampling.SECOND);
            fail("Refinement is not possible");
        } catch (final BrokerException bex) {
        }
    }

    @Test
    public void testResampleHourly() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.HOURLY);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-25 16:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("486.0", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("486.0", String.valueOf(ohlc.close));
        assertEquals("2", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResampleMonthly() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.MONTHLY);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-01 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("486.0", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("486.0", String.valueOf(ohlc.close));
        assertEquals("2", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResampleQuaterly() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.QUARTERLY);
        //  System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-07-01 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("486.0", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("486.0", String.valueOf(ohlc.close));
        assertEquals("2", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResampleWeekly() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.WEEKLY);
        //  System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-09-23 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("486.0", String.valueOf(ohlc.high));
        assertEquals("485.5", String.valueOf(ohlc.low));
        assertEquals("486.0", String.valueOf(ohlc.close));
        assertEquals("2", String.valueOf(ohlc.volume));
        assertEquals("2", String.valueOf(ohlc.openInt));
    }

    @Test
    public void testResampleYearly() {
        final Quotes baseQuotes = Databases.DEFAULT().getSequence(Selectors.DEAPL).getQuotes();
        final Quotes derivedQuotes = SamplingUtil.resample(baseQuotes, Sampling.SECOND, Sampling.YEARLY);
        // System.out.println(derivedQuotes);

        final Ohlc ohlc = derivedQuotes.get(0);
        assertEquals("2013-01-01 00:00:00", ohlc.moment.toIsoDatetime());
        assertEquals("485.5", String.valueOf(ohlc.open));
        assertEquals("568.65", String.valueOf(ohlc.high));
        assertEquals("481.35", String.valueOf(ohlc.low));
        assertEquals("567.05", String.valueOf(ohlc.close));
        assertEquals("126", String.valueOf(ohlc.volume));
        assertEquals("30", String.valueOf(ohlc.openInt));
    }

}
