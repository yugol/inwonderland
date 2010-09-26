namespace TradingAgent.Ui
{
    partial class RealtimeDataControl
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
            this.mainPanel = new System.Windows.Forms.TableLayoutPanel();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.price = new System.Windows.Forms.Label();
            this.ask = new System.Windows.Forms.Label();
            this.bid = new System.Windows.Forms.Label();
            this.time = new System.Windows.Forms.Label();
            this.volume = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.openPositions = new System.Windows.Forms.Label();
            this.label5 = new System.Windows.Forms.Label();
            this.lastEnterOperation = new System.Windows.Forms.Label();
            this.mainPanel.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainPanel
            // 
            this.mainPanel.ColumnCount = 2;
            this.mainPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.mainPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle());
            this.mainPanel.Controls.Add(this.label1, 0, 0);
            this.mainPanel.Controls.Add(this.label2, 0, 1);
            this.mainPanel.Controls.Add(this.label3, 0, 2);
            this.mainPanel.Controls.Add(this.price, 1, 2);
            this.mainPanel.Controls.Add(this.ask, 1, 0);
            this.mainPanel.Controls.Add(this.bid, 1, 1);
            this.mainPanel.Controls.Add(this.time, 1, 4);
            this.mainPanel.Controls.Add(this.volume, 1, 3);
            this.mainPanel.Controls.Add(this.label4, 0, 5);
            this.mainPanel.Controls.Add(this.openPositions, 1, 5);
            this.mainPanel.Controls.Add(this.label5, 0, 6);
            this.mainPanel.Controls.Add(this.lastEnterOperation, 1, 6);
            this.mainPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            this.mainPanel.Location = new System.Drawing.Point(10, 15);
            this.mainPanel.Name = "mainPanel";
            this.mainPanel.RowCount = 8;
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 24F));
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 20F));
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 30F));
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Absolute, 24F));
            this.mainPanel.RowStyles.Add(new System.Windows.Forms.RowStyle());
            this.mainPanel.Size = new System.Drawing.Size(163, 193);
            this.mainPanel.TabIndex = 0;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(3, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(32, 13);
            this.label1.TabIndex = 0;
            this.label1.Text = "Ask:";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(3, 24);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(29, 13);
            this.label2.TabIndex = 1;
            this.label2.Text = "Bid:";
            // 
            // label3
            // 
            this.label3.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(3, 57);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(40, 13);
            this.label3.TabIndex = 2;
            this.label3.Text = "Price:";
            // 
            // price
            // 
            this.price.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.price.AutoSize = true;
            this.price.Location = new System.Drawing.Point(114, 57);
            this.price.Name = "price";
            this.price.Size = new System.Drawing.Size(46, 13);
            this.price.TabIndex = 5;
            this.price.Text = "00.0000";
            // 
            // ask
            // 
            this.ask.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.ask.AutoSize = true;
            this.ask.Location = new System.Drawing.Point(114, 0);
            this.ask.Name = "ask";
            this.ask.Size = new System.Drawing.Size(46, 13);
            this.ask.TabIndex = 4;
            this.ask.Text = "00.0000";
            // 
            // bid
            // 
            this.bid.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.bid.AutoSize = true;
            this.bid.Location = new System.Drawing.Point(114, 24);
            this.bid.Name = "bid";
            this.bid.Size = new System.Drawing.Size(46, 13);
            this.bid.TabIndex = 3;
            this.bid.Text = "00.0000";
            // 
            // time
            // 
            this.time.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.time.AutoSize = true;
            this.time.Location = new System.Drawing.Point(111, 97);
            this.time.Name = "time";
            this.time.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.time.Size = new System.Drawing.Size(49, 13);
            this.time.TabIndex = 6;
            this.time.Text = "00:00:00";
            // 
            // volume
            // 
            this.volume.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.volume.AutoSize = true;
            this.volume.Location = new System.Drawing.Point(141, 77);
            this.volume.Name = "volume";
            this.volume.RightToLeft = System.Windows.Forms.RightToLeft.Yes;
            this.volume.Size = new System.Drawing.Size(19, 13);
            this.volume.TabIndex = 7;
            this.volume.Text = "(0)";
            // 
            // label4
            // 
            this.label4.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(3, 131);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(41, 13);
            this.label4.TabIndex = 8;
            this.label4.Text = "Open:";
            // 
            // openPositions
            // 
            this.openPositions.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.openPositions.AutoSize = true;
            this.openPositions.Location = new System.Drawing.Point(147, 131);
            this.openPositions.Name = "openPositions";
            this.openPositions.Size = new System.Drawing.Size(13, 13);
            this.openPositions.TabIndex = 9;
            this.openPositions.Text = "0";
            // 
            // label5
            // 
            this.label5.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(3, 155);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(68, 13);
            this.label5.TabIndex = 10;
            this.label5.Text = "Last enter:";
            // 
            // lastEnterOperation
            // 
            this.lastEnterOperation.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.lastEnterOperation.AutoSize = true;
            this.lastEnterOperation.Location = new System.Drawing.Point(122, 155);
            this.lastEnterOperation.Name = "lastEnterOperation";
            this.lastEnterOperation.Size = new System.Drawing.Size(38, 13);
            this.lastEnterOperation.TabIndex = 11;
            this.lastEnterOperation.Text = "NONE";
            // 
            // RealtimeDataControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.mainPanel);
            this.MinimumSize = new System.Drawing.Size(120, 150);
            this.Name = "RealtimeDataControl";
            this.Padding = new System.Windows.Forms.Padding(10, 15, 10, 15);
            this.Size = new System.Drawing.Size(183, 223);
            this.mainPanel.ResumeLayout(false);
            this.mainPanel.PerformLayout();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TableLayoutPanel mainPanel;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label bid;
        private System.Windows.Forms.Label ask;
        private System.Windows.Forms.Label price;
        private System.Windows.Forms.Label time;
        private System.Windows.Forms.Label volume;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Label openPositions;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label lastEnterOperation;
    }
}
