package aibroker.agents.sibex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.Context;
import aibroker.engines.Transaction;
import aibroker.engines.markets.MarketListener;
import aibroker.engines.markets.sibex.SibexFuturesMarket;
import aibroker.model.BidAsk;
import aibroker.util.Moment;

public class SibexFuturesMarketLogger {

    public static void main(final String... args) {
        final SibexFuturesMarket market = new SibexFuturesMarket();
        market.addMarketListener(new MarketListener() {

            @Override
            public void onBidAskChanged(final BidAsk context) {
                consoleLogger.info(context.toString());
            }

            @Override
            public void onMarketClosed(final Moment moment) {
                consoleLogger.info("Performing post close operations");
            }

            @Override
            public void onMarketOpened(final Moment moment) {
            }

            @Override
            public void onMarketPrepareClose(final Moment moment) {
            }

            @Override
            public void onMarketPrepareOpen(final Moment moment) {
                csvLogger.info("Symbol,Date,Time,Price,Volume,OpenInt");
            }

            @Override
            public void onNewTransaction(final Transaction transaction) {
                csvLogger.info(transaction.toCsv());
                consoleLogger.info(transaction.toCsv());
            }

            @Override
            public void onSettlementChanged(final Moment previous, final Moment current) {
            }

        });

        market.start();
    }

    private static final Logger csvLogger     = Context.getLogger(SibexFuturesMarketLogger.class);
    private static final Logger consoleLogger = LoggerFactory.getLogger(SibexFuturesMarket.class);

}
