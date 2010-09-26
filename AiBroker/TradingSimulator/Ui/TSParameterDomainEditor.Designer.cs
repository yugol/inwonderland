/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 4:33 AM
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
    partial class TSParameterDomainEditor
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
        	this.panel = new System.Windows.Forms.TableLayoutPanel();
        	this.nameLabel = new System.Windows.Forms.Label();
        	this.label1 = new System.Windows.Forms.Label();
        	this.label2 = new System.Windows.Forms.Label();
        	this.label3 = new System.Windows.Forms.Label();
        	this.minBox = new System.Windows.Forms.TextBox();
        	this.maxBox = new System.Windows.Forms.TextBox();
        	this.stepsCombo = new System.Windows.Forms.ComboBox();
        	this.panel.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// panel
        	// 
        	this.panel.ColumnCount = 6;
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 35F));
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 40F));
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 40F));
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 40F));
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 45F));
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
        	this.panel.Controls.Add(this.nameLabel, 0, 0);
        	this.panel.Controls.Add(this.label1, 0, 1);
        	this.panel.Controls.Add(this.label2, 2, 1);
        	this.panel.Controls.Add(this.label3, 4, 1);
        	this.panel.Controls.Add(this.minBox, 1, 1);
        	this.panel.Controls.Add(this.maxBox, 3, 1);
        	this.panel.Controls.Add(this.stepsCombo, 5, 1);
        	this.panel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.panel.Location = new System.Drawing.Point(0, 0);
        	this.panel.Name = "panel";
        	this.panel.RowCount = 2;
        	this.panel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 16F));
        	this.panel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
        	this.panel.Size = new System.Drawing.Size(381, 42);
        	this.panel.TabIndex = 0;
        	// 
        	// nameLabel
        	// 
        	this.nameLabel.AutoSize = true;
        	this.panel.SetColumnSpan(this.nameLabel, 6);
        	this.nameLabel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.nameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.nameLabel.Location = new System.Drawing.Point(3, 0);
        	this.nameLabel.Name = "nameLabel";
        	this.nameLabel.Size = new System.Drawing.Size(375, 16);
        	this.nameLabel.TabIndex = 0;
        	this.nameLabel.Text = "Parameter name";
        	// 
        	// label1
        	// 
        	this.label1.AutoSize = true;
        	this.label1.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.label1.Location = new System.Drawing.Point(3, 16);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(29, 26);
        	this.label1.TabIndex = 1;
        	this.label1.Text = "Min:";
        	// 
        	// label2
        	// 
        	this.label2.AutoSize = true;
        	this.label2.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.label2.Location = new System.Drawing.Point(78, 16);
        	this.label2.Name = "label2";
        	this.label2.Size = new System.Drawing.Size(34, 26);
        	this.label2.TabIndex = 2;
        	this.label2.Text = "Max:";
        	// 
        	// label3
        	// 
        	this.label3.AutoSize = true;
        	this.label3.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.label3.Location = new System.Drawing.Point(158, 16);
        	this.label3.Name = "label3";
        	this.label3.Size = new System.Drawing.Size(39, 26);
        	this.label3.TabIndex = 3;
        	this.label3.Text = "Steps:";
        	// 
        	// minBox
        	// 
        	this.minBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.minBox.Location = new System.Drawing.Point(38, 19);
        	this.minBox.Name = "minBox";
        	this.minBox.Size = new System.Drawing.Size(34, 20);
        	this.minBox.TabIndex = 4;
        	// 
        	// maxBox
        	// 
        	this.maxBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.maxBox.Location = new System.Drawing.Point(118, 19);
        	this.maxBox.Name = "maxBox";
        	this.maxBox.Size = new System.Drawing.Size(34, 20);
        	this.maxBox.TabIndex = 5;
        	// 
        	// stepsCombo
        	// 
        	this.stepsCombo.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.stepsCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        	this.stepsCombo.FormattingEnabled = true;
        	this.stepsCombo.Items.AddRange(new object[] {
        	        	        	"2",
        	        	        	"4",
        	        	        	"8",
        	        	        	"16",
        	        	        	"32",
        	        	        	"64",
        	        	        	"128",
        	        	        	"256",
        	        	        	"512",
        	        	        	"1024"});
        	this.stepsCombo.Location = new System.Drawing.Point(203, 19);
        	this.stepsCombo.MaxDropDownItems = 12;
        	this.stepsCombo.Name = "stepsCombo";
        	this.stepsCombo.Size = new System.Drawing.Size(175, 21);
        	this.stepsCombo.TabIndex = 6;
        	// 
        	// SearchSpaceEditor
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.panel);
        	this.MaximumSize = new System.Drawing.Size(0, 42);
        	this.MinimumSize = new System.Drawing.Size(200, 42);
        	this.Name = "SearchSpaceEditor";
        	this.Size = new System.Drawing.Size(381, 42);
        	this.panel.ResumeLayout(false);
        	this.panel.PerformLayout();
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.TextBox maxBox;
        private System.Windows.Forms.TextBox minBox;
        private System.Windows.Forms.ComboBox stepsCombo;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label nameLabel;
        private System.Windows.Forms.TableLayoutPanel panel;
    }
}
