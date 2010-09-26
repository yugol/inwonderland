/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/26/2009
 * Time: 10:27 PM
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
    partial class TitleSelectionForm
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
        	this.bottomPanel = new System.Windows.Forms.FlowLayoutPanel();
        	this.cancelButton = new System.Windows.Forms.Button();
        	this.okButton = new System.Windows.Forms.Button();
        	this.treeTitleSelector = new TradingCommon.Ui.TreeTitleSelector();
        	this.bottomPanel.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// bottomPanel
        	// 
        	this.bottomPanel.Controls.Add(this.cancelButton);
        	this.bottomPanel.Controls.Add(this.okButton);
        	this.bottomPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
        	this.bottomPanel.Location = new System.Drawing.Point(0, 293);
        	this.bottomPanel.Name = "bottomPanel";
        	this.bottomPanel.Padding = new System.Windows.Forms.Padding(5);
        	this.bottomPanel.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        	this.bottomPanel.Size = new System.Drawing.Size(215, 41);
        	this.bottomPanel.TabIndex = 0;
        	// 
        	// cancelButton
        	// 
        	this.cancelButton.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        	this.cancelButton.Location = new System.Drawing.Point(127, 8);
        	this.cancelButton.Name = "cancelButton";
        	this.cancelButton.Size = new System.Drawing.Size(75, 23);
        	this.cancelButton.TabIndex = 1;
        	this.cancelButton.Text = "Cancel";
        	this.cancelButton.UseVisualStyleBackColor = true;
        	// 
        	// okButton
        	// 
        	this.okButton.DialogResult = System.Windows.Forms.DialogResult.OK;
        	this.okButton.Enabled = false;
        	this.okButton.Location = new System.Drawing.Point(46, 8);
        	this.okButton.Name = "okButton";
        	this.okButton.Size = new System.Drawing.Size(75, 23);
        	this.okButton.TabIndex = 2;
        	this.okButton.Text = "OK";
        	this.okButton.UseVisualStyleBackColor = true;
        	// 
        	// treeTitleSelector
        	// 
        	this.treeTitleSelector.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.treeTitleSelector.Location = new System.Drawing.Point(0, 0);
        	this.treeTitleSelector.Name = "treeTitleSelector";
        	this.treeTitleSelector.Size = new System.Drawing.Size(215, 293);
        	this.treeTitleSelector.TabIndex = 1;
        	this.treeTitleSelector.TitleSelected += new TradingCommon.Ui.TitleSelectedHandler(this.TreeTitleSelectorTitleSelected);
        	// 
        	// TitleSelectionForm
        	// 
        	this.AcceptButton = this.okButton;
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.CancelButton = this.cancelButton;
        	this.ClientSize = new System.Drawing.Size(215, 334);
        	this.Controls.Add(this.treeTitleSelector);
        	this.Controls.Add(this.bottomPanel);
        	this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        	this.MaximizeBox = false;
        	this.MinimizeBox = false;
        	this.Name = "TitleSelectionForm";
        	this.Text = "Select Title";
        	this.bottomPanel.ResumeLayout(false);
        	this.ResumeLayout(false);
        }
        private TradingCommon.Ui.TreeTitleSelector treeTitleSelector;
        private System.Windows.Forms.Button okButton;
        private System.Windows.Forms.Button cancelButton;
        private System.Windows.Forms.FlowLayoutPanel bottomPanel;
    }
}
