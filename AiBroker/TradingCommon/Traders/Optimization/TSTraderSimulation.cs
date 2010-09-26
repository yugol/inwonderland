/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/25/2009
 * Time: 11:16 AM
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
using System;

using TradingCommon.Traders;

/*
 * Indicii de calculare a fitness-ului
 * - timpul de adaptare la o schimbare a pietei
 * - riscul la pierdere intr-o schimbare
 * - profit minim
 * - fereastra de optimizare (~3 luni)
 * - cum se comporta in situatii extreme (crash, boom, criza, ...)
 * 
 */

namespace TradingCommon.Traders.Optimization
{
    public class TSTraderSimulation : TSTrader
    {
        SimulationParameters simParam;
        int simulationBarIndex;
        
        public int FirstIndex { get { return simParam.FirstIndex; } }
        public int LastIndex { get { return simParam.LastIndex; } }

        public TSTraderSimulation(SimulationParameters simParam)
            : base(simParam.Title, simParam.Period, 1, simParam.TSys, null)
        {
            placeOrderHandler = PlaceOrderSimulatorHandler;
            this.simParam = simParam;
            this.series = simParam.Series;
            TSys.UpdateTimeSeries(simParam.Series);            
            Simulate();
        }
        
        void Simulate()
        {
            status = new TSStatus();
            simulationBarIndex = simParam.FirstIndex;
            while (simulationBarIndex <= simParam.LastIndex)
            {
                Decide(simulationBarIndex);
                ExecuteOrders();
                ++simulationBarIndex;
            }
            simulationBarIndex = simParam.LastIndex;
            CloseAllPositions();
            ExecuteOrders();
        }
        
        bool PlaceOrderSimulatorHandler(object sender, Order order)
        {
            order.SetPlaceData(-1, Series.DateTimes[simulationBarIndex]);
            status.AddPlacedOrder(order);
            return true;
        }
        
        void ExecuteOrders()
        {
            for (int i = 0; i < status.Orders.Count; i++)
            {
                Order o = status.Orders[i];

                if (!o.IsExecuted)
                {
                    Transaction t = null;

                    if (o.IsMarket)
                    {
                        t = new Transaction();
                        t.symbol = o.Symbol;
                        t.dateTime = Series.DateTimes[simulationBarIndex];
                        t.operation = o.Operation;
                        t.volume = o.Volume;
                        t.price = EstimateExecutionPrice(simulationBarIndex);
                    }
                    else
                    {
                        bool execute = false;

                        double high = Series.Highs[simulationBarIndex];
                        double low = Series.Lows[simulationBarIndex];

                        if ((o.Operation == Operation.BUY) && (low <= o.PlacePrice)) {
                            execute = true;
                        } else if ((o.Operation == Operation.SELL) && (high >= o.PlacePrice)) {
                            execute = true;
                        }

                        if (execute)
                        {
                            t = new Transaction();
                            t.dateTime = Series.DateTimes[simulationBarIndex];
                            t.operation = o.Operation;
                            t.volume = o.Volume;
                            t.price = o.PlacePrice;
                        }
                    }

                    if (t != null) {
                        status.AddTransaction(t);
                    }
                }
            }
        }

        void CloseAllPositions()
        {
            Order order = null;
            if (status.OpenPositions > 0) {
                order = new Order(Title.FullSymbol, Operation.sell, status.OpenPositions, double.NaN, OrderTimeLimit.DAY);
            } else if (status.OpenPositions < 0) {
                order = new Order(Title.FullSymbol, Operation.buy, -status.OpenPositions, double.NaN, OrderTimeLimit.DAY);
            }
            if (order != null) {
                PlaceOrder(order);
            }
        }
        
        double EstimateExecutionPrice(int barIndex)
        {
            return Series.Closes[barIndex];
        }
    }
}
