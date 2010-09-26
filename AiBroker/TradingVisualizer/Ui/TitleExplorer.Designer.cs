namespace TradingVisualizer.Ui
{
    partial class TitleExplorer
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TitleExplorer));
            this.titleTree = new System.Windows.Forms.TreeView();
            this.titleIcons = new System.Windows.Forms.ImageList(this.components);
            this.SuspendLayout();
            // 
            // titleTree
            // 
            this.titleTree.Dock = System.Windows.Forms.DockStyle.Fill;
            this.titleTree.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.titleTree.ImageIndex = 0;
            this.titleTree.ImageList = this.titleIcons;
            this.titleTree.Location = new System.Drawing.Point(0, 0);
            this.titleTree.Name = "titleTree";
            this.titleTree.SelectedImageIndex = 0;
            this.titleTree.Size = new System.Drawing.Size(142, 375);
            this.titleTree.TabIndex = 0;
            this.titleTree.AfterSelect += new System.Windows.Forms.TreeViewEventHandler(this.titleTree_AfterSelect);
            // 
            // titleIcons
            // 
            this.titleIcons.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("titleIcons.ImageStream")));
            this.titleIcons.TransparentColor = System.Drawing.Color.Transparent;
            this.titleIcons.Images.SetKeyName(0, "File.ico");
            this.titleIcons.Images.SetKeyName(1, "File_h.ico");
            this.titleIcons.Images.SetKeyName(2, "Right1.ico");
            this.titleIcons.Images.SetKeyName(3, "Right1_h.ico");
            this.titleIcons.Images.SetKeyName(4, "Down1.ico");
            this.titleIcons.Images.SetKeyName(5, "Down1_h.ico");
            this.titleIcons.Images.SetKeyName(6, "Open.ico");
            this.titleIcons.Images.SetKeyName(7, "Open_h.ico");
            this.titleIcons.Images.SetKeyName(8, "Add.ico");
            this.titleIcons.Images.SetKeyName(9, "Add_h.ico");
            // 
            // TitleExplorer
            // 
            this.AutoHidePortion = 150;
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(142, 375);
            this.Controls.Add(this.titleTree);
            this.DockAreas = ((WeifenLuo.WinFormsUI.Docking.DockAreas)(((WeifenLuo.WinFormsUI.Docking.DockAreas.Float | WeifenLuo.WinFormsUI.Docking.DockAreas.DockLeft)
                        | WeifenLuo.WinFormsUI.Docking.DockAreas.DockRight)));
            this.HideOnClose = true;
            this.Name = "TitleExplorer";
            this.ShowHint = WeifenLuo.WinFormsUI.Docking.DockState.DockLeftAutoHide;
            this.TabText = "Symbols";
            this.Text = "Symbols";
            this.Load += new System.EventHandler(this.SymbolsExplorer_Load);
            this.VisibleChanged += new System.EventHandler(this.TitleExplorer_VisibleChanged);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.TreeView titleTree;
        private System.Windows.Forms.ImageList titleIcons;
    }
}