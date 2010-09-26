/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/3/2009
 * Time: 1:11 PM
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

namespace TradingCommon.Traders.Optimization
{
    public delegate TSGenotype GAGenerateRandomGenotype();
    public delegate ISolution GAEvaluate(TSGenotype genotype);
    public delegate bool GACheckStopCondition(ISolution solution);
    public delegate void GADoCrossover(TSGenotype genotype1, TSGenotype genotype2);
    public delegate void GADoMutation(TSGenotype genotype);
    
    public class TSGA
    {
        static Random selector = new Random();

        GAGenerateRandomGenotype gaGenerateRandomGenotype;        
        GAEvaluate gaEvaluate;
        GACheckStopCondition gaCheckStopCondition;
        GADoCrossover gaDoCrossover;
        GADoMutation gaDoMutation;
        
        GAParameters optParam;
		
        List<TSGenotype> population;
		double[] roulette;
      
        public GAParameters OptParam
        {
            get { return optParam; }
        }
        
        public double[] Roulette
        {
            get { return roulette; }
        }
        
        public TSGA(GAParameters optParam,
                    GAGenerateRandomGenotype generateRandomGenotype,
                    GAEvaluate evaluate,
                    GACheckStopCondition checkStopCondition,
                    GADoCrossover doCrossover,
                    GADoMutation doMutation)
        {
            this.gaGenerateRandomGenotype = generateRandomGenotype;
            this.gaEvaluate = evaluate;
            this.gaCheckStopCondition = checkStopCondition;
            this.gaDoCrossover = doCrossover;
            this.gaDoMutation = doMutation;
            this.optParam = optParam;
            CreateSelectionProbabilitiesRanges();
        }
        
        void CreateSelectionProbabilitiesRanges()
        {
            roulette = new double[optParam.PopulationSize + 1];
            roulette[0] = 1; // the first interval size
            for (int i = 1; i < roulette.Length; ++i) {
                roulette[i] = roulette[i-1] * optParam.RouletteStep;
            }
        }
        
        public int SelectPopulationIndex()
        {
            if (optParam.RouletteStep == 1) {
                return selector.Next(optParam.PopulationSize);
            } else {
                double choice = selector.NextDouble() * roulette[roulette.Length - 1];
                int selection = Array.BinarySearch(roulette, choice);
                if (selection >= 0) {
                    return selection - 1;    
                } else if (selection < -1) {
                    return -selection -2;   
                } else {
                    return SelectPopulationIndex();
                }
            }
        }
        
        void InitializePopulation()
        {
            population = new List<TSGenotype>(optParam.PopulationSize);
            for (int i = 0; i < optParam.PopulationSize; ++i) {
                population.Add(gaGenerateRandomGenotype());
            }
        }
        
        bool EvaluatePopulation()
        {
            foreach (TSGenotype genotype in population) {
                if (gaCheckStopCondition(gaEvaluate(genotype))) {
                    return true;  
                }
            }
            population.Sort();
            return false;
        }
        
        void CreateNextGeneration()
        {
            List<TSGenotype> nextPopulation = new List<TSGenotype>(optParam.PopulationSize);
            
            while (nextPopulation.Count < population.Count) 
            {
                TSGenotype child1 = population[SelectPopulationIndex()].Clone();
                TSGenotype child2 = population[SelectPopulationIndex()].Clone();
                
                if (selector.NextDouble() <= optParam.CrossoverRate) {
                    gaDoCrossover(child1, child2);
                }
                if (selector.NextDouble() <= optParam.MutationRate) {
                    gaDoMutation(child1);
                }
                if (selector.NextDouble() <= optParam.MutationRate) {
                    gaDoMutation(child2);
                }
                
                nextPopulation.Add(child1);
                nextPopulation.Add(child2);
            }
            
            population = nextPopulation;
        }
        
        public void Run()
        {
            InitializePopulation();
            if (!EvaluatePopulation()) {
                for (int generation = 0; generation < optParam.GenerationSize; ++generation) 
                {
                    CreateNextGeneration();
                    if (EvaluatePopulation()) {
                        break;    
                    }
                }
            }
        }
        
    }
}
