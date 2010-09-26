namespace TradingAgent.Ui
{
    partial class AgentForm
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
        	System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AgentForm));
        	this.statusStrip = new System.Windows.Forms.StatusStrip();
        	this.modeLabel = new System.Windows.Forms.ToolStripStatusLabel();
        	this.timeLabel = new System.Windows.Forms.ToolStripStatusLabel();
        	this.formTimer = new System.Windows.Forms.Timer(this.components);
        	this.notifyIcon = new System.Windows.Forms.NotifyIcon(this.components);
        	this.mainMenu = new System.Windows.Forms.MenuStrip();
        	this.applicationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.optionsToolStripMenuItem1 = new System.Windows.Forms.ToolStripMenuItem();
        	this.manualControlToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.drawOnlyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.transactionsPage = new System.Windows.Forms.TabPage();
        	this.placeOrderPage = new System.Windows.Forms.TabPage();
        	this.orderPlaceBrowserPanel = new System.Windows.Forms.Panel();
        	this.orderPlaceControlPanel = new System.Windows.Forms.Panel();
        	this.tempBrowserPanel = new System.Windows.Forms.Panel();
        	this.portofolioPage = new System.Windows.Forms.TabPage();
        	this.homePage = new System.Windows.Forms.TabPage();
        	this.traderBPage = new System.Windows.Forms.TabPage();
        	this.traderBControl = new TradingAgent.Ui.TraderControl();
        	this.traderAPage = new System.Windows.Forms.TabPage();
        	this.traderAControl = new TradingAgent.Ui.TraderControl();
        	this.logPage = new System.Windows.Forms.TabPage();
        	this.logBox = new System.Windows.Forms.TextBox();
        	this.allTabs = new System.Windows.Forms.TabControl();
        	this.statusStrip.SuspendLayout();
        	this.mainMenu.SuspendLayout();
        	this.placeOrderPage.SuspendLayout();
        	this.orderPlaceControlPanel.SuspendLayout();
        	this.traderBPage.SuspendLayout();
        	this.traderAPage.SuspendLayout();
        	this.logPage.SuspendLayout();
        	this.allTabs.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// statusStrip
        	// 
        	this.statusStrip.GripMargin = new System.Windows.Forms.Padding(1);
        	this.statusStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.modeLabel,
        	        	        	this.timeLabel});
        	this.statusStrip.Location = new System.Drawing.Point(0, 744);
        	this.statusStrip.Name = "statusStrip";
        	this.statusStrip.Size = new System.Drawing.Size(967, 22);
        	this.statusStrip.TabIndex = 1;
        	this.statusStrip.Text = "statusStrip1";
        	// 
        	// modeLabel
        	// 
        	this.modeLabel.Name = "modeLabel";
        	this.modeLabel.Size = new System.Drawing.Size(33, 17);
        	this.modeLabel.Text = "Mode";
        	this.modeLabel.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
        	// 
        	// timeLabel
        	// 
        	this.timeLabel.Name = "timeLabel";
        	this.timeLabel.Size = new System.Drawing.Size(919, 17);
        	this.timeLabel.Spring = true;
        	this.timeLabel.Text = "00:00:00";
        	this.timeLabel.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
        	// 
        	// formTimer
        	// 
        	this.formTimer.Interval = 900;
        	this.formTimer.Tick += new System.EventHandler(this.formTimer_Tick);
        	// 
        	// notifyIcon
        	// 
        	this.notifyIcon.Icon = ((System.Drawing.Icon)(resources.GetObject("notifyIcon.Icon")));
        	this.notifyIcon.Text = "Trading Agent";
        	this.notifyIcon.MouseClick += new System.Windows.Forms.MouseEventHandler(this.notifyIcon_MouseClick);
        	// 
        	// mainMenu
        	// 
        	this.mainMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.applicationToolStripMenuItem,
        	        	        	this.optionsToolStripMenuItem1});
        	this.mainMenu.Location = new System.Drawing.Point(0, 0);
        	this.mainMenu.Name = "mainMenu";
        	this.mainMenu.Size = new System.Drawing.Size(967, 24);
        	this.mainMenu.TabIndex = 2;
        	this.mainMenu.Text = "Main menu";
        	// 
        	// applicationToolStripMenuItem
        	// 
        	this.applicationToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.exitToolStripMenuItem});
        	this.applicationToolStripMenuItem.Name = "applicationToolStripMenuItem";
        	this.applicationToolStripMenuItem.Size = new System.Drawing.Size(71, 20);
        	this.applicationToolStripMenuItem.Text = "Application";
        	// 
        	// exitToolStripMenuItem
        	// 
        	this.exitToolStripMenuItem.Name = "exitToolStripMenuItem";
        	this.exitToolStripMenuItem.Size = new System.Drawing.Size(92, 22);
        	this.exitToolStripMenuItem.Text = "Exit";
        	this.exitToolStripMenuItem.Click += new System.EventHandler(this.exitToolStripMenuItem_Click);
        	// 
        	// optionsToolStripMenuItem1
        	// 
        	this.optionsToolStripMenuItem1.CheckOnClick = true;
        	this.optionsToolStripMenuItem1.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.manualControlToolStripMenuItem,
        	        	        	this.drawOnlyToolStripMenuItem});
        	this.optionsToolStripMenuItem1.Name = "optionsToolStripMenuItem1";
        	this.optionsToolStripMenuItem1.Size = new System.Drawing.Size(56, 20);
        	this.optionsToolStripMenuItem1.Text = "Options";
        	// 
        	// manualControlToolStripMenuItem
        	// 
        	this.manualControlToolStripMenuItem.CheckOnClick = true;
        	this.manualControlToolStripMenuItem.Name = "manualControlToolStripMenuItem";
        	this.manualControlToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
        	this.manualControlToolStripMenuItem.Text = "Manual control";
        	this.manualControlToolStripMenuItem.Click += new System.EventHandler(this.manualControlToolStripMenuItem_Click);
        	// 
        	// drawOnlyToolStripMenuItem
        	// 
        	this.drawOnlyToolStripMenuItem.CheckOnClick = true;
        	this.drawOnlyToolStripMenuItem.Name = "drawOnlyToolStripMenuItem";
        	this.drawOnlyToolStripMenuItem.Size = new System.Drawing.Size(144, 22);
        	this.drawOnlyToolStripMenuItem.Text = "Draw only";
        	this.drawOnlyToolStripMenuItem.Click += new System.EventHandler(this.drawOnlyToolStripMenuItem_Click);
        	// 
        	// transactionsPage
        	// 
        	this.transactionsPage.Location = new System.Drawing.Point(4, 22);
        	this.transactionsPage.Name = "transactionsPage";
        	this.transactionsPage.Size = new System.Drawing.Size(959, 694);
        	this.transactionsPage.TabIndex = 9;
        	this.transactionsPage.Text = "Transactions page";
        	this.transactionsPage.UseVisualStyleBackColor = true;
        	// 
        	// placeOrderPage
        	// 
        	this.placeOrderPage.Controls.Add(this.orderPlaceBrowserPanel);
        	this.placeOrderPage.Controls.Add(this.orderPlaceControlPanel);
        	this.placeOrderPage.Location = new System.Drawing.Point(4, 22);
        	this.placeOrderPage.Name = "placeOrderPage";
        	this.placeOrderPage.Size = new System.Drawing.Size(959, 694);
        	this.placeOrderPage.TabIndex = 11;
        	this.placeOrderPage.Text = "Place order page";
        	this.placeOrderPage.UseVisualStyleBackColor = true;
        	// 
        	// orderPlaceBrowserPanel
        	// 
        	this.orderPlaceBrowserPanel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.orderPlaceBrowserPanel.Location = new System.Drawing.Point(0, 0);
        	this.orderPlaceBrowserPanel.Name = "orderPlaceBrowserPanel";
        	this.orderPlaceBrowserPanel.Size = new System.Drawing.Size(959, 694);
        	this.orderPlaceBrowserPanel.TabIndex = 0;
        	// 
        	// orderPlaceControlPanel
        	// 
        	this.orderPlaceControlPanel.Controls.Add(this.tempBrowserPanel);
        	this.orderPlaceControlPanel.Dock = System.Windows.Forms.DockStyle.Right;
        	this.orderPlaceControlPanel.Location = new System.Drawing.Point(959, 0);
        	this.orderPlaceControlPanel.Name = "orderPlaceControlPanel";
        	this.orderPlaceControlPanel.Size = new System.Drawing.Size(0, 694);
        	this.orderPlaceControlPanel.TabIndex = 1;
        	// 
        	// tempBrowserPanel
        	// 
        	this.tempBrowserPanel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.tempBrowserPanel.Location = new System.Drawing.Point(0, 0);
        	this.tempBrowserPanel.Name = "tempBrowserPanel";
        	this.tempBrowserPanel.Size = new System.Drawing.Size(0, 694);
        	this.tempBrowserPanel.TabIndex = 2;
        	// 
        	// portofolioPage
        	// 
        	this.portofolioPage.Location = new System.Drawing.Point(4, 22);
        	this.portofolioPage.Name = "portofolioPage";
        	this.portofolioPage.Size = new System.Drawing.Size(959, 694);
        	this.portofolioPage.TabIndex = 8;
        	this.portofolioPage.Text = "Portofolio page";
        	this.portofolioPage.UseVisualStyleBackColor = true;
        	// 
        	// homePage
        	// 
        	this.homePage.Location = new System.Drawing.Point(4, 22);
        	this.homePage.Name = "homePage";
        	this.homePage.Size = new System.Drawing.Size(959, 694);
        	this.homePage.TabIndex = 7;
        	this.homePage.Text = "Home page";
        	this.homePage.UseVisualStyleBackColor = true;
        	// 
        	// traderBPage
        	// 
        	this.traderBPage.Controls.Add(this.traderBControl);
        	this.traderBPage.Location = new System.Drawing.Point(4, 22);
        	this.traderBPage.Name = "traderBPage";
        	this.traderBPage.Size = new System.Drawing.Size(959, 694);
        	this.traderBPage.TabIndex = 13;
        	this.traderBPage.Text = "Trader B";
        	this.traderBPage.UseVisualStyleBackColor = true;
        	// 
        	// auxTraderControl
        	// 
        	this.traderBControl.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.traderBControl.Location = new System.Drawing.Point(0, 0);
        	this.traderBControl.Name = "auxTraderControl";
        	this.traderBControl.Size = new System.Drawing.Size(959, 694);
        	this.traderBControl.TabIndex = 0;
        	this.traderBControl.Title = null;
        	this.traderBControl.Trader = null;
        	// 
        	// traderAPage
        	// 
        	this.traderAPage.Controls.Add(this.traderAControl);
        	this.traderAPage.Location = new System.Drawing.Point(4, 22);
        	this.traderAPage.Name = "traderAPage";
        	this.traderAPage.Size = new System.Drawing.Size(959, 694);
        	this.traderAPage.TabIndex = 12;
        	this.traderAPage.Text = "Trader A";
        	this.traderAPage.UseVisualStyleBackColor = true;
        	// 
        	// mainTraderControl
        	// 
        	this.traderAControl.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.traderAControl.Location = new System.Drawing.Point(0, 0);
        	this.traderAControl.Name = "mainTraderControl";
        	this.traderAControl.Size = new System.Drawing.Size(959, 694);
        	this.traderAControl.TabIndex = 0;
        	this.traderAControl.Title = null;
        	this.traderAControl.Trader = null;
        	// 
        	// logPage
        	// 
        	this.logPage.Controls.Add(this.logBox);
        	this.logPage.Location = new System.Drawing.Point(4, 22);
        	this.logPage.Name = "logPage";
        	this.logPage.Padding = new System.Windows.Forms.Padding(3);
        	this.logPage.Size = new System.Drawing.Size(959, 694);
        	this.logPage.TabIndex = 1;
        	this.logPage.Text = "Log";
        	this.logPage.UseVisualStyleBackColor = true;
        	// 
        	// logBox
        	// 
        	this.logBox.BackColor = System.Drawing.SystemColors.Window;
        	this.logBox.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.logBox.Font = new System.Drawing.Font("Courier New", 9.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.logBox.Location = new System.Drawing.Point(3, 3);
        	this.logBox.Multiline = true;
        	this.logBox.Name = "logBox";
        	this.logBox.ReadOnly = true;
        	this.logBox.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        	this.logBox.Size = new System.Drawing.Size(953, 688);
        	this.logBox.TabIndex = 0;
        	// 
        	// allTabs
        	// 
        	this.allTabs.Controls.Add(this.logPage);
        	this.allTabs.Controls.Add(this.traderAPage);
        	this.allTabs.Controls.Add(this.traderBPage);
        	this.allTabs.Controls.Add(this.homePage);
        	this.allTabs.Controls.Add(this.portofolioPage);
        	this.allTabs.Controls.Add(this.placeOrderPage);
        	this.allTabs.Controls.Add(this.transactionsPage);
        	this.allTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.allTabs.Location = new System.Drawing.Point(0, 24);
        	this.allTabs.Name = "allTabs";
        	this.allTabs.SelectedIndex = 0;
        	this.allTabs.Size = new System.Drawing.Size(967, 720);
        	this.allTabs.TabIndex = 0;
        	// 
        	// AgentForm
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(967, 766);
        	this.Controls.Add(this.allTabs);
        	this.Controls.Add(this.statusStrip);
        	this.Controls.Add(this.mainMenu);
        	this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        	this.MainMenuStrip = this.mainMenu;
        	this.Name = "AgentForm";
        	this.Text = "Trading Agent";
        	this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        	this.Shown += new System.EventHandler(this.TraderForm_Shown);
        	this.statusStrip.ResumeLayout(false);
        	this.statusStrip.PerformLayout();
        	this.mainMenu.ResumeLayout(false);
        	this.mainMenu.PerformLayout();
        	this.placeOrderPage.ResumeLayout(false);
        	this.orderPlaceControlPanel.ResumeLayout(false);
        	this.traderBPage.ResumeLayout(false);
        	this.traderAPage.ResumeLayout(false);
        	this.logPage.ResumeLayout(false);
        	this.logPage.PerformLayout();
        	this.allTabs.ResumeLayout(false);
        	this.ResumeLayout(false);
        	this.PerformLayout();
        }
        private System.Windows.Forms.TabPage traderBPage;
        private System.Windows.Forms.TabPage traderAPage;

        #endregion

        private System.Windows.Forms.StatusStrip statusStrip;
        private System.Windows.Forms.ToolStripStatusLabel modeLabel;
        private System.Windows.Forms.ToolStripStatusLabel timeLabel;
        private System.Windows.Forms.Timer formTimer;
        private System.Windows.Forms.NotifyIcon notifyIcon;
        private System.Windows.Forms.MenuStrip mainMenu;
        private System.Windows.Forms.ToolStripMenuItem applicationToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem optionsToolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem manualControlToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem drawOnlyToolStripMenuItem;
        private System.Windows.Forms.TabPage transactionsPage;
        private System.Windows.Forms.TabPage placeOrderPage;
        private System.Windows.Forms.Panel orderPlaceBrowserPanel;
        private System.Windows.Forms.Panel orderPlaceControlPanel;
        private System.Windows.Forms.Panel tempBrowserPanel;
        private System.Windows.Forms.TabPage portofolioPage;
        private System.Windows.Forms.TabPage homePage;
        private TraderControl traderBControl;
        private TraderControl traderAControl;
        private System.Windows.Forms.TabPage logPage;
        private System.Windows.Forms.TextBox logBox;
        private System.Windows.Forms.TabControl allTabs;
    }
}

