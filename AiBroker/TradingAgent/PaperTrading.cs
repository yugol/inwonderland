using System;
using System.Collections.Generic;
using TradingCommon;
using TradingCommon.Traders;
using TradingCommon.Storage;

namespace TradingAgent
{
    class PaperTrading
    {
        internal event EventHandler TradesCompleted;

        TradingStatus orders;

        int prevLastTickIndex = 0;

        internal PaperTrading(TradingStatus orders)
        {
            this.orders = orders;
        }

        internal void UpdateTicks(IList<Tick> ticks)
        {
            IList<Transaction> trades = new List<Transaction>();

            DateTime now = DateTime.Now;
            if ((Configuration.MARKET_OPEN_HOUR <= now.Hour) && (now.Hour < Configuration.MARKET_CLOSE_HOUR))
            {
                int lastTickIndex = ticks.Count - 1;
                Tick lastTick = ticks[lastTickIndex];

                for (int i = 0; i < orders.Orders.Count; i++)
                {
                    Order order = orders.Orders[i];
                    if (!order.IsExecuted)
                    {
                        if (order.IsMarket)
                        {
                            ExecuteMarketOrder(trades, order, lastTick);
                        }
                        else
                        {
                            double low, high;
                            FindHighLowIntervalFromPreviousTicksUpdate(ticks, lastTickIndex, out low, out high);
                            ExecutePricedOrder(trades, order, low, high);
                        }
                    }
                }
                prevLastTickIndex = lastTickIndex;
            }


            if ((trades.Count > 0) && (TradesCompleted != null))
            {
                TradesCompleted(this, new TransactionsCompletedEventArgs(trades));
            }
        }

        private static void ExecutePricedOrder(IList<Transaction> trades, Order order, double low, double high)
        {
            bool execute = false;
            switch (order.Operation)
            {
                case Operation.BUY:
                    if (low <= order.PlacePrice)
                        execute = true;
                    break;
                case Operation.SELL:
                    if (high >= order.PlacePrice)
                        execute = true;
                    break;
            }
            if (execute)
            {
                Transaction trade = new Transaction();
                trade.dateTime = DateTime.Now;
                trade.symbol = order.Symbol;
                trade.operation = order.Operation;
                trade.volume = order.Volume;
                trade.price = order.PlacePrice;

                trades.Add(trade);
            }
        }

        private static void ExecuteMarketOrder(IList<Transaction> trades, Order order, Tick lastTick)
        {
            Transaction trade = new Transaction();
            trade.dateTime = DateTime.Now;
            trade.symbol = order.Symbol;
            trade.operation = order.Operation;
            trade.volume = order.Volume;
            trade.price = lastTick.price;
            trades.Add(trade);
        }

        private void FindHighLowIntervalFromPreviousTicksUpdate(IList<Tick> ticks, int lastTickIndex, out double low, out double high)
        {
            low = double.MaxValue;
            high = double.MinValue;
            for (int t = prevLastTickIndex + 1; t < lastTickIndex; ++t)
            {
                if (ticks[t].price > high) high = ticks[t].price;
                if (ticks[t].price < low) low = ticks[t].price;
            }
        }
    }
}
