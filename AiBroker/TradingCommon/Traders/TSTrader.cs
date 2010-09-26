/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 9:58 PM
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
using TradingCommon.Storage;

namespace TradingCommon.Traders
{
    public delegate bool PlaceOrderHandler(object sender, Order order);

    public class TSTrader
    {        
        Title title;
        public Title Title { get { return title; } }

        int period;
        public int Period { get { return period; } }

        int volume;
        public int Volume { get { return volume; } }
        
        TradingSystem system;
        public TradingSystem TSys { get { return system; } }

        protected TimeSeries series;
        public TimeSeries Series { get { return series; } }
        
        protected TSStatus status = new TSStatus();
        public TSStatus Status { get { return status; } }
        
        protected PlaceOrderHandler placeOrderHandler;
        
        protected TSTrader(Title title, int period, int volume, TradingSystem ts, PlaceOrderHandler placeOrderHandler)
        {
            this.title = title;
            this.period = period;
            this.volume = volume;
            this.system = ts;
            this.placeOrderHandler = placeOrderHandler;
        }
        
        public void UpdateTimeSeries()
        {
            series = DataUtil.ReadTimeSeries(title.FullSymbol, period);
            system.UpdateTimeSeries(series);
        }
        
        public void Decide(int barIndex)
        {
            if (barIndex > status.LastOperationBarIndex) 
            {
                Decision decision = system.Decide(barIndex, status);
                Order order = null;
                
                if (decision.IsBuy) {
                    if (status.OpenPositions == 0) {
                        order = new Order(title.FullSymbol, Operation.buy, volume, double.NaN, OrderTimeLimit.DAY);
                    } else if (status.OpenPositions < 0) {
                        if (decision.IsSar) {
                            order = new Order(title.FullSymbol, Operation.buy, volume - status.OpenPositions, double.NaN, OrderTimeLimit.DAY);
                        } else {
                            order = new Order(title.FullSymbol, Operation.buy, -status.OpenPositions, double.NaN, OrderTimeLimit.DAY);
                        }
                    }
                } else if (decision.IsSell) {
                    if (status.OpenPositions == 0) {
                        order = new Order(title.FullSymbol, Operation.sell, volume, double.NaN, OrderTimeLimit.DAY);
                    } else if (status.OpenPositions > 0) {
                        if (decision.IsSar) {
                            order = new Order(title.FullSymbol, Operation.sell, volume + status.OpenPositions, double.NaN, OrderTimeLimit.DAY);
                        } else {
                            order = new Order(title.FullSymbol, Operation.sell, status.OpenPositions, double.NaN, OrderTimeLimit.DAY);
                        }
                    }
                }
                
                if (order != null) {
                    if (PlaceOrder(order)) {
                        status.EnterOperation  = order.Operation;
                        status.EnterPrice = series.Closes[barIndex];
                        status.EnterTime = series.DateTimes[barIndex];
                        status.LastOperationBarIndex = barIndex;
                    }
                }
            }
        }
        
        protected bool PlaceOrder(Order order)
        {
            return placeOrderHandler(this, order);
        }
        
    }
}
