/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 8/30/2009
 * Time: 9:21 PM
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
    partial class FileContentControl
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
        	this.topPanel = new System.Windows.Forms.Panel();
        	this.fileNameBox = new System.Windows.Forms.TextBox();
        	this.bottomPanel = new System.Windows.Forms.Panel();
        	this.saveButton = new System.Windows.Forms.Button();
        	this.contentBox = new System.Windows.Forms.TextBox();
        	this.topPanel.SuspendLayout();
        	this.bottomPanel.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// topPanel
        	// 
        	this.topPanel.Controls.Add(this.fileNameBox);
        	this.topPanel.Dock = System.Windows.Forms.DockStyle.Top;
        	this.topPanel.Location = new System.Drawing.Point(0, 0);
        	this.topPanel.Name = "topPanel";
        	this.topPanel.Size = new System.Drawing.Size(242, 24);
        	this.topPanel.TabIndex = 0;
        	// 
        	// fileNameBox
        	// 
        	this.fileNameBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.fileNameBox.Location = new System.Drawing.Point(0, 0);
        	this.fileNameBox.Name = "fileNameBox";
        	this.fileNameBox.Size = new System.Drawing.Size(242, 20);
        	this.fileNameBox.TabIndex = 0;
        	// 
        	// bottomPanel
        	// 
        	this.bottomPanel.Controls.Add(this.saveButton);
        	this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
        	this.bottomPanel.Location = new System.Drawing.Point(0, 125);
        	this.bottomPanel.Name = "bottomPanel";
        	this.bottomPanel.Padding = new System.Windows.Forms.Padding(6);
        	this.bottomPanel.Size = new System.Drawing.Size(242, 34);
        	this.bottomPanel.TabIndex = 1;
        	// 
        	// saveButton
        	// 
        	this.saveButton.Dock = System.Windows.Forms.DockStyle.Right;
        	this.saveButton.Location = new System.Drawing.Point(161, 6);
        	this.saveButton.Name = "saveButton";
        	this.saveButton.Size = new System.Drawing.Size(75, 22);
        	this.saveButton.TabIndex = 0;
        	this.saveButton.Text = "Save";
        	this.saveButton.UseVisualStyleBackColor = true;
        	this.saveButton.Click += new System.EventHandler(this.SaveButtonClick);
        	// 
        	// contentBox
        	// 
        	this.contentBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.contentBox.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.contentBox.Location = new System.Drawing.Point(0, 24);
        	this.contentBox.Multiline = true;
        	this.contentBox.Name = "contentBox";
        	this.contentBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        	this.contentBox.Size = new System.Drawing.Size(242, 101);
        	this.contentBox.TabIndex = 2;
        	this.contentBox.WordWrap = false;
        	// 
        	// FileContentControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.contentBox);
        	this.Controls.Add(this.bottomPanel);
        	this.Controls.Add(this.topPanel);
        	this.Name = "FileContentControl";
        	this.Size = new System.Drawing.Size(242, 159);
        	this.topPanel.ResumeLayout(false);
        	this.topPanel.PerformLayout();
        	this.bottomPanel.ResumeLayout(false);
        	this.ResumeLayout(false);
        	this.PerformLayout();
        }
        private System.Windows.Forms.TextBox contentBox;
        private System.Windows.Forms.Button saveButton;
        private System.Windows.Forms.Panel bottomPanel;
        private System.Windows.Forms.TextBox fileNameBox;
        private System.Windows.Forms.Panel topPanel;
    }
}
