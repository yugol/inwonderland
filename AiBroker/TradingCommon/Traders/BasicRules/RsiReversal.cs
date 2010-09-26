/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/11/2009
 * Time: 12:58 AM
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
    public class RsiReversal : BasicRule
    {
        static List<TSParameter> defaultParameters = new List<TSParameter>(); 
        public override List<TSParameter> DefaultParameters { get { return defaultParameters; } }

        static RsiReversal()
        {
            // (Murphy 271[239])
            defaultParameters.Add(new TSParameterInt("Rsi_period", 14, 2, 200));
            defaultParameters.Add(new TSParameterInt("Rsi_overbought", 70, 0, 100));
            defaultParameters.Add(new TSParameterInt("Rsi_oversold", 30, 0, 100));
        }
        
        protected double[] rsi;
        
        public override void UpdateTimeSeries(TimeSeries timeSeries)
        {
            base.UpdateTimeSeries(timeSeries);
                        
            int begIdx, nbElements;
            rsi = new double[timeSeries.Count];
            Core.Rsi(0, timeSeries.Count - 1, timeSeries.Closes, (int) actualParameters[0].Value, out begIdx, out nbElements, rsi);
            ArrayUtil.MoveDataToEnd(rsi, begIdx, nbElements);
        }
        
        public override Decision Decide(int barIndex, TSStatus status)
        {
            Decision dec = new Decision();
            
            if (--barIndex > 0) {
                if (rsi[barIndex] > actualParameters[1].Value) {
                    dec.IsBuy = false;    
                    dec.IsSell = true;
                } else if (rsi[barIndex] < actualParameters[2].Value) {
                    dec.IsBuy = true;
                    dec.IsSell = false;
                }
            }
            
            return dec;
        }

    }
}
