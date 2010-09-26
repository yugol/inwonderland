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
namespace TradingSimulator.Ui
{
    partial class TSTunerControl
    {
        /// <summary>
        /// Designer variable used to keep track of non-visual components.
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        
        /// <summary>
        /// Disposes resources used by the control.
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
        	TradingCommon.Traders.Optimization.GAParameters gaParameters1 = new TradingCommon.Traders.Optimization.GAParameters();
        	TradingCommon.Traders.Optimization.FitnessParameters fitnessParameters1 = new TradingCommon.Traders.Optimization.FitnessParameters();
        	this.bottomPanel = new System.Windows.Forms.FlowLayoutPanel();
        	this.saveButton = new System.Windows.Forms.Button();
        	this.loadButton = new System.Windows.Forms.Button();
        	this.newButton = new System.Windows.Forms.Button();
        	this.testButton = new System.Windows.Forms.Button();
        	this.rawViewPage = new System.Windows.Forms.TabPage();
        	this.rawTextBox = new System.Windows.Forms.TextBox();
        	this.parametersPage = new System.Windows.Forms.TabPage();
        	this.parametersPanel = new System.Windows.Forms.Panel();
        	this.tsTabs = new System.Windows.Forms.TabControl();
        	this.searchPagePage = new System.Windows.Forms.TabPage();
        	this.searchSpacePanel = new System.Windows.Forms.Panel();
        	this.optimizePage = new System.Windows.Forms.TabPage();
        	this.gaParametersControl = new TradingSimulator.Ui.GAParametersControl();
        	this.fitnessParametersControl = new TradingSimulator.Ui.FitnessParametersControl();
        	this.gaButton = new System.Windows.Forms.Button();
        	this.bfButton = new System.Windows.Forms.Button();
        	this.bottomPanel.SuspendLayout();
        	this.rawViewPage.SuspendLayout();
        	this.parametersPage.SuspendLayout();
        	this.tsTabs.SuspendLayout();
        	this.searchPagePage.SuspendLayout();
        	this.optimizePage.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// bottomPanel
        	// 
        	this.bottomPanel.Controls.Add(this.saveButton);
        	this.bottomPanel.Controls.Add(this.loadButton);
        	this.bottomPanel.Controls.Add(this.newButton);
        	this.bottomPanel.Controls.Add(this.testButton);
        	this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
        	this.bottomPanel.FlowDirection = System.Windows.Forms.FlowDirection.RightToLeft;
        	this.bottomPanel.Location = new System.Drawing.Point(0, 538);
        	this.bottomPanel.Name = "bottomPanel";
        	this.bottomPanel.Padding = new System.Windows.Forms.Padding(1, 2, 1, 1);
        	this.bottomPanel.Size = new System.Drawing.Size(322, 32);
        	this.bottomPanel.TabIndex = 1;
        	// 
        	// saveButton
        	// 
        	this.saveButton.Location = new System.Drawing.Point(256, 5);
        	this.saveButton.Name = "saveButton";
        	this.saveButton.Size = new System.Drawing.Size(61, 23);
        	this.saveButton.TabIndex = 0;
        	this.saveButton.Text = "Save As";
        	this.saveButton.UseVisualStyleBackColor = true;
        	this.saveButton.Click += new System.EventHandler(this.SaveButtonClick);
        	// 
        	// loadButton
        	// 
        	this.loadButton.Location = new System.Drawing.Point(192, 5);
        	this.loadButton.Name = "loadButton";
        	this.loadButton.Size = new System.Drawing.Size(58, 23);
        	this.loadButton.TabIndex = 1;
        	this.loadButton.Text = "Load";
        	this.loadButton.UseVisualStyleBackColor = true;
        	this.loadButton.Click += new System.EventHandler(this.LoadButtonClick);
        	// 
        	// newButton
        	// 
        	this.newButton.Location = new System.Drawing.Point(128, 5);
        	this.newButton.Name = "newButton";
        	this.newButton.Size = new System.Drawing.Size(58, 23);
        	this.newButton.TabIndex = 2;
        	this.newButton.Text = "New";
        	this.newButton.UseVisualStyleBackColor = true;
        	this.newButton.Click += new System.EventHandler(this.NewButtonClick);
        	// 
        	// testButton
        	// 
        	this.testButton.BackColor = System.Drawing.SystemColors.ControlDark;
        	this.testButton.Location = new System.Drawing.Point(52, 5);
        	this.testButton.Margin = new System.Windows.Forms.Padding(3, 3, 8, 3);
        	this.testButton.Name = "testButton";
        	this.testButton.Size = new System.Drawing.Size(65, 23);
        	this.testButton.TabIndex = 3;
        	this.testButton.Text = "Test";
        	this.testButton.UseVisualStyleBackColor = false;
        	this.testButton.Click += new System.EventHandler(this.TestButtonClick);
        	// 
        	// rawViewPage
        	// 
        	this.rawViewPage.Controls.Add(this.rawTextBox);
        	this.rawViewPage.Location = new System.Drawing.Point(4, 22);
        	this.rawViewPage.Name = "rawViewPage";
        	this.rawViewPage.Size = new System.Drawing.Size(314, 512);
        	this.rawViewPage.TabIndex = 2;
        	this.rawViewPage.Text = "Raw view";
        	this.rawViewPage.UseVisualStyleBackColor = true;
        	// 
        	// rawTextBox
        	// 
        	this.rawTextBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.rawTextBox.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.rawTextBox.Location = new System.Drawing.Point(0, 0);
        	this.rawTextBox.Multiline = true;
        	this.rawTextBox.Name = "rawTextBox";
        	this.rawTextBox.ReadOnly = true;
        	this.rawTextBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        	this.rawTextBox.Size = new System.Drawing.Size(314, 512);
        	this.rawTextBox.TabIndex = 0;
        	this.rawTextBox.WordWrap = false;
        	// 
        	// parametersPage
        	// 
        	this.parametersPage.Controls.Add(this.parametersPanel);
        	this.parametersPage.Location = new System.Drawing.Point(4, 22);
        	this.parametersPage.Name = "parametersPage";
        	this.parametersPage.Padding = new System.Windows.Forms.Padding(3);
        	this.parametersPage.Size = new System.Drawing.Size(314, 512);
        	this.parametersPage.TabIndex = 1;
        	this.parametersPage.Text = "Parameters";
        	this.parametersPage.UseVisualStyleBackColor = true;
        	// 
        	// parametersPanel
        	// 
        	this.parametersPanel.AutoScroll = true;
        	this.parametersPanel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.parametersPanel.Location = new System.Drawing.Point(3, 3);
        	this.parametersPanel.Name = "parametersPanel";
        	this.parametersPanel.Size = new System.Drawing.Size(308, 506);
        	this.parametersPanel.TabIndex = 0;
        	this.parametersPanel.SizeChanged += new System.EventHandler(this.ParamPanelSizeChanged);
        	// 
        	// tsTabs
        	// 
        	this.tsTabs.Controls.Add(this.parametersPage);
        	this.tsTabs.Controls.Add(this.searchPagePage);
        	this.tsTabs.Controls.Add(this.rawViewPage);
        	this.tsTabs.Controls.Add(this.optimizePage);
        	this.tsTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.tsTabs.Location = new System.Drawing.Point(0, 0);
        	this.tsTabs.Name = "tsTabs";
        	this.tsTabs.SelectedIndex = 0;
        	this.tsTabs.Size = new System.Drawing.Size(322, 538);
        	this.tsTabs.TabIndex = 0;
        	this.tsTabs.Selecting += new System.Windows.Forms.TabControlCancelEventHandler(this.TsTabsSelecting);
        	// 
        	// searchPagePage
        	// 
        	this.searchPagePage.Controls.Add(this.searchSpacePanel);
        	this.searchPagePage.Location = new System.Drawing.Point(4, 22);
        	this.searchPagePage.Name = "searchPagePage";
        	this.searchPagePage.Size = new System.Drawing.Size(314, 512);
        	this.searchPagePage.TabIndex = 3;
        	this.searchPagePage.Text = "Search space";
        	this.searchPagePage.UseVisualStyleBackColor = true;
        	// 
        	// searchSpacePanel
        	// 
        	this.searchSpacePanel.AutoScroll = true;
        	this.searchSpacePanel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.searchSpacePanel.Location = new System.Drawing.Point(0, 0);
        	this.searchSpacePanel.Name = "searchSpacePanel";
        	this.searchSpacePanel.Size = new System.Drawing.Size(314, 512);
        	this.searchSpacePanel.TabIndex = 0;
        	this.searchSpacePanel.SizeChanged += new System.EventHandler(this.SearchSpacePanelSizeChanged);
        	// 
        	// optimizePage
        	// 
        	this.optimizePage.Controls.Add(this.gaParametersControl);
        	this.optimizePage.Controls.Add(this.fitnessParametersControl);
        	this.optimizePage.Controls.Add(this.gaButton);
        	this.optimizePage.Controls.Add(this.bfButton);
        	this.optimizePage.Location = new System.Drawing.Point(4, 22);
        	this.optimizePage.Name = "optimizePage";
        	this.optimizePage.Size = new System.Drawing.Size(314, 512);
        	this.optimizePage.TabIndex = 4;
        	this.optimizePage.Text = "Optimize";
        	this.optimizePage.UseVisualStyleBackColor = true;
        	// 
        	// gaParametersControl
        	// 
        	gaParameters1.CrossoverRate = 0.8;
        	gaParameters1.GenerationSize = 10;
        	gaParameters1.MutationRate = 0.05;
        	gaParameters1.PopulationSize = 100;
        	gaParameters1.RouletteStep = 1.01;
        	this.gaParametersControl.Data = gaParameters1;
        	this.gaParametersControl.Location = new System.Drawing.Point(8, 176);
        	this.gaParametersControl.Name = "gaParametersControl";
        	this.gaParametersControl.Size = new System.Drawing.Size(187, 196);
        	this.gaParametersControl.TabIndex = 39;
        	// 
        	// fitnessParametersControl
        	// 
        	fitnessParameters1.MaxTradesPerDay = 5;
        	fitnessParameters1.MinTradesPerDay = 0;
        	fitnessParameters1.TargetFitness = 1000000;
        	this.fitnessParametersControl.Data = fitnessParameters1;
        	this.fitnessParametersControl.Location = new System.Drawing.Point(8, 8);
        	this.fitnessParametersControl.Name = "fitnessParametersControl";
        	this.fitnessParametersControl.Size = new System.Drawing.Size(227, 107);
        	this.fitnessParametersControl.TabIndex = 38;
        	// 
        	// gaButton
        	// 
        	this.gaButton.Location = new System.Drawing.Point(24, 384);
        	this.gaButton.Name = "gaButton";
        	this.gaButton.Size = new System.Drawing.Size(120, 24);
        	this.gaButton.TabIndex = 29;
        	this.gaButton.Text = "Use genetic algorithm";
        	this.gaButton.UseVisualStyleBackColor = true;
        	this.gaButton.Click += new System.EventHandler(this.GaButtonClick);
        	// 
        	// bfButton
        	// 
        	this.bfButton.Location = new System.Drawing.Point(24, 128);
        	this.bfButton.Name = "bfButton";
        	this.bfButton.Size = new System.Drawing.Size(120, 24);
        	this.bfButton.TabIndex = 24;
        	this.bfButton.Text = "Search entire space";
        	this.bfButton.UseVisualStyleBackColor = true;
        	this.bfButton.Click += new System.EventHandler(this.BfButtonClick);
        	// 
        	// TSTunerControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.tsTabs);
        	this.Controls.Add(this.bottomPanel);
        	this.MinimumSize = new System.Drawing.Size(200, 0);
        	this.Name = "TSTunerControl";
        	this.Size = new System.Drawing.Size(322, 570);
        	this.Load += new System.EventHandler(this.TSTunerLoad);
        	this.bottomPanel.ResumeLayout(false);
        	this.rawViewPage.ResumeLayout(false);
        	this.rawViewPage.PerformLayout();
        	this.parametersPage.ResumeLayout(false);
        	this.tsTabs.ResumeLayout(false);
        	this.searchPagePage.ResumeLayout(false);
        	this.optimizePage.ResumeLayout(false);
        	this.ResumeLayout(false);
        }
        private TradingSimulator.Ui.GAParametersControl gaParametersControl;
        private TradingSimulator.Ui.FitnessParametersControl fitnessParametersControl;
        private System.Windows.Forms.Button bfButton;
        private System.Windows.Forms.Button gaButton;
        private System.Windows.Forms.Panel searchSpacePanel;
        private System.Windows.Forms.Panel parametersPanel;
        private System.Windows.Forms.TabPage optimizePage;
        private System.Windows.Forms.TabPage searchPagePage;
        private System.Windows.Forms.Button testButton;
        private System.Windows.Forms.TextBox rawTextBox;
        private System.Windows.Forms.Button newButton;
        private System.Windows.Forms.Button loadButton;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.FlowLayoutPanel bottomPanel;
        private System.Windows.Forms.TabPage rawViewPage;
        private System.Windows.Forms.TabPage parametersPage;
        private System.Windows.Forms.TabControl tsTabs;
    }
}
