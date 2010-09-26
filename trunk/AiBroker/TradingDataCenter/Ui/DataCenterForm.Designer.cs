namespace TradingDataCenter.Ui
{
    partial class DataCenterForm
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
        	this.components = new System.ComponentModel.Container();
        	System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(DataCenterForm));
        	this.mainMenu = new System.Windows.Forms.MenuStrip();
        	this.monitorToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.importToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.eODREGSToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.futuresToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
        	this.ssifBrokerEODFuturesTicksToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.statusStrip = new System.Windows.Forms.StatusStrip();
        	this.timeLabel = new System.Windows.Forms.ToolStripStatusLabel();
        	this.allTabs = new System.Windows.Forms.TabControl();
        	this.outputPage = new System.Windows.Forms.TabPage();
        	this.tbOutput = new System.Windows.Forms.TextBox();
        	this.formTimer = new System.Windows.Forms.Timer(this.components);
        	this.notifyIcon = new System.Windows.Forms.NotifyIcon(this.components);
        	this.pnlLeft = new System.Windows.Forms.Panel();
        	this.bmfmsFuturesRealtimeImporter = new TradingDataCenter.Ui.BmfmsFuturesRealtimeMonitor();
        	this.mainMenu.SuspendLayout();
        	this.statusStrip.SuspendLayout();
        	this.allTabs.SuspendLayout();
        	this.outputPage.SuspendLayout();
        	this.pnlLeft.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// mainMenu
        	// 
        	this.mainMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.monitorToolStripMenuItem,
        	        	        	this.importToolStripMenuItem});
        	this.mainMenu.Location = new System.Drawing.Point(0, 0);
        	this.mainMenu.Name = "mainMenu";
        	this.mainMenu.Size = new System.Drawing.Size(591, 24);
        	this.mainMenu.TabIndex = 0;
        	// 
        	// monitorToolStripMenuItem
        	// 
        	this.monitorToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.exitToolStripMenuItem});
        	this.monitorToolStripMenuItem.Name = "monitorToolStripMenuItem";
        	this.monitorToolStripMenuItem.Size = new System.Drawing.Size(80, 20);
        	this.monitorToolStripMenuItem.Text = "Application";
        	// 
        	// exitToolStripMenuItem
        	// 
        	this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
        	this.exitToolStripMenuItem.Size = new System.Drawing.Size(92, 22);
        	this.exitToolStripMenuItem.Text = "Exit";
        	this.exitToolStripMenuItem.Click += new System.EventHandler(this.exitToolStripMenuItem_Click);
        	// 
        	// importToolStripMenuItem
        	// 
        	this.importToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.ssifBrokerEODFuturesTicksToolStripMenuItem,
        	        	        	this.futuresToolStripMenuItem1,
        	        	        	this.eODREGSToolStripMenuItem});
        	this.importToolStripMenuItem.Name = "importToolStripMenuItem";
        	this.importToolStripMenuItem.Size = new System.Drawing.Size(55, 20);
        	this.importToolStripMenuItem.Text = "Import";
        	// 
        	// eODREGSToolStripMenuItem
        	// 
        	this.eODREGSToolStripMenuItem.Name = "eODREGSToolStripMenuItem";
        	this.eODREGSToolStripMenuItem.Size = new System.Drawing.Size(171, 22);
        	this.eODREGSToolStripMenuItem.Text = "3. EOD REGS";
        	this.eODREGSToolStripMenuItem.Click += new System.EventHandler(this.eODREGSToolStripMenuItem_Click);
        	// 
        	// futuresToolStripMenuItem1
        	// 
        	this.futuresToolStripMenuItem1.Name = "futuresToolStripMenuItem1";
        	this.futuresToolStripMenuItem1.Size = new System.Drawing.Size(171, 22);
        	this.futuresToolStripMenuItem1.Text = "2. EOD Futures";
        	this.futuresToolStripMenuItem1.Click += new System.EventHandler(this.futuresToolStripMenuItem1_Click);
        	// 
        	// ssifBrokerEODFuturesTicksToolStripMenuItem
        	// 
        	this.ssifBrokerEODFuturesTicksToolStripMenuItem.Name = "ssifBrokerEODFuturesTicksToolStripMenuItem";
        	this.ssifBrokerEODFuturesTicksToolStripMenuItem.Size = new System.Drawing.Size(171, 22);
        	this.ssifBrokerEODFuturesTicksToolStripMenuItem.Text = "1. Intraday Futures";
        	this.ssifBrokerEODFuturesTicksToolStripMenuItem.Click += new System.EventHandler(this.intradayFuturesToolStripMenuItem_Click);
        	// 
        	// statusStrip
        	// 
        	this.statusStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.timeLabel});
        	this.statusStrip.Location = new System.Drawing.Point(0, 357);
        	this.statusStrip.Name = "statusStrip";
        	this.statusStrip.Size = new System.Drawing.Size(591, 22);
        	this.statusStrip.TabIndex = 1;
        	this.statusStrip.Text = "statusStrip1";
        	// 
        	// timeLabel
        	// 
        	this.timeLabel.Name = "timeLabel";
        	this.timeLabel.Size = new System.Drawing.Size(576, 17);
        	this.timeLabel.Spring = true;
        	this.timeLabel.Text = "00:00:00";
        	this.timeLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        	// 
        	// allTabs
        	// 
        	this.allTabs.Controls.Add(this.outputPage);
        	this.allTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.allTabs.Location = new System.Drawing.Point(272, 24);
        	this.allTabs.Name = "allTabs";
        	this.allTabs.SelectedIndex = 0;
        	this.allTabs.Size = new System.Drawing.Size(319, 333);
        	this.allTabs.TabIndex = 2;
        	// 
        	// outputPage
        	// 
        	this.outputPage.Controls.Add(this.tbOutput);
        	this.outputPage.Location = new System.Drawing.Point(4, 22);
        	this.outputPage.Name = "outputPage";
        	this.outputPage.Size = new System.Drawing.Size(311, 307);
        	this.outputPage.TabIndex = 0;
        	this.outputPage.Text = "Output";
        	this.outputPage.UseVisualStyleBackColor = true;
        	// 
        	// tbOutput
        	// 
        	this.tbOutput.BackColor = System.Drawing.SystemColors.Window;
        	this.tbOutput.BorderStyle = System.Windows.Forms.BorderStyle.None;
        	this.tbOutput.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.tbOutput.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.tbOutput.Location = new System.Drawing.Point(0, 0);
        	this.tbOutput.Multiline = true;
        	this.tbOutput.Name = "tbOutput";
        	this.tbOutput.ReadOnly = true;
        	this.tbOutput.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
        	this.tbOutput.Size = new System.Drawing.Size(311, 307);
        	this.tbOutput.TabIndex = 0;
        	this.tbOutput.WordWrap = false;
        	// 
        	// formTimer
        	// 
        	this.formTimer.Interval = 900;
        	this.formTimer.Tick += new System.EventHandler(this.formTimer_Tick);
        	// 
        	// notifyIcon
        	// 
        	this.notifyIcon.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon.Icon")));
        	this.notifyIcon.Text = "Trading Data Center";
        	this.notifyIcon.MouseClick += new System.Windows.Forms.MouseEventHandler(this.notifyIcon_MouseClick);
        	// 
        	// pnlLeft
        	// 
        	this.pnlLeft.Controls.Add(this.bmfmsFuturesRealtimeImporter);
        	this.pnlLeft.Dock = System.Windows.Forms.DockStyle.Left;
        	this.pnlLeft.Location = new System.Drawing.Point(0, 24);
        	this.pnlLeft.Name = "pnlLeft";
        	this.pnlLeft.Size = new System.Drawing.Size(272, 333);
        	this.pnlLeft.TabIndex = 3;
        	// 
        	// bmfmsFuturesRealtimeImporter
        	// 
        	this.bmfmsFuturesRealtimeImporter.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.bmfmsFuturesRealtimeImporter.Location = new System.Drawing.Point(0, 0);
        	this.bmfmsFuturesRealtimeImporter.MinimumSize = new System.Drawing.Size(240, 200);
        	this.bmfmsFuturesRealtimeImporter.Name = "bmfmsFuturesRealtimeImporter";
        	this.bmfmsFuturesRealtimeImporter.Padding = new System.Windows.Forms.Padding(8);
        	this.bmfmsFuturesRealtimeImporter.Size = new System.Drawing.Size(272, 333);
        	this.bmfmsFuturesRealtimeImporter.TabIndex = 0;
        	// 
        	// DataCenterForm
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(591, 379);
        	this.Controls.Add(this.allTabs);
        	this.Controls.Add(this.pnlLeft);
        	this.Controls.Add(this.statusStrip);
        	this.Controls.Add(this.mainMenu);
        	this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        	this.MainMenuStrip = this.mainMenu;
        	this.Name = "DataCenterForm";
        	this.Text = "Trading Data Center";
        	this.Shown += new System.EventHandler(this.DataCenterForm_Shown);
        	this.mainMenu.ResumeLayout(false);
        	this.mainMenu.PerformLayout();
        	this.statusStrip.ResumeLayout(false);
        	this.statusStrip.PerformLayout();
        	this.allTabs.ResumeLayout(false);
        	this.outputPage.ResumeLayout(false);
        	this.outputPage.PerformLayout();
        	this.pnlLeft.ResumeLayout(false);
        	this.ResumeLayout(false);
        	this.PerformLayout();
        }
        private System.Windows.Forms.TextBox tbOutput;
        private System.Windows.Forms.ToolStripMenuItem ssifBrokerEODFuturesTicksToolStripMenuItem;
        private TradingDataCenter.Ui.BmfmsFuturesRealtimeMonitor bmfmsFuturesRealtimeImporter;
        private System.Windows.Forms.Panel pnlLeft;

        #endregion

        private System.Windows.Forms.MenuStrip mainMenu;
        private System.Windows.Forms.StatusStrip statusStrip;
        private System.Windows.Forms.ToolStripMenuItem importToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem monitorToolStripMenuItem;
        private System.Windows.Forms.TabControl allTabs;
        private System.Windows.Forms.ToolStripStatusLabel timeLabel;
        private System.Windows.Forms.Timer formTimer;
        private System.Windows.Forms.NotifyIcon notifyIcon;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem futuresToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem eODREGSToolStripMenuItem;
        private System.Windows.Forms.TabPage outputPage;
    }
}

