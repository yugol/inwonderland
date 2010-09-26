namespace TradingVisualizer.Ui
{
    partial class CursorValues
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
            this.nameValuePairs = new System.Windows.Forms.ListView();
            this.name = new System.Windows.Forms.ColumnHeader();
            this.value = new System.Windows.Forms.ColumnHeader();
            this.SuspendLayout();
            // 
            // nameValuePairs
            // 
            this.nameValuePairs.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.name,
            this.value});
            this.nameValuePairs.Dock = System.Windows.Forms.DockStyle.Fill;
            this.nameValuePairs.Location = new System.Drawing.Point(0, 0);
            this.nameValuePairs.Name = "nameValuePairs";
            this.nameValuePairs.Size = new System.Drawing.Size(191, 553);
            this.nameValuePairs.TabIndex = 0;
            this.nameValuePairs.UseCompatibleStateImageBehavior = false;
            this.nameValuePairs.View = System.Windows.Forms.View.Details;
            // 
            // name
            // 
            this.name.Text = "Name";
            // 
            // value
            // 
            this.value.Text = "Value";
            // 
            // CursorValues
            // 
            this.AutoHidePortion = 0.15;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(191, 553);
            this.Controls.Add(this.nameValuePairs);
            this.DockAreas = ((WeifenLuo.WinFormsUI.Docking.DockAreas)(((WeifenLuo.WinFormsUI.Docking.DockAreas.Float | WeifenLuo.WinFormsUI.Docking.DockAreas.DockLeft)
                        | WeifenLuo.WinFormsUI.Docking.DockAreas.DockRight)));
            this.HideOnClose = true;
            this.Name = "CursorValues";
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockRight;
            this.TabText = "Cursor values";
            this.Text = "Cursor Values";
            this.VisibleChanged += new System.EventHandler(this.CursorValues_VisibleChanged);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.ListView nameValuePairs;
        private System.Windows.Forms.ColumnHeader name;
        private System.Windows.Forms.ColumnHeader value;
    }
}