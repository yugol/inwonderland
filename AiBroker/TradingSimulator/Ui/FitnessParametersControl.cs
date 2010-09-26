/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/3/2009
 * Time: 4:07 PM
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

using TradingCommon.Traders.Optimization;

namespace TradingSimulator.Ui
{
    public partial class FitnessParametersControl : UserControl
    {
        public FitnessParameters Data
        {
            get
            {
                FitnessParameters param = new FitnessParameters();
                param.MinTradesPerDay = (double) minTpdNud.Value;
                param.MaxTradesPerDay = (double) maxTpdNud.Value;
                param.TargetFitness = double.Parse(targetFitnessBox.Text);
                return param;
            }
            
            set
            {
                FitnessParameters param = value;
                if (param == null) {
                    param = new FitnessParameters();    
                }
                minTpdNud.Value = (decimal) param.MinTradesPerDay;
                maxTpdNud.Value = (decimal) param.MaxTradesPerDay;
                targetFitnessBox.Text = param.TargetFitness.ToString();
            }
        }
        
        public FitnessParametersControl()
        {
            InitializeComponent();          
            Data = null;
        }
    }
}
