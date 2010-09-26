namespace TradingSimulator.Ui
{
    partial class SearchSpaceExplorer
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(SearchSpaceExplorer));
            this.map = new System.Windows.Forms.Panel();
            this.yName = new System.Windows.Forms.Label();
            this.yMax = new System.Windows.Forms.TextBox();
            this.yMin = new System.Windows.Forms.TextBox();
            this.xName = new System.Windows.Forms.Label();
            this.xMin = new System.Windows.Forms.TextBox();
            this.xMax = new System.Windows.Forms.TextBox();
            this.yDivs = new System.Windows.Forms.NumericUpDown();
            this.yValue = new System.Windows.Forms.Label();
            this.xValue = new System.Windows.Forms.Label();
            this.xDivs = new System.Windows.Forms.NumericUpDown();
            this.legend = new System.Windows.Forms.Panel();
            this.maxFitness = new System.Windows.Forms.Label();
            this.minFitness = new System.Windows.Forms.Label();
            this.fitnessValue = new System.Windows.Forms.Label();
            this.zoomOut = new System.Windows.Forms.Button();
            this.label1 = new System.Windows.Forms.Label();
            this.from = new System.Windows.Forms.DateTimePicker();
            this.to = new System.Windows.Forms.DateTimePicker();
            this.label4 = new System.Windows.Forms.Label();
            this.timeZone = new System.Windows.Forms.TrackBar();
            this.lockFrom = new System.Windows.Forms.CheckBox();
            this.done = new System.Windows.Forms.Button();
            this.label5 = new System.Windows.Forms.Label();
            this.selYName = new System.Windows.Forms.Label();
            this.selXName = new System.Windows.Forms.Label();
            this.selYValue = new System.Windows.Forms.Label();
            this.selXValue = new System.Windows.Forms.Label();
            this.label8 = new System.Windows.Forms.Label();
            this.middleFitness = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.yDivs)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.xDivs)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.timeZone)).BeginInit();
            this.SuspendLayout();
            // 
            // map
            // 
            this.map.BackColor = System.Drawing.Color.White;
            this.map.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.map.Location = new System.Drawing.Point(88, 16);
            this.map.Name = "map";
            this.map.Size = new System.Drawing.Size(400, 400);
            this.map.TabIndex = 0;
            this.map.MouseLeave += new System.EventHandler(this.map_MouseLeave);
            this.map.Paint += new System.Windows.Forms.PaintEventHandler(this.map_Paint);
            this.map.MouseMove += new System.Windows.Forms.MouseEventHandler(this.map_MouseMove);
            this.map.MouseDown += new System.Windows.Forms.MouseEventHandler(this.map_MouseDown);
            this.map.MouseUp += new System.Windows.Forms.MouseEventHandler(this.map_MouseUp);
            // 
            // yName
            // 
            this.yName.AutoSize = true;
            this.yName.Location = new System.Drawing.Point(16, 56);
            this.yName.Name = "yName";
            this.yName.Size = new System.Drawing.Size(17, 13);
            this.yName.TabIndex = 3;
            this.yName.Text = "Y:";
            // 
            // yMax
            // 
            this.yMax.Location = new System.Drawing.Point(16, 16);
            this.yMax.Name = "yMax";
            this.yMax.ReadOnly = true;
            this.yMax.Size = new System.Drawing.Size(56, 20);
            this.yMax.TabIndex = 4;
            this.yMax.Text = "0";
            // 
            // yMin
            // 
            this.yMin.Location = new System.Drawing.Point(16, 392);
            this.yMin.Name = "yMin";
            this.yMin.ReadOnly = true;
            this.yMin.Size = new System.Drawing.Size(56, 20);
            this.yMin.TabIndex = 5;
            this.yMin.Text = "0";
            // 
            // xName
            // 
            this.xName.AutoSize = true;
            this.xName.Location = new System.Drawing.Point(320, 432);
            this.xName.Name = "xName";
            this.xName.Size = new System.Drawing.Size(17, 13);
            this.xName.TabIndex = 6;
            this.xName.Text = "X:";
            // 
            // xMin
            // 
            this.xMin.Location = new System.Drawing.Point(88, 432);
            this.xMin.Name = "xMin";
            this.xMin.ReadOnly = true;
            this.xMin.Size = new System.Drawing.Size(56, 20);
            this.xMin.TabIndex = 7;
            this.xMin.Text = "0";
            // 
            // xMax
            // 
            this.xMax.Location = new System.Drawing.Point(432, 432);
            this.xMax.Name = "xMax";
            this.xMax.ReadOnly = true;
            this.xMax.Size = new System.Drawing.Size(56, 20);
            this.xMax.TabIndex = 8;
            this.xMax.Text = "0";
            // 
            // yDivs
            // 
            this.yDivs.Location = new System.Drawing.Point(16, 352);
            this.yDivs.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.yDivs.Name = "yDivs";
            this.yDivs.Size = new System.Drawing.Size(56, 20);
            this.yDivs.TabIndex = 2;
            this.yDivs.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.yDivs.ValueChanged += new System.EventHandler(this.yDivs_ValueChanged);
            // 
            // yValue
            // 
            this.yValue.AutoSize = true;
            this.yValue.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.yValue.Location = new System.Drawing.Point(16, 80);
            this.yValue.Name = "yValue";
            this.yValue.Size = new System.Drawing.Size(30, 13);
            this.yValue.TabIndex = 9;
            this.yValue.Text = "N/A";
            // 
            // xValue
            // 
            this.xValue.AutoSize = true;
            this.xValue.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.xValue.Location = new System.Drawing.Point(360, 432);
            this.xValue.Name = "xValue";
            this.xValue.Size = new System.Drawing.Size(30, 13);
            this.xValue.TabIndex = 10;
            this.xValue.Text = "N/A";
            // 
            // xDivs
            // 
            this.xDivs.Location = new System.Drawing.Point(161, 433);
            this.xDivs.Minimum = new decimal(new int[] {
            1,
            0,
            0,
            0});
            this.xDivs.Name = "xDivs";
            this.xDivs.Size = new System.Drawing.Size(56, 20);
            this.xDivs.TabIndex = 11;
            this.xDivs.Value = new decimal(new int[] {
            10,
            0,
            0,
            0});
            this.xDivs.ValueChanged += new System.EventHandler(this.xDivs_ValueChanged);
            // 
            // legend
            // 
            this.legend.BackColor = System.Drawing.Color.White;
            this.legend.BorderStyle = System.Windows.Forms.BorderStyle.FixedSingle;
            this.legend.Location = new System.Drawing.Point(512, 16);
            this.legend.Name = "legend";
            this.legend.Size = new System.Drawing.Size(24, 400);
            this.legend.TabIndex = 13;
            this.legend.Paint += new System.Windows.Forms.PaintEventHandler(this.legend_Paint);
            // 
            // maxFitness
            // 
            this.maxFitness.AutoSize = true;
            this.maxFitness.Location = new System.Drawing.Point(544, 16);
            this.maxFitness.Name = "maxFitness";
            this.maxFitness.Size = new System.Drawing.Size(27, 13);
            this.maxFitness.TabIndex = 14;
            this.maxFitness.Text = "N/A";
            // 
            // minFitness
            // 
            this.minFitness.AutoSize = true;
            this.minFitness.Location = new System.Drawing.Point(544, 400);
            this.minFitness.Name = "minFitness";
            this.minFitness.Size = new System.Drawing.Size(27, 13);
            this.minFitness.TabIndex = 15;
            this.minFitness.Text = "N/A";
            // 
            // fitnessValue
            // 
            this.fitnessValue.AutoSize = true;
            this.fitnessValue.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.fitnessValue.Location = new System.Drawing.Point(544, 96);
            this.fitnessValue.Name = "fitnessValue";
            this.fitnessValue.Size = new System.Drawing.Size(30, 13);
            this.fitnessValue.TabIndex = 16;
            this.fitnessValue.Text = "N/A";
            // 
            // zoomOut
            // 
            this.zoomOut.Location = new System.Drawing.Point(512, 432);
            this.zoomOut.Name = "zoomOut";
            this.zoomOut.Size = new System.Drawing.Size(88, 24);
            this.zoomOut.TabIndex = 17;
            this.zoomOut.Text = "Zoom out";
            this.zoomOut.UseVisualStyleBackColor = true;
            this.zoomOut.Click += new System.EventHandler(this.zoomOut_Click);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(16, 480);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(33, 13);
            this.label1.TabIndex = 18;
            this.label1.Text = "From:";
            // 
            // from
            // 
            this.from.Enabled = false;
            this.from.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.from.Format = System.Windows.Forms.DateTimePickerFormat.Short;
            this.from.Location = new System.Drawing.Point(64, 480);
            this.from.Name = "from";
            this.from.Size = new System.Drawing.Size(104, 20);
            this.from.TabIndex = 19;
            // 
            // to
            // 
            this.to.Enabled = false;
            this.to.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.to.Format = System.Windows.Forms.DateTimePickerFormat.Short;
            this.to.Location = new System.Drawing.Point(384, 480);
            this.to.Name = "to";
            this.to.Size = new System.Drawing.Size(104, 20);
            this.to.TabIndex = 21;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(344, 480);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(23, 13);
            this.label4.TabIndex = 20;
            this.label4.Text = "To:";
            // 
            // timeZone
            // 
            this.timeZone.Location = new System.Drawing.Point(16, 520);
            this.timeZone.Name = "timeZone";
            this.timeZone.Size = new System.Drawing.Size(472, 45);
            this.timeZone.TabIndex = 22;
            this.timeZone.TickStyle = System.Windows.Forms.TickStyle.TopLeft;
            this.timeZone.Scroll += new System.EventHandler(this.timeZone_Scroll);
            // 
            // lockFrom
            // 
            this.lockFrom.AutoSize = true;
            this.lockFrom.Checked = true;
            this.lockFrom.CheckState = System.Windows.Forms.CheckState.Checked;
            this.lockFrom.Location = new System.Drawing.Point(184, 480);
            this.lockFrom.Name = "lockFrom";
            this.lockFrom.Size = new System.Drawing.Size(50, 17);
            this.lockFrom.TabIndex = 23;
            this.lockFrom.Text = "Lock";
            this.lockFrom.UseVisualStyleBackColor = true;
            this.lockFrom.CheckedChanged += new System.EventHandler(this.lockFrom_CheckedChanged);
            // 
            // done
            // 
            this.done.Location = new System.Drawing.Point(512, 544);
            this.done.Name = "done";
            this.done.Size = new System.Drawing.Size(88, 23);
            this.done.TabIndex = 24;
            this.done.Text = "Done";
            this.done.UseVisualStyleBackColor = true;
            this.done.Click += new System.EventHandler(this.done_Click);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Location = new System.Drawing.Point(512, 464);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(91, 13);
            this.label5.TabIndex = 25;
            this.label5.Text = "Selected solution:";
            // 
            // selYName
            // 
            this.selYName.AutoSize = true;
            this.selYName.Location = new System.Drawing.Point(528, 488);
            this.selYName.Name = "selYName";
            this.selYName.Size = new System.Drawing.Size(17, 13);
            this.selYName.TabIndex = 26;
            this.selYName.Text = "Y:";
            // 
            // selXName
            // 
            this.selXName.AutoSize = true;
            this.selXName.Location = new System.Drawing.Point(528, 512);
            this.selXName.Name = "selXName";
            this.selXName.Size = new System.Drawing.Size(17, 13);
            this.selXName.TabIndex = 27;
            this.selXName.Text = "X:";
            // 
            // selYValue
            // 
            this.selYValue.AutoSize = true;
            this.selYValue.Location = new System.Drawing.Point(561, 489);
            this.selYValue.Name = "selYValue";
            this.selYValue.Size = new System.Drawing.Size(27, 13);
            this.selYValue.TabIndex = 28;
            this.selYValue.Text = "N/A";
            // 
            // selXValue
            // 
            this.selXValue.AutoSize = true;
            this.selXValue.Location = new System.Drawing.Point(561, 513);
            this.selXValue.Name = "selXValue";
            this.selXValue.Size = new System.Drawing.Size(27, 13);
            this.selXValue.TabIndex = 29;
            this.selXValue.Text = "N/A";
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Location = new System.Drawing.Point(544, 72);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(43, 13);
            this.label8.TabIndex = 30;
            this.label8.Text = "Fitness:";
            // 
            // middleFitness
            // 
            this.middleFitness.AutoSize = true;
            this.middleFitness.Location = new System.Drawing.Point(544, 200);
            this.middleFitness.Name = "middleFitness";
            this.middleFitness.Size = new System.Drawing.Size(27, 13);
            this.middleFitness.TabIndex = 31;
            this.middleFitness.Text = "N/A";
            // 
            // SearchSpaceExplorer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(617, 585);
            this.Controls.Add(this.middleFitness);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.selXValue);
            this.Controls.Add(this.selYValue);
            this.Controls.Add(this.selXName);
            this.Controls.Add(this.selYName);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.done);
            this.Controls.Add(this.lockFrom);
            this.Controls.Add(this.timeZone);
            this.Controls.Add(this.to);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.from);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.zoomOut);
            this.Controls.Add(this.fitnessValue);
            this.Controls.Add(this.minFitness);
            this.Controls.Add(this.maxFitness);
            this.Controls.Add(this.legend);
            this.Controls.Add(this.xDivs);
            this.Controls.Add(this.xValue);
            this.Controls.Add(this.yValue);
            this.Controls.Add(this.xMax);
            this.Controls.Add(this.xMin);
            this.Controls.Add(this.xName);
            this.Controls.Add(this.yMin);
            this.Controls.Add(this.yMax);
            this.Controls.Add(this.yName);
            this.Controls.Add(this.yDivs);
            this.Controls.Add(this.map);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.Fixed3D;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "SearchSpaceExplorer";
            this.Text = "SearchSpaceExplorer";
            ((System.ComponentModel.ISupportInitialize)(this.yDivs)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.xDivs)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.timeZone)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel map;
        private System.Windows.Forms.Label yName;
        private System.Windows.Forms.TextBox yMax;
        private System.Windows.Forms.TextBox yMin;
        private System.Windows.Forms.Label xName;
        private System.Windows.Forms.TextBox xMin;
        private System.Windows.Forms.TextBox xMax;
        private System.Windows.Forms.NumericUpDown yDivs;
        private System.Windows.Forms.Label yValue;
        private System.Windows.Forms.Label xValue;
        private System.Windows.Forms.NumericUpDown xDivs;
        private System.Windows.Forms.Label maxFitness;
        private System.Windows.Forms.Label minFitness;
        private System.Windows.Forms.Label fitnessValue;
        private System.Windows.Forms.Button zoomOut;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.DateTimePicker from;
        private System.Windows.Forms.DateTimePicker to;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TrackBar timeZone;
        private System.Windows.Forms.CheckBox lockFrom;
        private System.Windows.Forms.Button done;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label selYName;
        private System.Windows.Forms.Label selXName;
        private System.Windows.Forms.Label selYValue;
        private System.Windows.Forms.Label selXValue;
        private System.Windows.Forms.Label label8;
        private System.Windows.Forms.Label middleFitness;
        private System.Windows.Forms.Panel legend;
    }
}