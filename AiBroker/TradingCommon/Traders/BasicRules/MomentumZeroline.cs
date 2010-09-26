/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 6:36 PM
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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHERTHE
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
    public class MomentumZeroline : BasicRule
    {
        static List<TSParameter> defaultParameters = new List<TSParameter>(); 
        public override List<TSParameter> DefaultParameters { get { return defaultParameters; } }
        
        static MomentumZeroline()
        {
            // (Murphy 263[231])
            defaultParameters.Add(new TSParameterInt("Mom_optInTimePeriod", 10, 2, 200));
        }

#if TEST        
        internal double[] mom;
#else
        double[] mom;
#endif

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
            
            if (mom[barIndex] > 0) {
                --barIndex;
                while ((barIndex >= 0) && (mom[barIndex] == 0)) {
                    --barIndex;
                }
                if ((barIndex >= 0) && (mom[barIndex] < 0)){
                    dec.IsBuy = true;    
                    dec.IsSell = false;
                }
            } else if (mom[barIndex] < 0) {
                --barIndex;
                while ((barIndex >= 0) && (mom[barIndex] == 0)) {
                    --barIndex;
                }
                if ((barIndex >= 0) && (mom[barIndex] > 0)){
                    dec.IsBuy = false;    
                    dec.IsSell = true;    
                }
            }
            
            return dec;
        }
    }
}
