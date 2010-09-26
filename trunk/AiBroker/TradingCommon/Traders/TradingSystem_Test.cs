/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 4:24 PM
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
using System.IO;
using NUnit.Framework;

namespace TradingCommon.Traders
{
    [TestFixture]
    public class TradingSystem_Test
    {
        [Test]
        public void ToXmlString_New()
        {
            TradingSystem ts = new TradingSystem();
            Assert.AreEqual("<ts>\r\n  <parameters />\r\n  <rule />\r\n</ts>", ts.ToXmlString());
        }

        [Test]
        public void ToXmlString()
        {
            TradingSystem ts = TSParser.ParseTS(@"..\..\..\TestData\TSToXmlTest.xml");
            Assert.AreEqual(File.ReadAllText(@"..\..\..\TestData\TSToXmlTest.xml"), ts.ToXmlString());
        }

        [Test]
		public void ResetParameters()
		{
		    BasicRuleNode rule = (BasicRuleNode) TSParser.ParseRule("(MomentumZeroline days)");
			TradingSystem tSys = new TradingSystem();
			tSys.RuleTree = rule;
			tSys.FetchParameters();
			Assert.IsTrue(rule.ParameterNames.Contains("days"));
			Assert.IsTrue(tSys.Parameters.ContainsKey("days"));
			tSys.ResetParameters();
			Assert.IsFalse(rule.ParameterNames.Contains("days"));
			Assert.IsFalse(tSys.Parameters.ContainsKey("days"));
			Assert.GreaterOrEqual(rule.Rule.ActualParameters[0].Name.IndexOf("Mom_optInTimePeriod"), 0);
		}
		
    }
}
#endif
