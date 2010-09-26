/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/2/2009
 * Time: 9:47 PM
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

namespace TradingCommon.Traders.Optimization
{
    public delegate void SolutionEvaluatedHandler(ISolution solution);
    public delegate void ProcessCompletedHandler();
    
    public abstract class TSOptimizer
    {
        public event SolutionEvaluatedHandler SolutionEvaluatedEvent;
        public event ProcessCompletedHandler ProcessCompleted;
        
        SolutionBuffer solutionBuffer = new SolutionBuffer();
        public SolutionBuffer SolutionBuffer { get { return solutionBuffer; } }
        
        public abstract int EstimateWork();

        public abstract void RunOptimization();
        
        protected void SolutionEvaluated(ISolution solution)
        {
            if (SolutionEvaluatedEvent != null) {
                SolutionEvaluatedEvent(solution);
            }
        }        

        protected void EndOptimization()
        {
            if (ProcessCompleted != null) {
                ProcessCompleted();
            }
        }
        
        
    }
}
