/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 4:33 AM
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
using System.Windows.Forms;

using TradingCommon.Traders;

namespace TradingSimulator.Ui
{
    public delegate void ParameterChangedHandler(TSParameter parameter);
    
    public partial class TSParameterDomainEditor : UserControl
    {
        TSParameter parameter;
        
        public TSParameter Parameter
        {
            get
            {
                View2Data();
                return parameter;
            }
            set
            {
                parameter = value;
                Data2View();            
            }
        }
        
        public TSParameterDomainEditor()
        {
            InitializeComponent();
        }
        
        void Data2View()
        {
            nameLabel.Text = parameter.Name;
            minBox.Text = parameter.MinValue.ToString();
            maxBox.Text = parameter.MaxValue.ToString();
            stepsCombo.SelectedIndex = parameter.BitLength - 1;
        }

        public void View2Data()
        {
            parameter.MinValue = double.Parse(minBox.Text);
            parameter.MaxValue = double.Parse(maxBox.Text);
            parameter.BitLength = stepsCombo.SelectedIndex + 1;
        }

    }
}
