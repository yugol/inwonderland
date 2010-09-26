/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/12/2009
 * Time: 6:19 PM
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
    public class MacdSma : BasicRule
    {
        static List<TSParameter> defaultParameters = new List<TSParameter>();
		public override List<TSParameter> DefaultParameters { get { return defaultParameters; } }

		static MacdSma()
        {
            // (Murphy 284[252])
			defaultParameters.Add(new TSParameterInt("SmaFast_fastPeriod", 12, 2, 200));
			defaultParameters.Add(new TSParameterInt("SmaSlow_slowPeriod", 26, 2, 200));
			defaultParameters.Add(new TSParameterInt("SmaSlow_signalPeriod", 9, 2, 200));
			defaultParameters.Add(new TSParameterReal("Macd_overbought", 0.1, -10, 10));
			defaultParameters.Add(new TSParameterReal("Macd_oversold", -0.1, -10, 10));            
        }
		
		Core.MAType fastMaType = Core.MAType.Ema;
		Core.MAType slowMaType = Core.MAType.Ema;
		Core.MAType signalMaType = Core.MAType.Ema;
		double[] macd;
		double[] macdSig;
		double[] macdHist;
		
        public override void UpdateTimeSeries(TimeSeries timeSeries)
        {
            base.UpdateTimeSeries(timeSeries);
                        
            int begIdx, nbElements;
            
            macd = new double[timeSeries.Count];
            macdSig = new double[timeSeries.Count];
            macdHist = new double[timeSeries.Count];
            
            Core.MacdExt(0, timeSeries.Count - 1, timeSeries.Closes,
                         (int) actualParameters[0].Value, fastMaType,
                         (int) actualParameters[1].Value, slowMaType,
                         (int) actualParameters[2].Value, signalMaType,
                         out begIdx, out nbElements,
                         macd, macdSig, macdHist);
            
            ArrayUtil.MoveDataToEnd(macd, begIdx, nbElements);
            ArrayUtil.MoveDataToEnd(macdSig, begIdx, nbElements);
            ArrayUtil.MoveDataToEnd(macdHist, begIdx, nbElements);
        }
		
        public override Decision Decide(int barIndex, TSStatus status)
        {
            Decision dec = new Decision();
            
            if (barIndex > actualParameters[1].Value) {
                double[] t0 = {macd[barIndex], macdSig[barIndex]};
                --barIndex;
                double[] tMinus1 = {macd[barIndex], macdSig[barIndex]};
                --barIndex;
                double[] tMinus2 = {macd[barIndex], macdSig[barIndex]};
                
                double[] curr = t0;
                double[] prev = tMinus1;
                if (prev[0] == prev[1]) {
                    prev = tMinus2;
                }
    
                double currDelta = curr[0] - curr[1];
                double prevDelta = prev[0] - prev[1];
                double crossPoint = EstimateCrossPoint(prev, curr);
                
                if (prevDelta < 0 && currDelta > 0) {
                    if (crossPoint < actualParameters[4].Value) {
                        dec.IsBuy = true;
                        dec.IsSell = false;
                    }
                } else if (prevDelta > 0 && currDelta < 0) {
                    if (crossPoint > actualParameters[3].Value) {
                        dec.IsBuy = false;
                        dec.IsSell = true;
                    }
                }
            }
            
            return dec;
        }
		
    }
}
