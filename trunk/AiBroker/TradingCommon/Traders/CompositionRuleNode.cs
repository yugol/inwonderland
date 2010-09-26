/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/16/2009
 * Time: 3:15 PM
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
using System.Text;

using TradingCommon.Traders.CompositionRules;

namespace TradingCommon.Traders
{
	public class CompositionRuleNode : RuleTree
	{
		List<RuleTree> children = new List<RuleTree>();
		public List<RuleTree> Children
		{
			get { return children; }
			set { children = value; }
		}
		
		CompositionRule cRule;
        public CompositionRule Rule
        {
            get { return cRule; }
        }
		
		public CompositionRuleNode(string ruleTypeName)
		{
		    ChangeRule(ruleTypeName);
		}
		
		public void ChangeRule(string ruleTypeName)
		{
		    cRule = CompositionRule.CreateInstance(ruleTypeName);
		}
		
		public override string ToString()
		{
			StringBuilder buf = new StringBuilder("(");
			buf.Append(Rule.GetType().Name);
			foreach (RuleTree child in Children) 
			{
				buf.Append(" ");
				buf.Append(child.ToString());
			}
			buf.Append(")");
			return buf.ToString();
		}
		
		public override List<BasicRuleNode> GetBasicRuleNodes(List<BasicRuleNode> basicRuleNodes)
		{
			foreach (RuleTree rule in Children) {
			    rule.GetBasicRuleNodes(basicRuleNodes);
			}
		    return basicRuleNodes;
		}
		
        public override Decision Decide(int barIndex, TSStatus status)
        {
            List<Decision> decisions = new List<Decision>();
            foreach (RuleTree rule in children) {
                decisions.Add(rule.Decide(barIndex, status));
            }
            return Rule.Decide(decisions);
        }
        
        public override List<RuleTree> PreOrderNodes(List<RuleTree> nodes)
        {
            nodes.Add(this);
            foreach (RuleTree node in children) {
                node.PreOrderNodes(nodes);
            }
            return nodes;
        }
	}
}
