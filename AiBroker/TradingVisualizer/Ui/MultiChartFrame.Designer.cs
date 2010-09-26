namespace TradingVisualizer.Ui
{
    partial class MultiChartFrame
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
            this.multiChartControl = new TradingVisualizer.Ui.MultiChartControl();
            this.SuspendLayout();
            // 
            // multiChartControl
            // 
            this.multiChartControl.Dock = System.Windows.Forms.DockStyle.Fill;
            this.multiChartControl.Location = new System.Drawing.Point(0, 0);
            this.multiChartControl.Name = "multiChartControl";
            this.multiChartControl.Period = -2147483648;
            this.multiChartControl.Size = new System.Drawing.Size(474, 300);
            this.multiChartControl.TabIndex = 0;
            this.multiChartControl.WindowSplits = new float[] {
        100F};
            // 
            // ChartFrame
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(474, 300);
            this.Controls.Add(this.multiChartControl);
            this.DockAreas = WeifenLuo.WinFormsUI.Docking.DockAreas.Document;
            this.Name = "ChartFrame";
            this.TabText = "Chart";
            this.Text = "ChartFrame";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.ChartFrame_FormClosing);
            this.ResumeLayout(false);

        }

        #endregion

        private MultiChartControl multiChartControl;
    }
}