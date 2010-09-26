/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/3/2009
 * Time: 12:57 PM
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

using TradingCommon.Traders.Optimization.Tune;

namespace TradingCommon.Traders.Optimization.Search
{
    public class TSSeeker : TSOptimizer
    {
        SearchParameters searchParam;
        TSGA ga;
        
        public TSSeeker(SearchParameters param)
        {
            this.searchParam = param;
            ga = new TSGA(searchParam.OuterGAParam, GenerateRandomGenotype,
                          Evaluate, CheckStopCondition,
                          DoCrossover, DoMutation);
        }
        
        public override int EstimateWork()
        {
            return searchParam.OuterGAParam.PopulationSize * 
                (searchParam.OuterGAParam.GenerationSize + 1);
        }
        
        public override void RunOptimization()
        {
            ga.Run();
            EndOptimization();
        }
        
        TSGenotype GenerateRandomGenotype()
        {
            return TSRuleGenotype.Generate(searchParam.GenotypeParam);
        }
        
        ISolution Evaluate(TSGenotype genotype)
        {
            TradingSystem tSys = ((TSRuleGenotype) genotype).TSys;
            
            BoundedDataSpec data = searchParam.TrainData;
            
            TuneParameters tunerParams = new TuneParameters(data.Title, data.Period, tSys);
            tunerParams.FirstIndex = data.FirstIndex;
            tunerParams.LastIndex = data.LastIndex;
            tunerParams.FitnessParam = searchParam.InnerFitnessParam;
            tunerParams.GAParam = searchParam.InnerGAParam;
            
            TSTunerGA tuner = new TSTunerGA(tunerParams);
            tuner.SolutionBuffer.MaxSize = tunerParams.GAParam.PopulationSize / 2;
            
            tuner.RunOptimization();
            
            Solution best = null;
            data = searchParam.TestData;
            SimulationParameters simParam = new SimulationParameters(data.Title, data.Period, tSys);
            foreach (Solution solution in tuner.SolutionBuffer.Items) {
                if (solution.Fitness >= searchParam.MinFitness) {
                    TSParameterGenotype paramGenotype = TSParameterGenotype.Create(solution.TSys);
                    paramGenotype.ApplyTo(simParam.TSys);
                    TSTraderSimulation simulation = new TSTraderSimulation(simParam);
                    solution.Fitness = TSTuner.CalculateFitness(simulation, searchParam.OuterFitnessParam);
                } else if (solution.Fitness > 0) {
                    solution.Fitness = 0;
                }
                if ((best == null) || (solution.Fitness > best.Fitness)) {
                    best = solution;   
                }
            }
            
            genotype.Fitness = best.Fitness;
            SolutionBuffer.Add(best);
            
            SolutionEvaluated(best);
            return best;
        }
        
        bool CheckStopCondition(ISolution solution)
        {
            if (solution.Fitness >= searchParam.OuterFitnessParam.TargetFitness) {
                return true;    
            }
            return false;
        }
        
        void DoCrossover(TSGenotype genotype1, TSGenotype genotype2)
        {
            TSRuleGenotype.DoCrossover((TSRuleGenotype) genotype1, (TSRuleGenotype) genotype2, searchParam.GenotypeParam);
        }
        
        void DoMutation(TSGenotype genotype)
        {
            TSRuleGenotype.DoMutation((TSRuleGenotype) genotype, searchParam.GenotypeParam);
        }
        
    }
}
