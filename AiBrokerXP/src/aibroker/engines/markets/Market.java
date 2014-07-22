package aibroker.engines.markets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import aibroker.engines.Order;
import aibroker.engines.Transaction;
import aibroker.model.BidAsk;
import aibroker.util.Moment;

public abstract class Market {

    protected class OrderAndListener {

        public final Order                  order;
        public final OrderExecutionListener listener;

        private OrderAndListener(final Order order, final OrderExecutionListener listener) {
            this.order = order;
            this.listener = listener;
        }

    }

    protected final Queue<OrderAndListener> orders    = new LinkedList<OrderAndListener>();
    protected final List<MarketListener>    listeners = new ArrayList<MarketListener>();

    public void addMarketListener(final MarketListener listener) {
        listeners.add(listener);
    }

    public abstract boolean isClosed();

    public abstract boolean isOpened();

    public void placeOrder(final Order order, final OrderExecutionListener listener) {
        orders.add(new OrderAndListener(order, listener));
    }

    public abstract void start();

    protected void callBidAskChanged(final BidAsk bidAsk) {
        for (final MarketListener listener : listeners) {
            listener.onBidAskChanged(bidAsk);
        }
    }

    protected void callMarketClosed(final Moment moment) {
        for (final MarketListener listener : listeners) {
            listener.onMarketClosed(moment);
        }
    }

    protected void callMarketClosing(final Moment moment) {
        for (final MarketListener listener : listeners) {
            listener.onMarketClosing(moment);
        }
    }

    protected void callMarketOpened(final Moment moment) {
        for (final MarketListener listener : listeners) {
            listener.onMarketOpened(moment);
        }
    }

    protected void callNewTransaction(final Transaction transaction) {
        for (final MarketListener listener : listeners) {
            listener.onNewTransaction(transaction);
        }
    }

    protected void callSettlementChanged(final Moment previous, final Moment current) {
        for (final MarketListener listener : listeners) {
            listener.onSettlementChanged(previous, current);
        }
    }

}
