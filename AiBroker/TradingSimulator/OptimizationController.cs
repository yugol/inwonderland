/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/29/2009
 * Time: 9:00 AM
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
using System.Threading;

using TradingCommon.Traders.Optimization;
using TradingCommon.Traders.Optimization.Tune;
using TradingSimulator.Ui;

namespace TradingSimulator
{
    public class OptimizationController
    {
        MainForm view;
        TSOptimizer optimizer;
        Thread optimizingThread;
        SolutionEvaluatedHandler viewSolutionEvaluated;
        ProcessCompletedHandler viewEndOptimization;
        
        public OptimizationController(MainForm view, TSOptimizer optimizer)
        {
            this.view = view;
            this.optimizer = optimizer;
            viewSolutionEvaluated = new SolutionEvaluatedHandler(view.SolutionEvaluated);
            viewEndOptimization = new ProcessCompletedHandler(view.EndOptimization);
            optimizingThread = new Thread(this.optimizer.RunOptimization);
            optimizer.SolutionEvaluatedEvent += new SolutionEvaluatedHandler(SolutionEvaluated);
            optimizer.ProcessCompleted += new ProcessCompletedHandler(EndOptimization);
        }
        
        public void BeginOptimization()
        {
            optimizingThread.Start();
        }
        
        public void CancelOptimization()
        {
            optimizingThread.Abort();
            EndOptimization();
        }
        
        void SolutionEvaluated(ISolution solution)
        {
            if (view.InvokeRequired) {
                view.Invoke(viewSolutionEvaluated, new object[] {solution});
            } else {
                view.SolutionEvaluated(solution);
            }
        }
        
        void EndOptimization()
        {
            if (view.InvokeRequired) {
                view.Invoke(viewEndOptimization);
            } else {
                view.EndOptimization();
            }
        }
        
        public TSOptimizer Optimizer
        {
            get { return optimizer; }
        }
        
    }
}
