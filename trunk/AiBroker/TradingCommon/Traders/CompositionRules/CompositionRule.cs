/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/26/2009
 * Time: 11:47 AM
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
using System.Reflection;

namespace TradingCommon.Traders.CompositionRules
{
    public abstract class CompositionRule
    {
        static Assembly assembly = Assembly.Load("TradingCommon");
        static readonly List<string> validRules = new List<string>();
        
		public static CompositionRule CreateInstance(string typeName)
		{
            foreach (Type t in assembly.GetTypes()) {
                if (t.IsSubclassOf(typeof(CompositionRule))) {
		            if (t.Name == typeName) {
		                return (CompositionRule) Activator.CreateInstance(t);
		            }
                }
            }
		    throw new InvalidOperationException("Cannot instantiate type '" + typeName + "'");
		}

		static CompositionRule()
		{
            foreach (Type t in assembly.GetTypes()) {
                if (t.IsSubclassOf(typeof(CompositionRule))) {
                    validRules.Add(t.Name);
                }
            }		    
		}  
		
		public static bool IsValid(string ruleTypeName)
		{
		    return validRules.Contains(ruleTypeName);
		}
		
        public static List<string> ValidRules
        {
            get { return validRules; }
        }

		
		public Decision Decide(List<Decision> decisions)
        {
            Decision decision = decisions[0];
            for (int i = 1; i < decisions.Count; ++i) {
                decision = Decide(decision, decisions[i]);
            }
            return decision;
        }
		
        public abstract Decision Decide(Decision d1, Decision d2);
    }
}
