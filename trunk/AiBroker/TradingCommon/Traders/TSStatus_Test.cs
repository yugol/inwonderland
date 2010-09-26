/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/25/2009
 * Time: 4:54 PM
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
using TradingCommon.Util;

namespace TradingCommon.Traders
{
    [TestFixture]
    public class TSStatus_Test
    {
        [Test]
        public void OrdersAndTransactions()
        {
            TSStatus ts = new TSStatus();

            Order o = new Order("ABC", Operation.buy, 3, double.NaN, OrderTimeLimit.DESCHIS);
            o.SetPlaceData(1, DTUtil.ParseDateTime("2007-07-23 07:47:12"));
            ts.AddPlacedOrder(o);
            Assert.AreEqual(3, ts.Orders.Count);

            Transaction t = new Transaction();
            t.dateTime = DTUtil.ParseDateTime("2007-07-23 07:47:15");
            t.operation = Operation.buy;
            t.price = 3.1;
            t.symbol = "ABC";
            t.volume = 2;
            ts.AddTransaction(t);

            o = new Order("CBA", Operation.buy, 3, 3.2, OrderTimeLimit.DESCHIS);
            o.SetPlaceData(2, DTUtil.ParseDateTime("2007-07-23 07:47:20"));
            ts.AddPlacedOrder(o);

            t = new Transaction();
            t.dateTime = DTUtil.ParseDateTime("2007-07-23 07:47:25");
            t.operation = Operation.buy;
            t.price = 3.2;
            t.symbol = "CBA";
            t.volume = 2;
            ts.AddTransaction(t);

            t = new Transaction();
            t.dateTime = DTUtil.ParseDateTime("2007-07-23 07:47:30");
            t.operation = Operation.buy;
            t.price = 3.3;
            t.symbol = "ABC";
            t.volume = 1;
            ts.AddTransaction(t);

            Assert.AreEqual(6, ts.Orders.Count);

            o = ts.Orders[0];
            Assert.AreEqual(1, o.Id);
            Assert.AreEqual("ABC", o.Symbol);
            Assert.AreEqual(1,o.Volume);
            Assert.AreEqual(double.NaN, o.PlacePrice);
            Assert.AreEqual("2007-07-23 07:47:12" ,DTUtil.ToDateTimeString(o.PlaceTime));
            Assert.AreEqual(3.1,o.ExecutionPrice);
            Assert.AreEqual("2007-07-23 07:47:15", DTUtil.ToDateTimeString(o.ExecutionDateTime));

            o = ts.Orders[1];
            Assert.AreEqual(1, o.Id);
            Assert.AreEqual("ABC", o.Symbol);
            Assert.AreEqual(1, o.Volume);
            Assert.AreEqual(double.NaN, o.PlacePrice);
            Assert.AreEqual("2007-07-23 07:47:12", DTUtil.ToDateTimeString(o.PlaceTime));
            Assert.AreEqual(3.1, o.ExecutionPrice);
            Assert.AreEqual("2007-07-23 07:47:15", DTUtil.ToDateTimeString(o.ExecutionDateTime));

            o = ts.Orders[2];
            Assert.AreEqual(1, o.Id);
            Assert.AreEqual("ABC", o.Symbol);
            Assert.AreEqual(1, o.Volume);
            Assert.AreEqual(double.NaN, o.PlacePrice);
            Assert.AreEqual("2007-07-23 07:47:12", DTUtil.ToDateTimeString(o.PlaceTime));
            Assert.AreEqual(3.3, o.ExecutionPrice);
            Assert.AreEqual("2007-07-23 07:47:30", DTUtil.ToDateTimeString(o.ExecutionDateTime));

            o = ts.Orders[3];
            Assert.AreEqual(2, o.Id);
            Assert.AreEqual("CBA", o.Symbol);
            Assert.AreEqual(1, o.Volume);
            Assert.AreEqual(3.2, o.PlacePrice);
            Assert.AreEqual("2007-07-23 07:47:20", DTUtil.ToDateTimeString(o.PlaceTime));
            Assert.AreEqual(3.2, o.ExecutionPrice);
            Assert.AreEqual("2007-07-23 07:47:25", DTUtil.ToDateTimeString(o.ExecutionDateTime));

            o = ts.Orders[4];
            Assert.AreEqual(2, o.Id);
            Assert.AreEqual("CBA", o.Symbol);
            Assert.AreEqual(1, o.Volume);
            Assert.AreEqual(3.2, o.PlacePrice);
            Assert.AreEqual("2007-07-23 07:47:20", DTUtil.ToDateTimeString(o.PlaceTime));
            Assert.AreEqual(3.2, o.ExecutionPrice);
            Assert.AreEqual("2007-07-23 07:47:25", DTUtil.ToDateTimeString(o.ExecutionDateTime));

            o = ts.Orders[5];
            Assert.AreEqual(2, o.Id);
            Assert.AreEqual("CBA", o.Symbol);
            Assert.AreEqual(1, o.Volume);
            Assert.AreEqual(3.2, o.PlacePrice);
            Assert.AreEqual("2007-07-23 07:47:20", DTUtil.ToDateTimeString(o.PlaceTime));
            Assert.IsFalse(o.IsExecuted);

            Assert.AreEqual(5, ts.OpenPositions);
        }
    }
}
#endif
