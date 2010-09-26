namespace TradingSimulator.Ui
{
    partial class StatisticalReportControl
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
        	this.label23 = new System.Windows.Forms.Label();
        	this.distClusterCount = new System.Windows.Forms.NumericUpDown();
        	this.distGraph = new ZedGraph.ZedGraphControl();
        	this.mainSplitter = new System.Windows.Forms.SplitContainer();
        	this.report = new System.Windows.Forms.TextBox();
        	this.distributionConfigurationPanel = new System.Windows.Forms.Panel();
        	((System.ComponentModel.ISupportInitialize)(this.distClusterCount)).BeginInit();
        	this.mainSplitter.Panel1.SuspendLayout();
        	this.mainSplitter.Panel2.SuspendLayout();
        	this.mainSplitter.SuspendLayout();
        	this.distributionConfigurationPanel.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// label23
        	// 
        	this.label23.AutoSize = true;
        	this.label23.Location = new System.Drawing.Point(0, 10);
        	this.label23.Name = "label23";
        	this.label23.Size = new System.Drawing.Size(126, 13);
        	this.label23.TabIndex = 22;
        	this.label23.Text = "Distribution cluster count:";
        	this.label23.UseWaitCursor = true;
        	// 
        	// distClusterCount
        	// 
        	this.distClusterCount.Enabled = false;
        	this.distClusterCount.Location = new System.Drawing.Point(136, 8);
        	this.distClusterCount.Maximum = new decimal(new int[] {
        	        	        	1000,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.distClusterCount.Minimum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.distClusterCount.Name = "distClusterCount";
        	this.distClusterCount.Size = new System.Drawing.Size(56, 20);
        	this.distClusterCount.TabIndex = 23;
        	this.distClusterCount.Value = new decimal(new int[] {
        	        	        	21,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.distClusterCount.ValueChanged += new System.EventHandler(this.distClusterCount_ValueChanged);
        	// 
        	// distGraph
        	// 
        	this.distGraph.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.distGraph.IsShowPointValues = true;
        	this.distGraph.Location = new System.Drawing.Point(0, 0);
        	this.distGraph.Name = "distGraph";
        	this.distGraph.PointValueFormat = "0.####";
        	this.distGraph.ScrollGrace = 0;
        	this.distGraph.ScrollMaxX = 0;
        	this.distGraph.ScrollMaxY = 0;
        	this.distGraph.ScrollMaxY2 = 0;
        	this.distGraph.ScrollMinX = 0;
        	this.distGraph.ScrollMinY = 0;
        	this.distGraph.ScrollMinY2 = 0;
        	this.distGraph.Size = new System.Drawing.Size(446, 274);
        	this.distGraph.TabIndex = 1;
        	// 
        	// mainSplitter
        	// 
        	this.mainSplitter.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.mainSplitter.Location = new System.Drawing.Point(0, 0);
        	this.mainSplitter.Name = "mainSplitter";
        	this.mainSplitter.Orientation = System.Windows.Forms.Orientation.Horizontal;
        	// 
        	// mainSplitter.Panel1
        	// 
        	this.mainSplitter.Panel1.Controls.Add(this.report);
        	// 
        	// mainSplitter.Panel2
        	// 
        	this.mainSplitter.Panel2.Controls.Add(this.distGraph);
        	this.mainSplitter.Panel2.Controls.Add(this.distributionConfigurationPanel);
        	this.mainSplitter.Size = new System.Drawing.Size(446, 485);
        	this.mainSplitter.SplitterDistance = 175;
        	this.mainSplitter.TabIndex = 2;
        	// 
        	// report
        	// 
        	this.report.BackColor = System.Drawing.SystemColors.Window;
        	this.report.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.report.Font = new System.Drawing.Font("Courier New", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.report.Location = new System.Drawing.Point(0, 0);
        	this.report.Multiline = true;
        	this.report.Name = "report";
        	this.report.ReadOnly = true;
        	this.report.ScrollBars = System.Windows.Forms.ScrollBars.Both;
        	this.report.Size = new System.Drawing.Size(446, 175);
        	this.report.TabIndex = 0;
        	this.report.WordWrap = false;
        	// 
        	// distributionConfigurationPanel
        	// 
        	this.distributionConfigurationPanel.Controls.Add(this.label23);
        	this.distributionConfigurationPanel.Controls.Add(this.distClusterCount);
        	this.distributionConfigurationPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
        	this.distributionConfigurationPanel.Location = new System.Drawing.Point(0, 274);
        	this.distributionConfigurationPanel.Name = "distributionConfigurationPanel";
        	this.distributionConfigurationPanel.Size = new System.Drawing.Size(446, 32);
        	this.distributionConfigurationPanel.TabIndex = 0;
        	// 
        	// StatisticalReportControl
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.Controls.Add(this.mainSplitter);
        	this.MinimumSize = new System.Drawing.Size(380, 400);
        	this.Name = "StatisticalReportControl";
        	this.Size = new System.Drawing.Size(446, 485);
        	((System.ComponentModel.ISupportInitialize)(this.distClusterCount)).EndInit();
        	this.mainSplitter.Panel1.ResumeLayout(false);
        	this.mainSplitter.Panel1.PerformLayout();
        	this.mainSplitter.Panel2.ResumeLayout(false);
        	this.mainSplitter.ResumeLayout(false);
        	this.distributionConfigurationPanel.ResumeLayout(false);
        	this.distributionConfigurationPanel.PerformLayout();
        	this.ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.Label label23;
        private System.Windows.Forms.NumericUpDown distClusterCount;
        private ZedGraph.ZedGraphControl distGraph;
        private System.Windows.Forms.SplitContainer mainSplitter;
        private System.Windows.Forms.Panel distributionConfigurationPanel;
        private System.Windows.Forms.TextBox report;
    }
}
