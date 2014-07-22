package aibroker.model.primitives;

import static org.junit.Assert.assertEquals;
import java.io.File;
import org.junit.Test;
import aibroker.TestConfig;
import aibroker.model.Quotes;
import aibroker.model.drivers.csv.CsvReader;
import aibroker.util.Moment;

public class QuotesTest {

    @Test
    public void testSlice() {
        final File csv = new File(TestConfig.CSV_TEST_DATABASE_FOLDER + "/BRK.EOD.csv");
        final Quotes quotes = CsvReader.readQuotes(csv);
        assertEquals(62, quotes.size());
        assertEquals(62, quotes.slice(Moment.fromCompactIso("20110201"), Moment.fromCompactIso("20120501")).size());
        assertEquals(42, quotes.slice(Moment.fromCompactIso("20120201"), Moment.fromCompactIso("20120501")).size());
        assertEquals(21, quotes.slice(Moment.fromCompactIso("20120131121314"), Moment.fromCompactIso("20120229121314")).size());
        assertEquals(20, quotes.slice(Moment.fromCompactIso("20120201"), Moment.fromCompactIso("20120229")).size());
        assertEquals(42, quotes.slice(Moment.fromCompactIso("20120131121314")).size());
        assertEquals(42, quotes.slice(Moment.fromCompactIso("20120201")).size());
    }

}
