/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/10/2009
 * Time: 1:16 AM
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

namespace TradingCommon.Traders.Optimization.Tune
{
    [TestFixture]
    public class TSTunerBruteForce_Test
    {
        [Test]
        public void Optimization()
        {
            string systemXml = "<ts><parameters><parameter val='10'>p1</parameter><parameter val='2'>p2</parameter></parameters><rule>(And (MomentumZeroline p1) (MomentumZeroline p2))</rule></ts>";
            TradingSystem system = TSParser.ParseTS(systemXml, false);
            system.Parameters["p1"].BitLength = 1;
            system.Parameters["p2"].BitLength = 1;
            TuneParameters optParam = new TuneParameters(new Title("BRD"), TimeSeries.DAY_PERIOD, system);
            TSTuner optimizer = new TSTunerBruteForce(optParam);
            Assert.AreEqual(4, optimizer.EstimateWork());
            optimizer.RunOptimization();
            Assert.AreEqual(4, optimizer.SolutionBuffer.Items.Count);
        }
    }
}
#endif
