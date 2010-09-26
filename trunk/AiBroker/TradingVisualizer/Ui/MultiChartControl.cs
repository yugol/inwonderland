using System;
using System.Collections.Generic;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows.Forms;
using Tao.OpenGl;
using Tao.FreeGlut;
using TradingCommon;
using TradingCommon.Indicators;
using TradingVisualizer.Charting;
using TradingCommon.Util;

namespace TradingVisualizer.Ui
{
    public delegate void PeriodChangedEventHandler(int newPeriod);
    public delegate void ReloadRefreshEventHandler();

    public partial class MultiChartControl : UserControl
    {
        public const int Y_SCALE_WIDTH = 80;
        public const int X_SCALE_HEIGHT = 18;
        public const int TITLE_HEIGHT = 18;

        const float ZOOM_FACTOR = 1.5F;
        const int MIN_ZOOM = 5;

        [DllImport("user32.dll")]
        static extern bool GetCursorPos(ref Point lpPoint);

        public event PeriodChangedEventHandler PeriodChanged;
        public event ReloadRefreshEventHandler ReloadRefresh;

        public int WindowCount { get { return multiChartPanel.WindowCount; } }
        public int XScaleHeight { get { return xScalePanel.Height; } }
        public int YScaleWidth
        {
            get { return xScaleSpacerPanel.Width; }
            set
            {
                xScaleSpacerPanel.Width = value;
                multiChartPanel.YScaleWidth = value;
            }
        }
        public IList<ChartTitle> Titles { get { return multiChartPanel.Titles; } }
        public IList<ChartContent> Charts { get { return multiChartPanel.Charts; } }
        public float[] WindowSplits
        {
            get { return multiChartPanel.WindowSplits; }
            set { multiChartPanel.WindowSplits = value; }
        }

        int zoom = 0;
        public int Zoom
        {
            get
            {
                if (zoom >= parentFrame.TimeSeries.Count)
                    return -1;
                return zoom;
            }
            set
            {
                if ((value <= 0) || (value > parentFrame.TimeSeries.Count))
                    zoom = parentFrame.TimeSeries.Count;
                else
                    zoom = value;
            }
        }

        public DateTime LastDateTime
        {
            get
            {
                if ((lastPosition >= (parentFrame.TimeSeries.Count - 1)) || (lastPosition < 0))
                    return DateTime.MaxValue;
                return parentFrame.TimeSeries.DateTimes[lastPosition];
            }
            set
            {
                if (DTUtil.ToDateTimeString(value).Equals(DTUtil.ToDateTimeString(DateTime.MaxValue)))
                    lastPosition = parentFrame.TimeSeries.Count - 1;
                else
                    lastPosition = parentFrame.TimeSeries.GetBarIndex(value);
            }
        }

        int lastPosition = 0;
        public int LastPosition { get { return lastPosition; } }

        public int FirstPosition
        {
            get
            {
                int firstPosition = lastPosition - zoom + 1;
                if (firstPosition < 0)
                    firstPosition = 0;
                return firstPosition;
            }
        }

        Delta.Term deltaTerm = Delta.Term.NONE;
        public Delta.Term DeltaTerm { get { return deltaTerm; } set { deltaTerm = value; } }

        MultiChartFrame parentFrame;

        bool showCrossHair;
        public bool ShowCrossHair
        {
            get { return showCrossHair; }
            set
            {
                showCrossHair = value;
                if (showCrossHair)
                    crosshair.FlatAppearance.BorderColor = crosshair.FlatAppearance.MouseDownBackColor;
                else
                    crosshair.FlatAppearance.BorderColor = crosshair.BackColor;
            }
        }

        VerticalGrid verticalGrid;
        internal VerticalGrid VerticalGrid { get { return verticalGrid; } }

        public MultiChartControl()
        {
            InitializeComponent();

            navigationBar.Minimum = 0;

            tickToolStripMenuItem.Tag = TimeSeries.TICK_PERIOD;
            minutesToolStripMenuItem_1.Tag = 1;
            minutesToolStripMenuItem_5.Tag = 5;
            minutesToolStripMenuItem_10.Tag = 10;
            minutesToolStripMenuItem_15.Tag = 15;
            minutesToolStripMenuItem_20.Tag = 20;
            minutesToolStripMenuItem_30.Tag = 30;
            minutesToolStripMenuItem_60.Tag = 60;
            dailyfromTicksToolStripMenuItem.Tag = TimeSeries.DAY_FROM_TICK_PERIOD;
            dailyToolStripMenuItem.Tag = TimeSeries.DAY_PERIOD;
            weeklyToolStripMenuItem.Tag = TimeSeries.WEEK_PERIOD;
            monthlyToolStripMenuItem.Tag = TimeSeries.MONTH_PERIOD;
            yearlyToolStripMenuItem.Tag = TimeSeries.YEAR_PERIOD;

            xScale.InitializeContexts();

            xScalePanel.Height = X_SCALE_HEIGHT;
            multiChartPanel.TitleHeight = TITLE_HEIGHT;
            YScaleWidth = Y_SCALE_WIDTH;

            Period = -1;
            ShowCrossHair = false;
        }

        private void xScale_Paint(object sender, PaintEventArgs e)
        {
            if (parentFrame != null)
            {
                Color bg = SystemColors.Control;
                Color fg = SystemColors.ControlText;
                int height = xScale.Height - 1;

                Gl.glViewport(0, 0, xScale.Width, xScale.Height);
                Gl.glClearColor(bg.R / 255F, bg.G / 255F, bg.B / 255F, 1F);
                Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT);
                Gl.glLoadIdentity();

                Glu.gluOrtho2D(FirstPosition - VisualElement.ENTRY_HALF_WIDTH, LastPosition + VisualElement.ENTRY_HALF_WIDTH, 0, height);
                Gl.glColor3d(fg.R / 255D, fg.G / 255D, fg.B / 255D);

                foreach (VerticalGrid.GridPoint gridPoint in verticalGrid.GridPoints)
                {
                    double position = gridPoint.position;
                    Gl.glBegin(Gl.GL_LINES);
                    Gl.glVertex2d(position, 1);
                    Gl.glVertex2d(position, height);
                    Gl.glEnd();

                    Gl.glRasterPos2d(position, 3);
                    Glut.glutBitmapString(VerticalGrid.GLUT_X_LABELS_FONT, gridPoint.label);
                }
            }
        }

        private void MultiChartControl_ParentChanged(object sender, EventArgs e)
        {
            parentFrame = (MultiChartFrame)Parent;
        }

        internal void CalibrateNavigator()
        {
            if ((FirstPosition + zoom - 1) > lastPosition)
                lastPosition = FirstPosition + zoom - 1;

            if (zoom < parentFrame.TimeSeries.Count)
            {
                navigationBar.Enabled = true;
                navigationBar.Maximum = parentFrame.TimeSeries.Count - 1;
                navigationBar.LargeChange = zoom;
                navigationBar.Value = FirstPosition;
            }
            else
            {
                navigationBar.Enabled = false;
            }
        }

        private void zoomIn_Click(object sender, EventArgs e)
        {
            zoom = (int)(zoom / ZOOM_FACTOR);
            if (zoom < MIN_ZOOM)
                zoom = MIN_ZOOM;
            CalibrateNavigator();
            DrawChart();
        }

        private void zoomOut_Click(object sender, EventArgs e)
        {
            zoom = (int)(zoom * ZOOM_FACTOR);
            if (zoom > parentFrame.TimeSeries.Count)
                zoom = parentFrame.TimeSeries.Count;
            CalibrateNavigator();
            DrawChart();
        }

        private void zoomAll_Click(object sender, EventArgs e)
        {
            zoom = parentFrame.TimeSeries.Count;
            CalibrateNavigator();
            DrawChart();
        }

        internal void DrawChart()
        {
            if (parentFrame != null)
            {
                int first = FirstPosition;
                int last = LastPosition;
                double entryWidth = ((double)xScale.Width) / (last - first + 1);
                verticalGrid = new VerticalGrid(parentFrame.TimeSeries.DateTimes, first, last, Period, entryWidth);
                Refresh();
            }
        }

        private void MultiChartControl_Resize(object sender, EventArgs e)
        {
            DrawChart();
        }

        private void xScale_MouseDown(object sender, MouseEventArgs e)
        {
            if (e.Button == MouseButtons.Right)
            {
                DeltaSelectionForm deltaDlg = new DeltaSelectionForm();
                deltaDlg.DeltaTerm = deltaTerm;
                deltaDlg.ShowDialog();
                deltaTerm = deltaDlg.DeltaTerm;
                DrawChart();
            }
        }

        private void refresh_Click(object sender, EventArgs e)
        {
            if (ReloadRefresh != null)
                ReloadRefresh();
        }

        private void crosshair_Click(object sender, EventArgs e)
        {
            ShowCrossHair = !ShowCrossHair;
            refresh.Select();
        }

        #region period selection

        private void period_Click(object sender, EventArgs e)
        {
            Point defPnt = new Point();
            GetCursorPos(ref defPnt);
            periodMenu.Show(defPnt);
        }

        public int Period
        {
            get
            {
                foreach (ToolStripItem menuItem in periodMenu.Items)
                    if (menuItem is ToolStripMenuItem)
                        if (((ToolStripMenuItem)menuItem).Checked)
                            return (int)menuItem.Tag;
                return int.MinValue;
            }
            set
            {
                ToolStripMenuItem toolStripMenuItem = null;
                foreach (ToolStripItem menuItem in periodMenu.Items)
                {
                    if (menuItem is ToolStripMenuItem)
                    {
                        ((ToolStripMenuItem)menuItem).Checked = false;
                        if (((int)menuItem.Tag) == value)
                            toolStripMenuItem = (ToolStripMenuItem)menuItem;
                    }
                }
                if (toolStripMenuItem != null)
                {
                    toolStripMenuItem.Checked = true;
                    int period = (int)toolStripMenuItem.Tag;
                    if (period == TimeSeries.TICK_PERIOD)
                        periodButton.Text = "T";
                    else if (period >= 1 && period <= 60)
                        periodButton.Text = period.ToString();
                    else if (period == TimeSeries.DAY_FROM_TICK_PERIOD)
                        periodButton.Text = "d";
                    else if (period == TimeSeries.DAY_PERIOD)
                        periodButton.Text = "D";
                    else if (period == TimeSeries.WEEK_PERIOD)
                        periodButton.Text = "W";
                    else if (period == TimeSeries.MONTH_PERIOD)
                        periodButton.Text = "M";
                    else if (period == TimeSeries.YEAR_PERIOD)
                        periodButton.Text = "Y";
                    else
                        periodButton.Text = "X";
                }
                else
                {
                    periodButton.Text = "X";
                }
            }
        }

        private void ChangePeriod(object sender)
        {
            int period = (int)((ToolStripMenuItem)sender).Tag;
            Period = period;
            if (PeriodChanged != null)
                PeriodChanged(period);
        }

        private void tickToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void minutesToolStripMenuItem_1_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void minutesToolStripMenuItem_5_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void minutesToolStripMenuItem_10_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void minutesToolStripMenuItem_15_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void minutesToolStripMenuItem_20_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void minutesToolStripMenuItem_30_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void minutesToolStripMenuItem_60_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void dailyfromTicksToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void dailyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void weeklyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void monthlyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void yearlyToolStripMenuItem_Click(object sender, EventArgs e)
        {
            ChangePeriod(sender);
        }

        private void navigationBar_Scroll(object sender, ScrollEventArgs e)
        {
            lastPosition = e.NewValue + zoom - 1;
            DrawChart();
        }

        #endregion
    }
}
