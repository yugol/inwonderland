/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/26/2009
 * Time: 11:00 PM
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

using TradingCommon;
using TradingCommon.Storage;

namespace TradingSimulator
{
    public class DataSet : BoundedDataSpec
    {
        TimeSeries timeSeries;
                
        public TimeSeries TimeSeries
        {
            get { return timeSeries; }
        }
     
        DateTime from;
        DateTime to;

        public DateTime From
        {
            get { return from; }
            set { from = value; }
        }
        
        public DateTime To
        {
            get { return to; }
            set { to = value; }
        }
        
        new public int FirstIndex
        {
            get { return timeSeries.GetBarIndex(from); }
        }
        
        new public int LastIndex
        {
            get { return timeSeries.GetBarIndex(to); }
        }

        public DataSet(Title title, int period)
            : base(title, period)
        {
            timeSeries = DataUtil.ReadTimeSeries(title.FullSymbol, period);
            from = timeSeries.FirstDate;
            to = timeSeries.LastDate;
        }
        
        public int UsedEntries
        {
            get { return LastIndex - FirstIndex + 1; }
        }
    }
}
