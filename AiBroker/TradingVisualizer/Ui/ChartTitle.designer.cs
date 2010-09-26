namespace TradingVisualizer.Ui
{
    public partial class ChartTitle
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
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(ChartTitle));
            this.titleLabel = new System.Windows.Forms.Label();
            this.split = new System.Windows.Forms.Button();
            this.buttonImages = new System.Windows.Forms.ImageList(this.components);
            this.moveUp = new System.Windows.Forms.Button();
            this.moveDown = new System.Windows.Forms.Button();
            this.close = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // titleLabel
            // 
            this.titleLabel.Anchor = System.Windows.Forms.AnchorStyles.Left;
            this.titleLabel.AutoSize = true;
            this.titleLabel.Font = new System.Drawing.Font("Tahoma", 8.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.titleLabel.ForeColor = System.Drawing.SystemColors.ActiveCaptionText;
            this.titleLabel.Location = new System.Drawing.Point(0, 6);
            this.titleLabel.Name = "titleLabel";
            this.titleLabel.Padding = new System.Windows.Forms.Padding(4, 0, 0, 0);
            this.titleLabel.Size = new System.Drawing.Size(31, 13);
            this.titleLabel.TabIndex = 0;
            this.titleLabel.Text = "Title";
            // 
            // split
            // 
            this.split.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.split.FlatAppearance.BorderColor = System.Drawing.SystemColors.ActiveCaption;
            this.split.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.split.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.InactiveCaption;
            this.split.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.split.ImageIndex = 2;
            this.split.ImageList = this.buttonImages;
            this.split.Location = new System.Drawing.Point(452, 4);
            this.split.Name = "split";
            this.split.Size = new System.Drawing.Size(18, 18);
            this.split.TabIndex = 4;
            this.split.UseVisualStyleBackColor = true;
            this.split.Click += new System.EventHandler(this.split_Click);
            // 
            // buttonImages
            // 
            this.buttonImages.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("buttonImages.ImageStream")));
            this.buttonImages.TransparentColor = System.Drawing.Color.Transparent;
            this.buttonImages.Images.SetKeyName(0, "MoveUp10.gif");
            this.buttonImages.Images.SetKeyName(1, "MoveDown10.gif");
            this.buttonImages.Images.SetKeyName(2, "Split10.gif");
            this.buttonImages.Images.SetKeyName(3, "Close10.gif");
            // 
            // moveUp
            // 
            this.moveUp.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.moveUp.FlatAppearance.BorderColor = System.Drawing.SystemColors.ActiveCaption;
            this.moveUp.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.moveUp.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.InactiveCaption;
            this.moveUp.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.moveUp.ImageIndex = 0;
            this.moveUp.ImageList = this.buttonImages;
            this.moveUp.Location = new System.Drawing.Point(434, 4);
            this.moveUp.Name = "moveUp";
            this.moveUp.Size = new System.Drawing.Size(18, 18);
            this.moveUp.TabIndex = 3;
            this.moveUp.UseVisualStyleBackColor = true;
            this.moveUp.Click += new System.EventHandler(this.moveUp_Click);
            // 
            // moveDown
            // 
            this.moveDown.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.moveDown.FlatAppearance.BorderColor = System.Drawing.SystemColors.ActiveCaption;
            this.moveDown.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.moveDown.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.InactiveCaption;
            this.moveDown.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.moveDown.ImageIndex = 1;
            this.moveDown.ImageList = this.buttonImages;
            this.moveDown.Location = new System.Drawing.Point(416, 4);
            this.moveDown.Name = "moveDown";
            this.moveDown.Size = new System.Drawing.Size(18, 18);
            this.moveDown.TabIndex = 2;
            this.moveDown.UseVisualStyleBackColor = true;
            this.moveDown.Click += new System.EventHandler(this.moveDown_Click);
            // 
            // close
            // 
            this.close.Anchor = System.Windows.Forms.AnchorStyles.Right;
            this.close.FlatAppearance.BorderColor = System.Drawing.SystemColors.ActiveCaption;
            this.close.FlatAppearance.MouseDownBackColor = System.Drawing.SystemColors.InactiveCaptionText;
            this.close.FlatAppearance.MouseOverBackColor = System.Drawing.SystemColors.InactiveCaption;
            this.close.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.close.ImageIndex = 3;
            this.close.ImageList = this.buttonImages;
            this.close.Location = new System.Drawing.Point(470, 4);
            this.close.Name = "close";
            this.close.Size = new System.Drawing.Size(18, 18);
            this.close.TabIndex = 1;
            this.close.UseVisualStyleBackColor = true;
            this.close.Click += new System.EventHandler(this.close_Click);
            // 
            // ChartTitle
            // 
            this.BackColor = System.Drawing.SystemColors.ActiveCaption;
            this.Controls.Add(this.split);
            this.Controls.Add(this.moveUp);
            this.Controls.Add(this.moveDown);
            this.Controls.Add(this.close);
            this.Controls.Add(this.titleLabel);
            this.Name = "SplitterTitle";
            this.Size = new System.Drawing.Size(491, 27);
            this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.Title_MouseMove);
            this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.Title_MouseDown);
            this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.Title_MouseUp);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label titleLabel;
        private System.Windows.Forms.Button close;
        private System.Windows.Forms.Button moveDown;
        private System.Windows.Forms.Button moveUp;
        private System.Windows.Forms.Button split;
        private System.Windows.Forms.ImageList buttonImages;
    }
}
