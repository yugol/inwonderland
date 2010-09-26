/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/29/2009
 * Time: 7:01 AM
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

using TradingCommon.Traders.Optimization;
using TradingCommon.Traders.Optimization.Tune;

namespace TradingSimulator.Ui
{
    partial class TSTunerControl
    {
        void TestButtonClick(object sender, EventArgs e)
        {
            if (MainForm.CheckTestData() && CheckTSsy()) {
                if (TradingSystemTest != null) {
                    ReadTSysParams();
                    TradingSystemTest(tSys, ReadOptParams(SimulatorData.Instance.TestData));
                }
            }
        }
        
        void BfButtonClick(object sender, EventArgs e)
        {
            if (MainForm.CheckTrainData() && CheckTSysParameters()) {
                TuneParameters optParam = ReadOptParams(SimulatorData.Instance.TrainData);
                TSTuner optimizer = new TSTunerBruteForce(optParam);
                optimizer.SolutionBuffer.MaxSize = SimulatorData.Instance.Solutions.MaxSize;
                if (TradingSystemOptimization != null) {
                    TradingSystemOptimization(optimizer);
                }
            }
        }
        
        void GaButtonClick(object sender, EventArgs e)
        {
            if (MainForm.CheckTrainData() && CheckTSysParameters()) {
                TuneParameters optParam = ReadOptParams(SimulatorData.Instance.TrainData);
                TSTuner optimizer = new TSTunerGA(optParam);
                optimizer.SolutionBuffer.MaxSize = SimulatorData.Instance.Solutions.MaxSize;
                if (TradingSystemOptimization != null) {
                    TradingSystemOptimization(optimizer);
                }
            }
        }
        
        bool CheckTSsy()
        {
            if (tSys.RuleTree == null) {
                MainForm.ShowMessage("There is nothing to optimize.\nPlease specify a TRADING SYSTEM with at least one RULE.");
                return false;
            }
            return true;
        }

        bool CheckTSysParameters()
        {
            if (tSys.Parameters.Count <= 0) {
                MainForm.ShowMessage("There is nothing to optimize.\nPlease specify a TRADING SYSTEM with at least one PARAMETER.");
                return false;
            }
            return true;
        }
                
    }
}
