/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/11/2009
 * Time: 4:12 PM
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
using System;
using System.Collections.Generic;
using TradingCommon.Traders;

namespace TradingCommon.Traders.Optimization.Tune
{
    public class TSTunerGA : TSTuner
    {        
        TSGA ga;
        
        public TSTunerGA(TuneParameters tuneParam)
            : base(tuneParam) 
        { 
            ga = new TSGA(tuneParam.GAParam, GenerateRandomGenotype,
                          Evaluate, CheckStopCondition,
                          DoCrossover, DoMutation);
        }
        
        public override int EstimateWork()
        {
            return TuneParam.GAParam.PopulationSize * 
                  (TuneParam.GAParam.GenerationSize + 1);
        }
        
        public override void RunOptimization()
        {
            ga.Run();
            EndOptimization();
        }
        
        TSGenotype GenerateRandomGenotype()
        {
            return TSParameterGenotype.Create(TuneParam.TSys).Randomize();
        }
        
        ISolution Evaluate(TSGenotype genotype)
        {
            return base.Evaluate((TSParameterGenotype) genotype);
        }
        
        void DoCrossover(TSGenotype genotype1, TSGenotype genotype2)
        {
            TSParameterGenotype.DoCrossover((TSParameterGenotype) genotype1, (TSParameterGenotype) genotype2);
        }

        void DoMutation(TSGenotype genotype)
        {
            TSParameterGenotype.DoMutation((TSParameterGenotype) genotype);
        }
    }
}