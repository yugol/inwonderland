namespace TradingAgent.Ui
{
    partial class TraderControl
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
        	this.traderTabs = new System.Windows.Forms.TabControl();
        	this.chartTab = new System.Windows.Forms.TabPage();
        	this.chartPanel = new System.Windows.Forms.Panel();
        	this.chart = new ZedGraph.ZedGraphControl();
        	this.controlPanel = new System.Windows.Forms.Panel();
        	this.addOrder = new TradingAgent.Ui.AddOrderControl();
        	this.realtimeData = new TradingAgent.Ui.RealtimeDataControl();
        	this.statusTab = new System.Windows.Forms.TabPage();
        	this.statusBrowser = new System.Windows.Forms.WebBrowser();
        	this.marketTab = new System.Windows.Forms.TabPage();
        	this.traderTabs.SuspendLayout();
        	this.chartTab.SuspendLayout();
        	this.chartPanel.SuspendLayout();
        	this.controlPanel.SuspendLayout();
        	this.statusTab.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// traderTabs
        	// 
        	this.traderTabs.Controls.Add(this.chartTab);
        	this.traderTabs.Controls.Add(this.statusTab);
        	this.traderTabs.Controls.Add(this.marketTab);
        	this.traderTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.traderTabs.Location = new System.Drawing.Point(0, 0);
        	this.traderTabs.Name = "traderTabs";
        	this.traderTabs.SelectedIndex = 0;
        	this.traderTabs.Size = new System.Drawing.Size(685, 527);
        	this.traderTabs.TabIndex = 0;
        	// 
        	// chartTab
        	// 
        	this.chartTab.Controls.Add(this.chartPanel);
        	this.chartTab.Location = new System.Drawing.Point(4, 22);
        	this.chartTab.Name = "chartTab";
        	this.chartTab.Padding = new System.Windows.Forms.Padding(3);
        	this.chartTab.Size = new System.Drawing.Size(677, 501);
        	this.chartTab.TabIndex = 0;
        	this.chartTab.Text = "Chart";
        	this.chartTab.UseVisualStyleBackColor = true;
        	// 
        	// chartPanel
        	// 
        	this.chartPanel.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        	this.chartPanel.Controls.Add(this.chart);
        	this.chartPanel.Controls.Add(this.controlPanel);
        	this.chartPanel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.chartPanel.Location = new System.Drawing.Point(3, 3);
        	this.chartPanel.Name = "chartPanel";
        	this.chartPanel.Size = new System.Drawing.Size(671, 495);
        	this.chartPanel.TabIndex = 0;
        	// 
        	// chart
        	// 
        	this.chart.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.chart.IsShowPointValues = true;
        	this.chart.Location = new System.Drawing.Point(0, 0);
        	this.chart.Name = "chart";
        	this.chart.PointValueFormat = "0.####";
        	this.chart.ScrollGrace = 0;
        	this.chart.ScrollMaxX = 0;
        	this.chart.ScrollMaxY = 0;
        	this.chart.ScrollMaxY2 = 0;
        	this.chart.ScrollMinX = 0;
        	this.chart.ScrollMinY = 0;
        	this.chart.ScrollMinY2 = 0;
        	this.chart.Size = new System.Drawing.Size(469, 493);
        	this.chart.TabIndex = 0;
        	// 
        	// controlPanel
        	// 
        	this.controlPanel.Controls.Add(this.addOrder);
        	this.controlPanel.Controls.Add(this.realtimeData);
        	this.controlPanel.Dock = System.Windows.Forms.DockStyle.Right;
        	this.controlPanel.Location = new System.Drawing.Point(469, 0);
        	this.controlPanel.Name = "controlPanel";
        	this.controlPanel.Size = new System.Drawing.Size(200, 493);
        	this.controlPanel.TabIndex = 1;
        	// 
        	// addOrder
        	// 
        	this.addOrder.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        	this.addOrder.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.addOrder.Enabled = false;
        	this.addOrder.Location = new System.Drawing.Point(0, 223);
        	this.addOrder.MinimumSize = new System.Drawing.Size(194, 236);
        	this.addOrder.Name = "addOrder";
        	this.addOrder.Size = new System.Drawing.Size(200, 270);
        	this.addOrder.Symbol = "MMMMMM-MMMMM";
        	this.addOrder.TabIndex = 1;
        	this.addOrder.OrderPlaced += new System.EventHandler(this.addOrder_OrderPlaced);
        	// 
        	// realtimeData
        	// 
        	this.realtimeData.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
        	this.realtimeData.Dock = System.Windows.Forms.DockStyle.Top;
        	this.realtimeData.Location = new System.Drawing.Point(0, 0);
        	this.realtimeData.MinimumSize = new System.Drawing.Size(120, 150);
        	this.realtimeData.Name = "realtimeData";
        	this.realtimeData.Padding = new System.Windows.Forms.Padding(10, 15, 10, 15);
        	this.realtimeData.Size = new System.Drawing.Size(200, 223);
        	this.realtimeData.TabIndex = 0;
        	// 
        	// statusTab
        	// 
        	this.statusTab.Controls.Add(this.statusBrowser);
        	this.statusTab.Location = new System.Drawing.Point(4, 22);
        	this.statusTab.Name = "statusTab";
        	this.statusTab.Padding = new System.Windows.Forms.Padding(3);
        	this.statusTab.Size = new System.Drawing.Size(677, 501);
        	this.statusTab.TabIndex = 1;
        	this.statusTab.Text = "Status";
        	this.statusTab.UseVisualStyleBackColor = true;
        	// 
        	// statusBrowser
        	// 
        	this.statusBrowser.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.statusBrowser.Location = new System.Drawing.Point(3, 3);
        	this.statusBrowser.MinimumSize = new System.Drawing.Size(20, 20);
        	this.statusBrowser.Name = "statusBrowser";
        	this.statusBrowser.Size = new System.Drawing.Size(671, 495);
        	this.statusBrowser.TabIndex = 0;
        	// 
        	// marketTab
        	// 
        	this.marketTab.Location = new System.Drawing.Point(4, 22);
        	this.marketTab.Name = "marketTab";
        	this.marketTab.Size = new System.Drawing.Size(677, 501);
        	this.marketTab.TabIndex = 2;
        	this.marketTab.Text = "Market";
        	this.marketTab.UseVisualStyleBackColor = true;
        	// 
        	// TraderControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.traderTabs);
        	this.Name = "TraderControl";
        	this.Size = new System.Drawing.Size(685, 527);
        	this.traderTabs.ResumeLayout(false);
        	this.chartTab.ResumeLayout(false);
        	this.chartPanel.ResumeLayout(false);
        	this.controlPanel.ResumeLayout(false);
        	this.statusTab.ResumeLayout(false);
        	this.ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.TabControl traderTabs;
        private System.Windows.Forms.TabPage chartTab;
        private System.Windows.Forms.Panel chartPanel;
        private System.Windows.Forms.TabPage statusTab;
        private ZedGraph.ZedGraphControl chart;
        private System.Windows.Forms.Panel controlPanel;
        private RealtimeDataControl realtimeData;
        private AddOrderControl addOrder;
        private System.Windows.Forms.WebBrowser statusBrowser;
        private System.Windows.Forms.TabPage marketTab;
    }
}
