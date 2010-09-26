namespace TradingVisualizer.Ui
{
    partial class MultiChartControl
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MultiChartControl));
            this.xScale = new Tao.Platform.Windows.SimpleOpenGlControl();
            this.xScalePanel = new System.Windows.Forms.Panel();
            this.xScaleSpacerPanel = new System.Windows.Forms.Panel();
            this.navigationPanel = new System.Windows.Forms.Panel();
            this.navigationBar = new System.Windows.Forms.HScrollBar();
            this.navigationControlsPanel = new System.Windows.Forms.Panel();
            this.crosshair = new System.Windows.Forms.Button();
            this.navigationImages = new System.Windows.Forms.ImageList(this.components);
            this.zoomAll = new System.Windows.Forms.Button();
            this.zoomOut = new System.Windows.Forms.Button();
            this.zoomIn = new System.Windows.Forms.Button();
            this.pad = new System.Windows.Forms.Button();
            this.periodButton = new System.Windows.Forms.Button();
            this.refresh = new System.Windows.Forms.Button();
            this.periodMenu = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.tickToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem1 = new System.Windows.Forms.ToolStripSeparator();
            this.minutesToolStripMenuItem_1 = new System.Windows.Forms.ToolStripMenuItem();
            this.minutesToolStripMenuItem_5 = new System.Windows.Forms.ToolStripMenuItem();
            this.minutesToolStripMenuItem_10 = new System.Windows.Forms.ToolStripMenuItem();
            this.minutesToolStripMenuItem_15 = new System.Windows.Forms.ToolStripMenuItem();
            this.minutesToolStripMenuItem_20 = new System.Windows.Forms.ToolStripMenuItem();
            this.minutesToolStripMenuItem_30 = new System.Windows.Forms.ToolStripMenuItem();
            this.minutesToolStripMenuItem_60 = new System.Windows.Forms.ToolStripMenuItem();
            this.dailyfromTicksToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.toolStripMenuItem2 = new System.Windows.Forms.ToolStripSeparator();
            this.dailyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.weeklyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.monthlyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.yearlyToolStripMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.multiChartPanel = new TradingVisualizer.Ui.MultiChartPanel();
            this.xScalePanel.SuspendLayout();
            this.navigationPanel.SuspendLayout();
            this.navigationControlsPanel.SuspendLayout();
            this.periodMenu.SuspendLayout();
            this.SuspendLayout();
            // 
            // xScale
            // 
            this.xScale.AccumBits = ((byte)(0));
            this.xScale.AutoCheckErrors = false;
            this.xScale.AutoFinish = false;
            this.xScale.AutoMakeCurrent = true;
            this.xScale.AutoSwapBuffers = true;
            this.xScale.BackColor = System.Drawing.Color.OliveDrab;
            this.xScale.ColorBits = ((byte)(32));
            this.xScale.DepthBits = ((byte)(16));
            this.xScale.Dock = System.Windows.Forms.DockStyle.Fill;
            this.xScale.Location = new System.Drawing.Point(0, 0);
            this.xScale.Name = "xScale";
            this.xScale.Size = new System.Drawing.Size(688, 32);
            this.xScale.StencilBits = ((byte)(0));
            this.xScale.TabIndex = 0;
            this.xScale.Paint += new System.Windows.Forms.PaintEventHandler(this.xScale_Paint);
            this.xScale.MouseDown += new System.Windows.Forms.MouseEventHandler(this.xScale_MouseDown);
            // 
            // xScalePanel
            // 
            this.xScalePanel.Controls.Add(this.xScale);
            this.xScalePanel.Controls.Add(this.xScaleSpacerPanel);
            this.xScalePanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.xScalePanel.Location = new System.Drawing.Point(0, 471);
            this.xScalePanel.Name = "xScalePanel";
            this.xScalePanel.Size = new System.Drawing.Size(817, 32);
            this.xScalePanel.TabIndex = 2;
            // 
            // xScaleSpacerPanel
            // 
            this.xScaleSpacerPanel.Dock = System.Windows.Forms.DockStyle.Right;
            this.xScaleSpacerPanel.Location = new System.Drawing.Point(688, 0);
            this.xScaleSpacerPanel.Name = "xScaleSpacerPanel";
            this.xScaleSpacerPanel.Size = new System.Drawing.Size(129, 32);
            this.xScaleSpacerPanel.TabIndex = 1;
            // 
            // navigationPanel
            // 
            this.navigationPanel.Controls.Add(this.navigationBar);
            this.navigationPanel.Controls.Add(this.navigationControlsPanel);
            this.navigationPanel.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.navigationPanel.Location = new System.Drawing.Point(0, 503);
            this.navigationPanel.MaximumSize = new System.Drawing.Size(0, 24);
            this.navigationPanel.MinimumSize = new System.Drawing.Size(300, 24);
            this.navigationPanel.Name = "navigationPanel";
            this.navigationPanel.Size = new System.Drawing.Size(817, 24);
            this.navigationPanel.TabIndex = 3;
            // 
            // navigationBar
            // 
            this.navigationBar.Dock = System.Windows.Forms.DockStyle.Fill;
            this.navigationBar.Location = new System.Drawing.Point(0, 0);
            this.navigationBar.Name = "navigationBar";
            this.navigationBar.Size = new System.Drawing.Size(616, 24);
            this.navigationBar.TabIndex = 0;
            this.navigationBar.Scroll += new System.Windows.Forms.ScrollEventHandler(this.navigationBar_Scroll);
            // 
            // navigationControlsPanel
            // 
            this.navigationControlsPanel.Controls.Add(this.crosshair);
            this.navigationControlsPanel.Controls.Add(this.zoomAll);
            this.navigationControlsPanel.Controls.Add(this.zoomOut);
            this.navigationControlsPanel.Controls.Add(this.zoomIn);
            this.navigationControlsPanel.Controls.Add(this.pad);
            this.navigationControlsPanel.Controls.Add(this.periodButton);
            this.navigationControlsPanel.Controls.Add(this.refresh);
            this.navigationControlsPanel.Dock = System.Windows.Forms.DockStyle.Right;
            this.navigationControlsPanel.Location = new System.Drawing.Point(616, 0);
            this.navigationControlsPanel.Margin = new System.Windows.Forms.Padding(0);
            this.navigationControlsPanel.Name = "navigationControlsPanel";
            this.navigationControlsPanel.Size = new System.Drawing.Size(201, 24);
            this.navigationControlsPanel.TabIndex = 1;
            // 
            // crosshair
            // 
            this.crosshair.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.crosshair.FlatAppearance.BorderColor = System.Drawing.SystemColors.Control;
            this.crosshair.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.crosshair.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.ControlDark;
            this.crosshair.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.crosshair.ImageIndex = 0;
            this.crosshair.ImageList = this.navigationImages;
            this.crosshair.Location = new System.Drawing.Point(24, 0);
            this.crosshair.Name = "crosshair";
            this.crosshair.Size = new System.Drawing.Size(27, 22);
            this.crosshair.TabIndex = 6;
            this.crosshair.UseVisualStyleBackColor = true;
            this.crosshair.Click += new System.EventHandler(this.crosshair_Click);
            // 
            // navigationImages
            // 
            this.navigationImages.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("navigationImages.ImageStream")));
            this.navigationImages.TransparentColor = System.Drawing.Color.Transparent;
            this.navigationImages.Images.SetKeyName(0, "CrossHair13.gif");
            this.navigationImages.Images.SetKeyName(1, "Refresh13.gif");
            this.navigationImages.Images.SetKeyName(2, "Pad13.gif");
            this.navigationImages.Images.SetKeyName(3, "ZoomIn13.gif");
            this.navigationImages.Images.SetKeyName(4, "ZoomOut13.gif");
            this.navigationImages.Images.SetKeyName(5, "ZoomAll13.gif");
            // 
            // zoomAll
            // 
            this.zoomAll.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.zoomAll.FlatAppearance.BorderColor = System.Drawing.SystemColors.Control;
            this.zoomAll.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.zoomAll.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.ControlDark;
            this.zoomAll.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.zoomAll.ImageIndex = 5;
            this.zoomAll.ImageList = this.navigationImages;
            this.zoomAll.Location = new System.Drawing.Point(168, 1);
            this.zoomAll.Name = "zoomAll";
            this.zoomAll.Size = new System.Drawing.Size(27, 22);
            this.zoomAll.TabIndex = 5;
            this.zoomAll.UseVisualStyleBackColor = true;
            this.zoomAll.Click += new System.EventHandler(this.zoomAll_Click);
            // 
            // zoomOut
            // 
            this.zoomOut.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.zoomOut.FlatAppearance.BorderColor = System.Drawing.SystemColors.Control;
            this.zoomOut.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.zoomOut.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.ControlDark;
            this.zoomOut.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.zoomOut.ImageIndex = 4;
            this.zoomOut.ImageList = this.navigationImages;
            this.zoomOut.Location = new System.Drawing.Point(144, 1);
            this.zoomOut.Name = "zoomOut";
            this.zoomOut.Size = new System.Drawing.Size(27, 22);
            this.zoomOut.TabIndex = 4;
            this.zoomOut.UseVisualStyleBackColor = true;
            this.zoomOut.Click += new System.EventHandler(this.zoomOut_Click);
            // 
            // zoomIn
            // 
            this.zoomIn.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.zoomIn.FlatAppearance.BorderColor = System.Drawing.SystemColors.Control;
            this.zoomIn.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.zoomIn.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.ControlDark;
            this.zoomIn.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.zoomIn.ImageIndex = 3;
            this.zoomIn.ImageList = this.navigationImages;
            this.zoomIn.Location = new System.Drawing.Point(120, 1);
            this.zoomIn.Name = "zoomIn";
            this.zoomIn.Size = new System.Drawing.Size(27, 22);
            this.zoomIn.TabIndex = 3;
            this.zoomIn.UseVisualStyleBackColor = true;
            this.zoomIn.Click += new System.EventHandler(this.zoomIn_Click);
            // 
            // pad
            // 
            this.pad.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.pad.FlatAppearance.BorderColor = System.Drawing.SystemColors.Control;
            this.pad.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.pad.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.ControlDark;
            this.pad.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.pad.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.pad.ImageIndex = 2;
            this.pad.ImageList = this.navigationImages;
            this.pad.Location = new System.Drawing.Point(88, 1);
            this.pad.Margin = new System.Windows.Forms.Padding(0);
            this.pad.Name = "pad";
            this.pad.Size = new System.Drawing.Size(27, 22);
            this.pad.TabIndex = 2;
            this.pad.UseVisualStyleBackColor = true;
            // 
            // periodButton
            // 
            this.periodButton.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.periodButton.FlatAppearance.BorderColor = System.Drawing.SystemColors.Control;
            this.periodButton.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.periodButton.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.ControlDark;
            this.periodButton.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.periodButton.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.periodButton.Location = new System.Drawing.Point(56, 1);
            this.periodButton.Margin = new System.Windows.Forms.Padding(0);
            this.periodButton.Name = "periodButton";
            this.periodButton.Size = new System.Drawing.Size(35, 22);
            this.periodButton.TabIndex = 1;
            this.periodButton.Text = "O";
            this.periodButton.UseVisualStyleBackColor = true;
            this.periodButton.Click += new System.EventHandler(this.period_Click);
            // 
            // refresh
            // 
            this.refresh.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Left | System.Windows.Forms.AnchorStyles.Right)));
            this.refresh.FlatAppearance.BorderColor = System.Drawing.SystemColors.Control;
            this.refresh.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.ControlDarkDark;
            this.refresh.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.ControlDark;
            this.refresh.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.refresh.ImageIndex = 1;
            this.refresh.ImageList = this.navigationImages;
            this.refresh.Location = new System.Drawing.Point(0, 0);
            this.refresh.Name = "refresh";
            this.refresh.Size = new System.Drawing.Size(27, 22);
            this.refresh.TabIndex = 0;
            this.refresh.UseVisualStyleBackColor = true;
            this.refresh.Click += new System.EventHandler(this.refresh_Click);
            // 
            // periodMenu
            // 
            this.periodMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.tickToolStripMenuItem,
            this.toolStripMenuItem1,
            this.minutesToolStripMenuItem_1,
            this.minutesToolStripMenuItem_5,
            this.minutesToolStripMenuItem_10,
            this.minutesToolStripMenuItem_15,
            this.minutesToolStripMenuItem_20,
            this.minutesToolStripMenuItem_30,
            this.minutesToolStripMenuItem_60,
            this.dailyfromTicksToolStripMenuItem,
            this.toolStripMenuItem2,
            this.dailyToolStripMenuItem,
            this.weeklyToolStripMenuItem,
            this.monthlyToolStripMenuItem,
            this.yearlyToolStripMenuItem});
            this.periodMenu.Name = "periodMenu";
            this.periodMenu.Size = new System.Drawing.Size(155, 302);
            // 
            // tickToolStripMenuItem
            // 
            this.tickToolStripMenuItem.CheckOnClick = true;
            this.tickToolStripMenuItem.Name = "tickToolStripMenuItem";
            this.tickToolStripMenuItem.Size = new System.Drawing.Size(154, 22);
            this.tickToolStripMenuItem.Text = "Tick";
            this.tickToolStripMenuItem.Click += new System.EventHandler(this.tickToolStripMenuItem_Click);
            // 
            // toolStripMenuItem1
            // 
            this.toolStripMenuItem1.Name = "toolStripMenuItem1";
            this.toolStripMenuItem1.Size = new System.Drawing.Size(151, 6);
            // 
            // minutesToolStripMenuItem_1
            // 
            this.minutesToolStripMenuItem_1.CheckOnClick = true;
            this.minutesToolStripMenuItem_1.Name = "minutesToolStripMenuItem_1";
            this.minutesToolStripMenuItem_1.Size = new System.Drawing.Size(154, 22);
            this.minutesToolStripMenuItem_1.Text = "1 - minute";
            this.minutesToolStripMenuItem_1.Click += new System.EventHandler(this.minutesToolStripMenuItem_1_Click);
            // 
            // minutesToolStripMenuItem_5
            // 
            this.minutesToolStripMenuItem_5.CheckOnClick = true;
            this.minutesToolStripMenuItem_5.Name = "minutesToolStripMenuItem_5";
            this.minutesToolStripMenuItem_5.Size = new System.Drawing.Size(154, 22);
            this.minutesToolStripMenuItem_5.Text = "5 - minutes";
            this.minutesToolStripMenuItem_5.Click += new System.EventHandler(this.minutesToolStripMenuItem_5_Click);
            // 
            // minutesToolStripMenuItem_10
            // 
            this.minutesToolStripMenuItem_10.CheckOnClick = true;
            this.minutesToolStripMenuItem_10.Name = "minutesToolStripMenuItem_10";
            this.minutesToolStripMenuItem_10.Size = new System.Drawing.Size(154, 22);
            this.minutesToolStripMenuItem_10.Text = "10 - minutes";
            this.minutesToolStripMenuItem_10.Click += new System.EventHandler(this.minutesToolStripMenuItem_10_Click);
            // 
            // minutesToolStripMenuItem_15
            // 
            this.minutesToolStripMenuItem_15.CheckOnClick = true;
            this.minutesToolStripMenuItem_15.Name = "minutesToolStripMenuItem_15";
            this.minutesToolStripMenuItem_15.Size = new System.Drawing.Size(154, 22);
            this.minutesToolStripMenuItem_15.Text = "15 - minutes";
            this.minutesToolStripMenuItem_15.Click += new System.EventHandler(this.minutesToolStripMenuItem_15_Click);
            // 
            // minutesToolStripMenuItem_20
            // 
            this.minutesToolStripMenuItem_20.CheckOnClick = true;
            this.minutesToolStripMenuItem_20.Name = "minutesToolStripMenuItem_20";
            this.minutesToolStripMenuItem_20.Size = new System.Drawing.Size(154, 22);
            this.minutesToolStripMenuItem_20.Text = "20 - minutes";
            this.minutesToolStripMenuItem_20.Click += new System.EventHandler(this.minutesToolStripMenuItem_20_Click);
            // 
            // minutesToolStripMenuItem_30
            // 
            this.minutesToolStripMenuItem_30.CheckOnClick = true;
            this.minutesToolStripMenuItem_30.Name = "minutesToolStripMenuItem_30";
            this.minutesToolStripMenuItem_30.Size = new System.Drawing.Size(154, 22);
            this.minutesToolStripMenuItem_30.Text = "30 - minutes";
            this.minutesToolStripMenuItem_30.Click += new System.EventHandler(this.minutesToolStripMenuItem_30_Click);
            // 
            // minutesToolStripMenuItem_60
            // 
            this.minutesToolStripMenuItem_60.CheckOnClick = true;
            this.minutesToolStripMenuItem_60.Name = "minutesToolStripMenuItem_60";
            this.minutesToolStripMenuItem_60.Size = new System.Drawing.Size(154, 22);
            this.minutesToolStripMenuItem_60.Text = "60 - minutes";
            this.minutesToolStripMenuItem_60.Click += new System.EventHandler(this.minutesToolStripMenuItem_60_Click);
            // 
            // dailyfromTicksToolStripMenuItem
            // 
            this.dailyfromTicksToolStripMenuItem.Name = "dailyfromTicksToolStripMenuItem";
            this.dailyfromTicksToolStripMenuItem.Size = new System.Drawing.Size(154, 22);
            this.dailyfromTicksToolStripMenuItem.Text = "Daily (from ticks)";
            this.dailyfromTicksToolStripMenuItem.Click += new System.EventHandler(this.dailyfromTicksToolStripMenuItem_Click);
            // 
            // toolStripMenuItem2
            // 
            this.toolStripMenuItem2.Name = "toolStripMenuItem2";
            this.toolStripMenuItem2.Size = new System.Drawing.Size(151, 6);
            // 
            // dailyToolStripMenuItem
            // 
            this.dailyToolStripMenuItem.CheckOnClick = true;
            this.dailyToolStripMenuItem.Name = "dailyToolStripMenuItem";
            this.dailyToolStripMenuItem.Size = new System.Drawing.Size(154, 22);
            this.dailyToolStripMenuItem.Text = "Daily";
            this.dailyToolStripMenuItem.Click += new System.EventHandler(this.dailyToolStripMenuItem_Click);
            // 
            // weeklyToolStripMenuItem
            // 
            this.weeklyToolStripMenuItem.CheckOnClick = true;
            this.weeklyToolStripMenuItem.Name = "weeklyToolStripMenuItem";
            this.weeklyToolStripMenuItem.Size = new System.Drawing.Size(154, 22);
            this.weeklyToolStripMenuItem.Text = "Weekly";
            this.weeklyToolStripMenuItem.Click += new System.EventHandler(this.weeklyToolStripMenuItem_Click);
            // 
            // monthlyToolStripMenuItem
            // 
            this.monthlyToolStripMenuItem.CheckOnClick = true;
            this.monthlyToolStripMenuItem.Name = "monthlyToolStripMenuItem";
            this.monthlyToolStripMenuItem.Size = new System.Drawing.Size(154, 22);
            this.monthlyToolStripMenuItem.Text = "Monthly";
            this.monthlyToolStripMenuItem.Click += new System.EventHandler(this.monthlyToolStripMenuItem_Click);
            // 
            // yearlyToolStripMenuItem
            // 
            this.yearlyToolStripMenuItem.CheckOnClick = true;
            this.yearlyToolStripMenuItem.Name = "yearlyToolStripMenuItem";
            this.yearlyToolStripMenuItem.Size = new System.Drawing.Size(154, 22);
            this.yearlyToolStripMenuItem.Text = "Yearly";
            this.yearlyToolStripMenuItem.Click += new System.EventHandler(this.yearlyToolStripMenuItem_Click);
            // 
            // multiChartPanel
            // 
            this.multiChartPanel.ColumnCount = 1;
            this.multiChartPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.multiChartPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
            this.multiChartPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.multiChartPanel.Location = new System.Drawing.Point(0, 0);
            this.multiChartPanel.Margin = new System.Windows.Forms.Padding(0);
            this.multiChartPanel.Name = "multiChartPanel";
            this.multiChartPanel.RowCount = 2;
            this.multiChartPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 18F));
            this.multiChartPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 452F));
            this.multiChartPanel.Size = new System.Drawing.Size(817, 471);
            this.multiChartPanel.TabIndex = 1;
            this.multiChartPanel.TitleHeight = 18;
            this.multiChartPanel.WindowSplits = new float[] {
        100F};
            this.multiChartPanel.YScaleWidth = 130;
            // 
            // MultiChartControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.multiChartPanel);
            this.Controls.Add(this.xScalePanel);
            this.Controls.Add(this.navigationPanel);
            this.Name = "MultiChartControl";
            this.Size = new System.Drawing.Size(817, 527);
            this.ParentChanged += new System.EventHandler(this.MultiChartControl_ParentChanged);
            this.Resize += new System.EventHandler(this.MultiChartControl_Resize);
            this.xScalePanel.ResumeLayout(false);
            this.navigationPanel.ResumeLayout(false);
            this.navigationControlsPanel.ResumeLayout(false);
            this.periodMenu.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private Tao.Platform.Windows.SimpleOpenGlControl xScale;
        private MultiChartPanel multiChartPanel;
        private System.Windows.Forms.Panel xScalePanel;
        private System.Windows.Forms.Panel xScaleSpacerPanel;
        private System.Windows.Forms.Panel navigationPanel;
        private System.Windows.Forms.HScrollBar navigationBar;
        private System.Windows.Forms.Panel navigationControlsPanel;
        private System.Windows.Forms.Button refresh;
        private System.Windows.Forms.Button crosshair;
        private System.Windows.Forms.Button zoomAll;
        private System.Windows.Forms.Button zoomOut;
        private System.Windows.Forms.Button zoomIn;
        private System.Windows.Forms.Button pad;
        private System.Windows.Forms.Button periodButton;
        private System.Windows.Forms.ImageList navigationImages;
        private System.Windows.Forms.ContextMenuStrip periodMenu;
        private System.Windows.Forms.ToolStripMenuItem tickToolStripMenuItem;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem1;
        private System.Windows.Forms.ToolStripMenuItem minutesToolStripMenuItem_1;
        private System.Windows.Forms.ToolStripMenuItem minutesToolStripMenuItem_5;
        private System.Windows.Forms.ToolStripMenuItem minutesToolStripMenuItem_10;
        private System.Windows.Forms.ToolStripMenuItem minutesToolStripMenuItem_15;
        private System.Windows.Forms.ToolStripMenuItem minutesToolStripMenuItem_20;
        private System.Windows.Forms.ToolStripMenuItem minutesToolStripMenuItem_30;
        private System.Windows.Forms.ToolStripMenuItem minutesToolStripMenuItem_60;
        private System.Windows.Forms.ToolStripSeparator toolStripMenuItem2;
        private System.Windows.Forms.ToolStripMenuItem dailyToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem weeklyToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem monthlyToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem yearlyToolStripMenuItem;
        private System.Windows.Forms.ToolStripMenuItem dailyfromTicksToolStripMenuItem;
    }
}
