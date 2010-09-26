/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/12/2009
 * Time: 8:04 PM
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

namespace TradingCommon.Traders.BasicRules
{
    [TestFixture]
    public class BasicRule_Test
    {
        [Test]
        public void CalculateCrossPoint()
        {
            double[] prev = {0, 1};
            double[] curr = {0, 1};
            Assert.AreEqual(0.5, BasicRule.EstimateCrossPoint(prev, curr));
            
            curr[0] = 0;
            curr[1] = -1;
            Assert.AreEqual(0, BasicRule.EstimateCrossPoint(prev, curr));
            
            curr[0] = 0;
            curr[1] = 2;
            Assert.AreEqual(0.75, BasicRule.EstimateCrossPoint(prev, curr));
            
            curr[0] = -2;
            curr[1] = -1;
            Assert.AreEqual(-0.5, BasicRule.EstimateCrossPoint(prev, curr));
        }
    }
}
#endif
