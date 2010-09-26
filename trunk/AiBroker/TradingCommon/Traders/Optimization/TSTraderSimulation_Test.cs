/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/25/2009
 * Time: 6:45 PM
 * 
 *
 * Copyright (c) 2008, 2009 Iulian GORIAC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
#if TEST

using System;
using NUnit.Framework;
using TradingCommon.Traders;
using TradingCommon.Util;

namespace TradingCommon.Traders.Optimization
{
    [TestFixture]
    public class TSTraderSimulation_Test
    {
        [Test]
        public void BasicSystem()
        {
            string systemXml = "<ts><rule>(MomentumZeroline)</rule></ts>";
            TradingSystem system = TSParser.ParseTS(systemXml, false);
            SimulationParameters simParam = new SimulationParameters(new Title("BRD"), TimeSeries.DAY_PERIOD, system);
            simParam.LastIndex = 72;
            TSTraderSimulation trader = new TSTraderSimulation(simParam);
            
            Assert.AreEqual(10, trader.Status.Orders.Count);
            
            Order order = trader.Status.Orders[0];
            Assert.AreEqual("2005-02-04", DTUtil.ToDateString(order.PlaceTime)); // 24
            Assert.AreEqual(Operation.sell, order.Operation);
            Assert.AreEqual(4.98, order.ExecutionPrice);

            order = trader.Status.Orders[1];
            Assert.AreEqual("2005-02-10", DTUtil.ToDateString(order.PlaceTime)); // 28
            Assert.AreEqual(Operation.buy, order.Operation);
            Assert.AreEqual(4.85, order.ExecutionPrice);

            order = trader.Status.Orders[2];
            Assert.AreEqual("2005-03-07", DTUtil.ToDateString(order.PlaceTime)); // 42
            Assert.AreEqual(Operation.sell, order.Operation);
            Assert.AreEqual(5.25, order.ExecutionPrice);
            
            order = trader.Status.Orders[3];
            Assert.AreEqual("2005-03-08", DTUtil.ToDateString(order.PlaceTime)); // 43
            Assert.AreEqual(Operation.buy, order.Operation);
            Assert.AreEqual(5.45, order.ExecutionPrice);
            
            order = trader.Status.Orders[4];
            Assert.AreEqual("2005-03-14", DTUtil.ToDateString(order.PlaceTime)); // 47
            Assert.AreEqual(Operation.sell, order.Operation);
            Assert.AreEqual(5.75, order.ExecutionPrice);

            order = trader.Status.Orders[5];
            Assert.AreEqual("2005-03-16", DTUtil.ToDateString(order.PlaceTime)); // 49
            Assert.AreEqual(Operation.buy, order.Operation);
            Assert.AreEqual(5.6, order.ExecutionPrice);
            
            order = trader.Status.Orders[6];
            Assert.AreEqual("2005-03-17", DTUtil.ToDateString(order.PlaceTime)); // 50
            Assert.AreEqual(Operation.sell, order.Operation);
            Assert.AreEqual(5.05, order.ExecutionPrice);
            
            order = trader.Status.Orders[7];
            Assert.AreEqual("2005-04-01", DTUtil.ToDateString(order.PlaceTime)); // 61
            Assert.AreEqual(Operation.buy, order.Operation);
            Assert.AreEqual(4.96, order.ExecutionPrice);

            order = trader.Status.Orders[8];
            Assert.AreEqual("2005-04-15", DTUtil.ToDateString(order.PlaceTime)); // 71
            Assert.AreEqual(Operation.sell, order.Operation);
            Assert.AreEqual(4.66, order.ExecutionPrice);

            order = trader.Status.Orders[9];
            Assert.AreEqual("2005-04-18", DTUtil.ToDateString(order.PlaceTime)); // 72
            Assert.AreEqual(Operation.buy, order.Operation);
            Assert.AreEqual(4.52, order.ExecutionPrice);
            
            Assert.AreEqual(5, trader.Status.Trades.Count);
            
            trader.Status.FillAllTradesData(trader.Series);
            
            Trade trade = trader.Status.Trades[0];
            Assert.AreEqual(24, trade.EnterBarIndex);
            Assert.AreEqual(28, trade.ExitBarIndex);
            
            trade = trader.Status.Trades[1];
            Assert.AreEqual(42, trade.EnterBarIndex);
            Assert.AreEqual(43, trade.ExitBarIndex);
            
            trade = trader.Status.Trades[2];
            Assert.AreEqual(47, trade.EnterBarIndex);
            Assert.AreEqual(49, trade.ExitBarIndex);
            
            trade = trader.Status.Trades[3];
            Assert.AreEqual(50, trade.EnterBarIndex);
            Assert.AreEqual(61, trade.ExitBarIndex);
            
            trade = trader.Status.Trades[4];
            Assert.AreEqual(71, trade.EnterBarIndex);
            Assert.AreEqual(72, trade.ExitBarIndex);
            
            Assert.AreEqual("0.31", trader.Status.GrossProfit.ToString("0.00"));
        }
    
        [Test]
        public void CompositeSystem()
        {
            string systemXml = "<ts><parameters><parameter val='10'>p1</parameter><parameter val='2'>p2</parameter></parameters><rule>(And (MomentumZeroline p1) (MomentumZeroline p2))</rule></ts>";
            TradingSystem system = TSParser.ParseTS(systemXml, false);
            SimulationParameters simParam = new SimulationParameters(new Title("BRD"), TimeSeries.DAY_PERIOD, system);
            simParam.LastIndex = 72;
            TSTraderSimulation trader = new TSTraderSimulation(simParam);
            
            Assert.AreEqual(2, trader.Status.Orders.Count);
            
            Order order = trader.Status.Orders[0];
            Assert.AreEqual("2005-03-07", DTUtil.ToDateString(order.PlaceTime)); // 42
            Assert.AreEqual(Operation.sell, order.Operation);
            Assert.AreEqual(5.25, order.ExecutionPrice);

            order = trader.Status.Orders[1];
            Assert.AreEqual("2005-04-18", DTUtil.ToDateString(order.PlaceTime)); // 72
            Assert.AreEqual(Operation.buy, order.Operation);
            Assert.AreEqual(4.52, order.ExecutionPrice);
            
            Assert.AreEqual(1, trader.Status.Trades.Count);
            
            trader.Status.FillAllTradesData(trader.Series);
            
            Trade trade = trader.Status.Trades[0];
            Assert.AreEqual(42, trade.EnterBarIndex);
            Assert.AreEqual(72, trade.ExitBarIndex);
            
            Assert.AreEqual("0.73", trader.Status.GrossProfit.ToString("0.00"));
        }
    }
}
#endif
