namespace TradingSimulator.Ui
{
    partial class SlippageAnalyzer
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SlippageAnalyzer));
            this.allTabs = new System.Windows.Forms.TabControl();
            this.bidPage = new System.Windows.Forms.TabPage();
            this.askPage = new System.Windows.Forms.TabPage();
            this.bidStatistics = new TradingSimulator.Ui.StatisticalReportControl();
            this.askStatistics = new TradingSimulator.Ui.StatisticalReportControl();
            this.allTabs.SuspendLayout();
            this.bidPage.SuspendLayout();
            this.askPage.SuspendLayout();
            this.SuspendLayout();
            // 
            // allTabs
            // 
            this.allTabs.Controls.Add(this.bidPage);
            this.allTabs.Controls.Add(this.askPage);
            this.allTabs.Dock = System.Windows.Forms.DockStyle.Fill;
            this.allTabs.Location = new System.Drawing.Point(0, 0);
            this.allTabs.Name = "allTabs";
            this.allTabs.SelectedIndex = 0;
            this.allTabs.Size = new System.Drawing.Size(632, 446);
            this.allTabs.TabIndex = 0;
            // 
            // bidPage
            // 
            this.bidPage.Controls.Add(this.bidStatistics);
            this.bidPage.Location = new System.Drawing.Point(4, 22);
            this.bidPage.Name = "bidPage";
            this.bidPage.Padding = new System.Windows.Forms.Padding(3);
            this.bidPage.Size = new System.Drawing.Size(624, 420);
            this.bidPage.TabIndex = 0;
            this.bidPage.Text = "Price - Bid";
            this.bidPage.UseVisualStyleBackColor = true;
            // 
            // askPage
            // 
            this.askPage.Controls.Add(this.askStatistics);
            this.askPage.Location = new System.Drawing.Point(4, 22);
            this.askPage.Name = "askPage";
            this.askPage.Padding = new System.Windows.Forms.Padding(3);
            this.askPage.Size = new System.Drawing.Size(624, 420);
            this.askPage.TabIndex = 1;
            this.askPage.Text = "Ask - Price";
            this.askPage.UseVisualStyleBackColor = true;
            // 
            // bidStatistics
            // 
            this.bidStatistics.Dock = System.Windows.Forms.DockStyle.Fill;
            this.bidStatistics.Location = new System.Drawing.Point(3, 3);
            this.bidStatistics.MinimumSize = new System.Drawing.Size(380, 400);
            this.bidStatistics.Name = "bidStatistics";
            this.bidStatistics.Size = new System.Drawing.Size(618, 414);
            this.bidStatistics.TabIndex = 0;
            // 
            // askStatistics
            // 
            this.askStatistics.Dock = System.Windows.Forms.DockStyle.Fill;
            this.askStatistics.Location = new System.Drawing.Point(3, 3);
            this.askStatistics.MinimumSize = new System.Drawing.Size(380, 400);
            this.askStatistics.Name = "askStatistics";
            this.askStatistics.Size = new System.Drawing.Size(618, 414);
            this.askStatistics.TabIndex = 0;
            // 
            // SlippageAnalyzer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(632, 446);
            this.Controls.Add(this.allTabs);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(640, 480);
            this.Name = "SlippageAnalyzer";
            this.Text = "Slippage Analyzer";
            this.allTabs.ResumeLayout(false);
            this.bidPage.ResumeLayout(false);
            this.askPage.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TabControl allTabs;
        private System.Windows.Forms.TabPage bidPage;
        private StatisticalReportControl bidStatistics;
        private System.Windows.Forms.TabPage askPage;
        private StatisticalReportControl askStatistics;
    }
}