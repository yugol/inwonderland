namespace TradingCommon.Ui
{
    partial class ComboTickTitleSelector
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
            this.label1 = new System.Windows.Forms.Label();
            this.symbol = new System.Windows.Forms.ComboBox();
            this.label2 = new System.Windows.Forms.Label();
            this.maturity = new System.Windows.Forms.ComboBox();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(16, 20);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(44, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Symbol:";
            // 
            // symbol
            // 
            this.symbol.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.symbol.FormattingEnabled = true;
            this.symbol.Location = new System.Drawing.Point(72, 16);
            this.symbol.Name = "symbol";
            this.symbol.Size = new System.Drawing.Size(96, 21);
            this.symbol.Sorted = true;
            this.symbol.TabIndex = 1;
            this.symbol.SelectedIndexChanged += new System.EventHandler(this.symbol_SelectedIndexChanged);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(192, 20);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(47, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Maturity:";
            // 
            // maturity
            // 
            this.maturity.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.maturity.FormattingEnabled = true;
            this.maturity.Location = new System.Drawing.Point(248, 16);
            this.maturity.Name = "maturity";
            this.maturity.Size = new System.Drawing.Size(96, 21);
            this.maturity.TabIndex = 3;
            // 
            // ComboTickTitleSelector
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.maturity);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.symbol);
            this.Controls.Add(this.label1);
            this.Name = "ComboTickTitleSelector";
            this.Size = new System.Drawing.Size(362, 50);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.ComboBox symbol;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox maturity;
    }
}
