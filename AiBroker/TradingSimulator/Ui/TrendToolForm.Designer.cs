/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 8/30/2009
 * Time: 9:32 PM
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
namespace TradingSimulator.Ui
{
    partial class TrendToolForm
    {
        /// <summary>
        /// Designer variable used to keep track of non-visual components.
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        
        /// <summary>
        /// Disposes resources used by the form.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing) {
                if (components != null) {
                    components.Dispose();
                }
            }
            base.Dispose(disposing);
        }
        
        /// <summary>
        /// This method is required for Windows Forms designer support.
        /// Do not change the method contents inside the source code editor. The Forms designer might
        /// not be able to load this method if it was changed manually.
        /// </summary>
        private void InitializeComponent()
        {
        	this.controlPanel = new System.Windows.Forms.Panel();
        	this.classifyTrendsButton = new System.Windows.Forms.Button();
        	this.findClustersButton = new System.Windows.Forms.Button();
        	this.label1 = new System.Windows.Forms.Label();
        	this.historyNud = new System.Windows.Forms.NumericUpDown();
        	this.outputControl = new TradingSimulator.Ui.FileContentControl();
        	this.controlPanel.SuspendLayout();
        	((System.ComponentModel.ISupportInitialize)(this.historyNud)).BeginInit();
        	this.SuspendLayout();
        	// 
        	// controlPanel
        	// 
        	this.controlPanel.Controls.Add(this.classifyTrendsButton);
        	this.controlPanel.Controls.Add(this.findClustersButton);
        	this.controlPanel.Controls.Add(this.label1);
        	this.controlPanel.Controls.Add(this.historyNud);
        	this.controlPanel.Dock = System.Windows.Forms.DockStyle.Top;
        	this.controlPanel.Location = new System.Drawing.Point(0, 0);
        	this.controlPanel.Name = "controlPanel";
        	this.controlPanel.Size = new System.Drawing.Size(792, 40);
        	this.controlPanel.TabIndex = 0;
        	// 
        	// classifyTrendsButton
        	// 
        	this.classifyTrendsButton.Location = new System.Drawing.Point(304, 8);
        	this.classifyTrendsButton.Name = "classifyTrendsButton";
        	this.classifyTrendsButton.Size = new System.Drawing.Size(96, 23);
        	this.classifyTrendsButton.TabIndex = 3;
        	this.classifyTrendsButton.Text = "Classify Trends";
        	this.classifyTrendsButton.UseVisualStyleBackColor = true;
        	this.classifyTrendsButton.Click += new System.EventHandler(this.ClassifyTrendsButtonClick);
        	// 
        	// findClustersButton
        	// 
        	this.findClustersButton.Location = new System.Drawing.Point(176, 8);
        	this.findClustersButton.Name = "findClustersButton";
        	this.findClustersButton.Size = new System.Drawing.Size(96, 23);
        	this.findClustersButton.TabIndex = 2;
        	this.findClustersButton.Text = "Find Clusters";
        	this.findClustersButton.UseVisualStyleBackColor = true;
        	this.findClustersButton.Click += new System.EventHandler(this.FindClustersButtonClick);
        	// 
        	// label1
        	// 
        	this.label1.AutoSize = true;
        	this.label1.Location = new System.Drawing.Point(8, 8);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(74, 13);
        	this.label1.TabIndex = 1;
        	this.label1.Text = "History length:";
        	// 
        	// historyNud
        	// 
        	this.historyNud.Location = new System.Drawing.Point(88, 8);
        	this.historyNud.Maximum = new decimal(new int[] {
        	        	        	10,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.historyNud.Minimum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.historyNud.Name = "historyNud";
        	this.historyNud.Size = new System.Drawing.Size(56, 20);
        	this.historyNud.TabIndex = 0;
        	this.historyNud.Value = new decimal(new int[] {
        	        	        	5,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.historyNud.ValueChanged += new System.EventHandler(this.HistoryNudValueChanged);
        	// 
        	// outputControl
        	// 
        	this.outputControl.Content = "";
        	this.outputControl.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.outputControl.FileName = "";
        	this.outputControl.Location = new System.Drawing.Point(0, 40);
        	this.outputControl.Name = "outputControl";
        	this.outputControl.Size = new System.Drawing.Size(792, 536);
        	this.outputControl.TabIndex = 1;
        	// 
        	// TrendToolForm
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(792, 576);
        	this.Controls.Add(this.outputControl);
        	this.Controls.Add(this.controlPanel);
        	this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.SizableToolWindow;
        	this.Name = "TrendToolForm";
        	this.ShowInTaskbar = false;
        	this.Text = "RelativeHistoryForm";
        	this.controlPanel.ResumeLayout(false);
        	this.controlPanel.PerformLayout();
        	((System.ComponentModel.ISupportInitialize)(this.historyNud)).EndInit();
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.Button findClustersButton;
        private System.Windows.Forms.Button classifyTrendsButton;
        private System.Windows.Forms.NumericUpDown historyNud;
        private System.Windows.Forms.Label label1;
        private TradingSimulator.Ui.FileContentControl outputControl;
        private System.Windows.Forms.Panel controlPanel;
    }
}
