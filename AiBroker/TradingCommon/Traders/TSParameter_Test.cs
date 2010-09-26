/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 3/18/2009
 * Time: 12:34 PM
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
#if TEST

using System;
using System.Collections;
using NUnit.Framework;

namespace TradingCommon.Traders
{
	[TestFixture]
	public class TSParameter_Test
	{
		[Test]
		public void ParameterIntTest()
		{
			TSParameter p = new TSParameterInt("a", -2.3, -10, 10);
			Assert.AreEqual(-2, p.Value);
			
			p.Value = 3.5;
			Assert.AreEqual(4, p.Value);
			
			Assert.AreEqual(5, p.BitLength);
		}
		
		[Test]
		public void ParameterRealTest()
		{
			TSParameter p = new TSParameterReal("a", -2.3, -10, 10);
			Assert.AreEqual(-2.3, p.Value);
			
			p.Value = 3.5;
			Assert.AreEqual(3.5, p.Value);
			
			Assert.AreEqual(TSParameterReal.DEFAULT_BIT_LENGTH, p.BitLength);
		}
		
		[Test]
		public void IntMapping_00()
		{
		    TSParameter p = new TSParameterInt("int", 0, 0, 3);
		    BitArray rep = p.BitValue;
		    Assert.AreEqual(2, rep.Count);
		    Assert.AreEqual(false, rep.Get(0));
		    Assert.AreEqual(false, rep.Get(1));		    
		}
		
		[Test]
		public void IntMapping_110()
		{
		    TSParameter p = new TSParameterInt("int", 6, 0, 7);
		    BitArray rep = p.BitValue;
		    Assert.AreEqual(3, rep.Count);
		    Assert.AreEqual(false, rep.Get(0));
		    Assert.AreEqual(true, rep.Get(1));		    
		    Assert.AreEqual(true, rep.Get(2));		    
		}

		[Test]
		public void IntMapping()
		{
		    TSParameter p = new TSParameterInt("int", 6, -100, 113);
		    for (int v = -100; v <= 113; ++v) {
		        p.Value = v;
		        p.BitValue = p.BitValue;
		        Assert.AreEqual(v, p.Value);
		    }
		}
	
		[Test]
		public void RealMapping()
		{
		    TSParameter p = new TSParameterReal("r", 0, -10, 13);
		    p.BitLength = 8;
		    for (double v = -10; v <= 13; v += 0.1) {
		        p.Value = v;
		        p.BitValue = p.BitValue;
		        Assert.AreEqual(v, p.Value, 0.05);
		    }
		}
	
		[Test]
		[ExpectedException( typeof( ArgumentException ) )]
		public void SetBitLength()
		{
		    TSParameter p = new TSParameterReal("r", 0, -10, 13);
		    p.BitLength = 0;
		}
	
	}
}
#endif
