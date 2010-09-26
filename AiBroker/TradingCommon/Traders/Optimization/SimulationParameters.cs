/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/22/2009
 * Time: 3:05 PM
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

using TradingCommon.Storage;

namespace TradingCommon.Traders.Optimization
{
    public class SimulationParameters : SimulatorSpec
    {
        TimeSeries series;
        public TimeSeries Series { get { return series; } }
                        
        public SimulationParameters(Title title, int period, TradingSystem tSys)
            : base(title, period, tSys)
        {
            series = DataUtil.ReadTimeSeries(title.FullSymbol, period);
            FirstIndex = 0;
            LastIndex = series.Count - 1;
        }
    }
}
