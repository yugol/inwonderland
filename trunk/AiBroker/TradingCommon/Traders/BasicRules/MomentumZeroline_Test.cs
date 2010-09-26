/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/25/2009
 * Time: 11:02 AM
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
using System.Collections.Generic;
using NUnit.Framework;

using TradingCommon.Traders;

namespace TradingCommon.Traders.BasicRules
{
    [TestFixture]
    public class MomentumZeroline_Test
    {        
        [Test]
        public void TestRule()
        {
            string systemXml = "<ts><rule>(MomentumZeroline)</rule></ts>";
            TradingSystem system = TSParser.ParseTS(systemXml, false);
            TSTrader trader = new TSTraderTester(new Title("BRD"), TimeSeries.DAY_PERIOD, system);
            trader.UpdateTimeSeries();
            List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
            MomentumZeroline rule = (MomentumZeroline) trader.TSys.RuleTree.GetBasicRuleNodes(brNodes)[0].Rule;
            Decision d;
            
            rule.mom = new double[]{-1, 1, 0, -1, 0, -1, 0, 0 , 1};
            d = rule.Decide(0, null);
            Assert.IsTrue(d.IsNone);            
            d = rule.Decide(1, null);
            Assert.IsTrue(d.IsBuy);
            d = rule.Decide(2, null);
            Assert.IsTrue(d.IsNone);
            d = rule.Decide(3, null);
            Assert.IsTrue(d.IsSell);
            d = rule.Decide(4, null);
            Assert.IsTrue(d.IsNone);
            d = rule.Decide(5, null);
            Assert.IsTrue(d.IsNone);
            d = rule.Decide(6, null);
            Assert.IsTrue(d.IsNone);
            d = rule.Decide(7, null);
            Assert.IsTrue(d.IsNone);
            d = rule.Decide(8, null);
            Assert.IsTrue(d.IsBuy);
            
        }
    }
}
#endif
