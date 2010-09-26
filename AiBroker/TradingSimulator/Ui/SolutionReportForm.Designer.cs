
namespace TradingSimulator.Ui
{
    partial class SolutionReportForm
    {
        /// <summary>
        /// Designer variable used to keep track of non-visual components.
        /// </summary>
        private System.ComponentModel.IContainer components = null;
        
        /// <summary>
        /// Disposes resources used by the form.
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
        	System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SolutionReportForm));
        	this.solutionReport = new TradingSimulator.Ui.SolutionReportControl();
        	this.SuspendLayout();
        	// 
        	// solutionReport
        	// 
        	this.solutionReport.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.solutionReport.Location = new System.Drawing.Point(0, 0);
        	this.solutionReport.Name = "solutionReport";
        	this.solutionReport.Size = new System.Drawing.Size(892, 567);
        	this.solutionReport.TabIndex = 0;
        	// 
        	// SolutionReportForm
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(892, 567);
        	this.Controls.Add(this.solutionReport);
        	this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        	this.Name = "SolutionReportForm";
        	this.Text = "SolutionReportForm";
        	this.ResumeLayout(false);
        }
        private TradingSimulator.Ui.SolutionReportControl solutionReport;
    }
}
