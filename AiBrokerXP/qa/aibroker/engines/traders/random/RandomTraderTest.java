package aibroker.engines.traders.random;

import java.util.Calendar;
import org.junit.Test;
import aibroker.engines.markets.Market;
import aibroker.engines.markets.historic.HistoricMarket;
import aibroker.engines.traders.random.RandomTrader;
import aibroker.model.QuotesDatabase;
import aibroker.util.Moment;
import aibroker.util.convenience.Databases;
import aibroker.util.convenience.Stocks;

public class RandomTraderTest {

    @Test
    public void testRandomTrader() {
        final QuotesDatabase testDb = Databases.AMI_DEFAULT.getInstance();
        final Market market = new HistoricMarket(testDb, new Moment(2013, Calendar.JANUARY, 1), Stocks.DEDJIA_RON);
        final RandomTrader trader = new RandomTrader(market, Stocks.DEDJIA_RON);
        market.start();
        System.out.println(trader.getActivityReport().toString());
    }

}
