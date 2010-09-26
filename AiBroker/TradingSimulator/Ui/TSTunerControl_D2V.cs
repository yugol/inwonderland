/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 9:57 AM
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
using System.Drawing;
using System.Windows.Forms;

using TradingCommon.Traders.Optimization;
using TradingCommon.Traders.Optimization.Tune;

namespace TradingSimulator.Ui
{
    partial class TSTunerControl
    {

        void ShowTSysParameters()
        {
            parametersPanel.Visible = false;
            parametersPanel.Controls.Clear();
            int yPos = 0;
            foreach (string id in tSys.Parameters.Keys) {
                TSParameterValueEditor ctl = new TSParameterValueEditor();
                ctl.Parameter = tSys.Parameters[id];
                ctl.Location = new Point(0, yPos);
                yPos += ctl.Height;
                parametersPanel.Controls.Add(ctl);
            }
            if (parametersPanel.Controls.Count == 0) {
                // parametersPanel.Controls.Add(CreateMessageLabel());
            } else {
                ResizeParameterControls();
            }
            parametersPanel.Visible = true;
        }

        Label CreateMessageLabel()
        {
            Label message = new Label();
            message.Text = "None.";
            message.AutoSize = true;
            message.Location = new Point(10, 10);
            return message;
        }
        
        void ResizeParameterControls()
        {
            int width = parametersPanel.Width - 20;
            foreach (Control ctl in parametersPanel.Controls) {
                ctl.Width = width;
            }
        }
        
        void ShowSearchSpace()
        {
            searchSpacePanel.Visible = false;
            searchSpacePanel.Controls.Clear();
            int yPos = 0;
            foreach (string id in tSys.Parameters.Keys) {
                TSParameterDomainEditor ctl = new TSParameterDomainEditor();
                ctl.Parameter = tSys.Parameters[id];
                ctl.Location = new Point(0, yPos);
                yPos += ctl.Height;
                searchSpacePanel.Controls.Add(ctl);
            }
            if (searchSpacePanel.Controls.Count == 0) {
                 // searchSpacePanel.Controls.Add(CreateMessageLabel());
            } else {
                ResizeSearchSpaceControls();
            }
            searchSpacePanel.Visible = true;
        }
        
        void ResizeSearchSpaceControls()
        {
            int width = searchSpacePanel.Width - 20;
            foreach (Control ctl in searchSpacePanel.Controls) {
                ctl.Width = width;
            }
        }
        
        void ShowRawView() {
            rawTextBox.Text = tSys.ToXmlString();
        }

        void ShowOptParameters()
        {
            fitnessParametersControl.Data = null;
            gaParametersControl.Data = null;
        }
        
    }
}
