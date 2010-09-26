/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 12:21 AM
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

using ZedGraph;

using TradingCommon;

namespace TradingSimulator.Ui
{
    partial class MainForm
    {
        void PeriodSelectedIndexChanged(object sender, EventArgs e)
        {
            int selectedPeriod = TimeSeries.DAY_PERIOD;
            try {
                string item = periodCombo.SelectedItem.ToString().Split(' ')[0];
                selectedPeriod = int.Parse(item);
            } catch (Exception) {
            }
            SimulatorData.Instance.Period = selectedPeriod;
        }

        void TestDataSelectorCopyDataSet(DataSet dataSet)
        {
            trainDataSelector.CopyDataSetFrom(dataSet);
        }
        
        void TestDataSelectorDataSetChanged(DataSet dataSet)
        {
            SimulatorData.Instance.TestData = dataSet;	
        }
        
        void TestDataSelectorDisplayDataSet(DataSet dataSet)
        {
            DisplayDataSet(dataSet);
        }
        
        void TrainDataSelectorCopyDataSet(DataSet dataSet)
        {
            testDataSelector.CopyDataSetFrom(dataSet);
        }
        
        void TrainDataSelectorDataSetChanged(DataSet dataSet)
        {
            SimulatorData.Instance.TrainData = dataSet;	
        }
        
        void TrainDataSelectorDisplayDataSet(DataSet dataSet)
        {
        	DisplayDataSet(dataSet);
        }
        
        void DisplayDataSet(DataSet dataSet)
        {
            GraphPane pane = sampleDataGraph.GraphPane;
            pane.CurveList.Clear();

            pane.Title.Text = dataSet.Title.FullSymbol + " - Close prices";
            pane.XAxis.Title.Text = "Date/Time";
            pane.YAxis.Title.Text = "Price";
            pane.XAxis.Type = AxisType.Date;

            pane.XAxis.MajorGrid.IsVisible = true;
            pane.YAxis.MajorGrid.IsVisible = true;
            pane.XAxis.MajorGrid.Color = Color.Gray;
            pane.YAxis.MajorGrid.Color = Color.Gray;

            PointPairList data = new PointPairList();
            for (int i = dataSet.FirstIndex; i <= dataSet.LastIndex; ++i) {
                data.Add(dataSet.TimeSeries.DateTimes[i].ToOADate(), dataSet.TimeSeries.Closes[i]);
            }
            LineItem sarCurve = pane.AddCurve("", data, Color.Blue, SymbolType.None);

            sampleDataGraph.RestoreScale(pane);
            sampleDataGraph.Invalidate();

            sampleDataGraph.Visible = true;
        }
    }
}
