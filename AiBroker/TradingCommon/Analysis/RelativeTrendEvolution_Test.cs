/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 8/30/2009
 * Time: 10:16 PM
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
using weka.core;
using NUnit.Framework;

namespace TradingCommon.Analysis
{
    [TestFixture]
    public class RelativeTrendEvolution_Test
    {

        [Test]
        public void GetHistoryEntriesInstances_2()
        {
            Instances dataset = RelativeTrendEvolution.GetTrendInstances("BRD", 2);
            Assert.AreEqual(925, dataset.numInstances());
            Assert.AreEqual(9, dataset.numAttributes());
            Assert.AreEqual("DateTime, O1, H1, L1, C1, O2, H2, L2, C2", RelativeTrendEvolution.GetHeadersCsv(dataset));
            Assert.AreEqual("2005-01-03 00:00:00, 0.0000, 0.2000, -0.0400, 0.0800, 0.1000, 0.1700, 0.0500, 0.1400", RelativeTrendEvolution.GetInstanceCsv(dataset.instance(0)));
        }

    }
}
#endif
