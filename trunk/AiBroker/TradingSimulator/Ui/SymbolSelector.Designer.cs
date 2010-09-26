
namespace TradingSimulator.Ui
{
    partial class SymbolSelector
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
        	this.cbSymbols = new System.Windows.Forms.ComboBox();
        	this.btnOk = new System.Windows.Forms.Button();
        	this.btnCancel = new System.Windows.Forms.Button();
        	this.SuspendLayout();
        	// 
        	// cbSymbols
        	// 
        	this.cbSymbols.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
        	this.cbSymbols.FormattingEnabled = true;
        	this.cbSymbols.Location = new System.Drawing.Point(8, 8);
        	this.cbSymbols.Name = "cbSymbols";
        	this.cbSymbols.Size = new System.Drawing.Size(121, 21);
        	this.cbSymbols.TabIndex = 0;
        	this.cbSymbols.SelectedIndexChanged += new System.EventHandler(this.CbSymbolsSelectedIndexChanged);
        	// 
        	// btnOk
        	// 
        	this.btnOk.Enabled = false;
        	this.btnOk.Location = new System.Drawing.Point(144, 8);
        	this.btnOk.Name = "btnOk";
        	this.btnOk.Size = new System.Drawing.Size(75, 23);
        	this.btnOk.TabIndex = 1;
        	this.btnOk.Text = "OK";
        	this.btnOk.UseVisualStyleBackColor = true;
        	this.btnOk.Click += new System.EventHandler(this.BtnOkClick);
        	// 
        	// btnCancel
        	// 
        	this.btnCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
        	this.btnCancel.Location = new System.Drawing.Point(144, 40);
        	this.btnCancel.Name = "btnCancel";
        	this.btnCancel.Size = new System.Drawing.Size(75, 23);
        	this.btnCancel.TabIndex = 2;
        	this.btnCancel.Text = "Cancel";
        	this.btnCancel.UseVisualStyleBackColor = true;
        	this.btnCancel.Click += new System.EventHandler(this.BtnCancelClick);
        	// 
        	// SymbolSelector
        	// 
        	this.AcceptButton = this.btnOk;
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.CancelButton = this.btnCancel;
        	this.ClientSize = new System.Drawing.Size(230, 75);
        	this.ControlBox = false;
        	this.Controls.Add(this.btnCancel);
        	this.Controls.Add(this.btnOk);
        	this.Controls.Add(this.cbSymbols);
        	this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        	this.MaximizeBox = false;
        	this.MinimizeBox = false;
        	this.Name = "SymbolSelector";
        	this.ShowInTaskbar = false;
        	this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
        	this.Text = "Select symbol";
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.Button btnCancel;
        private System.Windows.Forms.Button btnOk;
        private System.Windows.Forms.ComboBox cbSymbols;
    }
}
