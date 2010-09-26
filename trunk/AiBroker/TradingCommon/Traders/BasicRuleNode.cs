/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/16/2009
 * Time: 3:16 PM
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

namespace TradingCommon.Traders
{
	public class BasicRuleNode : RuleTree
	{				
		List<string> parameterNames = new List<string>();
		BasicRule bRule;
		
		public List<string> ParameterNames { get { return parameterNames; } }
		
        public BasicRule Rule { get { return bRule; } }
		
		public BasicRuleNode(string ruleTypeName)
		{
		    ChangeRule(ruleTypeName);
		}
		
		public void ChangeRule(string ruleTypeName)
		{
		    bRule = BasicRule.CreateInstance(ruleTypeName);
		}
	
		public override string ToString()
		{
		    return "\n(" + Rule.GetType().Name + "\n\t" + string.Join("\n\t", ParameterNames.ToArray()) + ")";
		}
		
		public override List<BasicRuleNode> GetBasicRuleNodes(List<BasicRuleNode> basicRuleNodes)
		{
			basicRuleNodes.Add(this);
			return basicRuleNodes;
		}
		
		public void UpdateTimeSeries(TimeSeries timeSeries)
		{
		    bRule.UpdateTimeSeries(timeSeries);
		}
		
        public override Decision Decide(int barIndex, TSStatus status)
        {
            return Rule.Decide(barIndex, status);
        }
        
        public override List<RuleTree> PreOrderNodes(List<RuleTree> nodes)
        {
            nodes.Add(this);
            return nodes;
        }
	}
}
