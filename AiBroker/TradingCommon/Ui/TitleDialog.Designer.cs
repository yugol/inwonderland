
namespace TradingCommon.Ui
{
    partial class TitleDialog
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
        	this.ok = new System.Windows.Forms.Button();
        	this.cancel = new System.Windows.Forms.Button();
        	this.titleSelector = new TradingCommon.Ui.ComboTickTitleSelector();
        	this.SuspendLayout();
        	// 
        	// ok
        	// 
        	this.ok.Location = new System.Drawing.Point(160, 64);
        	this.ok.Name = "ok";
        	this.ok.Size = new System.Drawing.Size(80, 24);
        	this.ok.TabIndex = 8;
        	this.ok.Text = "OK";
        	this.ok.UseVisualStyleBackColor = true;
        	this.ok.Click += new System.EventHandler(this.ok_Click);
        	// 
        	// cancel
        	// 
        	this.cancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        	this.cancel.Location = new System.Drawing.Point(264, 64);
        	this.cancel.Name = "cancel";
        	this.cancel.Size = new System.Drawing.Size(80, 24);
        	this.cancel.TabIndex = 9;
        	this.cancel.Text = "Cancel";
        	this.cancel.UseVisualStyleBackColor = true;
        	this.cancel.Click += new System.EventHandler(this.cancel_Click);
        	// 
        	// titleSelector
        	// 
        	this.titleSelector.Location = new System.Drawing.Point(0, 0);
        	this.titleSelector.Name = "titleSelector";
        	this.titleSelector.Size = new System.Drawing.Size(362, 50);
        	this.titleSelector.TabIndex = 10;
        	// 
        	// TitleDialog
        	// 
        	this.AcceptButton = this.ok;
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.CancelButton = this.cancel;
        	this.ClientSize = new System.Drawing.Size(368, 102);
        	this.ControlBox = false;
        	this.Controls.Add(this.titleSelector);
        	this.Controls.Add(this.cancel);
        	this.Controls.Add(this.ok);
        	this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
        	this.MaximizeBox = false;
        	this.MinimizeBox = false;
        	this.Name = "TitleDialog";
        	this.ShowInTaskbar = false;
        	this.Text = "Trading Simulator - Load Title";
        	this.ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.Button ok;
        private System.Windows.Forms.Button cancel;
        private ComboTickTitleSelector titleSelector;
    }
}
