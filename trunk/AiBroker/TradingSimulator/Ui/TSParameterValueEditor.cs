/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 2:12 AM
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
using TradingCommon.Util;

namespace TradingSimulator.Ui
{
    public partial class TSParameterValueEditor : UserControl
    {
        bool processEvents = true;
        LinearMapper mapper;
                
        TSParameter parameter;
        public TSParameter Parameter
        {
            set 
            {
                parameter = value;
                mapper = new LinearMapper(parameter.MinValue, parameter.MaxValue, trackBar.Minimum, trackBar.Maximum);
                Data2View();
            }
        }
        
        public TSParameterValueEditor()
        {
            InitializeComponent();
        }
        
        public void Data2View()
        {
            processEvents = false;
            
            nameLabel.Text = parameter.Name;
            minLabel.Text = parameter.MinValue.ToString();
            maxLabel.Text = parameter.MaxValue.ToString();
            textBox.Text = parameter.Value.ToString();
            trackBar.Value = mapper.MapToInt(parameter.Value);
            
            processEvents = true;
        }
        
        void TextBoxTextChanged(object sender, EventArgs e)
        {
            if (processEvents) {
                try {
                    parameter.Value = double.Parse(textBox.Text);
                    Data2View();
                } catch (Exception) {                
                }
            }
        }
        
        void TrackBarScroll(object sender, EventArgs e)
        {
            if (processEvents) {
                parameter.Value = mapper.Unmap(trackBar.Value);
                Data2View();
            }
        }
    }
}
