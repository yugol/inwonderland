namespace TradingDataCenter.Ui
{
    partial class BmfmsFuturesRealtimeMonitor
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

        #region Component Designer generated code

        /// <summary> 
        /// Required method for Designer support - do not modify 
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
        	this.components = new System.ComponentModel.Container();
        	this.gbAll = new System.Windows.Forms.GroupBox();
        	this.pnlFill = new System.Windows.Forms.Panel();
        	this.tbLog = new System.Windows.Forms.TextBox();
        	this.pnlTop = new System.Windows.Forms.Panel();
        	this.tbUpdatedSymbols = new System.Windows.Forms.TextBox();
        	this.label2 = new System.Windows.Forms.Label();
        	this.btnAction = new System.Windows.Forms.Button();
        	this.label3 = new System.Windows.Forms.Label();
        	this.lblLastUpdateTime = new System.Windows.Forms.Label();
        	this.label1 = new System.Windows.Forms.Label();
        	this.updateTimer = new System.Windows.Forms.Timer(this.components);
        	this.gbAll.SuspendLayout();
        	this.pnlFill.SuspendLayout();
        	this.pnlTop.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// gbAll
        	// 
        	this.gbAll.Controls.Add(this.pnlFill);
        	this.gbAll.Controls.Add(this.pnlTop);
        	this.gbAll.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.gbAll.Location = new System.Drawing.Point(0, 0);
        	this.gbAll.Name = "gbAll";
        	this.gbAll.Size = new System.Drawing.Size(240, 240);
        	this.gbAll.TabIndex = 0;
        	this.gbAll.TabStop = false;
        	this.gbAll.Text = "Bmfms Futures Realtime Importer";
        	// 
        	// pnlFill
        	// 
        	this.pnlFill.Controls.Add(this.tbLog);
        	this.pnlFill.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.pnlFill.Location = new System.Drawing.Point(3, 152);
        	this.pnlFill.Name = "pnlFill";
        	this.pnlFill.Size = new System.Drawing.Size(234, 85);
        	this.pnlFill.TabIndex = 1;
        	// 
        	// tbLog
        	// 
        	this.tbLog.AcceptsReturn = true;
        	this.tbLog.BackColor = System.Drawing.SystemColors.Control;
        	this.tbLog.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.tbLog.Location = new System.Drawing.Point(0, 0);
        	this.tbLog.Multiline = true;
        	this.tbLog.Name = "tbLog";
        	this.tbLog.ReadOnly = true;
        	this.tbLog.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        	this.tbLog.Size = new System.Drawing.Size(234, 85);
        	this.tbLog.TabIndex = 0;
        	this.tbLog.WordWrap = false;
        	// 
        	// pnlTop
        	// 
        	this.pnlTop.Controls.Add(this.tbUpdatedSymbols);
        	this.pnlTop.Controls.Add(this.label2);
        	this.pnlTop.Controls.Add(this.btnAction);
        	this.pnlTop.Controls.Add(this.label3);
        	this.pnlTop.Controls.Add(this.lblLastUpdateTime);
        	this.pnlTop.Controls.Add(this.label1);
        	this.pnlTop.Dock = System.Windows.Forms.DockStyle.Top;
        	this.pnlTop.Location = new System.Drawing.Point(3, 16);
        	this.pnlTop.Name = "pnlTop";
        	this.pnlTop.Size = new System.Drawing.Size(234, 136);
        	this.pnlTop.TabIndex = 0;
        	// 
        	// tbUpdatedSymbols
        	// 
        	this.tbUpdatedSymbols.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
        	        	        	| System.Windows.Forms.AnchorStyles.Right)));
        	this.tbUpdatedSymbols.BackColor = System.Drawing.SystemColors.Control;
        	this.tbUpdatedSymbols.Location = new System.Drawing.Point(8, 80);
        	this.tbUpdatedSymbols.Name = "tbUpdatedSymbols";
        	this.tbUpdatedSymbols.ReadOnly = true;
        	this.tbUpdatedSymbols.Size = new System.Drawing.Size(216, 20);
        	this.tbUpdatedSymbols.TabIndex = 5;
        	this.tbUpdatedSymbols.WordWrap = false;
        	// 
        	// label2
        	// 
        	this.label2.AutoSize = true;
        	this.label2.Location = new System.Drawing.Point(8, 56);
        	this.label2.Name = "label2";
        	this.label2.Size = new System.Drawing.Size(91, 13);
        	this.label2.TabIndex = 4;
        	this.label2.Text = "Updated symbols:";
        	// 
        	// btnAction
        	// 
        	this.btnAction.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
        	this.btnAction.Location = new System.Drawing.Point(153, 8);
        	this.btnAction.Name = "btnAction";
        	this.btnAction.Size = new System.Drawing.Size(75, 23);
        	this.btnAction.TabIndex = 3;
        	this.btnAction.Text = "N/A";
        	this.btnAction.UseVisualStyleBackColor = true;
        	this.btnAction.Click += new System.EventHandler(this.btnAction_Click);
        	// 
        	// label3
        	// 
        	this.label3.AutoSize = true;
        	this.label3.Location = new System.Drawing.Point(8, 112);
        	this.label3.Name = "label3";
        	this.label3.Size = new System.Drawing.Size(28, 13);
        	this.label3.TabIndex = 2;
        	this.label3.Text = "Log:";
        	// 
        	// lblLastUpdateTime
        	// 
        	this.lblLastUpdateTime.AutoSize = true;
        	this.lblLastUpdateTime.Location = new System.Drawing.Point(8, 32);
        	this.lblLastUpdateTime.Name = "lblLastUpdateTime";
        	this.lblLastUpdateTime.Size = new System.Drawing.Size(106, 13);
        	this.lblLastUpdateTime.TabIndex = 1;
        	this.lblLastUpdateTime.Text = "0000-00-00 00:00:00";
        	// 
        	// label1
        	// 
        	this.label1.AutoSize = true;
        	this.label1.Location = new System.Drawing.Point(8, 8);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(88, 13);
        	this.label1.TabIndex = 0;
        	this.label1.Text = "Last update time:";
        	// 
        	// updateTimer
        	// 
        	this.updateTimer.Tick += new System.EventHandler(this.updateTimer_Tick);
        	// 
        	// BmfmsFuturesRealtimeMonitor
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.gbAll);
        	this.MinimumSize = new System.Drawing.Size(240, 240);
        	this.Name = "BmfmsFuturesRealtimeMonitor";
        	this.Size = new System.Drawing.Size(240, 240);
        	this.gbAll.ResumeLayout(false);
        	this.pnlFill.ResumeLayout(false);
        	this.pnlFill.PerformLayout();
        	this.pnlTop.ResumeLayout(false);
        	this.pnlTop.PerformLayout();
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.Timer updateTimer;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button btnAction;
        private System.Windows.Forms.TextBox tbLog;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label lblLastUpdateTime;
        private System.Windows.Forms.Panel pnlTop;
        private System.Windows.Forms.Panel pnlFill;
        private System.Windows.Forms.GroupBox gbAll;

        #endregion
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox tbUpdatedSymbols;
    }
}
