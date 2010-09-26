/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/3/2009
 * Time: 4:40 PM
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
using TradingCommon.Traders.Optimization.Search;

namespace TradingSimulator.Ui
{
    public partial class TSSeekerControl : UserControl
    {
        public event TradingSystemOptimizationHandler TradingSystemOptimization;
        
        public TSSeekerControl()
        {
            InitializeComponent();
            Data2View();
        }
        
        void Data2View()
        {
            SearchParameters param = new SearchParameters(null, null, 0);
            
            genotypeControl.Data = param.GenotypeParam;
            outerFitnessControl.Data = param.OuterFitnessParam;
            outerGAControl.Data = param.OuterGAParam;

            innerFitnessControl.Data = param.InnerFitnessParam;
            innerGAControl.Data = param.InnerGAParam;
            minFitnessBox.Text = param.MinFitness.ToString();
        }
        
        void RunButtonClick(object sender, EventArgs e)
        {
            if (MainForm.CheckTrainData() && MainForm.CheckTestData()) {
                DataSet train = SimulatorData.Instance.TrainData;
                DataSet test = SimulatorData.Instance.TestData;
                SearchParameters param = new SearchParameters(train.Title, test.Title, SimulatorData.Instance.Period);
                param.TrainData.FirstIndex = train.FirstIndex;
                param.TrainData.LastIndex = train.LastIndex;
                param.TestData.FirstIndex = train.FirstIndex;
                param.TestData.LastIndex = train.LastIndex;
                param.MinFitness = double.Parse(minFitnessBox.Text);
                param.GenotypeParam = genotypeControl.Data;
                param.OuterFitnessParam = outerFitnessControl.Data;
                param.OuterGAParam = outerGAControl.Data;
                param.InnerFitnessParam = innerFitnessControl.Data;
                param.InnerGAParam = innerGAControl.Data;
                
                TSOptimizer optimizer = new TSSeeker(param);
                if (TradingSystemOptimization != null) {
                    TradingSystemOptimization(optimizer);
                }
            }
        }
    }
}
