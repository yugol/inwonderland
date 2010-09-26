namespace TradingSimulator.Ui
{
    partial class MainForm
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
        	System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
        	this.mainMenu = new System.Windows.Forms.MenuStrip();
        	this.applicationToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.exitToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.toolsToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.searchSpaceExplorerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.slippageAnalyserToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.tradeLogAnalyzerToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.joinMaturitiesToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.relativeHistoryToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
        	this.statusStrip = new System.Windows.Forms.StatusStrip();
        	this.spacerLabel = new System.Windows.Forms.ToolStripStatusLabel();
        	this.progressLabel = new System.Windows.Forms.ToolStripStatusLabel();
        	this.statusProgressBar = new System.Windows.Forms.ToolStripProgressBar();
        	this.cancelLabel = new System.Windows.Forms.ToolStripStatusLabel();
        	this.viewTabs = new System.Windows.Forms.TabControl();
        	this.dataPage = new System.Windows.Forms.TabPage();
        	this.dataTabs = new System.Windows.Forms.TabControl();
        	this.dataChartPage = new System.Windows.Forms.TabPage();
        	this.sampleDataGraph = new ZedGraph.ZedGraphControl();
        	this.simulationsPage = new System.Windows.Forms.TabPage();
        	this.solutionReport = new TradingSimulator.Ui.SolutionReportControl();
        	this.solutionListPanel = new System.Windows.Forms.Panel();
        	this.solutionList = new System.Windows.Forms.ListView();
        	this.solutionListColumn1 = new System.Windows.Forms.ColumnHeader();
        	this.solutionListActionPanel = new System.Windows.Forms.Panel();
        	this.clearSolutionList = new System.Windows.Forms.Button();
        	this.maxSolutionCount = new System.Windows.Forms.NumericUpDown();
        	this.label10 = new System.Windows.Forms.Label();
        	this.operationTabs = new System.Windows.Forms.TabControl();
        	this.dataSetsPage = new System.Windows.Forms.TabPage();
        	this.dataComponentTable = new System.Windows.Forms.TableLayoutPanel();
        	this.periodCombo = new System.Windows.Forms.ComboBox();
        	this.label1 = new System.Windows.Forms.Label();
        	this.testDataSelector = new TradingSimulator.Ui.DataSetSelector();
        	this.trainDataSelector = new TradingSimulator.Ui.DataSetSelector();
        	this.tradingSystemPage = new System.Windows.Forms.TabPage();
        	this.parametersSimulatorTabs = new System.Windows.Forms.TabControl();
        	this.tunePage = new System.Windows.Forms.TabPage();
        	this.tsTuner = new TradingSimulator.Ui.TSTunerControl();
        	this.searchPage = new System.Windows.Forms.TabPage();
        	this.tsSeeker = new TradingSimulator.Ui.TSSeekerControl();
        	this.splitContainer = new System.Windows.Forms.SplitContainer();
        	this.mainMenu.SuspendLayout();
        	this.statusStrip.SuspendLayout();
        	this.viewTabs.SuspendLayout();
        	this.dataPage.SuspendLayout();
        	this.dataTabs.SuspendLayout();
        	this.dataChartPage.SuspendLayout();
        	this.simulationsPage.SuspendLayout();
        	this.solutionListPanel.SuspendLayout();
        	this.solutionListActionPanel.SuspendLayout();
        	((System.ComponentModel.ISupportInitialize)(this.maxSolutionCount)).BeginInit();
        	this.operationTabs.SuspendLayout();
        	this.dataSetsPage.SuspendLayout();
        	this.dataComponentTable.SuspendLayout();
        	this.tradingSystemPage.SuspendLayout();
        	this.parametersSimulatorTabs.SuspendLayout();
        	this.tunePage.SuspendLayout();
        	this.searchPage.SuspendLayout();
        	this.splitContainer.Panel1.SuspendLayout();
        	this.splitContainer.Panel2.SuspendLayout();
        	this.splitContainer.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// mainMenu
        	// 
        	this.mainMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.applicationToolStripMenuItem,
        	        	        	this.toolsToolStripMenuItem});
        	this.mainMenu.Location = new System.Drawing.Point(0, 0);
        	this.mainMenu.Name = "mainMenu";
        	this.mainMenu.Size = new System.Drawing.Size(992, 24);
        	this.mainMenu.TabIndex = 0;
        	this.mainMenu.Text = "menuStrip1";
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
        	this.exitToolStripMenuItem.Size = new System.Drawing.Size(94, 22);
        	this.exitToolStripMenuItem.Text = "Exit";
        	this.exitToolStripMenuItem.Click += new System.EventHandler(this.ExitToolStripMenuItemClick);
        	// 
        	// toolsToolStripMenuItem
        	// 
        	this.toolsToolStripMenuItem.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.searchSpaceExplorerToolStripMenuItem,
        	        	        	this.slippageAnalyserToolStripMenuItem,
        	        	        	this.tradeLogAnalyzerToolStripMenuItem,
        	        	        	this.joinMaturitiesToolStripMenuItem,
        	        	        	this.relativeHistoryToolStripMenuItem});
        	this.toolsToolStripMenuItem.Name = "toolsToolStripMenuItem";
        	this.toolsToolStripMenuItem.Size = new System.Drawing.Size(45, 20);
        	this.toolsToolStripMenuItem.Text = "Tools";
        	// 
        	// searchSpaceExplorerToolStripMenuItem
        	// 
        	this.searchSpaceExplorerToolStripMenuItem.Enabled = false;
        	this.searchSpaceExplorerToolStripMenuItem.Name = "searchSpaceExplorerToolStripMenuItem";
        	this.searchSpaceExplorerToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
        	this.searchSpaceExplorerToolStripMenuItem.Text = "Search Space Explorer";
        	this.searchSpaceExplorerToolStripMenuItem.Visible = false;
        	this.searchSpaceExplorerToolStripMenuItem.Click += new System.EventHandler(this.searchSpaceExplorerToolStripMenuItem_Click);
        	// 
        	// slippageAnalyserToolStripMenuItem
        	// 
        	this.slippageAnalyserToolStripMenuItem.Name = "slippageAnalyserToolStripMenuItem";
        	this.slippageAnalyserToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
        	this.slippageAnalyserToolStripMenuItem.Text = "Slippage Analyzer";
        	this.slippageAnalyserToolStripMenuItem.Click += new System.EventHandler(this.slippageAnalyserToolStripMenuItem_Click);
        	// 
        	// tradeLogAnalyzerToolStripMenuItem
        	// 
        	this.tradeLogAnalyzerToolStripMenuItem.Name = "tradeLogAnalyzerToolStripMenuItem";
        	this.tradeLogAnalyzerToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
        	this.tradeLogAnalyzerToolStripMenuItem.Text = "Trade Log Analyzer";
        	this.tradeLogAnalyzerToolStripMenuItem.Click += new System.EventHandler(this.tradeLogAnalyzerToolStripMenuItem_Click);
        	// 
        	// joinMaturitiesToolStripMenuItem
        	// 
        	this.joinMaturitiesToolStripMenuItem.Name = "joinMaturitiesToolStripMenuItem";
        	this.joinMaturitiesToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
        	this.joinMaturitiesToolStripMenuItem.Text = "Join Maturities";
        	this.joinMaturitiesToolStripMenuItem.Click += new System.EventHandler(this.JoinMaturitiesToolStripMenuItemClick);
        	// 
        	// relativeHistoryToolStripMenuItem
        	// 
        	this.relativeHistoryToolStripMenuItem.Name = "relativeHistoryToolStripMenuItem";
        	this.relativeHistoryToolStripMenuItem.Size = new System.Drawing.Size(186, 22);
        	this.relativeHistoryToolStripMenuItem.Text = "Trend Recognition";
        	this.relativeHistoryToolStripMenuItem.Click += new System.EventHandler(this.TrendRecognitionToolStripMenuItemClick);
        	// 
        	// statusStrip
        	// 
        	this.statusStrip.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
        	        	        	this.spacerLabel,
        	        	        	this.progressLabel,
        	        	        	this.statusProgressBar,
        	        	        	this.cancelLabel});
        	this.statusStrip.Location = new System.Drawing.Point(0, 644);
        	this.statusStrip.Name = "statusStrip";
        	this.statusStrip.Size = new System.Drawing.Size(992, 22);
        	this.statusStrip.TabIndex = 1;
        	// 
        	// spacerLabel
        	// 
        	this.spacerLabel.Name = "spacerLabel";
        	this.spacerLabel.Size = new System.Drawing.Size(977, 17);
        	this.spacerLabel.Spring = true;
        	// 
        	// progressLabel
        	// 
        	this.progressLabel.Margin = new System.Windows.Forms.Padding(0, 2, 0, 2);
        	this.progressLabel.Name = "progressLabel";
        	this.progressLabel.Size = new System.Drawing.Size(54, 20);
        	this.progressLabel.Text = "Progress: ";
        	this.progressLabel.Visible = false;
        	// 
        	// statusProgressBar
        	// 
        	this.statusProgressBar.Name = "statusProgressBar";
        	this.statusProgressBar.Size = new System.Drawing.Size(500, 16);
        	this.statusProgressBar.Step = 1;
        	this.statusProgressBar.Value = 4;
        	this.statusProgressBar.Visible = false;
        	// 
        	// cancelLabel
        	// 
        	this.cancelLabel.BackColor = System.Drawing.SystemColors.ControlDark;
        	this.cancelLabel.BorderSides = ((System.Windows.Forms.ToolStripStatusLabelBorderSides)((((System.Windows.Forms.ToolStripStatusLabelBorderSides.Left | System.Windows.Forms.ToolStripStatusLabelBorderSides.Top) 
        	        	        	| System.Windows.Forms.ToolStripStatusLabelBorderSides.Right) 
        	        	        	| System.Windows.Forms.ToolStripStatusLabelBorderSides.Bottom)));
        	this.cancelLabel.BorderStyle = System.Windows.Forms.Border3DStyle.Raised;
        	this.cancelLabel.Font = new System.Drawing.Font("Tahoma", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
        	this.cancelLabel.ForeColor = System.Drawing.Color.Black;
        	this.cancelLabel.Margin = new System.Windows.Forms.Padding(3, 2, 3, 2);
        	this.cancelLabel.Name = "cancelLabel";
        	this.cancelLabel.Padding = new System.Windows.Forms.Padding(0, 0, 0, 3);
        	this.cancelLabel.Size = new System.Drawing.Size(49, 20);
        	this.cancelLabel.Text = " Cancel ";
        	this.cancelLabel.Visible = false;
        	this.cancelLabel.Click += new System.EventHandler(this.CancelLabelClick);
        	// 
        	// viewTabs
        	// 
        	this.viewTabs.Controls.Add(this.dataPage);
        	this.viewTabs.Controls.Add(this.simulationsPage);
        	this.viewTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.viewTabs.Location = new System.Drawing.Point(0, 0);
        	this.viewTabs.Name = "viewTabs";
        	this.viewTabs.SelectedIndex = 0;
        	this.viewTabs.Size = new System.Drawing.Size(670, 620);
        	this.viewTabs.TabIndex = 0;
        	// 
        	// dataPage
        	// 
        	this.dataPage.Controls.Add(this.dataTabs);
        	this.dataPage.Location = new System.Drawing.Point(4, 22);
        	this.dataPage.Name = "dataPage";
        	this.dataPage.Padding = new System.Windows.Forms.Padding(3);
        	this.dataPage.Size = new System.Drawing.Size(662, 594);
        	this.dataPage.TabIndex = 1;
        	this.dataPage.Text = "Data view";
        	this.dataPage.UseVisualStyleBackColor = true;
        	// 
        	// dataTabs
        	// 
        	this.dataTabs.Controls.Add(this.dataChartPage);
        	this.dataTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.dataTabs.Enabled = false;
        	this.dataTabs.Location = new System.Drawing.Point(3, 3);
        	this.dataTabs.Name = "dataTabs";
        	this.dataTabs.SelectedIndex = 0;
        	this.dataTabs.Size = new System.Drawing.Size(656, 588);
        	this.dataTabs.TabIndex = 0;
        	// 
        	// dataChartPage
        	// 
        	this.dataChartPage.Controls.Add(this.sampleDataGraph);
        	this.dataChartPage.Location = new System.Drawing.Point(4, 22);
        	this.dataChartPage.Name = "dataChartPage";
        	this.dataChartPage.Padding = new System.Windows.Forms.Padding(3);
        	this.dataChartPage.Size = new System.Drawing.Size(648, 562);
        	this.dataChartPage.TabIndex = 0;
        	this.dataChartPage.Text = "Chart";
        	this.dataChartPage.UseVisualStyleBackColor = true;
        	// 
        	// sampleDataGraph
        	// 
        	this.sampleDataGraph.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.sampleDataGraph.IsShowPointValues = true;
        	this.sampleDataGraph.Location = new System.Drawing.Point(3, 3);
        	this.sampleDataGraph.Name = "sampleDataGraph";
        	this.sampleDataGraph.PointValueFormat = "0.####";
        	this.sampleDataGraph.ScrollGrace = 0;
        	this.sampleDataGraph.ScrollMaxX = 0;
        	this.sampleDataGraph.ScrollMaxY = 0;
        	this.sampleDataGraph.ScrollMaxY2 = 0;
        	this.sampleDataGraph.ScrollMinX = 0;
        	this.sampleDataGraph.ScrollMinY = 0;
        	this.sampleDataGraph.ScrollMinY2 = 0;
        	this.sampleDataGraph.Size = new System.Drawing.Size(642, 556);
        	this.sampleDataGraph.TabIndex = 0;
        	this.sampleDataGraph.Visible = false;
        	this.sampleDataGraph.ZoomStepFraction = 0.5;
        	// 
        	// simulationsPage
        	// 
        	this.simulationsPage.Controls.Add(this.solutionReport);
        	this.simulationsPage.Controls.Add(this.solutionListPanel);
        	this.simulationsPage.Location = new System.Drawing.Point(4, 22);
        	this.simulationsPage.Name = "simulationsPage";
        	this.simulationsPage.Size = new System.Drawing.Size(662, 594);
        	this.simulationsPage.TabIndex = 4;
        	this.simulationsPage.Text = "Simulations";
        	this.simulationsPage.UseVisualStyleBackColor = true;
        	// 
        	// solutionReport
        	// 
        	this.solutionReport.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.solutionReport.Location = new System.Drawing.Point(120, 0);
        	this.solutionReport.Name = "solutionReport";
        	this.solutionReport.Size = new System.Drawing.Size(542, 594);
        	this.solutionReport.TabIndex = 4;
        	// 
        	// solutionListPanel
        	// 
        	this.solutionListPanel.Controls.Add(this.solutionList);
        	this.solutionListPanel.Controls.Add(this.solutionListActionPanel);
        	this.solutionListPanel.Dock = System.Windows.Forms.DockStyle.Left;
        	this.solutionListPanel.Location = new System.Drawing.Point(0, 0);
        	this.solutionListPanel.Name = "solutionListPanel";
        	this.solutionListPanel.Padding = new System.Windows.Forms.Padding(0, 2, 3, 2);
        	this.solutionListPanel.Size = new System.Drawing.Size(120, 594);
        	this.solutionListPanel.TabIndex = 3;
        	// 
        	// solutionList
        	// 
        	this.solutionList.CheckBoxes = true;
        	this.solutionList.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
        	        	        	this.solutionListColumn1});
        	this.solutionList.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.solutionList.FullRowSelect = true;
        	this.solutionList.HideSelection = false;
        	this.solutionList.Location = new System.Drawing.Point(0, 2);
        	this.solutionList.MultiSelect = false;
        	this.solutionList.Name = "solutionList";
        	this.solutionList.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
        	this.solutionList.RightToLeftLayout = true;
        	this.solutionList.Size = new System.Drawing.Size(117, 530);
        	this.solutionList.TabIndex = 1;
        	this.solutionList.UseCompatibleStateImageBehavior = false;
        	this.solutionList.View = System.Windows.Forms.View.Details;
        	this.solutionList.SelectedIndexChanged += new System.EventHandler(this.SolutionListSelectedIndexChanged);
        	// 
        	// solutionListColumn1
        	// 
        	this.solutionListColumn1.Text = "Stored solutions";
        	this.solutionListColumn1.Width = 90;
        	// 
        	// solutionListActionPanel
        	// 
        	this.solutionListActionPanel.Controls.Add(this.clearSolutionList);
        	this.solutionListActionPanel.Controls.Add(this.maxSolutionCount);
        	this.solutionListActionPanel.Controls.Add(this.label10);
        	this.solutionListActionPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
        	this.solutionListActionPanel.Location = new System.Drawing.Point(0, 532);
        	this.solutionListActionPanel.Name = "solutionListActionPanel";
        	this.solutionListActionPanel.Size = new System.Drawing.Size(117, 60);
        	this.solutionListActionPanel.TabIndex = 2;
        	// 
        	// clearSolutionList
        	// 
        	this.clearSolutionList.Location = new System.Drawing.Point(8, 32);
        	this.clearSolutionList.Name = "clearSolutionList";
        	this.clearSolutionList.Size = new System.Drawing.Size(48, 23);
        	this.clearSolutionList.TabIndex = 2;
        	this.clearSolutionList.Text = "Clear";
        	this.clearSolutionList.UseVisualStyleBackColor = true;
        	this.clearSolutionList.Click += new System.EventHandler(this.ClearSolutionListClick);
        	// 
        	// maxSolutionCount
        	// 
        	this.maxSolutionCount.Location = new System.Drawing.Point(64, 8);
        	this.maxSolutionCount.Maximum = new decimal(new int[] {
        	        	        	1000,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.maxSolutionCount.Minimum = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.maxSolutionCount.Name = "maxSolutionCount";
        	this.maxSolutionCount.Size = new System.Drawing.Size(48, 20);
        	this.maxSolutionCount.TabIndex = 1;
        	this.maxSolutionCount.Value = new decimal(new int[] {
        	        	        	1,
        	        	        	0,
        	        	        	0,
        	        	        	0});
        	this.maxSolutionCount.ValueChanged += new System.EventHandler(this.MaxSolutionCountValueChanged);
        	// 
        	// label10
        	// 
        	this.label10.AutoSize = true;
        	this.label10.Location = new System.Drawing.Point(3, 10);
        	this.label10.Name = "label10";
        	this.label10.Size = new System.Drawing.Size(59, 13);
        	this.label10.TabIndex = 0;
        	this.label10.Text = "Buffer size:";
        	// 
        	// operationTabs
        	// 
        	this.operationTabs.Controls.Add(this.dataSetsPage);
        	this.operationTabs.Controls.Add(this.tradingSystemPage);
        	this.operationTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.operationTabs.Location = new System.Drawing.Point(0, 0);
        	this.operationTabs.Name = "operationTabs";
        	this.operationTabs.SelectedIndex = 0;
        	this.operationTabs.Size = new System.Drawing.Size(318, 620);
        	this.operationTabs.TabIndex = 4;
        	// 
        	// dataSetsPage
        	// 
        	this.dataSetsPage.Controls.Add(this.dataComponentTable);
        	this.dataSetsPage.Location = new System.Drawing.Point(4, 22);
        	this.dataSetsPage.Name = "dataSetsPage";
        	this.dataSetsPage.Padding = new System.Windows.Forms.Padding(3);
        	this.dataSetsPage.Size = new System.Drawing.Size(310, 594);
        	this.dataSetsPage.TabIndex = 0;
        	this.dataSetsPage.Text = "Data sets";
        	this.dataSetsPage.UseVisualStyleBackColor = true;
        	// 
        	// dataComponentTable
        	// 
        	this.dataComponentTable.ColumnCount = 2;
        	this.dataComponentTable.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
        	this.dataComponentTable.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 100F));
        	this.dataComponentTable.Controls.Add(this.periodCombo, 1, 0);
        	this.dataComponentTable.Controls.Add(this.label1, 0, 0);
        	this.dataComponentTable.Controls.Add(this.testDataSelector, 0, 1);
        	this.dataComponentTable.Controls.Add(this.trainDataSelector, 0, 2);
        	this.dataComponentTable.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.dataComponentTable.GrowStyle = System.Windows.Forms.TableLayoutPanelGrowStyle.AddColumns;
        	this.dataComponentTable.Location = new System.Drawing.Point(3, 3);
        	this.dataComponentTable.Name = "dataComponentTable";
        	this.dataComponentTable.RowCount = 3;
        	this.dataComponentTable.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 28F));
        	this.dataComponentTable.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
        	this.dataComponentTable.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 50F));
        	this.dataComponentTable.Size = new System.Drawing.Size(304, 588);
        	this.dataComponentTable.TabIndex = 0;
        	// 
        	// periodCombo
        	// 
        	this.periodCombo.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.periodCombo.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        	this.periodCombo.FormattingEnabled = true;
        	this.periodCombo.Items.AddRange(new object[] {
        	        	        	"1 minute",
        	        	        	"2 minutes",
        	        	        	"3 minutes",
        	        	        	"4 minutes",
        	        	        	"5 minutes",
        	        	        	"6 minutes",
        	        	        	"10 minutes",
        	        	        	"12 minutes",
        	        	        	"15 minutes",
        	        	        	"20 minutes",
        	        	        	"30 minutes",
        	        	        	"60 minutes",
        	        	        	"Daily"});
        	this.periodCombo.Location = new System.Drawing.Point(49, 3);
        	this.periodCombo.Name = "periodCombo";
        	this.periodCombo.Size = new System.Drawing.Size(252, 21);
        	this.periodCombo.TabIndex = 20;
        	this.periodCombo.SelectedIndexChanged += new System.EventHandler(this.PeriodSelectedIndexChanged);
        	// 
        	// label1
        	// 
        	this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
        	this.label1.AutoSize = true;
        	this.label1.Location = new System.Drawing.Point(3, 7);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(40, 13);
        	this.label1.TabIndex = 1;
        	this.label1.Text = "Period:";
        	// 
        	// testDataSelector
        	// 
        	this.dataComponentTable.SetColumnSpan(this.testDataSelector, 2);
        	this.testDataSelector.DataName = "Test data:";
        	this.testDataSelector.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.testDataSelector.Location = new System.Drawing.Point(3, 31);
        	this.testDataSelector.MinimumSize = new System.Drawing.Size(200, 0);
        	this.testDataSelector.Name = "testDataSelector";
        	this.testDataSelector.Size = new System.Drawing.Size(298, 274);
        	this.testDataSelector.TabIndex = 21;
        	this.testDataSelector.DisplayDataSet += new TradingSimulator.Ui.DisplayDataSetHandler(this.TestDataSelectorDisplayDataSet);
        	this.testDataSelector.CopyDataSet += new TradingSimulator.Ui.CopyDataSetHandler(this.TestDataSelectorCopyDataSet);
        	this.testDataSelector.DataSetChanged += new TradingSimulator.Ui.DataSetChangedHandler(this.TestDataSelectorDataSetChanged);
        	// 
        	// trainDataSelector
        	// 
        	this.dataComponentTable.SetColumnSpan(this.trainDataSelector, 2);
        	this.trainDataSelector.DataName = "Train data:";
        	this.trainDataSelector.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.trainDataSelector.Location = new System.Drawing.Point(3, 311);
        	this.trainDataSelector.MinimumSize = new System.Drawing.Size(200, 0);
        	this.trainDataSelector.Name = "trainDataSelector";
        	this.trainDataSelector.Size = new System.Drawing.Size(298, 274);
        	this.trainDataSelector.TabIndex = 22;
        	this.trainDataSelector.DisplayDataSet += new TradingSimulator.Ui.DisplayDataSetHandler(this.TrainDataSelectorDisplayDataSet);
        	this.trainDataSelector.CopyDataSet += new TradingSimulator.Ui.CopyDataSetHandler(this.TrainDataSelectorCopyDataSet);
        	this.trainDataSelector.DataSetChanged += new TradingSimulator.Ui.DataSetChangedHandler(this.TrainDataSelectorDataSetChanged);
        	// 
        	// tradingSystemPage
        	// 
        	this.tradingSystemPage.Controls.Add(this.parametersSimulatorTabs);
        	this.tradingSystemPage.Location = new System.Drawing.Point(4, 22);
        	this.tradingSystemPage.Name = "tradingSystemPage";
        	this.tradingSystemPage.Padding = new System.Windows.Forms.Padding(3);
        	this.tradingSystemPage.Size = new System.Drawing.Size(310, 594);
        	this.tradingSystemPage.TabIndex = 1;
        	this.tradingSystemPage.Text = "Trading system";
        	this.tradingSystemPage.UseVisualStyleBackColor = true;
        	// 
        	// parametersSimulatorTabs
        	// 
        	this.parametersSimulatorTabs.Controls.Add(this.tunePage);
        	this.parametersSimulatorTabs.Controls.Add(this.searchPage);
        	this.parametersSimulatorTabs.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.parametersSimulatorTabs.Location = new System.Drawing.Point(3, 3);
        	this.parametersSimulatorTabs.Name = "parametersSimulatorTabs";
        	this.parametersSimulatorTabs.SelectedIndex = 0;
        	this.parametersSimulatorTabs.Size = new System.Drawing.Size(304, 588);
        	this.parametersSimulatorTabs.TabIndex = 0;
        	// 
        	// tunePage
        	// 
        	this.tunePage.Controls.Add(this.tsTuner);
        	this.tunePage.Location = new System.Drawing.Point(4, 22);
        	this.tunePage.Name = "tunePage";
        	this.tunePage.Size = new System.Drawing.Size(296, 562);
        	this.tunePage.TabIndex = 4;
        	this.tunePage.Text = "Tune";
        	this.tunePage.UseVisualStyleBackColor = true;
        	// 
        	// tsTuner
        	// 
        	this.tsTuner.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.tsTuner.Location = new System.Drawing.Point(0, 0);
        	this.tsTuner.MinimumSize = new System.Drawing.Size(200, 0);
        	this.tsTuner.Name = "tsTuner";
        	this.tsTuner.Size = new System.Drawing.Size(296, 562);
        	this.tsTuner.TabIndex = 0;
        	// 
        	// searchPage
        	// 
        	this.searchPage.Controls.Add(this.tsSeeker);
        	this.searchPage.Location = new System.Drawing.Point(4, 22);
        	this.searchPage.Name = "searchPage";
        	this.searchPage.Size = new System.Drawing.Size(296, 562);
        	this.searchPage.TabIndex = 2;
        	this.searchPage.Text = "Search";
        	this.searchPage.UseVisualStyleBackColor = true;
        	// 
        	// tsSeeker
        	// 
        	this.tsSeeker.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.tsSeeker.Location = new System.Drawing.Point(0, 0);
        	this.tsSeeker.Name = "tsSeeker";
        	this.tsSeeker.Size = new System.Drawing.Size(296, 562);
        	this.tsSeeker.TabIndex = 0;
        	// 
        	// splitContainer
        	// 
        	this.splitContainer.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.splitContainer.FixedPanel = System.Windows.Forms.FixedPanel.Panel2;
        	this.splitContainer.Location = new System.Drawing.Point(0, 24);
        	this.splitContainer.Name = "splitContainer";
        	// 
        	// splitContainer.Panel1
        	// 
        	this.splitContainer.Panel1.Controls.Add(this.viewTabs);
        	// 
        	// splitContainer.Panel2
        	// 
        	this.splitContainer.Panel2.Controls.Add(this.operationTabs);
        	this.splitContainer.Size = new System.Drawing.Size(992, 620);
        	this.splitContainer.SplitterDistance = 670;
        	this.splitContainer.TabIndex = 5;
        	// 
        	// MainForm
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(992, 666);
        	this.Controls.Add(this.splitContainer);
        	this.Controls.Add(this.statusStrip);
        	this.Controls.Add(this.mainMenu);
        	this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        	this.MainMenuStrip = this.mainMenu;
        	this.MinimumSize = new System.Drawing.Size(640, 200);
        	this.Name = "MainForm";
        	this.Text = "Trading Simulator";
        	this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
        	this.Load += new System.EventHandler(this.MainForm_Load);
        	this.mainMenu.ResumeLayout(false);
        	this.mainMenu.PerformLayout();
        	this.statusStrip.ResumeLayout(false);
        	this.statusStrip.PerformLayout();
        	this.viewTabs.ResumeLayout(false);
        	this.dataPage.ResumeLayout(false);
        	this.dataTabs.ResumeLayout(false);
        	this.dataChartPage.ResumeLayout(false);
        	this.simulationsPage.ResumeLayout(false);
        	this.solutionListPanel.ResumeLayout(false);
        	this.solutionListActionPanel.ResumeLayout(false);
        	this.solutionListActionPanel.PerformLayout();
        	((System.ComponentModel.ISupportInitialize)(this.maxSolutionCount)).EndInit();
        	this.operationTabs.ResumeLayout(false);
        	this.dataSetsPage.ResumeLayout(false);
        	this.dataComponentTable.ResumeLayout(false);
        	this.dataComponentTable.PerformLayout();
        	this.tradingSystemPage.ResumeLayout(false);
        	this.parametersSimulatorTabs.ResumeLayout(false);
        	this.tunePage.ResumeLayout(false);
        	this.searchPage.ResumeLayout(false);
        	this.splitContainer.Panel1.ResumeLayout(false);
        	this.splitContainer.Panel2.ResumeLayout(false);
        	this.splitContainer.ResumeLayout(false);
        	this.ResumeLayout(false);
        	this.PerformLayout();
        }
        private System.Windows.Forms.ToolStripMenuItem relativeHistoryToolStripMenuItem;
        private TradingSimulator.Ui.TSSeekerControl tsSeeker;
        private System.Windows.Forms.ToolStripStatusLabel spacerLabel;
        private System.Windows.Forms.ToolStripStatusLabel progressLabel;
        private System.Windows.Forms.ToolStripStatusLabel cancelLabel;
        private TradingSimulator.Ui.TSTunerControl tsTuner;
        private System.Windows.Forms.TabPage tunePage;
        private System.Windows.Forms.TabPage searchPage;
        private System.Windows.Forms.ComboBox periodCombo;
        private System.Windows.Forms.TabPage dataChartPage;
        private System.Windows.Forms.TabControl dataTabs;
        private System.Windows.Forms.TabPage tradingSystemPage;
        private System.Windows.Forms.TabPage dataSetsPage;
        private System.Windows.Forms.TabControl operationTabs;
        private System.Windows.Forms.TabPage simulationsPage;
        private System.Windows.Forms.TabPage dataPage;
        private System.Windows.Forms.TabControl viewTabs;
        private System.Windows.Forms.SplitContainer splitContainer;
        private TradingSimulator.Ui.DataSetSelector trainDataSelector;
        private TradingSimulator.Ui.DataSetSelector testDataSelector;
        private System.Windows.Forms.ToolStripMenuItem joinMaturitiesToolStripMenuItem;
        private TradingSimulator.Ui.SolutionReportControl solutionReport;
        private System.Windows.Forms.ToolStripMenuItem tradeLogAnalyzerToolStripMenuItem;

        #endregion

        private System.Windows.Forms.MenuStrip mainMenu;
        private System.Windows.Forms.StatusStrip statusStrip;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TabControl parametersSimulatorTabs;
        private System.Windows.Forms.TableLayoutPanel dataComponentTable;
        private System.Windows.Forms.Panel solutionListPanel;
        private ZedGraph.ZedGraphControl sampleDataGraph;
        private System.Windows.Forms.ListView solutionList;
        private System.Windows.Forms.Panel solutionListActionPanel;
        private System.Windows.Forms.Button clearSolutionList;
        private System.Windows.Forms.NumericUpDown maxSolutionCount;
        private System.Windows.Forms.Label label10;
        private System.Windows.Forms.ColumnHeader solutionListColumn1;
        private System.Windows.Forms.ToolStripProgressBar statusProgressBar;
        private System.Windows.Forms.ToolStripMenuItem toolsToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem slippageAnalyserToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem searchSpaceExplorerToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem applicationToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitToolStripMenuItem;

    }
}

