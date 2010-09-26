/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 5:56 AM
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
using System.Windows.Forms;
using TradingCommon.Traders;
using TradingCommon.Traders.Optimization;
using TradingCommon.Traders.Optimization.Tune;

namespace TradingSimulator.Ui
{
    partial class MainForm
    {
        OptimizationController controller;
        
        void TestTradingSystem(TradingSystem tSys, TuneParameters tuneParams)
        {
            TSTraderSimulation simulation = new TSTraderSimulation(tuneParams);
            Solution solution = TSTuner.CreateSolution(simulation, tuneParams.FitnessParam);
            SimulatorData.Instance.AddSolution(solution);
        }

        void StartOptimization(TSOptimizer optimizer)
        {
            DisableOptimizationEvents();

            statusProgressBar.Value = 0;
            statusProgressBar.Maximum = optimizer.EstimateWork();
            
            optimizer.SolutionBuffer.MaxSize = SimulatorData.Instance.Solutions.MaxSize;
            controller = new OptimizationController(this, optimizer);
            controller.BeginOptimization();

            SetProgressControlsVisible(true);
        }
        
        public void SolutionEvaluated(ISolution solution)
        {
            statusProgressBar.Value += 1;
        }

        public void EndOptimization()
        {
            SetProgressControlsVisible(false);
            SimulatorData.Instance.Solutions = controller.Optimizer.SolutionBuffer;
            controller = null;
            EnableOptimizationEvents();
        }
        
        void CancelLabelClick(object sender, EventArgs e)
        {
            controller.CancelOptimization();
        }
        
        void EnableOptimizationEvents()
        {
            tsTuner.TradingSystemOptimization += optimizationHandler;
            tsSeeker.TradingSystemOptimization += optimizationHandler;
        }

        void DisableOptimizationEvents()
        {
            tsTuner.TradingSystemOptimization -= optimizationHandler;
            tsSeeker.TradingSystemOptimization -= optimizationHandler;
        }
        
        void SetProgressControlsVisible(bool val)
        {
            progressLabel.Visible = val;
            statusProgressBar.Visible = val;
            cancelLabel.Visible = val;
        }
    
    }
}
