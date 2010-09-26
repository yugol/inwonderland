/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/27/2009
 * Time: 2:12 AM
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
    partial class TSParameterValueEditor
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
        	this.nameLabel = new System.Windows.Forms.Label();
        	this.textBox = new System.Windows.Forms.TextBox();
        	this.trackBar = new System.Windows.Forms.TrackBar();
        	this.panel = new System.Windows.Forms.TableLayoutPanel();
        	this.minLabel = new System.Windows.Forms.Label();
        	this.maxLabel = new System.Windows.Forms.Label();
        	((System.ComponentModel.ISupportInitialize)(this.trackBar)).BeginInit();
        	this.panel.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// nameLabel
        	// 
        	this.nameLabel.AutoSize = true;
        	this.panel.SetColumnSpan(this.nameLabel, 3);
        	this.nameLabel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.nameLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.nameLabel.Location = new System.Drawing.Point(3, 0);
        	this.nameLabel.Name = "nameLabel";
        	this.nameLabel.Padding = new System.Windows.Forms.Padding(0, 1, 0, 0);
        	this.nameLabel.Size = new System.Drawing.Size(290, 15);
        	this.nameLabel.TabIndex = 0;
        	this.nameLabel.Text = "Name (p)";
        	// 
        	// textBox
        	// 
        	this.textBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.textBox.Location = new System.Drawing.Point(3, 33);
        	this.textBox.Name = "textBox";
        	this.textBox.Size = new System.Drawing.Size(54, 20);
        	this.textBox.TabIndex = 1;
        	this.textBox.TextChanged += new System.EventHandler(this.TextBoxTextChanged);
        	// 
        	// trackBar
        	// 
        	this.panel.SetColumnSpan(this.trackBar, 2);
        	this.trackBar.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.trackBar.LargeChange = 10;
        	this.trackBar.Location = new System.Drawing.Point(63, 33);
        	this.trackBar.Maximum = 500;
        	this.trackBar.Name = "trackBar";
        	this.trackBar.Size = new System.Drawing.Size(230, 19);
        	this.trackBar.TabIndex = 2;
        	this.trackBar.TickStyle = System.Windows.Forms.TickStyle.None;
        	this.trackBar.Scroll += new System.EventHandler(this.TrackBarScroll);
        	// 
        	// panel
        	// 
        	this.panel.ColumnCount = 3;
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Absolute, 60F));
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
        	this.panel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
        	this.panel.Controls.Add(this.trackBar, 1, 2);
        	this.panel.Controls.Add(this.textBox, 0, 2);
        	this.panel.Controls.Add(this.nameLabel, 0, 0);
        	this.panel.Controls.Add(this.minLabel, 1, 1);
        	this.panel.Controls.Add(this.maxLabel, 2, 1);
        	this.panel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.panel.Location = new System.Drawing.Point(0, 0);
        	this.panel.Name = "panel";
        	this.panel.RowCount = 3;
        	this.panel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 15F));
        	this.panel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 15F));
        	this.panel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 100F));
        	this.panel.Size = new System.Drawing.Size(296, 55);
        	this.panel.TabIndex = 3;
        	// 
        	// minLabel
        	// 
        	this.minLabel.AutoSize = true;
        	this.minLabel.Dock = System.Windows.Forms.DockStyle.Left;
        	this.minLabel.Location = new System.Drawing.Point(63, 15);
        	this.minLabel.Name = "minLabel";
        	this.minLabel.Size = new System.Drawing.Size(24, 15);
        	this.minLabel.TabIndex = 3;
        	this.minLabel.Text = "Min";
        	// 
        	// maxLabel
        	// 
        	this.maxLabel.AutoSize = true;
        	this.maxLabel.Dock = System.Windows.Forms.DockStyle.Right;
        	this.maxLabel.Location = new System.Drawing.Point(266, 15);
        	this.maxLabel.Name = "maxLabel";
        	this.maxLabel.Size = new System.Drawing.Size(27, 15);
        	this.maxLabel.TabIndex = 4;
        	this.maxLabel.Text = "Max";
        	// 
        	// TSParameterEditor
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.panel);
        	this.MaximumSize = new System.Drawing.Size(0, 55);
        	this.MinimumSize = new System.Drawing.Size(100, 55);
        	this.Name = "TSParameterEditor";
        	this.Size = new System.Drawing.Size(296, 55);
        	((System.ComponentModel.ISupportInitialize)(this.trackBar)).EndInit();
        	this.panel.ResumeLayout(false);
        	this.panel.PerformLayout();
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.Label maxLabel;
        private System.Windows.Forms.Label minLabel;
        private System.Windows.Forms.TextBox textBox;
        private System.Windows.Forms.TrackBar trackBar;
        private System.Windows.Forms.TableLayoutPanel panel;
        private System.Windows.Forms.Label nameLabel;
    }
}
