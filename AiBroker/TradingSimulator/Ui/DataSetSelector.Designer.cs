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
namespace TradingSimulator.Ui
{
    partial class DataSetSelector
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
        	this.groupBox = new System.Windows.Forms.GroupBox();
        	this.toDtp = new System.Windows.Forms.DateTimePicker();
        	this.fromDtp = new System.Windows.Forms.DateTimePicker();
        	this.usedEntriesLabel = new System.Windows.Forms.Label();
        	this.dataEntriesLabel = new System.Windows.Forms.Label();
        	this.label7 = new System.Windows.Forms.Label();
        	this.label6 = new System.Windows.Forms.Label();
        	this.label5 = new System.Windows.Forms.Label();
        	this.label4 = new System.Windows.Forms.Label();
        	this.label2 = new System.Windows.Forms.Label();
        	this.titleLabel = new System.Windows.Forms.Label();
        	this.label1 = new System.Windows.Forms.Label();
        	this.copyButton = new System.Windows.Forms.Button();
        	this.displayButton = new System.Windows.Forms.Button();
        	this.loadButton = new System.Windows.Forms.Button();
        	this.groupBox.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// groupBox
        	// 
        	this.groupBox.Controls.Add(this.toDtp);
        	this.groupBox.Controls.Add(this.fromDtp);
        	this.groupBox.Controls.Add(this.usedEntriesLabel);
        	this.groupBox.Controls.Add(this.dataEntriesLabel);
        	this.groupBox.Controls.Add(this.label7);
        	this.groupBox.Controls.Add(this.label6);
        	this.groupBox.Controls.Add(this.label5);
        	this.groupBox.Controls.Add(this.label4);
        	this.groupBox.Controls.Add(this.label2);
        	this.groupBox.Controls.Add(this.titleLabel);
        	this.groupBox.Controls.Add(this.label1);
        	this.groupBox.Controls.Add(this.copyButton);
        	this.groupBox.Controls.Add(this.displayButton);
        	this.groupBox.Controls.Add(this.loadButton);
        	this.groupBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.groupBox.Location = new System.Drawing.Point(0, 0);
        	this.groupBox.Name = "groupBox";
        	this.groupBox.Size = new System.Drawing.Size(200, 226);
        	this.groupBox.TabIndex = 0;
        	this.groupBox.TabStop = false;
        	this.groupBox.Text = "DataName";
        	// 
        	// toDtp
        	// 
        	this.toDtp.CustomFormat = "yyyy-MM-dd";
        	this.toDtp.Enabled = false;
        	this.toDtp.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
        	this.toDtp.Location = new System.Drawing.Point(96, 152);
        	this.toDtp.Name = "toDtp";
        	this.toDtp.Size = new System.Drawing.Size(96, 20);
        	this.toDtp.TabIndex = 15;
        	this.toDtp.ValueChanged += new System.EventHandler(this.ToDtpValueChanged);
        	// 
        	// fromDtp
        	// 
        	this.fromDtp.CustomFormat = "yyyy-MM-dd";
        	this.fromDtp.Enabled = false;
        	this.fromDtp.Format = System.Windows.Forms.DateTimePickerFormat.Custom;
        	this.fromDtp.Location = new System.Drawing.Point(96, 128);
        	this.fromDtp.Name = "fromDtp";
        	this.fromDtp.Size = new System.Drawing.Size(96, 20);
        	this.fromDtp.TabIndex = 14;
        	this.fromDtp.ValueChanged += new System.EventHandler(this.FromDtpValueChanged);
        	// 
        	// usedEntriesLabel
        	// 
        	this.usedEntriesLabel.AutoSize = true;
        	this.usedEntriesLabel.Location = new System.Drawing.Point(96, 184);
        	this.usedEntriesLabel.Name = "usedEntriesLabel";
        	this.usedEntriesLabel.Size = new System.Drawing.Size(27, 13);
        	this.usedEntriesLabel.TabIndex = 13;
        	this.usedEntriesLabel.Text = "N/A";
        	// 
        	// dataEntriesLabel
        	// 
        	this.dataEntriesLabel.AutoSize = true;
        	this.dataEntriesLabel.Location = new System.Drawing.Point(96, 80);
        	this.dataEntriesLabel.Name = "dataEntriesLabel";
        	this.dataEntriesLabel.Size = new System.Drawing.Size(27, 13);
        	this.dataEntriesLabel.TabIndex = 11;
        	this.dataEntriesLabel.Text = "N/A";
        	// 
        	// label7
        	// 
        	this.label7.AutoSize = true;
        	this.label7.Location = new System.Drawing.Point(8, 184);
        	this.label7.Name = "label7";
        	this.label7.Size = new System.Drawing.Size(69, 13);
        	this.label7.TabIndex = 10;
        	this.label7.Text = "Used entries:";
        	// 
        	// label6
        	// 
        	this.label6.AutoSize = true;
        	this.label6.Location = new System.Drawing.Point(32, 152);
        	this.label6.Name = "label6";
        	this.label6.Size = new System.Drawing.Size(23, 13);
        	this.label6.TabIndex = 9;
        	this.label6.Text = "To:";
        	// 
        	// label5
        	// 
        	this.label5.AutoSize = true;
        	this.label5.Location = new System.Drawing.Point(8, 104);
        	this.label5.Name = "label5";
        	this.label5.Size = new System.Drawing.Size(76, 13);
        	this.label5.TabIndex = 8;
        	this.label5.Text = "Focus interval:";
        	// 
        	// label4
        	// 
        	this.label4.AutoSize = true;
        	this.label4.Location = new System.Drawing.Point(32, 128);
        	this.label4.Name = "label4";
        	this.label4.Size = new System.Drawing.Size(33, 13);
        	this.label4.TabIndex = 7;
        	this.label4.Text = "From:";
        	// 
        	// label2
        	// 
        	this.label2.AutoSize = true;
        	this.label2.Location = new System.Drawing.Point(8, 80);
        	this.label2.Name = "label2";
        	this.label2.Size = new System.Drawing.Size(67, 13);
        	this.label2.TabIndex = 5;
        	this.label2.Text = "Data entries:";
        	// 
        	// titleLabel
        	// 
        	this.titleLabel.AutoSize = true;
        	this.titleLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.titleLabel.Location = new System.Drawing.Point(96, 56);
        	this.titleLabel.Name = "titleLabel";
        	this.titleLabel.Size = new System.Drawing.Size(77, 13);
        	this.titleLabel.TabIndex = 4;
        	this.titleLabel.Text = "not selected";
        	// 
        	// label1
        	// 
        	this.label1.AutoSize = true;
        	this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.label1.Location = new System.Drawing.Point(8, 56);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(36, 13);
        	this.label1.TabIndex = 3;
        	this.label1.Text = "Title:";
        	// 
        	// copyButton
        	// 
        	this.copyButton.Enabled = false;
        	this.copyButton.Location = new System.Drawing.Point(136, 24);
        	this.copyButton.Name = "copyButton";
        	this.copyButton.Size = new System.Drawing.Size(56, 23);
        	this.copyButton.TabIndex = 2;
        	this.copyButton.Text = "Copy";
        	this.copyButton.UseVisualStyleBackColor = true;
        	this.copyButton.Click += new System.EventHandler(this.CopyButtonClick);
        	// 
        	// displayButton
        	// 
        	this.displayButton.Enabled = false;
        	this.displayButton.Location = new System.Drawing.Point(72, 24);
        	this.displayButton.Name = "displayButton";
        	this.displayButton.Size = new System.Drawing.Size(56, 23);
        	this.displayButton.TabIndex = 1;
        	this.displayButton.Text = "Display";
        	this.displayButton.UseVisualStyleBackColor = true;
        	this.displayButton.Click += new System.EventHandler(this.DisplayButtonClick);
        	// 
        	// loadButton
        	// 
        	this.loadButton.Location = new System.Drawing.Point(8, 24);
        	this.loadButton.Name = "loadButton";
        	this.loadButton.Size = new System.Drawing.Size(56, 23);
        	this.loadButton.TabIndex = 0;
        	this.loadButton.Text = "Load";
        	this.loadButton.UseVisualStyleBackColor = true;
        	this.loadButton.Click += new System.EventHandler(this.LoadButtonClick);
        	// 
        	// DataSelector
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.groupBox);
        	this.MinimumSize = new System.Drawing.Size(200, 0);
        	this.Name = "DataSelector";
        	this.Size = new System.Drawing.Size(200, 226);
        	this.groupBox.ResumeLayout(false);
        	this.groupBox.PerformLayout();
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.Button copyButton;
        private System.Windows.Forms.DateTimePicker toDtp;
        private System.Windows.Forms.Label usedEntriesLabel;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label titleLabel;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.Label dataEntriesLabel;
        private System.Windows.Forms.DateTimePicker fromDtp;
        private System.Windows.Forms.Button loadButton;
        private System.Windows.Forms.Button displayButton;
        private System.Windows.Forms.GroupBox groupBox;
    }
}
