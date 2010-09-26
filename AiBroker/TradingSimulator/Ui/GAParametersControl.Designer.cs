/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 5/3/2009
 * Time: 4:23 PM
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
    partial class GAParametersControl
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
        	this.label9 = new System.Windows.Forms.Label();
        	this.rouletteStepNud = new System.Windows.Forms.NumericUpDown();
        	this.crossoverNud = new System.Windows.Forms.NumericUpDown();
        	this.mutationNud = new System.Windows.Forms.NumericUpDown();
        	this.generationNud = new System.Windows.Forms.NumericUpDown();
        	this.populationNud = new System.Windows.Forms.NumericUpDown();
        	this.label8 = new System.Windows.Forms.Label();
        	this.label7 = new System.Windows.Forms.Label();
        	this.label6 = new System.Windows.Forms.Label();
        	this.label5 = new System.Windows.Forms.Label();
        	((System.ComponentModel.ISupportInitialize)(this.rouletteStepNud)).BeginInit();
        	((System.ComponentModel.ISupportInitialize)(this.crossoverNud)).BeginInit();
        	((System.ComponentModel.ISupportInitialize)(this.mutationNud)).BeginInit();
        	((System.ComponentModel.ISupportInitialize)(this.generationNud)).BeginInit();
        	((System.ComponentModel.ISupportInitialize)(this.populationNud)).BeginInit();
        	this.SuspendLayout();
        	// 
        	// label9
        	// 
        	this.label9.AutoSize = true;
        	this.label9.Location = new System.Drawing.Point(8, 88);
        	this.label9.Name = "label9";
        	this.label9.Size = new System.Drawing.Size(73, 13);
        	this.label9.TabIndex = 47;
        	this.label9.Text = "Roulette step:";
        	// 
        	// rouletteStepNud
        	// 
        	this.rouletteStepNud.DecimalPlaces = 2;
        	this.rouletteStepNud.Increment = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	131072});
        	this.rouletteStepNud.Location = new System.Drawing.Point(128, 88);
        	this.rouletteStepNud.Maximum = new decimal(new int[] {
        	        	        	2,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.rouletteStepNud.Minimum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.rouletteStepNud.Name = "rouletteStepNud";
        	this.rouletteStepNud.Size = new System.Drawing.Size(56, 20);
        	this.rouletteStepNud.TabIndex = 46;
        	this.rouletteStepNud.Value = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	// 
        	// crossoverNud
        	// 
        	this.crossoverNud.DecimalPlaces = 2;
        	this.crossoverNud.Increment = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	131072});
        	this.crossoverNud.Location = new System.Drawing.Point(128, 168);
        	this.crossoverNud.Maximum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.crossoverNud.Name = "crossoverNud";
        	this.crossoverNud.Size = new System.Drawing.Size(56, 20);
        	this.crossoverNud.TabIndex = 45;
        	this.crossoverNud.Value = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	// 
        	// mutationNud
        	// 
        	this.mutationNud.DecimalPlaces = 2;
        	this.mutationNud.Increment = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	131072});
        	this.mutationNud.Location = new System.Drawing.Point(128, 128);
        	this.mutationNud.Maximum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.mutationNud.Name = "mutationNud";
        	this.mutationNud.Size = new System.Drawing.Size(56, 20);
        	this.mutationNud.TabIndex = 44;
        	this.mutationNud.Value = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	// 
        	// generationNud
        	// 
        	this.generationNud.Location = new System.Drawing.Point(128, 48);
        	this.generationNud.Minimum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.generationNud.Name = "generationNud";
        	this.generationNud.Size = new System.Drawing.Size(56, 20);
        	this.generationNud.TabIndex = 43;
        	this.generationNud.Value = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	// 
        	// populationNud
        	// 
        	this.populationNud.Increment = new decimal(new int[] {
        	        	        	2,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.populationNud.Location = new System.Drawing.Point(128, 8);
        	this.populationNud.Maximum = new decimal(new int[] {
        	        	        	500,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.populationNud.Minimum = new decimal(new int[] {
        	        	        	2,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.populationNud.Name = "populationNud";
        	this.populationNud.Size = new System.Drawing.Size(56, 20);
        	this.populationNud.TabIndex = 42;
        	this.populationNud.Value = new decimal(new int[] {
        	        	        	2,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	// 
        	// label8
        	// 
        	this.label8.AutoSize = true;
        	this.label8.Location = new System.Drawing.Point(8, 168);
        	this.label8.Name = "label8";
        	this.label8.Size = new System.Drawing.Size(78, 13);
        	this.label8.TabIndex = 41;
        	this.label8.Text = "Crossover rate:";
        	// 
        	// label7
        	// 
        	this.label7.AutoSize = true;
        	this.label7.Location = new System.Drawing.Point(8, 128);
        	this.label7.Name = "label7";
        	this.label7.Size = new System.Drawing.Size(72, 13);
        	this.label7.TabIndex = 40;
        	this.label7.Text = "Mutation rate:";
        	// 
        	// label6
        	// 
        	this.label6.AutoSize = true;
        	this.label6.Location = new System.Drawing.Point(8, 48);
        	this.label6.Name = "label6";
        	this.label6.Size = new System.Drawing.Size(92, 13);
        	this.label6.TabIndex = 39;
        	this.label6.Text = "Generation count:";
        	// 
        	// label5
        	// 
        	this.label5.AutoSize = true;
        	this.label5.Location = new System.Drawing.Point(8, 8);
        	this.label5.Name = "label5";
        	this.label5.Size = new System.Drawing.Size(81, 13);
        	this.label5.TabIndex = 38;
        	this.label5.Text = "Population size:";
        	// 
        	// GAParametersControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.label9);
        	this.Controls.Add(this.rouletteStepNud);
        	this.Controls.Add(this.crossoverNud);
        	this.Controls.Add(this.mutationNud);
        	this.Controls.Add(this.generationNud);
        	this.Controls.Add(this.populationNud);
        	this.Controls.Add(this.label8);
        	this.Controls.Add(this.label7);
        	this.Controls.Add(this.label6);
        	this.Controls.Add(this.label5);
        	this.Name = "GAParametersControl";
        	this.Size = new System.Drawing.Size(195, 196);
        	((System.ComponentModel.ISupportInitialize)(this.rouletteStepNud)).EndInit();
        	((System.ComponentModel.ISupportInitialize)(this.crossoverNud)).EndInit();
        	((System.ComponentModel.ISupportInitialize)(this.mutationNud)).EndInit();
        	((System.ComponentModel.ISupportInitialize)(this.generationNud)).EndInit();
        	((System.ComponentModel.ISupportInitialize)(this.populationNud)).EndInit();
        	this.ResumeLayout(false);
        	this.PerformLayout();
        }
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.NumericUpDown populationNud;
        private System.Windows.Forms.NumericUpDown generationNud;
        private System.Windows.Forms.NumericUpDown mutationNud;
        private System.Windows.Forms.NumericUpDown crossoverNud;
        private System.Windows.Forms.NumericUpDown rouletteStepNud;
        private System.Windows.Forms.Label label9;
    }
}
