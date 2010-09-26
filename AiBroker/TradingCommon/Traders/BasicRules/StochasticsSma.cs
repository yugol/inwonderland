/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/12/2009
 * Time: 5:14 PM
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
    public class StochasticsSma : BasicRule
    {
        static List<TSParameter> defaultParameters = new List<TSParameter>();
        public override List<TSParameter> DefaultParameters { get { return defaultParameters; } }

        static StochasticsSma()
        {
            // (Murphy 278[246])
            defaultParameters.Add(new TSParameterInt("Stochastics_fastKperiod", 14, 2, 200));
            defaultParameters.Add(new TSParameterInt("Stochastics_slowKperiod", 3, 1, 100));
            defaultParameters.Add(new TSParameterInt("Stochastics_fastDperiod", 3, 1, 100));
        }

        Core.MAType slowKmaType = Core.MAType.Sma;
        Core.MAType slowDmaType = Core.MAType.Sma;
        double[] slowK;
        double[] slowD;
        
        public override void UpdateTimeSeries(TimeSeries timeSeries)
        {
            base.UpdateTimeSeries(timeSeries);
            
            int begIdx, nbElements;
            
            slowK = new double[timeSeries.Count];
            slowD = new double[timeSeries.Count];
            
            Core.Stoch(0, timeSeries.Count - 1,
                       timeSeries.Highs, timeSeries.Lows, timeSeries.Closes,
                       (int) actualParameters[0].Value, (int) actualParameters[1].Value, slowKmaType,
                       (int) actualParameters[2].Value, slowDmaType,
                       out begIdx, out nbElements,
                       slowK, slowD);
            
            ArrayUtil.MoveDataToEnd(slowK, begIdx, nbElements);
            ArrayUtil.MoveDataToEnd(slowD, begIdx, nbElements);
        }
        
        public override Decision Decide(int barIndex, TSStatus status)
        {
            Decision dec = new Decision();
            
            if (barIndex > actualParameters[0].Value) {
                double t0 = slowK[barIndex] - slowD[barIndex];
                --barIndex;
                double tMinus1 = slowK[barIndex] - slowD[barIndex];
                --barIndex;
                double tMinus2 = slowK[barIndex] - slowD[barIndex];
                
                DecideIntersection(ref dec, t0, tMinus1, tMinus2);            
            }
            
            return dec;
        }
        
    }
}
