/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/10/2009
 * Time: 1:25 AM
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
    public class TSParameterGenotype_Test
    {
        [Test]
        public void Create_from_TradingSystem()
        {
            string systemXml = "<ts><parameters><parameter val='10'>p1</parameter><parameter val='2'>p2</parameter></parameters><rule>(And (MomentumZeroline p1) (MomentumZeroline p2))</rule></ts>";
            TradingSystem system = TSParser.ParseTS(systemXml, false);
            
            TSParameterGenotype genotype = TSParameterGenotype.Create(system);
            Assert.AreEqual(16, genotype.BitCount);
            
            system.Parameters["p1"].BitLength = 3;
            system.Parameters["p2"].BitLength = 4;
            genotype = TSParameterGenotype.Create(system);
            Assert.AreEqual(7, genotype.BitCount);
        }
        
        [Test]
        public void Create_from_int_array()
        {
            TSParameterGenotype genotype = TSParameterGenotype.Create(new int[]{3, 4, 5});
            Assert.AreEqual(12, genotype.BitCount);
            Assert.AreEqual(3, genotype.GetGene(0).Rep.Count);
            Assert.AreEqual(4, genotype.GetGene(1).Rep.Count);
            Assert.AreEqual(5, genotype.GetGene(2).Rep.Count);
        }
        
        [Test]
        public void ReadValuesFrom()
        {
            TSParameterGenotype genotype = TSParameterGenotype.Create(new int[]{2, 3, 4});
            genotype.ReadValuesFrom(118m);
            Assert.AreEqual("10", genotype.GetGene(0).ToBitString());
            Assert.AreEqual("101", genotype.GetGene(1).ToBitString());
            Assert.AreEqual("0011", genotype.GetGene(2).ToBitString());
            Assert.AreEqual("10.101.0011", genotype.ToBitString());
        }
        
        [Test]
        public void Randomize()
        {
            TSParameterGenotype genotype = TSParameterGenotype.Create(new int[]{3, 4, 10});
            genotype.Randomize();
            // System.Console.WriteLine(BitArrayToString(genotype.GetGene(0).Rep));
            // System.Console.WriteLine(BitArrayToString(genotype.GetGene(1).Rep));
            // System.Console.WriteLine(BitArrayToString(genotype.GetGene(2).Rep));
            Assert.AreNotEqual("0000000000", genotype.GetGene(2).ToBitString());
        }
    
        [Test]
        public void CloneGeneReps()
        {
            TSParameterGenotype genotype = TSParameterGenotype.Create(new int[]{1, 2, 3});
            TSParameterGenotype clone = (TSParameterGenotype) genotype.Clone();
            clone.GetGene(1).Rep.Set(0, true);
            Assert.AreEqual(false, genotype.GetGene(1).Rep.Get(0));
        }
        
        [Test]
        public void DoMutation()
        {
            TSParameterGenotype genotype = TSParameterGenotype.Create(new int[]{1});
            TSParameterGenotype.DoMutation(genotype);
            Assert.AreEqual(true, genotype.GetGene(0).Rep.Get(0));
        }
        
        [Test]
        public void DoCrossover()
        {
            TSParameterGenotype genotype1 = TSParameterGenotype.Create(new int[]{1, 1});
            TSParameterGenotype genotype2 = TSParameterGenotype.Create(new int[]{1, 1});
            genotype2.GetGene(0).Rep.SetAll(true);
            genotype2.GetGene(1).Rep.SetAll(true);
            TSParameterGenotype.DoCrossover(genotype1, genotype2);
            Assert.AreEqual(false, genotype1.GetGene(0).Rep.Get(0));
            Assert.AreEqual(true, genotype1.GetGene(1).Rep.Get(0));
            Assert.AreEqual(true, genotype2.GetGene(0).Rep.Get(0));
            Assert.AreEqual(false, genotype2.GetGene(1).Rep.Get(0));
        }
        
        [Test]
        public void Compare()
        {
            TSParameterGenotype genotype1 = TSParameterGenotype.Create(new int[]{1, 1});
            TSParameterGenotype genotype2 = TSParameterGenotype.Create(new int[]{1, 1});
            Assert.AreEqual(0, genotype1.CompareTo(genotype2));
        }
    }
}
#endif
