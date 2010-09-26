/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/11/2009
 * Time: 12:39 AM
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
    public class CciReversal : CciLambert
    {
        static List<TSParameter> defaultParameters = new List<TSParameter>(); 
        public override List<TSParameter> DefaultParameters { get { return defaultParameters; } }

        static CciReversal()
        {
            // (Murphy 270[238])
            defaultParameters.Add(new TSParameterInt("Cci_period", 20, 2, 200));
            defaultParameters.Add(new TSParameterInt("Cci_overbought", 100, 50, 200));
            defaultParameters.Add(new TSParameterInt("Cci_oversold", -100, -200, -50));
        }
        
        public override Decision Decide(int barIndex, TSStatus status)
        {
            Decision dec = new Decision();
            
            if (cci[barIndex] > actualParameters[1].Value) {
                dec.IsBuy = false;    
                dec.IsSell = true;
            } else if (cci[barIndex] < actualParameters[2].Value) {
                dec.IsBuy = true;
                dec.IsSell = false;
            }
            
            return dec;
        }
    }
}
