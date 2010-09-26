/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/10/2009
 * Time: 12:39 AM
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
using TradingCommon.Traders;

namespace TradingCommon.Traders.Optimization.Tune
{
    public class TSTunerBruteForce : TSTuner
    {
        public TSTunerBruteForce(TuneParameters optParam)
            : base(optParam) { }
        
        public override int EstimateWork()
        {
            TSParameterGenotype genotype = TSParameterGenotype.Create(TSystem);
            int genotypeBitCount = genotype.BitCount;
            CheckSearchSpaceSize(genotypeBitCount);
            return (1 << genotypeBitCount);
        }
        
        public override void RunOptimization()
        {
            TSParameterGenotype genotype = TSParameterGenotype.Create(TSystem);
            int genotypeBitCount = genotype.BitCount;
            CheckSearchSpaceSize(genotypeBitCount);
            
            decimal searchSpace = 1;
            for (int i = 0; i < genotypeBitCount; ++i) {
                searchSpace *= 2;
            }
            searchSpace -= 1;
            
            for (decimal solution = 0; solution < searchSpace; ++solution)
            {
                genotype.ReadValuesFrom(solution);
                if (CheckStopCondition(Evaluate(genotype))) {
                    return;
                }
            }
            genotype.ReadValuesFrom(searchSpace);
            Evaluate(genotype);
            
            EndOptimization();
        }

        void CheckSearchSpaceSize(int genotypeBitCount)
        {
            if (genotypeBitCount > 30) {
                throw new ArgumentOutOfRangeException("Search space too large");
            }
        }
    }
}
