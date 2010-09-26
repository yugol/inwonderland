/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/11/2009
 * Time: 4:14 PM
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
    public class TSTunerGA_Test
    {        
        [Test]
        public void Optimization()
        {
            string systemXml = "<ts><parameters><parameter val='10'>p1</parameter><parameter val='2'>p2</parameter></parameters><rule>(And (MomentumZeroline p1) (MomentumZeroline p2))</rule></ts>";
            TradingSystem system = TSParser.ParseTS(systemXml, false);
            TuneParameters optParam = new TuneParameters(new Title("BRD"), TimeSeries.DAY_PERIOD, system);
            optParam.FitnessParam.TargetFitness = 20;
            TSTunerGA optimizer = new TSTunerGA(optParam);
            // optimizer.OptimizationSolutionEvaluated += new OptimizationSolutionEvaluatedHandler(Print);
            optParam.GAParam.PopulationSize = 100;
            optParam = new TuneParameters(new Title("BRD"), TimeSeries.DAY_PERIOD, system);
            optimizer.RunOptimization();
            // Print(optimizer.SolutionBuffer);
            Assert.IsTrue(optimizer.SolutionBuffer.Items[0].Fitness >= 20, "Max fitness = " + optimizer.SolutionBuffer.Items[0].Fitness);
        }
        
        void Print(ISolution solution)
        {
            System.Console.WriteLine(solution.Fitness);
        }
        
        void Print(SolutionBuffer buffer)
        {
            System.Console.WriteLine("--- SolutionBuffer ---");
            foreach (ISolution solution in buffer.Items) {
                Print(solution);
            }
        }
        
    }
}
#endif
