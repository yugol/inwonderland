namespace TradingAgent.Ui
{
    partial class LoginForm
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
        	this.label1 = new System.Windows.Forms.Label();
        	this.label2 = new System.Windows.Forms.Label();
        	this.label3 = new System.Windows.Forms.Label();
        	this.mainPanel = new System.Windows.Forms.TableLayoutPanel();
        	this.accountName = new System.Windows.Forms.TextBox();
        	this.accountPassword = new System.Windows.Forms.TextBox();
        	this.orderPlacePassword = new System.Windows.Forms.TextBox();
        	this.ok = new System.Windows.Forms.Button();
        	this.paperTrade = new System.Windows.Forms.CheckBox();
        	this.panel1 = new System.Windows.Forms.Panel();
        	this.chkCloseWithMarket = new System.Windows.Forms.CheckBox();
        	this.mainPanel.SuspendLayout();
        	this.panel1.SuspendLayout();
        	this.SuspendLayout();
        	// 
        	// label1
        	// 
        	this.label1.Anchor = System.Windows.Forms.AnchorStyles.Left;
        	this.label1.AutoSize = true;
        	this.label1.Location = new System.Drawing.Point(3, 6);
        	this.label1.Name = "label1";
        	this.label1.Size = new System.Drawing.Size(79, 13);
        	this.label1.TabIndex = 0;
        	this.label1.Text = "Account name:";
        	// 
        	// label2
        	// 
        	this.label2.Anchor = System.Windows.Forms.AnchorStyles.Left;
        	this.label2.AutoSize = true;
        	this.label2.Location = new System.Drawing.Point(3, 32);
        	this.label2.Name = "label2";
        	this.label2.Size = new System.Drawing.Size(98, 13);
        	this.label2.TabIndex = 1;
        	this.label2.Text = "Account password:";
        	// 
        	// label3
        	// 
        	this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
        	this.label3.AutoSize = true;
        	this.label3.Location = new System.Drawing.Point(3, 58);
        	this.label3.Name = "label3";
        	this.label3.Size = new System.Drawing.Size(113, 13);
        	this.label3.TabIndex = 2;
        	this.label3.Text = "Order place password:";
        	// 
        	// mainPanel
        	// 
        	this.mainPanel.ColumnCount = 2;
        	this.mainPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
        	this.mainPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
        	this.mainPanel.Controls.Add(this.label1, 0, 0);
        	this.mainPanel.Controls.Add(this.label2, 0, 1);
        	this.mainPanel.Controls.Add(this.label3, 0, 2);
        	this.mainPanel.Controls.Add(this.accountName, 1, 0);
        	this.mainPanel.Controls.Add(this.accountPassword, 1, 1);
        	this.mainPanel.Controls.Add(this.orderPlacePassword, 1, 2);
        	this.mainPanel.Controls.Add(this.ok, 0, 5);
        	this.mainPanel.Controls.Add(this.paperTrade, 0, 3);
        	this.mainPanel.Controls.Add(this.chkCloseWithMarket, 0, 4);
        	this.mainPanel.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.mainPanel.Location = new System.Drawing.Point(10, 10);
        	this.mainPanel.Name = "mainPanel";
        	this.mainPanel.RowCount = 6;
        	this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
        	this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
        	this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
        	this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
        	this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
        	this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
        	this.mainPanel.Size = new System.Drawing.Size(249, 176);
        	this.mainPanel.TabIndex = 4;
        	// 
        	// accountName
        	// 
        	this.accountName.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.accountName.Location = new System.Drawing.Point(122, 3);
        	this.accountName.Name = "accountName";
        	this.accountName.Size = new System.Drawing.Size(124, 20);
        	this.accountName.TabIndex = 4;
        	// 
        	// accountPassword
        	// 
        	this.accountPassword.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.accountPassword.Location = new System.Drawing.Point(122, 29);
        	this.accountPassword.Name = "accountPassword";
        	this.accountPassword.PasswordChar = '*';
        	this.accountPassword.Size = new System.Drawing.Size(124, 20);
        	this.accountPassword.TabIndex = 5;
        	// 
        	// orderPlacePassword
        	// 
        	this.orderPlacePassword.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.orderPlacePassword.Location = new System.Drawing.Point(122, 55);
        	this.orderPlacePassword.Name = "orderPlacePassword";
        	this.orderPlacePassword.PasswordChar = '*';
        	this.orderPlacePassword.Size = new System.Drawing.Size(124, 20);
        	this.orderPlacePassword.TabIndex = 6;
        	// 
        	// ok
        	// 
        	this.ok.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
        	this.mainPanel.SetColumnSpan(this.ok, 2);
        	this.ok.DialogResult = System.Windows.Forms.DialogResult.OK;
        	this.ok.Location = new System.Drawing.Point(87, 151);
        	this.ok.Name = "ok";
        	this.ok.Size = new System.Drawing.Size(75, 22);
        	this.ok.TabIndex = 3;
        	this.ok.Text = "OK";
        	this.ok.UseVisualStyleBackColor = true;
        	this.ok.Click += new System.EventHandler(this.ok_Click);
        	// 
        	// paperTrade
        	// 
        	this.paperTrade.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
        	this.paperTrade.AutoSize = true;
        	this.paperTrade.Checked = true;
        	this.paperTrade.CheckState = System.Windows.Forms.CheckState.Checked;
        	this.paperTrade.Location = new System.Drawing.Point(3, 88);
        	this.paperTrade.Name = "paperTrade";
        	this.paperTrade.Size = new System.Drawing.Size(81, 17);
        	this.paperTrade.TabIndex = 7;
        	this.paperTrade.Text = "Paper trade";
        	this.paperTrade.UseVisualStyleBackColor = true;
        	// 
        	// panel1
        	// 
        	this.panel1.Controls.Add(this.mainPanel);
        	this.panel1.Dock = System.Windows.Forms.DockStyle.Fill;
        	this.panel1.Location = new System.Drawing.Point(0, 0);
        	this.panel1.Name = "panel1";
        	this.panel1.Padding = new System.Windows.Forms.Padding(10);
        	this.panel1.Size = new System.Drawing.Size(269, 196);
        	this.panel1.TabIndex = 5;
        	// 
        	// chkCloseWithMarket
        	// 
        	this.chkCloseWithMarket.Checked = true;
        	this.chkCloseWithMarket.CheckState = System.Windows.Forms.CheckState.Checked;
        	this.mainPanel.SetColumnSpan(this.chkCloseWithMarket, 2);
        	this.chkCloseWithMarket.Location = new System.Drawing.Point(3, 111);
        	this.chkCloseWithMarket.Name = "chkCloseWithMarket";
        	this.chkCloseWithMarket.Size = new System.Drawing.Size(237, 24);
        	this.chkCloseWithMarket.TabIndex = 8;
        	this.chkCloseWithMarket.Text = "Close with market";
        	this.chkCloseWithMarket.UseVisualStyleBackColor = true;
        	// 
        	// LoginForm
        	// 
        	this.AcceptButton = this.ok;
        	this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
        	this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
        	this.ClientSize = new System.Drawing.Size(269, 196);
        	this.Controls.Add(this.panel1);
        	this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
        	this.MaximizeBox = false;
        	this.MinimizeBox = false;
        	this.Name = "LoginForm";
        	this.ShowInTaskbar = false;
        	this.Text = "Trading Agent - Login";
        	this.Shown += new System.EventHandler(this.LoginForm_Shown);
        	this.mainPanel.ResumeLayout(false);
        	this.mainPanel.PerformLayout();
        	this.panel1.ResumeLayout(false);
        	this.ResumeLayout(false);
        }
        private System.Windows.Forms.CheckBox chkCloseWithMarket;

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TableLayoutPanel mainPanel;
        private System.Windows.Forms.TextBox accountName;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.TextBox accountPassword;
        private System.Windows.Forms.TextBox orderPlacePassword;
        private System.Windows.Forms.Button ok;
        private System.Windows.Forms.CheckBox paperTrade;
    }
}