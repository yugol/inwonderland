
namespace TradingSimulator.Ui
{
    partial class SolutionReportControl
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
        	this.components = new System.ComponentModel.Container();
        	this.solutionTabs = new System.Windows.Forms.TabControl();
        	this.solutionSummaryPage = new System.Windows.Forms.TabPage();
        	this.solutionSummary = new System.Windows.Forms.TextBox();
        	this.solutionEquityPage = new System.Windows.Forms.TabPage();
        	this.accountEquity = new ZedGraph.ZedGraphControl();
        	this.solutionProfitPage = new System.Windows.Forms.TabPage();
        	this.statisticsSectionControl = new TradingSimulator.Ui.StatisticalReportControl();
        	this.statisticsSectionTopPanel = new System.Windows.Forms.Panel();
        	this.statisticsSectionSelector = new System.Windows.Forms.ComboBox();
        	this.solutionStatisticsSectionLabelPanel = new System.Windows.Forms.Panel();
        	this.statisticsSectionLabel = new System.Windows.Forms.Label();
        	this.solutionTradesPage = new System.Windows.Forms.TabPage();
        	this.tradesList = new TradingSimulator.Ui.TradesListControl();
        	this.solutionOrdersPage = new System.Windows.Forms.TabPage();
        	this.orderBrowser = new System.Windows.Forms.WebBrowser();
        	this.solutionTabs.SuspendLayout();
        	this.solutionSummaryPage.SuspendLayout();
        	this.solutionEquityPage.SuspendLayout();
        	this.solutionProfitPage.SuspendLayout();
        	this.statisticsSectionTopPanel.SuspendLayout();
        	this.solutionStatisticsSectionLabelPanel.SuspendLayout();
        	this.solutionTradesPage.SuspendLayout();
        	this.solutionOrdersPage.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// solutionTabs
        	// 
        	this.solutionTabs.Controls.Add(this.solutionSummaryPage);
        	this.solutionTabs.Controls.Add(this.solutionEquityPage);
        	this.solutionTabs.Controls.Add(this.solutionProfitPage);
        	this.solutionTabs.Controls.Add(this.solutionTradesPage);
        	this.solutionTabs.Controls.Add(this.solutionOrdersPage);
        	this.solutionTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.solutionTabs.Enabled = false;
        	this.solutionTabs.Location = new System.Drawing.Point(0, 0);
        	this.solutionTabs.Name = "solutionTabs";
        	this.solutionTabs.SelectedIndex = 0;
        	this.solutionTabs.Size = new System.Drawing.Size(621, 440);
        	this.solutionTabs.TabIndex = 8;
        	// 
        	// solutionSummaryPage
        	// 
        	this.solutionSummaryPage.Controls.Add(this.solutionSummary);
        	this.solutionSummaryPage.Location = new System.Drawing.Point(4, 22);
        	this.solutionSummaryPage.Name = "solutionSummaryPage";
        	this.solutionSummaryPage.Size = new System.Drawing.Size(613, 414);
        	this.solutionSummaryPage.TabIndex = 3;
        	this.solutionSummaryPage.Text = "Summary";
        	this.solutionSummaryPage.UseVisualStyleBackColor = true;
        	// 
        	// solutionSummary
        	// 
        	this.solutionSummary.BackColor = System.Drawing.SystemColors.Window;
        	this.solutionSummary.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.solutionSummary.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.solutionSummary.Location = new System.Drawing.Point(0, 0);
        	this.solutionSummary.Multiline = true;
        	this.solutionSummary.Name = "solutionSummary";
        	this.solutionSummary.ReadOnly = true;
        	this.solutionSummary.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        	this.solutionSummary.Size = new System.Drawing.Size(613, 414);
        	this.solutionSummary.TabIndex = 6;
        	this.solutionSummary.WordWrap = false;
        	// 
        	// solutionEquityPage
        	// 
        	this.solutionEquityPage.Controls.Add(this.accountEquity);
        	this.solutionEquityPage.Location = new System.Drawing.Point(4, 22);
        	this.solutionEquityPage.Name = "solutionEquityPage";
        	this.solutionEquityPage.Padding = new System.Windows.Forms.Padding(3);
        	this.solutionEquityPage.Size = new System.Drawing.Size(613, 414);
        	this.solutionEquityPage.TabIndex = 0;
        	this.solutionEquityPage.Text = "Equity curve";
        	this.solutionEquityPage.UseVisualStyleBackColor = true;
        	// 
        	// accountEquity
        	// 
        	this.accountEquity.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.accountEquity.Location = new System.Drawing.Point(3, 3);
        	this.accountEquity.Name = "accountEquity";
        	this.accountEquity.ScrollGrace = 0;
        	this.accountEquity.ScrollMaxX = 0;
        	this.accountEquity.ScrollMaxY = 0;
        	this.accountEquity.ScrollMaxY2 = 0;
        	this.accountEquity.ScrollMinX = 0;
        	this.accountEquity.ScrollMinY = 0;
        	this.accountEquity.ScrollMinY2 = 0;
        	this.accountEquity.Size = new System.Drawing.Size(607, 408);
        	this.accountEquity.TabIndex = 3;
        	this.accountEquity.Visible = false;
        	// 
        	// solutionProfitPage
        	// 
        	this.solutionProfitPage.Controls.Add(this.statisticsSectionControl);
        	this.solutionProfitPage.Controls.Add(this.statisticsSectionTopPanel);
        	this.solutionProfitPage.Location = new System.Drawing.Point(4, 22);
        	this.solutionProfitPage.Name = "solutionProfitPage";
        	this.solutionProfitPage.Padding = new System.Windows.Forms.Padding(3);
        	this.solutionProfitPage.Size = new System.Drawing.Size(613, 414);
        	this.solutionProfitPage.TabIndex = 1;
        	this.solutionProfitPage.Text = "Statistics";
        	this.solutionProfitPage.UseVisualStyleBackColor = true;
        	// 
        	// statisticsSectionControl
        	// 
        	this.statisticsSectionControl.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.statisticsSectionControl.Location = new System.Drawing.Point(3, 27);
        	this.statisticsSectionControl.MinimumSize = new System.Drawing.Size(380, 400);
        	this.statisticsSectionControl.Name = "statisticsSectionControl";
        	this.statisticsSectionControl.Size = new System.Drawing.Size(607, 400);
        	this.statisticsSectionControl.TabIndex = 10;
        	this.statisticsSectionControl.Visible = false;
        	// 
        	// statisticsSectionTopPanel
        	// 
        	this.statisticsSectionTopPanel.Controls.Add(this.statisticsSectionSelector);
        	this.statisticsSectionTopPanel.Controls.Add(this.solutionStatisticsSectionLabelPanel);
        	this.statisticsSectionTopPanel.Dock = System.Windows.Forms.DockStyle.Top;
        	this.statisticsSectionTopPanel.Location = new System.Drawing.Point(3, 3);
        	this.statisticsSectionTopPanel.Name = "statisticsSectionTopPanel";
        	this.statisticsSectionTopPanel.Size = new System.Drawing.Size(607, 24);
        	this.statisticsSectionTopPanel.TabIndex = 9;
        	// 
        	// statisticsSectionSelector
        	// 
        	this.statisticsSectionSelector.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.statisticsSectionSelector.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        	this.statisticsSectionSelector.FormattingEnabled = true;
        	this.statisticsSectionSelector.Items.AddRange(new object[] {
        	        	        	"All trades profit statistics",
        	        	        	"Winning trades profit statistics",
        	        	        	"Losing trades profit statistics"});
        	this.statisticsSectionSelector.Location = new System.Drawing.Point(48, 0);
        	this.statisticsSectionSelector.Name = "statisticsSectionSelector";
        	this.statisticsSectionSelector.Size = new System.Drawing.Size(559, 21);
        	this.statisticsSectionSelector.TabIndex = 1;
        	this.statisticsSectionSelector.SelectedIndexChanged += new System.EventHandler(this.statisticsSectionSelector_SelectedIndexChanged);
        	// 
        	// solutionStatisticsSectionLabelPanel
        	// 
        	this.solutionStatisticsSectionLabelPanel.Controls.Add(this.statisticsSectionLabel);
        	this.solutionStatisticsSectionLabelPanel.Dock = System.Windows.Forms.DockStyle.Left;
        	this.solutionStatisticsSectionLabelPanel.Location = new System.Drawing.Point(0, 0);
        	this.solutionStatisticsSectionLabelPanel.Name = "solutionStatisticsSectionLabelPanel";
        	this.solutionStatisticsSectionLabelPanel.Size = new System.Drawing.Size(48, 24);
        	this.solutionStatisticsSectionLabelPanel.TabIndex = 2;
        	// 
        	// statisticsSectionLabel
        	// 
        	this.statisticsSectionLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
        	this.statisticsSectionLabel.AutoSize = true;
        	this.statisticsSectionLabel.Location = new System.Drawing.Point(0, 3);
        	this.statisticsSectionLabel.Name = "statisticsSectionLabel";
        	this.statisticsSectionLabel.Size = new System.Drawing.Size(46, 13);
        	this.statisticsSectionLabel.TabIndex = 4;
        	this.statisticsSectionLabel.Text = "Section:";
        	// 
        	// solutionTradesPage
        	// 
        	this.solutionTradesPage.Controls.Add(this.tradesList);
        	this.solutionTradesPage.Location = new System.Drawing.Point(4, 22);
        	this.solutionTradesPage.Name = "solutionTradesPage";
        	this.solutionTradesPage.Size = new System.Drawing.Size(613, 414);
        	this.solutionTradesPage.TabIndex = 4;
        	this.solutionTradesPage.Text = "Trades";
        	this.solutionTradesPage.UseVisualStyleBackColor = true;
        	// 
        	// tradesList
        	// 
        	this.tradesList.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.tradesList.Location = new System.Drawing.Point(0, 0);
        	this.tradesList.Name = "tradesList";
        	this.tradesList.Size = new System.Drawing.Size(613, 414);
        	this.tradesList.TabIndex = 7;
        	// 
        	// solutionOrdersPage
        	// 
        	this.solutionOrdersPage.Controls.Add(this.orderBrowser);
        	this.solutionOrdersPage.Location = new System.Drawing.Point(4, 22);
        	this.solutionOrdersPage.Name = "solutionOrdersPage";
        	this.solutionOrdersPage.Size = new System.Drawing.Size(613, 414);
        	this.solutionOrdersPage.TabIndex = 2;
        	this.solutionOrdersPage.Text = "Orders";
        	this.solutionOrdersPage.UseVisualStyleBackColor = true;
        	// 
        	// orderBrowser
        	// 
        	this.orderBrowser.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.orderBrowser.Location = new System.Drawing.Point(0, 0);
        	this.orderBrowser.MinimumSize = new System.Drawing.Size(20, 20);
        	this.orderBrowser.Name = "orderBrowser";
        	this.orderBrowser.Size = new System.Drawing.Size(613, 414);
        	this.orderBrowser.TabIndex = 5;
        	// 
        	// SolutionReportControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.solutionTabs);
        	this.Name = "SolutionReportControl";
        	this.Size = new System.Drawing.Size(621, 440);
        	this.solutionTabs.ResumeLayout(false);
        	this.solutionSummaryPage.ResumeLayout(false);
        	this.solutionSummaryPage.PerformLayout();
        	this.solutionEquityPage.ResumeLayout(false);
        	this.solutionProfitPage.ResumeLayout(false);
        	this.statisticsSectionTopPanel.ResumeLayout(false);
        	this.solutionStatisticsSectionLabelPanel.ResumeLayout(false);
        	this.solutionStatisticsSectionLabelPanel.PerformLayout();
        	this.solutionTradesPage.ResumeLayout(false);
        	this.solutionOrdersPage.ResumeLayout(false);
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.TabControl solutionTabs;
        private System.Windows.Forms.Label statisticsSectionLabel;
        private System.Windows.Forms.Panel solutionStatisticsSectionLabelPanel;
        private System.Windows.Forms.ComboBox statisticsSectionSelector;
        private System.Windows.Forms.Panel statisticsSectionTopPanel;
        private System.Windows.Forms.WebBrowser orderBrowser;
        private TradingSimulator.Ui.TradesListControl tradesList;
        private TradingSimulator.Ui.StatisticalReportControl statisticsSectionControl;
        private ZedGraph.ZedGraphControl accountEquity;
        private System.Windows.Forms.TextBox solutionSummary;
        private System.Windows.Forms.TabPage solutionOrdersPage;
        private System.Windows.Forms.TabPage solutionTradesPage;
        private System.Windows.Forms.TabPage solutionProfitPage;
        private System.Windows.Forms.TabPage solutionEquityPage;
        private System.Windows.Forms.TabPage solutionSummaryPage;
    }
}
