/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 7:26 PM
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
using TicTacTec.TA.Library;
using TradingCommon.Util;

namespace TradingCommon.Traders.BasicRules
{
    public class MomentumLowerUpperBound : BasicRule
    {
        static List<TSParameter> defaultParameters = new List<TSParameter>();
		public override List<TSParameter> DefaultParameters { get { return defaultParameters; } }
        static MomentumLowerUpperBound()
        {
            // (Murphy 264[232])
			defaultParameters.Add(new TSParameterInt("Mom_optInTimePeriod", 10, 2, 200));
			defaultParameters.Add(new TSParameterReal("Mom_upperBoundary", 1, 0, 10));
			defaultParameters.Add(new TSParameterReal("Mom_lowerBoundary", -1, -10, 0));            
        }
        
        double[] mom;

        public override void UpdateTimeSeries(TimeSeries timeSeries)
        {
            base.UpdateTimeSeries(timeSeries);
                        
            int begIdx, nbElements;
            mom = new double[timeSeries.Count];
            Core.Mom(0, timeSeries.Count - 1, timeSeries.Closes, (int) actualParameters[0].Value, out begIdx, out nbElements, mom);
            ArrayUtil.MoveDataToEnd(mom, begIdx, nbElements);
        }
        
        public override Decision Decide(int barIndex, TSStatus status)
        {
            Decision dec = new Decision();
            
            if (mom[barIndex] > actualParameters[1].Value) {
                dec.IsBuy = false;    
                dec.IsSell = true;
            } else if (mom[barIndex] < actualParameters[2].Value) {
                dec.IsBuy = true;
                dec.IsSell = false;
            }
            
            return dec;
        }
    }
}
