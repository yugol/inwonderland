/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/11/2009
 * Time: 3:39 PM
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

namespace TradingCommon.Traders.Optimization
{
    public class SolutionBuffer
    {
        private IList<ISolution> items = new List<ISolution>();
        public IList<ISolution> Items { get { return items; } }

        private int maxSize = 10;

        public int MaxSize
        {
            get { return maxSize; }
            set { maxSize = value; Trim(); }
        }

        public void Add(ISolution solution)
        {
            for (int i = 0; i < items.Count; i++)
            {
                if (solution.Fitness > items[i].Fitness)
                {
                    items.Insert(i, solution);
                    Trim();
                    return;
                }
            }
            items.Add(solution);
            Trim();
        }

        private void Trim()
        {
            while (items.Count > maxSize) items.RemoveAt(items.Count - 1);
        }

        public void Clear()
        {
            items.Clear();
        }
    }
}
