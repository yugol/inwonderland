using System;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

using Tao.FreeGlut;
using Tao.OpenGl;
using Tao.Platform.Windows;
using TradingCommon;
using TradingCommon.Indicators;
using TradingCommon.Ui;
using TradingVisualizer.Charting;
using TradingCommon.Util;

namespace TradingVisualizer.Ui
{
    public partial class ChartContent : UserControl
    {
        public SimpleOpenGlControl Chart { get { return chart; } }
        public SimpleOpenGlControl YScale { get { return yScale; } }

        IList<VisualElement> visualElements = new List<VisualElement>();

        int first, last;
        double min, max;
        XORDrawing xorDrawing = new XORDrawing();

        public int YScaleWidth
        {
            get { return yScale.Width; }
            set { yScale.Width = value; }
        }

        public ChartContent()
        {
            InitializeComponent();
            chart.InitializeContexts();
            yScale.InitializeContexts();
        }

        private void GetMinMax(int first, int last, out double min, out double max)
        {
            min = double.MaxValue;
            max = double.MinValue;
            foreach (VisualElement ve in visualElements)
            {
                double tempMin, tempMax;
                ve.GetMinMax(first, last, out tempMin, out tempMax);
                if (tempMin < min)
                    min = tempMin;
                if (tempMax > max)
                    max = tempMax;
            }
        }

        internal void AddVisualElement(TradingVisualizer.Charting.VisualElement ve)
        {
            visualElements.Add(ve);
        }

        private void ChartContent_Paint(object sender, PaintEventArgs e)
        {
            try
            {
                first = ((MultiChartControl)(Parent.Parent)).FirstPosition;
                last = ((MultiChartControl)(Parent.Parent)).LastPosition;
                GetMinMax(first, last, out min, out max);
            }
            catch (InvalidCastException)
            {
            }
        }

        private void chart_MouseMove(object sender, MouseEventArgs e)
        {
            int entryIndex;
            double value;
            MapMouseToDateTimeValue(e.X, e.Y, out entryIndex, out value);
            ShowDateTimeValueInStatusBar(entryIndex, value);

            if (((MultiChartControl)(Parent.Parent)).ShowCrossHair)
            {
                Point crossLocation = e.Location;
                if (!crossLocation.Equals(prevCrossLocation))
                {
                    DrawCross(prevCrossLocation);
                    DrawCross(crossLocation);
                    prevCrossLocation = crossLocation;
                }
            }
        }

        void chart_MouseLeave(object sender, EventArgs e)
        {
            DeleteCross();
        }

        #region draw chart

        private void chart_Paint(object sender, PaintEventArgs e)
        {
            try
            {
                Gl.glViewport(0, 0, chart.Width, chart.Height);
                Gl.glClearColor(1F, 1F, 1F, 1F);
                Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT);
                Gl.glMatrixMode(Gl.GL_PROJECTION);

                Delta.Term deltaTerm = ((MultiChartControl)(Parent.Parent)).DeltaTerm;
                if (deltaTerm != Delta.Term.NONE)
                {
                    DateTime[] dateTimes = ((MultiChartFrame)(Parent.Parent.Parent)).TimeSeries.DateTimes;
                    DeltaGrid.Paint(dateTimes, first, last, deltaTerm);
                }

                VerticalGrid verticalGrid = ((MultiChartControl)(Parent.Parent)).VerticalGrid;
                VerticalGrid.Paint(first, last, verticalGrid);

                Gl.glLoadIdentity();
                foreach (VisualElement ve in visualElements)
                    ve.Paint(first, last, min, max);
            }
            catch (InvalidCastException)
            {
            }
        }

        private void yScale_Paint(object sender, PaintEventArgs e)
        {
            HorizontalGrid hGrid = new HorizontalGrid(min, max, Height);

            Color bg = SystemColors.Control;
            Color fg = SystemColors.ControlText;

            Gl.glViewport(0, 0, yScale.Width, yScale.Height);
            Gl.glClearColor(bg.R / 255F, bg.G / 255F, bg.B / 255F, 1F);
            Gl.glClear(Gl.GL_COLOR_BUFFER_BIT | Gl.GL_DEPTH_BUFFER_BIT);
            Gl.glLoadIdentity();
            Glu.gluOrtho2D(0, yScale.Width, min, max);

            Gl.glColor3d(fg.R / 255D, fg.G / 255D, fg.B / 255D);
            foreach (HorizontalGrid.GridPoint point in hGrid.Points)
            {
                double y = point.value;

                Gl.glBegin(Gl.GL_LINES);
                Gl.glVertex2d(0, y);
                switch (point.format)
                {
                    case HorizontalGrid.LabelFormat.MINOR:
                        Gl.glVertex2d(HorizontalGrid.MINOR_LOGIC_LENGTH, y);
                        break;
                    case HorizontalGrid.LabelFormat.MIDDLE:
                        Gl.glVertex2d(HorizontalGrid.MIDDLE_LOGIC_LENGTH, y);
                        break;
                    case HorizontalGrid.LabelFormat.MAJOR:
                    case HorizontalGrid.LabelFormat.LABEL:
                        Gl.glVertex2d(HorizontalGrid.MAJOR_LOGIC_LENGTH, y);
                        break;
                }
                Gl.glEnd();

                if (point.format == HorizontalGrid.LabelFormat.LABEL)
                {
                    Gl.glRasterPos2d(HorizontalGrid.LABEL_LOGIC_DISTANCE, y);
                    Glut.glutBitmapString(HorizontalGrid.GLUT_Y_LABELS_FONT, point.label);
                }
            }

            if (hGrid.Magnitude > 1)
            {
                String text = "x" + hGrid.Magnitude;

                int hMargin = 2;
                int vMargin = 3;
                int width = Glut.glutBitmapLength(HorizontalGrid.GLUT_Y_LABELS_FONT, text) + 2 * hMargin;
                int height = HorizontalGrid.FONT_HEIGHT + 2 * vMargin;
                int x = yScale.Width - width - 2;
                int y = 1;

                Gl.glLoadIdentity();
                Glu.gluOrtho2D(0, yScale.Width - 1, 0, yScale.Height - 1);

                Gl.glBegin(Gl.GL_QUADS);
                Gl.glVertex2d(x, y);
                Gl.glVertex2d(x + width, y);
                Gl.glVertex2d(x + width, y + height);
                Gl.glVertex2d(x, y + height);
                Gl.glEnd();

                Gl.glColor3d(bg.R / 255D, bg.G / 255D, bg.B / 255D);
                Gl.glRasterPos2d(x + hMargin + 1, y + vMargin);
                Glut.glutBitmapString(HorizontalGrid.GLUT_Y_LABELS_FONT, text);
            }
        }

        #endregion

        #region draw cross

        static Point nullPoint = new Point(-100000, -100000);
        Point prevCrossLocation = nullPoint;

        private void DeleteCross()
        {
            DrawCross(prevCrossLocation);
            prevCrossLocation = nullPoint;
        }

        private void DrawCross(Point location)
        {
            DrawHorizontalLine(location.Y);
            foreach (ChartContent chartContent in ((MultiChartPanel)Parent).Charts)
                chartContent.DrawVerticalLine(location.X);
        }

        private void DrawHorizontalLine(int y)
        {
            Point from = chart.PointToScreen(new Point(0, y));
            Point to = chart.PointToScreen(new Point(chart.Width, y));
            ControlPaint.DrawReversibleLine(from, to, Color.Black);
        }

        private void DrawVerticalLine(int x)
        {
            Point from = chart.PointToScreen(new Point(x, 0));
            Point to = chart.PointToScreen(new Point(x, chart.Height));
            ControlPaint.DrawReversibleLine(from, to, Color.Black);
        }

        #endregion

        private void ShowDateTimeValueInStatusBar(int entryIndex, double value)
        {
            Program.MainForm.CursorDateTime = ((MultiChartFrame)(Parent.Parent.Parent)).TimeSeries.DateTimes[entryIndex];
            Program.MainForm.CursorValue = value;
        }

        private void MapMouseToDateTimeValue(int x, int y, out int entryIndex, out double value)
        {
            LinearMapper valueMapper = new LinearMapper(Height - 1, 0, min, max);
            value = valueMapper.Map(y);

            double entryCount = last - first + 1;
            double entryWidth = chart.Width / entryCount;
            entryIndex = (int)Math.Floor(x / entryWidth);
            entryIndex += first;
        }
    }
}
