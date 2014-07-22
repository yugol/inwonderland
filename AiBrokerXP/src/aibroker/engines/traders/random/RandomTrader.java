package aibroker.engines.traders.random;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.engines.Operation;
import aibroker.engines.Transaction;
import aibroker.engines.markets.Market;
import aibroker.engines.traders.Trader;
import aibroker.util.Moment;
import aibroker.util.convenience.Stocks;

public class RandomTrader extends Trader {

    private static final Logger logger = LoggerFactory.getLogger(RandomTrader.class);
    private static final Random rand   = new Random();

    protected RandomTrader(final Market market, final Stocks stock) {
        super(market, stock);
    }

    @Override
    public void onMarketClosed(final Moment moment) {
        logger.info(moment.toIsoDate() + " Cumulated gross profit: " + getGrossProfit());
    }

    @Override
    public void onMarketClosing(final Moment moment) {
        final int openPositions = getOpenPositions();
        if (openPositions > 0) {
            placeOrder(Operation.SELL, openPositions);
        } else if (openPositions < 0) {
            placeOrder(Operation.BUY, -openPositions);
        }
    }

    @Override
    public void onMarketOpened(final Moment moment) {
        final Operation operation = rand.nextBoolean() ? Operation.BUY : Operation.SELL;
        final int volume = rand.nextInt(9) + 1;
        placeOrder(operation, volume);
    }

    @Override
    public void onNewTransaction(final Transaction transaction) {
    }

}
