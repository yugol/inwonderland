/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 8:09 AM
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

using TradingCommon;

namespace TradingSimulator.Ui
{
    partial class MainForm
    {
        private void slippageAnalyserToolStripMenuItem_Click(object sender, EventArgs e)
        {
//            SlippageAnalyzer slippageAnalyzer = new SlippageAnalyzer();
//            slippageAnalyzer.Title = SimulatorData.Instance.Title;
//            slippageAnalyzer.ShowDialog();
        }

        private void searchSpaceExplorerToolStripMenuItem_Click(object sender, EventArgs e)
        {
//            try { SetSimulatorParametersFromUI(); }
//            catch (FormatException) { return; }
//
//            int xParamIndex = 1;
//            int yParamIndex = 0;
//
//            SimulatorController.Instance.Simulator.UpdateTicks(SimulatorData.Instance.Ticks);
//            SearchSpaceExplorer ssExplorer = new SearchSpaceExplorer(
//                                                 SimulatorController.Instance.Simulator,
//                                                 xParamIndex, yParamIndex,
//                                                 optimizeDomains.SearchSpace,
//                                                 SimulatorData.Instance.From, SimulatorData.Instance.To);
//            if (ssExplorer.ShowDialog() == DialogResult.OK)
//            {
//                SimulatorController.Instance.Simulator.Parameters[xParamIndex].Value = ssExplorer.SelXValue;
//                SimulatorController.Instance.Simulator.Parameters[yParamIndex].Value = ssExplorer.SelYValue;
//                instanceParameters.Parameters = SimulatorController.Instance.Simulator.Parameters;
//                optimizeDomains.SetDomainData(xParamIndex, ssExplorer.XMin, ssExplorer.XMax, ssExplorer.XDivs);
//                optimizeDomains.SetDomainData(yParamIndex, ssExplorer.YMin, ssExplorer.YMax, ssExplorer.YDivs);
//            }
        }
        
        
        void tradeLogAnalyzerToolStripMenuItem_Click(object sender, EventArgs e)
        {
//            OpenFileDialog ofd = new OpenFileDialog();
//            ofd.InitialDirectory = Configuration.ORDERS_FOLDER;
//            if (ofd.ShowDialog(this) == DialogResult.OK)
//            {
//                Solution sol = new Solution(ofd.FileName);
//                SolutionReportForm solReport = new SolutionReportForm();
//                solReport.Solution = sol;
//                solReport.Show();
//            }
        }
        
        void JoinMaturitiesToolStripMenuItemClick(object sender, EventArgs e)
        {
            SymbolSelector symbolSelector = new SymbolSelector();
            if (symbolSelector.ShowDialog() == DialogResult.OK)
            {
                if (symbolSelector.Symbol != null)
                {
                    MaturityJoiner joiner = new MaturityJoiner(symbolSelector.Symbol);
                    joiner.Join();                       
                    MessageBox.Show(symbolSelector.Symbol + " was joined under " + joiner.GetJoinSymbol());
                }
            }
        }

        
    }
}
