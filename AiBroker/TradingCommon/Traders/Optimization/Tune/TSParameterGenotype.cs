/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/9/2009
 * Time: 11:54 PM
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
using System.Collections;
using System.Collections.Generic;
using System.Text;

using TradingCommon.Traders;

namespace TradingCommon.Traders.Optimization.Tune
{
    public class TSParameterGenotype : TSGenotype
    {        
        public static TSParameterGenotype Create(TradingSystem ts)
        {
            TSParameterGenotype genotype = new TSParameterGenotype();
            foreach (string key in ts.Parameters.Keys) 
            {
                ParameterGene gene = new ParameterGene(key, ts.Parameters[key].BitValue);
                genotype.chromosome.Add(gene);
            }
            return genotype;
        }
        
        public static TSParameterGenotype Create(int[] sizes)
        {
            TSParameterGenotype genotype = new TSParameterGenotype();
            for (int i = 0; i < sizes.Length; ++i) 
            {
                ParameterGene gene = new ParameterGene(i.ToString(), new BitArray(sizes[i]));
                genotype.chromosome.Add(gene);
            }
            return genotype;
        }
        
        public static void DoMutation(TSParameterGenotype genotype)
        {
            int geneIndex = selector.Next(genotype.chromosome.Count);
            int locusIndex = selector.Next(genotype.chromosome[geneIndex].Rep.Count);
            genotype.chromosome[geneIndex].Rep.Set(locusIndex, !genotype.chromosome[geneIndex].Rep.Get(locusIndex));
        }
            
        public static void DoCrossover(TSParameterGenotype genotype1, TSParameterGenotype genotype2)
        {
            int crossoverPoint = selector.Next(genotype1.GeneCount - 1);
            for (int i = crossoverPoint + 1; i < genotype1.GeneCount; ++i) 
            {
                ParameterGene tempGene = genotype1.chromosome[i];
                genotype1.chromosome[i] = genotype2.chromosome[i];
                genotype2.chromosome[i] = tempGene;
            }
        }
        
        List<ParameterGene> chromosome = new List<ParameterGene>();
        
        public int BitCount
        {
            get
            {
                int bitLength = 0;
                foreach (ParameterGene gene in chromosome) {
                    bitLength += gene.Rep.Count;
                }
                return bitLength;
            }
        }
        
        public int GeneCount { get { return chromosome.Count; } }
        
        public ParameterGene GetGene(int index) 
        { 
            return chromosome[index];
        }
        
        private TSParameterGenotype() { }
        
        public void ReadValuesFrom(decimal bits)
        {
            foreach (ParameterGene gene in chromosome)
            {
                for (int i = gene.Rep.Count - 1; i >= 0; --i) 
                {
                    bool bit = (bits % 2) == 1;
                    if (bit) {
                        bits -= 1;    
                    }
                    bits /= 2;
                    gene.Rep.Set(i, bit);
                }
            }
        }
        
        public void ApplyTo(TradingSystem ts)
        {
            foreach (ParameterGene gene in chromosome) {
                ts.Parameters[gene.Name].BitValue = gene.Rep;
            }
        }
        
        public TSParameterGenotype Randomize()
        {
            foreach (ParameterGene gene in chromosome) {
                gene.Randomize();
            }
            return this;
        }
                
        public override TSGenotype Clone()
        {
            TSParameterGenotype clone = new TSParameterGenotype();
            for (int i = 0; i < chromosome.Count; ++i) 
            {
                ParameterGene gene = new ParameterGene(chromosome[i].Name, new BitArray(chromosome[i].Rep));
                clone.chromosome.Add(gene);
            }
            return clone;
        }
        
        public string ToBitString()
        {
            StringBuilder sb = new StringBuilder(chromosome[0].ToBitString());
            for (int i = 1; i < chromosome.Count; ++i) 
            {
                sb.Append(".");
                sb.Append(chromosome[i].ToBitString());
            }
            return sb.ToString();
        }
        
    }
}
