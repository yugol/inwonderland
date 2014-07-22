package aibroker.engines.traders;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aibroker.engines.Operation;
import aibroker.engines.Order;
import aibroker.engines.Position;
import aibroker.engines.Transaction;
import aibroker.engines.markets.Market;
import aibroker.engines.markets.MarketListener;
import aibroker.engines.markets.OrderExecutionListener;
import aibroker.model.BidAsk;
import aibroker.util.Moment;
import aibroker.util.convenience.Stocks;
import aibroker.util.reports.ActivityReport;

public abstract class Trader implements MarketListener, OrderExecutionListener {

    private static final Logger logger = LoggerFactory.getLogger(Trader.class);

    protected final Market      market;
    protected final Stocks      stock;
    protected final List<Order> orders = new ArrayList<Order>();

    protected Trader(final Market market, final Stocks stock) {
        this.market = market;
        this.stock = stock;
        market.addMarketListener(this);
    }

    public ActivityReport getActivityReport() {
        final ActivityReport report = new ActivityReport();
        // TODO start here for trader simulation
        return report;
    }

    public double getGrossProfit() {
        double profit = 0;
        for (final Order order : orders) {
            if (order.getAction() == Operation.BUY) {
                for (final Position p : order.getExecution()) {
                    profit -= p.getVolume() * p.getPrice();
                }
            } else if (order.getAction() == Operation.SELL) {
                for (final Position p : order.getExecution()) {
                    profit += p.getVolume() * p.getPrice();
                }
            }
        }
        return profit;
    }

    public int getOpenPositions() {
        int openPositions = 0;
        for (final Order order : orders) {
            if (order.getAction() == Operation.BUY) {
                openPositions += order.getExecutionVolume();
            } else if (order.getAction() == Operation.SELL) {
                openPositions -= order.getExecutionVolume();
            }
        }
        return openPositions;
    }

    @Override
    public void onBidAskChanged(final BidAsk context) {
        throw new UnsupportedOperationException("onBidAskChanged");
    }

    @Override
    public void onMarketClosed(final Moment moment) {
        throw new UnsupportedOperationException("onMarketClosed");
    }

    @Override
    public void onMarketClosing(final Moment moment) {
        throw new UnsupportedOperationException("onMarketClosing");
    }

    @Override
    public void onMarketOpened(final Moment moment) {
        throw new UnsupportedOperationException("onMarketOpened");
    }

    @Override
    public void onSettlementChanged(final Moment previous, final Moment current) {
        if (getOpenPositions() != 0) { throw new RuntimeException("all positions should have been closed"); }
    }

    @Override
    public void onNewTransaction(final Transaction transaction) {
        throw new UnsupportedOperationException("onNewTransaction");
    }

    @Override
    public void onOrderExecuted(final Order order) {
        final StringBuilder report = new StringBuilder("Executed ");
        report.append(order.toString());
        for (final Position p : order.getExecution()) {
            report.append("\n    Holding ");
            report.append(p.toString());
        }
        logger.trace(report.toString());
    }

    protected void placeOrder(final Operation operation, final int volume) {
        final Order order = new Order(operation, stock.symbol, volume);
        orders.add(order);
        market.placeOrder(order, this);
        logger.trace("  Placed " + order.toString());
    }

}
