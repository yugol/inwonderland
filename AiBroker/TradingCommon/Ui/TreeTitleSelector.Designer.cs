/*
 * Created by SharpDevelop.
 * User: Iulian
 * Date: 4/26/2009
 * Time: 10:19 PM
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
namespace TradingCommon.Ui
{
    partial class TreeTitleSelector
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
        	this.titleTree = new System.Windows.Forms.TreeView();
        	this.SuspendLayout();
        	// 
        	// titleTree
        	// 
        	this.titleTree.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.titleTree.Location = new System.Drawing.Point(0, 0);
        	this.titleTree.Name = "titleTree";
        	this.titleTree.Size = new System.Drawing.Size(244, 373);
        	this.titleTree.TabIndex = 0;
        	this.titleTree.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.TitleTreeAfterSelect);
        	// 
        	// TreeTitleSelector
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.titleTree);
        	this.Name = "TreeTitleSelector";
        	this.Size = new System.Drawing.Size(244, 373);
        	this.Load += new System.EventHandler(this.TreeTitleSelectorLoad);
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.TreeView titleTree;
    }
}
