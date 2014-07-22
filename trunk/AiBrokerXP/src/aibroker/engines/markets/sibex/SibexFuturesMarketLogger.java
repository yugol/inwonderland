package aibroker.engines.markets.sibex;

import org.slf4j.Logger;
import aibroker.Context;
import aibroker.engines.Transaction;
import aibroker.engines.markets.MarketListener;
import aibroker.model.BidAsk;
import aibroker.util.Moment;

public class SibexFuturesMarketLogger {

    public static void main(final String... args) {
        final SibexFuturesMarket market = new SibexFuturesMarket();
        market.addMarketListener(new MarketListener() {

            @Override
            public void onBidAskChanged(final BidAsk context) {
            }

            @Override
            public void onMarketClosed(final Moment moment) {
            }

            @Override
            public void onMarketClosing(final Moment moment) {
            }

            @Override
            public void onMarketOpened(final Moment moment) {
            }

            @Override
            public void onNewTransaction(final Transaction transaction) {
                logger.info(transaction.toCsv());
            }

            @Override
            public void onSettlementChanged(final Moment previous, final Moment current) {
            }

        });
        logger.info("Symbol,Date,Time,Price,Volume,OpenInt");
        market.start();
    }

    private static final Logger logger = Context.getLogger(SibexFuturesMarketLogger.class);

}
