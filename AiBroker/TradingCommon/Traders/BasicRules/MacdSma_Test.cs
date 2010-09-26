/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/12/2009
 * Time: 6:53 PM
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
using TradingCommon.Traders.Optimization;
using TradingCommon.Util;

namespace TradingCommon.Traders.BasicRules
{
    [TestFixture]
    public class MacdSma_Test
    {
        [Test]
        public void TestRule()
        {
            string systemXml = @"
<ts>
    <parameters>
        <parameter val=""12"">fast</parameter>
        <parameter val=""26"">slow</parameter>
        <parameter val=""9"">signal</parameter>
    </parameters>
    <rule>(MacdSma fast slow signal)</rule>
</ts>";
            TradingSystem system = TSParser.ParseTS(systemXml, false);
            SimulationParameters simParam = new SimulationParameters(new Title("BRD"), TimeSeries.DAY_PERIOD, system);
            simParam.LastIndex = 72;
            TSTraderSimulation trader = new TSTraderSimulation(simParam);

            Assert.AreEqual(2, trader.Status.Orders.Count);
            
            Order order = trader.Status.Orders[0];
            Assert.AreEqual("2005-03-07", DTUtil.ToDateString(order.PlaceTime));
            Assert.AreEqual(Operation.sell, order.Operation);

            order = trader.Status.Orders[1];
            Assert.AreEqual("2005-04-05", DTUtil.ToDateString(order.PlaceTime));
            Assert.AreEqual(Operation.buy, order.Operation);
        }
    }
}
#endif
