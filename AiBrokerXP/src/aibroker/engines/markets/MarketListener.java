package aibroker.engines.markets;

import aibroker.engines.Transaction;
import aibroker.model.BidAsk;
import aibroker.util.Moment;

public interface MarketListener {

    void onBidAskChanged(BidAsk context);

    void onMarketClosed(Moment moment);

    void onMarketClosing(Moment moment);

    void onMarketOpened(Moment moment);

    void onSettlementChanged(Moment previous, Moment current);

    void onNewTransaction(Transaction transaction);

}
