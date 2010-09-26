namespace TradingAgent.Ui
{
    partial class AddOrderControl
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
            this.label2 = new System.Windows.Forms.Label();
            this.volume = new System.Windows.Forms.ComboBox();
            this.label3 = new System.Windows.Forms.Label();
            this.price = new System.Windows.Forms.TextBox();
            this.symbol = new System.Windows.Forms.TextBox();
            this.buy = new System.Windows.Forms.Button();
            this.sell = new System.Windows.Forms.Button();
            this.label4 = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.timeLimit = new System.Windows.Forms.ComboBox();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(8, 56);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(44, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Symbol:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(8, 88);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(45, 13);
            this.label2.TabIndex = 2;
            this.label2.Text = "Volume:";
            // 
            // volume
            // 
            this.volume.DisplayMember = "1";
            this.volume.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.volume.FormattingEnabled = true;
            this.volume.Location = new System.Drawing.Point(96, 88);
            this.volume.Name = "volume";
            this.volume.Size = new System.Drawing.Size(88, 21);
            this.volume.TabIndex = 3;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(8, 120);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(34, 13);
            this.label3.TabIndex = 4;
            this.label3.Text = "Price:";
            // 
            // price
            // 
            this.price.Location = new System.Drawing.Point(96, 120);
            this.price.Name = "price";
            this.price.Size = new System.Drawing.Size(88, 20);
            this.price.TabIndex = 5;
            this.price.Text = "PIATA";
            // 
            // symbol
            // 
            this.symbol.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.symbol.Location = new System.Drawing.Point(64, 56);
            this.symbol.Name = "symbol";
            this.symbol.ReadOnly = true;
            this.symbol.Size = new System.Drawing.Size(120, 20);
            this.symbol.TabIndex = 6;
            this.symbol.Text = "MMMMMM-MMMMM";
            // 
            // buy
            // 
            this.buy.Location = new System.Drawing.Point(8, 208);
            this.buy.Name = "buy";
            this.buy.Size = new System.Drawing.Size(80, 32);
            this.buy.TabIndex = 9;
            this.buy.Text = "Buy";
            this.buy.UseVisualStyleBackColor = false;
            this.buy.Click += new System.EventHandler(this.buy_Click);
            // 
            // sell
            // 
            this.sell.Location = new System.Drawing.Point(104, 208);
            this.sell.Name = "sell";
            this.sell.Size = new System.Drawing.Size(80, 32);
            this.sell.TabIndex = 10;
            this.sell.Text = "Sell";
            this.sell.UseVisualStyleBackColor = false;
            this.sell.Click += new System.EventHandler(this.sell_Click);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(8, 16);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(89, 13);
            this.label4.TabIndex = 11;
            this.label4.Text = "Add new order";
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(8, 152);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(53, 13);
            this.label5.TabIndex = 12;
            this.label5.Text = "Time limit:";
            // 
            // timeLimit
            // 
            this.timeLimit.DropDownStyle = System.Windows.Forms.ComboBoxStyle.DropDownList;
            this.timeLimit.FormattingEnabled = true;
            this.timeLimit.Location = new System.Drawing.Point(96, 152);
            this.timeLimit.Name = "timeLimit";
            this.timeLimit.Size = new System.Drawing.Size(88, 21);
            this.timeLimit.TabIndex = 13;
            // 
            // AddOrderControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.timeLimit);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.sell);
            this.Controls.Add(this.buy);
            this.Controls.Add(this.symbol);
            this.Controls.Add(this.price);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.volume);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.MinimumSize = new System.Drawing.Size(194, 236);
            this.Name = "AddOrderControl";
            this.Size = new System.Drawing.Size(194, 267);
            this.Load += new System.EventHandler(this.AddOrderControl_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.ComboBox volume;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox price;
        private System.Windows.Forms.TextBox symbol;
        private System.Windows.Forms.Button buy;
        private System.Windows.Forms.Button sell;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.ComboBox timeLimit;
    }
}
