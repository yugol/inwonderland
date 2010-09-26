/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/2/2009
 * Time: 11:35 PM
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
using NUnit.Framework;
using TradingCommon.Traders.BasicRules;

namespace TradingCommon.Traders.Optimization.Search
{
    [TestFixture]
    public class TSRuleGenotype_Test
    {
        // [Test]
        public void Generate()
        {
            GenotypeParameters paramz = new GenotypeParameters();
            // param.MaxRuleCount = 10;
            TSRuleGenotype genotype = TSRuleGenotype.Generate(paramz);
            System.Console.WriteLine(genotype.TSys.ToXmlString());
        }
        
        [Test]
        public void DoCrossover_1_1()
        {
            List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
            GenotypeParameters paramz = new GenotypeParameters();

            TradingSystem tSys1 = new TradingSystem();
            tSys1.RuleTree = new BasicRuleNode("MomentumZeroline");
            tSys1.FetchParameters();
            TSRuleGenotype genotype1 = new TSRuleGenotype();
            genotype1.TSys = tSys1;
            
            TradingSystem tSys2 = new TradingSystem();
            tSys2.RuleTree = new BasicRuleNode("MomentumLowerUpperBound");
            tSys2.FetchParameters();
            TSRuleGenotype genotype2 = new TSRuleGenotype();
            genotype2.TSys = tSys2;
            
            TSRuleGenotype.DoCrossover(genotype1, genotype2, paramz);
            
            Assert.IsInstanceOf<BasicRuleNode>(genotype1.TSys.RuleTree);
            Assert.IsInstanceOf<MomentumZeroline>(genotype1.TSys.RuleTree.GetBasicRuleNodes(brNodes)[0].Rule);
            Assert.IsInstanceOf<BasicRuleNode>(genotype2.TSys.RuleTree);
            brNodes.Clear();
            Assert.IsInstanceOf<MomentumLowerUpperBound>(genotype2.TSys.RuleTree.GetBasicRuleNodes(brNodes)[0].Rule);
        }
                
        [Test]
        public void DoCrossover_1_n()
        {
            List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
            GenotypeParameters paramz = new GenotypeParameters();

            TradingSystem tSys1 = new TradingSystem();
            tSys1.RuleTree = new BasicRuleNode("MomentumZeroline");
            tSys1.FetchParameters();
            TSRuleGenotype genotype1 = new TSRuleGenotype();
            genotype1.TSys = tSys1;
            
            TradingSystem tSys2 = new TradingSystem();
            tSys2.RuleTree = new CompositionRuleNode("And");
            List<RuleTree> basicRules = ((CompositionRuleNode) tSys2.RuleTree).Children;
            basicRules.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            basicRules.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            basicRules.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            tSys2.FetchParameters();
            TSRuleGenotype genotype2 = new TSRuleGenotype();
            genotype2.TSys = tSys2;
            
            
            // System.Console.WriteLine(genotype1.TSys.ToXmlString());
            // System.Console.WriteLine(genotype2.TSys.ToXmlString());

            TSRuleGenotype.DoCrossover(genotype1, genotype2, paramz);
            
            // System.Console.WriteLine(genotype1.TSys.ToXmlString());
            // System.Console.WriteLine(genotype2.TSys.ToXmlString());

            
            Assert.IsInstanceOf<BasicRuleNode>(genotype1.TSys.RuleTree);
            Assert.IsInstanceOf<MomentumLowerUpperBound>(genotype1.TSys.RuleTree.GetBasicRuleNodes(brNodes)[0].Rule);
            Assert.IsInstanceOf<CompositionRuleNode>(genotype2.TSys.RuleTree);
            Assert.AreEqual(3, basicRules.Count);
            int i;
            for (i = 0; i < basicRules.Count; ++i) {
                if (((BasicRuleNode) basicRules[i]).Rule is MomentumZeroline) {
                    break;
                }
            }
            Assert.Less(i, basicRules.Count);
            
        }
        
        [Test]
        public void DoCrossover_n_m()
        {
            List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
            GenotypeParameters paramz = new GenotypeParameters();
            paramz.MaxRuleCount = 10;

            TradingSystem tSys1 = new TradingSystem();
            tSys1.RuleTree = new CompositionRuleNode("Or");
            List<RuleTree> rules1 = ((CompositionRuleNode) tSys1.RuleTree).Children;
            rules1.Add(new BasicRuleNode("MomentumZeroline"));
            rules1.Add(new BasicRuleNode("MomentumZeroline"));
            rules1.Add(new BasicRuleNode("MomentumZeroline"));
            tSys1.FetchParameters();
            TSRuleGenotype genotype1 = new TSRuleGenotype();
            genotype1.TSys = tSys1;
            
            TradingSystem tSys2 = new TradingSystem();
            tSys2.RuleTree = new CompositionRuleNode("And");
            List<RuleTree> rules2 = ((CompositionRuleNode) tSys2.RuleTree).Children;
            rules2.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            rules2.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            rules2.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            rules2.Add(new BasicRuleNode("MomentumLowerUpperBound"));
            tSys2.FetchParameters();
            TSRuleGenotype genotype2 = new TSRuleGenotype();
            genotype2.TSys = tSys2;
            
            // System.Console.WriteLine(genotype1.TSys.ToXmlString());
            // System.Console.WriteLine(genotype2.TSys.ToXmlString());

            TSRuleGenotype.DoCrossover(genotype1, genotype2, paramz);
            
            // System.Console.WriteLine(genotype1.TSys.ToXmlString());
            // System.Console.WriteLine(genotype2.TSys.ToXmlString());
            
            int i;

            List<BasicRuleNode> basicRules1 = genotype1.TSys.RuleTree.GetBasicRuleNodes(brNodes);
            for (i = 0; i < basicRules1.Count; ++i) {
                if (basicRules1[i].Rule is MomentumLowerUpperBound) {
                    break;
                }
            }
            Assert.Less(i, basicRules1.Count);

            List<BasicRuleNode> basicRules2 = genotype2.TSys.RuleTree.GetBasicRuleNodes(brNodes);
            for (i = 0; i < basicRules2.Count; ++i) {
                if (basicRules2[i].Rule is MomentumZeroline) {
                    break;
                }
            }
            Assert.Less(i, basicRules2.Count);
        }
        
        [Test]
        public void DoMutation_Deletion()
        {
            TradingSystem tSys = new TradingSystem();
            tSys.RuleTree = new CompositionRuleNode("Or");
            List<RuleTree> rules = ((CompositionRuleNode) tSys.RuleTree).Children;
            rules.Add(new BasicRuleNode("MomentumZeroline"));
            rules.Add(new BasicRuleNode("MomentumZeroline"));
            rules.Add(new BasicRuleNode("MomentumZeroline"));
            tSys.FetchParameters();
            TSRuleGenotype genotype = new TSRuleGenotype();
            genotype.TSys = tSys;
            
            TSRuleGenotype.DoMutation_Deletion(genotype);
            Assert.AreEqual(2, rules.Count);

            TSRuleGenotype.DoMutation_Deletion(genotype);
            Assert.IsInstanceOf<BasicRuleNode>(genotype.TSys.RuleTree);
        }
        
        [Test]
        public void DoMutation_Replacement_1()
        {
            TradingSystem tSys = new TradingSystem();
            tSys.RuleTree = new BasicRuleNode("MomentumZeroline");
            tSys.FetchParameters();
            TSRuleGenotype genotype = new TSRuleGenotype();
            genotype.TSys = tSys;
            
            TSRuleGenotype.DoMutation_Replacement(genotype);
            Assert.IsNotInstanceOf<MomentumZeroline>(((BasicRuleNode) genotype.TSys.RuleTree).Rule);
        }
        
        // [Test]
        public void DoMutation_Replacement_n()
        {
            TradingSystem tSys = new TradingSystem();
            tSys.RuleTree = new CompositionRuleNode("Or");
            List<RuleTree> rules = ((CompositionRuleNode) tSys.RuleTree).Children;
            rules.Add(new BasicRuleNode("MomentumZeroline"));
            rules.Add(new BasicRuleNode("MomentumZeroline"));
            tSys.FetchParameters();
            TSRuleGenotype genotype = new TSRuleGenotype();
            genotype.TSys = tSys;
            
            TSRuleGenotype.DoMutation_Replacement(genotype);
            
            System.Console.WriteLine(genotype.TSys.ToXmlString());
        }
        
        [Test]
        public void DoMutation_Insertion()
        {
            TradingSystem tSys = new TradingSystem();
            tSys.RuleTree = new BasicRuleNode("MomentumZeroline");
            tSys.FetchParameters();
            TSRuleGenotype genotype = new TSRuleGenotype();
            genotype.TSys = tSys;
            
            TSRuleGenotype.DoMutation_Insertion(genotype);
            
            List<RuleTree> rules = ((CompositionRuleNode) tSys.RuleTree).Children;
            Assert.AreEqual(2, rules.Count);
            
            TSRuleGenotype.DoMutation_Insertion(genotype);
            Assert.AreEqual(3, rules.Count);
        }
        
        
    }
}
#endif
