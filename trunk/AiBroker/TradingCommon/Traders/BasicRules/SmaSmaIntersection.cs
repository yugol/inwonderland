/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/10/2009
 * Time: 10:35 PM
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
    public class SmaSmaIntersection : BasicRule
    {
        static List<TSParameter> defaultParameters = new List<TSParameter>();
		public override List<TSParameter> DefaultParameters { get { return defaultParameters; } }
		
        static SmaSmaIntersection()
        {
            // (Murphy 266[234])
			defaultParameters.Add(new TSParameterInt("SmaFast_optInTimePeriod", 10, 2, 200));
			defaultParameters.Add(new TSParameterInt("SmaSlow_optInTimePeriod", 50, 2, 200));
        }
        
        Core.MAType fastMaType = Core.MAType.Sma;
        Core.MAType slowMaType = Core.MAType.Sma;
        double[] maFast;
        double[] maSlow;
        
        public override void UpdateTimeSeries(TimeSeries timeSeries)
        {
            base.UpdateTimeSeries(timeSeries);
                        
            int fastPeriod = (int) actualParameters[0].Value;
            int slowPeriod = (int) actualParameters[1].Value;
            
            if (fastPeriod > slowPeriod) {
                int temp = fastPeriod;
                fastPeriod = slowPeriod;
                slowPeriod = temp;
            }
            
            int begIdx, nbElements;
            
            maFast = new double[timeSeries.Count];
            Core.MovingAverage(0, timeSeries.Count - 1, timeSeries.Closes, fastPeriod, fastMaType, out begIdx, out nbElements, maFast);
            ArrayUtil.MoveDataToEnd(maFast, begIdx, nbElements);
            
            maSlow = new double[timeSeries.Count];
            Core.MovingAverage(0, timeSeries.Count - 1, timeSeries.Closes, slowPeriod, slowMaType, out begIdx, out nbElements, maSlow);
            ArrayUtil.MoveDataToEnd(maSlow, begIdx, nbElements);
        }
        
        public override Decision Decide(int barIndex, TSStatus status)
        {
            Decision dec = new Decision();
            
            if (barIndex > Math.Max(actualParameters[0].Value, actualParameters[1].Value)) {
                double t0 = maFast[barIndex] - maSlow[barIndex];
                --barIndex;
                double tMinus1 = maFast[barIndex] - maSlow[barIndex];
                --barIndex;
                double tMinus2 = maFast[barIndex] - maSlow[barIndex];
                
                DecideIntersection(ref dec, t0, tMinus1, tMinus2);
            }
                        
            return dec;
        }
        
    }
}
