using System;
using System.Collections.Generic;
using System.Text;
using TradingCommon;
using TradingCommon.Traders;

namespace TradingCommon.Traders
{
    public class Trade
    {
        Trade() {}

        int enterBarIndex = int.MinValue;
        public int EnterBarIndex { get { return enterBarIndex; } }

        Transaction enter;
        public Transaction Enter { get { return enter; } }

        int exitBarIndex = int.MinValue;
        public int ExitBarIndex { get { return exitBarIndex; } }

        Transaction exit = null;
        public Transaction Exit { get { return exit; } }
        
        double profit = double.NaN;
        public double Profit { get { return profit; } }

        double runUp = double.NaN;
        public double RunUp { get { return runUp; } }

        double drawDown = double.NaN;
        public double DrawDown { get { return drawDown; } }
        
        public bool IsOpen { get { return (exit == null); } }
        public bool IsLong { get { return (enter.operation == Operation.BUY); } }
        public bool IsShort { get { return (enter.operation == Operation.SELL); } }

        public int BarCount { get { return (exitBarIndex - enterBarIndex); } }

        public double Efficiency
        {
            get
            {
                if (profit > 0) 
                    return 100 * profit / RunUp;
                return double.NaN;
            }
        }

        public static Trade OpenTrade(Transaction transaction)
        {
            Trade trade = new Trade();
            trade.enter = transaction;
            return trade;
        }

        public static void CloseTrade(Trade trade, Transaction transaction)
        {
            trade.exit = transaction;
            trade.profit = trade.exit.price - trade.enter.price;
            if (trade.IsShort) {
                trade.profit = -trade.profit;
            }
        }
        
        public static void FillAllTradeData(Trade trade, TimeSeries series)
        {
            trade.enterBarIndex = series.GetBarIndex(trade.enter.dateTime);
            trade.exitBarIndex = series.GetBarIndex(trade.exit.dateTime);

            double min, max;
            series.GetMinMax(trade.enterBarIndex, trade.exitBarIndex, out min, out max);
            if (trade.IsLong) {
                trade.runUp = max - trade.enter.price;
                trade.drawDown = trade.enter.price - min;
            } else {
                trade.runUp = trade.enter.price - min;
                trade.drawDown = max - trade.enter.price;
            }
        }
        
    }
}
