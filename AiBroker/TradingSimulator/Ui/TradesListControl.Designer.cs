namespace TradingSimulator.Ui
{
    partial class TradesListControl
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
            this.tradesList = new System.Windows.Forms.ListView();
            this.columnIndex = new System.Windows.Forms.ColumnHeader();
            this.columnType = new System.Windows.Forms.ColumnHeader();
            this.columnDateTime = new System.Windows.Forms.ColumnHeader();
            this.columnBars = new System.Windows.Forms.ColumnHeader();
            this.columnPrice = new System.Windows.Forms.ColumnHeader();
            this.columnProfit = new System.Windows.Forms.ColumnHeader();
            this.columnRunUp = new System.Windows.Forms.ColumnHeader();
            this.columnDrawDown = new System.Windows.Forms.ColumnHeader();
            this.columnEfficiency = new System.Windows.Forms.ColumnHeader();
            this.SuspendLayout();
            // 
            // tradesList
            // 
            this.tradesList.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnIndex,
            this.columnType,
            this.columnDateTime,
            this.columnBars,
            this.columnPrice,
            this.columnProfit,
            this.columnRunUp,
            this.columnDrawDown,
            this.columnEfficiency});
            this.tradesList.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tradesList.FullRowSelect = true;
            this.tradesList.Location = new System.Drawing.Point(0, 0);
            this.tradesList.MultiSelect = false;
            this.tradesList.Name = "tradesList";
            this.tradesList.OwnerDraw = true;
            this.tradesList.Size = new System.Drawing.Size(625, 249);
            this.tradesList.TabIndex = 1;
            this.tradesList.UseCompatibleStateImageBehavior = false;
            this.tradesList.View = System.Windows.Forms.View.Details;
            this.tradesList.DrawColumnHeader += new System.Windows.Forms.DrawListViewColumnHeaderEventHandler(this.tradesList_DrawColumnHeader);
            this.tradesList.DrawItem += new System.Windows.Forms.DrawListViewItemEventHandler(this.tradesList_DrawItem);
            this.tradesList.ColumnClick += new System.Windows.Forms.ColumnClickEventHandler(this.tradesList_ColumnClick);
            this.tradesList.MouseMove += new System.Windows.Forms.MouseEventHandler(this.tradesList_MouseMove);
            this.tradesList.DrawSubItem += new System.Windows.Forms.DrawListViewSubItemEventHandler(this.tradesList_DrawSubItem);
            // 
            // columnIndex
            // 
            this.columnIndex.Text = "#";
            this.columnIndex.Width = 30;
            // 
            // columnType
            // 
            this.columnType.Text = "Type";
            this.columnType.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.columnType.Width = 50;
            // 
            // columnDateTime
            // 
            this.columnDateTime.Text = "Date/Time";
            this.columnDateTime.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.columnDateTime.Width = 120;
            // 
            // columnBars
            // 
            this.columnBars.Text = "Bar";
            this.columnBars.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            this.columnBars.Width = 40;
            // 
            // columnPrice
            // 
            this.columnPrice.Text = "Price";
            this.columnPrice.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            this.columnPrice.Width = 70;
            // 
            // columnProfit
            // 
            this.columnProfit.Text = "Profit";
            this.columnProfit.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            this.columnProfit.Width = 70;
            // 
            // columnRunUp
            // 
            this.columnRunUp.Text = "Run-up";
            this.columnRunUp.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            this.columnRunUp.Width = 70;
            // 
            // columnDrawDown
            // 
            this.columnDrawDown.Text = "Draw-down";
            this.columnDrawDown.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            this.columnDrawDown.Width = 70;
            // 
            // columnEfficiency
            // 
            this.columnEfficiency.Text = "Efficiency";
            this.columnEfficiency.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            this.columnEfficiency.Width = 70;
            // 
            // TradesListControl
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.Controls.Add(this.tradesList);
            this.Name = "TradesListControl";
            this.Size = new System.Drawing.Size(625, 249);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListView tradesList;
        private System.Windows.Forms.ColumnHeader columnIndex;
        private System.Windows.Forms.ColumnHeader columnType;
        private System.Windows.Forms.ColumnHeader columnDateTime;
        private System.Windows.Forms.ColumnHeader columnPrice;
        private System.Windows.Forms.ColumnHeader columnProfit;
        private System.Windows.Forms.ColumnHeader columnRunUp;
        private System.Windows.Forms.ColumnHeader columnDrawDown;
        private System.Windows.Forms.ColumnHeader columnEfficiency;
        private System.Windows.Forms.ColumnHeader columnBars;
    }
}
