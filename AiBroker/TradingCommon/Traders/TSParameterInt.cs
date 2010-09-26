/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 12:21 PM
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

namespace TradingCommon.Traders
{
	public class TSParameterInt : TSParameter
	{
		public TSParameterInt(string name, double defaultValue, double minValue, double maxValue)
			: base(name, defaultValue, minValue, maxValue) 
		{ 
		    bitLength = _MaxBitLength();
		}
		
		public override double Value
		{
			get { return Math.Round(val); }
		}
		
        public override int BitLength
        {
            set 
            { 
                base.BitLength = value;
                int maxBitLength = _MaxBitLength();
                bitLength = (value < maxBitLength) ? (value) : (maxBitLength);
            }
        }
        
        public override int MaxBitLength
        {
            get { return _MaxBitLength(); }
        }
		
		private int _MaxBitLength()
		{
            double amplitude = MaxValue - MinValue + 1;
            int maxBitLength = (int) Math.Min(Math.Ceiling(Math.Log(amplitude, 2)), MAX_BIT_LENGTH);
            return maxBitLength;
		}
	}
}
