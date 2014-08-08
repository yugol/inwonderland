package aibroker.engines.markets.historic;

import java.util.Arrays;
import java.util.Calendar;
import aibroker.engines.Order;
import aibroker.engines.Transaction;
import aibroker.engines.markets.Market;
import aibroker.engines.markets.MarketListener;
import aibroker.model.Ohlc;
import aibroker.model.QuotesDb;
import aibroker.model.Sequence;
import aibroker.model.SequenceSelector;
import aibroker.util.Moment;
import aibroker.util.convenience.Stocks;

public class HistoricMarket extends Market {

    private final QuotesDb qDb;
    private final Moment         first;
    private final Moment         last;
    private final Stocks         stocks;

    private final Sequence       sequence;
    private boolean              opened = false;

    public HistoricMarket(final QuotesDb qDb, final Moment first, final Moment last, final Stocks stocks) {
        this.qDb = qDb;
        this.first = first == null ? Moment.getAbsloluteBeginning() : first;
        this.last = last == null ? Moment.getAbsoluteEnd() : last;
        this.stocks = stocks;
        sequence = this.qDb.getSequence(SequenceSelector.fromName(this.stocks.symbol));
    }

    public HistoricMarket(final QuotesDb qDb, final Moment first, final Stocks stocks) {
        this(qDb, first, null, stocks);
    }

    public HistoricMarket(final QuotesDb qDb, final Stocks stocks) {
        this(qDb, null, null, stocks);
    }

    @Override
    public boolean isClosed() {
        return !opened;
    }

    @Override
    public boolean isOpened() {
        return opened;
    }

    @Override
    public void start() {
        Ohlc prevOhlc = null;
        for (final Ohlc ohlc : sequence.getQuotes()) {

            if (isClosed() && first.compareTo(ohlc.moment) <= 0) {
                opened = true;
                callMarketOpened(ohlc.moment);
            }

            if (isOpened()) {

                if (isDayChanged(prevOhlc, ohlc)) {
                    endDay(prevOhlc);
                    if (isMonthChanged(prevOhlc, ohlc)) {
                        int newMonth = ohlc.moment.get(Calendar.MONTH);
                        newMonth = newMonth == 0 ? 12 : newMonth;
                        int settlement = Arrays.binarySearch(stocks.settlements, newMonth);
                        if (settlement >= 0) {
                            int year = ohlc.moment.get(Calendar.YEAR);
                            final Moment previous = new Moment(year, stocks.settlements[settlement], 1);
                            previous.add(Calendar.DAY_OF_YEAR, -1);
                            settlement = settlement + 1;
                            if (settlement >= stocks.settlements.length) {
                                ++year;
                                settlement = 0;
                            }
                            final Moment current = new Moment(year, stocks.settlements[settlement], 1);
                            current.add(Calendar.DAY_OF_YEAR, -1);
                            callSettlementChanged(previous, current);
                        }
                    }
                    callMarketOpened(ohlc.moment);
                }

                updateOrders(ohlc);

                if (!listeners.isEmpty()) {
                    final Transaction transaction = new Transaction(sequence.getName());
                    transaction.setMoment(ohlc.moment);
                    transaction.setPrice(ohlc.close);
                    transaction.setVolume(ohlc.volume);
                    for (final MarketListener listener : listeners) {
                        listener.onNewTransaction(transaction);
                    }
                }

                if (last.compareTo(ohlc.moment) < 0) {
                    opened = false;
                    endDay(ohlc);
                }

                prevOhlc = ohlc;
            }

        }
        if (isOpened()) {
            opened = false;
            if (prevOhlc == null) {
                prevOhlc = new Ohlc(last, 0, 0);
            }
            endDay(prevOhlc);
        }
    }

    private void endDay(final Ohlc lastOhlc) {
        if (lastOhlc.close > 0) {
            callMarketPrepareClose(lastOhlc.moment);
            updateOrders(lastOhlc);
        }
        callMarketClosed(lastOhlc.moment);
    }

    private boolean isDayChanged(final Ohlc prevOhlc, final Ohlc ohlc) {
        if (prevOhlc == null) { return false; }
        return prevOhlc.moment.get(Calendar.DAY_OF_YEAR) != ohlc.moment.get(Calendar.DAY_OF_YEAR);
    }

    private boolean isMonthChanged(final Ohlc prevOhlc, final Ohlc ohlc) {
        if (prevOhlc == null) { return false; }
        return prevOhlc.moment.get(Calendar.MONTH) != ohlc.moment.get(Calendar.MONTH);
    }

    private void updateOrders(final Ohlc ohlc) {
        while (orders.size() > 0) {
            final OrderAndListener ol = orders.poll();
            final Order order = ol.order;
            final Transaction t = new Transaction(order.getSymbol());
            t.setMoment(ohlc.moment);
            t.setPrice(ohlc.close);
            t.setVolume(order.getVolume());
            order.addExecution(t);
            ol.listener.onOrderExecuted(order);
        }
    }

}
