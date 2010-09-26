namespace TradingDataCenter.Ui
{
    partial class ProgressReporter
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
        	this.message = new System.Windows.Forms.Label();
        	this.progress = new System.Windows.Forms.ProgressBar();
        	this.abort = new System.Windows.Forms.Button();
        	this.SuspendLayout();
        	// 
        	// message
        	// 
        	this.message.AutoSize = true;
        	this.message.Location = new System.Drawing.Point(12, 21);
        	this.message.Name = "message";
        	this.message.Size = new System.Drawing.Size(50, 13);
        	this.message.TabIndex = 0;
        	this.message.Text = "Message";
        	// 
        	// progress
        	// 
        	this.progress.Location = new System.Drawing.Point(15, 52);
        	this.progress.Name = "progress";
        	this.progress.Size = new System.Drawing.Size(439, 20);
        	this.progress.TabIndex = 1;
        	// 
        	// abort
        	// 
        	this.abort.Location = new System.Drawing.Point(376, 87);
        	this.abort.Name = "abort";
        	this.abort.Size = new System.Drawing.Size(75, 23);
        	this.abort.TabIndex = 2;
        	this.abort.Text = "Abort";
        	this.abort.UseVisualStyleBackColor = true;
        	this.abort.Click += new System.EventHandler(this.abort_Click);
        	// 
        	// ProgressReporter
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(463, 122);
        	this.ControlBox = false;
        	this.Controls.Add(this.abort);
        	this.Controls.Add(this.progress);
        	this.Controls.Add(this.message);
        	this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        	this.Name = "ProgressReporter";
        	this.ShowInTaskbar = false;
        	this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        	this.Text = "ProgressReporter";
        	this.ResumeLayout(false);
        	this.PerformLayout();
        }

        #endregion

        private System.Windows.Forms.Label message;
        private System.Windows.Forms.ProgressBar progress;
        private System.Windows.Forms.Button abort;
    }
}