package ess.gt.driver;

import org.junit.Test;
import ess.gt.GtContext;
import ess.gt.GtLogger;

public class GtWebReaderTest {

    @Test
    public void testFetchPlayerContext() {
        final GtLogger logger = new GtLogger();
        final GtWebReader reader = new GtWebDriver();

        final GtContext context = reader.login();
        System.out.println(context.getActiveUsersCount());
        logger.logActiveUsersCount(context.getActiveUsersCount());

        reader.fetchShares(context);
        System.out.println(context.getShares());
        logger.logShares(context.getShares());

        reader.fetchGoldRonExchangeRate(context);
        System.out.println(context.getGoldRonExchangeRate());
        logger.logGoldRonRate(context.getGoldRonExchangeRate());

        reader.fetchEuroGoldExchangeRate(context);
        System.out.println(context.getEuroGoldExchangeRate());
        logger.logEuroGoldRate(context.getEuroGoldExchangeRate());

        reader.fetchPlayerContext(context);
        System.out.println(context.getEuroAmount());
        System.out.println(context.getGoldAmount());
        System.out.println(context.getRonAmount());
    }

}
