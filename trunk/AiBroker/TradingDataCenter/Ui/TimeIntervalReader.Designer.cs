namespace TradingDataCenter.Ui
{
    partial class TimeIntervalReader
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
        	System.Windows.Forms.TableLayoutPanel mainPanel;
        	System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TimeIntervalReader));
        	this.label1 = new System.Windows.Forms.Label();
        	this.label2 = new System.Windows.Forms.Label();
        	this.from = new System.Windows.Forms.MonthCalendar();
        	this.to = new System.Windows.Forms.MonthCalendar();
        	this.ok = new System.Windows.Forms.Button();
        	this.panel1 = new System.Windows.Forms.Panel();
        	mainPanel = new System.Windows.Forms.TableLayoutPanel();
        	mainPanel.SuspendLayout();
        	this.panel1.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// mainPanel
        	// 
        	mainPanel.ColumnCount = 2;
        	mainPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
        	mainPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 50F));
        	mainPanel.Controls.Add(this.label1, 0, 0);
        	mainPanel.Controls.Add(this.label2, 1, 0);
        	mainPanel.Controls.Add(this.from, 0, 1);
        	mainPanel.Controls.Add(this.to, 1, 1);
        	mainPanel.Controls.Add(this.ok, 0, 2);
        	mainPanel.Dock = System.Windows.Forms.DockStyle.Fill;
        	mainPanel.Location = new System.Drawing.Point(10, 10);
        	mainPanel.Name = "mainPanel";
        	mainPanel.RowCount = 3;
        	mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
        	mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
        	mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
        	mainPanel.Size = new System.Drawing.Size(499, 230);
        	mainPanel.TabIndex = 0;
        	// 
        	// label1
        	// 
        	this.label1.AutoSize = true;
        	this.label1.Location = new System.Drawing.Point(3, 0);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(33, 13);
        	this.label1.TabIndex = 0;
        	this.label1.Text = "From:";
        	// 
        	// label2
        	// 
        	this.label2.AutoSize = true;
        	this.label2.Location = new System.Drawing.Point(252, 0);
        	this.label2.Name = "label2";
        	this.label2.Size = new System.Drawing.Size(23, 13);
        	this.label2.TabIndex = 1;
        	this.label2.Text = "To:";
        	// 
        	// from
        	// 
        	this.from.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.from.Location = new System.Drawing.Point(9, 22);
        	this.from.MaxSelectionCount = 1;
        	this.from.Name = "from";
        	this.from.TabIndex = 2;
        	// 
        	// to
        	// 
        	this.to.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.to.Location = new System.Drawing.Point(258, 22);
        	this.to.Name = "to";
        	this.to.TabIndex = 3;
        	// 
        	// ok
        	// 
        	this.ok.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        	mainPanel.SetColumnSpan(this.ok, 2);
        	this.ok.Location = new System.Drawing.Point(212, 204);
        	this.ok.Name = "ok";
        	this.ok.Size = new System.Drawing.Size(75, 23);
        	this.ok.TabIndex = 4;
        	this.ok.Text = "OK";
        	this.ok.UseVisualStyleBackColor = true;
        	this.ok.Click += new System.EventHandler(this.ok_Click);
        	// 
        	// panel1
        	// 
        	this.panel1.Controls.Add(mainPanel);
        	this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.panel1.Location = new System.Drawing.Point(0, 0);
        	this.panel1.Name = "panel1";
        	this.panel1.Padding = new System.Windows.Forms.Padding(10);
        	this.panel1.Size = new System.Drawing.Size(519, 250);
        	this.panel1.TabIndex = 1;
        	// 
        	// TimeIntervalReader
        	// 
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(519, 250);
        	this.Controls.Add(this.panel1);
        	this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        	this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
        	this.MaximizeBox = false;
        	this.MinimizeBox = false;
        	this.Name = "TimeIntervalReader";
        	this.ShowInTaskbar = false;
        	this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        	this.Text = "TimeIntervalReader";
        	mainPanel.ResumeLayout(false);
        	mainPanel.PerformLayout();
        	this.panel1.ResumeLayout(false);
        	this.ResumeLayout(false);
        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.MonthCalendar from;
        private System.Windows.Forms.MonthCalendar to;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Button ok;
    }
}