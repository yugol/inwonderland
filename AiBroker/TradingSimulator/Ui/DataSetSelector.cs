/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/26/2009
 * Time: 9:19 PM
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

using TradingCommon;
using TradingCommon.Storage;

namespace TradingSimulator.Ui
{
    public delegate void DataSetChangedHandler(DataSet dataSet);
    public delegate void DisplayDataSetHandler(DataSet dataSet);
    public delegate void CopyDataSetHandler(DataSet dataSet);
    
    public partial class DataSetSelector : UserControl
    {
        public event DataSetChangedHandler DataSetChanged;
        public event DisplayDataSetHandler DisplayDataSet;
        public event CopyDataSetHandler CopyDataSet;
        
        bool updating = false;
        
        Title title = null;
        
        public string DataName
        {
            get { return groupBox.Text; }
            set { groupBox.Text = value; }
        }

        DataSet dataSet = null;
        public DataSet DataSet
        {
            get { return dataSet; }
        }
        
        public void CopyDataSetFrom(DataSet copy)
        {
            title = copy.Title;
            UpdateData(copy);
        }
        
        public DataSetSelector()
        {
            InitializeComponent();
            SimulatorData.Instance.PeriodChanged += new PeriodChangedHandler(ChangePeriod);
        }
        
        public void ChangePeriod(int newPeriod)
        {
            UpdateData(null);
        }
        
        void LoadButtonClick(object sender, EventArgs e)
        {
            TitleSelectionForm selector = new TitleSelectionForm();
            if (selector.ShowDialog() == DialogResult.OK) {
                title = selector.Title;
                UpdateData(null);
            }
        }
        
        void UpdateData(DataSet copy)
        {
            updating = true;
            
            if (title != null) {
                dataSet = new DataSet(title, SimulatorData.Instance.Period);
                
                if (copy != null) {
                    dataSet.From = copy.From;
                    dataSet.To = copy.To;
                }
                
                displayButton.Enabled = true;
                copyButton.Enabled = true;
                fromDtp.Enabled = true;
                toDtp.Enabled = true;
                
                titleLabel.Text = dataSet.Title.FullSymbol;
                dataEntriesLabel.Text = dataSet.TimeSeries.Count.ToString();
                usedEntriesLabel.Text = dataSet.UsedEntries.ToString();
                
                fromDtp.MinDate = dataSet.TimeSeries.FirstDate;
                fromDtp.MaxDate = dataSet.TimeSeries.LastDate;
                toDtp.MinDate = dataSet.TimeSeries.FirstDate;
                toDtp.MaxDate = dataSet.TimeSeries.LastDate;
                
                fromDtp.Value = dataSet.From;
                toDtp.Value = dataSet.To;
            }
            
            updating = false;
            
            if (DataSetChanged != null) {
                DataSetChanged(dataSet);
            }
        }
        
        void FromDtpValueChanged(object sender, EventArgs e)
        {
            if (!updating) {
                dataSet.From = fromDtp.Value;
                usedEntriesLabel.Text = dataSet.UsedEntries.ToString();
                if (DataSetChanged != null) {
                    DataSetChanged(dataSet);
                }
            }
        }
        
        void ToDtpValueChanged(object sender, EventArgs e)
        {
            if (!updating) {
                dataSet.To = toDtp.Value;
                usedEntriesLabel.Text = dataSet.UsedEntries.ToString();
                if (DataSetChanged != null) {
                    DataSetChanged(dataSet);
                }
            }
        }
        
        void DisplayButtonClick(object sender, EventArgs e)
        {
            if (DisplayDataSet != null) {
                DisplayDataSet(dataSet);
            }
        }
        
        void CopyButtonClick(object sender, EventArgs e)
        {
            if (CopyDataSet != null) {
                CopyDataSet(dataSet);
            }
        }
    }
}
