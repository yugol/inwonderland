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
namespace TradingSimulator.Ui
{
    partial class TSSeekerControl
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
        	TradingCommon.Traders.Optimization.Search.GenotypeParameters genotypeParameters1 = new TradingCommon.Traders.Optimization.Search.GenotypeParameters();
        	TradingCommon.Traders.Optimization.GAParameters gaParameters1 = new TradingCommon.Traders.Optimization.GAParameters();
        	TradingCommon.Traders.Optimization.FitnessParameters fitnessParameters1 = new TradingCommon.Traders.Optimization.FitnessParameters();
        	TradingCommon.Traders.Optimization.GAParameters gaParameters2 = new TradingCommon.Traders.Optimization.GAParameters();
        	TradingCommon.Traders.Optimization.FitnessParameters fitnessParameters2 = new TradingCommon.Traders.Optimization.FitnessParameters();
        	this.seekerPages = new System.Windows.Forms.TabControl();
        	this.outerPage = new System.Windows.Forms.TabPage();
        	this.runButton = new System.Windows.Forms.Button();
        	this.genotypeControl = new TradingSimulator.Ui.GenotypeParametersControl();
        	this.outerGAControl = new TradingSimulator.Ui.GAParametersControl();
        	this.outerFitnessControl = new TradingSimulator.Ui.FitnessParametersControl();
        	this.innerPage = new System.Windows.Forms.TabPage();
        	this.minFitnessBox = new System.Windows.Forms.TextBox();
        	this.label4 = new System.Windows.Forms.Label();
        	this.innerGAControl = new TradingSimulator.Ui.GAParametersControl();
        	this.innerFitnessControl = new TradingSimulator.Ui.FitnessParametersControl();
        	this.seekerPages.SuspendLayout();
        	this.outerPage.SuspendLayout();
        	this.innerPage.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// seekerPages
        	// 
        	this.seekerPages.Controls.Add(this.outerPage);
        	this.seekerPages.Controls.Add(this.innerPage);
        	this.seekerPages.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.seekerPages.Location = new System.Drawing.Point(0, 0);
        	this.seekerPages.Name = "seekerPages";
        	this.seekerPages.SelectedIndex = 0;
        	this.seekerPages.Size = new System.Drawing.Size(278, 553);
        	this.seekerPages.TabIndex = 0;
        	// 
        	// outerPage
        	// 
        	this.outerPage.Controls.Add(this.runButton);
        	this.outerPage.Controls.Add(this.genotypeControl);
        	this.outerPage.Controls.Add(this.outerGAControl);
        	this.outerPage.Controls.Add(this.outerFitnessControl);
        	this.outerPage.Location = new System.Drawing.Point(4, 22);
        	this.outerPage.Name = "outerPage";
        	this.outerPage.Padding = new System.Windows.Forms.Padding(3);
        	this.outerPage.Size = new System.Drawing.Size(270, 527);
        	this.outerPage.TabIndex = 0;
        	this.outerPage.Text = "Outer";
        	this.outerPage.UseVisualStyleBackColor = true;
        	// 
        	// runButton
        	// 
        	this.runButton.Location = new System.Drawing.Point(24, 384);
        	this.runButton.Name = "runButton";
        	this.runButton.Size = new System.Drawing.Size(88, 23);
        	this.runButton.TabIndex = 3;
        	this.runButton.Text = "Run search";
        	this.runButton.UseVisualStyleBackColor = true;
        	this.runButton.Click += new System.EventHandler(this.RunButtonClick);
        	// 
        	// genotypeControl
        	// 
        	genotypeParameters1.MaxRuleCount = 3;
        	this.genotypeControl.Data = genotypeParameters1;
        	this.genotypeControl.Location = new System.Drawing.Point(8, 8);
        	this.genotypeControl.Name = "genotypeControl";
        	this.genotypeControl.Size = new System.Drawing.Size(200, 35);
        	this.genotypeControl.TabIndex = 2;
        	// 
        	// outerGAControl
        	// 
        	gaParameters1.CrossoverRate = 0.8;
        	gaParameters1.GenerationSize = 10;
        	gaParameters1.MutationRate = 0.05;
        	gaParameters1.PopulationSize = 100;
        	gaParameters1.RouletteStep = 1.01;
        	this.outerGAControl.Data = gaParameters1;
        	this.outerGAControl.Location = new System.Drawing.Point(8, 176);
        	this.outerGAControl.Name = "outerGAControl";
        	this.outerGAControl.Size = new System.Drawing.Size(187, 196);
        	this.outerGAControl.TabIndex = 1;
        	// 
        	// outerFitnessControl
        	// 
        	fitnessParameters1.MaxTradesPerDay = 5;
        	fitnessParameters1.MinTradesPerDay = 0;
        	fitnessParameters1.TargetFitness = 1000000;
        	this.outerFitnessControl.Data = fitnessParameters1;
        	this.outerFitnessControl.Location = new System.Drawing.Point(8, 56);
        	this.outerFitnessControl.Name = "outerFitnessControl";
        	this.outerFitnessControl.Size = new System.Drawing.Size(227, 107);
        	this.outerFitnessControl.TabIndex = 0;
        	// 
        	// innerPage
        	// 
        	this.innerPage.Controls.Add(this.minFitnessBox);
        	this.innerPage.Controls.Add(this.label4);
        	this.innerPage.Controls.Add(this.innerGAControl);
        	this.innerPage.Controls.Add(this.innerFitnessControl);
        	this.innerPage.Location = new System.Drawing.Point(4, 22);
        	this.innerPage.Name = "innerPage";
        	this.innerPage.Padding = new System.Windows.Forms.Padding(3);
        	this.innerPage.Size = new System.Drawing.Size(270, 527);
        	this.innerPage.TabIndex = 1;
        	this.innerPage.Text = "Inner";
        	this.innerPage.UseVisualStyleBackColor = true;
        	// 
        	// minFitnessBox
        	// 
        	this.minFitnessBox.Location = new System.Drawing.Point(136, 136);
        	this.minFitnessBox.Name = "minFitnessBox";
        	this.minFitnessBox.Size = new System.Drawing.Size(88, 20);
        	this.minFitnessBox.TabIndex = 44;
        	// 
        	// label4
        	// 
        	this.label4.AutoSize = true;
        	this.label4.Location = new System.Drawing.Point(16, 136);
        	this.label4.Name = "label4";
        	this.label4.Size = new System.Drawing.Size(60, 13);
        	this.label4.TabIndex = 43;
        	this.label4.Text = "Min fitness:";
        	// 
        	// innerGAControl
        	// 
        	gaParameters2.CrossoverRate = 0.8;
        	gaParameters2.GenerationSize = 10;
        	gaParameters2.MutationRate = 0.05;
        	gaParameters2.PopulationSize = 100;
        	gaParameters2.RouletteStep = 1.01;
        	this.innerGAControl.Data = gaParameters2;
        	this.innerGAControl.Location = new System.Drawing.Point(8, 176);
        	this.innerGAControl.Name = "innerGAControl";
        	this.innerGAControl.Size = new System.Drawing.Size(187, 196);
        	this.innerGAControl.TabIndex = 3;
        	// 
        	// innerFitnessControl
        	// 
        	fitnessParameters2.MaxTradesPerDay = 5;
        	fitnessParameters2.MinTradesPerDay = 0;
        	fitnessParameters2.TargetFitness = 1000000;
        	this.innerFitnessControl.Data = fitnessParameters2;
        	this.innerFitnessControl.Location = new System.Drawing.Point(8, 8);
        	this.innerFitnessControl.Name = "innerFitnessControl";
        	this.innerFitnessControl.Size = new System.Drawing.Size(227, 107);
        	this.innerFitnessControl.TabIndex = 2;
        	// 
        	// TSSeekerControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.seekerPages);
        	this.Name = "TSSeekerControl";
        	this.Size = new System.Drawing.Size(278, 553);
        	this.seekerPages.ResumeLayout(false);
        	this.outerPage.ResumeLayout(false);
        	this.innerPage.ResumeLayout(false);
        	this.innerPage.PerformLayout();
        	this.ResumeLayout(false);
        }
        private TradingSimulator.Ui.GenotypeParametersControl genotypeControl;
        private System.Windows.Forms.Button runButton;
        private System.Windows.Forms.TextBox minFitnessBox;
        private System.Windows.Forms.Label label4;
        private TradingSimulator.Ui.FitnessParametersControl innerFitnessControl;
        private TradingSimulator.Ui.GAParametersControl innerGAControl;
        private TradingSimulator.Ui.FitnessParametersControl outerFitnessControl;
        private TradingSimulator.Ui.GAParametersControl outerGAControl;
        private System.Windows.Forms.TabPage innerPage;
        private System.Windows.Forms.TabPage outerPage;
        private System.Windows.Forms.TabControl seekerPages;
    }
}
