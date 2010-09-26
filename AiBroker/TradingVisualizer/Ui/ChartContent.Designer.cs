namespace TradingVisualizer.Ui
{
    partial class ChartContent
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
        	this.chart = new Tao.Platform.Windows.SimpleOpenGlControl();
        	this.yScale = new Tao.Platform.Windows.SimpleOpenGlControl();
        	this.SuspendLayout();
        	// 
        	// chart
        	// 
        	this.chart.AccumBits = ((byte)(0));
        	this.chart.AutoCheckErrors = false;
        	this.chart.AutoFinish = false;
        	this.chart.AutoMakeCurrent = true;
        	this.chart.AutoSwapBuffers = true;
        	this.chart.BackColor = System.Drawing.Color.LightGray;
        	this.chart.ColorBits = ((byte)(16));
        	this.chart.DepthBits = ((byte)(16));
        	this.chart.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.chart.Location = new System.Drawing.Point(0, 0);
        	this.chart.Name = "chart";
        	this.chart.Size = new System.Drawing.Size(200, 261);
        	this.chart.StencilBits = ((byte)(0));
        	this.chart.TabIndex = 0;
        	this.chart.MouseLeave += new System.EventHandler(this.chart_MouseLeave);
        	this.chart.Paint += new System.Windows.Forms.PaintEventHandler(this.chart_Paint);
        	this.chart.MouseMove += new System.Windows.Forms.MouseEventHandler(this.chart_MouseMove);
        	// 
        	// yScale
        	// 
        	this.yScale.AccumBits = ((byte)(0));
        	this.yScale.AutoCheckErrors = false;
        	this.yScale.AutoFinish = false;
        	this.yScale.AutoMakeCurrent = true;
        	this.yScale.AutoSwapBuffers = true;
        	this.yScale.BackColor = System.Drawing.SystemColors.Control;
        	this.yScale.ColorBits = ((byte)(16));
        	this.yScale.DepthBits = ((byte)(16));
        	this.yScale.Dock = System.Windows.Forms.DockStyle.Right;
        	this.yScale.Location = new System.Drawing.Point(200, 0);
        	this.yScale.Name = "yScale";
        	this.yScale.Size = new System.Drawing.Size(130, 261);
        	this.yScale.StencilBits = ((byte)(0));
        	this.yScale.TabIndex = 1;
        	this.yScale.Paint += new System.Windows.Forms.PaintEventHandler(this.yScale_Paint);
        	// 
        	// ChartContent
        	// 
        	this.Controls.Add(this.chart);
        	this.Controls.Add(this.yScale);
        	this.Name = "ChartContent";
        	this.Size = new System.Drawing.Size(330, 261);
        	this.Paint += new System.Windows.Forms.PaintEventHandler(this.ChartContent_Paint);
        	this.ResumeLayout(false);
        }

        #endregion

        private Tao.Platform.Windows.SimpleOpenGlControl chart;
        private Tao.Platform.Windows.SimpleOpenGlControl yScale;

    }
}
