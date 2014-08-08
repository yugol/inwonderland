package aibroker.engines.markets.historic;

import org.junit.Test;
import org.slf4j.Logger;
import aibroker.Context;
import aibroker.engines.Transaction;
import aibroker.engines.markets.Market;
import aibroker.engines.markets.MarketListener;
import aibroker.model.BidAsk;
import aibroker.model.QuotesDatabase;
import aibroker.util.Moment;
import aibroker.util.convenience.Databases;
import aibroker.util.convenience.Stocks;

public class HistoricMarketTest {

    private static final Logger logger = Context.getLogger(HistoricMarketTest.class);

    @Test
    public void testMarket() {
        final QuotesDatabase testDb = Databases.AMI_DEFAULT.getInstance();
        final Market market = new HistoricMarket(testDb, Stocks.DEDJIA_RON); // Moment.fromIso("2013-01-01"),
        market.addMarketListener(new MarketListener() {

            @Override
            public void onBidAskChanged(final BidAsk context) {
            }

            @Override
            public void onMarketClosed(final Moment moment) {
                logger.info(moment.toIsoDate() + " market closed");
            }

            @Override
            public void onMarketOpened(final Moment moment) {
                logger.info(moment.toIsoDate() + " market opened");
            }

            @Override
            public void onMarketPrepareClose(final Moment moment) {
            }

            @Override
            public void onMarketPrepareOpen(final Moment moment) {
            }

            @Override
            public void onNewTransaction(final Transaction transaction) {
                logger.trace(String.format("%s %.00f %d", transaction.getMoment().toIsoTime(), transaction.getPrice(), transaction.getVolume()));
            }

            @Override
            public void onSettlementChanged(final Moment previous, final Moment current) {
                logger.warn("settlement changed from " + previous.toIsoDate() + " to " + current.toIsoDate());
            }

        });
        market.start();
    }

}
