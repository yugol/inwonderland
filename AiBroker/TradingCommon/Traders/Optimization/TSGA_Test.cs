/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/3/2009
 * Time: 1:28 PM
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

namespace TradingCommon.Traders.Optimization
{
    [TestFixture]
    public class TSGA_Test
    {
        // [Test]
        public void SelectPopulationIndex()
        {
            GAParameters optParam = new GAParameters();
            // optParam.RouletteStep = 1.01;
            TSGA optimizer = new TSGA(optParam, null, null, null, null, null);
            int[] distribution = new int[optimizer.OptParam.PopulationSize];
            for (int i = 0; i < 100000; ++i) {
                int selection = optimizer.SelectPopulationIndex();
                // System.Console.WriteLine(selection);
                distribution[selection]++;
            }
            for (int i = 0; i < optimizer.OptParam.PopulationSize; ++i) {
                System.Console.WriteLine(string.Format("{0}\t -> {2}\t\t -> {1}", i, distribution[i], optimizer.Roulette[i].ToString(".00")));
            }
        }
        
        [Test]
        public void SkipMe()
        {
        }
    }
}
#endif
