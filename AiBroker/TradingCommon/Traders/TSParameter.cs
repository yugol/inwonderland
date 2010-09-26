/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 12:16 PM
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
using System.Collections;
using TradingCommon.Util;

namespace TradingCommon.Traders
{
    public abstract class TSParameter
    {
        protected const int MAX_BIT_LENGTH = 32;
        
        private string name;
        private double minValue;
        private double maxValue;
        protected double val;

        public string Name { get { return name; } }
        
        public double MaxValue 
        { 
            get { return maxValue; } 
            set 
            {
                if (value < minValue) {
                    throw new ArgumentException("Parameter MAX value cannot be smaller than min value");    
                }
                maxValue = value;
                if (maxValue < val) {
                    val = maxValue;    
                }
            }
        }
        
        public double MinValue 
        { 
            get { return minValue; }
            set
            {
                if (value > maxValue) {
                    throw new ArgumentException("Parameter MIN value cannot be greater than max value");    
                }
                minValue = value;
                if (minValue > val) {
                    val = minValue;    
                }
            }
        }
        
        public virtual double Value
        {
            get { return val; }
            set
            {
                if ((value < minValue) || (value > maxValue)) {
                    throw new ArgumentException("Parameter value out of interval");
                }
                this.val = value;
            }
        }
        
        protected int bitLength = 0;
        public virtual int BitLength 
        { 
            get { return bitLength; }
            set 
            {
                if (value < 1) {
                    throw new ArgumentException("BitLength value is too small");
                }
            }
        }
        
        public virtual int MaxBitLength { get { return MAX_BIT_LENGTH; } }
        
        public BitArray BitValue
        {
            get
            {
                LinearMapper mapper = new LinearMapper(MinValue, MaxValue, 0, (1 << BitLength) - 1);
                uint bitVal = (uint)mapper.MapToInt(Value);
                BitArray rep = new BitArray(BitLength);
                for (int i = 0; i < BitLength; ++i) {
                    rep.Set(i, (bitVal % 2) == 1);
                    bitVal >>= 1;
                }
                return rep;
            }
            
            set
            {
                uint bitVal = 0;
                for (int i = BitLength - 1; i >= 0; --i) {
                    bitVal <<= 1;
                    bitVal += (uint)(value.Get(i) ? 1 : 0);
                }
                LinearMapper mapper = new LinearMapper(0, (1 << BitLength) - 1, MinValue, MaxValue);
                Value = mapper.Map(bitVal);                
            }
        }

        protected TSParameter(string name, double defaultValue, double minValue, double maxValue)
        {
            this.name = name;
            this.minValue = minValue;
            this.maxValue = maxValue;
            Value = defaultValue;
        }
    }
}
