/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/16/2009
 * Time: 3:04 PM
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

namespace TradingCommon.Traders
{
	[TestFixture]
	public class TSParser_Test
	{
		[Test]
		public void ParseTS_Empty()
		{
		    TradingSystem ts = TSParser.ParseTS(new TradingSystem().ToXmlString(), false);
		}

		[Test]
		public void BasicRule()
		{
			RuleTree rule = TSParser.ParseRule("(MomentumZeroline days)");
			Assert.AreEqual("\n(MomentumZeroline\n\tdays)", rule.ToString());
		}

		[Test]
		public void CompositionRule_Binary()
		{
			RuleTree rule = TSParser.ParseRule("(And (MomentumZeroline days1) (MomentumZeroline days2))");
			Assert.AreEqual("(And \n(MomentumZeroline\n\tdays1) \n(MomentumZeroline\n\tdays2))", rule.ToString());
		}
		
		[Test]
		public void ParseTS()
		{
			string filename = @"..\..\..\TestData\TSsample.xml";
			TradingSystem ts = TSParser.ParseTS(filename);
			
			string name4 = null;
			foreach (string key in ts.Parameters.Keys) {
			    if (key.StartsWith("M")) {
			        name4 = key;
			        break;
			    }
			}
			
			Assert.AreEqual(4, ts.Parameters.Count);
			
			TSParameter p = ts.Parameters["param1"];
			Assert.IsInstanceOf<TSParameterInt>(p);
			Assert.AreEqual("param1", p.Name);
			Assert.AreEqual(10, p.Value);
			Assert.AreEqual(2, p.MinValue);
			Assert.AreEqual(100, p.MaxValue);

			p = ts.Parameters["param2"];
			Assert.IsInstanceOf<TSParameterInt>(p);
			Assert.AreEqual("param2", p.Name);
			Assert.AreEqual(10, p.Value);
			Assert.AreEqual(2, p.MinValue);
			Assert.AreEqual(200, p.MaxValue);
			
			p = ts.Parameters["param3"];
			Assert.IsInstanceOf<TSParameterReal>(p);
			Assert.AreEqual("param3", p.Name);
			Assert.AreEqual(5, p.Value);
			Assert.AreEqual(0, p.MinValue);
			Assert.AreEqual(10, p.MaxValue);
					
			p = ts.Parameters[name4];
			Assert.IsInstanceOf<TSParameterReal>(p);
			Assert.AreEqual(name4, p.Name);
			Assert.AreEqual(-1, p.Value);
			Assert.AreEqual(-10, p.MinValue);
			Assert.AreEqual(0, p.MaxValue);
			
			Assert.AreEqual("(And \n(MomentumZeroline\n\tparam1) \n(MomentumLowerUpperBound\n\tparam2\n\tparam3\n\t" + name4 + "))", ts.RuleTree.ToString());
		}
		
		[Test]
		public void ParseTS_ReParse()
		{
			string filename = @"..\..\..\TestData\TSsample.xml";
			TradingSystem ts = TSParser.ParseTS(filename);
			string rep1 = ts.ToXmlString();
			ts = TSParser.ParseTS(rep1, false);
			string rep2 = ts.ToXmlString();
			Assert.AreEqual(rep1, rep2);
		}
	
	}
}
#endif
