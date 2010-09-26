/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/2/2009
 * Time: 9:36 PM
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

using TradingCommon.Traders.BasicRules;
using TradingCommon.Traders.CompositionRules;

namespace TradingCommon.Traders.Optimization.Search
{
    public class TSRuleGenotype : TSGenotype
    {
        TradingSystem tSys;
        
        public TradingSystem TSys
        {
            get { return tSys; }
#if TEST
            set { tSys = value; }
#endif
        }
        
        public TSRuleGenotype()
        {
            this.tSys = new TradingSystem();
        }
        
        public TSRuleGenotype(TradingSystem tSys)
        {
            this.tSys = tSys;
        }
        
        public override TSGenotype Clone()
        {
            return new TSRuleGenotype(TSParser.ParseTS(tSys.ToXmlString(), false));
        }
        
        public static TSRuleGenotype Generate(GenotypeParameters param)
        {
            TSRuleGenotype genotype = new TSRuleGenotype();
            
            int basicRuleCount = selector.Next(1, param.MaxRuleCount + 1);
            
            if (basicRuleCount == 1) {
                genotype.TSys.RuleTree = new BasicRuleNode(GetRandomBasicRuleName(null));
            } else {
                genotype.TSys.RuleTree = new CompositionRuleNode(GetRandomCompositionRuleNode(null));
                for (int i = 0; i < basicRuleCount; ++i) {
                    ((CompositionRuleNode)genotype.TSys.RuleTree).Children.Add(new BasicRuleNode(GetRandomBasicRuleName(null)));
                }
            }
            
            genotype.TSys.FetchParameters();
            
            return genotype;
        }
        
        static string GetRandomBasicRuleName(string except)
        {
            while (true) {
                string name = BasicRule.ValidRules[selector.Next(BasicRule.ValidRules.Count)];
                if (name != except) {
                    return name;    
                }
            }
        }

        static string GetRandomCompositionRuleNode(string except)
        {
            while (true) {
                string name = CompositionRule.ValidRules[selector.Next(CompositionRule.ValidRules.Count)];
                if (name != except) {
                    return name;    
                }
            }
        }
        
        public static void DoCrossover(TSRuleGenotype genotype1, TSRuleGenotype genotype2, GenotypeParameters param)
        {
            if (genotype1.TSys.RuleTree is BasicRuleNode) {
                if (genotype2.TSys.RuleTree is BasicRuleNode) {
                    return;
                } else {
                    DoCrossover_1_n(genotype1, genotype2);
                }
            } else {
                if (genotype2.TSys.RuleTree is BasicRuleNode) {
                    DoCrossover_1_n(genotype2, genotype1);
                } else {
                    DoCrossover_n_m(genotype1, genotype2, param);
                }
            }
            genotype1.TSys.ResetParameters();
            genotype2.TSys.ResetParameters();
        }

        static void DoCrossover_1_n(TSRuleGenotype genotype1, TSRuleGenotype genotype2)
        {
            BasicRuleNode temp = (BasicRuleNode) genotype1.TSys.RuleTree;
            List<RuleTree> basicRules = ((CompositionRuleNode) genotype2.TSys.RuleTree).Children;
            int pos = selector.Next(basicRules.Count);
            genotype1.TSys.RuleTree = basicRules[pos];
            basicRules[pos] = temp;
        }
        
        static void DoCrossover_n_m(TSRuleGenotype genotype1, TSRuleGenotype genotype2, GenotypeParameters param)
        {
            List<RuleTree> basicRules1 = ((CompositionRuleNode) genotype1.TSys.RuleTree).Children;
            List<RuleTree> basicRules2 = ((CompositionRuleNode) genotype2.TSys.RuleTree).Children;

            int pos1, pos2;
            while (true) {
                pos1 = selector.Next(basicRules1.Count - 1) + 1;
                pos2 = selector.Next(basicRules2.Count - 1) + 1;
                if ((basicRules2.Count - pos2 + pos1 <= param.MaxRuleCount) &&
                    (basicRules1.Count - pos1 + pos2 <= param.MaxRuleCount)
                   ) {
                    break;
                }
            }
            
            List<RuleTree> split1 = new List<RuleTree>();
            for (int i = pos1; i < basicRules1.Count; ++i) {
                split1.Add(basicRules1[i]);
            }
            basicRules1.RemoveRange(pos1, basicRules1.Count - pos1);
            
            List<RuleTree> split2 = new List<RuleTree>();
            for (int i = pos2; i < basicRules2.Count; ++i) {
                split2.Add(basicRules2[i]);
            }
            basicRules2.RemoveRange(pos2, basicRules2.Count - pos2);
            
            basicRules1.AddRange(split2);
            basicRules2.AddRange(split1);
        }
        
        public static void DoMutation(TSRuleGenotype genotype, GenotypeParameters param)
        {
            int kind;
            List<BasicRuleNode> brNodes = new List<BasicRuleNode>();
            
            if (param.MaxRuleCount == 1) {
                kind = 1;    
            } else if (genotype.TSys.RuleTree is BasicRuleNode) {
                kind = selector.Next(1, 3);
            } else if (genotype.TSys.RuleTree.GetBasicRuleNodes(brNodes).Count == param.MaxRuleCount) {
                kind = selector.Next(0, 2);
            } else {
                kind = selector.Next(0, 3);
            }
            
            switch (kind)
            {
                case 0:
                    DoMutation_Deletion(genotype);
                    break;
                case 1:
                    DoMutation_Replacement(genotype);
                    break;
                case 2:
                    DoMutation_Insertion(genotype);
                    break;
            }
            
            genotype.TSys.ResetParameters();
        }

#if TEST
        public
#endif
        static void DoMutation_Deletion(TSRuleGenotype genotype)
        {
            List<RuleTree> rules = ((CompositionRuleNode) genotype.TSys.RuleTree).Children;
            int pos = selector.Next(rules.Count);
            rules.RemoveAt(pos);
            if (rules.Count == 1) {
                genotype.TSys.RuleTree = rules[0];
            }
        }
        
#if TEST
        public
#endif
        static void DoMutation_Replacement(TSRuleGenotype genotype)
        {
            if (genotype.TSys.RuleTree is BasicRuleNode) {
                string currName = ((BasicRuleNode) genotype.TSys.RuleTree).Rule.GetType().Name;
                genotype.TSys.RuleTree = new BasicRuleNode(GetRandomBasicRuleName(currName));
            } else {
                List<RuleTree> nodes = new List<RuleTree>();
                genotype.TSys.RuleTree.PreOrderNodes(nodes);
                int pos = selector.Next(nodes.Count);
                if (nodes[pos] is CompositionRuleNode) {
                    CompositionRuleNode node = (CompositionRuleNode) nodes[pos];
                    node.ChangeRule(GetRandomCompositionRuleNode(node.Rule.GetType().Name));
                } else if (nodes[pos] is BasicRuleNode) {
                    BasicRuleNode node = (BasicRuleNode) nodes[pos];
                    node.ChangeRule(GetRandomBasicRuleName(node.Rule.GetType().Name));
                }
            }
        }
        
#if TEST
        public
#endif
        static void DoMutation_Insertion(TSRuleGenotype genotype)
        {
            if (genotype.TSys.RuleTree is BasicRuleNode) {
                CompositionRuleNode root = new CompositionRuleNode(GetRandomCompositionRuleNode(null));
                root.Children.Add(genotype.TSys.RuleTree);
                root.Children.Add(new BasicRuleNode(GetRandomBasicRuleName(null)));
                genotype.TSys.RuleTree = root;
            } else {
                ((CompositionRuleNode) genotype.TSys.RuleTree).Children.Add(new BasicRuleNode(GetRandomBasicRuleName(null)));
            }
        }
        
    }
}
