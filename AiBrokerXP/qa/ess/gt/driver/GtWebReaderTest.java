package ess.gt.driver;

import org.junit.Test;
import ess.gt.GtContext;

public class GtWebReaderTest {

    @Test
    public void testFetchPlayerContext() {
        final GtWebReader reader = new GtWebDriver();
        final GtContext context = reader.login();
        reader.fetchShares(context);
        System.out.println(context.getShares());
        reader.fetchPlayerContext(context);
        System.out.println(context.getEuroAmount());
        System.out.println(context.getGoldAmount());
        System.out.println(context.getRonAmount());
    }

}
