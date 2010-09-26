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
namespace TradingSimulator.Ui
{
    partial class FitnessParametersControl
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
        	this.minTpdNud = new System.Windows.Forms.NumericUpDown();
        	this.maxTpdNud = new System.Windows.Forms.NumericUpDown();
        	this.targetFitnessBox = new System.Windows.Forms.TextBox();
        	this.label4 = new System.Windows.Forms.Label();
        	this.label3 = new System.Windows.Forms.Label();
        	this.label2 = new System.Windows.Forms.Label();
        	this.label1 = new System.Windows.Forms.Label();
        	((System.ComponentModel.ISupportInitialize)(this.minTpdNud)).BeginInit();
        	((System.ComponentModel.ISupportInitialize)(this.maxTpdNud)).BeginInit();
        	this.SuspendLayout();
        	// 
        	// minTpdNud
        	// 
        	this.minTpdNud.DecimalPlaces = 2;
        	this.minTpdNud.Increment = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	131072});
        	this.minTpdNud.Location = new System.Drawing.Point(128, 8);
        	this.minTpdNud.Maximum = new decimal(new int[] {
        	        	        	10,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.minTpdNud.Name = "minTpdNud";
        	this.minTpdNud.Size = new System.Drawing.Size(56, 20);
        	this.minTpdNud.TabIndex = 42;
        	this.minTpdNud.Value = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	// 
        	// maxTpdNud
        	// 
        	this.maxTpdNud.DecimalPlaces = 2;
        	this.maxTpdNud.Increment = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	131072});
        	this.maxTpdNud.Location = new System.Drawing.Point(128, 40);
        	this.maxTpdNud.Maximum = new decimal(new int[] {
        	        	        	10,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.maxTpdNud.Minimum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	131072});
        	this.maxTpdNud.Name = "maxTpdNud";
        	this.maxTpdNud.Size = new System.Drawing.Size(56, 20);
        	this.maxTpdNud.TabIndex = 41;
        	this.maxTpdNud.Value = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	// 
        	// targetFitnessBox
        	// 
        	this.targetFitnessBox.Location = new System.Drawing.Point(128, 80);
        	this.targetFitnessBox.Name = "targetFitnessBox";
        	this.targetFitnessBox.Size = new System.Drawing.Size(88, 20);
        	this.targetFitnessBox.TabIndex = 40;
        	// 
        	// label4
        	// 
        	this.label4.AutoSize = true;
        	this.label4.Location = new System.Drawing.Point(8, 80);
        	this.label4.Name = "label4";
        	this.label4.Size = new System.Drawing.Size(74, 13);
        	this.label4.TabIndex = 39;
        	this.label4.Text = "Target fitness:";
        	// 
        	// label3
        	// 
        	this.label3.AutoSize = true;
        	this.label3.Location = new System.Drawing.Point(192, 48);
        	this.label3.Name = "label3";
        	this.label3.Size = new System.Drawing.Size(26, 13);
        	this.label3.TabIndex = 38;
        	this.label3.Text = "max";
        	// 
        	// label2
        	// 
        	this.label2.AutoSize = true;
        	this.label2.Location = new System.Drawing.Point(192, 16);
        	this.label2.Name = "label2";
        	this.label2.Size = new System.Drawing.Size(23, 13);
        	this.label2.TabIndex = 37;
        	this.label2.Text = "min";
        	// 
        	// label1
        	// 
        	this.label1.AutoSize = true;
        	this.label1.Location = new System.Drawing.Point(8, 8);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(81, 13);
        	this.label1.TabIndex = 36;
        	this.label1.Text = "Trades per day:";
        	// 
        	// FitnessParametersControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.minTpdNud);
        	this.Controls.Add(this.maxTpdNud);
        	this.Controls.Add(this.targetFitnessBox);
        	this.Controls.Add(this.label4);
        	this.Controls.Add(this.label3);
        	this.Controls.Add(this.label2);
        	this.Controls.Add(this.label1);
        	this.Name = "FitnessParametersControl";
        	this.Size = new System.Drawing.Size(227, 107);
        	((System.ComponentModel.ISupportInitialize)(this.minTpdNud)).EndInit();
        	((System.ComponentModel.ISupportInitialize)(this.maxTpdNud)).EndInit();
        	this.ResumeLayout(false);
        	this.PerformLayout();
        }
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox targetFitnessBox;
        private System.Windows.Forms.NumericUpDown maxTpdNud;
        private System.Windows.Forms.NumericUpDown minTpdNud;
    }
}
