/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/10/2009
 * Time: 12:00 AM
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
using System.Text;

namespace TradingCommon.Traders.Optimization.Tune
{
    public struct ParameterGene
    {
        static  Random generator = new Random();
        
        string name;
        BitArray rep;
        
        public ParameterGene(string name, BitArray rep)
        {
            this.name = name;
            this.rep = rep;
        }
        
        public string Name
        {
            get { return name; }
        }
        
        public BitArray Rep
        {
            get { return rep; }
        }
        
        public void Randomize()
        {
            ReadValueFrom(generator.Next());
        }
        
        public void ReadValueFrom(int bitmap)
        {
            for (int i = 0; i < rep.Count; ++i) 
            {
                rep.Set(i, (bitmap % 2) == 1);
                bitmap >>= 1;
            }
        }
        
        public string ToBitString()
        {
            StringBuilder sb = new StringBuilder();
            foreach (bool bit in rep) {
                sb.Append(bit ? "1" : "0");
            }
            return sb.ToString();
        }
        
    }
}
