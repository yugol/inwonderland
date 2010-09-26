/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 6:25 PM
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

namespace TradingCommon.Traders.BasicRules
{
    public abstract class BasicRule
    {
        // don't move actual parameters to base class (here)
        // they differ from type to type
        
        static Assembly assembly = Assembly.Load("TradingCommon");
        static readonly List<string> validRules = new List<string>();
        
		public abstract List<TSParameter> DefaultParameters { get; }
		protected TimeSeries series;
		protected TSParameter[] actualParameters = null;

		public static BasicRule CreateInstance(string typeName)
		{
            foreach (Type t in assembly.GetTypes()) {
                if (t.IsSubclassOf(typeof(BasicRule))) {
		            if (t.Name == typeName) {
		                return (BasicRule) Activator.CreateInstance(t);
		            }
                }
            }
		    throw new InvalidOperationException("Cannot instantiate type '" + typeName + "'");
		}

		static BasicRule()
		{
            foreach (Type t in assembly.GetTypes()) {
                if (t.IsSubclassOf(typeof(BasicRule))) {
                    validRules.Add(t.Name);
                }
            }		    
		}  
		
        public static List<string> ValidRules
        {
            get { return validRules; }
        }
		
		public static bool IsValid(string ruleTypeName)
		{
		    return validRules.Contains(ruleTypeName);
		}

		public virtual void UpdateTimeSeries(TimeSeries timeSeries)
		{
		    series = timeSeries;    
		}
        
        public void SetActualParameters(List<string> names, Dictionary<string, TSParameter> systemParameters)
        {
            actualParameters = new TSParameter[DefaultParameters.Count];
            for (int i = 0; i < actualParameters.Length; ++i ) {
                actualParameters[i] = systemParameters[names[i]];
            }
        }
		
        public abstract Decision Decide(int barIndex, TSStatus status);
        
        public TSParameter[] ActualParameters
        {
            get { return actualParameters; }
        }
        
        protected void DecideIntersection(ref Decision dec, double t0, double tMinus1, double tMinus2)
        {
            double curr = t0;
            double prev = tMinus1;
            if (prev == 0) {
                prev = tMinus2;
            }

            if (prev < 0 && curr > 0) {
                dec.IsBuy = true;
                dec.IsSell = false;
            } else if (prev > 0 && curr < 0) {
                dec.IsBuy = false;
                dec.IsSell = true;
            }
        }
        
        public static double EstimateCrossPoint(double[] prev, double[] curr)
        {
            double m1 = (prev[0] + prev[1]) / 2;
            double m2 = (curr[0] + curr[1]) / 2;
            double m = (m1 + m2) / 2;
            return m;
        }
        
    }
}
