namespace TradingVisualizer.Ui
{
    partial class DeltaSelectionForm
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
            this.deltaGridEnabled = new System.Windows.Forms.CheckBox();
            this.std = new System.Windows.Forms.RadioButton();
            this.itd = new System.Windows.Forms.RadioButton();
            this.mtd = new System.Windows.Forms.RadioButton();
            this.ltd = new System.Windows.Forms.RadioButton();
            this.sltd = new System.Windows.Forms.RadioButton();
            this.close = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // deltaGridEnabled
            // 
            this.deltaGridEnabled.AutoSize = true;
            this.deltaGridEnabled.Location = new System.Drawing.Point(16, 16);
            this.deltaGridEnabled.Name = "deltaGridEnabled";
            this.deltaGridEnabled.Size = new System.Drawing.Size(112, 17);
            this.deltaGridEnabled.TabIndex = 0;
            this.deltaGridEnabled.Text = "Delta grid enabled";
            this.deltaGridEnabled.UseVisualStyleBackColor = true;
            this.deltaGridEnabled.CheckedChanged += new System.EventHandler(this.deltaGridEnabled_CheckedChanged);
            // 
            // std
            // 
            this.std.AutoSize = true;
            this.std.Location = new System.Drawing.Point(56, 40);
            this.std.Name = "std";
            this.std.Size = new System.Drawing.Size(99, 17);
            this.std.TabIndex = 1;
            this.std.TabStop = true;
            this.std.Text = "Short term delta";
            this.std.UseVisualStyleBackColor = true;
            this.std.CheckedChanged += new System.EventHandler(this.std_CheckedChanged);
            // 
            // itd
            // 
            this.itd.AutoSize = true;
            this.itd.Location = new System.Drawing.Point(56, 64);
            this.itd.Name = "itd";
            this.itd.Size = new System.Drawing.Size(132, 17);
            this.itd.TabIndex = 2;
            this.itd.TabStop = true;
            this.itd.Text = "Intermediate term delta";
            this.itd.UseVisualStyleBackColor = true;
            this.itd.CheckedChanged += new System.EventHandler(this.itd_CheckedChanged);
            // 
            // mtd
            // 
            this.mtd.AutoSize = true;
            this.mtd.Location = new System.Drawing.Point(56, 88);
            this.mtd.Name = "mtd";
            this.mtd.Size = new System.Drawing.Size(111, 17);
            this.mtd.TabIndex = 3;
            this.mtd.TabStop = true;
            this.mtd.Text = "Medium term delta";
            this.mtd.UseVisualStyleBackColor = true;
            this.mtd.CheckedChanged += new System.EventHandler(this.mtd_CheckedChanged);
            // 
            // ltd
            // 
            this.ltd.AutoSize = true;
            this.ltd.Location = new System.Drawing.Point(56, 112);
            this.ltd.Name = "ltd";
            this.ltd.Size = new System.Drawing.Size(98, 17);
            this.ltd.TabIndex = 4;
            this.ltd.TabStop = true;
            this.ltd.Text = "Long term delta";
            this.ltd.UseVisualStyleBackColor = true;
            this.ltd.CheckedChanged += new System.EventHandler(this.ltd_CheckedChanged);
            // 
            // sltd
            // 
            this.sltd.AutoSize = true;
            this.sltd.Location = new System.Drawing.Point(56, 136);
            this.sltd.Name = "sltd";
            this.sltd.Size = new System.Drawing.Size(125, 17);
            this.sltd.TabIndex = 5;
            this.sltd.TabStop = true;
            this.sltd.Text = "Super long term delta";
            this.sltd.UseVisualStyleBackColor = true;
            this.sltd.CheckedChanged += new System.EventHandler(this.sltd_CheckedChanged);
            // 
            // close
            // 
            this.close.Location = new System.Drawing.Point(216, 16);
            this.close.Name = "close";
            this.close.Size = new System.Drawing.Size(75, 23);
            this.close.TabIndex = 6;
            this.close.Text = "Close";
            this.close.UseVisualStyleBackColor = true;
            this.close.Click += new System.EventHandler(this.close_Click);
            // 
            // DeltaSelectionForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(305, 172);
            this.ControlBox = false;
            this.Controls.Add(this.close);
            this.Controls.Add(this.sltd);
            this.Controls.Add(this.ltd);
            this.Controls.Add(this.mtd);
            this.Controls.Add(this.itd);
            this.Controls.Add(this.std);
            this.Controls.Add(this.deltaGridEnabled);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "DeltaSelectionForm";
            this.RightToLeftLayout = true;
            this.ShowInTaskbar = false;
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Delta grid selection";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.CheckBox deltaGridEnabled;
        private System.Windows.Forms.RadioButton std;
        private System.Windows.Forms.RadioButton itd;
        private System.Windows.Forms.RadioButton mtd;
        private System.Windows.Forms.RadioButton ltd;
        private System.Windows.Forms.RadioButton sltd;
        private System.Windows.Forms.Button close;
    }
}