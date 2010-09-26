/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/26/2009
 * Time: 1:04 PM
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

namespace TradingCommon.Traders.CompositionRules
{
    [TestFixture]
    public class Or_Test
    {
        [Test]
        public void OrRule()
        {
            Or rule = new Or();
            Decision d;
            
            Decision d0 = new Decision() { IsBuy = false, IsSell = false, IsSar = true };
            Decision d1 = new Decision() { IsBuy = true, IsSell = false, IsSar = false };
            Decision d2 = new Decision() { IsBuy = false, IsSell = true, IsSar = false };
            Decision d3 = new Decision() { IsBuy = true, IsSell = false, IsSar = true };            
            Decision d4 = new Decision() { IsBuy = false, IsSell = true, IsSar = true };
            
            d = rule.Decide(d0, d1);
            Assert.AreEqual(true, d.IsBuy);
            Assert.AreEqual(false, d.IsSell);
            Assert.AreEqual(true, d.IsSar);

            d = rule.Decide(d1, d0);
            Assert.AreEqual(true, d.IsBuy);
            Assert.AreEqual(false, d.IsSell);
            Assert.AreEqual(true, d.IsSar);

            d = rule.Decide(d0, d2);
            Assert.AreEqual(false, d.IsBuy);
            Assert.AreEqual(true, d.IsSell);
            Assert.AreEqual(true, d.IsSar);

            d = rule.Decide(d2, d0);
            Assert.AreEqual(false, d.IsBuy);
            Assert.AreEqual(true, d.IsSell);
            Assert.AreEqual(true, d.IsSar);

            d = rule.Decide(d1, d2);
            Assert.AreEqual(true, d.IsNone);
            Assert.AreEqual(false, d.IsSar);

            d = rule.Decide(d2, d1);
            Assert.AreEqual(true, d.IsNone);
            Assert.AreEqual(false, d.IsSar);

            d = rule.Decide(d1, d3);
            Assert.AreEqual(true, d.IsBuy);
            Assert.AreEqual(false, d.IsSell);
            Assert.AreEqual(true, d.IsSar);

            d = rule.Decide(d3, d1);
            Assert.AreEqual(true, d.IsBuy);
            Assert.AreEqual(false, d.IsSell);
            Assert.AreEqual(true, d.IsSar);

            d = rule.Decide(d2, d4);
            Assert.AreEqual(false, d.IsBuy);
            Assert.AreEqual(true, d.IsSell);
            Assert.AreEqual(true, d.IsSar);

            d = rule.Decide(d4, d2);
            Assert.AreEqual(false, d.IsBuy);
            Assert.AreEqual(true, d.IsSell);
            Assert.AreEqual(true, d.IsSar);
        }
    }
}
#endif
