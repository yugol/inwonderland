/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/10/2009
 * Time: 12:13 AM
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
    public abstract class TSTuner : TSOptimizer
    {
        TuneParameters tuneParam;
        public TuneParameters TuneParam { get { return tuneParam; } }                
        
        public TSTuner(TuneParameters tuneParam)
        {
            this.tuneParam = tuneParam;
        }
        public TradingSystem TSystem { get { return TuneParam.TSys; } }        
        
        public ISolution Evaluate(TSParameterGenotype genotype)
        {
            genotype.ApplyTo(TuneParam.TSys);
            TSTraderSimulation simulation = new TSTraderSimulation(TuneParam);
            Solution solution = CreateSolution(simulation, TuneParam.FitnessParam);
            genotype.Fitness = solution.Fitness;
            SolutionBuffer.Add(solution);
            SolutionEvaluated(solution);
            return solution;
        }
        
        protected bool CheckStopCondition(ISolution solution) 
        {
            if (solution.Fitness >= TuneParam.FitnessParam.TargetFitness) {
                return true;    
            }
            return false;
        }
        
        public static double CalculateFitness(TSTraderSimulation simulation, FitnessParameters optParam)
        {
            double fitness = simulation.Status.NetProfit;
            if (fitness > 0) {
                DateTime from = simulation.Series.Dates[simulation.FirstIndex].Date;
                DateTime to = simulation.Series.Dates[simulation.LastIndex].Date;
                double tradeFrequency = simulation.Status.Trades.Count / to.Subtract(from).TotalDays;
                if (tradeFrequency < optParam.MinTradesPerDay || 
                    tradeFrequency > optParam.MaxTradesPerDay) {
                    fitness = 0;
                }
            }
            return fitness;
        }
        
        public static Solution CreateSolution(TSTraderSimulation simulation, FitnessParameters optParams)
        {
            return new Solution(simulation.Title, 
                                simulation.Period, 
                                simulation.TSys,
                                simulation.FirstIndex, 
                                simulation.LastIndex,
                                CalculateFitness(simulation, optParams));
        }
    }
}
