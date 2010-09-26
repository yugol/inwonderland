/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 1:23 AM
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
using System.ComponentModel;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

using TradingCommon;
using TradingCommon.Traders;
using TradingCommon.Traders.Optimization;
using TradingCommon.Traders.Optimization.Tune;

namespace TradingSimulator.Ui
{
    public delegate void TradingSystemTestHandler(TradingSystem ts, TuneParameters optParams);
    public delegate void TradingSystemOptimizationHandler(TSOptimizer optimizer);
    
    public partial class TSTunerControl : UserControl
    {
        public event TradingSystemTestHandler TradingSystemTest;
        public event TradingSystemOptimizationHandler TradingSystemOptimization;
        
        TradingSystem tSys;
        
        public TradingSystem TSys
        {
            set 
            { 
                if (value != null) {
                    tSys = value;
                    ShowTSysParameters();
                    ShowSearchSpace();
                    ShowRawView();
                    ShowOptParameters();
                }
            }
        }
        
        public TSTunerControl()
        {
            InitializeComponent();
        }
        
        void TSTunerLoad(object sender, EventArgs e)
        {
        	TSys = new TradingSystem();
        }

        void ParamPanelSizeChanged(object sender, EventArgs e)
        {
            ResizeParameterControls();	
        }
        
        void SearchSpacePanelSizeChanged(object sender, EventArgs e)
        {
        	ResizeSearchSpaceControls();
        }

        void NewButtonClick(object sender, EventArgs e)
        {
            TSys = new TradingSystem();
        }
        
        void LoadButtonClick(object sender, EventArgs e)
        {
            OpenFileDialog ofd = new OpenFileDialog();
            ofd.Filter = "Trading system files (*.tsys)|*.tsys|All files (*.*)|*.*";
            ofd.InitialDirectory = Configuration.TSYS_FOLDER;
            ofd.Multiselect = false;
            if (ofd.ShowDialog() == DialogResult.OK) {
                TSys = TSParser.ParseTS(ofd.FileName);
            }
        }
        
        void SaveButtonClick(object sender, EventArgs e)
        {
            SaveFileDialog sfd = new SaveFileDialog();
            sfd.InitialDirectory = Configuration.TSYS_FOLDER;
            sfd.DefaultExt = "tsys";
            if (sfd.ShowDialog() == DialogResult.OK) {
                File.WriteAllText(sfd.FileName, tSys.ToXmlString());
            }
        }
        
        void TsTabsSelecting(object sender, TabControlCancelEventArgs e)
        {
            if (e.TabPage == parametersPage) {
                ReadTSysParams();
                ShowTSysParameters();
            } else if (e.TabPage == searchPagePage) {
                ShowSearchSpace();
            } else if (e.TabPage == rawViewPage) {
                ReadTSysParams();
                ShowRawView();
            } else if (e.TabPage == optimizePage) {
                ReadTSysParams();
            }
        }

    }
}
